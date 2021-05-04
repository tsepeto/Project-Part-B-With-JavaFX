-- ALL THE QUERIES FOR sCOOL PROGRAM

-- --------------------------------GET NUMBER OF TABLES ------------------ ? = 'scool'

SELECT COUNT(DISTINCT `table_name`) AS TotalNumberOfTables FROM `information_schema`.`columns` WHERE `table_schema` = ?;

-- -----------------------------              ASSIGNMENT DAO QUERIES          --------------------------

SELECT * FROM assignments WHERE id = ?; -- FINDBYID
SELECT * FROM assignments WHERE id = (SELECT max(id) FROM assignments); -- FINDBYMAXID
SELECT * FROM assignments WHERE title = ? and description = ? and sub_dateTime = ? and oral_mark = ? and total_mark = ? ; -- EXISTS
SELECT * FROM assignments; -- FINDALL
insert into assignments (title, description, sub_dateTime, oral_mark, total_mark) values (?, ?, ?, ?, ?); -- INSERT
UPDATE assignments SET title = ?, description = ?, sub_dateTime = ?, oral_mark = ?, total_mark = ? WHERE id = ?; -- UPDATE
DELETE FROM assignments WHERE id = ?; -- DELETE
DELETE FROM courses_assignments WHERE assignment_id = ?; -- REMOVE_FROM_COURSES
DELETE FROM studentgrades WHERE assignment_id = ?; -- DELETE_STUDENT_GRADES
SELECT * FROM courses C INNER JOIN courses_assignments R ON C.id =R.course_id INNER JOIN assignments A ON R.assignment_id = A.id WHERE A.id = ?; -- GET_COURSES_FOR_ASSIGNMENT
SELECT * FROM courses C WHERE C.start_date< ? AND C.end_date > ? AND C.id NOT IN (SELECT course_id FROM courses_assignments R WHERE R.assignment_id = ?); -- GET_COURSES_NOT_IN_ASSIGNMENT
SELECT * FROM assignments A WHERE WEEK(A.sub_dateTime,1) = ?; -- GET_ASSIGNMENTS_FOR_WEEK
insert into courses_assignments (course_id, assignment_id) values (?, ?); -- ADD_COURSE
DELETE FROM courses_assignments WHERE course_id = ? and assignment_id = ?; -- REMOVE_COURSE

-- ------------------ COURSE DAO QUERIES -------------------------------------------------

SELECT * FROM courses WHERE id = ?; -- FINDBYID
SELECT * FROM courses WHERE id = (SELECT max(id) FROM courses); -- FINDBYMAXID
SELECT * FROM courses WHERE title = ? and stream = ? and type = ? and start_date = ? and end_date = ? ;  -- EXISTS
SELECT * FROM courses;  -- FINDALL
insert into courses (title, stream, type, start_date, end_date) values (?, ?, ?, ?, ?); -- INSERT
UPDATE courses SET title = ?, stream = ?, type = ?, start_date = ?, end_date = ? WHERE id = ?; -- UPDATE
DELETE FROM courses WHERE id = ?; -- DELETE
SELECT * FROM courses WHERE start_date < ? AND end_date > ?; -- GET_COURSES_ARE_ACTIVE_IN
DELETE FROM courses_students WHERE course_id = ?; -- DELETE_STUDENTS_FROM_COURSE
DELETE FROM courses_trainers WHERE course_id = ?; -- DELETE_TRAINERS_FROM_COURSE
DELETE FROM courses_assignments WHERE course_id = ?; -- DELETE_ASSIGNMENTS_FROM_COURSE
insert into courses_students (course_id, student_id) values (?, ?); -- ADD_STUDENT_IN_COURSE
DELETE FROM courses_students WHERE course_id = ? and student_id = ?; -- REMOVE_STUDENT_FROM_COURSE
insert into courses_trainers (course_id, trainer_id) values (?, ?); -- ADD_TRAINER_IN_COURSE
DELETE FROM courses_trainers WHERE course_id = ? and trainer_id = ?; -- REMOVE_TRAINER_FROM_COURSE
DELETE FROM studentgrades WHERE course_id = ?; -- DELETE_STUDENTGRADES_FROM_COURSE 
SELECT * FROM assignments C INNER JOIN courses_assignments R ON C.id =R.assignment_id INNER JOIN courses T ON R.course_id = T.id WHERE T.id = ?; -- GET_ASSIGNMENTS_FOR_COURSE
SELECT * FROM students S INNER JOIN courses_students R ON S.id =R.student_id INNER JOIN courses C ON R.course_id = C.id WHERE C.id = ?; -- GET_SUDENTS_FOR_COURSE
SELECT * FROM students S WHERE S.id NOT IN (SELECT student_id FROM  courses_students C WHERE C.course_id = ?); -- GET_SUDENTS_NOT_IN_COURSE
SELECT * FROM trainers T INNER JOIN courses_trainers R ON T.id =R.trainer_id INNER JOIN courses C ON R.course_id = C.id WHERE C.id = ?; -- GET_TRAINERS_FOR_COURSE
SELECT * FROM trainers T WHERE T.id NOT IN (SELECT trainer_id FROM  courses_trainers C WHERE C.course_id = ?); -- GET_TRAINERS_NOT_IN_COURSE 
    
