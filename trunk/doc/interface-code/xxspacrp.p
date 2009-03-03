{mfdtitle.i}
def var domain like ac_domain.
def var acc1   like ac_code.
def var acc2   like ac_code.
def var usesac as   char format "x(1)" label "Special Account" init "1".
def var erryn  as   logi init yes.
def var useyn  as   logi label "Special Account".

form 
    skip(1)
    acc1 colon 25
    acc2 colon 45 label {t001.i}
    usesac colon 25 
    "  [1.Special Account 2.Not special Account 3.All]" 
    with frame a side-label width 80.
    
domain = global_domain.

repeat with frame a:
    if acc2 = hi_char then acc2 = "".
    update acc1 acc2
           usesac validate(lookup(usesac,"1,2,3") > 0 ,"Error usesac!")
           with frame a.
    if acc2 = "" then acc2 = hi_char.
    bcdparm = "".
    {mfquoter.i acc1 }
    {mfquoter.i acc2 }
    {mfquoter.i usesac }
    
    {mfselprt.i "page" 80}
    for each ac_mstr no-lock where ac_domain = domain
                               and ac_code >= acc1 and ac_code <= acc2
                               and ((usesac = "1" and ac__log01)
                                   or (usesac = "2" and not ac__log01)
                                   or usesac = "3"):
        disp ac_code ac_desc ac__log01 label "Special Account" with frame b down.
        down with frame b.
    end.
    {mfreset.i}
end.