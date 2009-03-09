{mfdeclre.i}
DEF INPUT PARAMETER domain_from like global_domain.
def input parameter domain_to		like global_domain.
def input parameter order like xxpo_nbr.
def input parameter	new_order 	as logical.
def input parameter site 	like si_site.
def input parameter basecurr as char.
def var quote as char /*delimiter*/ init '"'.
def var bill-to as char.
def var ship-to as char.

def var acc as char.
def var sub as char.
def var cc as char.
def var s-m as logical.
def var pj as char.
def var desc1 as char format "x(24)".

for first poc_ctrl where poc_domain = domain_from no-lock :
bill-to = poc_bill.
ship-to = poc_ship.
s-m = poc_ln_fmt.
end.

find xxoaif_ctrl no-lock where xxoaif_domain = global_domain no-error.
if new_order then 
for each xxpo_mstr where xxpo_mstr.xxpo_domain = global_domain
									 and xxpo_mstr.xxpo_nbr		 = order
									 no-lock:
		/*Frame b*/
		desc1 = substring(xxpo_mstr.xxpo_desc,1,24).
		PUT UNFORMATTED quote	/*so_nbr*/ "" quote space 					/*sale order*/
										SKIP 
              			quote	xxpo_mstr.xxpo_supp quote	 space				/*sold-to*/
              			skip
              			quote ship-to quote	 space				/*bill-to*/
              			SKIP
              			quote	xxpo_mstr.xxpo_crt_date quote space		/*order date*/
              			quote xxpo_mstr.xxpo_crt_date quote space   /*due date*/
              			 "-" space						/*buyer*/
              			quote bill-to quote space					/*bill-to*/
             		 	  "-" space					/*so_job*/
              			 "-" space 						/*contract*/ 
              			 "-" space							/*contact*/
              			 /*"-" space		*/					/*remarks*/
              			 quote desc1 quote space
              			 "-" space							/*PriceTable*/
              			 "-" space							/*DiscountTbl*/
              			 "-" space							/*Ln Discount*/
              			quote site quote space						/*site*/
              			 "-" space							/*Project*/
              			yes			space				/*Confirm*/
              			No			space				/*Imp/Exp*/
              			quote xxpo_mstr.xxpo_purcurr	 quote space 					/*Currency for new po*/
              			 "-"  space 					/*Language for new po*/
              			 "-"   space   				/*taxable*/
              			 "-"  space 				/*taxclass*/
              			today	space					/*Po_tax_date*/		
              			yes		space					/*fixprice*/
              			 "-" space							/*CreditTerms*/
              			 "-" space						/*CreditInt*/
              			/*quote global_userid quote space 		/*EnterBy for system*/*/
              			 "-" space							/*RequestedBy*/
              			no	space						/*Comments*/
              			skip.		
     if xxpo_mstr.xxpo_purcurr <> basecurr then do:
        put unformatted "-" space
                        "-" space
                        skip.
     end.         			
		/*Frame set-tax*/							 
		PUT UNFORMATTED  "-" space 						/*tax usage*/
										 "-" space 						/*tax Environment*/
										 "-" space							/*tax class*/
										 "-" space							/*taxable*/
										 "-" space 									/*tax In*/
										skip.
		/*Frame cmmt01 for Comments = yes*/
		/*   ****   */
		/*Frame c for pod*/								
    FOR EACH xxpod_det WHERE xxpod_det.xxpod_domain = global_domain 
                       AND xxpod_det.xxpod_nbr = xxpo_mstr.xxpo_nbr
                       NO-LOCK:
        acc = "".
        sub = "".
        cc = "".
        pj = "".
        PUT UNFORMATTED quote	xxpod_det.xxpod_line quote 
        								skip
                        quote	site quote		 		
                        skip
                        "-"					/*Reqnbr for new pod*/ 			
                        skip
                        /*"-" 		/*pod_part for new pod*/*/
                        quote substring(xxpod_det.xxpod_desc,1,10)	quote space
                        SKIP
                   			quote xxpod_det.xxpod_qty	quote space  /*order qty*/
                   			"-" space 			/*UM*/
                   			SKIP
                   			quote xxpod_det.xxpod_price	quote space 	/*Price-pod_pur_cost*/
                   			 "-" space				/*pod_disc_pct*/
                   			SKIP.
        if s-m then do:
           find first xxmap_mstr no-lock where xxmap_domain = global_domain
                                           and xxmap_type = xxpod_det.xxpod_oatype
                                           no-error.
           if avail xxmap_mstr then do:
              acc = xxmap_dr_acc.
           end.
           find first pj_mstr no-lock where pj_domain = global_domain
                                        and pj__qadc01 = xxpod_det.xxpod_buyfor no-error.
           if avail pj_mstr then do:
              pj = pj_project.
              find first cr_mstr no-lock where cr_domain = global_domain 
                                           and cr_code = pj_project
                                           and cr_type = "PJ_CC"
                                           no-error.
           end.
           if avail cr_mstr then do:
               cc = cr_code_beg.
		           if cc = "" then do:
		              find first pj_mstr no-lock where pj_domain = global_domain
		                                           and pj__qadc01 = xxpo_mstr.xxpo_crt_user no-error.
		              if avail pj_mstr then do:
		                 if pj = "" then pj = pj_project.
		                 find first cr_mstr no-lock where cr_domain = global_domain 
		                                         and cr_code = pj_project
		                                         and cr_type = "PJ_CC"
		                                         no-error.
		              end.
		              if avail cr_mstr then 
		                 cc = cr_code_beg.
		           end.
           end.
	         /*xiami070214*/
	         if xxpod_dept <> "" then do:
	         	  find first xxdept_mstr no-lock where xxdept_domain = global_domain
	                                         and xxdept_dept = xxpod_dept no-error.
	            if avail xxdept_mstr then do:
	               if xxdept__chr01 <> "" and xxdept__chr01 <> cc then cc = xxdept__chr01.
	            end.
	         end.
           /*xiami070214*/
           
           if xxpod_det.xxpod_re_fg then do:
               find first xxpore_det no-lock where xxpore_det.xxpore_domain = global_domain
                                                and xxpore_det.xxpore_nbr = xxpo_mstr.xxpo_nbr
                                                and xxpore_det.xxpore_rcid = xxpod_det.xxpod_item no-error.
               if avail xxpore_det then do:
					         /*xiami070214*/
					         if xxpore_dpcd <> "" then do:
					         	  find first xxdept_mstr no-lock where xxdept_domain = global_domain
					                                         and xxdept_dept = xxpore_dpcd no-error.
					            if avail xxdept_mstr then do:
					               if xxdept__chr01 <> "" and xxdept__chr01 <> cc then cc = xxdept__chr01.
					            end.
					         end.
				           /*xiami070214*/
		               if index(xxpore_det.xxpore_cscd,"-") > 0 then do:
		                 cc = substring(xxpore_det.xxpore_cscd,1,index(xxpore_det.xxpore_cscd,"-") - 1).
		                 sub = substring(xxpore_det.xxpore_cscd,index(xxpore_det.xxpore_cscd,"-") + 1 ,length(xxpore_det.xxpore_cscd)).
		               end.
		               else do:
		                 sub = "".
		                 cc = xxpore_det.xxpore_cscd.
		               end.
               end.
           end.
           
           if xxpod_det.xxpod__chr01 <> "" then cc = xxpod_det.xxpod__chr01.
           desc1 = substring(xxpod_det.xxpod_desc,1,24).
           
	         if xxoaif__log[4] then do:
	            find first ac_mstr no-lock where ac_domain = global_domain 
	                                         and ac_code = acc 
	                                         and ac__log01 no-error.
	            if avail ac_mstr and ac__log01 then do:
	               sub = "".
	               cc = "".
	               pj = "".
	            end.
	         end. 
	         
           PUT UNFORMATTED "-" space 
                           "-" space
                           "-" space
                           "-" space
                           /*"-" space*/
                           quote desc1 quote
                           today space
                           today space
                           today space
                           "-" space
                           yes space
                           quote acc quote space
                           /*"-" space*/
                           quote sub quote space
                           quote cc quote space
                           quote pj quote space
                           /*"-" space*/
                           "-" space
                           "-" space
                           "-" space
                           "-" space
                           "-" space
                           "-" space
                           "-" space
                           skip.
        end.  /*s-m*/
    END.
    /*Exit Pod*/
    PUT UNFORMATTED CHR(9) "." SKIP "." SKIP.
    /*Frame potot TaxDetail*/
    put unformatted "No" SKIP.
    /*Frame pomtd*/
    put unformatted  "-" space			/*Revision*/
    								Yes	space			/*Print PO*/
    								No	space			/*EDI PO*/
    								 "-" space				/*Ap Account*/
    								 "-" space				/*Ap Sub-Account*/
    								 "-" space				/*Ap Cost-Center*/
    								 "-" space				/*Deliver To*/
    								 "-" space				/*Amt Prepare*/
    								 "-" space 			/*Po_stat*/
    								 "-" space				/*Po_fob*/
    								 "-"	       			/*po_ShipVia*/
    								SKIP.
    /*Exit Po CimLoad*/								
    put unformatted "." SKIP .
  										 
end.									 