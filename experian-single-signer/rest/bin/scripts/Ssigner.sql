set serveroutput on size UNLIMITED
set define off
set pagesize 50000
set linesize 350

--==============================================================================
-- Cabecalho Serasa
-- set date on

spool script_pcmXXX_ip_control.sql.log

select * from global_name;

select to_char(sysdate,'dd/mm/yyyy hh24:mi') "Inicio da Execucao" from dual;
--==============================================================================

SET ECHO ON
SET VERIFY ON

ALTER SESSION SET CURRENT_SCHEMA=PROD;
--
WHENEVER SQLERROR EXIT SQL.SQLCODE
--
exec pkg_log.prc_header_commit ('PCM_XXX','Entrega pcmXXX','V_XX_XX_XX');
--
WHENEVER SQLERROR CONTINUE
--
exec pkg_log.prc_insere_controle_min ('PCM_XXX','Entrega pcmXXX','V_XX_XX_XX');

prompt ******** CRIANDO A TABELA PROD.LOG_MENSAGEM

declare
  vtable_exist integer;
begin
/*
***********************************
* CRIA A TABELA PROD.LOG_MENSAGEM *
***********************************
*/
  select count(1) vtable_exist
    into vtable_exist
    from ALL_TABLES
   where owner = 'PROD'
     and upper(table_name) like 'LOG_MENSAGEM';
  if vtable_exist = 0 then
    execute immediate('create table PROD.LOG_MENSAGEM (
							   ID_MENSAGEM NUMBER(15,0)    	   NOT NULL ,
							   DT_MENSAGEM TIMESTAMP(6)        NOT NULL ,
							   TIPO        VARCHAR2(5    CHAR) NOT NULL ,
							   FUNCAO 	   VARCHAR2(50   CHAR) NOT NULL ,
							   CLIENTE 	   VARCHAR2(100  CHAR) NOT NULL ,
							   MENSAGEM	   VARCHAR2(4000 CHAR) NOT NULL ,
  
                       CONSTRAINT PK_ID_LOG_MENSAGEM PRIMARY KEY ( ID_MENSAGEM ) ) ');
					   
    execute immediate(' ALTER TABLE LOG_MENSAGEM ADD CONSTRAINT CONST_TIPO_MENSAGEM CHECK ( TIPO in ( ''R'', ''D'' ) ) ENABLE ');
									
	execute immediate('COMMENT ON COLUMN PROD.LOG_MENSAGEM.ID_MENSAGEM IS ''ID de identificação da mensagem recebida.'' ');
	execute immediate('COMMENT ON COLUMN PROD.LOG_MENSAGEM.DT_MENSAGEM IS ''Data que a mensagem foi recebida / respondida.'' ');
	execute immediate('COMMENT ON COLUMN PROD.LOG_MENSAGEM.TIPO IS ''Tipo da mensagem R -> Recebida, D -> Devolvida.'' ');
	execute immediate('COMMENT ON COLUMN PROD.LOG_MENSAGEM.FUNCAO IS ''A função que recebeu/enviou a mensagem.'' ');
	execute immediate('COMMENT ON COLUMN PROD.LOG_MENSAGEM.CLIENTE IS ''Identificado do cliente que consumiu o serviço de assinatura.'' ');
	execute immediate('COMMENT ON COLUMN PROD.LOG_MENSAGEM.MENSAGEM IS ''Arquivo recebido/enviado na release 3 será refatorado para clob.'' ');
	
	dbms_output.put_line('Tabela "LOG_MENSAGEM": Criada com sucesso!');
	
  else
    dbms_output.put_line('Tabela "LOG_MENSAGEM": já existe!');
  end if;
  
  commit;
exception
  when others then
  dbms_output.put_line('Erro na criação da entidade IDENTIFICACAO_MOBILE:'||sqlerrm);
  rollback;
end;
/

prompt ******** CRIANDO A SEQUENCE ID_LOG_MENSAGEM_SEQ

declare
  vtable_exist integer;
