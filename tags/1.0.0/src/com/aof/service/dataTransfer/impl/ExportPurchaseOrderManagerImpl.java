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
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.LogFactory;

import com.aof.model.admin.Site;
import com.aof.model.business.po.PurchaseOrder;
import com.aof.model.business.po.PurchaseOrderItem;
import com.aof.model.business.po.PurchaseOrderItemRecharge;
import com.aof.model.metadata.CustomerType;
import com.aof.model.metadata.ExportStatus;
import com.aof.model.metadata.YesNo;

/**
 * µ¼³öPurchaseOrderÀà
 * @author ych
 * @version 1.0 (Dec 22, 2005)
 */
public class ExportPurchaseOrderManagerImpl extends BaseExportManager {

    private static final String PO_MASTER="1";
    private static final String PO_ITEM_ALLOCATE="2";
    private static final String PO_ITEM_RECHARGE="3";
    private static final String PO_ITEM="4";
    private static final String PO_NEW="1";
    private static final String PO_CANCEL="2";
    

    
    public ExportPurchaseOrderManagerImpl() {
        super();
        log = LogFactory.getLog(ExportPurchaseOrderManagerImpl.class);
        exportClassName="PurchaseOrder";
        folder="po";
    }

    public String exportTextFile(Site site) throws Exception {
        List poList=dao.getPOList(site);
        if (poList.size()>0) {
            File tempFile=new File(getLocalFileName("po","txt"));
            OutputStreamWriter writer=new OutputStreamWriter(new FileOutputStream(tempFile),FILE_ENCODE);
            for (Iterator itorPo = poList.iterator(); itorPo.hasNext();) {
                PurchaseOrder po = (PurchaseOrder) itorPo.next();
                writePurchaseOrder(writer,po);
                List poItemList=dao.getPOItemList(po);
                for (Iterator itorItem = poItemList.iterator(); itorItem.hasNext();) {
                    PurchaseOrderItem item = (PurchaseOrderItem) itorItem.next();
                    List poItemRechargeList=dao.getPOItemRecharge(item);
                    writePurchaseOrderItem(writer,item, !poItemRechargeList.isEmpty());
                    if (poItemRechargeList.size()==0) {
                        //down modified by jackey 2006-3-6
                        writePurchaseOrderItemAllocateData(writer,item,YesNo.NO,(Integer)getValue(item,"buyForDepartment.id"),(Integer)getValue(item,"buyForUser.id"),null, ((Integer)getValue(item,"id")).toString(),null,true);
                        //up modified by jackey 2006-3-6
                    } else {
                        for (Iterator itorItemRecharge = poItemRechargeList.iterator(); itorItemRecharge.hasNext();) {
                            PurchaseOrderItemRecharge recharge = (PurchaseOrderItemRecharge) itorItemRecharge.next();
                            recharge.setTotalAmount(item.getAmount());
                            if (recharge.getPerson()!=null || recharge.getPersonDepartment()!=null) {
                                //3 Department;4 Person
                                //down modified by jackey 2006-3-6
                                // modified by nicebean 2007-6-5, add 3000000000 to recharge id, so recharge id never equals to po item id
                                writePurchaseOrderItemAllocateData(writer,item,YesNo.NO,(Integer)getValue(recharge,"personDepartment.id"),(Integer)getValue(recharge,"person.id"),recharge.getPercentage(),String.valueOf(((Integer)getValue(recharge,"id")).intValue() + 3000000000l),recharge,!itorItemRecharge.hasNext());
                                //up modified by jackey 2006-3-6
                            } else {
                                //1 Customer;2 Entity
                                writePurchaseOrderItemRecharge(writer,recharge,!itorItemRecharge.hasNext());
                                //down modified by jackey 2006-3-6
                                // modified by nicebean 2007-6-5, add 3000000000 to recharge id, so recharge id never equals to po item id
                                writePurchaseOrderItemAllocateData(writer,item,YesNo.YES,(Integer)getValue(item,"buyForDepartment.id"),null,recharge.getPercentage(),String.valueOf(((Integer)getValue(recharge,"id")).intValue() + 3000000000l),recharge,!itorItemRecharge.hasNext());
                                //up modified by jackey 2006-3-6
                            }
                        }
                    }
                }
                po.setExportStatus(ExportStatus.EXPORTED);
                dao.updatePurchaseOrder(po);
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

    private void writePurchaseOrderItemRecharge(OutputStreamWriter writer, PurchaseOrderItemRecharge recharge, boolean last) throws IOException {
        boolean isNew=recharge.getPurchaseOrderItem().getPurchaseOrder().getExportStatus().equals(ExportStatus.UNEXPORTED);
        writer.write(PO_ITEM_RECHARGE);
        writer.write(isNew?PO_NEW:PO_CANCEL);
        writer.write(getFormatString(getValue(recharge,"purchaseOrderItem.purchaseOrder.id"),16));
        writer.write(getFormatString(getValue(recharge,"purchaseOrderItem.id"),10));
        // modified by nicebean 2007-6-5, add 3000000000 to recharge id, so recharge id never equals to po item id
        writer.write(getFormatString(String.valueOf(((Integer)getValue(recharge,"id")).intValue() + 3000000000l),20));
        /*if (recharge.getPerson()!=null) {
            writer.write(RECHARGE_TYPE_PERSON);
            writer.write(getFormatString(RECHARGE_EMPTY_CUSTOMER,8));
            writer.write(getFormatString(RECHARGE_EMPTY_ENTITY,8));
            writer.write(getFormatString(getValue(recharge,"personDepartment.name"),8));
            writer.write(getFormatString(getValue(recharge,"person.id"),10));
        } else {
            if (recharge.getPersonDepartment()!=null) {
                writer.write(RECHARGE_TYPE_DEPARTMENT);
                writer.write(getFormatString(RECHARGE_EMPTY_CUSTOMER,8));
                writer.write(getFormatString(RECHARGE_EMPTY_ENTITY,8));
                writer.write(getFormatString(getValue(recharge,"personDepartment.name"),8));
                writer.write(getFormatString(RECHARGE_EMPTY_PERSON,10));
            } else {
                if (recharge.getCustomer().getType().equals(CustomerType.CUSTOMER)) {
                    writer.write(RECHARGE_TYPE_CUSTOMER);
                    writer.write(getFormatString(getValue(recharge,"customer.code"),8));
                    writer.write(getFormatString(RECHARGE_EMPTY_ENTITY,8));
                    writer.write(getFormatString(RECHARGE_EMPTY_DEPARTMENT,8));
                    writer.write(getFormatString(RECHARGE_EMPTY_PERSON,10));
                } else {
                    writer.write(RECHARGE_TYPE_ENTITY);
                    writer.write(getFormatString(RECHARGE_EMPTY_CUSTOMER,8));
                    writer.write(getFormatString(getValue(recharge,"customer.code"),8));
                    writer.write(getFormatString(RECHARGE_EMPTY_DEPARTMENT,8));
                    writer.write(getFormatString(RECHARGE_EMPTY_PERSON,10));
                }
            }
        }*/
        if (recharge.getCustomer().getType().equals(CustomerType.CUSTOMER)) {
            writer.write(RECHARGE_TYPE_CUSTOMER);
            writer.write(getFormatString(getValue(recharge,"customer.code"),17));//modified by gaga
            writer.write(getFormatString(RECHARGE_EMPTY_ENTITY,8));
            writer.write(getFormatString(RECHARGE_EMPTY_DEPARTMENT,8));
            writer.write(getFormatString(RECHARGE_EMPTY_PERSON,10));
        } else {
            writer.write(RECHARGE_TYPE_ENTITY);
            writer.write(getFormatString(RECHARGE_EMPTY_CUSTOMER,8));
            writer.write(getFormatString(getValue(recharge,"customer.code"),17));//modified by gaga
            writer.write(getFormatString(RECHARGE_EMPTY_DEPARTMENT,8));
            writer.write(getFormatString(RECHARGE_EMPTY_PERSON,10));
        }
        PurchaseOrderItem item = recharge.getPurchaseOrderItem();
        BigDecimal amount = last ? item.getAmount().subtract(item.getOutputedAmount()) : item.getAmount().multiply(recharge.getPercentage()).divide(new BigDecimal(100d), 2, BigDecimal.ROUND_HALF_UP);
        writer.write(getFormatString(decimalFormat.format(amount.doubleValue()), 14));
        if (!last) {
            item.setOutputedAmount(item.getOutputedAmount().add(amount));
        }
        writer.write(getFormatString(getDecimalValue(recharge,"percentage",decimalFormat),6));
        writer.write(EOL);
    }

    private void writePurchaseOrderItem(OutputStreamWriter writer, PurchaseOrderItem item, boolean recharge) throws IOException {
        boolean isNew=item.getPurchaseOrder().getExportStatus().equals(ExportStatus.UNEXPORTED);
        writer.write(PO_ITEM);
        writer.write(isNew?PO_NEW:PO_CANCEL);
        writer.write(getFormatString(getValue(item,"purchaseOrder.id"),16));
        writer.write(getFormatString(getValue(item,"id"),10));
        writer.write(getFormatString(getValue(item,"purchaseType.code"),8));
        writer.write(getFormatString(getValue(item,"itemSpec"),50));
        writer.write(getFormatString(getDecimalValue(item,"unitPrice",decimalFormat),13));
        if (isNew)
            writer.write(getFormatString(item.getQuantity(),7));
        else {
            int cancelQuantity=(item.getCancelledQuantity()!=null?item.getCancelledQuantity().intValue():0);
            int returnQuantity=(item.getReturnedQuantity()!=null?item.getReturnedQuantity().intValue():0);
            writer.write(getFormatString(item.getQuantity()-cancelQuantity-returnQuantity,7));
        }
        writer.write(getFormatString(getDecimalValue(item,"amount",decimalFormat),14));
        writer.write(getFormatString(getDateValue(item,"dueDate",dateFormat),8));
        writer.write(getFormatString(getValue(item,"buyForDepartment.id"),8));
        writer.write(getFormatString(recharge ? "" : getValue(item,"buyForUser.id"),10));
        writer.write(getFormatString(getValue(item,"projectCode.code"),8));
        
        writer.write(EOL);
    }

    private void writePurchaseOrderItemAllocateData(OutputStreamWriter writer, PurchaseOrderItem item,YesNo isRecharge,Integer departmentId,Integer personId, BigDecimal percentage, String allocateId, PurchaseOrderItemRecharge recharge, boolean last) throws IOException {
        boolean isNew=item.getPurchaseOrder().getExportStatus().equals(ExportStatus.UNEXPORTED);
        writer.write(PO_ITEM_ALLOCATE);
        writer.write(isNew?PO_NEW:PO_CANCEL);
        writer.write(getFormatString(getValue(item,"purchaseOrder.id"),16));
        writer.write(getFormatString(getValue(item,"id"),10));
        writer.write(getFormatString(getValue(item,"purchaseType.code"),8));
        writer.write(getFormatString(getValue(item,"itemSpec"),50));
        if (percentage==null)
            writer.write(getFormatString(getDecimalValue(item,"unitPrice",decimalFormat),13));
        else {
            BigDecimal amount = last ? item.getUnitPrice().subtract(item.getOutputedPrice()) : recharge.getAmount().divide(new BigDecimal(item.getQuantity()), 2, BigDecimal.ROUND_HALF_UP);
            writer.write(getFormatString(decimalFormat.format(amount.doubleValue()),13));
            if (!last) {
                item.setOutputedPrice(item.getOutputedPrice().add(amount));
            }
        }
        if (isNew)
            writer.write(getFormatString(item.getQuantity(),7));
        else {
            int cancelQuantity=(item.getCancelledQuantity()!=null?item.getCancelledQuantity().intValue():0);
            int returnQuantity=(item.getReturnedQuantity()!=null?item.getReturnedQuantity().intValue():0);
            writer.write(getFormatString(item.getQuantity()-cancelQuantity-returnQuantity,7));
        }
        if (percentage==null)
            writer.write(getFormatString(getDecimalValue(item,"amount",decimalFormat),14));
        else {
            //writer.write(getFormatString(decimalFormat.format(item.getAmount().multiply(percentage).divide(new BigDecimal(100d), BigDecimal.ROUND_HALF_EVEN).doubleValue()),14));
            writer.write(getFormatString(decimalFormat.format(recharge.getAmount().doubleValue()),14));
        }
        writer.write(getFormatString(getDateValue(item,"dueDate",dateFormat),8));
        writer.write(isRecharge.equalsYesNo(YesNo.YES)?RECHARGE_YES:RECHARGE_NO);
        //writer.write(getFormatString(getValue(item,"buyForDepartment.id"),8));
        //writer.write(getFormatString(getValue(item,"buyForUser.id"),10));
        writer.write(getFormatString(departmentId==null?"":departmentId.toString(),8));
        writer.write(getFormatString(personId==null?"":personId.toString(),10));
        writer.write(getFormatString(allocateId,20));
        writer.write(getFormatString(getValue(item,"description"), 24));
        writer.write(EOL);
    }
    
    private void writePurchaseOrder(OutputStreamWriter writer,PurchaseOrder po) throws IOException {
        writer.write(PO_MASTER);
        writer.write(po.getExportStatus().equals(ExportStatus.UNEXPORTED)?PO_NEW:PO_CANCEL);
        writer.write(getFormatString(getValue(po,"id"),16));
        writer.write(getFormatString(getValue(po,"title"),50));
        writer.write(getFormatString(getValue(po,"supplier.code"),8));
        writer.write(getFormatString(getValue(po,"exchangeRate.currency.code"),8));
        writer.write(getFormatString(getDecimalValue(po,"amount",decimalFormat),14));
        writer.write(getFormatString(getValue(po,"createUser.loginName"),20));
        writer.write(getFormatString(getDateValue(po,"createDate",dateFormat),8));
        writer.write(getFormatString(getValue(po,"erpNo"),20));
        writer.write(EOL);
    }
    
    public String getRemoteExportFileName() {
        return getRemoteFileName("po","txt");
    }
    


}
