create table if not exists users
(
    user_id bigint not null,
    name    varchar(255) not null,
    email   varchar(255) not null,
    password text not null,
    version int not null default 1,
    deleted boolean not null default false,
    created_at timestamp not null default current_timestamp,
    created_by varchar(255),
    updated_at timestamp not null default current_timestamp,
    updated_by varchar(255),
    constraint user_pk primary key (user_id)
);
create unique index user_email_idx on users (email);

create table if not exists steps
(
    step_id bigint not null,
    user_id bigint not null,
    date    date not null,
    steps   int not null,
    version int not null default 1,
    deleted boolean not null default false,
    created_at timestamp not null default current_timestamp,
    created_by varchar(255),
    updated_at timestamp not null default current_timestamp,
    updated_by varchar(255),
    constraint step_pk primary key (step_id)
);
create unique index step_user_date_idx on steps (user_id, date);
create index step_date_idx on steps (date);

create table if not exists step_archives
(
    step_archive_id bigint not null,
    step_id bigint not null,
    user_id bigint not null,
    date    date not null,
    steps   int not null,
    version int not null default 1,
    deleted boolean not null default false,
    created_at timestamp not null default current_timestamp,
    created_by varchar(255),
    updated_at timestamp not null default current_timestamp,
    updated_by varchar(255),
    constraint step_archive_pk primary key (step_archive_id)
);
create unique index step_archive_step_id_idx on step_archives (step_id);
