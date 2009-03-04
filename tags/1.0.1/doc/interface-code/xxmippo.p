{mfdtitle.i}
/*Last Modify by Jean xu 04/08/2007 for double-byte issue, add raw while substring txt *J001*/ 
/*Last Modify by Jean xu 04/11/2007 for special account *J002*/ 

def var domain as char.
def var line as char format "x(38)".
def var outline like line.
def var line1 as char format "x(200)".
def var imp-yn as logi.
def var ln     as inte.
def var rcln   as inte.
def var sp-acc  as logical. /*xiami0215*/

DEF VAR in-file AS CHAR FORMAT "x(40)" LABEL "Import file name".
DEF VAR in-path AS CHAR FORMAT "x(40)" LABEL "Import path".
DEF VAR bakpath AS CHAR FORMAT "x(40)" LABEL "Backup path".
DEF VAR errpath AS CHAR FORMAT "x(40)" LABEL "Error path".

def var pm-Stat as char format "x(1)".  /*1,Master File;2,Detail File;3,Recharge File*/
def var pm-Type as char format "x(1)".  /*1, new PO; 2, cancel PO*/
def var pm-PONo as char format "x(16)".  /*PO(2) + Site No.(3) + yyMMdd(6) + sequence No.(5)*/
def var pm-Desc as char format "x(50)".
def var pm-SuppCode as char format "x(8)".
def var pm-PurCurr as char format "x(8)".
def var pm-PurAmt  as char format "x(14)".    /*deci13(2)*/
def var pm-Crtid as char format "x(20)".
def var pm-CrtDate as char format "x(8)".   /*date yyyyMMdd*/
def var pm-Ponbr as char format "x(20)".  /*ERP PO nbr*/

def var pd-Stat as char format "x(1)".   /*1,Master File;2,Detail File;3,Recharge File*/
def var pd-Type as char format "x(1)".   /*1, new PO; 2, cancel PO*/
def var pd-PONo as char format "x(16)".
def var pd-ItemID as char format "x(10)".  /*<> Pod line*/
def var pd-PurType  as char format "x(8)".
def var pd-Desc as char format "x(50)".
def var pd-Price  as char format "x(13)".   /*deci 12(2)*/
def var pd-Qty as char format "x(7)".     /*int*/
def var pd-Amt  as char format "x(14)".   /*deci 13(2)*/
def var pd-DueDate  as char format "x(8)".  /*date yyyyMMdd*/
def var pd-RegFlag as char format "x(1)".  /*0, do not need recharge; 1, need recharge*/
def var pd-DepCode as char format "x(8)".
def var pd-BuyFor as char format "x(10)".  /*Person ID*/
def var pd-alloid as char format "x(20)". /*Allocation ID*/

def var pr-Stat as char format "x(1)".  /*1,Master File;2,Detail File;3,Recharge File*/
def var pr-Type as char format "x(1)".  /*1, new PO; 2, cancel PO*/
def var pr-PONo as char format "x(16)".
def var pr-ItemID as char format "x(10)".
def var pr-RegID as char format "x(20)".
def var pr-RegType as char format "x(1)".  /*1, Customer;2 Entity;3 Person ID*/
def var pr-CustCode as char format "x(17)".
def var pr-Entity as char format "x(8)".
def var pr-DepCode as char format "x(8)".
def var pr-PersonID as char format "x(10)".
def var pr-Amt as char format "x(14)".  /*deci 13(2)*/
def var pr-Pct as char format "x(6)".  /*deci 5(2)*/

def var project as char.
def var fl   as char format "x(60)".
def var tp   as char.

def temp-table tmp
    field tmp-fname as char format "x(70)"
    field tmp-alname as char format "x(70)"
    field tmp-stat as char
index indx01 tmp-fname.

def buffer xxpod for xxpod_det .
def buffer xxpore for xxpore_det.
    
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
    title "PO Import".

