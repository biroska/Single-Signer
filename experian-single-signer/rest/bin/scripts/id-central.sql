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

prompt ******** CRIANDO A TABELA PROD.IDENTIFICACAO_MOBILE

declare
  vtable_exist integer;
begin
/*
*******************************************
* CRIA A TABELA PROD.IDENTIFICACAO_MOBILE *
*******************************************
*/
  select count(1) vtable_exist
    into vtable_exist
    from ALL_TABLES
   where owner = 'PROD'
     and upper(table_name) like 'IDENTIFICACAO_MOBILE';
  if vtable_exist = 0 then
    execute immediate('create table PROD.IDENTIFICACAO_MOBILE (
							   ID_IDENTIFICACAO_MOBILE  NUMBER(15,0)  	    not null ,
							   HASH_PUSH 			    VARCHAR2(4000 CHAR) not null,
							   DOCUMENTO 			    VARCHAR2(14 CHAR)   not null ,
							   NO_NOME                  VARCHAR2(150 CHAR)  not null ,
							   EM_EMAIL					VARCHAR2(60 CHAR) ,
  
                       CONSTRAINT PK_ID_IDENTIFICACAO_MOBILE PRIMARY KEY ( ID_IDENTIFICACAO_MOBILE ) ) ');
									
	execute immediate('COMMENT ON COLUMN PROD.IDENTIFICACAO_MOBILE.ID_IDENTIFICACAO_MOBILE IS ''ID de identificação do cliente mobile que instalou o certificado do mobile ID.'' ');
	execute immediate('COMMENT ON COLUMN PROD.IDENTIFICACAO_MOBILE.HASH_PUSH IS ''Hash de identificação para enviar a notificação push para o dispositivo móvel.'' ');
	execute immediate('COMMENT ON COLUMN PROD.IDENTIFICACAO_MOBILE.DOCUMENTO IS ''Documento de identificação do cliente que instalou o certificado no dispotivo móvel.'' ');
	execute immediate('COMMENT ON COLUMN PROD.IDENTIFICACAO_MOBILE.NO_NOME IS ''Nome do cliente que instalou o certificado no dispotivo móvel.'' ');
	execute immediate('COMMENT ON COLUMN PROD.IDENTIFICACAO_MOBILE.EM_EMAIL IS ''E-mail do cliente que instalou o certificado no dispotivo móvel.'' ');
	
	dbms_output.put_line('Tabela "IDENTIFICACAO_MOBILE": Criada com sucesso!');
	
  else
    dbms_output.put_line('Tabela "IDENTIFICACAO_MOBILE": já existe!');
  end if;
  
  commit;
exception
  when others then
  dbms_output.put_line('Erro na criação da entidade IDENTIFICACAO_MOBILE:'||sqlerrm);
  rollback;
end;
/

prompt ******** CRIANDO A SEQUENCE IDENTIFICACAO_MOBILE_SEQ

declare
  vtable_exist integer;
