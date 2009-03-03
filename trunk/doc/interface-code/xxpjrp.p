{mfdtitle.i}
def var domain  like pj_domain.
def var pjcode1 like pj_project.
def var pjcode2 like pj_project.
def var oacode1 as   char  format "x(10)" label "OA Code".
def var oacode2 like oacode1.
def var stat1   like pj_stat.
def var stat2   like pj_stat.
def var type1   like pj_type.
def var type2   like pj_type.
def var act     like pj_active.
def var begdt   like pj_beg_dt.
def var begacc  as   char label "Beg Acc".
def var endacc  as   char label "End Acc".
def var begsub  as   char label "Beg Sub".
def var endsub  as   char label "End Sub".
def var begcc   as   char label "Beg CC".
def var endcc   as   char label "End CC".
def buffer crmstr for cr_mstr.

form 
    pjcode1 colon 25
    pjcode2 colon 45 label {t001.i}
    oacode1 colon 25
    oacode2 colon 45 label {t001.i}
    stat1   colon 25
    stat2   colon 45 label {t001.i}
    type1   colon 25
    type2   colon 45 label {t001.i}
    act     colon 25
    begdt   colon 25
    with frame a side-label width 80.
    
domain = global_domain.

repeat:
    if pjcode2 = hi_char  then pjcode2 = "".
    if oacode2 = hi_char  then oacode2 = "".
    if stat2   = hi_char  then stat2   = "".
    if type2   = hi_char  then type2   = "".
    if begdt   = low_date then begdt   = ?.
    update pjcode1 pjcode2
           oacode1 oacode2
           stat1   stat2
           type1   type2
           act     begdt
           with frame a.
    if pjcode2 = "" then pjcode2 = hi_char.
    if oacode2 = "" then oacode2 = hi_char.
    if stat2   = "" then stat2   = hi_char.
    if type2   = "" then type2   = hi_char.
    if begdt   = ?  then begdt   = low_date.
    
    bcdparm = "".
    {mfquoter.i pjcode1 }
    {mfquoter.i pjcode2 }
    {mfquoter.i oacode1 }
    {mfquoter.i oacode2 }
    {mfquoter.i stat1 }
    {mfquoter.i stat2 }
    {mfquoter.i type1 }
    {mfquoter.i type2 }
    {mfquoter.i act }
    {mfquoter.i begdt }
    
    {mfselbpr.i "terminal" 80}
    for each pj_mstr no-lock where pj_domain = domain
                               and pj_project >= pjcode1 and pj_project <= pjcode2
                               and pj__qadc01 >= oacode1 and pj__qadc01 <= oacode2
                               and pj_stat    >= stat1   and pj_stat    <= stat2
                               and pj_type    >= type1   and pj_type    <= type2
                               and ((pj_active and act) or (not act))
                               and pj_beg_dt >= begdt
                               :
        find first cr_mstr no-lock where cr_domain = domain 
                                     and cr_code = pj_project
                                     and cr_type = "PJ_ACCT"
                                     no-error.
        if avail cr_mstr then assign begacc = cr_code_beg
                                     endacc = cr_code_end.
        else assign begacc = ""
                    endacc = "".
                    /*
        find first cr_mstr no-lock where cr_domain = domain 
                               and cr_code = pj_project
                               and cr_type = "PJ_SUB"
                               no-error.
        if avail cr_mstr then assign begsub = cr_code_beg
                                     endsub = cr_code_end.
        else assign begsub = "NONE"
                    endsub = "NONE".
                    */
        find first crmstr no-lock where crmstr.cr_domain = domain 
                                    and crmstr.cr_code = pj_project
                                    and crmstr.cr_type = "PJ_CC"
                                    no-error.
        if avail crmstr then assign begcc = crmstr.cr_code_beg
                                    endcc = crmstr.cr_code_end.
        else assign begcc = ""
                    endcc = "".
        disp pj_project pj_desc pj__qadc01 label "OA Code" format "x(10)"
             begacc endacc
/*             begsub endsub*/
             begcc  endcc 
             pj_type pj_stat 
             with frame b width 160 down.
        repeat:
            find next cr_mstr no-lock where cr_mstr.cr_domain = domain 
                                        and cr_mstr.cr_code = pj_project
                                        and cr_mstr.cr_type = "PJ_ACCT"
                                        no-error.
            if avail cr_mstr then assign begacc = cr_mstr.cr_code_beg
                                         endacc = cr_mstr.cr_code_end.
            else assign begacc = ""
                        endacc = "".
            find next crmstr no-lock where crmstr.cr_domain = domain 
                                        and crmstr.cr_code = pj_project
                                        and crmstr.cr_type = "PJ_CC"
                                        no-error.
            if avail crmstr then assign begcc = crmstr.cr_code_beg
                                         endcc = crmstr.cr_code_end.
            else assign begcc = ""
                        endcc = "".
            if not avail cr_mstr and not avail crmstr then leave.
            down with frame b.
            disp begacc endacc
                 begcc  endcc 
                 with frame b.
        end.
    end.
    {mfreset.i}
end.