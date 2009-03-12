/*
 * Created Wed Oct 12 13:44:15 CST 2005 by MyEclipse Hibernate Tool.
 */ 
package net.sourceforge.model.admin;

import java.io.Serializable;

/**
 * A class that represents a row in the 't_email' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class Email
    extends AbstractEmail
    implements Serializable
{
    /**
     * Simple constructor of TEmail instances.
     */
    public Email()
    {
    }

    /**
     * Constructor of TEmail instances given a simple primary key.
     * @param emailId
     */
    public Email(java.lang.Integer emailId)
    {
        super(emailId);
    }


}
