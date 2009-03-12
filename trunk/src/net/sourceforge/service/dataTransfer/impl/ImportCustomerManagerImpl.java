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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.LogFactory;

import net.sourceforge.model.admin.Customer;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.metadata.CustomerType;
import net.sourceforge.model.metadata.EnabledDisabled;

/**
 * 导入Customer的类
 * @author ych
 * @version 1.0 (Dec 29, 2005)
 */
public class ImportCustomerManagerImpl extends BaseImportManager {

    private static final int LINE_LENGTH = 60+9;
    private static final int CODE_BEGIN_INDEX=0;
    private static final int CODE_END_INDEX=8+9;
    private static final int DESC_BEGIN_INDEX=8+9;
    private static final int DESC_END_INDEX=58+9;
    private static final int TYPE_BEGIN_INDEX=58+9;
    private static final int TYPE_END_INDEX=59+9;
    private static final int ENABLE_BEGIN_INDEX=59+9;
    private static final int ENABLE_END_INDEX=60+9;
    
    private static final String CUSTOMER="1";
    
    /**
     * 
     */
    public ImportCustomerManagerImpl() {
        super();
        log = LogFactory.getLog(ImportCustomerManagerImpl.class);
        importClassName="Customer";
        remoteFileName="custcode.txt";
        folder="custcode";
    }

    public void importFromTextFile(Site site, String localFileName) throws Exception {
        Map customerMap = new HashMap();
        for (Iterator itor = dao.getCustomerList(site).iterator(); itor.hasNext(); ) {
            Customer customer = (Customer) itor.next();
            customerMap.put(customer.getCode(), customer);
        }
        BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(localFileName), FILE_ENCODE));
        String line;
        try {
            while((line=reader.readLine())!=null) {
                if (line.length()<LINE_LENGTH) continue;
                String code = getCode(line);
                boolean insert = false;
                Customer customer = (Customer) customerMap.remove(code);
                if (customer == null) {
                    insert = true;
                    customer = new Customer();
                    customer.setCode(code);
                }
                customer.setSite(site);
                customer.setDescription(getDesc(line));
                customer.setType(getType(line));
                customer.setEnabled(getEnable(line));
                dao.updateCustomer(customer, insert);
            }
            for (Iterator itor = customerMap.values().iterator(); itor.hasNext(); ) {
                Customer customer = (Customer) itor.next();
                if (EnabledDisabled.ENABLED.equals(customer.getEnabled())) {
                    customer.setEnabled(EnabledDisabled.DISABLED);
                    dao.updateCustomer(customer, false);
                }
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
    
    private CustomerType getType(String line) {
        return line.substring(TYPE_BEGIN_INDEX,TYPE_END_INDEX).equals(CUSTOMER)?CustomerType.CUSTOMER:CustomerType.ENTITY;
    }
}
