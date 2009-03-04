/* 270408  *J001*/
/*J001 Add raw */
{mfdtitle.i}

def var docnbr1 like xxep_nbr LABEL "Expense Nbr".
def var docnbr2 like docnbr1.
def var date1   like xxep_crt_dt.
def var date2   like date1.
def var effdate as   DATE LABEL "GL effective date".
def var dr-acc as char.
def var dr-sub as char.
def var dr-cc as char.
def var cr-acc as char.
def var cr-sub as char.
def var cr-cc as char.
def var basecurr as char.
def var curr-yn  as logical.
def var project as char.
def var cust as char .
def var bk   like bk_code.
def var desc1 as char format "x(24)".
def var sp-acc as logical.
find first gl_ctrl no-lock where gl_domain = global_domain no-error.
basecurr = gl_base_curr.

def var impt_name as char format "x(70)" .
def var cim-file like impt_name.
DEF VAR quote AS CHAR INIT '"'.
def var m_data as char format "x(70)".
def var m_prg as char format "x(10)".

DEF VAR in-file AS CHAR FORMAT "x(40)" LABEL "Import file name".
DEF VAR in-path AS CHAR FORMAT "x(40)" LABEL "Import path".
DEF VAR bakpath AS CHAR FORMAT "x(40)" LABEL "Backup path".
DEF VAR errpath AS CHAR FORMAT "x(40)" LABEL "Error path".

    FOR FIRST CODE_mstr NO-LOCK 
        WHERE code_domain = global_domain
          and CODE_fldname = "path" 
          AND CODE_value   = "Import path"
          :
        in-path = CODE_cmmt.
    END.
    FOR FIRST CODE_mstr NO-LOCK 
        WHERE code_domain = global_domain
          and CODE_fldname = "path" 
          AND CODE_value   = "Backup path"
          :
        bakpath = CODE_cmmt.
    END.
    FOR FIRST CODE_mstr NO-LOCK 
        WHERE code_domain = global_domain
          and CODE_fldname = "path" 
          AND CODE_value   = "Error path"
          :
        errpath = CODE_cmmt.
    END.

    IF in-path = "" OR bakpath = "" OR errpath = "" THEN DO:
        MESSAGE "Please define the path in path maintenance menu first!".
        UNDO,RETRY.
    END.

form
    docnbr1 colon 25
    docnbr2 colon 45 label {t001.i}
    date1   colon 25
    date2   colon 45 label {t001.i}
    skip(1)
    effdate colon 25
  	skip(1) 
    with frame a side-labels width 80 
    title "OA Expense Post to AP".

   find xxoaif_ctrl no-lock where xxoaif_domain = global_domain no-error.
   cust = xxoaif__chr[1].
