ALTER TABLE monitoring.users
    ADD password VARCHAR(255);

ALTER TABLE monitoring.users
    ALTER COLUMN password SET NOT NULL;

ALTER TABLE monitoring.alerts
    ALTER COLUMN calculation_interval DROP NOT NULL;

ALTER TABLE monitoring.projects
    ALTER COLUMN description TYPE TEXT USING (description::TEXT);

ALTER TABLE monitoring.labels
    ALTER COLUMN label_name TYPE VARCHAR(255) USING (label_name::VARCHAR(255));

ALTER TABLE monitoring.labels
    ALTER COLUMN label_value TYPE VARCHAR(255) USING (label_value::VARCHAR(255));

ALTER TABLE monitoring.alerts
    ALTER COLUMN name TYPE TEXT USING (name::TEXT);

ALTER TABLE monitoring.projects
    ALTER COLUMN name TYPE TEXT USING (name::TEXT);

ALTER TABLE monitoring.sensors
    ALTER COLUMN storage_sensor_name TYPE TEXT USING (storage_sensor_name::TEXT);

ALTER TABLE monitoring.notification_channels
    DROP COLUMN type;

ALTER TABLE monitoring.notification_channels
    ADD type VARCHAR(255) NOT NULL;

ALTER TABLE monitoring.users
    ALTER COLUMN username TYPE VARCHAR(255) USING (username::VARCHAR(255));