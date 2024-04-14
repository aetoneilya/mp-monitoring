create table monitoring."notification_channels" (
  "id" integer primary key,
  "type" monitoring.channel_type,
  "address" json
);