-- **********************************
-- *    SPC_LOG_MENSAGEM    *
-- **********************************

select SPC_LOG_MENSAGEM('14') from dual;

select SPC_LOG_MENSAGEM('','D') from dual;

select SPC_LOG_MENSAGEM( '','D', 'SIGN CMS', 'INTERVALOR', '04/12/2017 00:00:00', '04/12/2017 15:03:38' ) from dual;

-- **********************************
-- *    SPI_LOG_MENSAGEM    *
-- **********************************
SET SERVEROUTPUT ON
declare

  tipo_operacao varchar2(10);

  v_foo varchar2(32767);
begin

    select decode( MOD( SUBSTRB( to_char( systimestamp, 'SSFF' ), 0, 8 ) , 2 ), 0, 'R', 'D' )
    into tipo_operacao
    from dual;

    v_foo := SPI_LOG_MENSAGEM ( tipo_operacao ,
								'SIGN CMS',
								'INTERVALOR',
								'[{"updateDate": "2017-11-28T17:08:12Z",
                                        "quantity": 5,
                                        "total": 138792357.6699999868869781494140625,
                                        "type": "tipo" }]' );
    DBMS_OUTPUT.PUT_LINE( v_foo );
end;