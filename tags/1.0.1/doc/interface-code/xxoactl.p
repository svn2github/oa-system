{mfdtitle.i}
def var domain   like xxoaif_domain.
def var exp-acc  like xxoaif_exp_acc .
def var AR-acc   like xxoaif_ar_acc.
def var incm-acc like xxoaif_incm_acc .
def var rc-acc   like xxoaif_rc_acc .
def var bt-acc   like xxoaif_bt_acc .
def var btp-acc  like xxoaif_btp_acc.
def var enty     like en_entity.
def var bk1      like bk_code.
def var bk2      like bk_code.
def var use-sub  as   logical label "Use Subaccount".
def var use-pct  as   logical label "Use Project Percent".
def var use-ap   as   logical label "Use AP module".
def var use-sac   as   logical label "Use Special Account".
def var supp     like vd_addr.

form
    domain   colon 25
    skip(1)
    exp-acc  colon 25 
    AR-acc   colon 25
    incm-acc colon 25
    rc-acc   colon 25
    bt-acc   colon 25
    btp-acc  colon 25
    enty     colon 25
    bk1      colon 25    
    bk2   label "Other Bank"
    skip(1)
    use-sub  colon 25
    use-pct  colon 25
    use-sac   colon 25
    use-ap   colon 25  
    supp     colon 25
    ad_name  no-label 
    with frame a side-labels width 80 
    title "OA Interface Control".
    
domain = global_domain.

REPEAT WITH FRAME a:

    disp domain .
    find xxoaif_ctrl where xxoaif_domain = domain no-error.
    if avail xxoaif_ctrl then 
       assign exp-acc  = xxoaif_exp_acc
              ar-acc   = xxoaif_ar_acc
              incm-acc = xxoaif_incm_acc
              rc-acc   = xxoaif_rc_acc
              bt-acc   = xxoaif_bt_acc
              btp-acc  = xxoaif_btp_acc
              enty     = xxoaif__chr[2]
              bk1      = xxoaif__chr[3]
              bk2      = xxoaif__chr[4].
              
    update exp-acc validate(can-find (first ac_mstr where ac_domain = domain and ac_code = exp-acc) or exp-acc = "", "Error Account!")
           ar-acc validate(can-find (first ac_mstr where ac_domain = domain and ac_code = ar-acc) or ar-acc = "" , "Error Account!")
           incm-acc validate(can-find (first ac_mstr where ac_domain = domain and ac_code = incm-acc) or incm-acc = "", "Error Account!")
           rc-acc validate(can-find (first ac_mstr where ac_domain = domain and ac_code = rc-acc) or rc-acc = "", "Error Account!")
           bt-acc validate(can-find (first ac_mstr where ac_domain = domain and ac_code = bt-acc) or bt-acc = "", "Error Account!")
           btp-acc validate(can-find (first ac_mstr where ac_domain = domain and ac_code = btp-acc) or btp-acc = "", "Error Account!")
           enty validate(can-find (first en_mstr where en_domain = domain and en_entity = enty ), "Error Entity")
           bk1  validate(can-find(first bk_mstr where bk_domain = domain and bk_code = bk1), "Error Bank")
           bk2  validate(can-find(first bk_mstr where bk_domain = domain and bk_code = bk2), "Error Bank").

    bcdparm = "".
    {mfquoter.i exp-acc }
    {mfquoter.i ar-acc }
    {mfquoter.i incm-acc }
    {mfquoter.i rc-acc }
    {mfquoter.i bt-acc }
    {mfquoter.i btp-acc }
    {mfquoter.i enty }
    {mfquoter.i bk1 }
    {mfquoter.i bk2 }
    
    find xxoaif_ctrl where xxoaif_domain = domain no-error.
    if not avail xxoaif_ctrl then do:
       create xxoaif_ctrl.
       assign xxoaif_domain = domain.
    end.
    
    assign xxoaif_exp_acc  = exp-acc
           xxoaif_ar_acc   = ar-acc
           xxoaif_incm_acc = incm-acc
           xxoaif_rc_acc   = rc-acc
           xxoaif_bt_acc   = bt-acc
           xxoaif_btp_acc = btp-acc
           xxoaif__chr[2] = enty
           xxoaif__chr[3] = bk1
           xxoaif__chr[4] = bk2.
    use-sub = xxoaif__log[1].
    use-pct = xxoaif__log[2].
    use-ap  = xxoaif__log[3].
    use-sac = xxoaif__log[4].
    supp    = xxoaif__chr[1].
    if use-ap and supp <> "" then do:
       find first ad_mstr no-lock where ad_domain = domain and ad_addr = supp no-error.
       if avail ad_mstr then disp supp ad_name with frame a.
    end.
    release xxoaif_ctrl.
    /*message "Update Data!".*/
    
    update use-sub use-pct use-sac use-ap with frame a .
    if use-ap then do:
       update supp validate(can-find (first vd_mstr no-lock where vd_domain = domain and vd_addr = supp), "Invalidate Supplier!") with frame a.
       find first ad_mstr no-lock where ad_domain = domain and ad_addr = supp no-error.
       if avail ad_mstr then disp ad_name with frame a.
    end.
    else  supp = "".
    find xxoaif_ctrl where xxoaif_domain = domain no-error.
    assign xxoaif__log[1] = use-sub
           xxoaif__log[2] = use-pct
           xxoaif__log[3] = use-ap
           xxoaif__log[4] = use-sac
           xxoaif__chr[1] = supp.
    
end.

