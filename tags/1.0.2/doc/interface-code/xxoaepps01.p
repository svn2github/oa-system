/**/
{mfdtitle.i}
/*{cxcustom.i "GLTRMTM.P"}

/* EXTERNAL LABEL INCLUDE */
{gplabel.i}

{gldydef.i}
{gldynrm.i}
{gprunpdf.i "gpglvpl" "p"}
{gprunpdf.i "mcpl" "p"}
{gprunpdf.i "nrm" "p"} */
define variable type_parm as character.
define variable ref                   like glt_ref.
define variable l_glt_ref1            like glt_ref     no-undo.
define variable l_glt_ref2            like glt_ref     no-undo.
define variable co_daily_seq          like mfc_logical no-undo.
define variable cash_book             like mfc_logical.
define variable ctr_no      as   integer                     no-undo.
define variable jlnbr       as   integer                     no-undo.
define variable disp_curr like glt_curr    format "x(4)".
define variable disp_rndmthd    like rnd_rnd_mthd no-undo.
define variable mc-error-number like msg_nbr      no-undo.
define variable entity            like en_entity.

define variable pl        like co_pl.
define variable entities_balanced like co_enty_bal.
define variable allow_mod         like co_allow_mod.
define variable use_sub           like co_use_sub.
define variable use_cc            like co_use_cc.

def var basecurr as char.
def var curr-yn  as logical.
find first gl_ctrl no-lock where gl_domain = global_domain no-error.
basecurr = gl_base_curr.
def var project as char.
def var amt  as deci.

def var docnbr1 like xxep_nbr LABEL "Expense Nbr".
def var docnbr2 like docnbr1.
def var date1   like xxep_crt_dt.
def var date2   like date1.
def var effdate as   DATE LABEL "GL effective date".
/*def var prtm-yn as   LOGICAL LABEL "Print template report".*/

def var expcr-acc  as char.
def var rechg-yn   as logical .
def var rechg-acc  as char    .
def var rechg-cc   as char    .
def var pj-code    like pj_project.

def var dr-acc as char.
def var dr-sub as char.
def var dr-cc as char.
def var cr-acc as char.
def var cr-sub as char.
def var cr-cc as char.

def var gline as inte.
def var totamt like glt_amt.

expcr-acc = "0100".
rechg-acc = "0200".
rechg-cc  = "1111".

form
    docnbr1 colon 25
    docnbr2 colon 45 label {t001.i}
    date1   colon 25
    date2   colon 45 label {t001.i}
    skip(1)
    effdate colon 25
   /* prtm-yn colon 25*/
	skip(1) 
    with frame a side-labels width 80 
    title "OA Expense Post to GL".

def var exp-acc as char.
find last gl_ctrl no-lock where gl_domain = global_domain no-error.
exp-acc = gl_rcptx_acct.

cash_book = no.
type_parm = "OA".

for first gl_ctrl
   fields( gl_domain gl_rnd_mthd)
    where gl_ctrl.gl_domain = global_domain no-lock:
end. /* FOR FIRST gl_ctrl */

disp_curr = getTermLabel("BASE",4).

if not cash_book
then
   disp_rndmthd = gl_rnd_mthd.
else do:

   /* GET ROUNDING METHOD FROM CURRENCY MASTER */
   {gprunp.i "mcpl" "p" "mc-get-rnd-mthd"
      "(input  disp_curr,
        output disp_rndmthd,
        output mc-error-number)"}
   if mc-error-number <> 0
   then do:

      run p-disp-msg(input mc-error-number, input 3).
      pause 0.
      leave.
   end. /* IF mc-error-number <> 0 */

end.  /* ELSE DO */

{&GLTRMTM-P-TAG2}

/* GET P/L ACCOUNT CODE */
for first co_ctrl
   fields( co_domain co_allow_mod co_enty_bal co_pl co_use_cc co_use_sub)
    where co_ctrl.co_domain = global_domain no-lock:
end. /* FOR FIRST co_ctrl */

if not available co_ctrl
then do:

   /* CONTROL FILE MUST BE DEFINED BEFORE RUNNING REPORT */
   run p-disp-msg(input 3032, input 3).
   pause.
   leave.

end. /* IF NOT AVAILABLE co_ctrl */

assign
   pl                = co_pl
   use_cc            = co_use_cc
   use_sub           = co_use_sub
   entities_balanced = co_enty_bal
   allow_mod         = co_allow_mod.

release co_ctrl.

if not cash_book
then do:

   /* GET MFC_CTRL FIELD co_daily_seq, ADD IF NECESSARY */
   run get-co-daily-seq.

