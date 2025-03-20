alter table attraction
    add column owner_id bigint not null default 0;

alter table attraction
    add constraint fk_user foreign key (owner_id) references "users" (id)