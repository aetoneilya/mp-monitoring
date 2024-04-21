create sequence if not exists monitoring.users_seq start 1 increment 1;

create table monitoring."users"
(
    "id"         integer primary key default nextval('monitoring.users_seq'),
    "username"   varchar   not null,
    "created_at" timestamp not null
);