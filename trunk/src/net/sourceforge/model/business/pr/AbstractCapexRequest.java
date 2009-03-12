/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import net.sourceforge.model.admin.Currency;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.metadata.CapexRequestStatus;
import net.sourceforge.model.metadata.CapexRequestType;
import net.sourceforge.model.metadata.YesNo;

/**
 * 该类代表capex_request表中的一行
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public abstract class AbstractCapexRequest implements Serializable {    
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** identifier field */
    private Integer id;

    /** persistent field */
    private CapexRequestType type;

    /** persistent field */
    private String title;

    /** nullable persistent field */
    private String description;

    /** persistent field */
    private CapexRequestStatus status;

    /** persistent field */
    private BigDecimal amount;

    /** persistent field */
    private Date createDate;
    
    /** persistent field */
    private Date approveDate;

    /** nullable persistent field */
    private Date requestDate;

    /** nullable persistent field */
    private String approveRequestId;

    /** persistent field */
    private Capex capex;

    /** persistent field */
    private User creator;

    /** persistent field */
    private User requestor;
    
    /** persistent field */
    private String activity;
    
    /** nullable persistent field */
    private Date postAuditDate;
    
    /** nullable persistent field */
    private Date firstExpenseDate;
    
    /** nullable persistent field */
    private Date lastExpenseDate;
    
    /** nullable persistent field */
    private Date completionDate;
    
    /** nullable persistent field */
    private User projectLeader;
    
    /** nullable persistent field */
    private String projectLeaderTitle;
    
    /** nullable persistent field */
    private String accountingCode;
    
    /** persistent field */
    private YesNo isCapexType;
    
    /** persistent field */
    private YesNo isAssetDisposalType;
    
    /** nullable persistent field */
    private String otherType;
    
    /** persistent field */
    private YesNo isNewImplantationReason;
    
    /** persistent field */
    private YesNo isCapacityIncreaseReason;
    
    /** persistent field */
    private YesNo isCostImprovementReason;
    
    /** persistent field */
    private YesNo isNewProductLineReason;
    
    /** persistent field */
    private YesNo isDownsizingReason;
    
    /** persistent field */
    private YesNo isHSEReason;
    
    /** nullable persistent field */
    private String otherReason;
    
    /** persistent field */
    private BigDecimal capexCapitalizedAmount;
    
    /** persistent field */
    private BigDecimal otherExpenseAmount;
    
    /** persistent field */
    private BigDecimal assetDisposalAmount;
    
    /** persistent field */
    private BigDecimal grossBookAmount;
    
    /** nullable persistent field */
    private String projectImpactNonOperating1;
    
    /** nullable persistent field */
    private String projectImpactNonOperating2;    
    
    /** nullable persistent field */
    private String projectImpactOther1;
    
    /** nullable persistent field */
    private String projectImpactOther2;
    
    /** nullable persistent field */
    private String projectImpactOther3;
    
    /** persistent field */
    private Currency referenceCurrency;
    
    /** persistent field */
    private BigDecimal referenceExchangeRate;
    
    /** nullable persistent field */
    private BigDecimal lastForecastAmount;
    
    /** nullable persistent field */
    private BigDecimal discountedCashFlowPayback;
    
    /** nullable persistent field */
    private BigDecimal internalRateOfReturn;
    
    /** nullable persistent field */
    private BigDecimal npvAmount;
    
    /** nullable persistent field */
    private BigDecimal discountRate;
    
    /** default constructor */
    public AbstractCapexRequest() {
    }

    /** minimal constructor */
    public AbstractCapexRequest(Integer id) {
        setId(id);
    }

    /**
     * @return Returns the amount.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount
     *            The amount to set.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return Returns the approveRequestId.
     */
    public String getApproveRequestId() {
        return approveRequestId;
    }

    /**
     * @param approveRequestId
     *            The approveRequestId to set.
     */
    public void setApproveRequestId(String approveRequestId) {
        this.approveRequestId = approveRequestId;
    }

    /**
     * @return Returns the capex.
     */
    public Capex getCapex() {
        return capex;
    }

    /**
     * @param capex
     *            The capex to set.
     */
    public void setCapex(Capex capex) {
        this.capex = capex;
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
     * @return Returns the id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    protected void setId(Integer id) {
        this.hashValue = 0;
        this.id = id;
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
     * @return Returns the creator.
     */
    public User getCreator() {
        return creator;
    }

    /**
     * @param creator The creator to set.
     */
    public void setCreator(User creator) {
        this.creator = creator;
    }

    /**
     * @return Returns the requestor.
     */
    public User getRequestor() {
        return requestor;
    }

    /**
     * @param requestor
     *            The requestor to set.
     */
    public void setRequestor(User requestor) {
        this.requestor = requestor;
    }

    /**
     * @return Returns the status.
     */
    public CapexRequestStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            The status to set.
     */
    public void setStatus(CapexRequestStatus status) {
        this.status = status;
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
     * @return Returns the type.
     */
    public CapexRequestType getType() {
        return type;
    }

    /**
     * @param type
     *            The type to set.
     */
    public void setType(CapexRequestType type) {
        this.type = type;
    }

    /**
     * @return Returns the accountingCode.
     */
    public String getAccountingCode() {
        return accountingCode;
    }

    /**
     * @param accountingCode The accountingCode to set.
     */
    public void setAccountingCode(String accountingCode) {
        this.accountingCode = accountingCode;
    }

    /**
     * @return Returns the activity.
     */
    public String getActivity() {
        return activity;
    }

    /**
     * @param activity The activity to set.
     */
    public void setActivity(String activity) {
        this.activity = activity;
    }

    /**
     * @return Returns the assetDisposalAmount.
     */
    public BigDecimal getAssetDisposalAmount() {
        return assetDisposalAmount;
    }

    /**
     * @param assetDisposalAmount The assetDisposalAmount to set.
     */
    public void setAssetDisposalAmount(BigDecimal assetDisposalAmount) {
        this.assetDisposalAmount = assetDisposalAmount;
    }

    /**
     * @return Returns the capexCapitalizedAmount.
     */
    public BigDecimal getCapexCapitalizedAmount() {
        return capexCapitalizedAmount;
    }

    /**
     * @param capexCapitalizedAmount The capexCapitalizedAmount to set.
     */
    public void setCapexCapitalizedAmount(BigDecimal capexCapitalizedAmount) {
        this.capexCapitalizedAmount = capexCapitalizedAmount;
    }

    /**
     * @return Returns the completionDate.
     */
    public Date getCompletionDate() {
        return completionDate;
    }

    /**
     * @param completionDate The completionDate to set.
     */
    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    /**
     * @return Returns the discountedCashFlowPayback.
     */
    public BigDecimal getDiscountedCashFlowPayback() {
        return discountedCashFlowPayback;
    }

    /**
     * @param discountedCashFlowPayback The discountedCashFlowPayback to set.
     */
    public void setDiscountedCashFlowPayback(BigDecimal discountedCashFlowPayback) {
        this.discountedCashFlowPayback = discountedCashFlowPayback;
    }

    /**
     * @return Returns the discountRate.
     */
    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    /**
     * @param discountRate The discountRate to set.
     */
    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    /**
     * @return Returns the firstExpenseDate.
     */
    public Date getFirstExpenseDate() {
        return firstExpenseDate;
    }

    /**
     * @param firstExpenseDate The firstExpenseDate to set.
     */
    public void setFirstExpenseDate(Date firstExpenseDate) {
        this.firstExpenseDate = firstExpenseDate;
    }

    /**
     * @return Returns the grossBookAmount.
     */
    public BigDecimal getGrossBookAmount() {
        return grossBookAmount;
    }

    /**
     * @param grossBookAmount The grossBookAmount to set.
     */
    public void setGrossBookAmount(BigDecimal grossBookAmount) {
        this.grossBookAmount = grossBookAmount;
    }

    /**
     * @return Returns the hashValue.
     */
    public int getHashValue() {
        return hashValue;
    }

    /**
     * @param hashValue The hashValue to set.
     */
    public void setHashValue(int hashValue) {
        this.hashValue = hashValue;
    }

    /**
     * @return Returns the internalRateOfReturn.
     */
    public BigDecimal getInternalRateOfReturn() {
        return internalRateOfReturn;
    }

    /**
     * @param internalRateOfReturn The internalRateOfReturn to set.
     */
    public void setInternalRateOfReturn(BigDecimal internalRateOfReturn) {
        this.internalRateOfReturn = internalRateOfReturn;
    }

    /**
     * @return Returns the isAssetDisposalType.
     */
    public YesNo getIsAssetDisposalType() {
        return isAssetDisposalType;
    }

    /**
     * @param isAssetDisposalType The isAssetDisposalType to set.
     */
    public void setIsAssetDisposalType(YesNo isAssetDisposalType) {
        this.isAssetDisposalType = isAssetDisposalType;
    }

    /**
     * @return Returns the isCapacityIncreaseReason.
     */
    public YesNo getIsCapacityIncreaseReason() {
        return isCapacityIncreaseReason;
    }

    /**
     * @param isCapacityIncreaseReason The isCapacityIncreaseReason to set.
     */
    public void setIsCapacityIncreaseReason(YesNo isCapacityIncreaseReason) {
        this.isCapacityIncreaseReason = isCapacityIncreaseReason;
    }

    /**
     * @return Returns the isCapexType.
     */
    public YesNo getIsCapexType() {
        return isCapexType;
    }

    /**
     * @param isCapexType The isCapexType to set.
     */
    public void setIsCapexType(YesNo isCapexType) {
        this.isCapexType = isCapexType;
    }

    /**
     * @return Returns the isCostImprovementReason.
     */
    public YesNo getIsCostImprovementReason() {
        return isCostImprovementReason;
    }

    /**
     * @param isCostImprovementReason The isCostImprovementReason to set.
     */
    public void setIsCostImprovementReason(YesNo isCostImprovementReason) {
        this.isCostImprovementReason = isCostImprovementReason;
    }

    /**
     * @return Returns the isDownsizingReason.
     */
    public YesNo getIsDownsizingReason() {
        return isDownsizingReason;
    }

    /**
     * @param isDownsizingReason The isDownsizingReason to set.
     */
    public void setIsDownsizingReason(YesNo isDownsizingReason) {
        this.isDownsizingReason = isDownsizingReason;
    }

    /**
     * @return Returns the isHSEReason.
     */
    public YesNo getIsHSEReason() {
        return isHSEReason;
    }

    /**
     * @param isHSEReason The isHSEReason to set.
     */
    public void setIsHSEReason(YesNo isHSEReason) {
        this.isHSEReason = isHSEReason;
    }

    /**
     * @return Returns the isNewImplantationReason.
     */
    public YesNo getIsNewImplantationReason() {
        return isNewImplantationReason;
    }

    /**
     * @param isNewImplantationReason The isNewImplantationReason to set.
     */
    public void setIsNewImplantationReason(YesNo isNewImplantationReason) {
        this.isNewImplantationReason = isNewImplantationReason;
    }

    /**
     * @return Returns the isNewProductLineReason.
     */
    public YesNo getIsNewProductLineReason() {
        return isNewProductLineReason;
    }

    /**
     * @param isNewProductLineReason The isNewProductLineReason to set.
     */
    public void setIsNewProductLineReason(YesNo isNewProductLineReason) {
        this.isNewProductLineReason = isNewProductLineReason;
    }

    /**
     * @return Returns the lastExpenseDate.
     */
    public Date getLastExpenseDate() {
        return lastExpenseDate;
    }

    /**
     * @param lastExpenseDate The lastExpenseDate to set.
     */
    public void setLastExpenseDate(Date lastExpenseDate) {
        this.lastExpenseDate = lastExpenseDate;
    }

    /**
     * @return Returns the lastForecastAmount.
     */
    public BigDecimal getLastForecastAmount() {
        return lastForecastAmount;
    }

    /**
     * @param lastForecastAmount The lastForecastAmount to set.
     */
    public void setLastForecastAmount(BigDecimal lastForecastAmount) {
        this.lastForecastAmount = lastForecastAmount;
    }

    /**
     * @return Returns the npvAmount.
     */
    public BigDecimal getNpvAmount() {
        return npvAmount;
    }

    /**
     * @param npvAmount The npvAmount to set.
     */
    public void setNpvAmount(BigDecimal npvAmount) {
        this.npvAmount = npvAmount;
    }

    /**
     * @return Returns the otherExpenseAmount.
     */
    public BigDecimal getOtherExpenseAmount() {
        return otherExpenseAmount;
    }

    /**
     * @param otherExpenseAmount The otherExpenseAmount to set.
     */
    public void setOtherExpenseAmount(BigDecimal otherExpenseAmount) {
        this.otherExpenseAmount = otherExpenseAmount;
    }

    /**
     * @return Returns the otherReason.
     */
    public String getOtherReason() {
        return otherReason;
    }

    /**
     * @param otherReason The otherReason to set.
     */
    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason;
    }

    /**
     * @return Returns the otherType.
     */
    public String getOtherType() {
        return otherType;
    }

    /**
     * @param otherType The otherType to set.
     */
    public void setOtherType(String otherType) {
        this.otherType = otherType;
    }

    /**
     * @return Returns the postAuditDate.
     */
    public Date getPostAuditDate() {
        return postAuditDate;
    }

    /**
     * @param postAuditDate The postAuditDate to set.
     */
    public void setPostAuditDate(Date postAuditDate) {
        this.postAuditDate = postAuditDate;
    }

    /**
     * @return Returns the projectImpactNonOperating1.
     */
    public String getProjectImpactNonOperating1() {
        return projectImpactNonOperating1;
    }

    /**
     * @param projectImpactNonOperating1 The projectImpactNonOperating1 to set.
     */
    public void setProjectImpactNonOperating1(String projectImpactNonOperating1) {
        this.projectImpactNonOperating1 = projectImpactNonOperating1;
    }

    /**
     * @return Returns the projectImpactNonOperating2.
     */
    public String getProjectImpactNonOperating2() {
        return projectImpactNonOperating2;
    }

    /**
     * @param projectImpactNonOperating2 The projectImpactNonOperating2 to set.
     */
    public void setProjectImpactNonOperating2(String projectImpactNonOperating2) {
        this.projectImpactNonOperating2 = projectImpactNonOperating2;
    }

    /**
     * @return Returns the projectImpactOther1.
     */
    public String getProjectImpactOther1() {
        return projectImpactOther1;
    }

    /**
     * @param projectImpactOther1 The projectImpactOther1 to set.
     */
    public void setProjectImpactOther1(String projectImpactOther1) {
        this.projectImpactOther1 = projectImpactOther1;
    }

    /**
     * @return Returns the projectImpactOther2.
     */
    public String getProjectImpactOther2() {
        return projectImpactOther2;
    }

    /**
     * @param projectImpactOther2 The projectImpactOther2 to set.
     */
    public void setProjectImpactOther2(String projectImpactOther2) {
        this.projectImpactOther2 = projectImpactOther2;
    }

    /**
     * @return Returns the projectImpactOther3.
     */
    public String getProjectImpactOther3() {
        return projectImpactOther3;
    }

    /**
     * @param projectImpactOther3 The projectImpactOther3 to set.
     */
    public void setProjectImpactOther3(String projectImpactOther3) {
        this.projectImpactOther3 = projectImpactOther3;
    }

    /**
     * @return Returns the projectLeader.
     */
    public User getProjectLeader() {
        return projectLeader;
    }

    /**
     * @param projectLeader The projectLeader to set.
     */
    public void setProjectLeader(User projectLeader) {
        this.projectLeader = projectLeader;
    }

    /**
     * @return Returns the referenceCurrency.
     */
    public Currency getReferenceCurrency() {
        return referenceCurrency;
    }

    /**
     * @param referenceCurrency The referenceCurrency to set.
     */
    public void setReferenceCurrency(Currency referenceCurrency) {
        this.referenceCurrency = referenceCurrency;
    }

    /**
     * @return Returns the referenceExchangeRate.
     */
    public BigDecimal getReferenceExchangeRate() {
        return referenceExchangeRate;
    }

    /**
     * @param referenceExchangeRate The referenceExchangeRate to set.
     */
    public void setReferenceExchangeRate(BigDecimal referenceExchangeRate) {
        this.referenceExchangeRate = referenceExchangeRate;
    }

    /**
     * @return Returns the projectLeaderTitle.
     */
    public String getProjectLeaderTitle() {
        return projectLeaderTitle;
    }

    /**
     * @param projectLeaderTitle The projectLeaderTitle to set.
     */
    public void setProjectLeaderTitle(String projectLeaderTitle) {
        this.projectLeaderTitle = projectLeaderTitle;
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
        if (!(rhs instanceof CapexRequest))
            return false;
        CapexRequest that = (CapexRequest) rhs;
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
            int idValue = this.getId() == null ? 0 : this.getId().hashCode();
            result = result * 37 + idValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }

    /**
     * @return Returns the approveDate.
     */
    public Date getApproveDate() {
        return approveDate;
    }

    /**
     * @param approveDate The approveDate to set.
     */
    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

}
