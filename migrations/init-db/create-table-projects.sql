create sequence if not exists monitoring.projects_seq start 1 increment 1;

create table monitoring.projects
(
    "id"          integer primary key default nextval('monitoring.projects_seq'),
    "name"        varchar   not null unique,
    "description" varchar,
    "created_at"  timestamp not null
);