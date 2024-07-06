/*
 * Copyright 2023-present ByteChef Inc.
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

package com.bytechef.automation.configuration.web.rest.json;

import com.bytechef.automation.configuration.web.rest.model.WorkflowConnectionModel;
import com.bytechef.automation.configuration.web.rest.model.WorkflowTaskModel;
import com.bytechef.commons.util.JsonUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;

/**
 * @author Ivica Cardic
 */
@JsonComponent
public class WorkflowTaskModelJsonSerializer extends JsonSerializer<WorkflowTaskModel> {

    @Override
    public void serialize(WorkflowTaskModel value, JsonGenerator jsonGenerator, SerializerProvider serializers)
        throws IOException {

        jsonGenerator.writeStartObject();

        jsonGenerator.writeArrayFieldStart("connections");

        for (WorkflowConnectionModel workflowConnectionModel : value.getConnections()) {
            jsonGenerator.writePOJO(workflowConnectionModel);
        }

        jsonGenerator.writeEndArray();

        jsonGenerator.writeStringField("description", value.getDescription());
        jsonGenerator.writeObjectField("destination", value.getDestination());
        jsonGenerator.writeObjectField("", value.getFinalize());

        jsonGenerator.writeArrayFieldStart("finalize");

        for (WorkflowTaskModel workflowTaskModel : value.getFinalize()) {
            jsonGenerator.writePOJO(workflowTaskModel);
        }

        jsonGenerator.writeEndArray();

        jsonGenerator.writeStringField("label", value.getLabel());
        jsonGenerator.writeObjectField("metadata", value.getMetadata());
        jsonGenerator.writeStringField("name", value.getName());
        jsonGenerator.writeStringField("node", value.getNode());

        jsonGenerator.writeArrayFieldStart("post");

        for (WorkflowTaskModel workflowTaskModel : value.getPost()) {
            jsonGenerator.writePOJO(workflowTaskModel);
        }

        jsonGenerator.writeEndArray();

        jsonGenerator.writeArrayFieldStart("pre");

        for (WorkflowTaskModel workflowTaskModel : value.getPre()) {
            jsonGenerator.writePOJO(workflowTaskModel);
        }

        jsonGenerator.writeEndArray();

        jsonGenerator.writeObjectField("source", value.getSource());
        jsonGenerator.writeStringField("timeout", value.getTimeout());
        jsonGenerator.writeStringField("type", value.getType());

        jsonGenerator.writeFieldName("parameters");
        jsonGenerator.writeRaw(":");
        jsonGenerator.writeRaw(JsonUtils.write(value.getParameters(), true));

        jsonGenerator.writeEndObject();
    }
}