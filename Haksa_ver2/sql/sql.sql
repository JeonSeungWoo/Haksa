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


create table tbl_book(
bid number primary key,
title varchar2(100) not null,
numCheck number default 1
);

create SEQUENCE book_seq 
start with 1
increment BY 1;




create table bookrent(
no number primary key ,
id char(7) not null,
bookno number not null,
indate DATE DEFAULT sysdate,
outdate DATE, 
CONSTRAINT FK_id FOREIGN KEY(id)
REFERENCES student2(id),
CONSTRAINT FK_bookno FOREIGN KEY(bookno)
REFERENCES tbl_book(bid)
);


create SEQUENCE bookrent_seq 
start with 1
increment BY 1;

insert into bookrent(no,id,bookno) values(bookrent_seq.nextval,2018001,2);
