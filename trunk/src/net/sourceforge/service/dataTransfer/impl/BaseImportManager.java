/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.dataTransfer.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;


import net.sourceforge.model.admin.DataTransferParameter;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.metadata.ExportFileType;

import net.sourceforge.service.dataTransfer.ImportManager;


/**
 * 定义导入文件Manager的基类
 * @author ych
 * @version 1.0 (Dec 28, 2005)
 */
public abstract class BaseImportManager extends BaseDataTransferManager implements ImportManager {

   
    protected String importClassName;
    
    protected String remoteFileName;
    
    public String getImportClassName() {
        return importClassName;
    }
    
    protected String getLocalFileName() {
        String prefix=remoteFileName.substring(0,remoteFileName.indexOf("."));
        String postfix=remoteFileName.substring(remoteFileName.indexOf(".")+1);
        return System.getProperty("java.io.tmpdir")+File.separator+prefix+"_"+fileFormatter.format(new Date())+"."+postfix;
    }
    
    public void importFile(DataTransferParameter para) throws Exception {
        log.info("Begin importing "+importClassName+"!");
        String localFileName;
        try {
            localFileName=transferFileFromServer(para,remoteFileName);
        } catch (Exception e) {
            log.info("Fail to transfer file "+remoteFileName+" from FTP Server.");
            throw e;
        }
        if (localFileName.equals("")) {
            log.info("Find no file to import!");
            return;
        }
        log.info("Local file generated!FilePath:"+localFileName);
        try {
            if (para.getExportFileType().equals(ExportFileType.TEXT)) {
                importFromTextFile(para.getSite(),localFileName);
            } else {
                importFromXmlFile(para.getSite(),localFileName);
            }
        } 
        finally {
            deleteLocalFile(localFileName);
        }
        deleteFileFromServer(para,remoteFileName);
        log.info("Finish imorting "+importClassName+"!");

    }
    
    
    private void deleteFileFromServer(DataTransferParameter para, String remoteFileName){
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
            log.info("Delete remote file error!-->Could not connect to server");
            return;
        }
        try
        {
            log.info("FTP login user:"+userName+" password:******");
            if (!ftp.login(userName, password))
            {
                ftp.logout();
                log.info("Delete remote file error!-->Error user name or password");
                return;
            }
            log.info("Remote system is " + ftp.getSystemName());
            if (ftpDir!=null) {
                log.info("Change remote directory to " + ftpDir);
                if (ftp.changeWorkingDirectory(ftpDir)==false) {
                    log.info("Delete remote file error!-->FTP change dir error.No such directory:"+ftpDir);
                    return;
                }
            }
            ftp.deleteFile(remoteFileName);
            ftp.logout();
            return;
        }
        catch (FTPConnectionClosedException e)
        {
            log.info("Delete remote file error!-->Server closed connection");
            return;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            log.info("Delete remote file error!");
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
    
    private String transferFileFromServer(DataTransferParameter para, String remoteFileName) throws Exception {
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
            
            String localFileName=this.getLocalFileName();
            OutputStream output = new FileOutputStream(localFileName);
           
            if (ftp.retrieveFile(remoteFileName, output)==false){
                int reply = ftp.getReplyCode();
                output.close();
                ftp.logout();
                deleteLocalFile(localFileName);
                if (reply==FTPReply.FILE_UNAVAILABLE) {
                    log.info("Does't find remote file "+remoteFileName+"!");
                    return "";
                } else {
                    log.info("File transfer:"+remoteFileName+"-->"+localFileName+">>>error!");
                    throw new Exception("Error receiving file.");
                }    
            } else {
                log.info("File transfer:"+remoteFileName+"-->"+localFileName+">>>ok!");
            }

            output.close();
            
            ftp.logout();
            
            return localFileName;
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


    
    public abstract void importFromTextFile(Site site,String localFileName) throws Exception ;

    public abstract void importFromXmlFile(Site site,String localFileName) throws Exception ;

    

}
