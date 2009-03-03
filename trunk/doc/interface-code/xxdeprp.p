{mfdtitle.i}
def var domain like xxdept_domain.

def var dept1 like xxdept_dept.
def var dept2 like xxdept_dept.
def var p-code1 like xxdept_p_code.
def var p-code2 like xxdept_p_code.
def var stat  like xxdept_stat label "Validate Only".

form 
    dept1 colon 25
    dept2 colon 45 label {t001.i}
    p-code1 colon 25
    p-code2 colon 45 label {t001.i}
    stat colon 25
    with frame a width 80 side-label.
  
domain = global_domain.
  
repeat:
    if dept2 = hi_char then dept2 = "".
    if p-code2 = hi_char then p-code2 = "".
    update dept1 dept2 
           p-code1 p-code2
           stat 
           with frame a.
    if dept2 = "" then dept2 = hi_char.
    if p-code2 = "" then p-code2 = hi_char.
    
    {mfselbpr.i "terminal" 80}
    for each xxdept_mstr no-lock where xxdept_domain = domain
                                   and xxdept_dept >= dept1 and xxdept_dept <= dept2
                                   and xxdept_p_code >= p-code1 and xxdept_p_code <= p-code2
                                   and ((not xxdept_stat and stat) or not stat)
                                   :
        disp xxdept_dept xxdept_desc xxdept_p_code xxdept__chr01 label "Cost Center" xxdept_stat with frame b down.
    end.
    {mfreset.i}
end.