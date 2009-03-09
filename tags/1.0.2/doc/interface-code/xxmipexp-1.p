/*xxepre_det.xxepre__chr02 = trim(er-exptype)*/
/* 270408  *J001*/
/*J001 Add raw */
{mfdtitle.i}
def var domain as char.
def var line as char format "x(38)".
def var outline like line.
def var line1 as char format "x(183)".
def var imp-yn as logi.
def var rcln as inte.
def buffer xxepredet for xxepre_det.
def buffer xxepddet for xxepd_det.

DEF VAR in-file AS CHAR FORMAT "x(40)" LABEL "Import file name".
DEF VAR in-path AS CHAR FORMAT "x(40)" LABEL "Import path".
DEF VAR bakpath AS CHAR FORMAT "x(40)" LABEL "Backup path".
DEF VAR errpath AS CHAR FORMAT "x(40)" LABEL "Error path".

def var em-Stat as char format "x(1)".  /*1,Master File;2,Detail File;3,Recharge File*/
def var em-nbr as char format "x(16)".  /*EX(2) + Site No.(3) + yyMMdd(6) + sequence No.(5)*/
def var em-titl as char format "x(50)".
def var em-Desc as char format "x(50)".
def var em-dept as char format "x(20)".
def var em-depid as char format "x(8)".
def var em-Crtid as char format "x(10)".
def var em-CrtDate as char format "x(8)".   /*date yyyyMMdd*/
def var em-Curr as char format "x(8)".
def var em-Amt  as char format "x(10)".    
def var em-flag as char format "x(1)".  

def var ed-Stat as char format "x(1)".   /*1,Master File;2,Detail File;3,Recharge File*/
def var ed-nbr as char format "x(16)".
def var ed-Type as char format "x(8)".   
def var ed-flag as char format "x(1)". 
def var ed-Amt  as char format "x(10)".   
def var ed-cfAmt  as char format "x(10)".
def var ed-dept as char format "x(8)".
def var ed-person as char format "x(10)".
def var ed-desc as char format "x(50)".

def var er-Stat as char format "x(1)".  /*1,Master File;2,Detail File;3,Recharge File*/
def var er-nbr as char format "x(16)".
def var er-RegID as char format "x(20)".
def var er-RegType as char format "x(1)".  /*1, Customer;2 Entity;3 Person ID*/
def var er-CustCode as char format "x(17)".
def var er-Entity as char format "x(8)".
def var er-DepCode as char format "x(8)".
def var er-PersonID as char format "x(10)".
def var er-Amt as char format "x(10)".  /*deci 13(2)*/
def var er-Pct as char format "x(6)".  /*deci 5(2)*/
def var er-exptype as char.
def var er-desc as char format "x(50)".  /*xiami070215*/

def var fl   as char format "x(60)".
def var tp   as char.

def temp-table tmp
    field tmp-fname as char format "x(70)"
    field tmp-alname as char format "x(70)"
    field tmp-stat as char
index indx01 tmp-fname.
    
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
    in-file COLON 25
    SKIP(1)
    in-path COLON 25
    bakpath COLON 25
    errpath COLON 25
	  skip(1) 
    with frame a side-labels width 80
    title "Expense Import".

