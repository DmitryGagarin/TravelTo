create table attraction (
    id           serial  primary key,
    uuid         varchar not null       default gen_random_uuid(),
    name         varchar not null,
    description  varchar not null,
    address      varchar not null,
    image        varchar,
    phone        varchar,
    website      varchar,
    type         varchar,
    open_time    varchar,
    close_time   varchar
)