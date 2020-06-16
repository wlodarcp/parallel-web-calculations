create sequence hibernate_sequence start 1 increment 1;
create table authorities (id int8 not null, authority_type varchar(255) not null, primary key (id));
create table authors (id int8 not null, name varchar(255), surname varchar(255), primary key (id));
create table books (id int8 not null, rented boolean not null, title varchar(255), year int4 not null, rented_by_id int8, primary key (id));
create table books_authors (books_id int8 not null, authors_id int8 not null, primary key (books_id, authors_id));
create table books_tags (books_id int8 not null, tags_tag varchar(255) not null, primary key (books_id, tags_tag));
create table library_users (id int8 not null, password varchar(255), username varchar(255) not null, primary key (id));
create table tags (tag varchar(255) not null, primary key (tag));
create table user_authority (user_id int8 not null, authority_id int8 not null, primary key (user_id, authority_id));
alter table library_users add constraint library_users_unique_constraint unique (username);
alter table books add constraint books_users_fk foreign key (rented_by_id) references library_users;
alter table books_authors add constraint books_authors_fk foreign key (authors_id) references authors;
alter table books_authors add constraint authors_books_fk foreign key (books_id) references books;
alter table books_tags add constraint books_tags_fk foreign key (tags_tag) references tags;
alter table books_tags add constraint tags_books_fk foreign key (books_id) references books;
alter table user_authority add constraint user_uthority_fk foreign key (authority_id) references authorities;
alter table user_authority add constraint authority_user_fk foreign key (user_id) references library_users;

insert into authorities(id, authority_type) VALUES (1, 'ROLE_ADMIN');
insert into authorities(id, authority_type) VALUES (2, 'ROLE_USER');

/* hashed password - 'qwerty'*/
insert into library_users(id, password, username) VALUES (1, '$2a$10$ra4ON2wSfJjdpqLBRv0QGuQCZlTk1Lq6x1d3bxQBlCqm27O17lVA2', 'andrzej');
insert into library_users(id, password, username) VALUES (2, '$2a$10$ra4ON2wSfJjdpqLBRv0QGuQCZlTk1Lq6x1d3bxQBlCqm27O17lVA2', 'zbyszek');
insert into library_users(id, password, username) VALUES (3, '$2a$10$ra4ON2wSfJjdpqLBRv0QGuQCZlTk1Lq6x1d3bxQBlCqm27O17lVA2', 'karolek');

insert into user_authority(user_id, authority_id) VALUES (1,1);
insert into user_authority(user_id, authority_id) VALUES (2,1);
insert into user_authority(user_id, authority_id) VALUES (3,1);

insert into books(id, rented, title, year) VALUES (1, false, 'Harry Potter', 2012);
insert into books(id, rented, title, year) VALUES (2, false, 'Harry Potter 2', 2015);
insert into books(id, rented, title, year) VALUES (3, false, 'Rambo', 2014);
insert into books(id, rented, title, year) VALUES (4, false, 'Calineczka', 1992);

insert into authors(id, name, surname) VALUES (1, 'Andrzej', 'Sapkowski');
insert into authors(id, name, surname) VALUES (2, 'Wioletta', 'Willas');
insert into authors(id, name, surname) VALUES (3, 'Dagmara', 'Popiołek');
insert into authors(id, name, surname) VALUES (4, 'Ziemowit', 'Tracz');

insert into books_authors(books_id, authors_id) VALUES (1, 1);
insert into books_authors(books_id, authors_id) VALUES (2, 1);
insert into books_authors(books_id, authors_id) VALUES (3, 2);
insert into books_authors(books_id, authors_id) VALUES (4, 3);

insert into tags(tag) values ('fantastyka');
insert into tags(tag) values ('horror');
insert into tags(tag) values ('groza');
insert into tags(tag) values ('komedia');
insert into tags(tag) values ('sensacyjne');
insert into tags(tag) values ('naukowe');

insert into books_tags(books_id, tags_tag) VALUES (1, 'fantastyka');
insert into books_tags(books_id, tags_tag) VALUES (1, 'horror');
insert into books_tags(books_id, tags_tag) VALUES (2, 'fantastyka');
insert into books_tags(books_id, tags_tag) VALUES (3, 'fantastyka');
insert into books_tags(books_id, tags_tag) VALUES (4, 'fantastyka');
insert into books_tags(books_id, tags_tag) VALUES (4, 'groza');
insert into books_tags(books_id, tags_tag) VALUES (4, 'sensacyjne');
