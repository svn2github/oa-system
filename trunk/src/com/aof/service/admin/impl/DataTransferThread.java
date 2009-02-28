/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aof.model.admin.DataTransferParameter;
import com.aof.service.admin.DataTransferManager;
import com.aof.service.admin.EmailManager;
import com.shcnc.struts.form.DateFormatter;

/**
 * @author ych
 * @version 1.0 (Dec 20, 2005)
 */
public class DataTransferThread extends Thread {

    private Log log = LogFactory.getLog(DataTransferThread.class);

    private DataTransferManager manager;

    private DataTransferParameter para;
    
    private EmailManager emailManager;

    private int todayRunCount = 0;

    private Date lastJobTime;

    private boolean jobFinish = false;

    private boolean manualJob = false;

    private boolean resetJob = false;

    private static final long ONE_DAY_MILLISECOND = 24 * 60 * 60 * 1000;

    private static final long ONE_HOUR_MILLISECOND = 60 * 60 * 1000;

    private static final long ONE_MINUTE_MILLISECOND = 60 * 1000;

    private static final long ONE_SECOND_MILLISECOND = 1000;

    /**
     * @param jobFinish
     *            The jobFinish to set.
     */
    public void setJobFinish(boolean jobFinish) {
        this.jobFinish = jobFinish;
    }
    

    /**
     * @return Returns the jobFinish.
     */
    public boolean isJobFinish() {
        return jobFinish;
    }


    public DataTransferThread(EmailManager emailManager , DataTransferManager manager, DataTransferParameter para) {
        super();
        this.manager = manager;
        this.para = para;
        this.emailManager = emailManager;
        this.setName(new StringBuffer("DataTransfer_").append(para.getSite().getId()).append('[').append(para.getSite().getName()).append(']').toString());
    }

    /**
     * @return Returns the manualJob.
     */
    public boolean isManualJob() {
        return manualJob;
    }

    /**
     * @param manualJob
     *            The manualJob to set.
     */
    public void setManualJob(boolean manualJob) {
        this.manualJob = manualJob;
    }

    /**
     * @return Returns the resetJob.
     */
    public boolean isResetJob() {
        return resetJob;
    }

    /**
     * @param resetJob
     *            The resetJob to set.
     */
    public void setResetJob(boolean resetJob) {
        this.resetJob = resetJob;
    }

    /**
     * @return Returns the para.
     */
    public DataTransferParameter getPara() {
        return para;
    }

    /**
     * @param para
     *            The para to set.
     */
    public void setPara(DataTransferParameter para) {
        this.para = para;
    }

    public void run() {
        log.info("Begin data transfer job.\n\tSite-->" + this.para.getSite().getName());
        long sleepMilliSeconds = getSleepMilliSecond();
        if (sleepMilliSeconds == 0)
            log.info("Start the job at once!\n\tSite-->" + this.para.getSite().getName());
        else
            log.info(getSleepLogString(sleepMilliSeconds) + " before first job of today!\n\tSite-->" + this.para.getSite().getName());
        todayRunCount = 0;
        while (true) {
            try {
                sleep(sleepMilliSeconds);
            } catch (InterruptedException e) {
                if (this.isManualJob()) {
                    log.info("Start job manually!\n\tSite-->" + this.para.getSite().getName() + "\n\tNow time-->" + new Date());
                    doJob();
                    sleepMilliSeconds = getSleepAgainMilliSecond();
                    log.info("Finish job manually!" + getSleepLogString(sleepMilliSeconds) + "!\n\tSite-->" + this.para.getSite().getName());
                }
                if (this.isResetJob()) {
                    log.info("Parameter has been changed,reset job\n\tSite-->" + this.para.getSite().getName() + "\n\tNow time-->" + new Date());
                    todayRunCount = 0;
                    lastJobTime = null;
                    sleepMilliSeconds = getSleepMilliSecond();
                    if (sleepMilliSeconds == 0)
                        log.info("Start the job at once!\n\tSite-->" + this.para.getSite().getName());
                    else
                        log.info(getSleepLogString(sleepMilliSeconds) + " before first job of today!\n\tSite-->" + this.para.getSite().getName());
                }
            }
            if (this.isJobFinish()) {
                log.info("Terminate Job!\n\tSite-->" + this.para.getSite().getName() + "\n\tNow time-->" + new Date());
                return;
            }
            if (!(this.isManualJob() || this.isResetJob())) {
                todayRunCount++;
                lastJobTime = new Date();
                log.info("Doing Job!\n\tSite-->" + this.para.getSite().getName() + "\n\tNow time-->" + new Date() + "\n\tToday count-->" + todayRunCount);
                
                doJob();

                sleepMilliSeconds = getSleepMilliSecond();
                if (todayRunCount == 0)
                    log.info("Today's job has been finished!\n\tSite-->" + this.para.getSite().getName());
                log.info(getSleepLogString(sleepMilliSeconds) + "!\n\tSite-->" + this.para.getSite().getName());
            } else {
                this.setManualJob(false);
                this.setResetJob(false);
            }
        }
    }

    
    private void doJob() {
        if (para.getServerAddress()==null) {
            log.info("FTP Server of site("+this.para.getSite().getName()+") is unknown,terminate job!");
            return;
        }
        Map context=new HashMap();
        doExportJob(context);
        doImportJob(context);
        if (this.para.getSuccEmail()!=null) {
            try {
                emailManager.insertEmail(this.para.getSite(),this.para.getSuccEmail(),"DataTransfer.vm",context);
            } catch (Exception e) {
                log.error("Error send data transfer email.", e);
                e.printStackTrace();
            }
        }
    }
    
