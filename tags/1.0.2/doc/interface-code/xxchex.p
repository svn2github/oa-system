/*xxepd__chr03  Dr-acc Dr-sub Dr-cc*/
/*xxepd__chr04  Cr-acc*/
/*xxepre__chr03  Dr-acc Dr-sub Dr-cc*/
/*xxepre__chr04  Cr-acc*/

{mfdtitle.i}
def var domain like global_domain.
def var ponbr like xxep_nbr.
def var ln as inte.
def var rcln as inte label "Line". 
def var tot like xxepd_amt.
def var curtot like xxepd_amt.
def var rcid like xxepre_reid.
def var rcty like xxepre_retype.
def var cscd like xxepre_cscd.
def var amt  like xxepre_amt.
def var pct like xxepre_pct.

def var dr-acc as char.
def var dr-sub as char.
def var dr-cc as char.
def var cr-acc as char.

def temp-table tmp-ep
    field tmp-ln as inte label "Line" format ">>>9"
    field tmp-nbr  like xxep_nbr
    field tmp-desc like xxep_desc format "x(10)"
    field tmp-flag like xxepd_flag
    field tmp-edln like xxepd_edln
    field tmp-type like xxepd_type
    field tmp-amt  like xxepd_cf_amt column-label "Amount"
    field tmp-dacc as char label "Dr Acc"
    field tmp-dsub as char label "Dr Sub"
    field tmp-dcc  as char label "Dr CC"
    field tmp-cacc as char label "Cr Acc"
    index tmp-idx01 tmp-ln.

form 
    ponbr label "OA Exp nbr"
    xxep_desc no-label
    xxep_curr label "Curr" format "x(3)"
    xxep_crt_id label "Person"
    xxep_depid format "x(5)"
    xxep_amt label "Amount"
    xxep_crt_dt label "Date"
    with frame a width 80 side-label .
    
/*     
form 
    xxepd_edln label "Line" format ">>9"
    xxepd_desc format "x(10)"
    xxepd_type label "Type"
    /*xxepd_flag label "Flag"*/
    xxepd_cf_amt
    /*xxepd_buyfor*/
    with frame b width 90 4 down stream-io.
    */

form 
    tmp-ln
    tmp-desc
    tmp-amt
    tmp-dacc
    tmp-dsub
    tmp-dcc
    tmp-cacc
    with frame b width 90 6 down .
    
/*form 
    rcln 
    tot label "ToT"
    curtot  label "Rchg Tot"
    with frame c width 80 side-label.
    */
form 
    rcln
    skip
    tmp-dacc
    tmp-dsub
    tmp-dcc
    tmp-cacc
    with frame c width 80 side-label.
    
form
    rcln
    rcid
    rcty
    cscd
    amt
  /*  pct*/
    with frame d width 80 side-label.
    
domain = global_domain.

find xxoaif_ctrl where xxoaif_domain = global_domain no-error.

