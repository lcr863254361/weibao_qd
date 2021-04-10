--*******************************************--
--              cwm_relation_convert         --
CREATE OR REPLACE FUNCTION cwm_relation_convert(main_ta_name varchar2,
                                                 main_id      varchar2,
                                                 sub_tab_name varchar2)
   return varchar2 as
   sub_table_ids varchar2(1000);

      cursor cur_sql is
     select sub_data_id
       from cwm_relation_data
      where main_table_name = main_ta_name
        and main_data_id = main_id
        and sub_table_name in (select s_table_name
                                 from cwm_tables
                                start with s_table_name = sub_tab_name connect by prior id = pid);
      cursor cur_sql_rev is
     select main_data_id
       from cwm_relation_data
      where main_table_name = sub_tab_name
        and sub_data_id = main_id
        and sub_table_name in (select s_table_name
                                 from cwm_tables
                                start with s_table_name = main_ta_name
                               connect by prior id = pid);
    ret number;
 BEGIN
   sub_table_ids := '';
   for c in cur_sql loop
     sub_table_ids := sub_table_ids || c.sub_data_id || ',';
   end loop;
   sub_table_ids := substr(sub_table_ids, 0, length(sub_table_ids) - 1);
   IF sub_table_ids is NOT NULL
   THEN
      sub_table_ids := sub_table_ids || ',';
   END IF;
   for c in cur_sql_rev loop
   select INSTR(sub_table_ids,c.main_data_id) into ret from dual;
   IF ret IS NULL OR ret = 0
   THEN
       sub_table_ids := sub_table_ids || c.main_data_id || ',';
   END IF;
   end loop;
     sub_table_ids := substr(sub_table_ids, 0, length(sub_table_ids) - 1);
   return sub_table_ids;
END;
	
	
--*******************************************--
--             系统存储过程 SaveTabOperations         --
CREATE OR REPLACE PROCEDURE SaveTabOperations
 (v_tab_id IN cwm_sys_partoperations.TABLE_ID%TYPE,
  v_role_id IN cwm_sys_partoperations.ROLE_ID%TYPE,
  v_operations IN cwm_sys_partoperations.OPERATIONS_ID%TYPE,
  v_filter IN cwm_sys_partoperations.FILTER%TYPE,
  v_is_table IN cwm_sys_partoperations.IS_TABLE%TYPE
 )
 AS
 v_cnt number;
 No_result EXCEPTION;
 BEGIN
    SELECT count(*) into v_cnt FROM cwm_sys_partoperations WHERE table_id = v_tab_id and role_id = v_role_id and column_id = '*' and is_table = v_is_table;
    IF v_cnt=0 THEN
       insert into cwm_sys_partoperations (ID, role_id, table_id, column_id, operations_id, filter,is_table)
     values (seq_cwm_sys_partoperations.NEXTVAL,v_role_id,v_tab_id,'*',v_operations,v_filter,v_is_table);
     update cwm_sys_partoperations set OPERATIONS_ID = v_operations ,filter = v_filter where table_id = v_tab_id and is_table = v_is_table and role_id = v_role_id;
    else
        update cwm_sys_partoperations set OPERATIONS_ID = v_operations ,filter = v_filter where table_id = v_tab_id and is_table = v_is_table and role_id = v_role_id;
    END IF;
    DBMS_OUTPUT.PUT_LINE('produce success');
 EXCEPTION
    WHEN no_result THEN
       DBMS_OUTPUT.PUT_LINE('?????????!');
    WHEN OTHERS THEN
       DBMS_OUTPUT.PUT_LINE(SQLCODE||'---'||SQLERRM);
END SaveTabOperations;
	
	
--*******************************************--
--             系统方法CWM_SECRECY_CONVERT        --
CREATE OR REPLACE FUNCTION CWM_SECRECY_CONVERT(secrecy varchar2)
  RETURN NUMBER IS
BEGIN
  if (secrecy = 'C4CA4238A0B923820DCC509A6F75849B') then
    return 1;
  elsif (secrecy = 'C81E728D9D4C2F636F067F89CC14862C') then
    return 2;
  elsif (secrecy = 'ECCBC87E4B5CE2FE28308FD9F2A7BAF3') then
    return 3;
  elsif (secrecy = 'A87FF679A2F3E71D9181A67B7542122C') then
    return 4;
  elsif (secrecy = 'E4DA3B7FBBCE2345D7772B0674A318D5') then
    return 5;
  elsif (secrecy = '1679091C5A880FAF6FB5E6087EB1B2DC') then
    return 6;
  elsif (secrecy = '8F14E45FCEEA167A5A36DEDD4BEA2543') then
    return 7;
  elsif (secrecy = 'C9F0F895FB98AB9159F51FD0297E236D') then
    return 8;
  elsif (secrecy = '45C48CCE2E2D7FBDEA1AFC51C7C6AD26') then
    return 9;
  elsif (secrecy = 'D3D9446802A44259755D38E6D163E820') then
    return 10;
  else
    return 100;
  end if;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    NULL;
  WHEN OTHERS THEN
    -- Consider logging the error and then re-raise
    RAISE;
END CWM_SECRECY_CONVERT;
		