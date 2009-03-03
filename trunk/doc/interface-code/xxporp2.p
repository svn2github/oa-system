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
    porcid1 colon 25
    porcid2 colon 45 label {t001.i}
    with frame a width 80 side-label
    title "OA import PO Recharge Report".

repeat:
    if ponbr2 = hi_char then ponbr2 = "".
    if poitem2 = hi_char then poitem2 = "".
    if porcid2 = hi_char then porcid2 = "".
    update ponbr1 ponbr2
           poitem1 poitem2
           porcid1 porcid2
           with frame a .
    if ponbr2 = "" then ponbr2 = hi_char.
    if poitem2 = "" then poitem2 = hi_char.
    if porcid2 = "" then porcid2 = hi_char.
    
    {mfselbpr.i "terminal" 80}
    for each xxpo_mstr no-lock where xxpo_domain = domain 
                                 and xxpo_nbr >= ponbr1 and xxpo_nbr <= ponbr2,
        each xxpod_det no-lock where xxpod_domain = domain
                                 and xxpod_nbr = xxpo_nbr
                                 and xxpod_item >= poitem1 and xxpod_item <= poitem2,
        each xxpore_det no-lock where xxpore_domain = domain
                                  and xxpore_nbr = xxpo_nbr
                                  and xxpore_rcid = xxpod_item
                                  and xxpore_rcid >= porcid1 and xxpore_rcid <= porcid2
        break by xxpo_nbr by xxpod_item with frame b width 300 down:
        if first-of(xxpo_nbr) then 
           disp xxpo_nbr xxpo_supp xxpo_purcurr xxpo_amt .
        if first-of(xxpod_item) then
           disp xxpod_desc format "x(10)" xxpod_oatype xxpod_price xxpod_qty xxpod_amt .
        disp xxpore_rcty xxpore_cscd format "x(17)" xxpore_encd xxpore_dpcd xxpore_psid xxpore_amt xxpore_pct .
        down with frame b.
    end.
    {mfreset.i}
end.