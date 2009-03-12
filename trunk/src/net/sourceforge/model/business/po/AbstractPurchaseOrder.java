/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import net.sourceforge.model.admin.Currency;
import net.sourceforge.model.admin.ExchangeRate;
import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.Supplier;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.metadata.ExportStatus;
import net.sourceforge.model.metadata.PurchaseOrderStatus;

/**
 * 该类代表purchase_order表的一行记录
 */
public abstract class AbstractPurchaseOrder extends BasePurchaseOrder implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    private String id;

    private Site site;

    private String erpNo;

    private String title;

    private String description;

    private Supplier supplier;

    private PurchaseSubCategory subCategory;

    private ExchangeRate exchangeRate;

    private Currency baseCurrency;

    private BigDecimal exchangeRateValue;

    private User createUser;

    private Date createDate;

    private Date requestDate;

    private ExportStatus exportStatus;

    private User purchaser;

    private String receiver;

    private String receiveAddress;

    private User inspector;
    
    private int rowVersion;
    
    private User confirmer;
    
    private Date approveDate;
    
    private int emailTimes;
    
    private Date emailDate;
    
    private Date confirmDate;

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public Date getEmailDate() {
        return emailDate;
    }

    public void setEmailDate(Date emailDate) {
        this.emailDate = emailDate;
    }

    public int getEmailTimes() {
        return emailTimes;
    }

    public void setEmailTimes(int emailTimes) {
        this.emailTimes = emailTimes;
    }

    public User getConfirmer() {
        return confirmer;
    }

    public void setConfirmer(User confirmer) {
        this.confirmer = confirmer;
    }

    public int getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(int rowVersion) {
        this.rowVersion = rowVersion;
    }

    /**
     * 缺省构造函数
     */
    public AbstractPurchaseOrder() {
    }

    /**
     * 给定主键的构造函数
     * 
     * @param id
     */
    public AbstractPurchaseOrder(String id) {
        this.setId(id);
    }

    /**
     * @return Returns the baseCurrency.
     */
    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    /**
     * @param baseCurrency
     *            The baseCurrency to set.
     */
    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    /**
     * @return Returns the createDate.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     *            The createDate to set.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return Returns the createUser.
     */
    public User getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     *            The createUser to set.
     */
    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    /**
     * @return Returns the requestDate.
     */
    public Date getRequestDate() {
        return requestDate;
    }

    /**
     * @param requestDate
     *            The requestDate to set.
     */
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * @return Returns the currency.
     */
    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    /**
     * @param currency
     *            The currency to set.
     */
    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Returns the erpNo.
     */
    public String getErpNo() {
        return erpNo;
    }

    /**
     * @param erpNo
     *            The erpNo to set.
     */
    public void setErpNo(String erpNo) {
        this.erpNo = erpNo;
    }

    /**
     * @return Returns the exchangeRate.
     */
    public BigDecimal getExchangeRateValue() {
        if (this.getStatus() == null)
            return null;
        if (this.getStatus().equals(PurchaseOrderStatus.CONFIRMED) || this.getStatus().equals(PurchaseOrderStatus.RECEIVED))
            return exchangeRateValue;
        else
            return this.getExchangeRate().getExchangeRate();
    }

    /**
     * @param exchangeRate
     *            The exchangeRate to set.
     */
    public void setExchangeRateValue(BigDecimal exchangeRate) {
        this.exchangeRateValue = exchangeRate;
    }

    /**
     * @return Returns the exportStatus.
     */
    public ExportStatus getExportStatus() {
        return exportStatus;
    }

    /**
     * @param exportStatus
     *            The exportStatus to set.
     */
    public void setExportStatus(ExportStatus exportStatus) {
        this.exportStatus = exportStatus;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(String id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * @return Returns the purchaser.
     */
    public User getPurchaser() {
        return purchaser;
    }

    /**
     * @param purchaser
     *            The purchaser to set.
     */
    public void setPurchaser(User purchaser) {
        this.purchaser = purchaser;
    }

    /**
     * @return Returns the site.
     */
    public Site getSite() {
        return site;
    }

    /**
     * @param site
     *            The site to set.
     */
    public void setSite(Site site) {
        this.site = site;
    }

    /**
     * @return Returns the subCategory.
     */
    public PurchaseSubCategory getSubCategory() {
        return subCategory;
    }

    /**
     * @param subCategory
     *            The subCategory to set.
     */
    public void setSubCategory(PurchaseSubCategory subCategory) {
        this.subCategory = subCategory;
    }

    /**
     * @return Returns the supplier.
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * @param supplier
     *            The supplier to set.
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    /**
     * @return Returns the title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return Returns the receiveAddress.
     */
    public String getReceiveAddress() {
        return receiveAddress;
    }

    /**
     * @param receiveAddress
     *            The receiveAddress to set.
     */
    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    /**
     * @return Returns the receiver.
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * @param receiver
     *            The receiver to set.
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * @return Returns the inspector.
     */
    public User getInspector() {
        return inspector;
    }

    /**
     * @param inspector
     *            The inspector to set.
     */
    public void setInspector(User inspector) {
        this.inspector = inspector;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    /**
     * Implementation of the equals comparison on the basis of equality of the
     * primary key values.
     * 
     * @param rhs
     * @return boolean
     */
    public boolean equals(Object rhs) {
        if (rhs == null)
            return false;
        if (this == rhs)
            return true;
        if (!(rhs instanceof PurchaseOrder))
            return false;
        PurchaseOrder that = (PurchaseOrder) rhs;
        if (this.getId() != null)
            return this.getId().equals(that.getId());
        return that.getId() == null;
    }

    /**
     * Implementation of the hashCode method conforming to the Bloch pattern
     * with the exception of array properties (these are very unlikely primary
     * key types).
     * 
     * @return int
     */
    public int hashCode() {
        if (this.hashValue == 0) {
            int result = 17;
            int itemIdValue = this.getId() == null ? 0 : this.getId().hashCode();
            result = result * 37 + itemIdValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }
}
