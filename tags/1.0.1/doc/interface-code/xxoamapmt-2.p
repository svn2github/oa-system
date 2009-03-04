{mfdtitle.i}
def var domain   like xxmap_domain.
def var x-type   like xxmap_type.
def var x-dr-acc like xxmap_dr_acc.
def var x-dr-sub like xxmap_dr_sub.
def var x-dr-cc  like xxmap_dr_cc.
def var x-cr-acc like xxmap_cr_acc.
def var x-cr-sub like xxmap_cr_sub.
def var x-cr-cc  like xxmap_cr_cc.
def var x-desc   as   char format "x(20)".

form
    x-type   colon 25 
    x-desc   colon 45 no-label
    skip(1)
    x-dr-acc colon 20  x-cr-acc colon 50
    x-dr-sub colon 20  x-cr-sub colon 50
    x-dr-cc  colon 20  x-cr-cc  colon 50
    with frame a side-labels width 80 
    title "OA&GL Account mapping".

domain = global_domain.

repeat with frame a:
    x-type = "".
    x-dr-acc = "".
    x-dr-sub = "".
    x-dr-cc  = "".
    x-cr-acc = "".
    x-cr-sub = "".
    x-cr-cc  = "".
    
    recno = ?.
    prompt-for x-type  EDITING :

        /* FIND NEXT/PREVIOUS RECORD */
        {mfnp01.i xxmap_mstr x-type  xxmap_type  domain  xxmap_domain
                xxmap_idx01}

               if recno <> ? then do:
                  x-type   = xxmap_type.
                  x-dr-acc = xxmap_dr_acc.
                  x-dr-sub = xxmap_dr_sub.
                  x-dr-cc  = xxmap_dr_cc.
                  x-cr-acc = xxmap_cr_acc.
                  x-cr-sub = xxmap_cr_sub.
                  x-cr-cc  = xxmap_cr_cc.
                  find xxtype_mstr no-lock where xxtype_domain = domain 
                                             and xxtype_nbr    = xxmap_type
                                             no-error.
                  if avail xxtype_mstr then x-desc = xxtype_desc.
                  else x-desc = "".
                  
                  display
                      x-type x-desc
                      x-dr-acc
                      x-dr-sub
                      x-dr-cc
                      x-cr-acc
                      x-cr-sub
                      x-cr-cc
                      .
               end.

            end. /* prompt-for */
      find first xxmap_mstr where xxmap_domain = domain 
                        and xxmap_type   = input x-type
                        no-error.
      if not avail xxmap_mstr then do:
         create xxmap_mstr.
         assign xxmap_domain = domain
                xxmap_type   = input x-type
                .
         x-dr-acc = "".
         x-dr-sub = "".
         x-dr-cc  = "".
         x-cr-acc = "".
         x-cr-sub = "".
         x-cr-cc  = "".
      end.
                  x-type   = xxmap_type.
                  x-dr-acc = xxmap_dr_acc.
                  x-dr-sub = xxmap_dr_sub.
                  x-dr-cc  = xxmap_dr_cc.
                  x-cr-acc = xxmap_cr_acc.
                  x-cr-sub = xxmap_cr_sub.
                  x-cr-cc  = xxmap_cr_cc.
                  find first xxtype_mstr no-lock where xxtype_domain = domain 
                                                   and xxtype_nbr    = xxmap_type
                                                   no-error.
                  if avail xxtype_mstr then x-desc = xxtype_desc.
                  else x-desc = "".
                  
                  display
                      x-type x-desc
                      x-dr-acc
                      x-dr-sub
                      x-dr-cc
                      x-cr-acc
                      x-cr-sub
                      x-cr-cc
                      .
      
      update x-dr-acc x-dr-sub x-dr-cc 
             x-cr-acc x-cr-sub x-cr-cc with frame a.
      find first ac_mstr no-lock where ac_domain = domain
                             AND (ac_code = x-dr-acc or x-dr-acc = "") no-error.
      find first sb_mstr no-lock where sb_domain = domain
                             AND (sb_sub = x-dr-sub or x-dr-sub = "") no-error.
      find first cc_mstr no-lock where cc_domain = domain
                             AND (cc_ctr = x-dr-cc or x-dr-cc = "") no-error.
                                         
      if not avail ac_mstr or not avail sb_mstr or not avail cc_mstr then do:
         message "Wrong DR account information , Please check again!".
         undo,retry.
      end.
      
      find first ac_mstr no-lock where ac_domain = domain
                             AND (ac_code = x-cr-acc or x-cr-acc = "") no-error.
      find first sb_mstr no-lock where sb_domain = domain
                             AND (sb_sub = x-cr-sub or x-cr-sub = "") no-error.
      find first cc_mstr no-lock where cc_domain = domain
                             AND (cc_ctr = x-cr-cc or x-cr-cc = "") no-error.                                         
      if not avail ac_mstr or not avail sb_mstr or not avail cc_mstr then do:
         message "Wrong CR account information , Please check again!".
         undo,retry.
       end.
   
      assign xxmap_dr_acc = x-dr-acc
             xxmap_dr_sub = x-dr-sub
             xxmap_dr_cc  = x-dr-cc.
      assign xxmap_cr_acc = x-cr-acc
             xxmap_cr_sub = x-cr-sub
             xxmap_cr_cc  = x-cr-cc.
end.
