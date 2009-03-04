{mfdtitle.i}
def var domain as char.
def var line as char format "x(38)".
def var outline like line.
def var line1 as char format "x(600)".
def var imp-yn as logi.
def var path as char .
def var file as char.

def var Code as char format "x(8)".
def var Name as char format "x(50)".
def var Address1 as char format "x(50)".
def var Address2 as char format "x(50)".
def var Address3 as char format "x(50)".
def var Country as char format "x(50)".
def var City as char format "x(50)".
def var Post as char format "x(9)".
def var Attention1 as char format "x(24)".
def var Attention2 as char format "x(24)".
def var telephone1 as char format "x(16)".
def var ext1 as char format "x(4)".
def var telephone2 as char format "x(16)".
def var ext2 as char format "x(4)".
def var Fax1 as char format "x(16)".
def var Fax2 as char format "x(16)".
def var Pur-Acc as char format "x(8)".
def var Pur-Subacc as char format "x(8)".
def var Pur-CC as char format "x(8)".
def var AP-Acc as char format "x(8)".
def var AP-Subacc as char format "x(8)".
def var AP-CC as char format "x(8)".
def var Bank as char format "x(8)".
def var Curr as char format "x(3)".
def var CrTerms as char format "x(8)".
def var Contact as char format "x(20)".

DEF VAR in-file AS CHAR FORMAT "x(40)" LABEL "Import file name".
DEF VAR in-path AS CHAR FORMAT "x(40)" LABEL "Import path".
DEF VAR bakpath AS CHAR FORMAT "x(40)" LABEL "Backup path".
DEF VAR errpath AS CHAR FORMAT "x(40)" LABEL "Error path".

def var fl   as char format "x(60)".
def var tp   as char.

def temp-table tmp
    field tmp-fname as char format "x(70)"
    field tmp-alname as char format "x(70)"
    field tmp-stat as char
index indx01 tmp-fname.

def temp-table tmps-stat
    field tmps-code as char
    field tmps-desc as char format "x(60)"
    .
    
domain = global_domain.

for first code_mstr no-lock where code_fldname = "path" 
                              and code_value = "Import path":
    path = code_cmmt.
end.
if substring(path,length(path),1) <> "/"
   then path = path + "/".
   
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
    title "Supplier Import".

repeat:  
    in-file = "supplier/supplier_20060621120001094.txt".
    DISP in-path bakpath errpath WITH FRAME a.
    UPDATE in-file WITH FRAME a.
    if substring(in-path,length(in-path),1) <> "/"
       then in-path = in-path + "/".
    if in-file = "supplier/" or in-file = "supplier" then do:
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
				        Code = substring(line1,1,8,"raw").
				        Name = substring(line1,9,50,"raw").
				        Address1 = substring(line1,59,50,"raw").
				        Address2 = substring(line1,109,50,"raw").
				        Address3 = substring(line1,159,50,"raw").
				        Country = substring(line1,209,50,"raw").
				        City = substring(line1,259,50,"raw").
				        Post = substring(line1,309,9,"raw").
				        Attention1 = substring(line1,318,24,"raw").
				        Attention2 = substring(line1,342,24,"raw").
				        telephone1 = substring(line1,366,16,"raw").
				        ext1 = substring(line1,382,4,"raw").
				        telephone2 = substring(line1,386,16,"raw").
				        ext2 = substring(line1,402,4,"raw").
				        Fax1 = substring(line1,406,16,"raw").
				        Fax2 = substring(line1,422,16,"raw").
				        Pur-Acc = substring(line1,438,8,"raw").
				        Pur-Subacc = substring(line1,446,8,"raw").
				        Pur-CC = substring(line1,454,8,"raw").
				        AP-Acc = substring(line1,462,8,"raw").
				        AP-Subacc = substring(line1,470,8,"raw").
				        AP-CC = substring(line1,478,8,"raw").
				        Bank = substring(line1,486,8,"raw").
				        Curr = substring(line1,494,3,"raw").
				        CrTerms = substring(line1,497,8,"raw").
				        Contact = substring(line1,505,20,"raw").
				        
				        disp code name country.
				        
				        find first tmps-stat where tmps-code = code no-error.
				        if not avail tmps-stat then do:
				           create tmps-stat.
				           assign tmps-code = code.
				        end.
				        else do:
				           assign tmps-desc = "Two Suppliers used one code! ".
				        end.
				        find first ctry_mstr no-lock where ctry_ctry_code = Country no-error.
				        if not avail ctry_mstr then do:
				           assign tmps-desc = tmps-desc + " No valid Country codes! ".
				        end.
				        find first cu_mstr no-lock where cu_curr = Curr 
				                                     and cu_active no-error.
				        if not avail cu_mstr then do:
				           assign tmps-desc = tmps-desc + " No valid Currency codes! ".
				        end.
				        disp tmps-desc.
				        if code <> "" and tmps-desc = "" then do:
				           find ad_mstr where ad_domain = domain 
				                          and ad_addr = code no-error.
				           if not avail ad_mstr then do:
				              create ad_mstr.
				              assign ad_domain = domain
				                     ad_addr = code
				                     ad_type = "supplier"
				                     ad__qad01 = "1"
				                     ad_date = today
				                     ad_userid = "System"
				                     .
				           end.
				           assign ad_name = name
				                  ad_line1 = Address1
				                  ad_line2 = Address2
				                  ad_line3 = Address3
				                  ad_ctry = country
				                  ad_country = ctry_country
				                  ad_city = City
				                  ad_zip = Post
				                  ad_attn = Attention1
				                  ad_attn2 = Attention2
				                  ad_phone = telephone1
				                  ad_ext = ext1
				                  ad_phone2 = telephone2
				                  ad_ext2 = ext2
				                  ad_fax = Fax1
				                  ad_fax2 = Fax2
				                  ad_user1 = Contact
				                  ad_mod_date = today
				                  ad_userid = global_userid
				                  .
				                  
				           find vd_mstr where vd_domain = domain 
				                          and vd_addr = code no-error.
				           if not avail vd_mstr then do:
				              create vd_mstr.
				              assign vd_domain = domain
				                     vd_addr = code
				                     .
				           end.
				           assign vd_sort = name
				                  vd_curr = curr
				                  vd_mod_date = today
				                  vd_userid = global_userid
				                  .
				        end.
				    end.
				    input close.
					  os-copy value(tmp-alname) value(bakpath).
			      os-delete value(tmp-alname).						
				    
				end.
    end.

end.
