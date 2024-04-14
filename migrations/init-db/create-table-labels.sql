create table monitoring."labels" (
  "id" integer primary key,
  "sensor_id" integer,
  "label_name" varchar,
  "label_value" varchar
);

ALTER TABLE monitoring."labels" ADD FOREIGN KEY ("sensor_id") REFERENCES monitoring."sensors" ("id");