repeat:
    for each tmp-ep:
        delete tmp-ep.
    end.
    clear frame b all.
    clear frame c all.
    recno = ?.
    prompt-for ponbr  WITH FRAME a EDITING :

        /* FIND NEXT/PREVIOUS RECORD */
        {mfnp01.i xxep_mstr ponbr  xxep_nbr  domain  xxep_domain
                xxep_idx01}

        if recno <> ? then do:
           clear frame b.
           hide frame b.
           disp xxep_nbr @ ponbr
                /*xxep_desc     */
                xxep_curr 
                xxep_depid
                xxep_amt 
                xxep_crt_id
                xxep_crt_dt
                with frame a.
        end.
    end. /*prompt-for*/
    find xxep_mstr no-lock where xxep_nbr = input ponbr and xxep_domain = domain no-error.
    if not avail xxep_mstr then do:
       message "Non-valiable OA PO Nbr.".
       undo,retry.
    end.
	   disp xxep_nbr @ ponbr
	        /*xxep_desc     */
	        xxep_curr 
	        xxep_depid
	        xxep_amt 
	        xxep_crt_id
	        xxep_crt_dt
	        with frame a.
	  ln = 0.
    for each xxepd_det no-lock where xxepd_domain = domain
                                        and xxepd_nbr = xxep_nbr
                                        and xxepd_flag = "0":        
	       find first xxmap_mstr no-lock where xxmap_domain = global_domain
	                                       and xxmap_type = xxepd_type no-error.
	       ln = ln + 1.
	       if avail xxmap_mstr then do:
	          dr-acc = xxmap_dr_acc.
	          dr-cc = xxmap_dr_cc.
	          dr-sub = xxmap_dr_sub.
	          cr-acc = xxmap_cr_acc.
	       end.
	       else do:
	          dr-acc = "".
	          dr-sub = "".
	          dr-cc = "".
	          cr-acc = "".
	       end.
	       if cr-acc = "" then cr-acc = xxoaif_exp_acc.
         if xxepd_dpid <> "" then do:
	          find xxdept_mstr no-lock where xxdept_domain = global_domain and xxdept_dept = xxepd_dpid no-error.
	          if avail xxdept_mstr then dr-cc = xxdept__chr01.
	          else dr-cc = "".
	       end.
         if xxepd__chr03 <> "" then do:
            if substring(xxepd__chr03,1,8) <> "" then dr-acc = substring(xxepd__chr03,1,8).
            if substring(xxepd__chr03,9,8) <> "" then dr-sub = substring(xxepd__chr03,9,8).
            if substring(xxepd__chr03,17,8) <> "" then dr-cc = substring(xxepd__chr03,17,8).
         end.
         if xxepd__chr04 <> "" then do:
            if substring(xxepd__chr04,1,8) <> "" then cr-acc = substring(xxepd__chr04,1,8).
         end.
	       create tmp-ep.
	       assign tmp-ln = ln 
	              tmp-nbr  = xxepd_nbr
	              tmp-desc = xxepd_desc
	              tmp-flag = xxepd_flag
	              tmp-edln = xxepd_edln
	              tmp-type = xxepd_type
	              tmp-amt  = xxepd_cf_amt
	              tmp-dacc = dr-acc
	              tmp-dsub = dr-sub
	              tmp-dcc  = dr-cc
	              tmp-cacc = cr-acc
	              .
    end.
    for each xxepre_det no-lock where xxepre_domain = domain
                                        and xxepre_nbr = xxep_nbr
                                        :      
         ln = ln + 1.  
	       find first xxmap_mstr no-lock where xxmap_domain = global_domain
	                                       and xxmap_type = xxepre__chr02 no-error.
	       if avail xxmap_mstr then do:
	          dr-acc = xxmap_dr_acc.
	          dr-cc = xxmap_dr_cc.
	          dr-sub = xxmap_dr_sub.
	          cr-acc = xxmap_cr_acc.
	       end.
	       else do:
	          dr-acc = "".
	          dr-sub = "".
	          dr-cc = "".
	          cr-acc = "".
	       end.
	       if cr-acc = "" then cr-acc = xxoaif_exp_acc.
         if index(xxepre_cscd,"-") > 0 then do:
            dr-cc = substring(xxepre_cscd,1,index(xxepre_cscd,"-") - 1).
            dr-sub = substring(xxepre_cscd,index(xxepre_cscd,"-") + 1 ,length(xxepre_cscd)).
         end.
         else do:
            dr-sub = "".
            dr-cc = xxepre_cscd.
         end.
         if xxepre__chr03 <> "" then do:
            if substring(xxepre__chr03,1,8) <> "" then dr-acc = substring(xxepre__chr03,1,8).
            if substring(xxepre__chr03,9,8) <> "" then dr-sub = substring(xxepre__chr03,9,8).
            if substring(xxepre__chr03,17,8) <> "" then dr-cc = substring(xxepre__chr03,17,8).
         end.
         if xxepre__chr04 <> "" then do:
            if substring(xxepre__chr04,1,8) <> "" then cr-acc = substring(xxepre__chr04,1,8).
         end.
	       create tmp-ep.
	       assign tmp-ln = ln
	              tmp-nbr  = xxepre_nbr
	              tmp-desc = /*xxepre_desc*/ "Recharge"
	              tmp-flag = xxepre_retype
	              tmp-edln = xxepre_erln
	              tmp-type = xxepre__chr02
	              tmp-amt  = xxepre_amt
	              tmp-dacc = dr-acc
	              tmp-dsub = dr-sub
	              tmp-dcc  = dr-cc
	              tmp-cacc = cr-acc
	              .
	              /*
        disp xxepd_edln
             xxepd_desc
             xxepd_type
             /*xxepd_flag*/
             xxepd_cf_amt
             dr-acc
             dr-sub
             dr-cc
             cr-acc
             /*xxepd_buyfor*/
             with frame b down.
        down with frame b.
        */
    end.
    for each tmp-ep no-lock:
        disp tmp-ln
					   tmp-desc
					   tmp-amt
					   tmp-dacc
					   tmp-dsub
					   tmp-dcc
					   tmp-cacc
					    with frame b down.
				down with frame b.
    end.
    repeat : 
        update rcln with frame c.
        /*
        find xxepd_det no-lock where xxepd_domain = domain
                                 and xxepd_nbr = xxep_nbr
                                 and xxepd_edln = ln no-error.
        if not avail xxepd_det then do:
           message "Non-valiable OA PO Detail Data".
           undo,retry.
        end.
        */
        find first tmp-ep where tmp-ln = rcln no-error.
        if not avail tmp-ep then do:
           message "Non-valiable OA Expense Detail Data".
           undo,retry.
        end.
        update tmp-dacc tmp-dsub tmp-dcc tmp-cacc with frame c.
        if tmp-dacc <> "" then do:
           find first ac_mstr no-lock where ac_domain = domain
                                        and ac_code = tmp-dacc no-error.
           if not avail ac_mstr then do:
              message "Error Dr Account Code , please re-enter!".
              undo,retry.
           end.
        end.
        if tmp-dsub <> "" then do:
           find first sb_mstr no-lock where sb_domain = domain
                                        and sb_sub    = tmp-dsub no-error.
           if not avail sb_mstr then do:
              message "Error Subaccount Code , please re-enter!".
              undo,retry.
           end.
           
        end.
        if tmp-dcc <> "" then do:
           find first cc_mstr no-lock where cc_domain = domain
                                        and cc_ctr = tmp-dcc no-error.
           if not avail cc_mstr then do:
              message "Error Cost Center Code , please re-enter!".
              undo,retry.
           end.
        end.
        if tmp-cacc <> "" then do:
           find first ac_mstr no-lock where ac_domain = domain
                                        and ac_code = tmp-cacc no-error.
           if not avail ac_mstr then do:
              message "Error Cr Account Code , please re-enter!".
              undo,retry.
           end.
        end.
        
    /*for each tmp-ep no-lock :*/
        if tmp-flag = "0" then do:
           find xxepd_det where xxepd_nbr = tmp-nbr 
                            and xxepd_edln = tmp-edln
                            no-error.
           if avail xxepd_det then do:
              substring(xxepd__chr03,1,8) = "".
              substring(xxepd__chr03,9,8) = "".
              substring(xxepd__chr03,17,8) = "".
              substring(xxepd__chr04,1,8) = "".

              substring(xxepd__chr03,1,8) = tmp-dacc.
              substring(xxepd__chr03,9,8) = tmp-dsub.
              substring(xxepd__chr03,17,8) = tmp-dcc.
              substring(xxepd__chr04,1,8) = tmp-cacc.
           end.
        end.
        if tmp-flag = "1" then do:
           find xxepre_det where xxepre_nbr = tmp-nbr 
                            and xxepre_erln = tmp-edln
                            no-error.
           if avail xxepre_det then do:
              substring(xxepre__chr03,1,8) = "".
              substring(xxepre__chr03,9,8) = "".
              substring(xxepre__chr03,17,8) = "".
              substring(xxepre__chr04,1,8) = "".
              substring(xxepre__chr03,1,8) = tmp-dacc.
              substring(xxepre__chr03,9,8) = tmp-dsub.
              substring(xxepre__chr03,17,8) = tmp-dcc.
              substring(xxepre__chr04,1,8) = tmp-cacc.
           end.
           
        end.
    /*end.*/
    end.
    
end.