package com.dorpeled.pterodactylcommandsmod.models

import com.fasterxml.jackson.annotation.JsonProperty

class Schedule {
    @JsonProperty("object")
    private var `object`: String? = null

    @JsonProperty("data")
    private var data: List<ServerSchedule?>? = null

    // Getters and Setters
    fun getObject(): String? {
        return `object`
    }

    fun setObject(`object`: String?) {
        this.`object` = `object`
    }

    fun getData(): List<ServerSchedule?>? {
        return data
    }

    fun setData(data: List<ServerSchedule?>?) {
        this.data = data
    }

    class ServerSchedule {
        @JsonProperty("object")
        private var `object`: String? = null

        @JsonProperty("attributes")
        private var attributes: Attributes? = null

        // Getters and Setters
        fun getObject(): String? {
            return `object`
        }

        fun setObject(`object`: String?) {
            this.`object` = `object`
        }

        fun getAttributes(): Attributes? {
            return attributes
        }

        fun setAttributes(attributes: Attributes?) {
            this.attributes = attributes
        }

        class Attributes {
            @JsonProperty("id")
            private var id = 0

            @JsonProperty("name")
            private var name: String? = null

            @JsonProperty("cron")
            private var cron: Cron? = null

            @JsonProperty("is_active")
            private var isActive = false

            @JsonProperty("is_processing")
            private var isProcessing = false

            @JsonProperty("only_when_online")
            private var onlyWhenOnline = false

            @JsonProperty("last_run_at")
            private var lastRunAt: String? = null

            @JsonProperty("next_run_at")
            private var nextRunAt: String? = null

            @JsonProperty("created_at")
            private var createdAt: String? = null

            @JsonProperty("updated_at")
            private var updatedAt: String? = null

            @JsonProperty("relationships")
            private var relationships: Relationships? = null

            // Getters and Setters
            fun getId(): Int {
                return id
            }

            fun setId(id: Int) {
                this.id = id
            }

            fun getName(): String? {
                return name
            }

            fun setName(name: String?) {
                this.name = name
            }

            fun getCron(): Cron? {
                return cron
            }

            fun setCron(cron: Cron?) {
                this.cron = cron
            }

            fun isActive(): Boolean {
                return isActive
            }

            fun setActive(isActive: Boolean) {
                this.isActive = isActive
            }

            fun isProcessing(): Boolean {
                return isProcessing
            }

            fun setProcessing(isProcessing: Boolean) {
                this.isProcessing = isProcessing
            }

            fun isOnlyWhenOnline(): Boolean {
                return onlyWhenOnline
            }

            fun setOnlyWhenOnline(onlyWhenOnline: Boolean) {
                this.onlyWhenOnline = onlyWhenOnline
            }

            fun getLastRunAt(): String? {
                return lastRunAt
            }

            fun setLastRunAt(lastRunAt: String?) {
                this.lastRunAt = lastRunAt
            }

            fun getNextRunAt(): String? {
                return nextRunAt
            }

            fun setNextRunAt(nextRunAt: String?) {
                this.nextRunAt = nextRunAt
            }

            fun getCreatedAt(): String? {
                return createdAt
            }

            fun setCreatedAt(createdAt: String?) {
                this.createdAt = createdAt
            }

            fun getUpdatedAt(): String? {
                return updatedAt
            }

            fun setUpdatedAt(updatedAt: String?) {
                this.updatedAt = updatedAt
            }

            fun getRelationships(): Relationships? {
                return relationships
            }

            fun setRelationships(relationships: Relationships?) {
                this.relationships = relationships
            }

            class Cron {
                @JsonProperty("day_of_week")
                private var dayOfWeek: String? = null

                @JsonProperty("day_of_month")
                private var dayOfMonth: String? = null

                @JsonProperty("month")
                private var month: String? = null

                @JsonProperty("hour")
                private var hour: String? = null

                @JsonProperty("minute")
                private var minute: String? = null

                // Getters and Setters
                fun getDayOfWeek(): String? {
                    return dayOfWeek
                }

                fun setDayOfWeek(dayOfWeek: String?) {
                    this.dayOfWeek = dayOfWeek
                }

