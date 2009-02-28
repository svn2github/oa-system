package com.aof.web.struts.repeatsubmit;

import javax.servlet.jsp.JspException;

import org.apache.struts.Globals;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.FormTag;

import com.aof.web.struts.action.SecureActionMapping;

/**
 * the struts form tag for Preventing Repeat Submit 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class PreventRepeatSubmitFormTag extends FormTag {
    protected void lookup() throws JspException {
        super.lookup();
        if(mapping instanceof SecureActionMapping)
        {
            if(mapping.getScope().equals("session"))
            {
                beanName=mapping.getName();
                beanScope="request";
            }
        }
    }
    /**
     * get token from request instead of session
     */
    protected String renderToken() {
        StringBuffer results = new StringBuffer();

        String token = (String) pageContext.getRequest().getAttribute(Globals.TRANSACTION_TOKEN_KEY);

        if (token != null) {
            results.append("<div><input type=\"hidden\" name=\"");
            results.append(Constants.TOKEN_KEY);
            results.append("\" value=\"");
            results.append(token);
            results.append("\" />");
            results.append("</div>");
        }

        return results.toString();
    }

}
