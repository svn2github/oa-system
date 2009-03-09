{mfdtitle.i}
def var domain like xxpo_domain.
domain = global_domain.
def var epnbr1 like xxep_nbr.
def var epnbr2 like xxep_nbr.
def var eptype1 like xxepd_type.
def var eptype2 like xxepd_type.
def var epflag1 like xxepd_flag.
def var epflag2 like xxepd_flag.
def var date1   as date label "Import Date" init today.
def var date2   as date.

form 
    epnbr1 colon 25
    epnbr2 colon 45  label {t001.i}
    eptype1 colon 25
    eptype2 colon 45 label {t001.i}
    epflag1 colon 25
    epflag2 colon 45 label {t001.i}
    date1 colon 25
    date2 colon 45 label {t001.i}
    with frame a width 80 side-label
    title "OA import Expense Report".

repeat:
    if epnbr2 = hi_char then epnbr2 = "".
    if eptype2 = hi_char then eptype2 = "".
    if epflag2 = hi_char then epflag2 = "".
    if date1 = low_date then date1 = ?.
    if date2 = hi_date then date2 = ?.
    update epnbr1 epnbr2
           eptype1 eptype2
           epflag1 epflag2
           date1 date2
           with frame a .
    if epnbr2 = "" then epnbr2 = hi_char.
    if eptype2 = "" then eptype2 = hi_char.
    if epflag2 = "" then epflag2 = hi_char.
    if date1 = ? then date1 = low_date.
    if date2 = ? then date2 = hi_date.
    
    {mfselbpr.i "terminal" 80}
    for each xxep_mstr no-lock where xxep_domain = domain 
                                 and xxep_nbr >= epnbr1 and xxep_nbr <= epnbr2
                                 and xxep__dte01 >= date1 and xxep__dte01 <= date2,
        each xxepd_det no-lock where xxepd_domain = domain
                                 and xxepd_nbr = xxep_nbr
                                 and xxepd_type >= eptype1 and xxepd_type <= eptype2
                                 and xxepd_flag >= epflag1 and xxepd_flag <= epflag2
        break by xxep_nbr with frame b width 300 down:
        if first-of(xxep_nbr) then 
           disp xxep_nbr xxep_title format "x(20)" xxep_desc format "x(20)"  xxep__dte01 label "Import Date"
                xxep_depid xxep_amt xxep_curr xxep_crt_id .
        disp xxepd_type xxepd_flag xxepd_amt xxepd_cf_amt.
        if first-of(xxep_nbr) then disp xxep__chr05 format "x(26)" xxep__chr01 label "Voucher".
        
    end.
    {mfreset.i}
end.