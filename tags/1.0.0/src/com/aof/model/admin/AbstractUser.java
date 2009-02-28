/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 *
 * Created Tue Jun 14 10:11:27 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.aof.model.admin;

import java.io.Serializable;
import java.util.Date;

import com.aof.model.metadata.EnabledDisabled;
import com.aof.model.metadata.Gender;

public abstract class AbstractUser implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String loginName;

    /** nullable persistent field */
    private String name;

    /** persistent field */
    private String password;

    /** persistent field */
    private Gender gender;

    /** persistent field */
    private String email;

    /** nullable persistent field */
    private String telephone;

    /** nullable persistent field */
    private Site primarySite;

    /** nullable persistent field */
    private String passwdHintQuestion;

    /** nullable persistent field */
    private String passwdHintAnswer;

    /** nullable persistent field */
    private Date lastLoginTime;

    /** persistent field */
    private int loginFailedCount;

    /** nullable persistent field */
    private String locale;

    /** persistent field */
    private EnabledDisabled enabled;

    /**
     * Simple constructor of AbstractUser instances.
     */
    public AbstractUser() {
    }

    /**
     * Constructor of AbstractUser instances given a simple primary
     * key.
     * 
     * @param id
     */
    public AbstractUser(Integer id) {
        this.setId(id);
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Site getPrimarySite() {
        return this.primarySite;
    }

    public void setPrimarySite(Site primarySite) {
        this.primarySite = primarySite;
    }

    public String getPasswdHintQuestion() {
        return this.passwdHintQuestion;
    }

    public void setPasswdHintQuestion(String passwdHintQuestion) {
        this.passwdHintQuestion = passwdHintQuestion;
    }

    public String getPasswdHintAnswer() {
        return this.passwdHintAnswer;
    }

    public void setPasswdHintAnswer(String passwdHintAnswer) {
        this.passwdHintAnswer = passwdHintAnswer;
    }

    public Date getLastLoginTime() {
        return this.lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getLoginFailedCount() {
        return this.loginFailedCount;
    }

    public void setLoginFailedCount(int loginFailedCount) {
        this.loginFailedCount = loginFailedCount;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public EnabledDisabled getEnabled() {
        return this.enabled;
    }

    public void setEnabled(EnabledDisabled enabled) {
        this.enabled = enabled;
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
        if (!(rhs instanceof User)) return false;
        User that = (User) rhs;
        if (this.getId() != null) return this.getId().equals(that.getId());
        return that.getId() == null;
    }

    /**
     * Implementation of the hashCode method conforming to the Bloch pattern
     * with the exception of array properties (these are very unlikely primary
     * key types).
     * 
     * @return int
     */
    public int hashCode() {
        if (this.hashValue == 0) {
            int result = 17;
            int idValue = this.getId() == null ? 0 : this.getId().hashCode();
            result = result * 37 + idValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }
}