{mfdeclre.i}
DEF INPUT PARAMETER domain_from like global_domain.
def input parameter domain_to		like global_domain.
def input parameter order like xxpo_nbr.
def input parameter item like xxpod_item.
def input parameter	new_order 	as logical.
def input parameter site 	like si_site.
def input parameter curr as char.
def input parameter idt as date.
def var quote as char /*delimiter*/ init '"'.
def var dt as date.
DEFINE VAR qt AS CHARACTER INIT '"'.
DEFINE VAR spc AS CHARACTER INIT " ".

if new_order then 
for each xxpo_mstr where xxpo_mstr.xxpo_domain = global_domain
									 and xxpo_mstr.xxpo_nbr		 = order
									 no-lock:
		/*Frame b*/
		find first xxprh_hist where xxprh_hist.xxprh_domain = global_domain
		                        and xxprh_hist.xxprh_nbr = xxpo_mstr.xxpo_nbr
		                        and xxprh_hist.xxprh__chr01 = "" no-lock no-error.
		if avail xxprh_hist then dt = xxprh_hist.xxprh_rcv_dt .
		else dt = today.
		if idt <> ? then dt = idt. 
		PUT UNFORMATTED quote	xxpo_mstr.xxpo_ponbr quote space 					/*sale order*/
                    skip
                    "- - "
                    qt dt qt spc
                    spc "- No No - " 
                    skip.
    find first vef_ctr no-lock no-error.
    if avail vef_ctrl and vef_using_sup_perf then 
       put unformatted  "-" skip.
    find gl_ctrl where gl_domain = domain_to no-lock no-error.
    if gl_base_curr <> curr then do:
		   put unformatted "-" skip.
		end.
		/*Frame c for pod*/								
    FOR EACH xxpod_det WHERE xxpod_det.xxpod_domain = global_domain 
                       AND xxpod_det.xxpod_nbr = xxpo_mstr.xxpo_nbr
                       ,
        each xxprh_hist where xxprh_hist.xxprh_domain = global_domain
                          and xxprh_hist.xxprh_nbr = xxpo_mstr.xxpo_nbr
                          and xxprh_hist.xxprh_item = xxpod_det.xxpod_item
                          and xxprh_hist.xxprh__chr01 = ""
                       NO-LOCK:
        PUT UNFORMATTED quote	xxpod_det.xxpod_line quote 
        								skip
                        quote	xxprh_hist.xxprh_rcv_qty quote	
                        "- No - "
                        "- - "
                        "- - - - - No No "
                        skip
                        .
                        	 		
    END.
    put unformatted  "." skip "." skip.
  										 
end.									 