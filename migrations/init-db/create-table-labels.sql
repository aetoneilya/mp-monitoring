create sequence if not exists monitoring.labels_seq start 1 increment 1;

create table monitoring."labels"
(
    "id"          integer primary key default nextval('monitoring.labels_seq'),
    "sensor_id"   integer,
    "label_name"  varchar not null,
    "label_value" varchar
);

ALTER TABLE monitoring."labels"
    ADD FOREIGN KEY ("sensor_id") REFERENCES monitoring."sensors" ("id");