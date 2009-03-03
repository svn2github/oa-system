{mfdtitle.i}
def var domain as char.
def var date1 as date      label "Output Date".
def var purtype as logical label "Output purchase type".
def var exctype as logical label "Output expense type".
def var cust as logical    label "Output customer code".
def var exrate as logical  label "Output exchange rate".
def var pathdir as char    label "Output Path" format "x(50)".
def var line1 as char format "x(59)".
def var line2 as char format "x(69)".
def var line3 as char format "x(69)".
def var line4 as char format "x(70)".
def var basecurr like cu_curr.
def var curr     like cu_curr.
def var currdesc as char format "x(50)".
def var rate like exr_rate2 format "9999999.9999".

form 
    date1   colon 30
    skip(1)
    purtype colon 30  "[purtype/purtype.txt]"
    exctype colon 30  "[exptype/exptype.txt]"
    cust    colon 30  "[custcode/custcode.txt]"
    exrate  colon 30  "[exchrate/exchrate.txt]"
    skip(1)
    pathdir colon 20
    with frame a width 80 side-label.
    
domain = global_domain.
date1 = today.

repeat:
    disp date1
           with frame a.
    
    update purtype
           exctype
           cust
           exrate
           with frame a.
           
    FOR FIRST CODE_mstr NO-LOCK 
        WHERE code_domain = global_domain
          and CODE_fldname = "path" 
          AND CODE_value   = "Import path"
          :
        pathdir = CODE_cmmt.
    END.
    
    IF pathdir = "" THEN DO:
        MESSAGE "Please define the path in path maintenance menu first!".
        UNDO,RETRY.
    END.

    update pathdir with frame a .
    
    if substring(pathdir,length(pathdir),1) <> "/"
       then pathdir = pathdir + "/".
    message "Output Path : " pathdir .
    
    if purtype then do:
       output to value(pathdir + "purtype/purtype.txt").
       for each xxtype_mstr no-lock where xxtype_domain = domain
                                      and xxtype__chr01 = "purchase"
                                      :
           LINE1 = "".
           substring(line1,1,8) = xxtype_nbr.
           substring(line1,9,50) = xxtype_desc.
           if xxtype__log01 then substring(line1,59,1) = "0".
           else substring(line1,59,1) = "1".
           PUT LINE1 SKIP.
       end.
       output close.
    end.
    if exctype then do:
       output to value(pathdir + "exptype/exptype.txt").
       for each xxtype_mstr no-lock where xxtype_domain = domain
                                      and xxtype__chr01 <> "purchase"
           break by xxtype_domain by xxtype__chr01:
           if first-of(xxtype__chr01) then do:
              LINE2 = "".
              substring(line2,1,1) = "1".
              substring(line2,2,8) = xxtype__chr01.
              substring(line2,10,50) = xxtype__chr01.
              if xxtype__log01 then substring(line2,60,1) = "0".
              else substring(line2,60,1) = "1".
           PUT LINE2 SKIP.
           end.
       end.
       for each xxtype_mstr no-lock where xxtype_domain = domain
                                      and xxtype__chr01 <> "purchase"
                                      :
           LINE2 = "".
           substring(line2,1,1) = "2".
           substring(line2,2,8) = xxtype__chr01.
           substring(line2,10,8) = xxtype_nbr.
           substring(line2,18,50) = xxtype_desc.
           if xxtype__log01 then substring(line2,68,1) = "0".
           else substring(line2,68,1) = "1".
           PUT LINE2 SKIP.
       end.       
       output close.
    end.
    if cust then do:
       output to value(pathdir + "custcode/custcode.txt").
       find xxoaif_ctrl where xxoaif_domain = domain no-error.
       if not xxoaif__log[1] then do:
		       for each cm_mstr no-lock where cm_domain = domain,
		           each ad_mstr no-lock where ad_domain = domain
		                                  and ad_addr = cm_addr:
		           line3 = "".
		           substring(line3,1,17) = cm_addr.
		           substring(line3,19,49) = trim(ad_name).
		           substring(line3,68,1) = "1".
		           substring(line3,69,1) = "1".
		           put line3 skip.
		       end.
		       /*
		       for each en_mstr no-lock where en_domain = domain:
		           line3 = "".
		           substring(line3,1,8) = en_entity.
		           substring(line3,9,50) = en_name.
		           substring(line3,59,1) = "2".
		           substring(line3,60,1) = "1".
		           put line3 skip.       
		       end.
		       */
       end.
       else do:
		       for each cm_mstr no-lock where cm_domain = domain,
		           each ad_mstr no-lock where ad_domain = domain
		                                  and ad_addr = cm_addr,
		           each cc_mstr no-lock where cc_domain = domain
		                                  and cc_ctr = cm_addr
		                                  and cc_active:
		           line3 = "".
		           substring(line3,1,17) = cm_addr.
		           substring(line3,19,49) = trim(ad_name).
		           substring(line3,68,1) = "1".
		           substring(line3,69,1) = "1".
		           put line3 skip.
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
               for each cr_mstr no-lock where cr_domain = domain
                                          and cr_code = cm_addr
                                          and cr_type = "CC_SUB"
                                          and cr_code_beg <> "",   /*xiami061227*/
                   each sb_mstr no-lock where sb_domain = domain
                                          and ( /*(sb_sub >= cr_code_beg and cr_code_end = hi_char)
                                               or*/ (sb_sub >= cr_code_beg
                                                   and sb_sub <= cr_code_end))
                                          and sb_active:
                   
		               line3 = "".
				           /*substring(line3,1,8) = cm_addr.
				           substring(line3,9,1) = "-".
				           substring(line3,10,8) = sb_sub.*/
				           substring(line3,1,17) = trim(cm_addr) + "-" + trim(sb_sub).
				           substring(line3,19,49) = trim(ad_name) + "-" + trim(sb_desc).
				           substring(line3,68,1) = "1".
				           substring(line3,69,1) = "1".
				           put line3 skip.    
                   
               end.
               /*
		           for each sb_mstr no-lock where sb_domain = domain:
		               line3 = "".
				           /*substring(line3,1,8) = cm_addr.
				           substring(line3,9,1) = "-".
				           substring(line3,10,8) = sb_sub.*/
				           substring(line3,1,17) = trim(cm_addr) + "-" + trim(sb_sub).
				           substring(line3,19,49) = trim(ad_name) + "-" + trim(sb_desc).
				           substring(line3,68,1) = "1".
				           substring(line3,69,1) = "1".
				           put line3 skip.    
		           end.
		           */
		       end.
		       /*
		       for each en_mstr no-lock where en_domain = domain:
		           line3 = "".
		           substring(line3,1,8) = en_entity.
		           substring(line3,9,50) = en_name.
		           substring(line3,59,1) = "2".
		           substring(line3,60,1) = "1".
		           put line3 skip.       
		       end.
		       */
       end.
       output close.
    end.
    if exrate then do:
       output to value(pathdir + "exchrate/exchrate.txt").
       find first gl_ctrl no-lock where gl_domain = domain no-error.
       if avail gl_ctrl then basecurr = gl_base_curr.
       else do:
          basecurr = "".
          find first en_mstr no-lock where en_domain = domain
                                and en_pri no-error.
          if avail en_mstr then basecurr = en_curr.
          else basecurr = "".
       end.
       for each exr_rate no-lock where exr_domain = domain
                                   and exr_start_date <= today
                                   and exr_end_date >= today
           break by exr_curr1 by exr_start_date desc:
           if first-of(exr_curr1) then do:
              if exr_curr1 = basecurr then do:
                 find first cu_mstr where cu_curr = exr_curr2 and cu_active no-lock no-error.
                 if not avail cu_mstr then next.
                 curr = exr_curr2.
                 currdesc = cu_desc.
                 rate = exr_rate / exr_rate2.
              end.
              if exr_curr2 = basecurr then do:
                 find first cu_mstr where cu_curr = exr_curr1 and cu_active no-lock no-error.
                 if not avail cu_mstr then next.
                 curr = exr_curr1.
                 currdesc = cu_desc.
                 rate = exr_rate2 / exr_rate.
              end.
              line4 = "".
              substring(line4,1,8) = curr.
              substring(line4,9,50) = currdesc.
              substring(line4,59,12) = string(rate,"999999.9999").
              put line4 skip.
              
              curr = "".
              currdesc = "".
              rate = 0.
           end.
       end.       
       output close.
    end.
end.