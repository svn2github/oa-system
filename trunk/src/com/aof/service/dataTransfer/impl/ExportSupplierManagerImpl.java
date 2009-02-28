/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.dataTransfer.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.LogFactory;

import com.aof.model.admin.Site;
import com.aof.model.admin.Supplier;
import com.aof.model.metadata.ExportStatus;

/**
 * 导出Supplier的类
 * @author ych
 * @version 1.0 (Dec 26, 2005)
 */
public class ExportSupplierManagerImpl extends BaseExportManager {

    
    public ExportSupplierManagerImpl() {
        super();
        log = LogFactory.getLog(ExportSupplierManagerImpl.class);
        exportClassName="Supplier"; 
        folder="supplier";
    }

    public String exportTextFile(Site site) throws Exception {
        List supplierList=dao.getSupplierList(site);
        if (supplierList.size()>0) {
            File tempFile=new File(getLocalFileName("supplier","txt"));
            OutputStreamWriter writer=new OutputStreamWriter(new FileOutputStream(tempFile),FILE_ENCODE);
            for (Iterator itorExpense = supplierList.iterator(); itorExpense.hasNext();) {
                Supplier supplier=(Supplier) itorExpense.next();
                writeSupplier(writer,supplier);
                supplier.setExportStatus(ExportStatus.EXPORTED);
                dao.updateSupplier(supplier);
            }
            writer.close();
            return tempFile.getPath();
        } else {
            return "";
        }
    }

    public String exportXmlFile(Site site) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
    
    private void writeSupplier(OutputStreamWriter writer, Supplier supplier) throws IOException {
        writer.write(getFormatString(getValue(supplier,"code"),8));
        writer.write(getFormatString(getValue(supplier,"name"),50));
        writer.write(getFormatString(getValue(supplier,"address1"),50));
        writer.write(getFormatString(getValue(supplier,"address2"),50));
        writer.write(getFormatString(getValue(supplier,"address3"),50));
        writer.write(getFormatString(getValue(supplier,"country.shortName"),50));
        writer.write(getFormatString(getValue(supplier,"city.engName"),50));
        writer.write(getFormatString(getValue(supplier,"post"),9));
        writer.write(getFormatString(getValue(supplier,"attention1"),24));
        writer.write(getFormatString(getValue(supplier,"attention2"),24));
        writer.write(getFormatString(getValue(supplier,"telephone1"),16));
        writer.write(getFormatString(getValue(supplier,"ext1"),4));
        writer.write(getFormatString(getValue(supplier,"telephone2"),16));
        writer.write(getFormatString(getValue(supplier,"ext2"),4));
        writer.write(getFormatString(getValue(supplier,"fax1"),16));
        writer.write(getFormatString(getValue(supplier,"fax2"),16));
        writer.write(getFormatString(getValue(supplier,"purchaseAccount"),8));
        writer.write(getFormatString(getValue(supplier,"purchaseSubAccount"),8));
        writer.write(getFormatString(getValue(supplier,"purchaseCostCenter"),8));
        writer.write(getFormatString(getValue(supplier,"apAccount"),8));
        writer.write(getFormatString(getValue(supplier,"apSubAccount"),8));
        writer.write(getFormatString(getValue(supplier,"apCostCenter"),8));
        writer.write(getFormatString(getValue(supplier,"bank"),8));
        writer.write(getFormatString(getValue(supplier,"currency.code"),3));
        writer.write(getFormatString(getValue(supplier,"creditTerms"),8));
        writer.write(getFormatString(getValue(supplier,"contact"),20));
        writer.write(EOL);
    }

    public String getRemoteExportFileName() {
        return getRemoteFileName("supplier","txt");
    }
    
}
