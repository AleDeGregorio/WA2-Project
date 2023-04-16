create table if not exists products(
    ean varchar(15) primary key,
    name varchar(255),
    brand varchar(255)
);

create table if not exists users(
 email varchar(15) primary key,
 username varchar(255),
 age int
)