repeat:  
   for each tmp:
       delete tmp.
   end.
   find xxoaif_ctrl where xxoaif_domain = global_domain no-error.
    in-file = "po/po_20060221205308991.txt".
    DISP in-path bakpath errpath WITH FRAME a.
    UPDATE in-file WITH FRAME a.
    if substring(in-path,length(in-path),1) <> "/"
       then in-path = in-path + "/".
    if in-file = "po/" or in-file = "po" then do:
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
				    input from value(tmp-alname).
				    repeat:
				        import unformatted line1.
				        if substring(line1,1,1) = "1" then do:
				           pm-Stat = substring(line1,1,1,"raw").
				           pm-Type = substring(line1,2,1,"raw").
				           pm-PONo = substring(line1,3,16,"raw").
				           pm-Desc = substring(line1,19,50).
				           pm-SuppCode = substring(line1,69,8,"raw").
				           pm-PurCurr = substring(line1,77,8,"raw").
				           pm-PurAmt  = substring(line1,85,14,"raw").
				           pm-Crtid = substring(line1,99,20,"raw").
				           pm-CrtDate = substring(line1,119,8,"raw").
				           pm-Ponbr = substring(line1,127,20,"raw").
				           find xxpo_mstr where xxpo_domain = domain
				                            and xxpo_nbr    = pm-PONO
				                            no-error.
				           if not avail xxpo_mstr then do:
				              create xxpo_mstr.
				              assign xxpo_domain = domain
				                     xxpo_nbr = pm-PONO.
				           end.
				           assign xxpo_desc = trim(pm-Desc)
				                  xxpo_supp = trim(pm-SuppCode)
				                  xxpo_purcurr = trim(pm-PurCurr)
				                  xxpo_amt  = deci(pm-PurAmt)
				                  xxpo_crt_user = trim(pm-Crtid)
				                  xxpo_ponbr = trim(pm-Ponbr)
				                  xxpo__dte01 = today
				                  .
				           if pm-CrtDate <> "" then 
				            xxpo_crt_date = date(int(substring(pm-CrtDate,5,2)),int(substring(pm-CrtDate,7,2)),int(substring(pm-CrtDate,1,4))).
				                  
				        end.
				        else if substring(line1,1,1,"raw") = "2" then do:
				           pd-Stat = substring(line1,1,1,"raw").
				           pd-Type = substring(line1,2,1,"raw").
				           pd-PONo = substring(line1,3,16,"raw").
				           pd-ItemID = substring(line1,19,10,"raw").
				           pd-PurType  = substring(line1,29,8,"raw").
				           pd-Desc = substring(line1,168,24,"raw").
				           pd-Price  = substring(line1,87,13,"raw").
				           pd-Qty = substring(line1,100,7,"raw").
				           pd-Amt  = substring(line1,107,14,"raw").
				           pd-DueDate  = substring(line1,121,8,"raw").           
				           pd-RegFlag = substring(line1,129,1,"raw").
				           pd-DepCode = substring(line1,130,8,"raw").
				           pd-BuyFor = substring(line1,138,10,"raw").
				           pd-alloid = substring(line1,148,20,"raw").
				           /*pd-DepCode = substring(line1,129,8).
				           pd-BuyFor = substring(line1,137,10).*/
				           if not xxoaif__log[2] or pd-BuyFor = "" then do:
						           find xxpod_det where xxpod_domain = domain
						                            and xxpod_nbr = pd-PONo 
						                            and xxpod_item = pd-alloid
						                            /*and xxpod__int01 = inte(pd-alloid)*/
						                            /*and xxpod_item = pd-ItemID*/ no-error.
						           if not avail xxpod_det then do:
						              find last xxpod no-lock where xxpod.xxpod_domain = domain
						                                        and xxpod.xxpod_nbr = pd-PONo no-error.
						              if not avail xxpod then ln = 1.
						              else ln = xxpod.xxpod_line + 1.
						              create xxpod_det.
						              assign xxpod_det.xxpod_domain = domain
						                     xxpod_det.xxpod_nbr = trim(pd-PONo)
						                     /*xxpod_det.xxpod_item = trim(pd-ItemID)*/
						                     xxpod_det.xxpod_item = trim(pd-alloid)
						                     xxpod_det.xxpod_line = ln.
						           end.
				               assign xxpod_det.xxpod_oatype = trim(pd-PurType)
				                      xxpod_det.xxpod_desc = trim(pd-Desc)
				                      xxpod_det.xxpod_price = deci(pd-Price)
				                      xxpod_det.xxpod_qty = int(pd-qty)
				                      xxpod_det.xxpod_amt = deci(pd-Amt)
				                      xxpod_det.xxpod_re_fg = if pd-RegFlag = "0" then no else yes
				                      xxpod_det.xxpod_dept = trim(pd-DepCode)
				                      xxpod_det.xxpod_buyfor = trim(pd-BuyFor)
				                      /*xxpod_det.xxpod__int01 = inte(pd-alloid)*/
				                      xxpod_det.xxpod__int01 = inte(pd-ItemID).
				               if pd-DueDate <> "" then xxpod_det.xxpod_due_dt = date(int(substring(pd-DueDate,5,2)),int(substring(pd-DueDate,7,2)),int(substring(pd-DueDate,1,4))).
				           end.
				           else do:
				              sp-acc = no.
				              find first pj_mstr no-lock where pj_domain = global_domain
				                                           and pj__qadc01 = pd-BuyFor no-error.
				              if avail pj_mstr then project = pj_project.
				              
				              /*xiami070215*/
	                     find first xxmap_mstr no-lock where xxmap_domain = global_domain
	                               and xxmap_type = trim(pd-PurType)
	                               no-error.
						           if avail xxmap_mstr then do:
							            find first ac_mstr no-lock where ac_domain = global_domain 
							                                         and ac_code = xxmap_dr_acc
							                                         and ac__log01 no-error.
							            if avail ac_mstr and ac__log01 then sp-acc = yes.
						           end.
                      /*xiami070215*/
                      
				              find first xxpc_mstr no-lock where xxpc_domain = domain 
				                                           and xxpc_project = project
				                                           and xxpc_year = year(today)
				                                           and xxpc_month = month(today) no-error.
