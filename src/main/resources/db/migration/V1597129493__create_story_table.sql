CREATE TABLE story (
    id          bigserial primary key,
    title       varchar ,
    user_name   varchar(20),
    url         varchar  ,
    created_at  TIMESTAMP(6) not null,
    score       numeric ,
    story_id    bigint);
