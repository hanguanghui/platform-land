prompt PL/SQL Developer import file
prompt Created on 2017Äê5ÔÂ1ÈÕ by jimboLix
set feedback off
set define off
prompt Creating T_ROLE_MENU...
create table T_ROLE_MENU
(
  role_id NVARCHAR2(50),
  menu_id NVARCHAR2(50)
)
;

prompt Creating T_USER_ROLE...
create table T_USER_ROLE
(
  user_id NVARCHAR2(50),
  role_id NVARCHAR2(50)
)
;

prompt Disabling triggers for T_ROLE_MENU...
alter table T_ROLE_MENU disable all triggers;
prompt Disabling triggers for T_USER_ROLE...
alter table T_USER_ROLE disable all triggers;
prompt Loading T_ROLE_MENU...
insert into T_ROLE_MENU (role_id, menu_id)
values ('2', 'c2nor8vn00bnulls71bny90x9xwpfdt');
insert into T_ROLE_MENU (role_id, menu_id)
values ('2', 'c2nor8vn00bnulls71bny90x9xwpfdt');
insert into T_ROLE_MENU (role_id, menu_id)
values ('2', 'jsjkaskjkasdjsdjsdjkfjkskldiosdj');
insert into T_ROLE_MENU (role_id, menu_id)
values ('10', 'jjksalsadfjnsdjkjksfbnnsdfmnjnjj');
insert into T_ROLE_MENU (role_id, menu_id)
values ('10', 'jjksalsadfjnsdjkjksfbnnsdfmnjnjd');
insert into T_ROLE_MENU (role_id, menu_id)
values ('7', 'jjksalsadfjnsdjkjksfbnnsdfmnjnjj');
insert into T_ROLE_MENU (role_id, menu_id)
values ('7', 'anlqvibxzrroq55qdqfvakdscbhnqu36');
commit;
prompt 7 records loaded
prompt Loading T_USER_ROLE...
insert into T_USER_ROLE (user_id, role_id)
values ('mits011il4nyxzaykzv6xj068vwuumjb', '10');
insert into T_USER_ROLE (user_id, role_id)
values ('mits011il4nyxzaykzv6xj068vwuumjb', '7');
commit;
prompt 2 records loaded
prompt Enabling triggers for T_ROLE_MENU...
alter table T_ROLE_MENU enable all triggers;
prompt Enabling triggers for T_USER_ROLE...
alter table T_USER_ROLE enable all triggers;
set feedback on
set define on
prompt Done.
