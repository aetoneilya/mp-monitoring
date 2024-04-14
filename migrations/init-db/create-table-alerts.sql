create table monitoring.alerts
(
    id                       integer primary key,
    name                     varchar,
    project_id               integer,
    rule                     json,
    calculation_interval     interval,
    last_calculation         timestamp
);

ALTER TABLE monitoring."alerts"
    ADD FOREIGN KEY ("project_id") REFERENCES monitoring."projects" ("id");