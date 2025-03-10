CREATE TABLE attraction (
    id           serial  primary key,
    uuid         varchar not null       default gen_random_uuid(),
    name         varchar not null,
    description  varchar not null,
    address      varchar not null,
    image        varchar not null,
    phone        varchar not null,
    website      varchar not null,
    type         varchar not null,
    open_time    timestamp,
    close_time   timestamp
)