end. /* IF NOT cash_book */

find first en_mstr no-lock where en_domain = global_domain
                           and en_primary = yes 
                           no-error.
if avail en_mstr then entity = en_entity.
              /*      
find xx_mstr no-lock where xx_entity = entity no-error.
if avail xx_mstr then do:
   expcr-acc = xx_exp_acc.
   rechg-acc = xx_rec_acc.
   rechg-cc  = xx_rec_cc.
end.
       */
   find xxoaif_ctrl where xxoaif_domain = global_domain no-error.
repeat:
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
    /*{mfquoter.i prtm-yn }*/
    curr-yn = no.
    find first xxep_mstr no-lock where xxep_domain = global_domain
                                   and xxep_nbr >= docnbr1 and xxep_nbr <= docnbr2
                                   and xxep_crt_dt >= date1 and xxep_crt_dt <= date2 
                                   and xxep_curr <> basecurr no-error.
    if avail xxep_mstr then do:
       message "Need Exchange Rate! Do you want to continue?" update curr-yn.
       if not curr-yn then undo,retry.
    end.
    message "Create GL Transaction!". 
    
    for each xxep_mstr exclusive-lock where xxep_domain = global_domain
                                        and xxep_nbr >= docnbr1 and xxep_nbr <= docnbr2
                                        and xxep_crt_dt >= date1 and xxep_crt_dt <= date2
                                        :
         /*message xxep_nbr.*/
         gline = 1.
         totamt = 0.
         if not cash_book then do:
            /* GET REFERENCE NUMBER */
            if co_daily_seq then
               ref = type_parm + substring(string(year(today),"9999"),3,2)
                   + string(month(today),"99") + string(day(today),"99").
            else ref = type_parm.

            for last glt_det fields( glt_domain  
                      glt_acct    glt_addr       glt_amt        glt_batch
                      glt_cc      glt_correction glt_curr       glt_curr_amt
                      glt_date    glt_desc       glt_doc        glt_doc_type
                      glt_dy_code glt_dy_num     glt_ecur_amt   glt_effdate
                      glt_entity  glt_en_exrate  glt_en_exrate2 glt_en_exru_seq
                      glt_error   glt_exru_seq   glt_ex_rate    glt_ex_rate2
                      glt_line    glt_project    glt_ref        glt_ex_ratetype
                      glt_rflag   glt_sub        glt_tr_type    glt_unb
                      glt_user1   glt_user2      glt_userid)
                where glt_det.glt_domain = global_domain and  glt_ref >= ref
                  and glt_ref   <= ref + fill(hi_char,14) no-lock:
            end. /* FOR LAST glt_det */

            for last gltr_hist
               fields( gltr_domain gltr_desc gltr_eff_dt gltr_ent_dt gltr_line
                      gltr_ref gltr_tr_type gltr_user)
                where gltr_hist.gltr_domain = global_domain and  gltr_ref >= ref
               and gltr_ref   <= ref + fill(hi_char,14) no-lock:
            end. /* FOR LAST gltr_hist */

            /* IF DAILY */
            if co_daily_seq then do:
               assign
                  ref   = max(ref + string(0, "999999"),
                          max(if available glt_det
                              then glt_ref
                              else "",
                          if available gltr_hist
                          then gltr_ref
                          else ""))
                  jlnbr = 0.

               do on error undo, leave:
                  jlnbr = integer(substring(ref,9,6)).
               end. /* DO ON ERROR */

               hide message no-pause.

               ref = type_parm
                   + substring(string(year(today),"9999"),3,2)
                   + string(month(today),"99")
                   + string(day(today),"99")
                   + string((jlnbr + 1),"999999").
               l_glt_ref1 = ref.
               
               ref = type_parm
                   + substring(string(year(today),"9999"),3,2)
                   + string(month(today),"99")
                   + string(day(today),"99")
                   + string((jlnbr + 2),"999999").               
               l_glt_ref2 = ref.
            end. /* IF co_daily_seq */
            else do:  /* IF CONTINUOUS */
               assign
                  ref = max(ref + string(0, "999999999999"),
                        max(if available glt_det
                            then glt_ref
                            else "",
                            if available gltr_hist
                            then gltr_ref
                            else ""))
                  jlnbr = 0.

               do ctr_no = 6 to 14:
                  if substring(ref,ctr_no,1)  >= "0"
                  and substring(ref,ctr_no,1) <= "9"
                  then next.
                  else leave.
               end. /* DO ctrl_no = 6 TO 14 */

               if ctr_no = 15
               then do on error undo, leave:
                     jlnbr = integer(substring(ref,6,9)).
               end. /* IF ctrl_no = 15 */

               hide message no-pause.

               /* DUE TO INTEGER LIMITATIONS, CAN ONLY INCREMENT THE */
               /* LAST 9 DIGITS OF THE REFERENCE NUMBER              */
               ref = type_parm
                   + substring(ref,3,3)
                   + string(integer(jlnbr + 1),"999999999").
               l_glt_ref1 = ref.

               ref = type_parm
                   + substring(ref,3,3)
                   + string(integer(jlnbr + 2),"999999999").
               l_glt_ref2 = ref.
            end. /* IF CONTINUOUS */
         end. /*if not cash_book*/
         ref = "".

        for each xxepd_det exclusive-lock where xxepd_domain = global_domain
                                        and xxepd_nbr = xxep_nbr
                                        and xxepd_flag = "0"
                                        and xxepd__chr01 = "":
           dr-acc = "".
           dr-cc = "".
           cr-acc = "".
           cr-cc = "".
           rechg-yn = no.
           find first xxmap_mstr no-lock where xxmap_domain = global_domain
                                           and xxmap_type = xxepd_type no-error.
           if avail xxmap_mstr then do:
              dr-acc = xxmap_dr_acc.
              dr-cc = xxmap_dr_cc.
              cr-acc = xxmap_cr_acc.
              cr-cc = xxmap_cr_cc.
           end.
           else do:
              dr-acc = "".
              dr-cc = "".
              cr-acc = "".
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
	         if xxep_curr <> basecurr then do:
	            find first exr_rate no-lock where exr_domain = global_domain
	                                          and exr_curr1 = xxep_curr and exr_curr2 = basecurr
	                                          and exr_start_date <= today and exr_end_date >= today
	                                          no-error.
	            if avail exr_rate then amt = xxepd_cf_amt / exr_rate * exr_rate2.
	            else do:
	            find first exr_rate no-lock where exr_domain = global_domain
	                                          and exr_curr2 = xxep_curr and exr_curr1 = basecurr
	                                          and exr_start_date <= today and exr_end_date >= today
	                                          no-error.
	            if avail exr_rate then amt = xxepd_cf_amt / exr_rate2 * exr_rate.
	            end. 
	         end.
	         else amt = xxepd_cf_amt.
           if xxoaif__log[2] and xxepd_psid <> "" then do:
              /*****
              find first pj_mstr no-lock where pj_domain = global_domain
                                           and pj__qadc01 = xxepd_psid no-error.
              if avail pj_mstr then project = pj_project.
              *******/
              for each xxpc_mstr no-lock where xxpc_domain = global_domain
                                           and xxpc_project = project
                                           and xxpc_year = year(effdate)
                                           and xxpc_month = month(effdate):
					         create glt_det.
					         assign glt_domain        = global_domain
					                glt_acct          = /*xxtmp_acc*/ dr-acc
					                glt_addr          = ""
					                glt_amt           = /*xxtmp_amt xxepd_cf_amt*/ amt * xxpc_pct / 100
					                glt_batch         = ""
					                glt_cc            = /*""*/ /*dr-cc*/ xxpc_cc
					                glt_correction    = no
					                glt_curr          = basecurr
					                glt_curr_amt      = xxepd_cf_amt * xxpc_pct / 100
					                glt_date          = today
					                glt_desc          = /*xxtmp_doc_nbr*/ /*xxepd_nbr *xiami01* */ substring(xxepd_desc,12)
					                glt_doc           = /*l_glt_ref1 *xiami01* */ xxepd_nbr
					                glt_doc_type      = ""
					                glt_dy_code       = ""
					                glt_dy_num        = ""
					                glt_ecur_amt      = /*xxtmp_amt xxepd_cf_amt*/ amt * xxpc_pct / 100
					                glt_effdate       = effdate
					                glt_entity        = entity
					                glt_en_exrate     = 1
					                glt_en_exrate2    = 1
					                glt_en_exru_seq   = 0
					                glt_error         = ""
					                glt_exru_seq      = 0
					                glt_ex_rate       = 1
					                glt_ex_rate2      = 1
					                glt_line          = gline
					                glt_project       = /*xxtmp_pj_code*/ project
					                glt_ref           = l_glt_ref1
					                glt_ex_ratetype   = ""
					                glt_rflag         = false
					                glt_sub           = ""
					                glt_tr_type       = "OA"
					                glt_unb           = no
					                glt_userid        = global_userid
					                .
					         release glt_det.
					         
					      /* assign xxtmp_GL_REF = l_glt_ref1.*/
					       gline = gline + 1.
              end.
           end.
           else do:
		         create glt_det.
		         assign glt_domain        = global_domain
		                glt_acct          = /*xxtmp_acc*/ dr-acc
		                glt_addr          = ""
		                glt_amt           = /*xxtmp_amt xxepd_cf_amt*/ amt
		                glt_batch         = ""
		                glt_cc            = /*""*/ dr-cc
		                glt_correction    = no
		                glt_curr          = basecurr
		                glt_curr_amt      = xxepd_cf_amt
		                glt_date          = today
		                glt_desc          = /*xxtmp_doc_nbr*/ /*xxepd_nbr *xiami01* */ substring(xxepd_desc,12)
		                glt_doc           = /*l_glt_ref1 *xiami01* */ xxepd_nbr
		                glt_doc_type      = ""
		                glt_dy_code       = ""
		                glt_dy_num        = ""
		                glt_ecur_amt      = /*xxtmp_amt xxepd_cf_amt*/ amt
		                glt_effdate       = effdate
		                glt_entity        = entity
		                glt_en_exrate     = 1
		                glt_en_exrate2    = 1
		                glt_en_exru_seq   = 0
		                glt_error         = ""
		                glt_exru_seq      = 0
		                glt_ex_rate       = 1
		                glt_ex_rate2      = 1
		                glt_line          = gline
		                glt_project       = /*xxtmp_pj_code*/ project
		                glt_ref           = l_glt_ref1
		                glt_ex_ratetype   = ""
		                glt_rflag         = false
		                glt_sub           = ""
		                glt_tr_type       = "OA"
		                glt_unb           = no
		                glt_userid        = global_userid
		                .
		         release glt_det.
		         
		      /* assign xxtmp_GL_REF = l_glt_ref1.*/
		       gline = gline + 1.
		     end. /*end not xxoaif__log[2] use-pct*/
		       assign xxepd__chr01 = l_glt_ref1.
		       totamt = totamt + amt.
		   end. /**/
        for each xxepre_det exclusive-lock where xxepre_domain = global_domain
                                        and xxepre_nbr = xxep_nbr
                                        and xxepre_retype = "1"
                                        and xxepre__chr01 = "":
           dr-acc = "".
           dr-cc = "".
           dr-sub = "".
           cr-acc = "".
           cr-cc = "".
           cr-sub = "".
           rechg-yn = no.
           find first xxmap_mstr no-lock where xxmap_domain = global_domain
                                           and xxmap_type = xxepre__chr02 no-error.
           if avail xxmap_mstr then do:
              dr-acc = xxmap_dr_acc.
              dr-cc = xxmap_dr_cc.
              cr-acc = xxmap_cr_acc.
              cr-cc = xxmap_cr_cc.
           end.
           else do:
              dr-acc = "".
              dr-cc = "".
              cr-acc = "".
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
	         if xxep_curr <> basecurr then do:
	            find first exr_rate no-lock where exr_domain = global_domain
	                                          and exr_curr1 = xxep_curr and exr_curr2 = basecurr
	                                          and exr_start_date <= today and exr_end_date >= today
	                                          no-error.
	            if avail exr_rate then amt = xxepre_amt / exr_rate * exr_rate2.
	            else do:
	            find first exr_rate no-lock where exr_domain = global_domain
	                                          and exr_curr2 = xxep_curr and exr_curr1 = basecurr
	                                          and exr_start_date <= today and exr_end_date >= today
	                                          no-error.
	            if avail exr_rate then amt = xxepre_amt / exr_rate2 * exr_rate.
	            end. 
	         end.
	         else amt = xxepre_amt.
           if index(xxepre_cscd,"-") > 0 then do:
              dr-cc = substring(xxepre_cscd,1,index(xxepre_cscd,"-") - 1).
              dr-sub = substring(xxepre_cscd,index(xxepre_cscd,"-") + 1 ,length(xxepre_cscd)).
           end.
         if xxepre__chr03 <> "" then do:
            if substring(xxepre__chr03,1,8) <> "" then dr-acc = substring(xxepre__chr03,1,8).
            if substring(xxepre__chr03,9,8) <> "" then dr-sub = substring(xxepre__chr03,9,8).
            if substring(xxepre__chr03,17,8) <> "" then dr-cc = substring(xxepre__chr03,17,8).
         end.
         if xxepre__chr04 <> "" then do:
            if substring(xxepre__chr04,1,8) <> "" then cr-acc = substring(xxepre__chr04,1,8).
         end.
         
		         create glt_det.
		         assign glt_domain        = global_domain
		                glt_acct          = /*xxtmp_acc*/ dr-acc
		                glt_addr          = ""
		                glt_amt           = amt
		                glt_batch         = ""
		                glt_cc            = dr-cc
		                glt_correction    = no
		                glt_curr          = basecurr
		                glt_curr_amt      = xxepre_amt
		                glt_date          = today
		                glt_desc          = xxep_desc
		                glt_doc           = xxep_nbr
		                glt_doc_type      = ""
		                glt_dy_code       = ""
		                glt_dy_num        = ""
		                glt_ecur_amt      = amt
		                glt_effdate       = effdate
		                glt_entity        = entity
		                glt_en_exrate     = 1
		                glt_en_exrate2    = 1
		                glt_en_exru_seq   = 0
		                glt_error         = ""
		                glt_exru_seq      = 0
		                glt_ex_rate       = 1
		                glt_ex_rate2      = 1
		                glt_line          = gline
		                glt_project       = /*xxtmp_pj_code*/ project
		                glt_ref           = l_glt_ref1
		                glt_ex_ratetype   = ""
		                glt_rflag         = false
		                glt_sub           = dr-sub
		                glt_tr_type       = "OA"
		                glt_unb           = no
		                glt_userid        = global_userid
		                .
		         release glt_det.
		         
		       assign xxepre__chr01 = l_glt_ref1.
		       gline = gline + 1.
		       totamt = totamt + amt.
		   end. /**/
       if totamt <> 0 then do:
         create glt_det.
         assign glt_domain        = global_domain
                glt_acct          = cr-acc
                glt_addr          = ""
                glt_amt           = - totamt
                glt_batch         = ""
                glt_cc            = "" /*cr-cc*/
                glt_correction    = no
                glt_curr          = basecurr
                glt_curr_amt      = - xxep_amt
                glt_date          = today
                glt_desc          =  xxep_nbr
                glt_doc           =  xxep_nbr
                glt_doc_type      = ""
                glt_dy_code       = ""
                glt_dy_num        = ""
                glt_ecur_amt      = - totamt
                glt_effdate       = effdate
                glt_entity        = entity
                glt_en_exrate     = 1
                glt_en_exrate2    = 1
                glt_en_exru_seq   = 0
                glt_error         = ""
                glt_exru_seq      = 0
                glt_ex_rate       = 1
                glt_ex_rate2      = 1
                glt_line          = gline
                glt_project       = "" /*project*/
                glt_ref           = l_glt_ref1
                glt_ex_ratetype   = ""
                glt_rflag         = false
                glt_sub           = ""
                glt_tr_type       = "OA"
                glt_unb           = no
                glt_userid        = global_userid
                .
       end.
    end. /*for each xxtmp_mstr*/
    /*
    if prtm-yn then do:
       {mfselprt.i "print" 80}
       /*
       for each xxtmp_mstr no-lock 
           where xxtmp_doc_nbr >= docnbr1 and xxtmp_doc_nbr <= docnbr2
             and xxtmp_date >= date1 and xxtmp_date <= date2
             :
           disp xxtmp_mstr with frame c width 200.
       end.
       */
       {mfrtrail.i}
    end.
    */
