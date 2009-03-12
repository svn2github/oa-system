/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.service.admin;

import net.sourceforge.model.admin.DataTransferParameter;


/**
 * 定义ERP数据转换的接口
 * @author ych
 * @version 1.0 (Dec 20, 2005)
 */
public interface DataTransferManager {

    public void startJob(DataTransferParameter para);
    
    public void resetJob(DataTransferParameter para);
    
    public void removeJob(DataTransferParameter para);
    
    public void updateJobParameter(DataTransferParameter para);
    
    public void exportPurchaseOrder(DataTransferParameter para) throws Exception;
    
    public void exportExpense(DataTransferParameter para) throws Exception;
    
    public void exportSupplier(DataTransferParameter para) throws Exception;
    
    public void exportDepartment(DataTransferParameter para) throws Exception;
    
    public void exportCountry(DataTransferParameter para) throws Exception;
    
    public void exportPurchaseOrderReceipt(DataTransferParameter para) throws Exception;
    
    public void importPurchaseType(DataTransferParameter para) throws Exception;
    
    public void importExpenseCategory(DataTransferParameter para) throws Exception;
    
    public void importCustomer(DataTransferParameter para) throws Exception;

    public void importExchangeRate(DataTransferParameter para) throws Exception;
    

    
    
}
