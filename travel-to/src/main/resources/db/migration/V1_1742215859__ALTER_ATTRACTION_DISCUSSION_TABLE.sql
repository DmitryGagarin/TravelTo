alter table attraction_discussion
    add column created_at timestamp not null default now(),
    add column updated_at timestamp not null default now()