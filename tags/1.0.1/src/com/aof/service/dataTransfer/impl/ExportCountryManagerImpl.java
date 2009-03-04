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

import com.aof.model.admin.Country;
import com.aof.model.admin.Site;

/**
 * 导出Country的类
 * @author ych
 * @version 1.0 (Dec 27, 2005)
 */
public class ExportCountryManagerImpl extends BaseExportManager {

    
    public ExportCountryManagerImpl() {
        super();
        log = LogFactory.getLog(ExportCountryManagerImpl.class);
        exportClassName="Country";
        folder="country";
    }

    public String exportTextFile(Site site) throws Exception {
        List countryList=dao.getCountryList(site);
        if (countryList.size()>0) {
            File tempFile=new File(getLocalFileName("country","txt"));
            OutputStreamWriter writer=new OutputStreamWriter(new FileOutputStream(tempFile),FILE_ENCODE);
            for (Iterator itorExpense = countryList.iterator(); itorExpense.hasNext();) {
                Country country=(Country) itorExpense.next();
                writeCountry(writer,country);
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
    
    private void writeCountry(OutputStreamWriter writer, Country country) throws IOException {
        writer.write(getFormatString(getValue(country,"id"),8));
        writer.write(getFormatString(getValue(country,"engName"),28));
        writer.write(getFormatString(getValue(country,"shortName"),3));
        writer.write(EOL);
    }

    
    public String getRemoteExportFileName() {
        return "country.txt";
    }

    
}