begin
/*
********************************************
* CRIA A SEQUENCE ID_LOG_MENSAGEM_SEQ *
********************************************
*/
  select count(1) vtable_exist
    into vtable_exist
    from ALL_OBJECTS
   where owner = 'PROD'
     and upper(OBJECT_NAME) like 'ID_LOG_MENSAGEM_SEQ';
  if vtable_exist = 0 then
    execute immediate('CREATE SEQUENCE ID_LOG_MENSAGEM_SEQ
						 START WITH     1
						 INCREMENT BY   1
						 NOCACHE
						 NOCYCLE ');
										
	dbms_output.put_line('SEQUENCE "ID_LOG_MENSAGEM_SEQ": Criada com sucesso!');
	
  else
    dbms_output.put_line('SEQUENCE "ID_LOG_MENSAGEM_SEQ": já existe!');
  end if;
  
  commit;
exception
  when others then
  dbms_output.put_line('Erro na criação da SEQUENCE "ID_LOG_MENSAGEM_SEQ": '||sqlerrm);
  rollback;
end;
/

prompt ********CRIANDO A FUNCTION PROD.SPC_LOG_MENSAGEM

/*
*****************************************
* CRIA A FUNCTION PROD.SPC_LOG_MENSAGEM *
*****************************************
*/

create or replace function SPC_LOG_MENSAGEM ( p_identificador varchar2 default null,
										      p_tipo          varchar2 default null,
											  p_funcao		  varchar2 default null,
                                              p_cliente		  varchar2 default null,
											  p_dt_msg_ini    varchar2 default null,
											  p_dt_msg_final  varchar2 default null )

return util.refcursor
  is
	r util.refcursor;
	v_dt_msg_ini        varchar2(2000);
	v_dt_msg_final      varchar2(2000);
	v_sql				varchar2(2000);
begin
	
	if ( TRIM( p_identificador ) IS NULL AND
		 TRIM( p_tipo ) IS NULL AND
		 TRIM( p_funcao ) IS NULL AND
		 TRIM( p_cliente ) IS NULL AND
		 TRIM( p_dt_msg_ini ) IS NULL AND 
		 TRIM( p_dt_msg_final ) IS NULL ) then
		 
		RAISE_APPLICATION_ERROR(-20001,'Pelo menos um dos parametros deve ser informado');
	end if;
	
	v_sql := ' SELECT ID_MENSAGEM, DT_MENSAGEM, TIPO, FUNCAO, CLIENTE, MENSAGEM
			     FROM LOG_MENSAGEM
                WHERE 1 = 1 ';
			   
	if ( TRIM( p_identificador ) IS NOT NULL ) then
		v_sql := v_sql || ' AND ID_MENSAGEM = ' || p_identificador;
	end if;
	
	if ( TRIM( p_tipo ) IS NOT NULL ) then
		v_sql := v_sql || ' AND TIPO = ''' || p_tipo || '''';
	end if;
	
	if ( TRIM( p_funcao ) IS NOT NULL ) then
		v_sql := v_sql || ' AND FUNCAO = ''' || p_funcao || '''';
	end if;
		
	if ( TRIM( p_cliente ) IS NOT NULL ) then
		v_sql := v_sql || ' AND CLIENTE = ''' || p_cliente || '''';
	end if;
	
	if ( TRIM( p_dt_msg_ini ) IS NOT NULL ) then
		v_dt_msg_ini := TO_TIMESTAMP( p_dt_msg_ini, 'DD/MM/YYYY HH24:MI:SS');
		v_sql := v_sql || ' AND DT_MENSAGEM >= ''' || v_dt_msg_ini || '''';
	end if;
		
	if ( TRIM( p_dt_msg_final ) IS NOT NULL ) then
		v_dt_msg_final := TO_TIMESTAMP( p_dt_msg_final, 'DD/MM/YYYY HH24:MI:SS');
		v_sql := v_sql || ' AND DT_MENSAGEM <= ''' || v_dt_msg_final || '''';
	end if;
		
	open r for v_sql;
  return r;
  
exception
  when no_data_found then
       open r for select 1 from dual where 1=2;
       return r;

end;
/

/*
************************************
* CRIA A FUNCTION SPI_LOG_MENSAGEM *
************************************
*/

prompt ******** CRIANDO A FUNCTION SPI_LOG_MENSAGEM

create or replace function SPI_LOG_MENSAGEM (
								p_tipo 	    varchar2,
								p_funcao 	varchar2,
								p_cliente   varchar2,
								p_mensagem	varchar2 )
return varchar2
is
  -- -------------------------------------------------------------------------------------------
  --    Function....: SPI_LOG_MENSAGEM
  --    Assunto.....: Insere dados na IDENTIFICACAO_MOBILE
  --    Data Criacao: 30/11/2017 - Aimbere Galdino
  --    Revisada por: 00/00/0000 - 
  --    Manutencoes.: 00/00/0000 - 
  -- -------------------------------------------------------------------------------------------
  v_identificador NUMBER(15,0);
  v_tipo 	      LOG_MENSAGEM.tipo%type;
  v_funcao        LOG_MENSAGEM.funcao%type;
  v_cliente       LOG_MENSAGEM.cliente%type;
  v_mensagem      LOG_MENSAGEM.mensagem%type; 
   
begin

	v_tipo      :=  SUBSTR( nvl( p_tipo, ''), 0, 5);
	v_funcao    :=  SUBSTR( nvl( p_funcao, ''), 0, 50);
	v_cliente   :=  SUBSTR( nvl( p_cliente, ''), 0, 100);
	v_mensagem	:=  SUBSTR( nvl( p_mensagem, ''), 0, 4000);

	/*
	*	Validacao de obrigatoriedade dos parametros
	*/
	
	if ( TRIM( v_tipo ) IS NULL ) then
		RAISE_APPLICATION_ERROR( -20001,'O tipo deve ser informado');
    end if;
	
	if ( TRIM( v_funcao ) IS NULL ) then
		RAISE_APPLICATION_ERROR( -20002,'A função executada deve ser informada');
    end if;
	
	if ( TRIM( v_cliente ) IS NULL ) then
		RAISE_APPLICATION_ERROR( -20003,'O cliente deve ser informado');
    end if;

	if ( TRIM( v_mensagem ) IS NULL ) then
		RAISE_APPLICATION_ERROR( -20003,'A mensagem deve ser informada');
    end if;
	
	select ID_LOG_MENSAGEM_SEQ.nextval into v_identificador from dual;
  
	INSERT INTO LOG_MENSAGEM ( ID_MENSAGEM,     DT_MENSAGEM,  TIPO,   FUNCAO,   CLIENTE,   MENSAGEM )
					  VALUES ( V_IDENTIFICADOR, SYSTIMESTAMP, v_tipo, v_funcao, v_cliente, v_mensagem );
	COMMIT;
      
	RETURN TO_CHAR(V_IDENTIFICADOR,'FM999999999999999');
end;
/

--==============================================================================
--
--                    RECOMPILACAO AUTOMATICA DE OBJETOS
--
--==============================================================================
exec prc_recompile_invalid_objects
exec prc_recompile_invalid_objects
exec prc_recompile_invalid_objects

--==============================================================================
set echo off verify off

select to_char(sysdate,'dd/mm/yyyy hh24:mi') "Fim da Execucao" from dual;

spool off
--==============================================================================