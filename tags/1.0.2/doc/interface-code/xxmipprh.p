{mfdtitle.i}
def var domain as char.
def var line as char format "x(38)".
def var outline like line.
def var line1 as char format "x(79)".
def var imp-yn as logi.

DEF VAR in-file AS CHAR FORMAT "x(40)" LABEL "Import file name".
DEF VAR in-path AS CHAR FORMAT "x(40)" LABEL "Import path".
DEF VAR bakpath AS CHAR FORMAT "x(40)" LABEL "Backup path".
DEF VAR errpath AS CHAR FORMAT "x(40)" LABEL "Error path".

def var prh-nbr as char format "x(16)".  /*EX(2) + Site No.(3) + yyMMdd(6) + sequence No.(5)*/
def var prh-item as char format "x(20)".
def var prh-rcqty as char format "x(7)".
def var prh-rcid as char format "x(10)".
def var prh-cfid as char format "x(10)".
def var prh-rcDate as char format "x(8)".   /*date yyyyMMdd*/
def var prh-cfDate as char format "x(8)".

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
    title "PO Receive Import".

repeat:  
    in-file = "prh/prh_20060221205310714.txt".
    DISP in-path bakpath errpath WITH FRAME a.
    UPDATE in-file WITH FRAME a.
    if substring(in-path,length(in-path),1) <> "/"
       then in-path = in-path + "/".
    if in-file = "prh/" or in-file = "prh" then do:
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
				        prh-nbr = substring(line1,1,16).
				        prh-item = substring(line1,17,20).
				        prh-rcqty = substring(line1,37,7).
				        prh-rcid = substring(line1,44,10).
				        prh-cfid = substring(line1,54,10).
				        prh-rcDate = substring(line1,64,8).
				        prh-cfDate = substring(line1,72,8).
				           find xxprh_hist where xxprh_domain = domain
				                             and xxprh_nbr   = prh-nbr
				                             and xxprh_item  = prh-item
				                            no-error.
				           if not avail xxprh_hist then do:
				              create xxprh_hist.
				              assign xxprh_domain = domain
				                     xxprh_nbr = prh-nbr
				                     xxprh_item = prh-item.
				           end.
				           if xxprh__chr01 = "" then do:
				              assign xxprh_rcv_qty = xxprh_rcv_qty + int(prh-rcqty).
				           end.
				           else do:
				              assign xxprh_rcv_qty = int(prh-rcqty).
				           end.
				           assign /*xxprh_rcv_qty = int(prh-rcqty)*/
				                  xxprh_rcv_id = trim(prh-rcid)
				                  xxprh_rcv_dt = date(int(substring(prh-rcDate,5,2)),int(substring(prh-rcDate,7,2)),int(substring(prh-rcDate,1,4)))
				                  xxprh_cf_id = trim(prh-cfid)
				                  xxprh_cf_dt = date(int(substring(prh-cfDate,5,2)),int(substring(prh-cfDate,7,2)),int(substring(prh-cfDate,1,4)))
				                  xxprh__dte01 = today
				                  xxprh__chr02 = xxprh__chr02 + " " + xxprh__chr01
				                  xxprh__chr01 = ""
				                  xxprh__dec01 = int(prh-rcqty) + xxprh__dec01
				                  .        
				    end.
				    input close.
					  os-copy value(tmp-alname) value(bakpath).
			      os-delete value(tmp-alname).						
				    
				end.
    end.
    
end.
