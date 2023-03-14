insert into course(id, name)
values (10, 'Spring'), (20, 'Sprintboot in 100 Steps'), (30, 'AWS');

insert into passport(id, number)
values (201, 'N1234'),
       (202, 'L1234'),
       (204, 'K1234');

insert into student(id, name, passport_id)
values (301, 'meenal', 201),
       (310, 'leo', 202),
       (305, 'john', 204);

insert into review(id, rating, description, course_id)
values (801, '5', 'Great Course', 10),
       (810, '4', 'Awesome Course', 10),
       (811, '5', 'Nice Content', 20);

insert into student_course(student_id, course_id)
values (301, 10),
       (301, 20),
       (305, 30);























