
{mfdtitle.i}
def buffer xxprh for xxprh_hist.
DEF var domain_from like global_domain.
def var domain_to		like global_domain.
def var ponbr1 like xxpo_nbr.
def var ponbr2 like xxpo_nbr.
def var item1 like xxpod_item.
def var item2 like xxpod_item.
def var order like xxpo_nbr.
def var	new-order 	as logical.
def var site 	like si_site.
def var domain like global_domain.
def var curr as char.
def var cim-yn as logical.
DEF VAR in-file AS CHAR FORMAT "x(40)" LABEL "Import file name".
DEF VAR in-path AS CHAR FORMAT "x(40)" LABEL "Import path".
DEF VAR bakpath AS CHAR FORMAT "x(40)" LABEL "Backup path".
DEF VAR errpath AS CHAR FORMAT "x(40)" LABEL "Error path".
def var cim-file as char format "x(40)".
def var log-file as char format "x(40)".
def var cur-yn as logical.
def var curr2 as char.
def var prhnbr    as inte.
def var odt as date label "Receive date".

def var basecurr as char.
find first gl_ctrl no-lock where gl_domain = global_domain no-error.
basecurr = gl_base_curr.

find first xxprh no-lock no-error.
find first si_mstr no-lock no-error.
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
     ponbr1 colon 25 
     ponbr2 colon 45 label {t001.i}
     item1  colon 25
     item2  colon 45 label {t001.i}
     odt colon 25 help "To Use the original receive date ,leave the value empty!"
     with frame a width 80 side-label.
     
cim-file = /*bakpath +*/ global_userid + "xxprh_cim.txt".   /*xiami061229*/
log-file = /*bakpath +*/ global_userid + "xxprh.log".   /*xiami061229*/
repeat:
    if ponbr2 = hi_Char then ponbr2 = "".
    if item2 = hi_char then item2 = "".
    odt = ?.
    update ponbr1 ponbr2 
           item1 item2 
           odt
           with frame a.
    if ponbr2 = "" then ponbr2 = hi_char.
    if item2 = "" then item2 = hi_char.
    curr2 = basecurr.
    for each xxprh no-lock where xxprh.xxprh_domain = domain
                                       and xxprh.xxprh_nbr >= ponbr1 and xxprh.xxprh_nbr <= ponbr2
                                       and xxprh.xxprh_item >= item1 and xxprh.xxprh_item <= item2
                                       and xxprh.xxprh__chr01 = "",
        each xxpo_mstr no-lock where xxpo_domain = domain and xxpo_nbr = xxprh.xxprh_nbr:
        if xxpo_purcurr <> basecurr then curr2 = xxpo_purcurr.
    end.
    if curr2 <> basecurr then do:
          message "Need Exchange Rate In these PO, Continue?" update cur-yn.
          if NOT cur-yn then UNDO,RETRY.
    END.
    message "Start to Cimload to PO Receiver" update cim-yn.
    if cim-yn then 
    for each xxprh exclusive-lock where xxprh.xxprh_domain = domain
                                       and xxprh.xxprh_nbr >= ponbr1 and xxprh.xxprh_nbr <= ponbr2
                                       and xxprh.xxprh_item >= item1 and xxprh.xxprh_item <= item2
                                       and xxprh.xxprh__chr01 = "":
           find FIRST poc_ctrl WHERE poc_domain = domain no-lock no-error.
           if avail poc_ctrl then do:
              prhnbr = poc_rcv_nbr.
           end.
        find first xxpo_mstr exclusive-lock where xxpo_domain = domain 
                                       and xxpo_nbr = xxprh.xxprh_nbr no-error.
        if avail xxpo_mstr then do:
           curr = xxpo_purcurr.
            if xxpo_purcurr <> basecurr then do:
               find first exr_rate no-lock where exr_domain = global_domain
                                          and exr_curr1 = xxpo_purcurr and exr_curr2 = basecurr
                                          and exr_start_date <= today and exr_end_date >= today
                                          no-error.
               if avail exr_rate then do:
                  assign xxpo__dec02 = exr_rate
                         xxpo__dec03 = exr_rate2.
               end.
               else do:
                  find first exr_rate no-lock where exr_domain = global_domain
                                                and exr_curr2 = xxpo_purcurr and exr_curr1 = basecurr
                                                and exr_start_date <= today and exr_end_date >= today
                                                no-error.
                  if avail exr_rate then assign xxpo__dec02 = exr_rate2
                                                xxpo__dec03 = exr_rate.
               end. 
            end.
         end.
        output to value(cim-file).
            {gprun.i ""xxcimprh.p"" "(input xxprh.xxprh_domain,input xxprh.xxprh_domain,
                                      input  xxprh.xxprh_nbr,input xxprh.xxprh_item,
                                      input yes,input si_site, input curr, input odt)"}.
        output close.    
        {gprun.i ""xxporc.p"" "(input cim-file, input log-file)"}
           find FIRST poc_ctrl WHERE poc_domain = domain no-lock no-error.
           if avail poc_ctrl then do:
              find first prh_hist no-lock where prh_domain = domain and prh_receiver = poc_rcv_pre + string(prhnbr,"9999") no-error.
              if avail prh_hist then do:
                 for each xxprh_hist where xxprh_hist.xxprh_domain = domain
                          and xxprh_hist.xxprh_nbr = xxprh.xxprh_nbr
                          and xxprh_hist.xxprh__chr01 = "":
                     xxprh_hist.xxprh__chr01 = poc_rcv_pre + string(prhnbr,"9999").
                 end.
              end.
              /*xxprh.xxprh__chr01 = poc_rcv_pre + string(prhnbr,"9999").*/
           end.
           /*xxprh.xxprh__chr01 = poc_rcv_pre + string(poc_rcv_nbr - 1,"9999").*/
    end.
end.