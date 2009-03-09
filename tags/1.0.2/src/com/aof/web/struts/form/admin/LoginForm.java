/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.form.admin;

import org.apache.struts.action.ActionForm;

/**
 * µÇÂ¼»­ÃæµÄForm
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class LoginForm extends ActionForm {
    private String loginName;

    private String password;

    private String locale;

    /**
     * @return Returns the locale.
     */
    public String getLocale() {
        return locale;
    }

    /**
     * @param locale
     *            The locale to set.
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    /**
     * @return Returns the loginName.
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @param loginName
     *            The loginName to set.
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