repeat:
    if not xxoaif__log[3] then do:
       Message "You couldn't use AP module!".
       leave.
    end.
        if substring(bakpath,length(bakpath),1) <> "/"
       then bakpath = bakpath + "/".

    impt_name = /*bakpath +*/ global_userid + "OAExpToAp".
    effdate = today.
    if docnbr2 = hi_char then docnbr2 = "".
    if date1 = low_date then date1 = ?.
    if date2 = hi_date then date2 = ?.
    update docnbr1 docnbr2
           date1   date2
           effdate
        /*   prtm-yn*/
           with frame a.
    if docnbr2 = "" then docnbr2 = hi_char.
    if date1 = ? then date1 = low_date.
    if date2 = ? then date2 = hi_date.
    
    bcdparm = "".
    {mfquoter.i docnbr1 }
    {mfquoter.i docnbr2 }
    {mfquoter.i date1 }
    {mfquoter.i date2 }
    {mfquoter.i effdate }

    curr-yn = no.
    find first xxep_mstr no-lock where xxep_domain = global_domain
                                   and xxep_nbr >= docnbr1 and xxep_nbr <= docnbr2
                                   and xxep_crt_dt >= date1 and xxep_crt_dt <= date2 
                                   and xxep_curr <> basecurr and not xxep__log01 no-error.
    if avail xxep_mstr then do:
       message "Need Exchange Rate! Do you want to continue?" update curr-yn.
       if not curr-yn then undo,retry.
    end.
    message "Create GL Transaction!". 

    cim-file = impt_name + ".cim".
    output to value(cim-file).
    for each xxep_mstr no-lock where xxep_domain = global_domain
                                        and xxep_nbr >= docnbr1 and xxep_nbr <= docnbr2
                                        and xxep_crt_dt >= date1 and xxep_crt_dt <= date2
                                        and not xxep__log01
                                        :
	     if xxep_curr = "usd" then bk = xxoaif__chr[4].
	     else bk = xxoaif__chr[3].
	     desc1 = substring(xxep_desc,1,20,"raw").
	     PUT UNFORMATTED '@@batchload apvomt.p'  skip
							      "-" space skip
							      "-" space skip
							      "-" space skip
							      "." SKIP
							      "-" space
							      quote cust quote space
							      quote effdate quote space 
							      "-" space skip
							      quote xxep_curr quote space
							      quote bk quote space
							      quote xxep_nbr quote space 
							      "-" space   /*Date*/
							      "-" space   /*Terms*/
							      "-" space   /*Disc Date*/
							      "-" space   /*Due Date*/
							      "-" space   /*Expected*/
							      "-" space   /*account*/
							      "-" space   /*sub*/
							      "-" space   /*cc*/
							      "-" space   /*Dis acc*/
							      "-" space   /*sub*/
							      "-" space   /*cc*/
							      quote xxoaif__chr[2] quote space
							      quote xxep_nbr quote space
							      "-" space  skip.
			 if xxep_curr <> "cny" then do:
			    PUT UNFORMATTED "-" space skip.
			 end.
			 PUT UNFORMATTED 
							      "-" space skip
							      "-" space 
							      quote "CHN" quote space skip.
        for each xxepd_det no-lock where xxepd_domain = global_domain
                                        and xxepd_nbr = xxep_nbr
                                        and xxepd_flag = "0"
                                        and xxepd__chr01 = "":
           dr-acc = "".
           dr-sub = "".
           dr-cc = "".
           cr-acc = "".
           cr-sub = "".
           cr-cc = "".
           find first xxmap_mstr no-lock where xxmap_domain = global_domain
                                           and xxmap_type = xxepd_type no-error.
           if avail xxmap_mstr then do:
              dr-acc = xxmap_dr_acc.
              dr-sub = xxmap_dr_sub.
              dr-cc = xxmap_dr_cc.
              cr-acc = xxmap_cr_acc.
              cr-sub = xxmap_cr_sub.
              cr-cc = xxmap_cr_cc.
           end.
           else do:
              dr-acc = "".
              dr-sub = "".
              dr-cc = "".
              cr-acc = "".
              cr-sub = "".
              cr-cc = "".
           end.
           if cr-acc = "" then cr-acc = xxoaif_exp_acc.
           project = "".
           if xxepd_psid <> "" then do:
              find first pj_mstr no-lock where pj_domain = global_domain
                                           and pj__qadc01 = xxepd_psid no-error.
              if avail pj_mstr then do:
                  project = pj_project.
                  find first cr_mstr no-lock where cr_domain = global_domain 
                                              and cr_code = pj_project
                                              and cr_type = "PJ_CC"
                                              no-error.
                 if avail cr_mstr then do:
                    if dr-cc = "" then dr-cc = cr_code_beg.
                    if cr-cc = "" then cr-cc = cr_code_beg.
                 end.
              end.
           end.
           if project = "" and xxep_crt_id <> "" then do:
              find first pj_mstr no-lock where pj_domain = global_domain
                                           and pj__qadc01 = xxep_crt_id no-error.
              if avail pj_mstr then do:
                 project = pj_project.
                  find first cr_mstr no-lock where cr_domain = global_domain 
                                              and cr_code = pj_project
                                              and cr_type = "PJ_CC"
                                              no-error.
                 if avail cr_mstr then do:
                    if dr-cc = "" then dr-cc = cr_code_beg.
                    if cr-cc = "" then cr-cc = cr_code_beg.
                 end.
              end.
           end.
	         if dr-cc = "" and xxepd_dpid <> "" then do:
	            find xxdept_mstr no-lock where xxdept_domain = global_domain and xxdept_dept = xxepd_dpid no-error.
	            if avail xxdept_mstr then dr-cc = xxdept__chr01.
	            else dr-cc = "".
	         end.
	         if dr-cc = "" and xxep_depid <> "" then do:
	            find first xxdept_mstr no-lock where xxdept_domain = global_domain
	                                         and xxdept_dept = xxep_depid no-error.
	            if avail xxdept_mstr then do:
	               if dr-cc = "" then dr-cc = xxep__chr01.
	               if cr-cc = "" then cr-cc = xxep__chr01.
	            end.
	         end.
	         /*xiami070214*/
	         if xxepd_dpid <> "" then do:
	         	  find first xxdept_mstr no-lock where xxdept_domain = global_domain
	                                         and xxdept_dept = xxepd_dpid no-error.
	            if avail xxdept_mstr then do:
	               if xxdept__chr01 <> "" and xxdept__chr01 <> dr-cc then dr-cc = xxdept__chr01.
	            end.
	         end.
           /*xiami070214*/
         if xxepd__chr03 <> "" then do:
            if substring(xxepd__chr03,1,8,"raw") <> "" then dr-acc = substring(xxepd__chr03,1,8,"raw").
            if substring(xxepd__chr03,9,8,"raw") <> "" then dr-sub = substring(xxepd__chr03,9,8,"raw").
            if substring(xxepd__chr03,17,8,"raw") <> "" then dr-cc = substring(xxepd__chr03,17,8,"raw").
         end.
         if xxepd__chr04 <> "" then do:
            if substring(xxepd__chr04,1,8,"raw") <> "" then cr-acc = substring(xxepd__chr04,1,8,"raw").
         end.
         
         sp-acc = no. /*xiami070215*/
         if xxoaif__log[4] then do:
            find first ac_mstr no-lock where ac_domain = global_domain 
                                         and ac_code = dr-acc 
                                         and ac__log01 no-error.
            if avail ac_mstr and ac__log01 then do:
               dr-sub = "".
               dr-cc = "".
               project = "".
               sp-acc = yes.  /*xiami070215*/
            end.
         end. 
         
         find first xxpc_mstr no-lock where xxpc_domain = global_domain
                                           and xxpc_project = project
                                           and xxpc_year = year(effdate)
                                           and xxpc_month = month(effdate) no-error.
         if xxoaif__log[2] and xxepd_psid <> "" and avail xxpc_mstr and not sp-acc /*xiami070215*/ then do:
            def var totamt as deci.
            def var totamt2 as deci.
            def var subamt as deci.
            totamt = xxepd_cf_amt.
            totamt2 = 0.
              for each xxpc_mstr no-lock where xxpc_domain = global_domain
                                           and xxpc_project = project
                                           and xxpc_year = year(effdate)
                                           and xxpc_month = month(effdate)
                                           break by xxpc_project:
                 
                 subamt = round(xxepd_cf_amt * xxpc_pct / 100,2).
                 if last-of(xxpc_project) then do:
                    subamt = totamt - totamt2.
                 end.
                 totamt2 = totamt2 + subamt.
                 /*if totamt2 > totamt then subamt = subamt - (totamt2 - totamt).*/
             PUT UNFORMATTED "-" space skip
                             quote dr-acc quote space
                             quote dr-sub quote space
                             quote xxpc_cc  quote space
                             quote project quote space 
                             quote xxoaif__chr[2] quote space skip
                             quote trim(substring(xxepd_desc,1,22,"raw")) quote space skip
                             quote subamt  quote space skip
                             .

              end.
         end.
         else do:
             PUT UNFORMATTED "-" space skip
                             quote dr-acc quote space
                             quote dr-sub quote space
                             quote dr-cc  quote space
                             quote project quote space 
                             quote xxoaif__chr[2] quote space skip
                             quote trim(substring(xxepd_desc,1,22,"raw")) quote space skip
                             quote xxepd_cf_amt  quote space skip
                             .
         
         end.  /*end if xxoaif__log[2] and xxepd_psid <> ""*/
       end.  /*end for each xxepd_det*/
        for each xxepre_det exclusive-lock where xxepre_domain = global_domain
                                        and xxepre_nbr = xxep_nbr
                                        and xxepre_retype = "1"
