/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.form.business.ta;

import net.sourceforge.web.struts.form.BaseSessionQueryForm;

/**
 * ²éÑ¯Travel ApplicationµÄForm
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class TravelApplicationQueryForm extends BaseSessionQueryForm {

    /* id */
    private String id;

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
        this.id = id;
    }

    /* keyPropertyList */

    /* kmtoIdList */

    /* mtoIdList */
    private String department_id;
    
    private String site_id;

    private String fromCity_id;

    private String toCity_id;
    
    private String toProvince_id;
    
    private String toCountry_id;
    
    private String fromDate1;
    
    private String fromDate2;
    
    private String toDate1;
    
    private String toDate2;
    
    private String requestDate1;
    
    private String requestDate2;
    
    private String createDate1;
    
    private String createDate2;

    private String hotel_id;

    private String price_id;

    private String requestor_id;
    
    private String requestor_name;

    private String booker_id;
    
    /**
     * @return Returns the booker_id.
     */
    public String getBooker_id() {
        return booker_id;
    }

    /**
     * @param booker_id
     *            The booker_id to set.
     */
    public void setBooker_id(String booker_id) {
        this.booker_id = booker_id;
    }

    /**
     * @return Returns the department_id.
     */
    public String getDepartment_id() {
        return department_id;
    }

    /**
     * @param department_id
     *            The department_id to set.
     */
    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    /**
     * @return Returns the fromCity_id.
     */
    public String getFromCity_id() {
        return fromCity_id;
    }

    /**
     * @param fromCity_id
     *            The fromCity_id to set.
     */
    public void setFromCity_id(String fromCity_id) {
        this.fromCity_id = fromCity_id;
    }

    /**
     * @return Returns the hotel_id.
     */
    public String getHotel_id() {
        return hotel_id;
    }

    /**
     * @param hotel_id
     *            The hotel_id to set.
     */
    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    /**
     * @return Returns the price_id.
     */
    public String getPrice_id() {
        return price_id;
    }

    /**
     * @param price_id
     *            The price_id to set.
     */
    public void setPrice_id(String price_id) {
        this.price_id = price_id;
    }

    /**
     * @return Returns the requestor_id.
     */
    public String getRequestor_id() {
        return requestor_id;
    }

    /**
     * @param requestor_id
     *            The requestor_id to set.
     */
    public void setRequestor_id(String requestor_id) {
        this.requestor_id = requestor_id;
    }

    /**
     * @return Returns the toCity_id.
     */
    public String getToCity_id() {
        return toCity_id;
    }

    /**
     * @param toCity_id
     *            The toCity_id to set.
     */
    public void setToCity_id(String toCity_id) {
        this.toCity_id = toCity_id;
    }

    /* property */
    private String title;

    private String description;

    private String status;
    
    private String urgent;
    
    private String bookStatus;

    private String hotelName;

    private String roomDescription;

    private String checkInDate;

    private String checkOutDate;

    private String travellingMode;

    private String singleOrReturn;

    private String fromDate;

    private String fromTime;

    private String toDate;

    private String toTime;

    private String requestDate;

    private String approveRequestId;

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
     * @return Returns the checkInDate.
     */
    public String getCheckInDate() {
        return checkInDate;
    }

    /**
     * @param checkInDate
     *            The checkInDate to set.
     */
    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    /**
     * @return Returns the checkOutDate.
     */
    public String getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * @param checkOutDate
     *            The checkOutDate to set.
     */
    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
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
     * @return Returns the fromDate.
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate
     *            The fromDate to set.
     */
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return Returns the fromTime.
     */
    public String getFromTime() {
        return fromTime;
    }

    /**
     * @param fromTime
     *            The fromTime to set.
     */
    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    /**
     * @return Returns the hotelName.
     */
    public String getHotelName() {
        return hotelName;
    }

    /**
     * @param hotelName
     *            The hotelName to set.
     */
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    /**
     * @return Returns the requestDate.
     */
    public String getRequestDate() {
        return requestDate;
    }

    /**
     * @param requestDate
     *            The requestDate to set.
     */
    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * @return Returns the roomDescription.
     */
    public String getRoomDescription() {
        return roomDescription;
    }

    /**
     * @param roomDescription
     *            The roomDescription to set.
     */
    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    /**
     * @return Returns the singleOrReturn.
     */
    public String getSingleOrReturn() {
        return singleOrReturn;
    }

    /**
     * @param singleOrReturn
     *            The singleOrReturn to set.
     */
    public void setSingleOrReturn(String singleOrReturn) {
        this.singleOrReturn = singleOrReturn;
    }

    /**
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            The status to set.
     */
    public void setStatus(String status) {
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
     * @return Returns the toDate.
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * @param toDate
     *            The toDate to set.
     */
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    /**
     * @return Returns the toTime.
     */
    public String getToTime() {
        return toTime;
    }

    /**
     * @param toTime
     *            The toTime to set.
     */
    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    /**
     * @return Returns the travellingMode.
     */
    public String getTravellingMode() {
        return travellingMode;
    }

    /**
     * @param travellingMode
     *            The travellingMode to set.
     */
    public void setTravellingMode(String travellingMode) {
        this.travellingMode = travellingMode;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }

    public String getToCountry_id() {
        return toCountry_id;
    }

    public void setToCountry_id(String toCountry_id) {
        this.toCountry_id = toCountry_id;
    }

    public String getToProvince_id() {
        return toProvince_id;
    }

    public void setToProvince_id(String toProvince_id) {
        this.toProvince_id = toProvince_id;
    }

    public String getFromDate1() {
        return fromDate1;
    }

    public void setFromDate1(String fromDate1) {
        this.fromDate1 = fromDate1;
    }

    public String getFromDate2() {
        return fromDate2;
    }

    public void setFromDate2(String fromDate2) {
        this.fromDate2 = fromDate2;
    }

    public String getToDate1() {
        return toDate1;
    }

    public void setToDate1(String toDate1) {
        this.toDate1 = toDate1;
    }

    public String getToDate2() {
        return toDate2;
    }

    public void setToDate2(String toDate2) {
        this.toDate2 = toDate2;
    }

    public String getRequestor_name() {
        return requestor_name;
    }

    public void setRequestor_name(String requestor_name) {
        this.requestor_name = requestor_name;
    }

    public String getRequestDate1() {
        return requestDate1;
    }

    public void setRequestDate1(String requestDate1) {
        this.requestDate1 = requestDate1;
    }

    public String getRequestDate2() {
        return requestDate2;
    }

    public void setRequestDate2(String requestDate2) {
        this.requestDate2 = requestDate2;
    }

    public String getCreateDate1() {
        return createDate1;
    }

    public void setCreateDate1(String createDate1) {
        this.createDate1 = createDate1;
    }

    public String getCreateDate2() {
        return createDate2;
    }

    public void setCreateDate2(String createDate2) {
        this.createDate2 = createDate2;
    }

}
