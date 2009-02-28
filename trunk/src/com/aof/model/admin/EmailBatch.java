/*
 * Created Wed Oct 12 13:44:15 CST 2005 by MyEclipse Hibernate Tool.
 */ 
package com.aof.model.admin;

import java.io.Serializable;

/**
 * A class that represents a row in the 't_email' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class EmailBatch
    extends AbstractEmailBatch
    implements Serializable
{
    /**
     * Simple constructor of TEmail instances.
     */
    public EmailBatch()
    {
    }

    /**
     * Constructor of TEmail instances given a simple primary key.
     * @param emailId
     */
    public EmailBatch(java.lang.Integer emailId)
    {
        super(emailId);
    }


}
