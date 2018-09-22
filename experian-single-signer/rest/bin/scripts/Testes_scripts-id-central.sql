-- **********************************
-- *    SPC_IDENTIFICACAO_MOBILE    *
-- **********************************

select SPC_IDENTIFICACAO_MOBILE('14','55079167538','6427567571500064275675729000') from dual;

select SPC_IDENTIFICACAO_MOBILE('14') from dual;

-- **********************************
-- *    SPI_IDENTIFICACAO_MOBILE    *
-- **********************************
SET SERVEROUTPUT ON
declare
  v_foo constant varchar2(32767) := SPI_IDENTIFICACAO_MOBILE (
/* Hash */						TO_CHAR(SYSTIMESTAMP, 'SSSSSFF')||TO_CHAR(SYSTIMESTAMP, 'SSSSSFF') ,
/* CPF */						to_char( dbms_random.value(10000000000, 99999999999), '99999999999' ),
/* nome */						dbms_random.string('L', 10) ||' '|| dbms_random.string('L', 10),
/* e-mail */					dbms_random.string('L', 10) ||'@teste.com' );
begin
  DBMS_OUTPUT.PUT_LINE( v_foo );
end;

-- **********************************
-- *    SPU_IDENTIFICACAO_MOBILE    *
-- **********************************
SET SERVEROUTPUT ON
DECLARE
  v_Return NUMBER;
  v_max_id NUMBER;
BEGIN
  
  select max( ID_IDENTIFICACAO_MOBILE )
    into v_max_id
    from IDENTIFICACAO_MOBILE;

  v_Return := SPU_IDENTIFICACAO_MOBILE(
                                to_char( v_max_id ),
/* Hash */						'UPDATE_' || TO_CHAR(SYSTIMESTAMP, 'SSSSSFF') || TO_CHAR(SYSTIMESTAMP, 'SSSSSFF') ,
/* CPF */						'UPDATE_' || to_char( dbms_random.value(10000000000, 99999999999), '99999999999' ),
/* nome */						'UPDATE_' || dbms_random.string('L', 10) ||' '|| dbms_random.string('L', 10),
/* e-mail */					'UPDATE_' || dbms_random.string('L', 10) ||'@teste.com' );
  
    DBMS_OUTPUT.PUT_LINE( v_Return );
END;


-- ***************************************
-- *    SPI_REQUISICAO_ASSINAT_MOBILE    *
-- ***************************************
SET SERVEROUTPUT ON
declare
    v_max_id NUMBER;
    v_Return NUMBER;
begin

 select max( ID_IDENTIFICACAO_MOBILE )
    into v_max_id
    from IDENTIFICACAO_MOBILE;

  v_Return := SPI_REQUISICAO_ASSINAT_MOBILE(
/* FK da Ident. Mobile */       to_char( v_max_id ),
/* Hash recebido */				dbms_random.string('L', 10) ||' '|| dbms_random.string('L', 10) ,
/* dt_solicitacao */			to_char( sysdate, 'DD/MM/YYYY'),
/* hash_assinado */			    dbms_random.string('L', 10) ||' '|| dbms_random.string('L', 10) ,
/* dt_assinatura */				to_char( sysdate, 'DD/MM/YYYY' ) );
  
    DBMS_OUTPUT.PUT_LINE( v_Return );
end;

-- ***************************************
-- *    SPU_REQUISICAO_ASSINAT_MOBILE    *
-- ***************************************
SET SERVEROUTPUT ON
declare
    v_Return     NUMBER;
    v_max_id     NUMBER;
    v_max_id_req NUMBER;
begin

 select max( ID_IDENTIFICACAO_MOBILE )
    into v_max_id
    from IDENTIFICACAO_MOBILE;

 select max( ID_REQUISICAO_ASSINATURA_MOB )
    into v_max_id_req
    from REQUISICAO_ASSINATURA_MOBILE;

  v_Return := SPU_REQUISICAO_ASSINAT_MOBILE(
/* ID da Req. Assinat. */       to_char( v_max_id_req ),
/* FK da Ident. Mobile */       to_char( v_max_id ),
/* Hash recebido */				dbms_random.string('L', 10) ||' '|| dbms_random.string('L', 10) ,
/* dt_solicitacao */			to_char( sysdate, 'DD/MM/YYYY'),
/* hash_assinado */			    dbms_random.string('L', 10) ||' '|| dbms_random.string('L', 10) ,
/* dt_assinatura */				to_char( sysdate, 'DD/MM/YYYY' ) );
  
    DBMS_OUTPUT.PUT_LINE( v_Return );
end;