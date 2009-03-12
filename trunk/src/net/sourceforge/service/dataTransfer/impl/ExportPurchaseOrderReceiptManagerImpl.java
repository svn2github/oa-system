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
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.LogFactory;

import net.sourceforge.model.admin.Site;
import net.sourceforge.model.business.po.PurchaseOrderItem;
import net.sourceforge.model.business.po.PurchaseOrderItemReceipt;
import net.sourceforge.model.business.po.PurchaseOrderItemRecharge;
import net.sourceforge.model.metadata.ExportStatus;

/**
 * µ¼³öPurchaseOrderReceiptÀà
 * @author ych
 * @version 1.0 (Dec 27, 2005)
 */
public class ExportPurchaseOrderReceiptManagerImpl extends BaseExportManager {

    
    
    public ExportPurchaseOrderReceiptManagerImpl() {
        super();
        log = LogFactory.getLog(ExportPurchaseOrderReceiptManagerImpl.class);
        exportClassName="PurchaseOrderReceipt";
        folder="prh";
    }

    public String exportTextFile(Site site) throws Exception {
        List receiptList=dao.getPOItemReceiptList(site);
        if (receiptList.size()>0) {
            File tempFile=new File(getLocalFileName("prh","txt"));
            OutputStreamWriter writer=new OutputStreamWriter(new FileOutputStream(tempFile),FILE_ENCODE);
            for (Iterator itorExpense = receiptList.iterator(); itorExpense.hasNext();) {
                PurchaseOrderItemReceipt receipt=(PurchaseOrderItemReceipt) itorExpense.next();
                
                //down modified by jackey 2006-3-6
                PurchaseOrderItem item = receipt.getPurchaseOrderItem();
                List poItemRechargeList=dao.getPOItemRecharge(item);
                if (poItemRechargeList.size()==0) {
                    writePurchaseOrderItemReceipt(writer,receipt, item.getId().toString());
                } else {
                    for (Iterator itorItemRecharge = poItemRechargeList.iterator(); itorItemRecharge.hasNext();) {
                        PurchaseOrderItemRecharge recharge = (PurchaseOrderItemRecharge) itorItemRecharge.next();
                        // modified by nicebean 2007-6-5, add 3000000000 to recharge id, so recharge id never equals to po item id
                        writePurchaseOrderItemReceipt(writer,receipt, String.valueOf(recharge.getId().intValue() + 3000000000l));
                    }
                }               
                //up modified by jackey 2006-3-6
                receipt.setExportStatus(ExportStatus.EXPORTED);
                dao.updatePurchaseOrderItemReceipt(receipt);
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
    
    private void writePurchaseOrderItemReceipt(OutputStreamWriter writer, PurchaseOrderItemReceipt receipt, String allocateId) throws IOException {
        writer.write(getFormatString(getValue(receipt,"purchaseOrderItem.purchaseOrder.id"),16));
        writer.write(getFormatString(allocateId, 20));
        writer.write(getFormatString(getValue(receipt,"receiveQty1"),7));
        writer.write(getFormatString(getValue(receipt,"receiver1.id"),10));
        writer.write(getFormatString(getValue(receipt,"receiver2.id"),10));
        writer.write(getFormatString(getDateValue(receipt,"receiveDate1",dateFormat),8));
        writer.write(getFormatString(getDateValue(receipt,"receiveDate2",dateFormat),8));
        writer.write(EOL);
    }

    public String getRemoteExportFileName() {
        return getRemoteFileName("prh","txt");
    }
    
}
