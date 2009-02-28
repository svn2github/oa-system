/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.form.admin;

import com.aof.web.struts.form.BaseSessionQueryForm;

/**
 * ²éÑ¯PriceµÄForm
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class PriceQueryForm extends BaseSessionQueryForm {

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
    private String hotel_id;

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

    /* property */
    private String room;

    private String price;

    private String discount;

    private String description;

    private String enabled;

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
     * @return Returns the discount.
     */
    public String getDiscount() {
        return discount;
    }

    /**
     * @param discount
     *            The discount to set.
     */
    public void setDiscount(String discount) {
        this.discount = discount;
    }

    /**
     * @return Returns the enabled.
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     *            The enabled to set.
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    /**
     * @return Returns the price.
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price
     *            The price to set.
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * @return Returns the room.
     */
    public String getRoom() {
        return room;
    }

    /**
     * @param room
     *            The room to set.
     */
    public void setRoom(String room) {
        this.room = room;
    }
}