    private void doExportJob(Map context) {
        boolean successDepartment=exportDepartment(context);
        if (successDepartment) {
            exportExpense(context);
        } 
        if (exportCountry(context)) {
            if (exportSupplier(context)) {
                if (successDepartment) {
                    if (exportPurchaseOrder(context))
                        exportPurchaseOrderReceipt(context);
                }
            }
        } 
    }

    private void doImportJob(Map context) {
        importPurchaseType(context);
        importCustomer(context);
        importExchangeRate(context);
        importExpenseCategory(context);
    }
    
    private boolean exportCountry(Map context) {
        try {
            context.put("startTimeCountry",this.getNowTime());
            this.manager.exportCountry(para);
        } catch (Exception e) {
            log.info("Error occurs while exporting country.\nThe reason is:" + e.toString());
            e.printStackTrace();
            context.put("errorMessageCountry",e.toString());
            context.put("errorMessageSupplier","can't export supplier for failing in exporting country");
            context.put("errorMessagePO","can't export PO for failing in exporting country");
            context.put("errorMessagePOReceipt","can't export PO Receipt for failing in exporting country");
            return false;
        } 
        return true;
    }
    
    private boolean exportDepartment(Map context) {
        try {
            context.put("startTimeDepartment",this.getNowTime());
            this.manager.exportDepartment(para);
        } catch (Exception e) {
            log.info("Error occurs while exporting department.\nThe reason is:" + e.toString());
            e.printStackTrace();
            context.put("errorMessageDepartment",e.toString());
            context.put("errorMessageExpense","can't export expense for failing in exporting department");
            context.put("errorMessagePO","can't export PO for failing in exporting department");
            context.put("errorMessagePOReceipt","can't export PO Receipt for failing in exporting department");
            return false;
        } 
        return true;
    }
    
    private boolean exportExpense(Map context) {
        try {
            context.put("startTimeExpense",this.getNowTime());
            this.manager.exportExpense(para);
        } catch (Exception e) {
            log.info("Error occurs while exporting expense.\nThe reason is:" + e.toString());
            e.printStackTrace();
            context.put("errorMessageExpense",e.toString());
            return false;
        }
        return true;
    }

    private boolean exportPurchaseOrder(Map context) {
        try {
            context.put("startTimePO",this.getNowTime());
            this.manager.exportPurchaseOrder(para);
        } catch (Exception e) {
            log.info("Error occurs while exporting purchase order.\nThe reason is:" + e.toString());
            e.printStackTrace();
            context.put("errorMessagePO",e.toString());
            context.put("errorMessagePOReceipt","can't export PO Receipt for failing in exporting PO");
            return false;
        }
        return true;
    }

    private boolean exportPurchaseOrderReceipt(Map context) {
        try {
            context.put("startTimePOReceipt",this.getNowTime());
            this.manager.exportPurchaseOrderReceipt(para);
        } catch (Exception e) {
            log.info("Error occurs while exporting purchase order receipt.\nThe reason is:" + e.toString());
            e.printStackTrace();
            context.put("errorMessagePOReceipt",e.toString());
            return false;
        }
        return true;
    }

    private boolean exportSupplier(Map context) {
        try {
            context.put("startTimeSupplier",this.getNowTime());
            this.manager.exportSupplier(para);
        } catch (Exception e) {
            log.info("Error occurs while exporting supplier.\nThe reason is:" + e.toString());
            e.printStackTrace();
            context.put("errorMessageSupplier",e.toString());
            context.put("errorMessagePO","can't export PO for failing in exporting supplier");
            context.put("errorMessagePOReceipt","can't export PO Receipt for failing in exporting supplier");
            return false;
        }
        return true;
    }
    
    private void importPurchaseType(Map context) {
        try {
            context.put("startTimePurchaseType",this.getNowTime());
            this.manager.importPurchaseType(para);
        } catch (Exception e) {
            log.info("Error occurs while importing purchase type.\nThe reason is:" + e.toString());
            e.printStackTrace();
            context.put("errorMessagePurchaseType",e.toString());
        }
    }
    
