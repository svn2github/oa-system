{mfdtitle.i}
def var dp-dept  like xxdept_dept.
def var dp-desc  like xxdept_desc.
def var dp-pcode like xxdept_p_code.
def var dp-cc    as   char format "x(8)".
def var dp-stat  like xxdept_stat.
def var domain      like xxtype_domain.
def var del-yn like mfc_logical initial no.
 
form
    dp-dept  colon 25
    dp-desc  colon 25
    dp-pcode colon 25
    skip(1)
    dp-cc    colon 25
    dp-stat  colon 25
    with frame a side-labels width 80
    title "Cost Center Maintenance".
    
domain = global_domain.   
 
repeat with frame a:
    recno = ?.
    prompt-for dp-dept EDITING:

       /* FIND NEXT/PREVIOUS RECORD */
       {mfnp01.i xxdept_mstr dp-dept  xxdept_dept  domain xxdept_domain
               xxdept_idx01}

       if recno <> ? then do:
          dp-dept  = xxdept_dept.
          dp-desc  = xxdept_desc.
          dp-pcode = xxdept_p_code.
          dp-cc    = xxdept__chr01.
          dp-stat  = xxdept_stat.
          display
              dp-dept
              dp-desc
              dp-pcode
              dp-cc
              dp-stat
              .
       end.

    end. /* prompt-for */


		bcdparm = "".
		{mfquoter.i dp-dept }
		find first xxdept_mstr where xxdept_domain = domain
		                         and xxdept_dept   = input dp-dept
		                         no-error.
    if not avail xxdept_mstr then do:
       create xxdept_mstr.
       assign xxdept_domain = domain
              xxdept_dept   = input dp-dept
              .
       update dp-desc dp-pcode.
       {mfquoter.i dp-desc }
       {mfquoter.i dp-pcode }
       assign xxdept_desc = dp-desc
              xxdept_p_code = dp-pcode.
       dp-cc = "".
       dp-stat = no.
    end.
    else do:
	      dp-dept  = xxdept_dept.
	      dp-desc  = xxdept_desc.
	      dp-pcode = xxdept_p_code.
	      dp-cc    = xxdept__chr01.
	      dp-stat  = xxdept_stat.
	      display
	          dp-dept
	          dp-desc
	          dp-pcode
	          dp-cc
	          dp-stat
	          .
    end.
    update dp-cc dp-stat .
    find first cc_mstr no-lock where cc_domain = domain and cc_ctr = dp-cc no-error.
    if not avail cc_mstr then do:
       message "Error Cost Center Code , please re-enter!".
       undo,retry.
    end.
    else do:
       assign xxdept__chr01 = dp-cc
              xxdept_stat = dp-stat.
    end.
    
end.