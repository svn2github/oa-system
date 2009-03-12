/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.dataTransfer.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import net.sourceforge.dao.admin.DataTransferDAO;
import net.sourceforge.model.admin.DataTransferParameter;
import net.sourceforge.service.BaseManager;
import com.shcnc.struts.form.DateFormatter;
import com.shcnc.utils.BeanHelper;

/**
 * 
 * @author ych
 * @version 1.0 (Dec 28, 2005)
 */
public class BaseDataTransferManager extends BaseManager {
    
    protected Log log ;
    
    protected String folder;
    
    protected static final String FILE_ENCODE="GB2312";
    
    protected static final DateFormatter fileFormatter = new DateFormatter(
            java.util.Date.class, "yyyyMMddHHmmssSSS");
    
    protected static final DecimalFormat decimalFormat = new DecimalFormat("##0.00");
    protected static final DecimalFormat exchangeRateFormat = new DecimalFormat("##.00000");
    
    protected static final DateFormatter dateFormat =new DateFormatter(java.util.Date.class, "yyyyMMdd");
    
    protected static final String EOL="\n";
    
    protected static final String EMPTY_STRING=null;
    
    protected static final String EMPTY_DATE=getSpace(8);
    
    protected static final String RECHARGE_NO="0";
    protected static final String RECHARGE_YES="1";
    
    protected static final String RECHARGE_TYPE_CUSTOMER="1";
    protected static final String RECHARGE_TYPE_ENTITY="2";
    protected static final String RECHARGE_TYPE_DEPARTMENT="3";
    protected static final String RECHARGE_TYPE_PERSON="4";
    
    protected static final String RECHARGE_EMPTY_CUSTOMER=null;
    protected static final String RECHARGE_EMPTY_ENTITY=null;
    protected static final String RECHARGE_EMPTY_PERSON=null;
    protected static final String RECHARGE_EMPTY_DEPARTMENT=null;
    
    protected static final String DISABLED="0";
    protected static final String ENABLED="1";
    
    protected static final int FTP_DEFAULT_PORT=21;
    protected static final String FTP_DEFAULT_USERNAME="anonymous";
    protected static final String FTP_DEFAULT_PASSWORD="anonymous@anonymous.com";
    
    protected DataTransferDAO dao;
    
    /**
     * @param dao
     */
    public void setDataTransferDAO(DataTransferDAO dao) {
        this.dao = dao;
    }
    
    protected String getFormatString(int intSrc,int length) {
        return getFormatString((new Integer(intSrc)).toString(),length);
    }
    
    protected String getFormatString(Object objSrc,int length) {
        return getFormatString(objSrc!=null?objSrc.toString():EMPTY_STRING,length);
    }
    
    protected String getFormatString(Integer integerSrc,int length) {
        return getFormatString(integerSrc!=null?integerSrc.toString():"0",length);
    }
    
    protected String getFormatString(String strSrc,int length) {
        String result=strSrc;
        if (result==null) return getSpace(length);
        result = result.replace((char)0xd, ' ');
        result = result.replace((char)0xa, ' ');
        
        int count=0;
        int index=0;
        StringBuffer sb = new StringBuffer("");
        while (count<length) {
            if (index>=result.length()) break;
            char contentChar = result.charAt(index++);
            if (contentChar > 255) {
                count += 2;
            } else {
                count++;
            }
            if (count<=length) {
                sb.append(contentChar);
            }
        }
        if (count!=length) {
            if (count<length) {
                sb.append(getSpace(length-count));
            } else {
                sb.append(getSpace(1));
            }
        }
        return sb.toString();
    }
    
    protected static String getSpace(int length) {
        StringBuffer sb=new StringBuffer("");
        for (int i = 0; i < length; i++) 
            sb.append(" ");
        return sb.toString();
    }
    
    protected String getLocalFileName(String prefix,String postfix) {
        return System.getProperty("java.io.tmpdir")+File.separator+prefix+"_"+fileFormatter.format(new Date())+"."+postfix;
    }
    
    protected String getRemoteFileName(String prefix,String postfix) {
        return prefix+"_"+fileFormatter.format(new Date())+"."+postfix;
    }
    
    protected Object getValue(Object object, String name) {
        return BeanHelper.getBeanPropertyValue(object,name);
    }

    protected String getDateValue(Object object, String name,DateFormatter dateFormat) {
        Object value=getValue(object,name);
        if (value!=null)
            return dateFormat.format(value);
        else
            return EMPTY_DATE;
    }
    
    protected String getDecimalValue(Object object, String name,DecimalFormat decimalFormat) {
        Object value=getValue(object,name);
        if (value!=null)
            return decimalFormat.format(((BigDecimal)value).doubleValue());
        else
            return decimalFormat.format(0);
    }

    protected void deleteLocalFile(String filePath) {
        File tempFile=new File(filePath);
        tempFile.delete();
    }
    
    protected boolean connectToFTP(FTPClient ftp,String server,int port,Log log) {
        try
        {
            ftp.setDefaultTimeout(30000);
            ftp.connect(server,port);
            log.info("Connected to " + server + ".");
                
            // After connection attempt, you should check the reply code to verify
            // success.
            int reply = ftp.getReplyCode();
            
            if (!FTPReply.isPositiveCompletion(reply))
            {
                ftp.disconnect();
                log.info("FTP server refused connection.");
                return false;
            }
            ftp.setSoTimeout(60000);
            ftp.setDataTimeout(60000);
            return true;
        }
        catch (IOException e)
        {
            if (ftp.isConnected())
            {
                try {
                    ftp.disconnect();
                } catch (IOException f){}
            }
            return false;
        }
    }

    protected String getFtpDir(DataTransferParameter para) { 
        String ftpDir=para.getServerDir();
        if (this.folder.trim().length()!=0) {
            if (ftpDir.substring(ftpDir.length()-1,ftpDir.length()).equals("/")){
                ftpDir=ftpDir+this.folder;
            } else {
                ftpDir=ftpDir+"/"+this.folder;
            }
        }
        return ftpDir;
    }
}
