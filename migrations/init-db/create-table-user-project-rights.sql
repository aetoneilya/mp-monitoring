create table monitoring.user_project_rights
(
    "user_id"      integer,
    "project_id"   integer,
    "access_right" monitoring.access_rights not null,
    primary key (user_id, project_id)
);

alter table monitoring."user_project_rights"
    add foreign key ("user_id") references monitoring."users" ("id");

alter table monitoring."user_project_rights"
    add foreign key ("project_id") references monitoring."projects" ("id");