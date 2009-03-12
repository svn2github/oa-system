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
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.LogFactory;

import net.sourceforge.model.admin.ExpenseCategory;
import net.sourceforge.model.admin.ExpenseSubCategory;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.model.metadata.ExpenseType;
import net.sourceforge.model.metadata.YesNo;

/**
 * 导入ExpenseCategory的类
 * @author ych
 * @version 1.0 (Dec 29, 2005)
 */
public class ImportExpenseCategoryManagerImpl extends BaseImportManager {

    private static final int EXPENSE_LINE_LENGTH = 60;
    private static final int EXPENSE_SUB_LINE_LENGTH = 68;
    private static final int TYPE_BEGIN_INDEX=0;
    private static final int TYPE_END_INDEX=1;
    
    private static final int EXPENSE_CODE_BEGIN_INDEX=1;
    private static final int EXPENSE_CODE_END_INDEX=9;
    private static final int EXPENSE_DESC_BEGIN_INDEX=9;
    private static final int EXPENSE_DESC_END_INDEX=59;
    private static final int EXPENSE_ENABLE_BEGIN_INDEX=59;
    private static final int EXPENSE_ENABLE_END_INDEX=60;
    
    
    private static final int EXPENSE_SUB_CODE_BEGIN_INDEX=1;
    private static final int EXPENSE_SUB_CODE_END_INDEX=9;
    private static final int EXPENSE_SUB_SUBCODE_BEGIN_INDEX=9;
    private static final int EXPENSE_SUB_SUBCODE_END_INDEX=17;
    private static final int EXPENSE_SUB_DESC_BEGIN_INDEX=17;
    private static final int EXPENSE_SUB_DESC_END_INDEX=67;
    private static final int EXPENSE_SUB_ENABLE_BEGIN_INDEX=67;
    private static final int EXPENSE_SUB_ENABLE_END_INDEX=68;
    
    private static final String TYPE_EXPENSE="1";
    private static final String TYPE_EXPENSE_SUB="2";
    
