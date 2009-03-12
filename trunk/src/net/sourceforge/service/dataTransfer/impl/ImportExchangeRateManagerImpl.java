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
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.LogFactory;

import net.sourceforge.model.admin.Currency;
import net.sourceforge.model.admin.ExchangeRate;
import net.sourceforge.model.admin.Site;

/**
 * 导入ExchangeRate的类
 * @author ych
 * @version 1.0 (Dec 29, 2005)
 */
public class ImportExchangeRateManagerImpl extends BaseImportManager {

    
    private static final int LINE_LENGTH = 70;
    private static final int CODE_BEGIN_INDEX=0;
    private static final int CODE_END_INDEX=8;
    private static final int RATE_BEGIN_INDEX=58;
    private static final int RATE_END_INDEX=70;
    
    
    public ImportExchangeRateManagerImpl() {
        super();
        log = LogFactory.getLog(ImportExchangeRateManagerImpl.class);
        importClassName="ExchangeRate";
        remoteFileName="exchrate.txt";
        folder="exchrate";
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.dataTransfer.impl.BaseImportManager#importFromTextFile(net.sourceforge.model.admin.Site, java.lang.String)
     */
    public void importFromTextFile(Site site, String localFileName) throws Exception {
        List exchangeRateList=dao.getExchangeRateList(site);
        BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(localFileName), FILE_ENCODE) );
        String line;
        try {
            while((line=reader.readLine())!=null) {
                if (line.length()<LINE_LENGTH) continue;
                Currency currency = getCurrency(line);
                ExchangeRate exchangeRate = getExchangeRate(exchangeRateList,currency);
                boolean insert=false;
                if (exchangeRate==null) {
                    insert=true;
                    exchangeRate=new ExchangeRate();
                }
                exchangeRate.setSite(site);
                exchangeRate.setCurrency(currency);
                exchangeRate.setExchangeRate(getRate(line));
                dao.updateExchangeRate(exchangeRate,insert);
                exchangeRateList.add(exchangeRate);
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

    private Currency getCurrency(String line) {
        return dao.getCurrency(line.substring(CODE_BEGIN_INDEX,CODE_END_INDEX).trim());
    }
    
    private BigDecimal getRate(String line) {
        return new BigDecimal(line.substring(RATE_BEGIN_INDEX,RATE_END_INDEX).trim());
    }
    
    private ExchangeRate getExchangeRate(List exchangeRateList,Currency currency) {
        for (Iterator itor = exchangeRateList.iterator(); itor.hasNext();) {
            ExchangeRate exchangeRate = (ExchangeRate) itor.next();
            if (exchangeRate.getCurrency().equals(currency))
                return exchangeRate;
        }
        return null;
    }
}
