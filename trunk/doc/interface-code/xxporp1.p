{mfdtitle.i}
def var domain like xxpo_domain.
domain = global_domain.
def var ponbr1 like xxpo_nbr.
def var ponbr2 like xxpo_nbr.
def var poitem1 like xxpod_item.
def var poitem2 like xxpod_item.
def var porcid1 like xxpore_rcid.
def var porcid2 like xxpore_rcid.

form 
    ponbr1 colon 25
    ponbr2 colon 45  label {t001.i}
    poitem1 colon 25
    poitem2 colon 45 label {t001.i}
    with frame a width 80 side-label
    title "OA import PO Report".

repeat:
    if ponbr2 = hi_char then ponbr2 = "".
    if poitem2 = hi_char then poitem2 = "".
    update ponbr1 ponbr2
           poitem1 poitem2
           with frame a .
    if ponbr2 = "" then ponbr2 = hi_char.
    if poitem2 = "" then poitem2 = hi_char.
    
    {mfselbpr.i "terminal" 80}
    for each xxpo_mstr no-lock where xxpo_domain = domain 
                                 and xxpo_nbr >= ponbr1 and xxpo_nbr <= ponbr2,
        each xxpod_det no-lock where xxpod_domain = domain
                                 and xxpod_nbr = xxpo_nbr
                                 and xxpod_item >= poitem1 and xxpod_item <= poitem2
        break by xxpo_nbr with frame b width 300 down:
        if first-of(xxpo_nbr) then 
           disp xxpo_nbr xxpo_desc format "x(10)" 
                xxpo_supp xxpo_purcurr xxpo_amt xxpo_ponbr.
        disp xxpod_line xxpod_item xxpod_oatype xxpod_desc format "x(10)"
             xxpod_price xxpod_qty xxpod_amt .
        
    end.
    {mfreset.i}
end.