repeat:  
    in-file = "expense/expense_20060221205315050.txt".
    DISP in-path bakpath errpath WITH FRAME a.
    UPDATE in-file WITH FRAME a.
    if substring(in-path,length(in-path),1) <> "/"
       then in-path = in-path + "/".
    if in-file = "expense/" or in-file = "expense" then do:
            output to tmp.txt.
				      INPUT FROM OS-DIR(in-path + in-file).
				        REPEAT:
					          set fl ^ tp  WITH FRAME x no-label.
					          pause 0.
				            if tp = "F" then do:
				               find tmp where tmp-fname = fl no-error.
				               if not avail tmp then do:
				                  create tmp.
				                  assign tmp-fname  = fl
				                         tmp-alname = in-path + in-file 
				                                    + if substring(in-file,length(in-file),1) = "/" then "" else "/"
				                                    + fl.
				               end.
				            end.   
				        END.
				      INPUT CLOSE.  
				    output close.
		end.
		else do:
		    create tmp.
		    assign tmp-fname = substring(in-file,4)
		           tmp-alname = in-path + in-file.
		end.
		/*
    for each tmp:
    disp tmp.
    end.
    */
    message "Import file " in-path + in-file update imp-yn.
    if imp-yn then do:
        for each tmp no-lock:
            find first xxep_mstr no-lock where xxep__chr05 = tmp-fname no-error.
            if avail xxep_mstr then next.
				    input from value(tmp-alname).
				    repeat:
				        import unformatted line1.
				        if substring(line1,1,1,"raw") = "1" then do:
				           em-Stat = substring(line1,1,1,"raw").
				           em-nbr = substring(line1,2,16,"raw").
				           em-titl = substring(line1,18,50,"raw").
				           em-Desc = substring(line1,68,50,"raw").
				           em-dept = substring(line1,118,20,"raw").
				           em-depid = substring(line1,138,8,"raw").
				           em-Crtid = substring(line1,146,10,"raw").
				           em-CrtDate = substring(line1,156,8,"raw").
				           em-Curr = substring(line1,164,8,"raw").
				           em-Amt  = substring(line1,172,10,"raw").
				           /*em-flag = substring(line1,182,1).*/
				           find xxep_mstr where xxep_domain = domain
				                            and xxep_nbr    = em-nbr
				                            no-error.
				           if not avail xxep_mstr then do:
				              create xxep_mstr.
				              assign xxep_domain = domain
				                     xxep_nbr = em-nbr.
				           end.
				           assign xxep_title = trim(em-titl)
				                  xxep_desc = trim(em-Desc)
				                  xxep_dep = trim(em-dept)
				                  xxep_depid = trim(em-depid)
				                  xxep_crt_id = trim(em-Crtid)
				                  xxep_crt_dt = date(int(substring(em-CrtDate,5,2)),int(substring(em-CrtDate,7,2)),int(substring(em-CrtDate,1,4)))
				                  xxep_curr = trim(em-Curr)
				                  xxep_amt  = deci(em-Amt)
				                  xxep__dte01 = today
				                  /*xxep_flag = trim(em-flag)*/
				                  xxep__chr05 = tmp-fname
				                  .
				        end.
				        else if substring(line1,1,1,"raw") = "2" then do:
				           ed-Stat = substring(line1,1,1,"raw").
				           ed-nbr = substring(line1,2,16,"raw").
				           ed-Type = substring(line1,18,8,"raw").
				           ed-Amt  = substring(line1,26,10,"raw").
				           ed-cfamt = substring(line1,36,10,"raw").
				           ed-flag = substring(line1,46,1,"raw").
				           ed-dept = substring(line1,47,8,"raw").
				           ed-person = substring(line1,55,10,"raw").
				           ed-desc = substring(line1,65,3000,"raw").
				           
				           /*ed-flag = substring(line1,26,1).
				           ed-Amt  = substring(line1,27,10).
				           ed-cfamt  = substring(line1,37,10).*/
				           find xxepd_det where xxepd_domain = domain
				                            and xxepd_nbr = ed-nbr
				                            and xxepd_type = ed-type
				                            and xxepd_flag = ed-flag no-error.
				           /*if not avail xxepd_det then do:*/
				              find last xxepddet no-lock where xxepddet.xxepd_domain = domain
				                                        and xxepddet.xxepd_nbr = ed-nbr no-error.
				              if not avail xxepddet then rcln = 1.
				              else rcln = xxepddet.xxepd_edln + 1.
				              create xxepd_det.
				              assign xxepd_det.xxepd_domain = domain
				                     xxepd_det.xxepd_nbr = trim(ed-nbr)
				                     xxepd_det.xxepd_type = trim(ed-type)
				                     xxepd_det.xxepd_flag = trim(ed-flag)
				                     xxepd_det.xxepd_edln = rcln.
				              if ed-type = "" then xxepd_det.xxepd_type = "exp00007".
				           /*end.*/
				              assign xxepd_det.xxepd_amt = deci(ed-Amt)
				                     xxepd_det.xxepd_cf_amt = deci(ed-cfAmt)
				                     xxepd_det.xxepd_dpid = ed-dept
				                     xxepd_det.xxepd_psid = ed-person
				                     xxepd_det.xxepd_desc = ed-desc.
				        end.
				        else if substring(line1,1,1,"raw") = "3" then do:
				           er-Stat = substring(line1,1,1,"raw").
				           er-nbr = substring(line1,2,16,"raw").
				           er-exptype = substring(line1,18,8,"raw").
				           er-RegID = substring(line1,26,20,"raw").
				           er-RegType = substring(line1,46,1,"raw").
				           er-CustCode = substring(line1,47,17,"raw").
				           er-Entity = substring(line1,64,8,"raw").
				           er-DepCode = substring(line1,72,8,"raw").
				           er-PersonID = substring(line1,80,10,"raw").
				           er-Amt = substring(line1,90,10,"raw").
				           er-Pct = substring(line1,100,6,"raw").
				           er-desc = substring(line1,106,50,"raw").  /*xiami070215*/
				           find xxepre_det where xxepre_domain = domain
				                             and xxepre_nbr = er-nbr
				                             /*and xxepre_reid = er-RegID*/ no-error.
				           /*if not avail xxepre_det then do:*/
				              find last xxepredet no-lock where xxepredet.xxepre_domain = domain
				                                        and xxepredet.xxepre_nbr = er-nbr no-error.
				              if not avail xxepredet then rcln = 1.
				              else rcln = xxepredet.xxepre_erln + 1.
				              create xxepre_det .
				              assign xxepre_det.xxepre_domain = domain
				                     xxepre_det.xxepre_nbr = trim(er-nbr)
				                     xxepre_det.xxepre_reid = trim(er-RegID)
				                     xxepre_det.xxepre_erln = rcln.
				           /*end.*/
				           assign xxepre_det.xxepre_retype = trim(er-RegType)
				                  xxepre_det.xxepre_cscd = trim(er-CustCode)
				                  xxepre_det.xxepre_encd = trim(er-Entity)
				                  xxepre_det.xxepre_dpcd = trim(er-DepCode)
				                  xxepre_det.xxepre_psid = trim(er-PersonID)
				                  xxepre_det.xxepre_amt  = deci(er-Amt)
				                  xxepre_det.xxepre_pct  = deci(er-Pct)
				                  xxepre_det.xxepre__chr02 = trim(er-exptype)
				                  xxepre_det.xxepre__chr01 = trim(er-desc)  /*xiami070215*/
				                  .
				        end.
				        
				    end.
				    input close.
					  os-copy value(tmp-alname) value(bakpath).
			      os-delete value(tmp-alname).						
				    
				end.
    end.
    
end.
