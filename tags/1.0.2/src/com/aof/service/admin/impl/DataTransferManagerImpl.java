/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.aof.dao.admin.DataTransferDAO;
import com.aof.model.admin.DataTransferParameter;
import com.aof.service.BaseManager;
import com.aof.service.admin.DataTransferManager;
import com.aof.service.admin.EmailManager;
import com.aof.service.dataTransfer.ExportManager;
import com.aof.service.dataTransfer.ImportManager;

/**
 * DataTransferManagerµÄÊµÏÖ
 * @author ych
 * @version 1.0 (Dec 20, 2005)
 */
public class DataTransferManagerImpl extends BaseManager implements DataTransferManager {
    
    private DataTransferDAO dao;
    
    private Map map;

    private EmailManager emailManager;
    
    private ExportManager poExportManager;
    
    private ExportManager expenseExportManager;
    
    private ExportManager supplierExportManager;
    
    private ExportManager departmentExportManager;
    
    private ExportManager countryExportManager;
    
    private ExportManager poReceiptExportManager;
    
    private ImportManager purchaseTypeImportManager;

    private ImportManager expenseCategoryImportManager;
    
    private ImportManager customerImportManager;
    
    private ImportManager exchangeRateImportManager;
    
    
    
    public void setEmailManager(EmailManager emailManager) {
        this.emailManager = emailManager;
    }


    /**
     * @param poReceiptExportManager The poReceiptExportManager to set.
     */
    public void setPoReceiptExportManager(ExportManager poReceiptExportManager) {
        this.poReceiptExportManager = poReceiptExportManager;
    }


    /**
     * @param poExportManager The poExportManager to set.
     */
    public void setPoExportManager(ExportManager poExportManager) {
        this.poExportManager = poExportManager;
    }

    
    /**
     * @param expenseExportManager The expenseExportManager to set.
     */
    public void setExpenseExportManager(ExportManager expenseExportManager) {
        this.expenseExportManager = expenseExportManager;
    }

    

    /**
     * @param supplierExportManager The supplierExportManager to set.
     */
    public void setSupplierExportManager(ExportManager supplierExportManager) {
        this.supplierExportManager = supplierExportManager;
    }

    
    
    /**
     * @param countryExportManager The countryExportManager to set.
     */
    public void setCountryExportManager(ExportManager countryExportManager) {
        this.countryExportManager = countryExportManager;
    }


    /**
     * @param departmentExportManager The departmentExportManager to set.
     */
    public void setDepartmentExportManager(ExportManager departmentExportManager) {
        this.departmentExportManager = departmentExportManager;
    }


    
    
    /**
     * @param customerImportManager The customerImportManager to set.
     */
    public void setCustomerImportManager(ImportManager customerImportManager) {
        this.customerImportManager = customerImportManager;
    }


    /**
     * @param exchangeRateImportManager The exchangeRateImportManager to set.
     */
    public void setExchangeRateImportManager(ImportManager exchangeRateImportManager) {
        this.exchangeRateImportManager = exchangeRateImportManager;
    }


    /**
     * @param expenseCategoryImportManager The expenseCategoryImportManager to set.
     */
    public void setExpenseCategoryImportManager(ImportManager expenseCategoryImportManager) {
        this.expenseCategoryImportManager = expenseCategoryImportManager;
    }


    /**
     * @param purchaseTypeImportManager The purchaseTypeImportManager to set.
     */
    public void setPurchaseTypeImportManager(ImportManager purchaseTypeImportManager) {
        this.purchaseTypeImportManager = purchaseTypeImportManager;
    }


    /**
     * @param dao
     */
    public void setDataTransferDAO(DataTransferDAO dao) {
        this.dao = dao;
    }

    public void init()  {
        //test weather it is ych's computer
        //File testFile=new File("D:\\change.txt");
        //if (!testFile.exists()) return;
        
        map = new HashMap();
        List paraList = dao.getDataTransferParameterList();
        for (Iterator itor = paraList.iterator(); itor.hasNext();) {
            DataTransferParameter para = (DataTransferParameter) itor.next();
            DataTransferThread dtThread=new DataTransferThread(emailManager,this,para);
            map.put(para,dtThread);
            dtThread.start();
        }
    }
    
    public void removeJob(DataTransferParameter para) {
        DataTransferThread dtThread=(DataTransferThread)map.get(para);
        if (dtThread!=null) {
            dtThread.setJobFinish(true);
            dtThread.interrupt();
            map.remove(para);
        }
    }
    
    public void startJob(DataTransferParameter para) {
        DataTransferThread dtThread=(DataTransferThread)map.get(para);
        if (dtThread!=null) {
            dtThread.setManualJob(true);
            dtThread.interrupt();
        }
    }
    
    public void resetJob(DataTransferParameter para) {
        DataTransferThread dtThread=(DataTransferThread)map.get(para);
        if (dtThread==null) {
            dtThread=new DataTransferThread(emailManager,this,para);
            map.put(para,dtThread);
            dtThread.start();
        } else {
            dtThread.setPara(para);
            dtThread.setResetJob(true);
            dtThread.interrupt();
        }
    }

    public void updateJobParameter(DataTransferParameter para) {
        DataTransferThread dtThread=(DataTransferThread)map.get(para);
        if (dtThread!=null) {
            dtThread.setPara(para);
        }
    }
    
    public void destroy() {
        for (Iterator itor=map.keySet().iterator();itor.hasNext();) {
            DataTransferThread dtThread=(DataTransferThread) map.get(itor.next());
            dtThread.setJobFinish(true);
            dtThread.interrupt();
        }
        
    }

    public void exportPurchaseOrder(DataTransferParameter para) throws Exception {
        poExportManager.exportFile(para);
    }

    public void exportExpense(DataTransferParameter para) throws Exception {
        expenseExportManager.exportFile(para);
    }

    public void exportSupplier(DataTransferParameter para) throws Exception {
        supplierExportManager.exportFile(para);
    }

    public void exportDepartment(DataTransferParameter para) throws Exception {
        departmentExportManager.exportFile(para);
    }
    
    public void exportCountry(DataTransferParameter para) throws Exception {
        countryExportManager.exportFile(para);
    }
    
    public void exportPurchaseOrderReceipt(DataTransferParameter para) throws Exception {
        poReceiptExportManager.exportFile(para);
    }

    public void importCustomer(DataTransferParameter para) throws Exception {
        customerImportManager.importFile(para);
        
    }

    public void importExchangeRate(DataTransferParameter para) throws Exception {
        exchangeRateImportManager.importFile(para);
        
    }

    public void importExpenseCategory(DataTransferParameter para) throws Exception {
        expenseCategoryImportManager.importFile(para);
        
    }

    public void importPurchaseType(DataTransferParameter para) throws Exception {
        purchaseTypeImportManager.importFile(para);
    }
    
    
}
