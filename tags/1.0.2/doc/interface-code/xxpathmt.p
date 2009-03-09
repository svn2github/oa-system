{mfdtitle.i}
DEF VAR in-path AS CHAR FORMAT "x(40)" LABEL "Import path".
DEF VAR bakpath AS CHAR FORMAT "x(40)" LABEL "Backup path".
DEF VAR errpath AS CHAR FORMAT "x(40)" LABEL "Error path".

form
    SKIP(1) 
    in-path COLON 25
    bakpath COLON 25
    errpath COLON 25
	  skip(1) 
    with frame a side-labels width 80 
    title "Path Maintenance".


REPEAT:
    
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
    
    DISP in-path bakpath errpath WITH FRAME a.

    UPDATE in-path bakpath errpath WITH FRAME a.

    FIND FIRST CODE_mstr WHERE code_domain = global_domain
                           and CODE_fldname = "path"
                           AND CODE_value = "Import path"
                           NO-ERROR.
    IF NOT AVAIL CODE_mstr THEN DO:
        CREATE CODE_mstr.
        ASSIGN code_domain = global_domain
               CODE_fldname = "path"
               CODE_value = "Import path".
    END.
    ASSIGN CODE_cmmt = in-path.

    FIND FIRST CODE_mstr WHERE code_domain = global_domain
                           and CODE_fldname = "path"
                           AND CODE_value = "Backup path"
                           NO-ERROR.
    IF NOT AVAIL CODE_mstr THEN DO:
        CREATE CODE_mstr.
        ASSIGN code_domain = global_domain
               CODE_fldname = "path"
               CODE_value = "Backup path".
    END.
    ASSIGN CODE_cmmt = bakpath.

    FIND FIRST CODE_mstr WHERE code_domain = global_domain
                           and CODE_fldname = "path"
                           AND CODE_value = "Error path"
                           NO-ERROR.
    IF NOT AVAIL CODE_mstr THEN DO:
        CREATE CODE_mstr.
        ASSIGN code_domain = global_domain
               CODE_fldname = "path"
               CODE_value = "Error path".
    END.
    ASSIGN CODE_cmmt = errpath.

END.

