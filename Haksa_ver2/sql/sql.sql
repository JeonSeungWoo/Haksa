create table TBL_USER(
id varchar(100) PRIMARY KEY,
pw VARCHAR2(100) not null,
name VARCHAR2(100) not null,
email varchar2(100) not null
);



create table tbl_dept(
dno number PRIMARY KEY,
dept varchar2(100) not null
);


create SEQUENCE dno_seq 
start with 1
increment BY 1;