-- ------------------ STUDENT DAO QUERIES -------------------------------------------------

SELECT * FROM students WHERE id = ?;		-- FINDBYID
SELECT * FROM students WHERE id = (SELECT max(id) FROM students);	-- FINDBYMAXID
SELECT * FROM Students S INNER JOIN courses_students C ON S.id = C.student_id GROUP BY S.id HAVING COUNT(*) > ?; -- GET_STUDENTS_WHEN_LESSONS_MORE_THAN
SELECT * FROM students WHERE first_name = ? and last_name = ? and birthday = ? and tuition_fees = ?;  --  STUDENTEXISTS
SELECT * FROM students; -- FINDALL
insert into students (first_name, last_name, birthday, tuition_fees) values (?, ?, ?, ?); -- INSERT
UPDATE students SET first_name = ?, last_name = ?, birthday = ?, tuition_fees = ? WHERE id = ?; -- UPDATE
DELETE FROM students WHERE id = ?; -- DELETE
DELETE FROM courses_students WHERE student_id = ?; -- REMOVE_STUDENT_FROM_COURSES
DELETE FROM studentgrades WHERE student_id = ?; -- REMOVE_STUDENTGRADES_FOR_STUDENT_WITH_ID
SELECT * FROM courses C INNER JOIN courses_students R ON C.id =R.course_id INNER JOIN students T ON R.student_id = T.id WHERE T.id = ?;  -- GET_COURSES_FOR_STUDENT

SELECT * FROM assignments A INNER JOIN courses_assignments R ON A.id =R.assignment_id INNER JOIN courses C ON R.course_id = C.id INNER JOIN courses_students X ON C.id = X.course_id 
                                                                INNER JOIN students S ON X.student_id = S.id WHERE S.id = ?;  -- GET_ASSIGNMENTS_FOR_STUDENT
                                                                
  SELECT * FROM assignments S WHERE S.id IN (SELECT assignment_id FROM  studentgrades C WHERE C.student_id = ? AND C.course_id = ?);  --  GET_RATED_ASSIGNMENTS_FROM_STUDENT_COURSE
  
select * from assignments A join courses_assignments CA on A.id = CA.assignment_id join courses C on CA.course_id = C.id join courses_students CS on C.id = CS.course_id join students S on CS.student_id = S.id where S.id = ?  and C.id = ? and A.id not in 
                                                                                (select SG.assignment_id from studentgrades SG where SG.student_id = ? and SG.course_id = ?);   --  GET_UNRATED_ASSIGNMENTS_FROM_STUDENT_COURSE
                                                                                
SELECT * FROM courses C WHERE  C.id NOT IN (SELECT course_id FROM courses_students R WHERE R.student_id = ?);  -- GET_AVALIABLE_COURSES  for student
                                                                                
-- ------------------ STUDENTGRADE DAO QUERIES -------------------------------------------------

SELECT * FROM studentgrades WHERE assignment_id = ? AND course_id = ? AND student_id = ?;		-- FINDBYID
insert into studentgrades (assignment_id, course_id, student_id, oral_mark, total_mark) values (?, ?, ?, ?, ?);		-- INSERT
SELECT * FROM studentgrades WHERE assignment_id = ? and course_id = ? and student_id = ?;	-- EXISTS
UPDATE studentgrades SET  oral_mark = ?, total_mark = ? WHERE assignment_id = ? AND course_id = ? AND student_id = ?;	-- UPDATE

-- ------------------ TRAINER DAO QUERIES -------------------------------------------------

SELECT * FROM trainers WHERE id = ?;	-- FINDBYID
SELECT * FROM trainers WHERE id = (SELECT max(id) FROM trainers);	-- FINDBYMAXID
SELECT * FROM trainers WHERE first_name = ? and last_name = ? and subject = ?;	-- EXISTS
SELECT * FROM trainers;	-- FINDALL
insert into trainers (first_name, last_name, subject) values (?, ?, ?);		-- INSERT
UPDATE trainers SET first_name = ?, last_name = ?, subject = ? WHERE id = ?;	-- UPDATE
DELETE FROM trainers WHERE id = ?;	-- DELETE
DELETE FROM courses_trainers WHERE trainer_id = ?;	-- REMOVE_TRAINER_FROM_COURSES
SELECT * FROM courses C INNER JOIN courses_trainers R ON C.id =R.course_id INNER JOIN trainers T ON R.trainer_id = T.id WHERE T.id = ?;	-- GET_COURSES_FOR_TRAINER
SELECT * FROM courses C WHERE  C.id NOT IN (SELECT course_id  FROM courses_trainers R WHERE R.trainer_id = ?);  --  GET_COURSES_AVALIABLE_FOR_TRAINERS
    