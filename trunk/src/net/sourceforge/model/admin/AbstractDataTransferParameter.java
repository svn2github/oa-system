/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin;

import java.io.Serializable;
import java.util.Date;

import net.sourceforge.model.metadata.ExportFileType;

/**
 * A class that represents a row in the erp_tran table. You can customize the
 * behavior of this class by editing the class, {@link DataTransferParameter()}.
 * 
 * @author nicebean
 * @version 1.0 (2005-12-14)
 */
public abstract class AbstractDataTransferParameter implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** The simple primary key value. */
    private Integer id;

    /** The one-to-one supplier association. */
    private Site site;

    private Date startTime;

    private Integer timePerDay;

    private Integer intervalMin;

    private String succEmail;

    private String failEmail;
    
    private ExportFileType exportFileType;
    
    private String serverAddress;

    private Integer serverPort;

    private String serverUserName;

    private String serverPassword;

    private String serverDir;


    /**
     * Simple constructor of AbstractDataTransferParameter instances.
     */
    public AbstractDataTransferParameter() {
    }

    /**
     * Constructor of AbstractDataTransferParameter instances given a simple
     * primary key.
     * 
     * @param supplier
     */
    public AbstractDataTransferParameter(Site site) {
        this.setSite(site);
    }

    /**
     * @return Returns the id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(Integer id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * Return one-to-one association that identifies this object.
     * 
     * @return Site
     */
    public Site getSite() {
        return site;
    }

    /**
     * Set one-to-one association that identifies this object.
     * 
     * @param site
     */
    public void setSite(Site site) {
        this.site = site;
        if (site == null) {
            this.setId(null);
        } else {
            this.setId(site.getId());
        }
    }

    /**
     * @return Returns the failEmail.
     */
    public String getFailEmail() {
        return failEmail;
    }

    /**
     * @param failEmail
     *            The failEmail to set.
     */
    public void setFailEmail(String failEmail) {
        this.failEmail = failEmail;
    }

    /**
     * @return Returns the interval.
     */
    public Integer getIntervalMin() {
        return intervalMin;
    }

    /**
     * @param interval
     *            The interval to set.
     */
    public void setIntervalMin(Integer interval) {
        this.intervalMin = interval;
    }

    /**
     * @return Returns the startTime.
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     *            The startTime to set.
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return Returns the succEmail.
     */
    public String getSuccEmail() {
        return succEmail;
    }

    /**
     * @param succEmail
     *            The succEmail to set.
     */
    public void setSuccEmail(String succEmail) {
        this.succEmail = succEmail;
    }

    /**
     * @return Returns the timePerDay.
     */
    public Integer getTimePerDay() {
        return timePerDay;
    }

    /**
     * @param timePerDay
     *            The timePerDay to set.
     */
    public void setTimePerDay(Integer timePerDay) {
        this.timePerDay = timePerDay;
    }

    /**
     * @return Returns the exportFileType.
     */
    public ExportFileType getExportFileType() {
        return exportFileType;
    }

    /**
     * @param exportFileType The exportFileType to set.
     */
    public void setExportFileType(ExportFileType exportFileType) {
        this.exportFileType = exportFileType;
    }

    
    
    /**
     * @return Returns the serverAddress.
     */
    public String getServerAddress() {
        return serverAddress;
    }

    /**
     * @param serverAddress The serverAddress to set.
     */
    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    /**
     * @return Returns the serverDir.
     */
    public String getServerDir() {
        return serverDir;
    }

    /**
     * @param serverDir The serverDir to set.
     */
    public void setServerDir(String serverDir) {
        this.serverDir = serverDir;
    }

    /**
     * @return Returns the serverPassword.
     */
    public String getServerPassword() {
        return serverPassword;
    }

    /**
     * @param serverPassword The serverPassword to set.
     */
    public void setServerPassword(String serverPassword) {
        this.serverPassword = serverPassword;
    }



    /**
     * @return Returns the serverPort.
     */
    public Integer getServerPort() {
        return serverPort;
    }

    /**
     * @param serverPort The serverPort to set.
     */
    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * @return Returns the serverUserName.
     */
    public String getServerUserName() {
        return serverUserName;
    }

    /**
     * @param serverUserName The serverUserName to set.
     */
    public void setServerUserName(String serverUserName) {
        this.serverUserName = serverUserName;
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

    /**
     * Implementation of the equals comparison on the basis of equality of the
     * primary key values.
     * 
     * @param rhs
     * @return boolean
     */
    public boolean equals(Object rhs) {
        if (rhs == null)
            return false;
        if (this == rhs)
            return true;
        if (!(rhs instanceof DataTransferParameter))
            return false;
        DataTransferParameter that = (DataTransferParameter) rhs;
        if (this.getId() != null)
            return this.getId().equals(that.getId());
        return that.getId() == null;
    }

}
