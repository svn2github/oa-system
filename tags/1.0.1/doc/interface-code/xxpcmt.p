{mfdtitle.i}
def var domain like xxpc_domain.
def var pj like xxpc_project.
def var pjdesc like pj_desc .
def var yy like xxpc_year.
def var mm like xxpc_month.
def var cc like xxpc_cc.
def var pct like xxpc_pct.
def var tot as deci.
def var del-yn    like mfc_logical initial false.

form 
    pj colon 20
    pjdesc no-label
    yy colon 20
    mm colon 20
    with frame a side-label width 80.
form
    xxpc_cc 
    cc_desc
    xxpc_pct
    with frame b width 80 8 down.
    
form 
    cc
    cc_desc no-label
    pct
    with frame c width 80 side-label.
    
repeat:
    domain = global_domain.
    tot = 0.
    recno = ?.
    prompt-for pj with frame a EDITING:

       /* FIND NEXT/PREVIOUS RECORD */
       {mfnp01.i pj_mstr pj  pj_project domain pj_domain
                 pj_project}
       if recno <> ? then do:
          pj = pj_project.
          display
                 pj pj_desc @ pjdesc with frame a.
       end.
    end. /* prompt-for */
    find first pj_mstr no-lock where pj_domain = domain and pj_project = input pj no-error.
    disp pj_desc @ pjdesc with frame a.
    
    update yy validate(yy > 1000 and yy < 10000,"Error Year")
           mm validate(lookup(mm,"1,2,3,4,5,6,7,8,9,10,11,12") > 0 , "Error Month") with frame a.
    find first xxpc_mstr no-lock where xxpc_domain = domain and xxpc_project = input pj
                                   and xxpc_year   = yy     and xxpc_month = mm no-error.
    if not avail xxpc_mstr then do:
       message "Add New Project Percent!".
       view frame b.
    end.
    /*
    for each xxpc_mstr no-lock where xxpc_domain = domain 
                                 and xxpc_project = input pj
                                 and xxpc_year   = yy     
                                 and xxpc_month = mm,
        each cc_mstr no-lock where cc_domain = domain
                               and cc_ctr    = xxpc_cc
        break by xxpc_domain by xxpc_project by xxpc_year by xxpc_month:
        
        tot = tot + xxpc_pct.
        disp xxpc_cc cc_desc xxpc_pct with frame b.
        down with frame b.
        if last-of(xxpc_month) then do:
           underline xxpc_pct with frame b.
           disp "TOT" @ cc_desc
                tot @ xxpc_pct
                with frame b.
        end.
    end.
    */
    repeat:
        clear frame b all.
        clear frame c all.
        pct = 0.
        cc  = "".
        tot = 0.
		    for each xxpc_mstr no-lock where xxpc_domain = domain 
		                                 and xxpc_project = input pj
		                                 and xxpc_year   = yy     
		                                 and xxpc_month = mm,
		        each cc_mstr no-lock where cc_domain = domain
		                               and cc_ctr    = xxpc_cc
		        break by xxpc_domain by xxpc_project by xxpc_year by xxpc_month:
		        
		        tot = tot + xxpc_pct.
		        disp xxpc_cc cc_desc xxpc_pct with frame b.
		        down with frame b.
		        if last-of(xxpc_month) then do:
		           underline xxpc_pct with frame b.
		           disp "TOT" @ cc_desc
		                tot @ xxpc_pct
		                with frame b.
		        end.
		    end.
        
		    update cc validate(can-find(cc_mstr no-lock where cc_domain = domain and cc_ctr = cc), "Error Cost Center" ) 
		           go-on("F5" "CTRL-D" "F4" "CTRL-S") with frame c.
		    
		    find first cc_mstr no-lock where cc_domain = domain
		                                 and cc_ctr = cc no-error.
		    disp cc_desc with frame c.
		    find first xxpc_mstr no-lock where xxpc_domain = domain and xxpc_project = input pj
		                                   and xxpc_year   = yy     and xxpc_month   = mm
		                                   and xxpc_cc     = cc no-error.
		    if avail xxpc_mstr then do:
		       tot = tot - xxpc_pct.
		       pct = xxpc_pct.
		    end.
		    
        if lastkey = keycode("F5") or lastkey = keycode("CTRL-D")
        then do:
           del-yn = yes.
           {mfmsg01.i 11 1 del-yn}
           if del-yn = no then
              undo,retry .
           find first xxpc_mstr where xxpc_domain = domain and xxpc_project = input pj
                                  and xxpc_year   = yy     and xxpc_month   = mm
                                  and xxpc_cc     = cc no-error.
           if avail xxpc_mstr then delete xxpc_mstr.
        end.
        if lastkey = keycode("F4") or lastkey = keycode("CTRL-S") then do:
           message tot.
           if tot <> 100 then do:
              message "Percent Total must be 100".
              undo,retry.
           end.
        end.		    
		    
		    update pct validate(tot + pct <= 100 and pct <> 0,"Tot Percent More Than 100!") go-on("F5" "CTRL-D" "F4") with frame c.
		    tot = tot + pct.
		    
		    find first xxpc_mstr where xxpc_domain = domain and xxpc_project = input pj
		                           and xxpc_year   = yy     and xxpc_month   = mm
		                           and xxpc_cc     = cc no-error.
		    if avail xxpc_mstr then do:
		       xxpc_pct = pct.
		    end.
		    else do:
		       create xxpc_mstr.
		       assign xxpc_domain = domain
		              xxpc_project = input pj
		              xxpc_year    = yy
		              xxpc_month   = mm
		              xxpc_cc      = cc
		              xxpc_pct     = pct.
		    end.
        if lastkey = keycode("F4") then do:
           message tot.
           if tot <> 100 then do:
              message "Percent Total must be 100".
              undo,retry.
           end.
        end.		    
		    
        if lastkey = keycode("F5") or lastkey = keycode("CTRL-D")
        then do:
           del-yn = yes.
           {mfmsg01.i 11 1 del-yn}
           if del-yn = no then
              undo,retry .
           find first xxpc_mstr where xxpc_domain = domain and xxpc_project = input pj
                                  and xxpc_year   = yy     and xxpc_month   = mm
                                  and xxpc_cc     = cc no-error.
           if avail xxpc_mstr then delete xxpc_mstr.
        end.
        if lastkey = keycode("F4") then do:
           message tot.
           if tot <> 100 then do:
              message "Percent Total must be 100".
              undo,retry.
           end.
        end.		    
    end.
    
end.
    