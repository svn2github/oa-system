/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.form.admin;

import com.aof.web.struts.form.BaseSessionQueryForm;

/**
 * ²éÑ¯EmailµÄForm
 * @author ych
 * @version 1.0 (Nov 14, 2005)
 */
public class EmailQueryForm extends BaseSessionQueryForm {

	private String id;

	private String mailFrom;

	private String mailTo;

	private String subject;

	private String createTimeBegin;

	private String createTimeTo;

	private String sentTimeBegin;

	private String sentTimeTo;

	private String failCount;

	private String body;

	private String waitToSend;

	

	/**
     * @return Returns the body.
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body The body to set.
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return Returns the failCount.
     */
    public String getFailCount() {
        return failCount;
    }

    /**
     * @param failCount The failCount to set.
     */
    public void setFailCount(String failCount) {
        this.failCount = failCount;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Returns the mailFrom.
     */
    public String getMailFrom() {
        return mailFrom;
    }

    /**
     * @param mailFrom The mailFrom to set.
     */
    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    /**
     * @return Returns the mailTo.
     */
    public String getMailTo() {
        return mailTo;
    }

    /**
     * @param mailTo The mailTo to set.
     */
    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    /**
     * @return Returns the subject.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject The subject to set.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
	 * @return Returns the createTimeBegin.
	 */
	public String getCreateTimeBegin() {
		return createTimeBegin;
	}

	/**
	 * @param createTimeBegin
	 *            The createTimeBegin to set.
	 */
	public void setCreateTimeBegin(String createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}

	/**
	 * @return Returns the createTimeTo.
	 */
	public String getCreateTimeTo() {
		return createTimeTo;
	}

	/**
	 * @param createTimeTo
	 *            The createTimeTo to set.
	 */
	public void setCreateTimeTo(String createTimeTo) {
		this.createTimeTo = createTimeTo;
	}

	/**
	 * @return Returns the sentTimeBegin.
	 */
	public String getSentTimeBegin() {
		return sentTimeBegin;
	}

	/**
	 * @param sentTimeBegin
	 *            The sentTimeBegin to set.
	 */
	public void setSentTimeBegin(String sentTimeBegin) {
		this.sentTimeBegin = sentTimeBegin;
	}

	/**
	 * @return Returns the sentTimeTo.
	 */
	public String getSentTimeTo() {
		return sentTimeTo;
	}

	/**
	 * @param sentTimeTo
	 *            The sentTimeTo to set.
	 */
	public void setSentTimeTo(String sentTimeTo) {
		this.sentTimeTo = sentTimeTo;
	}

	

	/**
	 * @return Returns the waitToSend.
	 */
	public String getWaitToSend() {
		return waitToSend;
	}

	/**
	 * @param waitToSend The waitToSend to set.
	 */
	public void setWaitToSend(String waitToSend) {
		this.waitToSend = waitToSend;
	}

	
}
