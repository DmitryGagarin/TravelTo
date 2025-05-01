alter table file_menu
    add column attraction_id bigint not null default 0;

alter table text_menu
    add column attraction_id bigint not null default 0;