/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.ta;

import java.io.Serializable;

import net.sourceforge.model.business.BaseRecharge;

/**
 * 该类代表t_at_recharge表的一行记录
 */
public abstract class AbstractAirTicketRecharge extends BaseRecharge implements Serializable {
    private AirTicket airTicket;
    
    /**
     * 缺省构造函数
     */
    public AbstractAirTicketRecharge() {
    }

    /**
     * 给定主键的构造函数
     * 
     * @param id
     */
    public AbstractAirTicketRecharge(Integer id) {
        this.setId(id);
    }

    /**
     * @return Returns the airTicket.
     */
    public AirTicket getAirTicket() {
        return airTicket;
    }

    /**
     * @param airTicket The airTicket to set.
     */
    public void setAirTicket(AirTicket airTicket) {
        this.airTicket = airTicket;
    }

    /**
     * Implementation of the equals comparison on the basis of equality of the
     * primary key values.
     * 
     * @param rhs
     * @return boolean
     */
    public boolean equals(Object rhs) {
        if (rhs == null) return false;
        if (this == rhs) return true;
        if (!(rhs instanceof AirTicketRecharge)) return false;
        AirTicketRecharge that = (AirTicketRecharge) rhs;
        if (this.getId() != null) return this.getId().equals(that.getId());
        return that.getId() == null;
    }

}
