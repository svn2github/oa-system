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

import net.sourceforge.model.metadata.SiteMailReminderType;

public class SiteMailReminder extends AbstractSiteMailReminder {

    /**
     * Simple constructor of SiteMailReminder instances.
     */
    public SiteMailReminder() {
    }

    /**
     * Constructor of SiteMailReminder instances given a simple primary key.
     * 
     * @param site
     * @param type
     */
    public SiteMailReminder(Site site, SiteMailReminderType type) {
        super(site, type);
    }

    /* Add customized code below */
    public Date getApproveDate(Date d)
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
