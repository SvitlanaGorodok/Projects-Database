create schema if not exists public;

create database hw3 with owner postgres;

comment on schema public is 'standard public schema';

alter schema public owner to postgres;

create table developers
(
	id INT primary key,
	first_name VARCHAR(30) not null,
	last_name VARCHAR(30) not null,
	age INT
);

alter table developers owner to postgres;

create table skills
(
	id INT primary key,
	area VARCHAR(30) not null,
	level VARCHAR(30) not null
);

alter table skills owner to postgres;

create table projects
(
	id INT primary key,
	name VARCHAR(30) not null,
	description VARCHAR(100) not null
);

alter table projects owner to postgres;

create table companies
(
	id INT primary key,
	name VARCHAR(30) not null,
	description VARCHAR(100) not null
);

alter table companies owner to postgres;

create table customers
(
	id INT primary key,
	name VARCHAR(30) not null,
	description VARCHAR(100) not null
);

alter table customers owner to postgres;


create table developers_skills (
    developer_id BIGINT not null,
    skill_id BIGINT not null,
    primary key (developer_id, skill_id),
    foreign key (developer_id) references developers(id),
    foreign key (skill_id) references skills(id)
);

alter table developers_skills owner to postgres;

create table developers_projects (
    developer_id BIGINT not null,
    project_id BIGINT not null,
    primary key (developer_id, project_id),
    foreign key (developer_id) REFERENCES developers(id),
    foreign key (project_id) REFERENCES projects(id)
);

alter table developers_projects owner to postgres;

create table companies_projects (
    company_id BIGINT not null,
    project_id BIGINT not null,
    primary key (company_id, project_id),
    foreign key (company_id) references companies(id),
    foreign key (project_id) references projects(id)
);

alter table companies_projects owner to postgres;

create table customers_projects (
    customer_id BIGINT not null,
    project_id BIGINT not null,
    primary key (customer_id, project_id),
    foreign key (customer_id) references customers(id),
    foreign key (project_id) references projects(id)
);

alter table customers_projects owner to postgres;

