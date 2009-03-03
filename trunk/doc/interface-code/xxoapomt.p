/* GUI CONVERTED from popomt.p (converter v1.77) Tue Jul 22 07:01:35 2003 */
/* popomt.p - PURCHASE ORDER MAINTENANCE                                      */
/* Copyright 1986-2004 QAD Inc., Carpinteria, CA, USA.                        */
/* All rights reserved worldwide.  This is an unpublished work.               */
/* $Revision: 1.3.4.4 $                                                       */
/*V8:ConvertMode=Maintenance                                                  */
/*                                                                            */
/* Revision: 6.0  BY:SVG                DATE:08/13/90         ECO: *D058*     */
/* Revision: 7.0  BY:AFS   (rev only)   DATE:07/01/92         ECO: *F727*     */
/* Revision: 8.6  BY:Alfred Tan         DATE:05/20/98         ECO: *K1Q4*     */
/* Revision: 9.1  BY:Annasaheb Rahane   DATE:03/24/00         ECO: *N08T*     */
/* Revision: 1.3.4.2     BY: Mark B. Smith       DATE:11/08/99   ECO: *N059*  */
/* Old ECO marker removed, but no ECO header exists *F0PN*                    */
/* Revision: 1.3.4.3     BY: Jean Miller         DATE: 12/11/01  ECO: *P03N*  */
/* $Revision: 1.3.4.4 $  BY: Jean Miller         DATE: 01/07/02  ECO: *P040*  */
/******************************************************************************/
/* All patch markers and commented out code have been removed from the source */
/* code below. For all future modifications to this file, any code which is   */
/* no longer required should be deleted and no in-line patch markers should   */
/* be added.  The ECO marker should only be included in the Revision History. */
/******************************************************************************/

{mfdeclre.i}
def input parameter	cim-file as char format "x(40)".
def input parameter log-file as char format "x(40)".

OUTPUT TO value(log-file).

DO TRANSACTION ON ERROR UNDO,RETRY.
    INPUT FROM value(cim-file).

batchrun = YES.
/* INPUT OF FALSE MEANS THIS IS NOT A BLANKET PURCHASE ORDER */
{gprun.i ""pomt.p"" "(input false)"}.

INPUT CLOSE.

/*UNDO,RETRY.*/
END.
OUTPUT CLOSE.
