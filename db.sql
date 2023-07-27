use employeeDb;
show tables;
create table employee_data(
id int primary key,
name varchar(150),
gender varchar(10),
department varchar(70),
salary int
);
create table role_data(
id int primary key,
name varchar(100)
);
insert into role_data(id,name) values
(1,"Create"),
(2,"Edit"),
(3,"Delete"),
(4,"Admin"),
(5,"Default");

create table user_data(
id int primary key,
name varchar(200),
gender varchar(100),
role_name varchar(100),
user_name varchar(150),
password varchar(120)
);
select*from role_data;