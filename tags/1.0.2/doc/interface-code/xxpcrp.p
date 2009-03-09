{mfdtitle.i}
def var domain like xxpc_domain.
def var pj1    like xxpc_project.
def var pj2    like xxpc_project.
def var yy1    like xxpc_year.
def var yy2    like xxpc_year.
def var mm1    like xxpc_month.
def var mm2    like xxpc_month.
def var err    as   char.

form
    pj1 colon 25
    pj2 colon 50
    yy1 colon 25
    yy2 colon 50
    mm1 colon 25
    mm2 colon 50
    
    with frame a width 80 side-label.
    
repeat:
    domain = global_domain.
    if pj2 = hi_char then pj2 = "".
    if yy2 = 9999 then yy2 = 0.
    update pj1 pj2 yy1 yy2 mm1 mm2 with frame a.
    if pj2 = "" then pj2 = hi_char.
    if yy2 = 0 then yy2 = 9999.
    {mfselbpr.i "page" 80}
    for each xxpc_mstr no-lock where xxpc_domain = domain 
                                 and xxpc_project >= pj1 and xxpc_project <= pj2
                                 and ((yy1 = yy2 and xxpc_year = yy1 and xxpc_month >= mm1 and xxpc_month <= mm2)
                                      or (yy1 + 1 = yy2 and ((xxpc_year = yy1 and xxpc_month >= mm1) or (xxpc_year = yy2 and xxpc_month <= mm2)))
                                      or (yy1 + 1 < yy2 and ((xxpc_year = yy1 and xxpc_month >= mm1) or (xxpc_year = yy2 and xxpc_month <= mm2) or (xxpc_year > yy1 and xxpc_year < yy2)))),
        each pj_mstr no-lock where pj_project = xxpc_project,
		    each cc_mstr no-lock where cc_domain = domain
		                           and cc_ctr    = xxpc_cc
        break by xxpc_project by xxpc_year by xxpc_month:
        if first-of(xxpc_month) then do:
           disp xxpc_project pj_desc format "x(10)" pj__qadc01 label "Person ID in OA" xxpc_year with frame b width 120.
        end.
        disp xxpc_month xxpc_cc cc_desc format "x(26)" xxpc_pct with frame b down.
        down with frame b.
    end.
    {mfreset.i}
end.