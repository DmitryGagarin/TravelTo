ALTER TABLE attraction
    ALTER COLUMN image SET DATA TYPE bytea
        USING image::bytea;
