{mfdtitle.i}
DEFINE VARIABLE search-dir AS CHARACTER FORMAT "x(60)" LABEL "dir".
DEFINE VARIABLE file-name AS CHARACTER FORMAT "x(50)" LABEL "File".
DEFINE VARIABLE attr-list AS CHARACTER FORMAT "x(4)" LABEL "Attributes".
DEFINE VARIABLE tmp-disp AS CHARACTER FORMAT "x(60)".
DEF VAR in-path AS CHAR FORMAT "x(40)" LABEL "Import path".

repeat:
    FOR FIRST CODE_mstr NO-LOCK 
        WHERE code_domain = global_domain
          and CODE_fldname = "path" 
          AND CODE_value   = "Import path"
          :
          in-path = CODE_cmmt.
   END.
   disp code_cmmt with frame a.
PROMPT-FOR search-dir with frame a.
ASSIGN search-dir.
 search-dir = in-path + search-dir.
def temp-table os-dir-search
    field os-dir-name as char format "x(50)"
    field os-dir-attr as char format "x(4)"
    .

if substring(search-dir,1,1) <> "/"
then run disp-win-dir.

if substring(search-dir,1,1) = "/"
then run disp-unix-dir.

end.
            /*
/*windows procedure*/
procedure disp-win-dir:
    if substring(search-dir,length(search-dir),1) <> "\"
    then search-dir = search-dir + "\".

    output to tmpfile.

    INPUT FROM OS-DIR(search-dir).
    repeat:
    create os-dir-search.
    SET os-dir-name ^ os-dir-attr . 

    end.
    input close.

    output close.

    FOR EACH os-dir-search:
        tmp-disp = search-dir + os-dir-name.
        DISP tmp-disp os-dir-attr .
    END.
end procedure.

              */


/*unix procedure*/
procedure disp-unix-dir:
    if substring(search-dir,length(search-dir),1) <> "/"
    then search-dir = search-dir + "/".

    output to tmpfile.

    INPUT FROM OS-DIR(search-dir).
    repeat:
    create os-dir-search.
    SET os-dir-name ^ os-dir-attr . 

    end.
    input close.

    output close.

    FOR EACH os-dir-search:
        tmp-disp = search-dir + os-dir-name.
        DISP tmp-disp os-dir-attr .
    END.
end procedure.



