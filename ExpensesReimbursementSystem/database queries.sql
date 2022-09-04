--Revature Project: Database for Expensenses Reimbursement System
--Programmer: Abderrahim Bahia

--Create database
CREATE database expensesReimbursementSystemDB;

--Create Employee table if not exist
create table if not exists Employees
(
	emplyeeID  serial      primary key,
	firstName  varchar(30) not null,
	lastName   varchar(30) not null,
	username   varchar(30) not null,
	password   varchar(30) not null,
	email      varchar(30) not null,
	phone      varchar(10) default null,
	position   varchar(30) default 'employee', 
	street     varchar(50) default null,
	city	   varchar(15) default null,
	state 	   varchar(2)  default null,
	zipcode	   varchar(5)  default null
);

--add a manager id column
--references employee id in same table
alter table employees 
add column managerID integer default null references employees(employeeID);

-- Create Tickets table if not exist
create table if not exists Tickets
(
	ticketID    serial 	   	  primary key,
	status      varchar(30)   default 'pending',
	type 	    varchar(30)   default null,
	amount 	    money 		  default 0.00,
	description text		  default null
);

--add employee id column to tickets table
--the column references the employee id in employees table
alter table tickets  
add column employeeID2 integer default null references employees(employeeID);


--add a picture column for profile picture to employees table
alter table employees 
add column picture bytea;

----add a picture column for receipt picture to tickets table
alter table tickets 
add column picture bytea;


