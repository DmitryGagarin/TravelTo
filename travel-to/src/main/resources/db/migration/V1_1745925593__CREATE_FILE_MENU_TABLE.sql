create table file_menu
(
    id serial primary key not null,
    uuid varchar not null default gen_random_uuid()
);

create table file_menu_element
(
    id serial primary key not null,
    uuid varchar not null default gen_random_uuid(),
    file bytea not null,
    attraction_id bigint not null default 0,
    menu_id bigint not null,
    constraint fk_file_menu
        foreign key (menu_id)
        references file_menu (id)
        on delete cascade
);

create table text_menu
(
    id serial primary key not null,
    uuid varchar not null default gen_random_uuid()
);

create table text_menu_element
(
    id serial primary key not null,
    uuid varchar not null default gen_random_uuid(),
    dish_name varchar not null default ' ',
    dish_description varchar not null default ' ',
    dish_price varchar not null default ' ',
    dish_image bytea not null,
    attraction_id bigint not null default 0,
    menu_id bigint not null,
    constraint fk_text_menu
        foreign key (menu_id)
            references text_menu (id)
            on delete cascade
)