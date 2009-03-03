/* Create By Britney Shen 20060714 */
{mfdtitle.i}
def var del-yn    like mfc_logical initial false.
def var fldname   like code_fldname init "oa-type".

form 
   code_fldname   colon 25
   code_value     colon 25 format "x(30)" skip(1)
   code_cmmt      colon 25
    with frame a side-labels width 80 .
    
repeat with frame a:
   if input code_value <> "" then
      find code_mstr  where code_mstr.code_domain = global_domain and
                            code_fldname = fldname
                        and code_value = input code_value
   no-lock no-error.
   if available code_mstr then
      recno = recid(code_mstr).
   disp fldname @ code_fldname.
      prompt-for
         code_value
      editing:

         {mfnp05.i code_mstr code_fldval
            " code_mstr.code_domain = global_domain and code_fldname  = fldname" code_value
            "input code_value"}

         if recno <> ? then
            display code_fldname code_value code_cmmt.

      end. /* editing: */
      
   find code_mstr  where code_mstr.code_domain = global_domain and
                        code_fldname = fldname
                    and code_value = input code_value
   no-error.

   if not available code_mstr then do:
      create code_mstr. 
      code_mstr.code_domain = global_domain.
      assign
         code_fldname code_value.
   end. /* if not available code_mstr then do: */

   update code_cmmt go-on(F5 CTRL-D).

    if lastkey = keycode("F5") or lastkey = keycode("CTRL-D") then do:
       del-yn = yes.
       {mfmsg01.i 11 1 del-yn}
      if del-yn then do:
         delete code_mstr.
         clear frame a.
      end.
    end.
    
end.