-- Use the database --
USE scool;

-- Create Students -- 
insert into students (id,first_name, last_name, birthday, tuition_fees) values 
(1,'Nikos', 'Tsepetzidis', '1989-09-19',2250 ),(2,'Christos', 'Kotsou', '1988-03-15', 2500),			-- id 1 , 2 
(3,'Petros', 'Tasou', '1992-10-14',2250 ),(4,'Giorgos', 'Argiriou', '1988-03-15', 2500),				-- id 3 , 4 
(5,'Eirini', 'Anagnostou', '1989-12-15',2000 ),(6,'Eleni', 'Xatzi', '1999-03-03',2500 ),				-- id 5 , 6 	
(7,'Grigoris', 'Simaioforidis', '1985-01-27',2000 ),(8,'Christos', 'Bochoris', '1983-07-21', 2500),		-- id 7 , 8 
(9,'Christos', 'Kontosis', '1987-03-05',2250 ),(10,'Maria', 'Likoudi', '1991-07-21',2500 ),				-- id 9 , 10 
(11,'Dimitra', 'Tsepetzidou', '1983-06-18',2250 ),(12,'Eirini', 'Adamopoulou', '2006-12-24',2250 ),		-- id 11 , 12 
(13,'Kiriaki', 'Adamopoulou', '2006-12-24',2250 ),(14,'Dimitris', 'Novas', '1978-08-22',2500 ),			-- id 13 , 14 
(15,'Stelios', 'Valavanis', '1982-09-07',2250 ),(16,'Vasilis', 'Prekas', '1983-12-29',2500 ),			-- id 15 , 16 
(17,'Mixalis', 'Xasapis', '1972-06-15',2250 ),(18,'Nikos', 'Tsempoulatis', '1991-07-05', 2000),			-- id 17 , 18 
(19,'Savvas', 'Grigoriadis', '1983-04-23',2250 ),(20,'Mixalis', 'Tsepetzidis', '1955-11-22', 2000),		-- id 19 , 20 
(21,'Manolis', 'Tsepetzidis', '1992-09-08',2000),(22,'Eirini', 'Mavrogeorgi', '1955-11-22', 2000),		-- id 21 , 22 
(23,'Thanasis', 'Vavatsikos', '1993-06-03',2500 ),(24,'Vicky', 'Spanopoulou', '1989-06-03',2250 );		-- id 23 , 24 

-- Create Trainers --
insert into trainers (id,first_name, last_name, subject) values 
(1,'Giorgos', 'Iraklidis', 'Java, DevOps'),				-- id 1 
(2,'Giannis','Mavros','Java') ,							-- id 2 
(3,'Tasos','Lelakis','Java, JDBC'),						-- id 3 	
(4,'Giorgos','Rigopoulos','Java, MySql') ,				-- id 4 
(5,'Konstantinos','Takakis','Html, Css, JavaScript');	-- id 5 

-- Create Courses -- 
insert into courses (id, title, stream, type, start_date, end_date) values 
(1,'CB12', 'Java', 'Morning Lessons', '2020-10-15','2021-03-15'),        	-- id 1
(2,'CB12', 'Python', 'Morning Lessons', '2020-11-01','2021-03-10'),			-- id 2
(3,'CB13', 'C#', 'Evening Lessons', '2021-02-15','2021-09-15'),				-- id 3
(4,'CB13', 'Python', 'Evening Lessons', '2021-03-01','2021-09-15'),			-- id 3
(5,'CB13', 'JavaScript', 'Evening Lessons', '2020-10-01','2021-04-15'),		-- id 4
(6,'CB13', 'Java', 'Evening Lessons', '2021-02-15','2021-09-10');			-- id 5

