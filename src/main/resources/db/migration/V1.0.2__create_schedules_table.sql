create table if not exists schedules
(
    schedule_id bigint not null,
    code varchar(255) not null,
    next_run_at timestamp not null,
    version int not null default 1,
    created_at timestamp not null default current_timestamp,
    created_by varchar(255),
    updated_at timestamp not null default current_timestamp,
    updated_by varchar(255),
    constraint schedule_pk primary key (schedule_id)
);
create unique index if not exists schedule_code_unique_idx on schedules (code);

insert into schedules (schedule_id, code, next_run_at, created_by, updated_by) values (100000000000000000, 'CLEAN_PREVIOUS_MONTH_DATA', current_timestamp, 'system', 'system');
