create table monitoring."sensors" (
  "id" integer primary key,
  "project_id" integer,
  "clickhouse_id" varchar
);


ALTER TABLE monitoring."sensors" ADD FOREIGN KEY ("project_id") REFERENCES monitoring."projects" ("id");
