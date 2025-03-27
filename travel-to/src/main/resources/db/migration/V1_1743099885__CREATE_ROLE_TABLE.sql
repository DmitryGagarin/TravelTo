create table role
(
    id serial primary key not null,
    uuid varchar not null default gen_random_uuid(),
    role varchar not null default ''
);

insert into role (id, uuid, role) values (1, 'a98e9e63-f35f-4f2e-8f69-5815c333a207', 'ADMIN');
insert into role (id, uuid, role) values (2, '1dd36330-536d-40e4-81e7-fd074ac37fbd', 'OWNER');
insert into role (id, uuid, role) values (3, '8cdd60bc-ed60-4d81-8152-9aac1086309b', 'USER');
insert into role (id, uuid, role) values (4, 'ef4fb3d3-826c-401b-ae89-b02bf0e9aad7','DISCUSSION_OWNER')
