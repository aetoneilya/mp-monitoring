create type monitoring."alert_status" as enum (
  'OK',
  'WARNING',
  'ERROR',
  'NONE'
);