/*J002*				              if not avail xxpc_mstr and sp-acc /*xiami070215*/ then do:	*/
/*J002*/				         if not avail xxpc_mstr or sp-acc then do:
							           find xxpod_det where xxpod_det.xxpod_domain = domain
							                            and xxpod_det.xxpod_nbr = pd-PONo 
							                            and xxpod_det.xxpod_item = pd-alloid no-error.
							           if not avail xxpod_det then do:
							              find last xxpod no-lock where xxpod.xxpod_domain = domain
							                                        and xxpod.xxpod_nbr = pd-PONo no-error.
							              if not avail xxpod then ln = 1.
							              else ln = xxpod.xxpod_line + 1.
							              create xxpod_det.
							              assign xxpod_det.xxpod_domain = domain
							                     xxpod_det.xxpod_nbr = trim(pd-PONo)
							                     /*xxpod_det.xxpod_item = trim(pd-ItemID)*/
							                     xxpod_det.xxpod_item = trim(pd-alloid)
							                     xxpod_det.xxpod_line = ln.
							           end.
					               assign xxpod_det.xxpod_oatype = trim(pd-PurType)
					                      xxpod_det.xxpod_desc = trim(pd-Desc)
					                      xxpod_det.xxpod_price = deci(pd-Price)
					                      xxpod_det.xxpod_qty = int(pd-qty)
					                      xxpod_det.xxpod_amt = deci(pd-Amt)
					                      xxpod_det.xxpod_re_fg = if pd-RegFlag = "0" then no else yes
					                      xxpod_det.xxpod_dept = trim(pd-DepCode)
					                      xxpod_det.xxpod_buyfor = trim(pd-BuyFor)
					                      /*xxpod_det.xxpod__int01 = inte(pd-alloid)*/
					                      xxpod_det.xxpod__int01 = inte(pd-ItemID)
					                      .
					               if pd-DueDate <> "" then xxpod_det.xxpod_due_dt = date(int(substring(pd-DueDate,5,2)),int(substring(pd-DueDate,7,2)),int(substring(pd-DueDate,1,4))).
				              end.
				              else do:
							            def var totamt as deci.
							            def var totamt2 as deci.
							            def var subamt as deci.
							            totamt = deci(pd-Price).
							            totamt2 = 0.
						              for each xxpc_mstr no-lock where xxpc_domain = domain
						                                           and xxpc_project = project
						                                           and xxpc_year = year(today)
						                                           and xxpc_month = month(today)
						                                           break by xxpc_project:
								              subamt = round(deci(pd-Price) * xxpc_pct / 100,2).
							                 if last-of(xxpc_project) then do:
							                    subamt = totamt - totamt2.
							                 end.
							                 totamt2 = totamt2 + subamt.
								              find last xxpod no-lock where xxpod.xxpod_domain = domain
								                                        and xxpod.xxpod_nbr = pd-PONo no-error.
								              if not avail xxpod then ln = 1.
								              else ln = xxpod.xxpod_line + 1.
								              create xxpod_det.
								              assign xxpod_det.xxpod_domain = domain
								                     xxpod_det.xxpod_nbr = trim(pd-PONo)
								                     /*xxpod_det.xxpod_item = trim(pd-ItemID)*/
								                     xxpod_det.xxpod_item = trim(pd-alloid)
								                     xxpod_det.xxpod_line = ln.
								               assign xxpod_det.xxpod_oatype = trim(pd-PurType)
								                      xxpod_det.xxpod_desc = trim(pd-Desc)
								                      /*xxpod_det.xxpod_price = deci(pd-Price) * xxpc_pct / 100*/
								                      xxpod_det.xxpod_price = subamt
								                      xxpod_det.xxpod_qty = int(pd-qty)
								                      xxpod_det.xxpod_amt = deci(pd-Amt) * xxpc_pct / 100
								                      xxpod_det.xxpod_re_fg = if pd-RegFlag = "0" then no else yes
								                      xxpod_det.xxpod_dept = trim(pd-DepCode)
								                      xxpod_det.xxpod_buyfor = trim(pd-BuyFor)
								                      /*xxpod_det.xxpod__int01 = inte(pd-alloid)*/
								                      xxpod_det.xxpod__int01 = inte(pd-ItemID)
								                      xxpod_det.xxpod__chr01 = xxpc_cc.
								               if pd-DueDate <> "" then xxpod_det.xxpod_due_dt = date(int(substring(pd-DueDate,5,2)),int(substring(pd-DueDate,7,2)),int(substring(pd-DueDate,1,4))).
						              end.
						          end.
				           end.
				        end.
				        else if substring(line1,1,1,"raw") = "3" then do:
				           pr-Stat = substring(line1,1,1,"raw").
				           pr-Type = substring(line1,2,1,"raw").
				           pr-PONo = substring(line1,3,16,"raw").
				           pr-ItemID = substring(line1,19,10,"raw").
				           pr-RegID = substring(line1,29,20,"raw").
				           pr-RegType = substring(line1,49,1,"raw").
				           pr-CustCode = substring(line1,50,17,"raw").
				           pr-Entity = substring(line1,67,8,"raw").
				           pr-DepCode = substring(line1,75,8,"raw").
				           pr-PersonID = substring(line1,83,10,"raw").
				           pr-Amt = substring(line1,93,14,"raw").
				           pr-Pct = substring(line1,107,6,"raw").
				           find xxpore_det where xxpore_domain = domain
				                             and xxpore_nbr = pr-PONo
				                             and xxpore_item = pr-ItemID
				                             and xxpore_rcid = pr-RegID no-error.
				           if not avail xxpore_det then do:
				              find last xxpore no-lock where xxpore.xxpore_domain = domain
				                                        and xxpore.xxpore_nbr = pd-PONo no-error.
				              if not avail xxpore then rcln = 1.
				              else rcln = xxpore.xxpore_prln + 1.
				              create xxpore_det .
				              assign xxpore_det.xxpore_domain = domain
				                     xxpore_det.xxpore_nbr = trim(pr-PONo)
				                     xxpore_det.xxpore_item = trim(pr-ItemID)
				                     xxpore_det.xxpore_rcid = trim(pr-RegID)
				                     xxpore_det.xxpore_prln = rcln.
				           end.
				           assign xxpore_det.xxpore_rcty = trim(pr-RegType)
				                  xxpore_det.xxpore_cscd = trim(pr-CustCode)
				                  xxpore_det.xxpore_encd = trim(pr-Entity)
				                  xxpore_det.xxpore_dpcd = trim(pr-DepCode)
				                  xxpore_det.xxpore_psid = trim(pr-PersonID)
				                  xxpore_det.xxpore_amt  = deci(pr-Amt)
				                  xxpore_det.xxpore_pct  = deci(pr-Pct).
				        end.
				        
				    end.
				    input close.
					  os-copy value(tmp-alname) value(bakpath).
			      os-delete value(tmp-alname).						
				    
				end.
    end.
    
end.
