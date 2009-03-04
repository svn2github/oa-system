/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.ta;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.aof.model.admin.City;
import com.aof.model.admin.ExchangeRate;
import com.aof.model.admin.PurchaseType;
import com.aof.model.admin.Supplier;
import com.aof.model.business.po.PurchaseOrderItem;
import com.aof.model.metadata.AirTicketStatus;
import com.aof.model.metadata.FlightClass;
import com.aof.model.metadata.YesNo;

/**
 * 该类代表air_ticket表的一行记录
 */
public abstract class AbstractAirTicket implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    private Integer id;

    private TravelApplication travelApplication;

    private Supplier supplier;

    private PurchaseType purchaseType;

    private ExchangeRate exchangeRate;

    private BigDecimal exchangeRateValue;

    private BigDecimal price;

    private YesNo isRecharge;

    private AirTicketStatus status;

    private PurchaseOrderItem cancelPoItem;

    private String flightNo;

    private FlightClass flightClass;

    private City fromCity;

    private City toCity;

    private Date departTime;

    private Date arriveTime;

    private PurchaseOrderItem poItem;
    
    private YesNo boardingPassRecevied;



    /**
     * 缺省构造函数
     */
    public AbstractAirTicket() {
    }

    /**
     * 给定主键的构造函数
     * 
     * @param id
     */
    public AbstractAirTicket(Integer id) {
        this.setId(id);
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
    public void setId(Integer id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * @return Returns the exchangeRate.
     */
    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    /**
     * @param exchangeRate
     *            The exchangeRate to set.
     */
    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**
     * @return Returns the exchangeRateValue.
     */
    public BigDecimal getExchangeRateValue() {
        return exchangeRateValue;
    }

    /**
     * @param exchangeRateValue
     *            The exchangeRate Valueto set.
     */
    public void setExchangeRateValue(BigDecimal exchangeRateValue) {
        this.exchangeRateValue = exchangeRateValue;
    }

    /**
     * @return Returns the isRecharge.
     */
    public YesNo getIsRecharge() {
        return isRecharge;
    }

    /**
     * @param isRecharge
     *            The isRecharge to set.
     */
    public void setIsRecharge(YesNo isRecharge) {
        this.isRecharge = isRecharge;
    }

    /**
     * @return Returns the leaveArriveTime.
     */
    public Date getArriveTime() {
        return arriveTime;
    }

    /**
     * @param leaveArriveTime
     *            The leaveArriveTime to set.
     */
    public void setArriveTime(Date leaveArriveTime) {
        this.arriveTime = leaveArriveTime;
    }

    /**
     * @return Returns the leaveDepartTime.
     */
    public Date getDepartTime() {
        return departTime;
    }

    /**
     * @param leaveDepartTime
     *            The leaveDepartTime to set.
     */
    public void setDepartTime(Date leaveDepartTime) {
        this.departTime = leaveDepartTime;
    }

    /**
     * @return Returns the leaveFlightClass.
     */
    public FlightClass getFlightClass() {
        return flightClass;
    }

    /**
     * @param leaveFlightClass
     *            The leaveFlightClass to set.
     */
    public void setFlightClass(FlightClass leaveFlightClass) {
        this.flightClass = leaveFlightClass;
    }

    /**
     * @return Returns the leaveFlightNo.
     */
    public String getFlightNo() {
        return flightNo;
    }

    /**
     * @param leaveFlightNo
     *            The leaveFlightNo to set.
     */
    public void setFlightNo(String leaveFlightNo) {
        this.flightNo = leaveFlightNo;
    }

    /**
     * @return Returns the leaveFromCity.
     */
    public City getFromCity() {
        return fromCity;
    }

    /**
     * @param leaveFromCity
     *            The leaveFromCity to set.
     */
    public void setFromCity(City leaveFromCity) {
        this.fromCity = leaveFromCity;
    }

    /**
     * @return Returns the leavePOItem.
     */
    public PurchaseOrderItem getCancelPoItem() {
        return cancelPoItem;
    }

    /**
     * @param leavePOItem
     *            The leavePOItem to set.
     */
    public void setCancelPoItem(PurchaseOrderItem item) {
        this.cancelPoItem = item;
    }

    /**
     * @return Returns the leaveToCity.
     */
    public City getToCity() {
        return toCity;
    }

    /**
     * @param leaveToCity
     *            The leaveToCity to set.
     */
    public void setToCity(City leaveToCity) {
        this.toCity = leaveToCity;
    }

    /**
     * @return Returns the price.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price
     *            The price to set.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

   

    /**
     * @return Returns the returnPOItem.
     */
    public PurchaseOrderItem getPoItem() {
        return poItem;
    }

    /**
     * @param returnPOItem
     *            The returnPOItem to set.
     */
    public void setPoItem(PurchaseOrderItem item) {
        this.poItem = item;
    }

   

    /**
     * @return Returns the status.
     */
    public AirTicketStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            The status to set.
     */
    public void setStatus(AirTicketStatus status) {
        this.status = status;
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
     * @return Returns the travelApplication.
     */
    public TravelApplication getTravelApplication() {
        return travelApplication;
    }

    /**
     * @param travelApplication
     *            The travelApplication to set.
     */
    public void setTravelApplication(TravelApplication travelApplication) {
        this.travelApplication = travelApplication;
    }

    /**
     * @return Returns the purchaseType.
     */
    public PurchaseType getPurchaseType() {
        return purchaseType;
    }

    /**
     * @param purchaseType
     *            The purchaseType to set.
     */
    public void setPurchaseType(PurchaseType purchaseType) {
        this.purchaseType = purchaseType;
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
        if (!(rhs instanceof AirTicket))
            return false;
        AirTicket that = (AirTicket) rhs;
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

    /**
     * @return Returns the boardingPassRecevied.
     */
    public YesNo getBoardingPassRecevied() {
        return boardingPassRecevied;
    }

    /**
     * @param boardingPassRecevied The boardingPassRecevied to set.
     */
    public void setBoardingPassRecevied(YesNo boardingPassRecevied) {
        this.boardingPassRecevied = boardingPassRecevied;
    }
    
    
}
