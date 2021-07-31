
-- drop sql are only for testing phase
drop table if exists T_ROLE_PERMISSION;
drop table if exists T_USER_ROLE;
drop table if exists T_ROLE;
drop table if exists T_USER;
drop table if exists T_PERMISSION;

create table if not exists T_USER (
  ID bigint(10) unsigned not null auto_increment,
  USERNAME varchar(20) not null,
  PASSWORD varchar(255) not null,
  primary key (ID)
);

create table if not exists T_ROLE (
  ID bigint(10) unsigned not null auto_increment,
  NAME varchar(20) not null,
  primary key (ID)
);

create table if not exists T_USER_ROLE (
  USER_ID bigint(10) unsigned not null,
  ROLE_ID bigint(10) unsigned not null,
  primary key (USER_ID, ROLE_ID),
  foreign key (USER_ID) references T_USER(ID),
  foreign key (ROLE_ID) references T_ROLE(ID)
);

create table if not exists T_PERMISSION (
  ID bigint(10) unsigned not null auto_increment,
  NAME varchar(20) not null,
  primary key (ID)
);

create table if not exists T_ROLE_PERMISSION (
  ROLE_ID bigint(10) unsigned not null,
  PERMISSION_ID bigint(10) unsigned not null,
  primary key (ROLE_ID, PERMISSION_ID),
  foreign key (ROLE_ID) references T_ROLE(ID),
  foreign key (PERMISSION_ID) references T_PERMISSION(ID)
);