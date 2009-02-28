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

import com.aof.model.admin.Department;
import com.aof.model.admin.Site;
import com.aof.model.metadata.EnabledDisabled;

/**
 * 导出Department的类
 * @author ych
 * @version 1.0 (Dec 27, 2005)
 */
public class ExportDepartmentManagerImpl extends BaseExportManager {

    
   
    public ExportDepartmentManagerImpl() {
        super();
        log = LogFactory.getLog(ExportDepartmentManagerImpl.class);
        exportClassName="Department";
        folder="department";
    }

    public String exportTextFile(Site site) throws Exception {
        List departmentList=dao.getDepartmentList(site);
        if (departmentList.size()>0) {
            File tempFile=new File(getLocalFileName("department","txt"));
            OutputStreamWriter writer=new OutputStreamWriter(new FileOutputStream(tempFile),FILE_ENCODE);
            for (Iterator itorExpense = departmentList.iterator(); itorExpense.hasNext();) {
                Department department=(Department) itorExpense.next();
                writeDepartment(writer,department);
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

    private void writeDepartment(OutputStreamWriter writer, Department department) throws IOException {
        writer.write(getFormatString(getValue(department,"id"),8));
        writer.write(getFormatString(getValue(department,"name"),50));
        writer.write(getFormatString(getValue(department,"parentDepartment.id"),8));
        writer.write(department.getEnabled().equals(EnabledDisabled.ENABLED)?ENABLED:DISABLED);
        writer.write(EOL);
    }
    
    public String getRemoteExportFileName() {
        return "department.txt";
    }
    
}
