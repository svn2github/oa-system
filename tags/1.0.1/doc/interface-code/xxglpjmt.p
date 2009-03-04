{mfdtitle.i}
def var domain like pj_domain.
def var pjcode like pj_project.
def var erryn  as   logi init yes.
def var lcode  as   char format "x(10)" label "Person ID".

form 
    skip(1)
    pjcode colon 25
    pj_desc colon 40 no-label
    skip(1)
    lcode  colon 25
    xxdept_desc colon 40 no-label
    with frame a side-label width 80.
    
domain = global_domain.

repeat with frame a:
    recno = ?.
    prompt-for pjcode EDITING:

       /* FIND NEXT/PREVIOUS RECORD */
       {mfnp01.i pj_mstr pjcode  pj_project domain pj_domain
                 pj_project}
       if recno <> ? then do:
          pjcode = pj_project.
          lcode = pj__qadc01.
          display
                 pjcode pj_desc lcode.
       end.
    end. /* prompt-for */
    bcdparm = "".
    {mfquoter.i pjcode }
    find pj_mstr where pj_domain = domain
                   and pj_project = input pjcode no-error.
    if not avail pj_mstr then do:
       message "No validate Project Code, Please reenter!".
       undo,retry.
    end.
    
       update lcode.
       assign pj__qadc01 = lcode.
       
       /*
    if ltype = "2" then do:
       recno = ?.
       prompt-for lcode editing:
          {mfnp01.i xxdept_mstr lcode xxdept_dept domain xxdept_domain
                    xxdept_idx01}
          if recno <> ? then do:
             lcode = xxdept_dept.
             display
                    lcode xxdept_desc.
          end.
       end.
       find xxdept_mstr no-lock where xxdept_dept = input lcode
                                  and xxdept_domain = domain no-error.
       if not avail xxdept_mstr then do:
          message "No validate department information ,please maintain it first!" update erryn.
          undo,retry.
       end.
       disp xxdept_desc.
       assign pj__qadc01 = input lcode.
    end.
    */
end.