    private static final ExpenseType DEFAULT_EXPENSE_TYPE=ExpenseType.OTHER;
    private static final YesNo DEFAULT_EXPENSE_SUB_IS_HOTEL=YesNo.NO;
    
    
    public ImportExpenseCategoryManagerImpl() {
        super();
        log = LogFactory.getLog(ImportExpenseCategoryManagerImpl.class);
        importClassName="ExpenseCategory";
        remoteFileName="exptype.txt";
        folder="exptype";
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.dataTransfer.impl.BaseImportManager#importFromTextFile(net.sourceforge.model.admin.Site, java.lang.String)
     */
    public void importFromTextFile(Site site, String localFileName) throws Exception {
        List expenseCategoryList=dao.getExpenseCategoryList(site);
        List expenseSubCategoryList=dao.getExpenseSubCategoryList(site);
        BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(localFileName), FILE_ENCODE) );
        String line;
        try {
            while((line=reader.readLine())!=null) {
                if (line.length()<TYPE_END_INDEX) continue;
                if (getType(line).equals(TYPE_EXPENSE)) {
                    if (line.length()<EXPENSE_LINE_LENGTH) continue;
                    String erpId = getExpenseCode(line);
                    ExpenseCategory expenseCategory = getExpenseCategory(expenseCategoryList,erpId);
                    boolean insert=false;
                    if (expenseCategory==null) {
                        insert=true;
                        expenseCategory=new ExpenseCategory();
                        expenseCategory.setType(DEFAULT_EXPENSE_TYPE);
                    }
                    expenseCategory.setSite(site);
                    expenseCategory.setReferenceErpId(erpId);
                    expenseCategory.setEnabled(getExpenseEnable(line));
                    expenseCategory.setDescription(getExpenseDesc(line));
                    dao.updateExpenseCategory(expenseCategory,insert);
                    if (insert) expenseCategoryList.add(expenseCategory);
                } else {
                    if (line.length()<EXPENSE_SUB_LINE_LENGTH) continue;
                    String erpId = getExpenseSubCode(line);
                    ExpenseSubCategory expenseSubCategory = getExpenseSubCategory(expenseSubCategoryList,erpId);
                    boolean insert=false;
                    if (expenseSubCategory==null) {
                        insert=true;
                        expenseSubCategory=new ExpenseSubCategory();
                    }
                    if (insert) {
                        ExpenseCategory expenseCategory = getExpenseCategory(site,line);
                        expenseSubCategory.setExpenseCategory(expenseCategory);
                        expenseSubCategory.setIsHotel(DEFAULT_EXPENSE_SUB_IS_HOTEL);
                    }
                    expenseSubCategory.setDescription(getExpenseSubDesc(line));
                    expenseSubCategory.setEnabled(getExpenseSubEnable(line));
                    expenseSubCategory.setReferenceErpId(erpId);
                    dao.updateExpenseSubCategory(expenseSubCategory,insert);
                    if (insert) expenseSubCategoryList.add(expenseSubCategory);
                }
            } 
        } finally {
            reader.close();
        }

    }

    
    private ExpenseSubCategory getExpenseSubCategory(List expenseSubCategoryList, String erpId) {
        for (Iterator itor = expenseSubCategoryList.iterator(); itor.hasNext();) {
            ExpenseSubCategory expenseSubCategory = (ExpenseSubCategory) itor.next();
            if (expenseSubCategory.getReferenceErpId()!=null) {
                if (expenseSubCategory.getReferenceErpId().equals(erpId))
                    return expenseSubCategory;
            }
        }
        return null;
    }

    private ExpenseCategory getExpenseCategory(List expenseCategoryList, String erpId) {
        for (Iterator itor = expenseCategoryList.iterator(); itor.hasNext();) {
            ExpenseCategory expenseCategory = (ExpenseCategory) itor.next();
            if (expenseCategory.getReferenceErpId()!=null) {
                if (expenseCategory.getReferenceErpId().equals(erpId)) 
                    return expenseCategory;
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.dataTransfer.impl.BaseImportManager#importFromXmlFile(net.sourceforge.model.admin.Site, java.lang.String)
     */
    public void importFromXmlFile(Site site, String localFileName) throws Exception {
        // TODO Auto-generated method stub
    }

    private String getType(String line) {
        return line.substring(TYPE_BEGIN_INDEX,TYPE_END_INDEX).equals(TYPE_EXPENSE)?TYPE_EXPENSE:TYPE_EXPENSE_SUB;
    }
    
    private String getExpenseCode(String line) {
        return line.substring(EXPENSE_CODE_BEGIN_INDEX,EXPENSE_CODE_END_INDEX).trim();
    }
    
    private String getExpenseSubCode(String line) {
        return line.substring(EXPENSE_SUB_SUBCODE_BEGIN_INDEX,EXPENSE_SUB_SUBCODE_END_INDEX).trim();
    }
    
    private EnabledDisabled getExpenseEnable(String line) {
        return line.substring(EXPENSE_ENABLE_BEGIN_INDEX,EXPENSE_ENABLE_END_INDEX).equals(ENABLED)?EnabledDisabled.ENABLED:EnabledDisabled.DISABLED;
    }
    
    private EnabledDisabled getExpenseSubEnable(String line) {
        return line.substring(EXPENSE_SUB_ENABLE_BEGIN_INDEX,EXPENSE_SUB_ENABLE_END_INDEX).equals(ENABLED)?EnabledDisabled.ENABLED:EnabledDisabled.DISABLED;
    }
    
    private String getExpenseDesc(String line) {
        return line.substring(EXPENSE_DESC_BEGIN_INDEX,EXPENSE_DESC_END_INDEX).trim();
    }
    
    private String getExpenseSubDesc(String line) {
        return line.substring(EXPENSE_SUB_DESC_BEGIN_INDEX,EXPENSE_SUB_DESC_END_INDEX).trim();
    }

    private ExpenseCategory getExpenseCategory(Site site,String line) {
        return dao.getExpenseCategory(site,line.substring(EXPENSE_SUB_CODE_BEGIN_INDEX,EXPENSE_SUB_CODE_END_INDEX).trim());
    }
}
