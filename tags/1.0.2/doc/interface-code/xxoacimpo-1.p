
{mfdtitle.i}
def buffer xxpomstr for xxpo_mstr.
DEF var domain_from like global_domain.
def var domain_to		like global_domain.
def var ponbr1 like xxpo_nbr.
def var ponbr2 like xxpo_nbr.
def var order like xxpo_nbr.
def var	new-order 	as logical.
def var site 	like si_site.
def var domain like global_domain.
def var cim-yn as logical.
DEF VAR in-file AS CHAR FORMAT "x(40)" LABEL "Import file name".
DEF VAR in-path AS CHAR FORMAT "x(40)" LABEL "Import path".
DEF VAR bakpath AS CHAR FORMAT "x(40)" LABEL "Backup path".
DEF VAR errpath AS CHAR FORMAT "x(40)" LABEL "Error path".
def var cim-file as char format "x(40)".
def var log-file as char format "x(40)".
def var ponbr    as inte.
def var i as inte.
def var j as inte.

def var basecurr as char.
find first gl_ctrl no-lock where gl_domain = global_domain no-error.
basecurr = gl_base_curr.
find first xxpomstr no-lock no-error.
find first si_mstr no-lock where si_domain = global_domain no-error.
domain_from = global_domain.
domain_to   = global_domain.
domain = global_domain.

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
     ponbr1 ponbr2

     with frame a width 80 side-label.
     
cim-file = bakpath + global_userid + "xxpo_cim.txt".  /*xiami061229*/
log-file = bakpath + global_userid + "xxpo.log".   /*xiami061229*/
repeat:
    if ponbr2 = hi_Char then ponbr2 = "".
    update ponbr1 ponbr2 with frame a.
    if ponbr2 = "" then ponbr2 = hi_char.
    message "Start to Cimload to PO" update cim-yn.
    if cim-yn then 
    for each xxpomstr exclusive-lock where xxpomstr.xxpo_domain = domain
                                       and xxpomstr.xxpo_nbr >= ponbr1 and xxpomstr.xxpo_nbr <= ponbr2:
           find FIRST poc_ctrl WHERE poc_domain = domain no-lock no-error.
           if avail poc_ctrl then do:
              ponbr = poc_po_nbr.
           end.
        new-order = yes.
        output to value(cim-file).
            {gprun.i ""xxcimpo-1.p"" "(input xxpomstr.xxpo_domain,input xxpomstr.xxpo_domain,
                                       input  xxpomstr.xxpo_nbr,input new-order,
                                       input si_site, input basecurr)"}.
        output close.   
        
        if xxpomstr.xxpo_ponbr = "" then new-order =YES.
        else new-order =no.
        
        if new-order then do:
           {gprun.i ""xxoapomt.p"" "(input cim-file, input log-file)"}
           find FIRST poc_ctrl WHERE poc_domain = domain no-lock no-error.
           if avail poc_ctrl then do:
              /*xiami 060427 add */
              find first po_mstr where po_domain = domain and po_nbr = poc_po_pre + string(ponbr,"9999")
                                 no-lock no-error.
              if avail po_mstr then do:
                 i = 0.
                 j = 0.
                 for each pod_det no-lock where pod_domain = domain and pod_nbr = po_nbr :
                     i = i + 1.
                 end.
                 for each xxpod_det no-lock where xxpod_domain = domain
                                              and xxpod_nbr = xxpomstr.xxpo_nbr:
                     j = j + 1.
                 end.
                 if i = j then 
		                 xxpomstr.xxpo_ponbr = poc_po_pre + string(ponbr,"9999").
              end.
           end.
        end.
    end.
end.