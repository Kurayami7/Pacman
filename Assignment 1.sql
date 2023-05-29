/*
				Assignment 1 
                  Worth 3%
                Total Assignment is out of 75 marks  
		Complete all of the questions in this SQL file and submit the file for grading
                Open this file in SQL Workbench to complete all of the statements

*/





/*
 Question 1
 
 Write a statement to Create a database (make sure your database doesn't contain special characters)  ( 5 marks )
 
*/

CREATE Database AssignmentOne;




/*
 Question 2
 
 Write a statement to Drop that Database you created in Question 1  ( 5 marks )
 
*/

DROP Database AssignmentOne;

  /*
  Question 3
  
  Write a statement to set the database you created to be the one that will be active for your session  ( 5 marks )
  
  */

USE AssignmentOne; 

  /*
  Question 4
  
  Write a statement to Create a table that will have columns that represent all of the basic datatypes that are available in the databse  (make sure your table and column names are describe the content of the table)  ( 15 marks )
  example
  CREATE TABLE book_info (
      id                 VARCHAR(32)     not null,
      filePath           long varchar     null,
      price		        float     null ,
      title                  null,
      author             long varchar     null,
    );
  */

  CREATE TABLE football (
    playerNo INT (2) PRIMARY KEY,
    playerName VARCHAR (50) NULL,
    club VARCHAR (22) NOT NULL,
    birthDate DATE NOT NULL,
    goalRatio FLOAT (3, 2) NULL,
    mood TEXT NULL
  )

/*
 Question 5
 
 Write 5 statements to add data to the table you created in question 4   ( 15 marks )  
 */
INSERT INTO football (club, playerNo, birthDate, goalRatio)
VALUES 
    ('Manchester United', 10, '1990-05-15', 0.75),
    ('Real Madrid', 7, '1992-09-02', 0.92),
    ('Barcelona', 9, '1991-11-25', 0.85),
    ('Bayern Munich', 25, '1993-07-18', 0.67),
    ('Juventus', 10, '1994-03-12', 0.78);

/*
 Question 6
 
 Write 2 statements to Create two tables that have a way to force uniqueness from row to row ( make sure your table and column names are describe the content of the table)   ( 10 marks )  
 */
CREATE TABLE employees (
  employee_id INT PRIMARY KEY,
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  email VARCHAR(100) UNIQUE
);

CREATE TABLE goods (
  item_code VARCHAR(20) PRIMARY KEY,
  product_name VARCHAR(100),
  price DECIMAL(10, 2),
  description TEXT,
  supplier_id INT,
  CONSTRAINT ca_item_code UNIQUE (item_code)
);

/*
 Question 7
 
 Write 5 statements to add to the first table you created in question 6   ( 10 marks )
 
 Write 5 statements to add to the second table you created in question 6  ( 10 marks )
  
 */

INSERT INTO employees (employee_id, first_name, last_name, email) 
VALUES (1, 'John', 'Doe', 'john.doe@example.com');

INSERT INTO goods (item_code, product_name, price, description, supplier_id)
VALUES
('1006', 'Sony PlayStation 5 Digital Edition', 399.99, 'Digital-only console, 825GB storage', 3),
('1007', 'Samsung QLED 55-inch TV', 1299.99, '4K UHD, Quantum HDR, Smart TV', 4),
('1008', 'Apple AirPods Pro', 249.99, 'Active noise cancellation, wireless charging', 5),
('1009', 'Dell XPS 13 Laptop', 1499.99, '13.3-inch FHD, Intel Core i7, 16GB RAM', 6),
('1010', 'Canon EOS R5 Mirrorless Camera', 3799.99, '45MP full-frame sensor, 8K video recording', 7);