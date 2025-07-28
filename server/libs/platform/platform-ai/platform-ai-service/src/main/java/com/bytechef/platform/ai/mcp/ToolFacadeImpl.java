package com.bytechef.platform.ai.mcp;

import com.bytechef.platform.component.domain.ClusterElementDefinition;
import com.bytechef.platform.component.facade.ClusterElementDefinitionFacade;
import com.bytechef.platform.component.service.ClusterElementDefinitionService;
import com.bytechef.platform.component.util.JsonSchemaGeneratorUtils;
import com.bytechef.platform.configuration.domain.McpComponent;
import com.bytechef.platform.configuration.service.McpComponentService;
import com.bytechef.platform.configuration.service.McpToolService;
import com.bytechef.platform.constant.Environment;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Matija Petanjek
 */
@Service("automationToolFacade")
public class ToolFacadeImpl implements ToolFacade {

    private final ClusterElementDefinitionService clusterElementDefinitionService;
    private final ClusterElementDefinitionFacade clusterElementDefinitionFacade;
    private final McpComponentService mcpComponentService;
    private final McpToolService mcpToolService;


    @SuppressFBWarnings("EI")
    public ToolFacadeImpl(
        ClusterElementDefinitionService clusterElementDefinitionService,
        ClusterElementDefinitionFacade clusterElementDefinitionFacade,
        McpComponentService mcpComponentService, McpToolService mcpToolService) {

        this.clusterElementDefinitionService = clusterElementDefinitionService;
        this.clusterElementDefinitionFacade = clusterElementDefinitionFacade;
        this.mcpComponentService = mcpComponentService;
        this.mcpToolService = mcpToolService;
    }

    @Override
    public List<ToolDTO> getTools() {
        return mcpToolService.getMcpTools()
            .stream()
            .map(mcpTool -> {
                McpComponent mcpComponent = mcpComponentService.getMcpComponent(mcpTool.getMcpComponentId());

                ClusterElementDefinition clusterElementDefinition =
                    clusterElementDefinitionService.getClusterElementDefinition(
                        mcpComponent.getComponentName(), mcpTool.getName());

                return new ToolDTO(
                    getToolName(mcpComponent.getComponentName(), clusterElementDefinition.getName()),
                    clusterElementDefinition.getDescription(),
                    JsonSchemaGeneratorUtils.generateInputSchema(clusterElementDefinition.getProperties()),
                    mcpComponent.getConnectionId()
                );
            })
            .toList();
    }

    @Override
    public Object executeTool(
        String toolName, Map<String, Object> inputParameters, Long connectionId, Environment environment) {

        ComponentClusterElementNameResult result = getComponentClusterElementNames(toolName);

        ClusterElementDefinition clusterElementDefinition = clusterElementDefinitionService.getClusterElementDefinition(
            result.componentName(), result.clusterElementName());

        String componentName = clusterElementDefinition.getComponentName();

        return clusterElementDefinitionFacade.executeTool(
            componentName, clusterElementDefinition.getComponentVersion(), clusterElementDefinition.getName(),
            inputParameters, connectionId);
    }

    private String getToolName(String componentName, String clusterElementName) {
        StringBuilder sb = new StringBuilder();

        sb.append(componentName.toUpperCase());
        sb.append("_");

        sb.append(Character.toUpperCase(clusterElementName.charAt(0)));

        for (int i = 1; i < clusterElementName.length(); i++) {
            char c = clusterElementName.charAt(i);

            if (Character.isUpperCase(c)) {
                sb.append('_')
                    .append(c);
            } else {
                sb.append(Character.toUpperCase(c));
            }
        }

        return sb.toString();
    }

    private static ComponentClusterElementNameResult getComponentClusterElementNames(String toolName) {
        String lowerCase = toolName.toLowerCase();

        String[] parts = lowerCase.split("_");

        String componentName = parts[0];
        StringBuilder clusterElementName = new StringBuilder();

        for (int i = 1; i < parts.length; i++) {
            String part = parts[i];

            if (!part.isEmpty()) {
                if (clusterElementName.isEmpty()) {
                    clusterElementName.append(part);
                } else {
                    clusterElementName.append(Character.toUpperCase(part.charAt(0)));
                    clusterElementName.append(part.substring(1));
                }
            }
        }

        return new ComponentClusterElementNameResult(componentName, clusterElementName.toString());
    }

    private record ComponentClusterElementNameResult(String componentName, String clusterElementName) {
    }
}
