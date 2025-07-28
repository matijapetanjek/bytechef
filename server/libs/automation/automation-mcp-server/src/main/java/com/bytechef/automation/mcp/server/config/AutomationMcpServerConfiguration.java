/*
 * Copyright 2025 ByteChef
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bytechef.automation.mcp.server.config;

import com.bytechef.platform.ai.mcp.ToolDTO;
import com.bytechef.platform.ai.mcp.ToolFacade;
import com.bytechef.platform.constant.Environment;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.WebMvcSseServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpServerTransportProvider;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * @author Ivica Cardic
 */
@Configuration
public class AutomationMcpServerConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(AutomationMcpServerConfiguration.class);

    private final ToolFacade toolFacade;

    @SuppressFBWarnings("EI")
    public AutomationMcpServerConfiguration(@Qualifier("automationToolFacade") ToolFacade toolFacade) {
        this.toolFacade = toolFacade;
    }

    @Bean
    public RouterFunction<ServerResponse> mvcMcpRouterFunction(WebMvcSseServerTransportProvider transportProvider) {
        return transportProvider.getRouterFunction();
    }

    @Bean
    WebMvcSseServerTransportProvider webMvcSseServerTransportProvider(ObjectMapper objectMapper) {
        return new WebMvcSseServerTransportProvider(objectMapper, "/api/automation/v1/mcp/message", "/api/automation/sse");
    }

    @Bean
    public McpSyncServer automationMCPSyncServer(
        McpServerTransportProvider transportProvider) {

        McpSchema.ServerCapabilities.Builder capabilitiesBuilder = McpSchema.ServerCapabilities.builder();

        McpSchema.Implementation serverInfo = new McpSchema.Implementation("bytechef-mcp-server", "1.0.0");

        McpServer.SyncSpecification serverBuilder = McpServer.sync(transportProvider).serverInfo(serverInfo);

        capabilitiesBuilder.tools(true);

        List<McpServerFeatures.SyncToolSpecification> syncToolSpecifications =
            toSyncToolSpecifications(getToolCallbacks());

        if (!CollectionUtils.isEmpty(syncToolSpecifications)) {
            serverBuilder.tools(syncToolSpecifications);
            logger.info("Registered tools: " + syncToolSpecifications.size());
        }

        serverBuilder.capabilities(capabilitiesBuilder.build());

        serverBuilder.requestTimeout(Duration.ofSeconds(20));

        return serverBuilder.build();
    }

    public List<ToolCallback> getToolCallbacks() {
        List<ToolCallback> toolCallbacks = new ArrayList<>();

        List<ToolDTO> toolDTOs = toolFacade.getTools();

        for (ToolDTO toolDTO : toolDTOs) {
            FunctionToolCallback.Builder<Map<String, Object>, Object> builder = FunctionToolCallback
                .builder(toolDTO.name(), getToolCallbackFunction(toolDTO.name(), toolDTO.connectionId()))
                .inputType(Map.class)
                .inputSchema(toolDTO.parameters());

            if (toolDTO.description() != null) {
                builder.description(toolDTO.description());
            }

            toolCallbacks.add(builder.build());
        }

        return toolCallbacks;
    }

    private Function<Map<String, Object>, Object> getToolCallbackFunction(String toolName, Long connectionId) {
        return inputParameters ->
            toolFacade.executeTool(toolName, inputParameters, connectionId, Environment.PRODUCTION);
    }

    private List<McpServerFeatures.SyncToolSpecification> toSyncToolSpecifications(List<ToolCallback> tools) {

        // De-duplicate tools by their name, keeping the first occurrence of each tool
        // name
        return tools.stream() // Key: tool name
            .collect(Collectors.toMap(tool -> tool.getToolDefinition().name(), tool -> tool, // Value:
                // the
                // tool
                // itself
                (existing, replacement) -> existing)) // On duplicate key, keep the
            // existing tool
            .values()
            .stream()
            .map(tool -> {
                String toolName = tool.getToolDefinition().name();
                return McpToolUtils.toSyncToolSpecification(tool, null);
            })
            .toList();
    }
}
