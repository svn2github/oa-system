{mfdtitle.i}
def var domain like xxpc_domain.
def var pj1    like xxpc_project.
def var pj2    like xxpc_project.
def var yy1     like xxpc_year.
def var yy2     like xxpc_year.
def var mm1    like xxpc_month label "Month From".
def var mm2    like xxpc_month label "Month To".
def var err    as   char.
def var crt    as   logical.
def buffer xxpcmstr for xxpc_mstr.

form
    yy1  colon 25
    yy2 colon 50
    pj1 colon 25
    pj2 colon 50 label {t001.i}
    mm1 colon 25
    mm2 colon 50
    /*
    mm1 colon 25
    mm2 colon 50
    */
    with frame a width 80 side-label.
    
repeat:
    yy1 = year(today).
    yy2 = year(today).
    mm1 = month(today) - 1.
    mm2 = month(today).
    domain = global_domain.
    if pj2 = hi_char then pj2 = "".
    update yy1 validate(yy1 <> 0 , "please input year")
           yy2 validate(yy2 <> 0 , "please input year")
           pj1 pj2 
           mm1 validate (mm1 <> 0 ,"please input month from")
           mm2 validate (mm2 <> 0 , "please input month to")
           with frame a.
    if pj2 = "" then pj2 = hi_char.
    /*message domain "yy" pj1 "xx" pj2 yy mm1.*/
    for each xxpcmstr no-lock where xxpcmstr.xxpc_domain = domain 
                                 and xxpcmstr.xxpc_project >= pj1 and xxpcmstr.xxpc_project <= pj2
                                 and xxpcmstr.xxpc_year = yy1 and xxpcmstr.xxpc_month = mm1
        break by xxpcmstr.xxpc_domain by xxpcmstr.xxpc_project by xxpcmstr.xxpc_year by xxpcmstr.xxpc_month:
        if first-of(xxpcmstr.xxpc_month) then do:
		        find first xxpc_mstr no-lock where xxpc_mstr.xxpc_domain = domain
		                                       and xxpc_mstr.xxpc_project = xxpcmstr.xxpc_project
		                                       and xxpc_mstr.xxpc_year    = yy2
		                                       and xxpc_mstr.xxpc_month = mm2 no-error.
		        if not avail xxpc_mstr then crt = yes.
		        else crt = no.
        end.
        /*message xxpcmstr.xxpc_project xxpcmstr.xxpc_year xxpcmstr.xxpc_month xxpcmstr.xxpc_cc crt.*/
        if crt then do:
           create xxpc_mstr.
           assign xxpc_mstr.xxpc_domain  = xxpcmstr.xxpc_domain 
                  xxpc_mstr.xxpc_project = xxpcmstr.xxpc_project
                  xxpc_mstr.xxpc_year    = yy2
                  xxpc_mstr.xxpc_month   = mm2
                  xxpc_mstr.xxpc_cc      = xxpcmstr.xxpc_cc
                  xxpc_mstr.xxpc_pct     = xxpcmstr.xxpc_pct
                  .
        end.
    end.
end.