package com.aof.web.struts.form.business.po;

import com.aof.web.struts.form.BaseSessionQueryForm;



public class PurchaseOrderItemQueryForm extends BaseSessionQueryForm {
    private String id;
    private String itemSpec;
    private String purchaseRequest_id;
    private boolean includeReceivedItem;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return Returns the includeReceivedItem.
     */
    public boolean isIncludeReceivedItem() {
        return includeReceivedItem;
    }
    /**
     * @param includeReceivedItem The includeReceivedItem to set.
     */
    public void setIncludeReceivedItem(boolean includeReceivedItem) {
        this.includeReceivedItem = includeReceivedItem;
    }
    /**
     * @return Returns the itemSpec.
     */
    public String getItemSpec() {
        return itemSpec;
    }
    /**
     * @param itemSpec The itemSpec to set.
     */
    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }
    /**
     * @return Returns the purchaseRequest_id.
     */
    public String getPurchaseRequest_id() {
        return purchaseRequest_id;
    }
    /**
     * @param purchaseRequest_id The purchaseRequest_id to set.
     */
    public void setPurchaseRequest_id(String purchaseRequest_id) {
        this.purchaseRequest_id = purchaseRequest_id;
    }
    

}
