create table user
(
    id              bigint auto_increment
        primary key,
    created_date    datetime(6)   null,
    modified_date   datetime(6)   null,
    active          bit           not null,
    birth_date      datetime(6)   null,
    cleaning_policy varchar(255)  null,
    description     varchar(1000) null,
    email           varchar(255)  null,
    first_name      varchar(255)  null,
    gender          varchar(255)  null,
    guests_policy   varchar(255)  null,
    last_name       varchar(255)  null,
    password        varchar(255)  null,
    pet_policy      varchar(255)  null,
    smoking_policy  varchar(255)  null,
    studying_at     varchar(255)  null,
    working_in      varchar(255)  null
);

INSERT INTO dorm.user (id, created_date, modified_date, active, birth_date, cleaning_policy, description, email, first_name, gender, guests_policy, last_name, password, pet_policy, smoking_policy, studying_at, working_in) VALUES (1, '2020-01-27 14:04:23.557000', '2020-02-07 09:05:39.075000', false, '2000-01-25 23:00:00.000000', 'Raz w tygodniu', 'Do Łodzi przyjechałam z nowej wsi, żeby studiować na politechnice łódzkiej chemie. Teraz jestem studentka drugiego roku. Właśnie się przeprowadziłam z akademika do mieszkania przy ulicy piotrkowskiej która jest po prostu śliczna! W wolnym czasie lubię gotować oraz czytać książki :)', 'fiona@gmail.com', 'Clara', 'MALE', 'Nie ma problemu!', 'Siemanko', '111', 'Tylko nie to', 'Tak ale nigdy w domu', 'Politechnika Łódzka', 'McDonald');