CREATE TABLE hogwards.public.human
(
    id      serial primary key,
    name    varchar(15),
    age     int,
    license boolean

);

CREATE TABLE hogwards.public.car
(
    id    serial primary key,
    brand varchar(15),
    model varchar(15),
    cost  money

);

CREATE TABLE hogwards.public.registration
(
    id       serial primary key,
    car_id   integer not null references car,
    human_id integer not null references human,
    unique (car_id, human_id)
);


select student.name, student.age, faculty.name
from student
         inner join faculty on faculty.id = student.faculty_id;


select student.name, student.age, avatar.id, avatar.media_type
from student
         inner join avatar on student.id = avatar.student_id