alter table attraction
    add column owner_id bigint not null;

alter table attraction
    add constraint fk_user foreign key (owner_id) references "users" (id)