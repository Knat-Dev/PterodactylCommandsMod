package com.dorpeled.pterodactylcommandsmod.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Schedule {

    @JsonProperty("object")
    private String object;

    @JsonProperty("data")
    private List<ServerSchedule> data;

    // Getters and Setters
    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<ServerSchedule> getData() {
        return data;
    }

    public void setData(List<ServerSchedule> data) {
        this.data = data;
    }

    public static class ServerSchedule {

        @JsonProperty("object")
        private String object;

        @JsonProperty("attributes")
        private Attributes attributes;

        // Getters and Setters
        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public Attributes getAttributes() {
            return attributes;
        }

        public void setAttributes(Attributes attributes) {
            this.attributes = attributes;
        }

        public static class Attributes {

            @JsonProperty("id")
            private int id;

            @JsonProperty("name")
            private String name;

            @JsonProperty("cron")
            private Cron cron;

            @JsonProperty("is_active")
            private boolean isActive;

            @JsonProperty("is_processing")
            private boolean isProcessing;

            @JsonProperty("only_when_online")
            private boolean onlyWhenOnline;

            @JsonProperty("last_run_at")
            private String lastRunAt;

            @JsonProperty("next_run_at")
            private String nextRunAt;

            @JsonProperty("created_at")
            private String createdAt;

            @JsonProperty("updated_at")
            private String updatedAt;

            @JsonProperty("relationships")
            private Relationships relationships;

            // Getters and Setters
            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Cron getCron() {
                return cron;
            }

            public void setCron(Cron cron) {
                this.cron = cron;
            }

            public boolean isActive() {
                return isActive;
            }

            public void setActive(boolean isActive) {
                this.isActive = isActive;
            }

            public boolean isProcessing() {
                return isProcessing;
            }

            public void setProcessing(boolean isProcessing) {
                this.isProcessing = isProcessing;
            }

            public boolean isOnlyWhenOnline() {
                return onlyWhenOnline;
            }

            public void setOnlyWhenOnline(boolean onlyWhenOnline) {
                this.onlyWhenOnline = onlyWhenOnline;
            }

            public String getLastRunAt() {
                return lastRunAt;
            }

            public void setLastRunAt(String lastRunAt) {
                this.lastRunAt = lastRunAt;
            }

            public String getNextRunAt() {
                return nextRunAt;
            }

            public void setNextRunAt(String nextRunAt) {
                this.nextRunAt = nextRunAt;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public Relationships getRelationships() {
                return relationships;
            }

            public void setRelationships(Relationships relationships) {
                this.relationships = relationships;
            }

            public static class Cron {

                @JsonProperty("day_of_week")
                private String dayOfWeek;

                @JsonProperty("day_of_month")
                private String dayOfMonth;

                @JsonProperty("month")
                private String month;

                @JsonProperty("hour")
                private String hour;

                @JsonProperty("minute")
                private String minute;

                // Getters and Setters
                public String getDayOfWeek() {
                    return dayOfWeek;
                }

                public void setDayOfWeek(String dayOfWeek) {
                    this.dayOfWeek = dayOfWeek;
                }

                public String getDayOfMonth() {
                    return dayOfMonth;
                }

                public void setDayOfMonth(String dayOfMonth) {
                    this.dayOfMonth = dayOfMonth;
                }

                public String getMonth() {
                    return month;
                }

                public void setMonth(String month) {
                    this.month = month;
                }

                public String getHour() {
                    return hour;
                }

                public void setHour(String hour) {
                    this.hour = hour;
                }

                public String getMinute() {
                    return minute;
                }

                public void setMinute(String minute) {
                    this.minute = minute;
                }
            }

            public static class Relationships {

                @JsonProperty("tasks")
                private Tasks tasks;

                // Getters and Setters
                public Tasks getTasks() {
                    return tasks;
                }

                public void setTasks(Tasks tasks) {
                    this.tasks = tasks;
                }

                public static class Tasks {

                    @JsonProperty("object")
                    private String object;

                    @JsonProperty("data")
                    private List<ScheduleTask> data;

                    // Getters and Setters
                    public String getObject() {
                        return object;
                    }

                    public void setObject(String object) {
                        this.object = object;
                    }

                    public List<ScheduleTask> getData() {
                        return data;
                    }

                    public void setData(List<ScheduleTask> data) {
                        this.data = data;
                    }

                    public static class ScheduleTask {

                        @JsonProperty("object")
                        private String object;

                        @JsonProperty("attributes")
                        private TaskAttributes attributes;

                        // Getters and Setters
                        public String getObject() {
                            return object;
                        }

                        public void setObject(String object) {
                            this.object = object;
                        }

                        public TaskAttributes getAttributes() {
                            return attributes;
                        }

                        public void setAttributes(TaskAttributes attributes) {
                            this.attributes = attributes;
                        }

                        public static class TaskAttributes {

                            @JsonProperty("id")
                            private int id;

                            @JsonProperty("sequence_id")
                            private int sequenceId;

                            @JsonProperty("action")
                            private String action;

                            @JsonProperty("payload")
                            private String payload;

                            @JsonProperty("time_offset")
                            private int timeOffset;

                            @JsonProperty("is_queued")
                            private boolean isQueued;

                            @JsonProperty("continue_on_failure")
                            private boolean continueOnFailure;

                            @JsonProperty("created_at")
                            private String createdAt;

                            @JsonProperty("updated_at")
                            private String updatedAt;

                            // Getters and Setters
                            public int getId() {
                                return id;
                            }

                            public void setId(int id) {
                                this.id = id;
                            }

                            public int getSequenceId() {
                                return sequenceId;
                            }

                            public void setSequenceId(int sequenceId) {
                                this.sequenceId = sequenceId;
                            }

                            public String getAction() {
                                return action;
                            }

                            public void setAction(String action) {
                                this.action = action;
                            }

                            public String getPayload() {
                                return payload;
                            }

                            public void setPayload(String payload) {
                                this.payload = payload;
                            }

                            public int getTimeOffset() {
                                return timeOffset;
                            }

                            public void setTimeOffset(int timeOffset) {
                                this.timeOffset = timeOffset;
                            }

                            public boolean isQueued() {
                                return isQueued;
                            }

                            public void setQueued(boolean isQueued) {
                                this.isQueued = isQueued;
                            }

                            public boolean isContinueOnFailure() {
                                return continueOnFailure;
                            }

                            public void setContinueOnFailure(boolean continueOnFailure) {
                                this.continueOnFailure = continueOnFailure;
                            }

                            public String getCreatedAt() {
                                return createdAt;
                            }

                            public void setCreatedAt(String createdAt) {
                                this.createdAt = createdAt;
                            }

                            public String getUpdatedAt() {
                                return updatedAt;
                            }

                            public void setUpdatedAt(String updatedAt) {
                                this.updatedAt = updatedAt;
                            }
                        }
                    }
                }
            }
        }
    }
}
