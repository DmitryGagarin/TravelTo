CREATE TABLE users
(
    id         serial PRIMARY KEY                                   not null,
    uuid       varchar             default gen_random_uuid()        not null,
    created_at timestamp           default now()                    not null,
    updated_at timestamp           default now()                    not null,
    username   varchar                                              not null,
    email      varchar                                              not null,
    password   varchar                                              not null
)