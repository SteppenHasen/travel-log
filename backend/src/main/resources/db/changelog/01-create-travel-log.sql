--liquibase formatted sql

--changeset postgres:1
DROP TABLE IF EXISTS travellogs;

CREATE TABLE travellogs (
    id varchar(50) primary key,
    traveldate date,
    registrationnumber varchar(20),
    description varchar(200),
    route varchar(50),
    owner varchar(50),
    startodometer int,
    endodometer int
);

--rollback drop table travellogs;