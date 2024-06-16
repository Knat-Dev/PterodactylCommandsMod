package com.dorpeled.pterodactylcommandsmod.models

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class ServerScheduleList(
    @JsonProperty("object") val objectType: String,
    @JsonProperty("data") val data: List<ServerScheduleWrapper>
)

data class ServerScheduleWrapper(
    @JsonProperty("object") val objectType: String,
    @JsonProperty("attributes") val attributes: ServerSchedule
) {
    fun hasPowerAction(): Boolean {
        return this.attributes.relationships.tasks.data
            .any { it.attributes.action == "power" }
    }
}

data class ServerSchedule(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("cron") val cron: CronExpression,
    @JsonProperty("is_active") val isActive: Boolean,
    @JsonProperty("is_processing") val isProcessing: Boolean,
    @JsonProperty("only_when_online") val onlyWhenOnline: Boolean,
    @JsonProperty("last_run_at") val lastRunAt: OffsetDateTime?,
    @JsonProperty("next_run_at") val nextRunAt: OffsetDateTime,
    @JsonProperty("created_at") val createdAt: OffsetDateTime,
    @JsonProperty("updated_at") val updatedAt: OffsetDateTime,
    @JsonProperty("relationships") val relationships: Relationships
)

data class CronExpression(
    @JsonProperty("day_of_week") val dayOfWeek: String,
    @JsonProperty("day_of_month") val dayOfMonth: String,
    @JsonProperty("month") val month: String,
    @JsonProperty("hour") val hour: String,
    @JsonProperty("minute") val minute: String
)

data class Relationships(
    @JsonProperty("tasks") val tasks: TasksWrapper
)

data class TasksWrapper(
    @JsonProperty("object") val objectType: String,
    @JsonProperty("data") val data: List<ScheduleTask>
)

data class ScheduleTask(
    @JsonProperty("object") val objectType: String,
    @JsonProperty("attributes") val attributes: TaskAttributes
)

data class TaskAttributes(
    @JsonProperty("id") val id: Int,
    @JsonProperty("sequence_id") val sequenceId: Int,
    @JsonProperty("action") val action: String,
    @JsonProperty("payload") val payload: String,
    @JsonProperty("time_offset") val timeOffset: Int,
    @JsonProperty("is_queued") val isQueued: Boolean,
    @JsonProperty("continue_on_failure") val continueOnFailure: Boolean,
    @JsonProperty("created_at") val createdAt: OffsetDateTime,
    @JsonProperty("updated_at") val updatedAt: OffsetDateTime
)