/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.domain;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import com.aof.model.admin.User;
import com.aof.utils.IpAddress;
import com.shcnc.struts.form.DateFormatter;

/**
 * 该类用于产生察看Session列表
 * @author ych
 * @version 1.0 (2005-10-08)
 */
public class SessionListView {
	
	private HttpSession session;
	private long accessTime;
	private long liveMinute=0;
	private long liveSecond=0;
	private User user;
	private IpAddress ip;
	private Date loginTime;
	
	private static final DateFormatter dfDisplayDate = new DateFormatter(
            java.util.Date.class, "yyyy-MM-dd hh:mm:ss");
	


    /**
     * @param accessTime  The accessTime to set.
     */
	public void setAccessTime(long accessTime) {
		this.accessTime=accessTime;
	}
	
    /**
     * @return Returns the accessTime.
     */
	public String getAccessTime() {
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(this.accessTime);
		return dfDisplayDate.format(calendar.getTime());
	}
	/**
	 * @return Returns the liveMinute.
	 */
	public long getLiveMinute() {
		return liveMinute;
	}
	/**
	 * @param liveMinute The liveMinute to set.
	 */
	public void setLiveMinute(long liveMinute) {
		this.liveMinute = liveMinute;
	}
	/**
	 * @return Returns the liveSecond.
	 */
	public long getLiveSecond() {
		return liveSecond;
	}
	/**
	 * @param liveSecond The liveSecond to set.
	 */
	public void setLiveSecond(long liveSecond) {
		this.liveSecond = liveSecond;
	}
	/**
	 * @return Returns the session.
	 */
	public HttpSession getSession() {
		return session;
	}
	/**
	 * @param session The session to set.
	 */
	public void setSession(HttpSession session) {
		this.session = session;
	}
	/**
	 * @return Returns the user.
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * @param user The user to set.
	 */
	public void setUser(User user) {
		this.user=user;
	}
	
    /**
     * @return Returns the ip.
     */
    public IpAddress getIp() {
        return ip;
    }

    /**
     * @param ip The ip to set.
     */
    public void setIp(IpAddress ip) {
        this.ip = ip;
    }
    /**
     * @return Returns the loginTime.
     */
    public String getLoginTime() {
        return dfDisplayDate.format(this.loginTime);
    }
    /**
     * @param loginTime The loginTime to set.
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime=loginTime;
    }
}
