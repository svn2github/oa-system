package net.sourceforge.web.struts.form.business.po;

import net.sourceforge.web.struts.form.BaseSessionQueryForm;


public class PurchaseOrderItemReceiptQueryForm extends BaseSessionQueryForm {

	/*id*/
	private String id;
	public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

	/*keyPropertyList*/

	/*kmtoIdList*/


	/*mtoIdList*/
	private String purchaseOrderItem_id;
	public String getPurchaseOrderItem_id() {
        return purchaseOrderItem_id;
    }
    public void setPurchaseOrderItem_id(String purchaseOrderItem_id) {
        this.purchaseOrderItem_id = purchaseOrderItem_id;
    }
	private String receiver1_id;
	public String getReceiver1_id() {
        return receiver1_id;
    }
    public void setReceiver1_id(String receiver1_id) {
        this.receiver1_id = receiver1_id;
    }
	private String receiver2_id;
	public String getReceiver2_id() {
        return receiver2_id;
    }
    public void setReceiver2_id(String receiver2_id) {
        this.receiver2_id = receiver2_id;
    }
      

	/*property*/
	private String receiveDate1;
	public String getReceiveDate1() {
        return receiveDate1;
    }
    public void setReceiveDate1(String receiveDate1) {
        this.receiveDate1 = receiveDate1;
    }
	private String receiveQty1;
	public String getReceiveQty1() {
        return receiveQty1;
    }
    public void setReceiveQty1(String receiveQty1) {
        this.receiveQty1 = receiveQty1;
    }
	private String receiveDate2;
	public String getReceiveDate2() {
        return receiveDate2;
    }
    public void setReceiveDate2(String receiveDate2) {
        this.receiveDate2 = receiveDate2;
    }
	private String receiveQty2;
	public String getReceiveQty2() {
        return receiveQty2;
    }
    public void setReceiveQty2(String receiveQty2) {
        this.receiveQty2 = receiveQty2;
    }
	private String exportStatus;
	public String getExportStatus() {
        return exportStatus;
    }
    public void setExportStatus(String exportStatus) {
        this.exportStatus = exportStatus;
    }
}
