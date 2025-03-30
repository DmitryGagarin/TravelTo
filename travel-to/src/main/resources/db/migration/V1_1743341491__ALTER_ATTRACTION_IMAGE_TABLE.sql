alter table attraction_image
    add column if not exists image_format varchar not null default 'png'