end.  /*repeat*/



PROCEDURE get-co-daily-seq:
/*-----------------------------------------------------------
Purpose:      Returns the value for co_daily_seq from mfc_ctrl
Parameters:   <None>
Notes:        Created with L18P to avoid action segment error
-------------------------------------------------------------*/

   {glmfcctl.i}

END PROCEDURE. /* get-co-daily-seq */

PROCEDURE p-disp-msg:
   define input parameter msg_number  like msg_nbr no-undo.
   define input parameter error_level as   integer no-undo.

   {pxmsg.i &MSGNUM=msg_number &ERRORLEVEL=error_level}
END PROCEDURE. /* p-disp-msg */


/********
         assign glt_domain 
                glt_acct    
                glt_addr       
                glt_amt
                glt_batch
                glt_cc      
                glt_correction 
                glt_curr       
                glt_curr_amt
                glt_date    
                glt_desc       
                glt_doc        
                glt_doc_type
                glt_dy_code 
                glt_dy_num     
                glt_ecur_amt   
                glt_effdate
                glt_entity  
                glt_en_exrate  
                glt_en_exrate2 
                glt_en_exru_seq
                glt_error   
                glt_exru_seq   
                glt_ex_rate    
                glt_ex_rate2
                glt_line    
                glt_project    
                glt_ref        
                glt_ex_ratetype
                glt_rflag   
                glt_sub        
                glt_tr_type    
                glt_unb
                glt_user1   
                glt_user2      
                glt_userid
                .
************/