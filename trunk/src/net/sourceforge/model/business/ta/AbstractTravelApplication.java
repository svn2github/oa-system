/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.ta;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import net.sourceforge.model.admin.Currency;
import net.sourceforge.model.admin.Department;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.metadata.TravelApplicationBookStatus;
import net.sourceforge.model.metadata.TravelApplicationUrgent;
import net.sourceforge.model.metadata.YesNo;

/**
 * 该类代表travel_application表的一行记录
 */
public abstract class AbstractTravelApplication extends BaseTravelApplication implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    private String id;

    private Department department;

    private String title;

    private String description;

    private User requestor;

    private Date requestDate;

    private User booker;

    private User creator;

    private Date createDate;

    private TravelApplicationUrgent urgent;

    private TravelApplicationBookStatus bookStatus;
    
    private Date approveDate;
    
    private Date emailDate;
    
    private int emailTimes=0;
    
    private BigDecimal fee;
    
    private YesNo isOnTravel;
    
    private Currency currency;

    /**
     * 缺省构造函数
     */
    public AbstractTravelApplication() {
    }

    /**
     * 给定主键的构造函数
     * 
     * @param id
     */
    public AbstractTravelApplication(String id) {
        this.setId(id);
    }

    /**
     * @return Returns the booker.
     */
    public User getBooker() {
        return booker;
    }

    /**
     * @param booker
     *            The booker to set.
     */
    public void setBooker(User booker) {
        this.booker = booker;
    }

    /**
     * @return Returns the department.
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * @param department
     *            The department to set.
     */
    public void setDepartment(Department department) {
        this.department = department;
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
     * @return Returns the bookStatus.
     */
    public TravelApplicationBookStatus getBookStatus() {
        return bookStatus;
    }

    /**
     * @param bookStatus
     *            The bookStatus to set.
     */
    public void setBookStatus(TravelApplicationBookStatus bookStatus) {
        this.bookStatus = bookStatus;
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
     * @return Returns the creator.
     */
    public User getCreator() {
        return creator;
    }

    /**
     * @param creator
     *            The creator to set.
     */
    public void setCreator(User creator) {
        this.creator = creator;
    }

    /**
     * @return Returns the approveDate.
     */
    public Date getApproveDate() {
        return approveDate;
    }

    /**
     * @param approveDate
     *            The approveDate to set.
     */
    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
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
        if (!(rhs instanceof TravelApplication))
            return false;
        TravelApplication that = (TravelApplication) rhs;
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

    public TravelApplicationUrgent getUrgent() {
        return urgent;
    }

    public void setUrgent(TravelApplicationUrgent urgent) {
        this.urgent = urgent;
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

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    /**
     * @return Returns the isOnTravel.
     */
    public YesNo getIsOnTravel() {
        return isOnTravel;
    }

    /**
     * @param isOnTravel The isOnTravel to set.
     */
    public void setIsOnTravel(YesNo isOnTravel) {
        this.isOnTravel = isOnTravel;
    }

    /**
     * @return Returns the currency.
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * @param currency The currency to set.
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    
}