                fun getDayOfMonth(): String? {
                    return dayOfMonth
                }

                fun setDayOfMonth(dayOfMonth: String?) {
                    this.dayOfMonth = dayOfMonth
                }

                fun getMonth(): String? {
                    return month
                }

                fun setMonth(month: String?) {
                    this.month = month
                }

                fun getHour(): String? {
                    return hour
                }

                fun setHour(hour: String?) {
                    this.hour = hour
                }

                fun getMinute(): String? {
                    return minute
                }

                fun setMinute(minute: String?) {
                    this.minute = minute
                }
            }

            class Relationships {
                @JsonProperty("tasks")
                private var tasks: Tasks? = null

                // Getters and Setters
                fun getTasks(): Tasks? {
                    return tasks
                }

                fun setTasks(tasks: Tasks?) {
                    this.tasks = tasks
                }

                class Tasks {
                    @JsonProperty("object")
                    private var `object`: String? = null

                    @JsonProperty("data")
                    private var data: List<ScheduleTask?>? = null

                    // Getters and Setters
                    fun getObject(): String? {
                        return `object`
                    }

                    fun setObject(`object`: String?) {
                        this.`object` = `object`
                    }

                    fun getData(): List<ScheduleTask?>? {
                        return data
                    }

                    fun setData(data: List<ScheduleTask?>?) {
                        this.data = data
                    }

                    class ScheduleTask {
                        @JsonProperty("object")
                        private var `object`: String? = null

                        @JsonProperty("attributes")
                        private var attributes: TaskAttributes? = null

                        // Getters and Setters
                        fun getObject(): String? {
                            return `object`
                        }

                        fun setObject(`object`: String?) {
                            this.`object` = `object`
                        }

                        fun getAttributes(): TaskAttributes? {
                            return attributes
                        }

                        fun setAttributes(attributes: TaskAttributes?) {
                            this.attributes = attributes
                        }

                        class TaskAttributes {
                            @JsonProperty("id")
                            private var id = 0

                            @JsonProperty("sequence_id")
                            private var sequenceId = 0

                            @JsonProperty("action")
                            private var action: String? = null

                            @JsonProperty("payload")
                            private var payload: String? = null

                            @JsonProperty("time_offset")
                            private var timeOffset = 0

                            @JsonProperty("is_queued")
                            private var isQueued = false

                            @JsonProperty("continue_on_failure")
                            private var continueOnFailure = false

                            @JsonProperty("created_at")
                            private var createdAt: String? = null

                            @JsonProperty("updated_at")
                            private var updatedAt: String? = null

                            // Getters and Setters
                            fun getId(): Int {
                                return id
                            }

                            fun setId(id: Int) {
                                this.id = id
                            }

                            fun getSequenceId(): Int {
                                return sequenceId
                            }

                            fun setSequenceId(sequenceId: Int) {
                                this.sequenceId = sequenceId
                            }

                            fun getAction(): String? {
                                return action
                            }

                            fun setAction(action: String?) {
                                this.action = action
                            }

                            fun getPayload(): String? {
                                return payload
                            }

                            fun setPayload(payload: String?) {
                                this.payload = payload
                            }

                            fun getTimeOffset(): Int {
                                return timeOffset
                            }

                            fun setTimeOffset(timeOffset: Int) {
                                this.timeOffset = timeOffset
                            }

                            fun isQueued(): Boolean {
                                return isQueued
                            }

                            fun setQueued(isQueued: Boolean) {
                                this.isQueued = isQueued
                            }

                            fun isContinueOnFailure(): Boolean {
                                return continueOnFailure
                            }

                            fun setContinueOnFailure(continueOnFailure: Boolean) {
                                this.continueOnFailure = continueOnFailure
                            }

                            fun getCreatedAt(): String? {
                                return createdAt
                            }

                            fun setCreatedAt(createdAt: String?) {
                                this.createdAt = createdAt
                            }

                            fun getUpdatedAt(): String? {
                                return updatedAt
                            }

                            fun setUpdatedAt(updatedAt: String?) {
                                this.updatedAt = updatedAt
                            }
                        }
                    }
                }
            }
        }
    }
}
