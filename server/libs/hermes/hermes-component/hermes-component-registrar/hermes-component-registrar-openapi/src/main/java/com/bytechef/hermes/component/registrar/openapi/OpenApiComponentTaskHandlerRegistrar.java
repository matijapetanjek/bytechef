/*
 * Copyright 2021 <your company/name>.
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

package com.bytechef.hermes.component.registrar.openapi;

import com.bytechef.atlas.event.EventPublisher;
import com.bytechef.atlas.worker.task.handler.TaskHandler;
import com.bytechef.hermes.component.OpenApiComponentHandler;
import com.bytechef.hermes.component.definition.Action;
import com.bytechef.hermes.component.registrar.AbstractTaskHandlerRegistrar;
import com.bytechef.hermes.connection.service.ConnectionService;
import com.bytechef.hermes.file.storage.service.FileStorageService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.stereotype.Component;

/**
 * @author Ivica Cardic
 */
@Component
public class OpenApiComponentTaskHandlerRegistrar extends AbstractTaskHandlerRegistrar<OpenApiComponentHandler> {

    private final ConnectionService connectionService;
    private final EventPublisher eventPublisher;
    private final FileStorageService fileStorageService;

    @SuppressFBWarnings("EI2")
    public OpenApiComponentTaskHandlerRegistrar(
            ConnectionService connectionService, EventPublisher eventPublisher, FileStorageService fileStorageService) {
        super(OpenApiComponentHandler.class);
        this.connectionService = connectionService;
        this.eventPublisher = eventPublisher;
        this.fileStorageService = fileStorageService;
    }

    @Override
    protected TaskHandler<?> createTaskHandler(Action action, OpenApiComponentHandler openApiComponentHandler) {
        return new OpenApiComponentTaskHandler(
                action, connectionService, openApiComponentHandler, eventPublisher, fileStorageService);
    }
}
