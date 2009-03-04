{mfdtitle.i}
def var domain like xxmap_domain.

def var type1 like xxtype_nbr.
def var type2 like xxtype_nbr.
def var x-type  like xxtype__chr01 label "Purchase/Expense".

form 
     type1 colon 25
     type2 colon 45 label {t001.i}
     x-type colon 25
     with frame a width 80 side-label.

domain = global_domain.

repeat:
     if type2 = hi_char then type2 = "".
     update 
                 type1 type2
                  x-type
                 with frame a .
     if type2 = "" then type2 = hi_char.
     {mfselbpr.i "terminal" 80}
     for each xxtype_mstr no-lock where xxtype_domain = domain
                                    and xxtype_nbr >= type1 and xxtype_nbr <= type2
                                    and (xxtype__chr01 begins x-type or x-type = ""),
         each xxmap_mstr no-lock where xxmap_domain = domain
                                   and xxmap_type = xxtype_nbr
         break by xxtype_domain by xxtype_nbr  
         WITH FRAME b DOWN WIDTH 180:
         if first-of(xxtype_nbr) then do:
            disp xxtype_nbr xxtype_desc xxtype__chr01 LABEL "Purchase/Expense"
                 with frame b.
         end.
         disp xxmap_dr_acc xxmap_dr_sub xxmap_dr_cc
              xxmap_cr_acc xxmap_cr_sub xxmap_cr_cc
              with frame b.
     end.
     {mfreset.i}
end.