    private void importCustomer(Map context) {
        try {
            context.put("startTimeCustomer",this.getNowTime());
            this.manager.importCustomer(para);
        } catch (Exception e) {
            log.info("Error occurs while importing customer.\nThe reason is:" + e.toString());
            e.printStackTrace();
            context.put("errorMessageCustomer",e.toString());
        }
    }
    
    private void importExchangeRate(Map context) {
        try {
            context.put("startTimeExchangeRate",this.getNowTime());
            this.manager.importExchangeRate(para);
        } catch (Exception e) {
            log.info("Error occurs while importing exchangerate.\nThe reason is:" + e.toString());
            e.printStackTrace();
            context.put("errorMessageExchangeRate",e.toString());
        }
    }
    
    private void importExpenseCategory(Map context) {
        try {
            context.put("startTimeExpenseCategory",this.getNowTime());
            this.manager.importExpenseCategory(para);
        } catch (Exception e) {
            log.info("Error occurs while importing expense category.\nThe reason is:" + e.toString());
            e.printStackTrace();
            context.put("errorMessageExpenseCategory",e.toString());
        }
    }

    private String getNowTime() {
        DateFormatter emailDateFormatter = new DateFormatter(java.util.Date.class, "yyyy/MM/dd HH:MM:ss");
        return emailDateFormatter.format(new Date());
    }
    
    private long getSleepMilliSecond() {
        Date nowTime = new Date();
        Date startTime = this.getTodayStartTime(para.getStartTime());
        if (todayRunCount < this.para.getTimePerDay().intValue()) {
            if (todayRunCount == 0) {
                if (startTime.compareTo(nowTime) <= 0) {
                    return 0;
                } else {
                    return (startTime.getTime() - nowTime.getTime());
                }
            } else {
                if (para.getIntervalMin().longValue() * ONE_MINUTE_MILLISECOND - (nowTime.getTime() - this.lastJobTime.getTime()) > 0)
                    return para.getIntervalMin().longValue() * ONE_MINUTE_MILLISECOND - (nowTime.getTime() - this.lastJobTime.getTime());
                else
                    return 0;
            }
        } else {
            todayRunCount = 0;
            return (startTime.getTime() - nowTime.getTime() + ONE_DAY_MILLISECOND);
        }
    }

    private long getSleepAgainMilliSecond() {
        Date nowTime = new Date();
        Date startTime = this.getTodayStartTime(para.getStartTime());
        if (todayRunCount != 0) {
            // 今天运行过几次，睡到下次预定开始时间
            if (para.getIntervalMin().longValue() * ONE_MINUTE_MILLISECOND - (nowTime.getTime() - this.lastJobTime.getTime()) > 0)
                return para.getIntervalMin().longValue() * ONE_MINUTE_MILLISECOND - (nowTime.getTime() - this.lastJobTime.getTime());
            else
                return 0;
        } else {
            // todayRunCount=0,可能是今天已经全部结束，或者今天尚未开始
            if (lastJobTime == null) {
                // 任务开始后，一次都没运行过，睡到预定的今天开始时间
                if (startTime.compareTo(nowTime) <= 0) {
                    return 0;
                } else {
                    return (startTime.getTime() - nowTime.getTime());
                }
            } else {
                if (getDay(nowTime) == getDay(lastJobTime)) {
                    // 现在时间和上次任务时间在同一天,则睡到明天开始时间
                    return (startTime.getTime() - nowTime.getTime() + ONE_DAY_MILLISECOND);
                } else {
                    // 现在时间和上次任务时间不在同一天，则睡到今天开始时间
                    return (startTime.getTime() - nowTime.getTime());
                }
            }
        }
    }

    private String getSleepLogString(long sleepMilliSeconds) {
        long hour = sleepMilliSeconds / ONE_HOUR_MILLISECOND;
        long minute = (sleepMilliSeconds - hour * ONE_HOUR_MILLISECOND) / ONE_MINUTE_MILLISECOND;
        long second = (sleepMilliSeconds - hour * ONE_HOUR_MILLISECOND - minute * ONE_MINUTE_MILLISECOND) / ONE_SECOND_MILLISECOND;
        return "Sleep " + hour + " hours," + minute + " minutes," + second + " seconds";
    }

    private Date getTodayStartTime(Date startTime) {
        Date now = new Date();
        return new Date(now.getTime() / ONE_DAY_MILLISECOND * ONE_DAY_MILLISECOND + startTime.getTime() % ONE_DAY_MILLISECOND);
    }

    private long getDay(Date time) {
        return (time.getTime() / ONE_DAY_MILLISECOND * ONE_DAY_MILLISECOND);
    }

}