begin
/*
********************************************
* CRIA A SEQUENCE IDENTIFICACAO_MOBILE_SEQ *
********************************************
*/
  select count(1) vtable_exist
    into vtable_exist
    from ALL_OBJECTS
   where owner = 'PROD'
     and upper(OBJECT_NAME) like 'IDENTIFICACAO_MOBILE_SEQ';
  if vtable_exist = 0 then
    execute immediate('CREATE SEQUENCE IDENTIFICACAO_MOBILE_SEQ
						 START WITH     1
						 INCREMENT BY   1
						 NOCACHE
						 NOCYCLE ');
										
	dbms_output.put_line('SEQUENCE "IDENTIFICACAO_MOBILE_SEQ": Criada com sucesso!');
	
  else
    dbms_output.put_line('SEQUENCE "IDENTIFICACAO_MOBILE_SEQ": já existe!');
  end if;
  
  commit;
exception
  when others then
  dbms_output.put_line('Erro na criação da SEQUENCE "IDENTIFICACAO_MOBILE_SEQ": '||sqlerrm);
  rollback;
end;
/

prompt ********CRIANDO A TABELA PROD.REQUISICAO_ASSINATURA_MOBILE

declare
  vtable_exist integer;
begin
/*
***************************************************
* CRIA A TABELA PROD.REQUISICAO_ASSINATURA_MOBILE *
***************************************************
*/
  select count(1) vtable_exist
    into vtable_exist
    from ALL_TABLES
   where owner = 'PROD'
     and upper(table_name) like 'REQUISICAO_ASSINATURA_MOBILE';
  if vtable_exist = 0 then
    execute immediate('create table PROD.REQUISICAO_ASSINATURA_MOBILE (
							   ID_REQUISICAO_ASSINATURA_MOB  NUMBER(15,0)        NOT NULL ,
							   ID_IDENTIFICACAO_MOBILE       NUMBER(15,0)        NOT NULL ,
							   HASH_RECEBIDO                 VARCHAR2(4000 CHAR) NOT NULL ,
							   DT_SOLICITACAO                TIMESTAMP(6)        NOT NULL ,
							   HASH_ASSINADO                 VARCHAR2(4000 CHAR)          ,
							   DT_ASSINATURA                 TIMESTAMP(6)        NULL     ,
						CONSTRAINT PK_ID_REQUISICAO_ASSINAT_MOB PRIMARY KEY ( ID_REQUISICAO_ASSINATURA_MOB ) ,
						CONSTRAINT FK_IDENTIF_MOB_REQ_ASSINAT_MOB FOREIGN KEY ( ID_IDENTIFICACAO_MOBILE ) REFERENCES IDENTIFICACAO_MOBILE( ID_IDENTIFICACAO_MOBILE )
						) ');							   
									
	execute immediate('COMMENT ON COLUMN PROD.REQUISICAO_ASSINATURA_MOBILE.ID_REQUISICAO_ASSINATURA_MOB IS ''ID de identificação do Hash enviado pelo cliente mobile para assinatura.'' ');
	execute immediate('COMMENT ON COLUMN PROD.REQUISICAO_ASSINATURA_MOBILE.ID_IDENTIFICACAO_MOBILE IS ''Chave de identificação do cliente mobile que solicitou a assinatura - FK: IDENTIFICACAO_MOBILE.ID_IDENTIFICACAO_MOBILE'' ');
	execute immediate('COMMENT ON COLUMN PROD.REQUISICAO_ASSINATURA_MOBILE.HASH_RECEBIDO IS ''Hash enviado pelo cliente mobile para assinatura.'' ');
	execute immediate('COMMENT ON COLUMN PROD.REQUISICAO_ASSINATURA_MOBILE.DT_SOLICITACAO IS ''Timestamp da solicitação de assinatura.'' ');
	execute immediate('COMMENT ON COLUMN PROD.REQUISICAO_ASSINATURA_MOBILE.HASH_ASSINADO IS ''Hash assinado e que será retornado, via push, para o cliente mobile.'' ');
	execute immediate('COMMENT ON COLUMN PROD.REQUISICAO_ASSINATURA_MOBILE.DT_ASSINATURA IS ''Timestamp da assinatura.'' ');
	
	dbms_output.put_line('Tabela "REQUISICAO_ASSINATURA_MOBILE": Criada com sucesso!');
	
  else
    dbms_output.put_line('Tabela "REQUISICAO_ASSINATURA_MOBILE": já existe!');
  end if;
  
  commit;
exception
  when others then
  dbms_output.put_line('Erro na criação da entidade REQUISICAO_ASSINATURA_MOBILE:'||sqlerrm);
  rollback;
end;
/

prompt ******** CRIANDO A SEQUENCE REQUISICAO_ASSINATURA_MOB_SEQ

declare
  vtable_exist integer;
begin
/*
*************************************************
* CRIA A SEQUENCE REQUISICAO_ASSINATURA_MOB_SEQ *
*************************************************
*/
  select count(1) vtable_exist
    into vtable_exist
    from ALL_OBJECTS
   where owner = 'PROD'
     and upper(OBJECT_NAME) like 'REQUISICAO_ASSINATURA_MOB_SEQ';
  if vtable_exist = 0 then
    execute immediate('CREATE SEQUENCE REQUISICAO_ASSINATURA_MOB_SEQ
						 START WITH     1
						 INCREMENT BY   1
						 NOCACHE
						 NOCYCLE');
										
	dbms_output.put_line('SEQUENCE "REQUISICAO_ASSINATURA_MOB_SEQ": Criada com sucesso!');
	
  else
    dbms_output.put_line('SEQUENCE "REQUISICAO_ASSINATURA_MOB_SEQ": já existe!');
  end if;
  
  commit;
exception
  when others then
  dbms_output.put_line('Erro na criação da SEQUENCE "REQUISICAO_ASSINATURA_MOB_SEQ": '||sqlerrm);
  rollback;
end;
/

prompt ********CRIANDO A FUNCTION PROD.SPC_IDENTIFICACAO_MOBILE

/*
*************************************************
* CRIA A FUNCTION PROD.SPC_IDENTIFICACAO_MOBILE *
*************************************************
*/

create or replace function SPC_IDENTIFICACAO_MOBILE ( p_id_identificacao_mobile varchar2 default null,
													  p_documento               varchar2 default null,
													  p_hash_push				varchar2 default null )
return util.refcursor
  is
	r util.refcursor;
	v_sql				varchar2(2000);
begin
	
	if ( TRIM( p_id_identificacao_mobile ) IS NULL AND
		 TRIM( p_documento ) IS NULL AND
		 TRIM( p_hash_push ) IS NULL ) then
		 
		RAISE_APPLICATION_ERROR(-20001,'Pelo menos um dos parametros deve ser informado');
	end if;
	
	v_sql := 'SELECT IM.ID_IDENTIFICACAO_MOBILE, IM.HASH_PUSH, IM.DOCUMENTO, IM.NO_NOME, IM.EM_EMAIL					
				FROM IDENTIFICACAO_MOBILE IM
			   WHERE 1 = 1';
			   
	if ( TRIM( p_id_identificacao_mobile ) IS NOT NULL ) then
		v_sql := v_sql || ' AND IM.ID_IDENTIFICACAO_MOBILE = ' || p_id_identificacao_mobile;
	end if;
	
	if ( TRIM( p_documento ) IS NOT NULL ) then
		v_sql := v_sql || ' AND IM.DOCUMENTO = ' || p_documento;
	end if;
	
	if ( TRIM( p_hash_push ) IS NOT NULL ) then
		v_sql := v_sql || ' AND IM.HASH_PUSH = ' || p_hash_push;
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
********************************************
* CRIA A FUNCTION SPI_IDENTIFICACAO_MOBILE *
********************************************
*/

prompt ******** CRIANDO A FUNCTION SPI_IDENTIFICACAO_MOBILE

create or replace function SPI_IDENTIFICACAO_MOBILE (
								p_hash_push 	varchar2,
								p_documento 	varchar2,
								p_no_nome       varchar2,
								p_em_email		varchar2 )
return varchar2
is
  -- -------------------------------------------------------------------------------------------
  --    Function....: SPI_IDENTIFICACAO_MOBILE
  --    Assunto.....: Insere dados na IDENTIFICACAO_MOBILE
  --    Data Criacao: 30/11/2017 - Aimbere Galdino
  --    Revisada por: 00/00/0000 - 
  --    Manutencoes.: 00/00/0000 - 
  -- -------------------------------------------------------------------------------------------
  v_identificador NUMBER(15,0);
  v_hash_push     VARCHAR2(100 CHAR);
  v_documento     VARCHAR2(14  CHAR);
  v_no_nome       VARCHAR2(150 CHAR);
  v_em_email	  VARCHAR2(60  CHAR);
     
begin

	v_hash_push :=  SUBSTR( nvl(p_hash_push, ''), 0, 100);
	v_documento :=  SUBSTR( nvl(p_documento, ''), 0, 14);
	v_no_nome   :=  SUBSTR( nvl(p_no_nome, ''), 0, 150);
	v_em_email	:=  SUBSTR( nvl(p_em_email, ''), 0, 60);
	

	/*
	*	Validacao de obrigatoriedade dos parametros
	*/
	
	if ( TRIM( v_hash_push ) IS NULL ) then
		RAISE_APPLICATION_ERROR( -20001,'O HASH_PUSH deve ser informado');
    end if;
	
	if ( TRIM( v_documento ) IS NULL ) then
		RAISE_APPLICATION_ERROR( -20002,'O DOCUMENTO deve ser informado');
    end if;
	
	if ( TRIM( v_no_nome ) IS NULL ) then
		RAISE_APPLICATION_ERROR( -20003,'O NO_NOME deve ser informado');
    end if;

	select IDENTIFICACAO_MOBILE_SEQ.nextval into v_identificador from dual;
  
  
	INSERT INTO IDENTIFICACAO_MOBILE ( ID_IDENTIFICACAO_MOBILE, HASH_PUSH,   DOCUMENTO,   NO_NOME,   EM_EMAIL )
							  VALUES ( V_IDENTIFICADOR,         V_HASH_PUSH, V_DOCUMENTO, V_NO_NOME, V_EM_EMAIL);
	COMMIT;
      
	RETURN TO_CHAR(V_IDENTIFICADOR,'FM999999999999999');
end;
/

/*
********************************************
* CRIA A FUNCTION SPU_IDENTIFICACAO_MOBILE *
********************************************
*/

prompt ******** CRIANDO A FUNCTION SPU_IDENTIFICACAO_MOBILE

create or replace function SPU_IDENTIFICACAO_MOBILE (
								p_id_mobile 	varchar2,
								p_hash_push 	varchar2,
								p_documento 	varchar2,
								p_no_nome       varchar2,
								p_em_email		varchar2 )
return number
is
  -- -------------------------------------------------------------------------------------------
  --    Function....: SPU_IDENTIFICACAO_MOBILE
  --    Assunto.....: Atualiza dados na IDENTIFICACAO_MOBILE
  --    Data Criacao: 01/12/2017 - Aimbere Galdino
  --    Revisada por: 00/00/0000 - 
  --    Manutencoes.: 00/00/0000 - 
  -- -------------------------------------------------------------------------------------------
  v_identificador IDENTIFICACAO_MOBILE.ID_IDENTIFICACAO_MOBILE%type;
  v_hash_push     IDENTIFICACAO_MOBILE.HASH_PUSH%type;
  v_documento     IDENTIFICACAO_MOBILE.DOCUMENTO%type;
  v_no_nome       IDENTIFICACAO_MOBILE.NO_NOME%type;
  v_em_email	  IDENTIFICACAO_MOBILE.EM_EMAIL%type;
  
  v_ret 		  number;
     
begin

	v_identificador :=  TO_NUMBER ( nvl( p_id_mobile,'0'),'99999999999999');
	v_hash_push     :=  SUBSTR( nvl(p_hash_push, ''), 0, 100);
	v_documento     :=  SUBSTR( nvl(p_documento, ''), 0, 14);
	v_no_nome       :=  SUBSTR( nvl(p_no_nome, ''), 0, 150);
	v_em_email     	:=  SUBSTR( nvl(p_em_email, ''), 0, 60);
	
	v_ret           := -2;
	

	/*
	*	Validacao de obrigatoriedade dos parametros
	*/
	
	if ( v_identificador = '0' ) then
		RAISE_APPLICATION_ERROR( -20004,'O ID do registro é obrigatório.');
    end if;
	
	if ( TRIM( v_hash_push ) IS NULL ) then
		RAISE_APPLICATION_ERROR( -20001,'O HASH_PUSH deve ser informado');
    end if;
	
	if ( TRIM( v_documento ) IS NULL ) then
		RAISE_APPLICATION_ERROR( -20002,'O DOCUMENTO deve ser informado');
    end if;
	
	if ( TRIM( v_no_nome ) IS NULL ) then
		RAISE_APPLICATION_ERROR( -20003,'O NO_NOME deve ser informado');
    end if;

  
	BEGIN
		UPDATE IDENTIFICACAO_MOBILE
		   SET HASH_PUSH = v_hash_push ,
			   DOCUMENTO = v_documento ,
			   NO_NOME   = v_no_nome ,
			   EM_EMAIL  = v_em_email
		 WHERE ID_IDENTIFICACAO_MOBILE = v_identificador;
		 
		 v_ret:=sql%rowcount;
		 
		 COMMIT;
		 
		 exception
			when others then
				return sqlcode;
	END;
	
	return v_ret;
	
	exception
		when no_data_found then
			return v_ret;
end;
/

prompt ******** CRIANDO A FUNCTION SPC_REQUISICAO_ASSINAT_MOBILE

/*
******************************************************
* CRIA A FUNCTION PROD.SPC_REQUISICAO_ASSINAT_MOBILE *
******************************************************
*/

create or replace function SPC_REQUISICAO_ASSINAT_MOBILE ( p_id_requisicao_assinatura varchar2 default null,
													       p_id_identificacao_mobile  varchar2 default null )
return util.refcursor
  is
	r util.refcursor;
	v_sql						varchar2(2000);
	v_id_requisicao_assinatura  number(15,0);
	v_id_identificacao_mobile   number(15,0);
	
begin

	v_id_requisicao_assinatura := TO_NUMBER ( nvl( p_id_requisicao_assinatura,'0'),'99999999999999');
	v_id_identificacao_mobile := TO_NUMBER ( nvl( p_id_identificacao_mobile,'0'),'99999999999999');
	
	if ( v_id_requisicao_assinatura = 0 AND
		 v_id_identificacao_mobile = 0 ) then
		RAISE_APPLICATION_ERROR(-20001,'Pelo menos um dos parametros deve ser informado');
	end if;
	
	v_sql := ' SELECT ID_REQUISICAO_ASSINATURA_MOB, ID_IDENTIFICACAO_MOBILE, DT_SOLICITACAO, HASH_RECEBIDO, DT_ASSINATURA, HASH_ASSINADO ' ||
			 '	FROM REQUISICAO_ASSINATURA_MOBILE ' ||
			 ' WHERE 1 = 1 ';
			   
	if ( v_id_requisicao_assinatura <> 0 ) then
		v_sql := v_sql || ' AND ID_REQUISICAO_ASSINATURA_MOB = ' || v_id_requisicao_assinatura;
	end if;
	
	if ( v_id_identificacao_mobile <> 0 ) then
		v_sql := v_sql || ' AND ID_IDENTIFICACAO_MOBILE = ' || v_id_identificacao_mobile;
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
*************************************************
* CRIA A FUNCTION SPI_REQUISICAO_ASSINAT_MOBILE *
*************************************************
*/

prompt ******** CRIANDO A FUNCTION SPI_REQUISICAO_ASSINAT_MOBILE

create or replace function SPI_REQUISICAO_ASSINAT_MOBILE (
								p_identificacao_mobile  varchar2,
								p_hash_recebido 	    varchar2,
								p_dt_solicitacao        varchar2,
								p_hash_assinado		    varchar2,
							    p_dt_assinatura		    varchar2  )
return varchar2
is
  -- -------------------------------------------------------------------------------------------
  --    Function....: SPI_REQUISICAO_ASSINAT_MOBILE
  --    Assunto.....: Insere dados na REQUISICAO_ASSINATURA_MOBILE
  --    Data Criacao: 01/12/2017 - Aimbere Galdino
  --    Revisada por: 00/00/0000 - 
  --    Manutencoes.: 00/00/0000 - 
  -- -------------------------------------------------------------------------------------------
	v_identificador        REQUISICAO_ASSINATURA_MOBILE.ID_REQUISICAO_ASSINATURA_MOB%type;
	v_identificacao_mobile REQUISICAO_ASSINATURA_MOBILE.ID_IDENTIFICACAO_MOBILE%type;
	v_hash_recebido 	   REQUISICAO_ASSINATURA_MOBILE.HASH_RECEBIDO%type;
	v_dt_solicitacao       REQUISICAO_ASSINATURA_MOBILE.DT_SOLICITACAO%type;
	v_hash_assinado		   REQUISICAO_ASSINATURA_MOBILE.HASH_ASSINADO%type;
	v_dt_assinatura		   REQUISICAO_ASSINATURA_MOBILE.DT_ASSINATURA%type;
	
	v_validation           boolean;
	
begin

    v_identificacao_mobile := TO_NUMBER ( nvl( p_identificacao_mobile,'0'),'99999999999999' );
	v_hash_recebido 	   := SUBSTR( nvl(p_hash_recebido, ''), 0, 100);
	v_dt_solicitacao       := to_timestamp( p_dt_solicitacao,'DD/MM/YYYY - HH24:MI:SS');
	v_hash_assinado		   := SUBSTR( nvl( p_hash_assinado, ''), 0, 150);
	v_dt_assinatura		   := to_timestamp( p_dt_assinatura,'DD/MM/YYYY - HH24:MI:SS');

	/*
	*	Validacao de obrigatoriedade dos parametros
	*/
	
	if ( v_identificacao_mobile = '0' ) then
		RAISE_APPLICATION_ERROR( -20001,'O ID de registro deve ser informado');
    end if;
	
	if ( TRIM( v_hash_recebido ) IS NULL ) then
		RAISE_APPLICATION_ERROR( -20002,'O HASH deve ser informado.');
    end if;
	
	/* Obrigatoriedade: Se informar o hash ou a data da solicitação, ambos são obrigatórios */
	if ( NOT ( TRIM( v_hash_recebido ) IS NOT NULL AND
	           v_dt_solicitacao IS NOT NULL ) ) THEN
	
		v_validation := XOR( TRIM( v_hash_recebido ) IS NOT NULL,
							 v_dt_solicitacao IS NOT NULL );
		if (  v_validation = true ) then
			RAISE_APPLICATION_ERROR( -20003,'Se informar o hash ou a data da solicitação, ambos são obrigatórios');
		end if;
	END IF;
	
	
	/* Obrigatoriedade: Se informar o hash_assinado ou a data de assinatura, ambos são obrigatórios */
	if ( NOT ( TRIM( v_hash_assinado ) IS NOT NULL AND
	           v_dt_assinatura IS NOT NULL ) ) THEN
	
		v_validation := XOR( TRIM( v_hash_assinado ) IS NOT NULL,
							 v_dt_assinatura IS NOT NULL );
		if (  v_validation = true ) then
			RAISE_APPLICATION_ERROR( -20004,'Se informar o hash_assinado ou a data de assinatura, ambos são obrigatórios');
		end if;
	END IF;

	select REQUISICAO_ASSINATURA_MOB_SEQ.nextval into v_identificador from dual;
  
  
	INSERT INTO REQUISICAO_ASSINATURA_MOBILE ( ID_REQUISICAO_ASSINATURA_MOB, ID_IDENTIFICACAO_MOBILE, HASH_RECEBIDO,   DT_SOLICITACAO,   HASH_ASSINADO,   DT_ASSINATURA )
		 VALUES ( V_IDENTIFICADOR,              v_identificacao_mobile,  v_hash_recebido, v_dt_solicitacao, v_hash_assinado, v_dt_assinatura);
	COMMIT;
									  
	RETURN TO_CHAR(V_IDENTIFICADOR,'FM999999999999999');
end;
/

/*
*************************************************
* CRIA A FUNCTION SPU_REQUISICAO_ASSINAT_MOBILE *
*************************************************
*/

prompt ******** CRIANDO A FUNCTION SPU_REQUISICAO_ASSINAT_MOBILE

create or replace function SPU_REQUISICAO_ASSINAT_MOBILE (
								p_identificador 	    varchar2,
								p_identificacao_mobile 	varchar2,
								p_hash_recebido 	    varchar2,
								p_dt_solicitacao        varchar2,
								p_hash_assinado		    varchar2 ,
								p_dt_assinatura		    varchar2 )
								
return number
is
  -- -------------------------------------------------------------------------------------------
  --    Function....: SPU_REQUISICAO_ASSINAT_MOBILE
  --    Assunto.....: Atualiza dados na IDENTIFICACAO_MOBILE
  --    Data Criacao: 01/12/2017 - Aimbere Galdino
  --    Revisada por: 00/00/0000 - 
  --    Manutencoes.: 00/00/0000 - 
  -- -------------------------------------------------------------------------------------------
  v_identificador         REQUISICAO_ASSINATURA_MOBILE.ID_REQUISICAO_ASSINATURA_MOB%type;
  v_identificacao_mobile  REQUISICAO_ASSINATURA_MOBILE.ID_IDENTIFICACAO_MOBILE%type;
  v_hash_recebido         REQUISICAO_ASSINATURA_MOBILE.HASH_RECEBIDO%type;
  v_dt_solicitacao        REQUISICAO_ASSINATURA_MOBILE.DT_SOLICITACAO%type;
  v_hash_assinado         REQUISICAO_ASSINATURA_MOBILE.HASH_ASSINADO%type;
  v_dt_assinatura	      REQUISICAO_ASSINATURA_MOBILE.DT_ASSINATURA%type;
  
  v_ret 		          number;
  v_validation            boolean;
     
begin

	v_identificador        := TO_NUMBER ( nvl( p_identificador,'0'),'99999999999999' );
	v_identificacao_mobile := TO_NUMBER ( nvl( p_identificacao_mobile,'0'),'99999999999999' );
	v_hash_recebido 	   := SUBSTR( nvl(p_hash_recebido, ''), 0, 100);
	v_dt_solicitacao       := to_timestamp( p_dt_solicitacao,'DD/MM/YYYY - HH24:MI:SS');
	v_hash_assinado		   := SUBSTR( nvl( p_hash_assinado, ''), 0, 150);
	v_dt_assinatura		   := to_timestamp( p_dt_assinatura,'DD/MM/YYYY - HH24:MI:SS');
	
	v_ret           := -2;

	/*
	*	Validacao de obrigatoriedade dos parametros
	*/
	
	if ( v_identificacao_mobile = '0' ) then
		RAISE_APPLICATION_ERROR( -20001,'O ID de registro deve ser informado');
    end if;
	
	if ( TRIM( v_hash_recebido ) IS NULL ) then
		RAISE_APPLICATION_ERROR( -20002,'O HASH deve ser informado.');
    end if;
	
	/* Obrigatoriedade: Se informar o hash ou a data da solicitação, ambos são obrigatórios */
	if ( NOT ( TRIM( v_hash_recebido ) IS NOT NULL AND
	           v_dt_solicitacao IS NOT NULL ) ) THEN
	
		v_validation := XOR( TRIM( v_hash_recebido ) IS NOT NULL,
							 v_dt_solicitacao IS NOT NULL );
		if (  v_validation = true ) then
			RAISE_APPLICATION_ERROR( -20003,'Se informar o hash ou a data da solicitação, ambos são obrigatórios');
		end if;
	END IF;
	
	
	/* Obrigatoriedade: Se informar o hash_assinado ou a data de assinatura, ambos são obrigatórios */
	if ( NOT ( TRIM( v_hash_assinado ) IS NOT NULL AND
	           v_dt_assinatura IS NOT NULL ) ) THEN
	
		v_validation := XOR( TRIM( v_hash_assinado ) IS NOT NULL,
							 v_dt_assinatura IS NOT NULL );
		if (  v_validation = true ) then
			RAISE_APPLICATION_ERROR( -20004,'Se informar o hash_assinado ou a data de assinatura, ambos são obrigatórios');
		end if;
	END IF;

  
	BEGIN
		UPDATE REQUISICAO_ASSINATURA_MOBILE
		   SET ID_IDENTIFICACAO_MOBILE = v_identificacao_mobile ,
    		   HASH_RECEBIDO           = v_hash_recebido        ,   
			   DT_SOLICITACAO          = v_dt_solicitacao       ,   
			   HASH_ASSINADO           = v_hash_assinado        ,   
			   DT_ASSINATURA           = v_dt_assinatura
		 WHERE ID_REQUISICAO_ASSINATURA_MOB = v_identificador;
		 
		 v_ret:=sql%rowcount;
		 
		 COMMIT;
		 
		 exception
			when others then
				return sqlcode;
	END;
	
	return v_ret;
	
	exception
		when no_data_found then
			return v_ret;
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