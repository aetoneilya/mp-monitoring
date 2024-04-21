CREATE TABLE monitoring.alerts_notification_channels
(
    "alert_id"                integer,
    "notification_channel_id" integer,
    PRIMARY KEY ("alert_id", "notification_channel_id")
);

ALTER TABLE monitoring.alerts_notification_channels
    ADD FOREIGN KEY ("alert_id") REFERENCES monitoring."alerts" ("id");
ALTER TABLE monitoring.alerts_notification_channels
    ADD FOREIGN KEY ("notification_channel_id") REFERENCES monitoring."notification_channels" ("id");
