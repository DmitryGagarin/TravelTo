create table usability_questionnaire
(
    id          serial primary key     not null,
    uuid        varchar                not null     default gen_random_uuid(),
    answerer_id int                   not null     default 0,
    q1          int                   not null     default 0,
    q2          int                   not null     default 0,
    q3          int                   not null     default 0,
    q4          int                   not null     default 0,
    g5          int                   not null     default 0,
    q6          int                   not null     default 0,
    q7          int                   not null     default 0,
    q8          int                   not null     default 0,
    q9          int                   not null     default 0,
    q10         int                   not null     default 0
)