create sequence if not exists monitoring.alerts_seq start 1 increment 1;

create table monitoring.alerts
(
    "id"                   integer primary key default nextval('monitoring.alerts_seq'),
    "name"                 varchar  not null,
    "project_id"           integer,
    "rule"                 jsonb    not null,
    "calculation_interval" interval not null,
    "last_calculation"     timestamp
);

ALTER TABLE monitoring."alerts"
    ADD FOREIGN KEY ("project_id") REFERENCES monitoring."projects" ("id");