/*J001*/                                /*and xxepre__chr01 = "" */ :
           dr-acc = "".
           dr-sub = "".
           dr-cc = "".
           cr-acc = "".
           cr-sub = "".
           cr-cc = "".
           find first xxmap_mstr no-lock where xxmap_domain = global_domain
                                           and xxmap_type = xxepre__chr02 no-error.
           if avail xxmap_mstr then do:
              dr-acc = xxmap_dr_acc.
              dr-sub = xxmap_dr_sub.
              dr-cc = xxmap_dr_cc.
              cr-acc = xxmap_cr_acc.
              cr-sub = xxmap_cr_sub.
              cr-cc = xxmap_cr_cc.
           end.
           else do:
              dr-acc = "".
              dr-sub = "".
              dr-cc = "".
              cr-acc = "".
              cr-sub = "".
              cr-cc = "".
           end.
           if cr-acc = "" then cr-acc = xxoaif_exp_acc.
           project = "".
           if xxep_crt_id <> "" then do:
              find first pj_mstr no-lock where pj_domain = global_domain
                                           and pj__qadc01 = xxep_crt_id no-error.
              if avail pj_mstr then do:
                 project = pj_project.
                  find first cr_mstr no-lock where cr_domain = global_domain 
                                              and cr_code = pj_project
                                              and cr_type = "PJ_CC"
                                              no-error.
                 if avail cr_mstr then do:
                    if dr-cc = "" then dr-cc = cr_code_beg.
                    if cr-cc = "" then cr-cc = cr_code_beg.
                 end.
              end.
           end.
	         if xxep_depid <> "" then do:
	            find first xxdept_mstr no-lock where xxdept_domain = global_domain
	                                         and xxdept_dept = xxep_depid no-error.
	            if avail xxdept_mstr then do:
	               if dr-cc = "" then dr-cc = xxep__chr01.
	               if cr-cc = "" then cr-cc = xxep__chr01.
	            end.
	         end.
	         /*
	         if xxepd_dpid <> "" then do:
	            find xxdept_mstr no-lock where xxdept_domain = global_domain and xxdept_dept = xxepd_dpid no-error.
	            if avail xxdept_mstr then dr-cc = xxdept__chr01.
	            else dr-cc = "".
	         end.
	         */
	         /*xiami070214*/
	         if xxepre_dpcd <> "" then do:
	         	  find first xxdept_mstr no-lock where xxdept_domain = global_domain
	                                         and xxdept_dept = xxepre_dpcd no-error.
	            if avail xxdept_mstr then do:
	               if xxdept__chr01 <> "" and xxdept__chr01 <> dr-cc then dr-cc = xxdept__chr01.
	            end.
	         end.
           /*xiami070214*/
           
           if index(xxepre_cscd,"-") > 0 then do:
              dr-cc = substring(xxepre_cscd,1,index(xxepre_cscd,"-") - 1,"raw").
              dr-sub = substring(xxepre_cscd,index(xxepre_cscd,"-") + 1 ,length(xxepre_cscd),"raw").
           end.
           else if xxepre_cscd <> "" then do:
              dr-cc = xxepre_cscd.
           end.
         if xxepre__chr03 <> "" then do:
            if substring(xxepre__chr03,1,8,"raw") <> "" then dr-acc = substring(xxepre__chr03,1,8,"raw").
            if substring(xxepre__chr03,9,8,"raw") <> "" then dr-sub = substring(xxepre__chr03,9,8,"raw").
            if substring(xxepre__chr03,17,8,"raw") <> "" then dr-cc = substring(xxepre__chr03,17,8,"raw").
         end.
         if xxepre__chr04 <> "" then do:
            if substring(xxepre__chr04,1,8,"raw") <> "" then cr-acc = substring(xxepre__chr04,1,8,"raw").
         end.
         
         if xxoaif__log[4] then do:
            find first ac_mstr no-lock where ac_domain = global_domain 
                                         and ac_code = dr-acc 
                                         and ac__log01 no-error.
            if avail ac_mstr and ac__log01 then do:
               dr-sub = "".
               dr-cc = "".
               project = "".
            end.
         end. 
         
             PUT UNFORMATTED "-" space skip
                             quote dr-acc quote space
                             quote dr-sub quote space
                             quote dr-cc  quote space
                             quote project quote space 
                             quote xxoaif__chr[2] quote space skip
                             /*quote xxep_desc quote space skip xiami070215*/
                             quote xxepre__chr01 quote space skip  /*xiami070215*/
                             quote xxepre_amt  quote space skip
                             .
         
       end.  /*end for each xxepre_det*/

       PUT UNFORMATTED "." SKIP
                       "-" SKIP
                       "-" SKIP
                       "." SKIP
                       "." SKIP
                       '@@end'  skip .	
    end. /*end for each xxep_mstr*/
    output close.
    
    do transaction on error undo,leave:

		   {xxcim.i "impt_name"}
						  
	  end.              
    for each xxep_mstr exclusive-lock where xxep_domain = global_domain
                                        and xxep_nbr >= docnbr1 and xxep_nbr <= docnbr2
                                        and xxep_crt_dt >= date1 and xxep_crt_dt <= date2
                                        and not xxep__log01 :
        find first vo_mstr no-lock where vo_invoice = xxep_nbr no-error.
        if avail vo_mstr then do:
                              xxep__log01 = yes.
                              xxep__chr01 = vo_ref.
        end.
    end.
end.