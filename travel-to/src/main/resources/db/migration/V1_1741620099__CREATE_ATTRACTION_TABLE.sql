create table attraction
(
    id           serial  primary key,
    uuid         varchar not null       default gen_random_uuid(),
    name         varchar not null,
    description  varchar not null,
    address      varchar not null,
    image        varchar,
    phone        varchar not null,
    website      varchar not null,
    type         varchar not null,
    open_time    varchar not null,
    close_time   varchar not null
)