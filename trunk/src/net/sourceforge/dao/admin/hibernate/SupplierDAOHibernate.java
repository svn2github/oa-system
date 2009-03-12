/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.admin.hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.admin.SupplierDAO;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.dao.convert.QueryParameterConvert;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.orm.hibernate.HibernateCallback;

import net.sourceforge.model.admin.Currency;
import net.sourceforge.model.admin.ExchangeRate;
import net.sourceforge.model.admin.GlobalMailReminder;
import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.Supplier;
import net.sourceforge.model.admin.SupplierItem;
import net.sourceforge.model.admin.query.SupplierQueryCondition;
import net.sourceforge.model.admin.query.SupplierQueryOrder;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.model.metadata.SupplierConfirmStatus;
import net.sourceforge.model.metadata.SupplierPromoteStatus;
import net.sourceforge.model.metadata.YesNo;
import com.shcnc.hibernate.CompositeQuery;
import com.shcnc.hibernate.QueryCondition;

/**
 * SupplierDAOµÄHibernateÊµÏÖ 
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class SupplierDAOHibernate extends BaseDAOHibernate implements SupplierDAO {
    private Log log = LogFactory.getLog(SupplierDAOHibernate.class);


    public Supplier getSupplier(Integer id){
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get Supplier with null id");
            return null;
        }
        return (Supplier) getHibernateTemplate().get(Supplier.class, id);
    }

    public Supplier updateSupplier(Supplier supplier) {
        Integer id = supplier.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a supplier with null id");
        }
        Supplier oldSupplier = getSupplier(id);
        if (oldSupplier != null) {
        	try{
                PropertyUtils.copyProperties(oldSupplier,supplier);
        	}
        	catch(Exception e)
        	{
                throw new RuntimeException("copy error£º"+e);
        	}
        	getHibernateTemplate().update(oldSupplier);	
        	return oldSupplier;
        }
        else
        {
        	throw new RuntimeException("supplier not found");
        }
    }

	public Supplier insertSupplier(Supplier supplier) {
       	getHibernateTemplate().save(supplier);
       	return supplier;
    }

    private final static String SQL_COUNT = "select count(*) from Supplier supplier";

    private final static String SQL = "from Supplier supplier";

    private final static Object[][] QUERY_CONDITIONS = {
    	{ SupplierQueryCondition.CONTRACT_NOT_ACTIVE, "(supplier.contractStartDate is not null and supplier.contractStartDate>?)",null},
		{ SupplierQueryCondition.CONTRACT_EXPIRED, "(supplier.contractExpireDate is not null and supplier.contractExpireDate<?)",null},
		{ SupplierQueryCondition.CONTRACT_ACTIVE, " ((supplier.contractStartDate is null and supplier.contractExpireDate is null) " +  
				" or (supplier.contractStartDate is null and supplier.contractExpireDate is not null and supplier.contractExpireDate>?) " +
				" or (supplier.contractStartDate is not null and supplier.contractExpireDate is null and supplier.contractStartDate<?) " +
				" or (supplier.contractStartDate is not null and supplier.contractExpireDate is not null and supplier.contractStartDate<? and supplier.contractExpireDate>?) )",null},
        { SupplierQueryCondition.ID_EQ, "supplier.id = ?", null },        
    	{ SupplierQueryCondition.CODE_LIKE, "supplier.code like ?", new LikeConvert() },
		{ SupplierQueryCondition.NAME_LIKE, "supplier.name like ?", new LikeConvert() },
		{ SupplierQueryCondition.SITE_ID_EQ, "supplier.site.id = ?", null },
		{ SupplierQueryCondition.GLOBAL_OR_SITE_ID_EQ, "supplier.promoteStatus=? or supplier.site.id = ?", new QueryParameterConvert() {

            public Object convert(Object src) {
                return new Object[] { SupplierPromoteStatus.GLOBAL, src };
            }
            
        }},
		{ SupplierQueryCondition.COUNTRY_ID_EQ, "supplier.country.id = ?", null },
		{ SupplierQueryCondition.CITY_ID_EQ, "supplier.city.id = ?", null },
		{ SupplierQueryCondition.ENABLED_EQ, "supplier.enabled = ?", null },
		{ SupplierQueryCondition.DRAFT_EQ, "supplier.draft = ?", null },
		{ SupplierQueryCondition.CONFIRM_EQ, "supplier.confirmed = ?", null },
		{ SupplierQueryCondition.PROMOTE_STATUS_EQ, "supplier.promoteStatus = ?", null },
		{ SupplierQueryCondition.PROMOTE_STATUS_NEQ, "supplier.promoteStatus <> ?", null },
		{ SupplierQueryCondition.CONFIRM_TYPE_EQ, "supplier.confirmStatus = ? ",null },
        { SupplierQueryCondition.FOR_SITE_ID_EQ, "((supplier.site) is null or (supplier.site.id = ?))",null },
        { SupplierQueryCondition.IS_AIRTICKET, "supplier.airTicket = ?", null },
		
    };
    
    private final static Object[][] QUERY_ORDERS = {
        { SupplierQueryOrder.ID, "supplier.id" },
        { SupplierQueryOrder.CODE, "supplier.code" },
        { SupplierQueryOrder.CITYENGNAME, "supplier.city.engName" },
        { SupplierQueryOrder.CITYCHNNAME, "supplier.city.chnName" },
        { SupplierQueryOrder.NAME, "supplier.name" },
        { SupplierQueryOrder.PROMOTE_STATUS, "supplier.promoteStatus" },
        { SupplierQueryOrder.ENABLED, "supplier.enabled" },
        { SupplierQueryOrder.DRAFT, "supplier.draft" },
    };
    
    public int getSupplierListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getSupplierList(final Map conditions, final int pageNo, final int pageSize, final SupplierQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }
    
    public String getLastSupplierCode(){
    	String result=(String)getHibernateTemplate().find(
				"select max(supplier.code) from Supplier supplier where supplier.code like 'SP%'").get(0);
    	return (result==null?"SP000000":result+"000000").substring(0,8);
    }

    public boolean isCodeUsed(String code,Site site) {
        if (site!=null)
            return getHibernateTemplate().iterate("select s.code from Supplier s where s.code = ? and s.site.id=?", new Object[]{code,site.getId()}, new Type[]{Hibernate.STRING,Hibernate.INTEGER}).hasNext();
        else
            return getHibernateTemplate().iterate("select s.code from Supplier s where s.code = ? and s.site.id is null", code, Hibernate.STRING).hasNext();
    }

    public List getSuitableSupplierListForPurchase(final Site site, final PurchaseSubCategory psc, final List exchangeRateList) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                CompositeQuery query = new CompositeQuery("select si from SupplierItem si join si.supplier s", "s.name, si.sepc", session);
                QueryCondition qc = query.createQueryCondition("s.promoteStatus = ? or s.site.id = ?");
                qc.appendParameter(SupplierPromoteStatus.GLOBAL);
                qc.appendParameter(site.getId());
                query.createQueryCondition("si.enabled = ?").appendParameter(EnabledDisabled.ENABLED);
                query.createQueryCondition("s.enabled = ?").appendParameter(EnabledDisabled.ENABLED);
                query.createQueryCondition("si.purchaseSubCategory.id = ?").appendParameter(psc.getId());
                qc = query.createQueryCondition("(s.confirmStatus = ? or s.confirmed = ?)");
                qc.appendParameter(SupplierConfirmStatus.MODIFY);
                qc.appendParameter(YesNo.YES);
                List supplierItemList = query.list();
                List currencyList = null;
                if (exchangeRateList != null) {
                    currencyList = new ArrayList();
                    for (Iterator itor = exchangeRateList.iterator(); itor.hasNext();) {
                        ExchangeRate er = (ExchangeRate) itor.next();
                        currencyList.add(er.getCurrency());
                    }
                }
                List result = new ArrayList();
                for (Iterator itor = supplierItemList.iterator(); itor.hasNext();) {
                    SupplierItem si = (SupplierItem) itor.next();
                    if (exchangeRateList == null || currencyList.contains(si.getCurrency())) {
                        Supplier s = si.getSupplier();
                        s.addItem(si);
                        if (!result.contains(s)) {
                            result.add(s);
                        }
                    }
                }
                return result;
            }
            
        });
    }

    public List getSuitableSupplierItemListForPurchase(final Supplier supplier, final PurchaseSubCategory psc, final Currency currency) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                CompositeQuery query = new CompositeQuery
                    ("select si from SupplierItem si", "si.sepc", session);
                query.createQueryCondition("si.enabled = ?").appendParameter(EnabledDisabled.ENABLED.getEnumCode());
                query.createQueryCondition("si.supplier.id = ?").appendParameter(supplier.getId());
                query.createQueryCondition("si.purchaseSubCategory.id = ?").appendParameter(psc.getId());
                query.createQueryCondition("si.currency.code = ?").appendParameter(currency.getCode());
                return query.list();
            }
            
        });
    }
    
    public List getSupplierMaintainerNotResponsedList(final Site site, final Date time, final GlobalMailReminder gmr) {
        return getHibernateTemplate().find("from Supplier s where s.site.id=? and s.promoteStatus=? and s.promoteDate<=? " +
                "and s.emailTimes<? and (s.emailDate is null or s.emailDate<=?) and s.enabled=?",
                new Object[] {site.getId(),SupplierPromoteStatus.REQUEST.getEnumCode(),
                    gmr.getResponseDate(time),new Integer(gmr.getMaxTime()),gmr.getEmailDate(time),
                    EnabledDisabled.ENABLED.getEnumCode()},
                new Type[] { Hibernate.INTEGER , Hibernate.INTEGER, Hibernate.TIMESTAMP , Hibernate.INTEGER ,Hibernate.TIMESTAMP, Hibernate.INTEGER });
    }
    
}
