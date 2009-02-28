/*
 * Created Wed Oct 12 13:44:15 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.aof.model.admin;

import java.io.Serializable;

/**
 * A class that represents a row in the 'email' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class EmailBatchBody extends AbstractEmailBatchBody implements Serializable {
    /**
     * Simple constructor of EmailBody instances.
     */
    public EmailBatchBody() {
    }

    /**
     * Constructor of EmailBody instances given a simple primary key.
     * 
     * @param emailId
     */
    public EmailBatchBody(java.lang.Integer emailId) {
        super(emailId);
    }

}

