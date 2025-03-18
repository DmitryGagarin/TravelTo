alter table attraction
    alter column image set data type bytea
        using image::bytea;
