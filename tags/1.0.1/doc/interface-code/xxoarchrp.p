{mfdtitle.i}
def var domain like global_domain.
def var ponbr1 like xxpo_nbr.
def var ponbr2 like xxpo_nbr.
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
    
    for each xxpo_mstr exclusive-lock where xxpo_domain = domain
                                       and xxpo_nbr >= ponbr1 and xxpo_nbr <= ponbr2
                                       and xxpo_crt_date >= dt1 and xxpo_crt_date <= dt2,
        each xxpore_det exclusive-lock where xxpore_domain = domain
                                        and xxpore_nbr = xxpo_nbr
                                        and xxpore_cscd <> ""
        break by year(xxpo_crt_date) by month(xxpo_crt_date) by xxpore_cscd:
        accumulate xxpore_amt (total by year(xxpo_crt_date) by month(xxpo_crt_date) by xxpore_cscd).
        if last-of(xxpore_cscd) then do:
           disp year(xxpo_crt_date) @ yy
                month(xxpo_crt_date) @ mm
                xxpore_cscd @ cust
                accumu total by xxpore_cscd xxpore_amt @ amt
                with frame b.
           down with frame b.
        end.
        if last-of (month(xxpo_crt_date)) then do:
           disp year(xxpo_crt_date) @ yy
                month(xxpo_crt_date) @ mm
                "BT Tax" @ cust
                (accumu total by month(xxpo_crt_date) xxpore_amt) / 0.9 * 0.05 @ amt
                with frame b.
           down with frame b.
        end.
    end. /*for each xxpomstr*/
end.
