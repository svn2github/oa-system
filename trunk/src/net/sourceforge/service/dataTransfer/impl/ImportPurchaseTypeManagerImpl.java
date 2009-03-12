/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.dataTransfer.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.logging.LogFactory;

import net.sourceforge.model.admin.PurchaseType;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.metadata.EnabledDisabled;

/**
 * 导入PurchaseType的类
 * @author ych
 * @version 1.0 (Dec 29, 2005)
 */
public class ImportPurchaseTypeManagerImpl extends BaseImportManager {

    
    private static final int LINE_LENGTH = 59;
    private static final int CODE_BEGIN_INDEX=0;
    private static final int CODE_END_INDEX=8;
    private static final int DESC_BEGIN_INDEX=8;
    private static final int DESC_END_INDEX=58;
    private static final int ENABLE_BEGIN_INDEX=58;
    private static final int ENABLE_END_INDEX=59;
    
    
    public ImportPurchaseTypeManagerImpl() {
        super();
        log = LogFactory.getLog(ImportPurchaseTypeManagerImpl.class);
        importClassName="PurchaseType";
        remoteFileName="purtype.txt";
        folder="purtype";
    }

    public void importFromTextFile(Site site, String localFileName) throws Exception {
        List codeList=dao.getPurchaseTypeCodeList(site);
        BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(localFileName), FILE_ENCODE) );
        String line;
        try {
            while((line=reader.readLine())!=null) {
                if (line.length()<LINE_LENGTH) continue;
                PurchaseType purchaseType=new PurchaseType();
                purchaseType.setSite(site);
                purchaseType.setCode(getCode(line));
                purchaseType.setDescription(getDesc(line));
                purchaseType.setEnabled(getEnable(line));
                dao.updatePurchaseType(purchaseType,!codeList.contains(purchaseType.getCode()));
            } 
        } finally {
            reader.close();
        }
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.dataTransfer.impl.BaseImportManager#importFromXmlFile(net.sourceforge.model.admin.Site, java.lang.String)
     */
    public void importFromXmlFile(Site site, String localFileName) throws Exception {
        // TODO Auto-generated method stub
    }

    private String getCode(String line) {
        return line.substring(CODE_BEGIN_INDEX,CODE_END_INDEX).trim();
    }
    
    private String getDesc(String line) {
        return line.substring(DESC_BEGIN_INDEX,DESC_END_INDEX).trim();
    }
    
    private EnabledDisabled getEnable(String line) {
        return line.substring(ENABLE_BEGIN_INDEX,ENABLE_END_INDEX).equals(ENABLED)?EnabledDisabled.ENABLED:EnabledDisabled.DISABLED;
    }
    
}
