CREATE TABLE IF NOT EXISTS teacher (
    id INT AUTO_INCREMENT PRIMARY KEY,
    teacher_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS student (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(255),
    phone_number VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS course (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS course_teacher (
    course_id INT NOT NULL,
    teacher_id INT NOT NULL,
    PRIMARY KEY (course_id, teacher_id)
);

CREATE TABLE IF NOT EXISTS course_student_teacher (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT NOT NULL,
    student_id INT NOT NULL,
    teacher_id INT NOT NULL
);
