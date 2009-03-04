{mfdtitle.i}
def var domain like ac_domain.
def var acc    like ac_code.
def var erryn  as   logi init yes.
def var useyn  as   logi label "Special Account".

form 
    skip(1)
    acc colon 25
    ac_desc colon 40 no-label
    skip(1)
    useyn  colon 25 help "Special Account -- won't have subacc costcenter projectcode in it's transactions from OA"
    with frame a side-label width 80.
    
domain = global_domain.

repeat with frame a:
    recno = ?.
    prompt-for acc EDITING:

       /* FIND NEXT/PREVIOUS RECORD */
       {mfnp01.i ac_mstr acc  ac_code domain ac_domain
                 ac_code}
       if recno <> ? then do:
          acc = ac_code.
          useyn = ac__log01.
          display
                 acc ac_desc useyn.
       end.
    end. /* prompt-for */
    bcdparm = "".
    {mfquoter.i acc }
    find first ac_mstr where ac_domain = domain
                         and ac_code = input acc no-error.
    if not avail ac_mstr then do:
       message "No validate Account, Please reenter!".
       undo,retry.
    end.
    useyn = ac__log01 .
    disp ac_desc useyn.
    
       update useyn.
       assign ac__log01 = useyn.
       
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