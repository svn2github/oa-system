/* GUI CONVERTED from poporc.p (converter v1.77) Thu Oct  2 02:01:17 2003 */
/* poporc.p  -  PURCHASE ORDER RECEIPT W/ SERIAL NUMBER CONTROL               */
/* Copyright 1986-2004 QAD Inc., Carpinteria, CA, USA.                        */
/* All rights reserved worldwide.  This is an unpublished work.               */
/* $Revision: 1.8.4.7 $                                                               */
/*                                                                            */
/*                                                                            */
/* REVISION: 1.0     LAST MODIFIED: 06/11/86    BY: PML                       */
/* REVISION: 1.0     LAST MODIFIED: 08/26/86    BY: EMB *10*                  */
/* REVISION: 1.0     LAST MODIFIED: 10/29/86    BY: EMB *39*                  */
/* REVISION: 2.1     LAST MODIFIED: 12/17/86    BY: PML *A1*                  */
/* REVISION: 2.1     LAST MODIFIED: 01/02/87    BY: PML *A13*                 */
/* REVISION: 2.1     LAST MODIFIED: 03/06/87    BY: PML *A38*                 */
/* REVISION: 2.1     LAST MODIFIED: 06/11/87    BY: PML *A63*                 */
/* REVISION: 2.1     LAST MODIFIED: 06/11/87    BY: WUG *A64*                 */
/* REVISION: 2.1     LAST MODIFIED: 07/23/87    BY: WUG *A77*                 */
/* REVISION: 2.1     LAST MODIFIED: 09/09/87    BY: PML *A91*                 */
/* REVISION: 2.1     LAST MODIFIED: 09/10/87    BY: WUG *A94*                 */
/* REVISION: 4.0     LAST MODIFIED: 03/04/88    BY: FLM *A108*                */
/* REVISION: 4.0     LAST MODIFIED: 01/18/88    BY: WUG *A151*                */
/* REVISION: 4.0     LAST MODIFIED: 07/20/88    BY: FLM *A333*                */
/* REVISION: 4.0     LAST MODIFIED: 09/26/88    BY: RL  *C0028                */
/* REVISION: 4.0     LAST MODIFIED: 02/21/89    BY: EMB *A649*                */
/* REVISION: 5.0     LAST MODIFIED: 02/27/89    BY: FLM *B054*                */
/* REVISION: 5.0     LAST MODIFIED: 03/02/89    BY: WUG *B058*                */
/* REVISION: 5.0     LAST MODIFIED: 03/21/89    BY: WUG *B071*                */
/* REVISION: 5.0     LAST MODIFIED: 06/23/89    BY: MLB *B159*                */
/* REVISION: 5.0     LAST MODIFIED: 01/26/90    BY: PML *B536*                */
/* REVISION: 5.0     LAST MODIFIED: 02/02/90    BY: FTB *B550*                */
/* REVISION: 5.0     LAST MODIFIED: 02/27/90    BY: EMB *B591*                */
/* REVISION: 6.0     LAST MODIFIED: 04/20/90    BY: WUG *D002*                */
/* REVISION: 5.0     LAST MODIFIED: 06/25/90    BY: RAM *B718*                */
/* REVISION: 6.0     LAST MODIFIED: 08/01/90    BY: RAM *D030*                */
/* REVISION: 6.0     LAST MODIFIED: 08/16/90    BY: SVG *D058*                */
/* REVISION: 6.0     LAST MODIFIED: 10/24/90    BY: pml *D143**/ /*Rev only   */
/* REVISION: 6.0     LAST MODIFIED: 10/24/90    BY: pml *D146**/ /*Rev only   */
/* REVISION: 6.0     LAST MODIFIED: 01/21/91    BY: RAM *D310*                */
/* REVISION: 6.0     LAST MODIFIED: 02/11/91    BY: RAM *D345*                */
/* REVISION: 6.0     LAST MODIFIED: 03/27/91    BY: RAM *D462*                */
/* REVISION: 6.0     LAST MODIFIED: 04/11/91    BY: RAM *D518*                */
/* REVISION: 6.0     LAST MODIFIED: 05/08/91    BY: MLV *D622*                */
/* REVISION: 6.0     LAST MODIFIED: 06/25/91    BY: RAM *D676*                */
/* REVISION: 6.0     LAST MODIFIED: 07/16/91    BY: RAM *D777*                */
/* REVISION: 6.0     LAST MODIFIED: 08/15/91    BY: pma *D829**/ /*Rev only   */
/* REVISION: 7.0     LAST MODIFIED: 11/11/91    BY: MLV *F029*                */
/* REVISION: 6.0     LAST MODIFIED: 11/13/91    BY: WUG *D858*                */
/* REVISION: 6.0     LAST MODIFIED: 09/20/91    BY: RAM *D871*                */
/* REVISION: 6.0     LAST MODIFIED: 11/13/91    BY: WUG *D887*                */
/* REVISION: 6.0     LAST MODIFIED: 11/14/91    BY: RAM *D952*                */
/* REVISION: 7.0     LAST MODIFIED: 11/19/91    BY: pma *F003*                */
/* REVISION: 7.0     LAST MODIFIED: 12/09/91    BY: RAM *F033*                */
/* REVISION: 7.0     LAST MODIFIED: 12/09/91    BY: RAM *F070*                */
/* REVISION: 7.0     LAST MODIFIED: 01/31/92    BY: RAM *F126*                */
/* REVISION: 7.0     LAST MODIFIED: 02/04/92    BY: RAM *F163*                */
/* REVISION: 7.0     LAST MODIFIED: 02/06/92    BY: RAM *F177*                */
/* REVISION: 7.0     LAST MODIFIED: 02/14/92    BY: SAS *F211*                */
/* REVISION: 7.0     LAST MODIFIED: 02/24/92    BY: sas *F211*                */
/* REVISION: 7.3     LAST MODIFIED: 10/27/94    BY: cdt *FS84*                */
/* REVISION: 7.3     LAST MODIFIED: 10/28/94    BY: cdt *FS95*                */
/* REVISION: 7.3     LAST MODIFIED: 11/08/94    BY: bcm *GO37*                */
/* REVISION: 8.5     LAST MODIFIED: 06/26/95    BY: tjs *J055*                */
/* REVISION: 7.3     LAST MODIFIED: 08/01/95    BY: jym *G0T7*                */
/* REVISION: 8.5     LAST MODIFIED: 12/04/95    BY: *J094* Tom Vogten         */
/* REVISION: 8.6     LAST MODIFIED: 06/11/96    BY: aal *K001*                */
/* REVISION: 8.6     LAST MODIFIED: 05/20/98    BY: *K1Q4* Alfred Tan         */
/* Old ECO marker removed, but no ECO header exists *F0PN*                    */
/* REVISION: 9.1      LAST MODIFIED: 06/15/00   BY: Hemanth Ebenezer *N0DK*   */
/* REVISION: 9.1      LAST MODIFIED: 08/13/00   BY: *N0KQ* myb                */
/* Revision: 1.8.4.5     BY: John Corda           DATE: 08/08/02  ECO: *N1QP* */
/* $Revision: 1.8.4.7 $   BY: Dorota Hohol      DATE: 10/02/03 ECO: *P112* */
/*                                                                            */
/*V8:ConvertMode=Maintenance                                                  */

/* poporc.p  moved to poporcm.p with F211*/
def input parameter	cim-file as char format "x(40)".
def input parameter log-file as char format "x(40)".

OUTPUT TO value(log-file).

{mfdtitle.i "1+ "}
{cxcustom.i "POPORC.P"}
{gldydef.i new}
{gldynrm.i new}

define new shared variable porec       like mfc_logical                no-undo.
define new shared variable ports       as   character                  no-undo.
define new shared variable is-return   like mfc_logical                no-undo.
define new shared variable tax_tr_type like tx2_tax_type  initial "21" no-undo.
{&POPORC-P-TAG1}

/* Let poporcm.p know that we're receiving purchase orders. */
assign
   ports     = ""
   porec     = yes
   is-return = no.
   

DO TRANSACTION ON ERROR UNDO,RETRY.
    INPUT FROM value(cim-file).

batchrun = YES.

{gprun.i ""poporcm.p""}

INPUT CLOSE.

/*UNDO,RETRY.*/
END.
OUTPUT CLOSE.

