/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.dataTransfer.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;

import com.aof.model.admin.DataTransferParameter;
import com.aof.model.admin.Site;
import com.aof.model.metadata.ExportFileType;
import com.aof.service.dataTransfer.ExportManager;


/**
 * 定义导出文件Manager的基类
 * @author ych
 * @version 1.0 (Dec 22, 2005)
 */
public abstract class BaseExportManager extends BaseDataTransferManager implements ExportManager {
    
    protected String exportClassName;
    
    public String getExportClassName() {
        return exportClassName;
    }
    
    public void exportFile(DataTransferParameter para) throws Exception {
        log.info("Begin exporting "+exportClassName+"!");
        String localFileName;
        if (para.getExportFileType().equals(ExportFileType.TEXT)) {
            localFileName = exportTextFile(para.getSite());
        } else {
            localFileName = exportXmlFile(para.getSite());
        }
        if (localFileName.equals("")) {
            log.info("Find no "+exportClassName+" to export");
            return;
        }
        log.info("Local file generated!FilePath:"+localFileName);
        String remoteFileName=getRemoteExportFileName();
        try {
            transferFileToServer(para,localFileName,remoteFileName);
        } catch (Exception e) {
            log.info("Fail to transfer local file to FTP Server.");
            throw e;
        }
        log.info("Finish exporting "+exportClassName+"!");
        deleteLocalFile(localFileName);
    }

    

    private void transferFileToServer(DataTransferParameter para, String localFileName, String remoteFileName) throws Exception {
        FTPClient ftp = new FTPClient();
        String server=para.getServerAddress();
        int port=para.getServerPort()==null?FTP_DEFAULT_PORT:para.getServerPort().intValue();
        String userName=para.getServerUserName();
        String password=para.getServerPassword();
        if (userName==null) {
            userName=FTP_DEFAULT_USERNAME;
            password=FTP_DEFAULT_PASSWORD;
        }
        String ftpDir=getFtpDir(para);
        if (this.connectToFTP(ftp,server,port,log)==false) {
            throw new Exception("Could not connect to server.");
        }
        
        try
        {
            log.info("FTP login user:"+userName+" password:******");
            if (!ftp.login(userName, password))
            {
                ftp.logout();
                throw new Exception("Error user name or password.");
            }
            log.info("Remote system is " + ftp.getSystemName());
            if (ftpDir!=null) {
                log.info("Change remote directory to " + ftpDir);
                if (ftp.changeWorkingDirectory(ftpDir)==false) {
                    log.info("FTP change dir error.No such directory:"+ftpDir+".Try to create it!");
                    if (ftp.makeDirectory(ftpDir)==false) {
                        ftp.logout();
                        throw new Exception("Create directory error,logout!");
                    } else {
                        log.info("Directory-->"+ftpDir+" has been created!");
                    }
                    ftp.changeWorkingDirectory(ftpDir);
                }    
            }
            
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();
            InputStream input=new FileInputStream(localFileName);
            
            if (ftp.storeFile(remoteFileName, input)==false){
                input.close();
                ftp.logout();
                log.info("File transfer:"+localFileName+"-->"+remoteFileName+">>>error!");
                throw new Exception("Error sending file.");
            } else {
                log.info("File transfer:"+localFileName+"-->"+remoteFileName+">>>ok!");
            }
            input.close();
            ftp.logout();
        }
        catch (FTPConnectionClosedException e)
        {
            throw new Exception("Server closed connection.");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new Exception("File transfer error.");
        }
        finally
        {
            if (ftp.isConnected())
            {
                try {
                    ftp.disconnect();
                } catch (IOException f){}
            }
            
        }
        
    }


    public abstract String exportTextFile(Site site) throws Exception ;

    public abstract String exportXmlFile(Site site) throws Exception ;
    
    public abstract String getRemoteExportFileName(); 
}
