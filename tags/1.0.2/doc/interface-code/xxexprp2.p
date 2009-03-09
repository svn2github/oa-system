{mfdtitle.i}
def var domain like xxpo_domain.
domain = global_domain.
def var epnbr1 like xxep_nbr.
def var epnbr2 like xxep_nbr.
def var reid1 like xxepre_reid.
def var reid2 like xxepre_reid.

form 
    epnbr1 colon 25
    epnbr2 colon 45  label {t001.i}
    reid1 colon 25
    reid2 colon 45 label {t001.i}
    with frame a width 80 side-label
    title "OA import PO Report".

repeat:
    if epnbr2 = hi_char then epnbr2 = "".
    if reid2 = hi_char then reid2 = "".
    update epnbr1 epnbr2
           reid1 reid2
           with frame a .
    if epnbr2 = "" then epnbr2 = hi_char.
    if reid2 = "" then reid2 = hi_char.
    
    {mfselbpr.i "terminal" 80}
    for each xxep_mstr no-lock where xxep_domain = domain 
                                 and xxep_nbr >= epnbr1 and xxep_nbr <= epnbr2,
        each xxepre_det no-lock where xxepre_domain = domain
                                 and xxepre_nbr = xxep_nbr
                                 and xxepre_reid >= reid1 and xxepre_reid <= reid2
        break by xxep_nbr with frame b width 300 down:
        if first-of(xxep_nbr) then 
           disp xxep_nbr xxep_title format "x(20)" xxep_desc format "x(20)" 
                xxep_depid xxep_amt xxep_curr xxep_crt_id .
        disp xxepre_reid xxepre_cscd format "x(17)" xxepre_encd xxepre_dpcd xxepre_psid xxepre_amt xxepre_pct.
        
    end.
    {mfreset.i}
end.