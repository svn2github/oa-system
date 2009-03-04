{mfdtitle.i "oa+"}
def var x-typenbr   like xxtype_nbr.
def var x-desc      like xxtype_desc format "x(30)".
def var x-stat      like xxtype_stat.
/*def var x-type      as   logical       format "Purchase/Expense" label "Purchase/Expense".*/
def var x-type      like xxtype__chr01 label "Purchase/Expense" format "x(9)".
def var x-valid     like xxtype__log01 label "Invalidation".
def var domain      like xxtype_domain.
def var del-yn like mfc_logical initial no.

form
    x-typenbr colon 25
    skip(1)
    x-desc   colon 25 
    x-type   colon 25 
    skip(1)
    x-valid  colon 25
	  skip(1) 
    with frame a side-labels width 80
    title "Type Maintenance".
    
domain = global_domain.

REPEAT WITH FRAME a:
/*N014*/    recno = ?.
            prompt-for x-typenbr EDITING:

               /* FIND NEXT/PREVIOUS RECORD */
               {mfnp01.i xxtype_mstr x-typenbr  xxtype_nbr  domain xxtype_domain
                       xxtype_idx01}

               if recno <> ? then do:
                  x-typenbr = xxtype_nbr.
                  x-desc    = xxtype_desc.
                  x-valid   = xxtype__log01.
                  x-type    = xxtype__chr01.
                  /*if xxtype__chr01 = "Purchase" then x-type = yes.
                  else x-type = no.*/
                  display
                      x-typenbr
                      x-desc
                      x-type 
                      x-valid
                      with frame a.
               end.

            end. /* prompt-for */

    
    bcdparm = "".
    {mfquoter.i x-typenbr }
    
    find xxtype_mstr where xxtype_domain = domain and xxtype_nbr = INPUT x-typenbr no-error.
    if not avail xxtype_mstr then do:
       create xxtype_mstr.
       assign xxtype_domain = domain
              xxtype_nbr = INPUT x-typenbr.
    end.
    x-desc = xxtype_desc.
    x-valid = xxtype__log01.
    x-type  = xxtype__chr01.

    update x-desc 
           x-type VALIDATE ( lookup(x-type,"Expense,Purchase,travel,meal,hotel,telephone,allowance") > 0, "Please Choose Purchase or Expense" )
           help "Expense,Purchase,travel,meal,hotel,telephone,allowance"
           x-valid go-on(F5 CTRL-D).
    
    bcdparm = "".
    {mfquoter.i x-typenbr }
    {mfquoter.i x-desc }
    {mfquoter.i x-stat }
    {mfquoter.i x-type }
    {mfquoter.i x-valid }
    
    assign xxtype_desc = x-desc
           xxtype__log01 = x-valid
           xxtype__chr01 = x-type.
    if lastkey = keycode("F5") or lastkey = keycode("CTRL-D") 
       then do:
       del-yn = yes.
       message "Confirm to Delete the Type Code? " update del-yn.
       if del-yn then do:
          delete xxtype_mstr.
          clear frame a.
       end.
    end.
end.

