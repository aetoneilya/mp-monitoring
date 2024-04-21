create sequence if not exists monitoring.notification_channels_seq start 1 increment 1;

create table monitoring."notification_channels"
(
    "id"      integer primary key default nextval('monitoring.notification_channels_seq'),
    "type"    monitoring.channel_type not null,
    "address" jsonb                   not null
);