/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Thu Oct 11 15:04:41 CST 2005 by MyEclipse Hibernate Tool.
 */
package net.sourceforge.model.admin;

import java.util.Calendar;
import java.util.Date;

import net.sourceforge.model.metadata.GlobalMailReminderType;

public class GlobalMailReminder extends AbstractGlobalMailReminder {

    /**
     * Simple constructor of GlobalMailReminder instances.
     */
    public GlobalMailReminder() {
    }

    /**
     * Constructor of GlobalMailReminder instances given a simple primary key.
     * 
     * @param siteId
     */
    public GlobalMailReminder(GlobalMailReminderType type) {
        super(type);
    }

    /* Add customized code below */

    public Date getResponseDate(Date d)
    {
        Calendar c=Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE,-this.getDueDay());
        return c.getTime();
    }
    
    public Date getEmailDate(Date d)
    {
        Calendar c=Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE,-this.getIntervalDay());
        c.add(Calendar.HOUR,1);
        return c.getTime();
        
    }
}
