create sequence if not exists monitoring.sensors_seq start 1 increment 1;

create table monitoring."sensors"
(
    "id"                  integer primary key default nextval('monitoring.sensors_seq'),
    "project_id"          integer,
    "storage_sensor_name" varchar not null
);


ALTER TABLE monitoring."sensors"
    ADD FOREIGN KEY ("project_id") REFERENCES monitoring."projects" ("id");
