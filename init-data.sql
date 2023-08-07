insert into users (user_id, name, email, password, version, deleted, created_at, created_by, updated_at, updated_by) values
    (242982880122896384, 'Alice', 'alice@gmail.com', 'c4318372f98f4c46ed3a32c16ee4d7a76c832886d887631c0294b3314f34edf1', 1, false, CURRENT_TIMESTAMP, 'script', CURRENT_TIMESTAMP, 'script'),
    (242982880143867904, 'Bob', 'bob@gmail.com', 'c4318372f98f4c46ed3a32c16ee4d7a76c832886d887631c0294b3314f34edf1', 1, false, CURRENT_TIMESTAMP, 'script', CURRENT_TIMESTAMP, 'script'),
    (242982880143867905, 'Toan', 'work@toannv.dev', 'c4318372f98f4c46ed3a32c16ee4d7a76c832886d887631c0294b3314f34edf1', 1, false, CURRENT_TIMESTAMP, 'script', CURRENT_TIMESTAMP, 'script');

insert into steps (step_id, user_id, date, steps, version, deleted, created_at, created_by, updated_at, updated_by) values
    (243044708259594240, 242982880143867905, '2023-08-02', 30000, 1, false, CURRENT_TIMESTAMP, 'script', CURRENT_TIMESTAMP, 'script'),
    (243044708259594241, 242982880143867904, '2023-08-02', 1345, 1, false, CURRENT_TIMESTAMP, 'script', CURRENT_TIMESTAMP, 'script'),
    (243044708259594242, 242982880122896384, '2023-08-02', 30000, 1, false, CURRENT_TIMESTAMP, 'script', CURRENT_TIMESTAMP, 'script'),
    (243044708259594243, 242982880143867905, CURRENT_DATE, 965, 1, false, CURRENT_TIMESTAMP, 'script', CURRENT_TIMESTAMP, 'script'),
    (243044708259594244, 242982880143867904, CURRENT_DATE, 231, 1, false, CURRENT_TIMESTAMP, 'script', CURRENT_TIMESTAMP, 'script'),
    (243044708259594245, 242982880122896384, CURRENT_DATE, 746, 1, false, CURRENT_TIMESTAMP, 'script', CURRENT_TIMESTAMP, 'script');

insert into step_archives (step_archive_id, step_id, user_id, date, steps, version, deleted, created_at, created_by, updated_at, updated_by) values
    (243053833276489728, 243044708259594240, 242982880143867905, '2023-08-02', 30000, 1, false, CURRENT_TIMESTAMP, 'script', CURRENT_TIMESTAMP, 'script'),
    (243315401783246848, 243044708259594241, 242982880143867904, '2023-08-02', 1345, 1, false, CURRENT_TIMESTAMP, 'script', CURRENT_TIMESTAMP, 'script'),
    (243493441510440960, 243044708259594242, 242982880122896384, '2023-08-02', 30000, 1, false, CURRENT_TIMESTAMP, 'script', CURRENT_TIMESTAMP, 'script'),
    (243053833276489761, 243044708259594243, 242982880143867905, CURRENT_DATE, 965, 1, false, CURRENT_TIMESTAMP, 'script', CURRENT_TIMESTAMP, 'script'),
    (243315401783246862, 243044708259594244, 242982880143867904, CURRENT_DATE, 231, 1, false, CURRENT_TIMESTAMP, 'script', CURRENT_TIMESTAMP, 'script'),
    (243493441510440963, 243044708259594245, 242982880122896384, CURRENT_DATE, 746, 1, false, CURRENT_TIMESTAMP, 'script', CURRENT_TIMESTAMP, 'script');
