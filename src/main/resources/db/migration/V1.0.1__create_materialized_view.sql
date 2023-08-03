create materialized view if not exists daily_ranking as
    select s.step_id,
           s.user_id,
           s.steps
    from steps s
    where s.date = to_char(current_date at time zone 'ALMST', 'yyyy-MM-dd')::date;


create materialized view if not exists weekly_ranking as
    select max(s.step_id) as step_id,
           s.user_id,
           sum(s.steps) as steps
    from steps s
    where s.date >= date_trunc('week', now()) - interval '7 hours' and
          s.date < date_trunc('week', now()) - interval '7 hours' + interval '1 week'
    group by s.user_id;


create materialized view if not exists monthly_ranking as
    select max(s.step_id) as step_id,
           s.user_id,
           sum(s.steps) as steps
    from steps s
    where s.date >= date_trunc('month', now()) - interval '7 hours' and
          s.date < date_trunc('month', now()) - interval '7 hours' + interval '1 month'
    group by s.user_id;
