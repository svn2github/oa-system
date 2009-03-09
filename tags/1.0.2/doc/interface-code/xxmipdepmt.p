{mfdtitle.i}
def var line as char format "x(67)".
DEF VAR in-file AS CHAR FORMAT "x(40)" LABEL "Import file name".
DEF VAR in-path AS CHAR FORMAT "x(40)" LABEL "Import path".
DEF VAR bakpath AS CHAR FORMAT "x(40)" LABEL "Backup path".
DEF VAR errpath AS CHAR FORMAT "x(40)" LABEL "Error path".

def var domain like xxdept_domain.
    FOR FIRST CODE_mstr NO-LOCK 
        WHERE code_domain = global_domain
          and CODE_fldname = "path" 
          AND CODE_value   = "Import path"
          :
        in-path = CODE_cmmt.
    END.
    FOR FIRST CODE_mstr NO-LOCK 
        WHERE code_domain = global_domain
          and CODE_fldname = "path" 
          AND CODE_value   = "Backup path"
          :
        bakpath = CODE_cmmt.
    END.
    FOR FIRST CODE_mstr NO-LOCK 
        WHERE code_domain = global_domain
          and CODE_fldname = "path" 
          AND CODE_value   = "Error path"
          :
        errpath = CODE_cmmt.
    END.
    
form
    in-file COLON 25
    SKIP(1)
    in-path COLON 25
    bakpath COLON 25
    errpath COLON 25
	  skip(1) 
    with frame a side-labels width 80
    title "Departmen Import".
domain = global_domain.    
repeat :   
    in-file = "department/department.txt".
    DISP in-path bakpath errpath WITH FRAME a.
    UPDATE in-file WITH FRAME a.
    if substring(in-path,length(in-path),1) <> "/"
       then in-path = in-path + "/".
   
    input from value(in-path + in-file).
    repeat:
        IMPORT unformatted line .
        if substring(line,1,8) <> "" then do:
           find xxdept_mstr where xxdept_domain = domain and xxdept_dept = substring(line,1,8) no-error.
           if not avail xxdept_mstr then do:
              create xxdept_mstr.
              assign xxdept_domain = domain
                     xxdept_dept = substring(line,1,8)
                     .
           end.
           assign xxdept_desc   = substring(line,9,50)
                  xxdept_p_code = substring(line,59,8)
                  xxdept__dte01 = today
                  xxdept__log01 = yes
                  .
           if substring(line,67,1) = "0" then xxdept_stat = yes.
           else xxdept_stat = no.
        end.
    end.
    input close.
    for each xxdept_mstr no-lock where xxdept_domain = domain and xxdept__log01:
        disp xxdept_dept xxdept_desc xxdept_p_code xxdept_stat 
             with frame b width 80 down .
    end.
    for each xxdept_mstr where xxdept_domain = domain and xxdept__log01:
        xxdept__log01 = no.
    end.
    os-delete value(in-path + in-file).
end.
