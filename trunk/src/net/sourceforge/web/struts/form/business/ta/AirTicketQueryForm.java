/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.form.business.ta;

import org.apache.struts.action.ActionForm;

public class AirTicketQueryForm extends ActionForm {
    private String site_id;
    
    private String department_id;

    private String supplier_name;

    private String leave_datetime_from;

    private String leave_datetime_to;
    
    private String buyFor;
    
    private String is_on_travel;    
    
    private String include_received_air_ticket;

    /**
     * @return Returns the site_id.
     */
    public String getSite_id() {
        return site_id;
    }

    /**
     * @param site_id
     *            The site_id to set.
     */
    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }
    
    /**
     * @return Returns the leave_datetime_from.
     */
    public String getLeave_datetime_from() {
        return leave_datetime_from;
    }

    /**
     * @param leave_datetime_from The leave_datetime_from to set.
     */
    public void setLeave_datetime_from(String leave_datetime_from) {
        this.leave_datetime_from = leave_datetime_from;
    }

    /**
     * @return Returns the leave_datetime_to.
     */
    public String getLeave_datetime_to() {
        return leave_datetime_to;
    }

    /**
     * @param leave_datetime_to The leave_datetime_to to set.
     */
    public void setLeave_datetime_to(String leave_datetime_to) {
        this.leave_datetime_to = leave_datetime_to;
    }

    /**
     * @return Returns the supplier_name.
     */
    public String getSupplier_name() {
        return supplier_name;
    }

    /**
     * @param supplier_name
     *            The supplier_name to set.
     */
    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    /**
     * @return Returns the buyFor.
     */
    public String getBuyFor() {
        return buyFor;
    }

    /**
     * @param buyFor The buyFor to set.
     */
    public void setBuyFor(String buyFor) {
        this.buyFor = buyFor;
    }

    /**
     * @return Returns the department_id.
     */
    public String getDepartment_id() {
        return department_id;
    }

    /**
     * @param department_id The department_id to set.
     */
    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    /**
     * @return Returns the is_on_travel.
     */
    public String getIs_on_travel() {
        return is_on_travel;
    }

    /**
     * @param is_on_travel The is_on_travel to set.
     */
    public void setIs_on_travel(String is_on_travel) {
        this.is_on_travel = is_on_travel;
    }

    /**
     * @return Returns the include_received_air_ticket.
     */
    public String getInclude_received_air_ticket() {
        return include_received_air_ticket;
    }

    /**
     * @param include_received_air_ticket The include_received_air_ticket to set.
     */
    public void setInclude_received_air_ticket(String include_received_air_ticket) {
        this.include_received_air_ticket = include_received_air_ticket;
    }        
}
