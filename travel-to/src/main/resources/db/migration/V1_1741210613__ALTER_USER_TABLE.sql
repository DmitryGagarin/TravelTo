ALTER TABLE users
    ADD COLUMN name varchar,
    ADD COLUMN surname varchar,
    ADD COLUMN phone varchar,
    DROP COLUMN username;