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

package com.bytechef.platform.configuration.domain.notification;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Matija Petanjek
 */
@Table("notification_notification_event")
public class NotificationEvent {

    @Id
    private Long id;
    @Column("notification_id")
    private Long notificationId;
    @Column("event_id")
    private Long eventId;

    public NotificationEvent(Long notificationId, Long eventId) {
        this.notificationId = notificationId;
        this.eventId = eventId;
    }

    public Long getEventId() {
        return eventId;
    }

    public Long getNotificationId() {
        return notificationId;
    }
}