-- Create Assignments -- 
insert into assignments (id,title, description, sub_dateTime, oral_mark, total_mark) values 
(1,'Indivigual Project Part A', 'School managment programm without database', '2021-03-30',50 , 100),	-- id 1 
(2,'Indivigual Project Part B', 'School managment programm with database', '2021-05-10',20 ,100 ),		-- id 2 
(3,'Indivigual Project Part A', 'Cinema managment programm without database', '2020-11-30',50 , 100),	-- id 3
(4,'Indivigual Project Part B', 'Cinema managment programm with database', '2021-02-15',20 ,100 ),		-- id 4 
(5,'Indivigual Project Part A', 'Library managment programm without database', '2020-12-15',50 ,100 ),	-- id 5 
(6,'Indivigual Project Part B', 'Library managment programm with database', '2021-02-02',20 ,100 );		-- id 6 

-- Add Students to Courses --
insert into courses_students (course_id, student_id) values 
( 1, 17) , (1 , 18),
( 1, 22) , ( 1, 12),
(1 , 7) , 

( 2, 1) , ( 2, 2),
( 2, 7) , ( 2, 3),
( 2, 11) , 

(3 , 5) , ( 3, 8),
(3 , 6) , ( 3, 16),
(3 , 14) , ( 3, 20),

( 4, 20) , ( 4, 19),
( 4, 13) , ( 4, 4),
( 4, 17) , 

(5 , 17) , (5 , 18),
(5 , 13) , (5 , 3),
(5 , 22) , 

(6 , 1) , (6 , 2),
(6 , 9) , (6 , 10),
(6 , 23) , (6 , 15) , (6 , 24);


-- Add Trainers to Courses --
insert into courses_trainers (course_id, trainer_id) values 
( 1, 1),( 1, 3),

( 2, 4),( 2, 3),

( 3, 2),( 3, 4),

( 4, 5),( 4, 4),( 4, 3),

( 5, 1),( 5, 5),

( 6, 2),( 6, 3);

-- Add Assignments to Courses --
insert into courses_assignments (course_id, assignment_id) values 
(3, 1),(4, 1),(6, 1),

(3, 2),(4, 2),(6, 2),

(1, 3),(2, 3),

(1, 4),(2, 4),

(5, 5),

(5, 6);


-- Add Grades to Students --
insert into studentgrades (assignment_id, course_id, student_id, oral_mark, total_mark) values 
(3, 1, 17, 45, 80) , (4, 1, 17, 19, 90),
(3, 1, 18, 46, 85) , (4, 1, 18, 20, 90),
(3, 1, 22, 50, 95) , (4, 1, 22, 20, 99),
(3, 1, 12, 49, 93) , (4, 1, 12, 19, 95),
(3, 1, 7, 50, 96 ), (4, 1, 7, 15, 95),

(3, 2, 1, 50, 100) , (4, 2, 1, 20, 100),
(3, 2, 2, 46, 85) , (4, 2, 2, 20, 90),
(3, 2, 7, 50, 95) , (4, 2, 7, 20, 99),
(3, 2, 3, 49, 93) , (4, 2, 3, 15, 95),
(3, 2, 11, 50, 96) , (4, 2, 11, 15, 95),

(1, 6, 1, 50, 100) , (1, 6, 9, 50, 100),
(1, 6, 2, 45, 90) , (1, 6, 10, 49, 96),
(1, 6, 23, 50, 100) , (1, 6, 15, 40, 85),
(1, 6, 24, 49, 99) , 

(1, 4, 20, 50, 100) , (1, 4, 19, 45, 90),
(1, 4, 13, 45, 95) , (1, 4, 17, 40, 90),

(1, 3, 20, 50, 100) , (1, 3, 6, 40, 80),
(1, 3, 5, 48, 98) , (1, 3, 16, 45, 95),

(5, 5, 17, 48, 98) , (6, 5, 17, 20, 90),
(5, 5, 18, 40, 88) , (6, 5, 18, 20, 90),
(5, 5, 13, 47, 95) , (6, 5, 13, 16, 96),
(5, 5, 3, 35, 81) , (6, 5, 3, 15, 88),
(5, 5, 22, 50, 100) , (6, 5, 22, 20, 100);

