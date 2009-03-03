{mfdtitle.i}
def var domain like global_domain.
def var ponbr1 like xxep_nbr.
def var ponbr2 like xxep_nbr.
def var dt1    as   date label "Date".
def var dt2    as   date.
def var cust   as   char label "Customer".
def var yy     as inte label "Year".
def var mm     as inte label "Month".
def var amt    as deci label "Amt".

form 
     ponbr1 colon 20 ponbr2 colon 45 label {t001.i}
     dt1    colon 20 dt2 colon 45 label {t001.i}
     with frame a width 80 side-label.

form 
    yy
    mm 
    cust 
    amt 
    with frame b width 80 down.
    
domain = global_domain.
repeat:
    
    if ponbr2 = hi_Char then ponbr2 = "".
    if dt1 = low_date then dt1 = ?.
    if dt2 = hi_date then dt2 = ?.
    update ponbr1 ponbr2 dt1 dt2 with frame a.
    if ponbr2 = "" then ponbr2 = hi_char.
    if dt1 = ? then dt1 = low_date.
    if dt2 = ? then dt2 = hi_date.
    
    for each xxep_mstr exclusive-lock where xxep_domain = domain
                                       and xxep_nbr >= ponbr1 and xxep_nbr <= ponbr2
                                       and xxep_crt_dt >= dt1 and xxep_crt_dt <= dt2,
        each xxepre_det exclusive-lock where xxepre_domain = domain
                                        and xxepre_nbr = xxep_nbr
                                        and xxepre_cscd <> ""
        break by year(xxep_crt_dt) by month(xxep_crt_dt) by xxepre_cscd:
        accumulate xxepre_amt (total by year(xxep_crt_dt) by month(xxep_crt_dt) by xxepre_cscd).
        if last-of(xxepre_cscd) then do:
           disp year(xxep_crt_dt) @ yy
                month(xxep_crt_dt) @ mm
                xxepre_cscd @ cust
                accumu total by xxepre_cscd xxepre_amt @ amt
                with frame b.
           down with frame b.
        end.
        if last-of (month(xxep_crt_dt)) then do:
           disp year(xxep_crt_dt) @ yy
                month(xxep_crt_dt) @ mm
                "BT Tax" @ cust
                (accumu total by month(xxep_crt_dt) xxepre_amt) / 0.9 * 0.05 @ amt
                with frame b.
           down with frame b.
        end.
    end. /*for each xxepmstr*/
end.
