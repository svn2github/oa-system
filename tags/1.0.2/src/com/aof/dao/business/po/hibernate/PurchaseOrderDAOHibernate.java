package com.aof.dao.business.po.hibernate;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.HibernateTemplate;

import com.aof.dao.BaseDAOHibernate;
import com.aof.dao.business.po.PurchaseOrderDAO;
import com.aof.dao.convert.LikeConvert;
import com.aof.model.admin.SiteMailReminder;
import com.aof.model.business.po.PaymentScheduleDetail;
import com.aof.model.business.po.PurchaseOrder;
import com.aof.model.business.po.PurchaseOrderApproveRequest;
import com.aof.model.business.po.PurchaseOrderAttachment;
import com.aof.model.business.po.PurchaseOrderAttachmentContent;
import com.aof.model.business.po.PurchaseOrderHistory;
import com.aof.model.business.po.PurchaseOrderHistoryItem;
import com.aof.model.business.po.PurchaseOrderItem;
import com.aof.model.business.po.PurchaseOrderItemAttachment;
import com.aof.model.business.po.PurchaseOrderItemAttachmentContent;
import com.aof.model.business.po.PurchaseOrderItemRecharge;
import com.aof.model.business.po.query.PurchaseOrderItemQueryCondition;
import com.aof.model.business.po.query.PurchaseOrderItemQueryOrder;
import com.aof.model.business.po.query.PurchaseOrderQueryCondition;
import com.aof.model.business.po.query.PurchaseOrderQueryOrder;
import com.aof.model.business.pr.PurchaseRequest;
import com.aof.model.metadata.PurchaseOrderStatus;
import com.shcnc.hibernate.CompositeQuery;

public class PurchaseOrderDAOHibernate extends BaseDAOHibernate implements PurchaseOrderDAO {
    private Log log = LogFactory.getLog(PurchaseOrderDAOHibernate.class);

    public PurchaseOrder getPurchaseOrder(String id) {
        if (id == null) {
            if (log.isDebugEnabled())
                log.debug("try to get PurchaseOrder with null id");
            return null;
        }
        return (PurchaseOrder) getHibernateTemplate().get(PurchaseOrder.class, id);
    }

    public PurchaseOrder updatePurchaseOrder(PurchaseOrder purchaseOrder) {
        getHibernateTemplate().update(purchaseOrder);
        return purchaseOrder;
    }

    public PurchaseOrder insertPurchaseOrder(PurchaseOrder purchaseOrder) {
        getHibernateTemplate().save(purchaseOrder);
        return purchaseOrder;
    }

    private final static String SQL_COUNT = "select count(*) from PurchaseOrder purchaseOrder";

    private final static String SQL = "from PurchaseOrder purchaseOrder";

    private final static Object[][] QUERY_CONDITIONS = {
    /* id */
    { PurchaseOrderQueryCondition.ID_EQ, "purchaseOrder.id = ?", null },
    { PurchaseOrderQueryCondition.ID_LIKE, "purchaseOrder.id like ?", new LikeConvert() },

    /* keyPropertyList */

    /* kmtoIdList */

    /* mtoIdList */
    { PurchaseOrderQueryCondition.SITE_ID_EQ, "purchaseOrder.site.id = ?", null },
            { PurchaseOrderQueryCondition.SUPPLIER_ID_EQ, "purchaseOrder.supplier.id = ?", null },
            { PurchaseOrderQueryCondition.SUPPLIER_NAME_LIKE, "purchaseOrder.supplier.name like ?", new LikeConvert() },
            { PurchaseOrderQueryCondition.SUBCATEGORY_ID_EQ, "purchaseOrder.subCategory.id = ?", null },
            { PurchaseOrderQueryCondition.CURRENCY_CODE_EQ, "purchaseOrder.currency.code = ?", null },
            { PurchaseOrderQueryCondition.BASECURRENCY_CODE_EQ, "purchaseOrder.baseCurrency.code = ?", null },
            { PurchaseOrderQueryCondition.CREATEUSER_ID_EQ, "purchaseOrder.createUser.id = ?", null },
            { PurchaseOrderQueryCondition.PURCHASER_ID_EQ, "purchaseOrder.purchaser.id = ?", null },

            /* property */
            { PurchaseOrderQueryCondition.ERPNO_LIKE, "purchaseOrder.erpNo like ?", new LikeConvert() },
            { PurchaseOrderQueryCondition.TITLE_LIKE, "purchaseOrder.title like ?", new LikeConvert() },
            { PurchaseOrderQueryCondition.DESCRIPTION_LIKE, "purchaseOrder.description like ?", new LikeConvert() },
            { PurchaseOrderQueryCondition.AMOUNT_EQ, "purchaseOrder.amount = ?", null },
            { PurchaseOrderQueryCondition.EXCHANGERATE_EQ, "purchaseOrder.exchangeRate = ?", null },
            { PurchaseOrderQueryCondition.STATUS_EQ, "purchaseOrder.status = ?", null },
            { PurchaseOrderQueryCondition.STATUS_IN2, "purchaseOrder.status in(?,?)", null },
            { PurchaseOrderQueryCondition.STATUS_IN3, "purchaseOrder.status in(?,?,?)", null },
            { PurchaseOrderQueryCondition.CREATEDATE_EQ, "purchaseOrder.createDate = ?", null },
            { PurchaseOrderQueryCondition.EXPORTSTATUS_EQ, "purchaseOrder.exportStatus = ?", null },
            { PurchaseOrderQueryCondition.CREATEDATE_GE, "purchaseOrder.createDate >= ?", null },
            { PurchaseOrderQueryCondition.CREATEDATE_LT, "purchaseOrder.createDate < ?", null },
            { PurchaseOrderQueryCondition.CONFIRMDATE_GE, "purchaseOrder.confirmDate >= ?", null },
            { PurchaseOrderQueryCondition.CONFIRMDATE_LT, "purchaseOrder.confirmDate < ?", null },
            { PurchaseOrderQueryCondition.CATEGORY_ID_EQ, "purchaseOrder.subCategory.purchaseCategory.id = ?", null },
            { PurchaseOrderQueryCondition.AMOUNT_GE, "purchaseOrder.amount >= ?", null },
            { PurchaseOrderQueryCondition.AMOUNT_LE, "purchaseOrder.amount <= ?", null },
            { PurchaseOrderQueryCondition.APPROVEREQUESTID_EQ, "purchaseOrder.approveRequestId = ?", null }, };

    private final static Object[][] QUERY_ORDERS = {
        { PurchaseOrderQueryOrder.ID, "purchaseOrder.id" },
        { PurchaseOrderQueryOrder.ERPNO, "purchaseOrder.erpNo" }, { PurchaseOrderQueryOrder.TITLE, "purchaseOrder.title" },
        { PurchaseOrderQueryOrder.DESCRIPTION, "purchaseOrder.description" }, { PurchaseOrderQueryOrder.AMOUNT, "purchaseOrder.amount" },
        { PurchaseOrderQueryOrder.EXCHANGERATE, "purchaseOrder.exchangeRate" }, { PurchaseOrderQueryOrder.STATUS, "purchaseOrder.status" },
        { PurchaseOrderQueryOrder.CREATEDATE, "purchaseOrder.createDate" }, { PurchaseOrderQueryOrder.EXPORTSTATUS, "purchaseOrder.exportStatus" },
        { PurchaseOrderQueryOrder.APPROVEREQUESTID, "purchaseOrder.approveRequestId" },
        { PurchaseOrderQueryOrder.SUPPLIER_NAME, "purchaseOrder.supplier.name" },
        { PurchaseOrderQueryOrder.CATEGORY_DESCRIPTION, "purchaseOrder.subCategory.purchaseCategory.description" },
        { PurchaseOrderQueryOrder.SUBCATEGORY_DESCRIPTION, "purchaseOrder.subCategory.description" },
        { PurchaseOrderQueryOrder.CREATEUSER_NAME, "purchaseOrder.createUser.name" },
        { PurchaseOrderQueryOrder.CONFIRMDATE, "purchaseOrder.confirmDate" },
    };

    public int getPurchaseOrderListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getPurchaseOrderList(final Map conditions, final int pageNo, final int pageSize, final PurchaseOrderQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

    public PurchaseOrderItem insertPurchaseOrderItem(PurchaseOrderItem item) {
        getHibernateTemplate().save(item);
        return item;
    }

    public PurchaseOrderItem updatePurchaseOrderItem(PurchaseOrderItem item) {
        Integer id = item.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a PurchaseOrderItem with null id");
        }
        PurchaseOrderItem oldItem = getPurchaseOrderItem(id);
        if (oldItem != null) {
            try {
                PropertyUtils.copyProperties(oldItem, item);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            getHibernateTemplate().update(oldItem);
            return oldItem;
        } else {
            throw new RuntimeException("PurchaseOrderItem not found");
        }
    }

    public PurchaseOrderItem getPurchaseOrderItem(Integer id) {
        if (id == null) {
            if (log.isDebugEnabled())
                log.debug("try to get PurchaseOrderItem with null id");
            return null;
        }
        return (PurchaseOrderItem) getHibernateTemplate().get(PurchaseOrderItem.class, id);
    }

    public void deletePurchaseOrderItem(PurchaseOrderItem item) {
        getHibernateTemplate().delete(item);
    }

    public List getPurchaseOrderItemListByPurchaseRequest(PurchaseRequest pr) {
        return getHibernateTemplate().find("from PurchaseOrderItem poi where poi.purchaseRequestItem.purchaseRequest.id = ?", pr.getId(), Hibernate.STRING);
    }

    public List getIsolationPurchaseOrderItemAttachmentByPurchaseRequest(PurchaseRequest pr) {
        return getHibernateTemplate().find("from PurchaseOrderItemAttachment poia where poia.purchaseOrderItem.purchaseRequestItem.purchaseRequest.id = ? and poia.purchaseOrderItem.purchaseOrder.id is null", pr.getId(), Hibernate.STRING);
    }
    
    public List getIsolationPurchaseOrderItemRechargeByPurchaseRequest(PurchaseRequest pr) {
        return getHibernateTemplate().find("from PurchaseOrderItemRecharge poir where poir.purchaseOrderItem.purchaseRequestItem.purchaseRequest.id = ? and poir.purchaseOrderItem.purchaseOrder.id is null", pr.getId(), Hibernate.STRING);
    }

    public List getIsolationPurchaseOrderItemByPurchaseRequest(PurchaseRequest pr) {
        return getHibernateTemplate().find("from PurchaseOrderItem poi where poi.purchaseRequestItem.purchaseRequest.id = ? and poi.purchaseOrder.id is null", pr.getId(), Hibernate.STRING);
    }

    public void deleteIsolationPurchaseOrderItemByPurchaseRequest(PurchaseRequest pr) {
        HibernateTemplate template = getHibernateTemplate();
        template.delete("from PurchaseOrderItemAttachment poia where poia.purchaseOrderItem.purchaseRequestItem.purchaseRequest.id = ? and poia.purchaseOrderItem.purchaseOrder.id is null", pr.getId(), Hibernate.STRING);
        template.delete("from PurchaseOrderItemRecharge poir where poir.purchaseOrderItem.purchaseRequestItem.purchaseRequest.id = ? and poir.purchaseOrderItem.purchaseOrder.id is null", pr.getId(), Hibernate.STRING);
        template.delete("from PurchaseOrderItem poi where poi.purchaseRequestItem.purchaseRequest.id = ? and poi.purchaseOrder.id is null", pr.getId(), Hibernate.STRING);
    }

    public PurchaseOrderItemAttachment getPurchaseOrderItemAttachment(Integer id) {
        return (PurchaseOrderItemAttachment) this.getHibernateTemplate().get(PurchaseOrderItemAttachment.class, id);
    }

    public InputStream getPurchaseRequestItemAttachmentContent(final Integer id) {
        return (InputStream) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                PurchaseOrderItemAttachmentContent poiac =
                   (PurchaseOrderItemAttachmentContent) session.get(PurchaseOrderItemAttachmentContent.class, id);
                if (poiac != null) {
                    Blob content = poiac.getFileContent();
                    if (content != null)
                        try {
                            return preRead(content.getBinaryStream());
                        } catch (IOException e) {
                            throw new HibernateException(e);
                        }
                }
                return null;
            }

        });
    }

    public PurchaseOrderItemAttachment insertPurchaseOrderItemAttachment(final PurchaseOrderItemAttachment poia, final InputStream inputStream) {
        return (PurchaseOrderItemAttachment) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                session.save(poia);
                PurchaseOrderItemAttachmentContent content = 
                    (PurchaseOrderItemAttachmentContent) session.get(PurchaseOrderItemAttachmentContent.class, poia.getId());
                try {
                    Blob fileContent = Hibernate.createBlob(inputStream);
                    content.setFileContent(fileContent);
                    session.update(content);
                } catch (IOException e) {
                    throw new HibernateException(e);
                }
                return poia;
            }

        });
    }

    public Collection getPurchaseOrderItemAttachments(PurchaseOrderItem poi) {
        String sql = "from PurchaseOrderItemAttachment poia where poia.purchaseOrderItem.id=?";
        return this.getHibernateTemplate().find(sql, poi.getId(), Hibernate.INTEGER);
    }

    public Collection getPurchaseOrderItemRecharges(PurchaseOrderItem poi) {
        String sql = "from PurchaseOrderItemRecharge poir where poir.purchaseOrderItem.id=?";
        return this.getHibernateTemplate().find(sql, poi.getId(), Hibernate.INTEGER);
    }

    public void deletePurchaseOrderItemRecharges(PurchaseOrderItem poi) {
        String sql = "from PurchaseOrderItemRecharge poir where poir.purchaseOrderItem.id=?";
        this.getHibernateTemplate().delete(sql, poi.getId(), Hibernate.INTEGER);
    }

    public void insertPurchaseOrderItemRecharge(PurchaseOrderItemRecharge poir) {
        this.getHibernateTemplate().save(poir);
    }

    public void updatePurchaseOrderItemAttachment(PurchaseOrderItemAttachment poia) {
        this.getHibernateTemplate().update(poia);
    }

    public void deletePurchaseOrderItemAttachment(Integer itemAttachmentId) {
        String sql = "from PurchaseOrderItemAttachment poia where poia.id=?";
        this.getHibernateTemplate().delete(sql, itemAttachmentId, Hibernate.INTEGER);
    }

    public String getMaxPurchaseOrderIdBeginWith(final String prefix) {
        List l = (List) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String sql = "select max(po.id) from PurchaseOrder po";
                CompositeQuery query = new CompositeQuery(sql, "", session);
                query.createQueryCondition("po.id like ?").appendParameter(prefix + "%");
                return query.list();
            }

        });

        if (l.isEmpty())
            return null;
        else
            return (String) l.get(0);

    }

    public List getPurchaseOrderItemAttachmentIdList(PurchaseOrderItem poi) {
        String sql = "select poia.id from PurchaseOrderItemAttachment poia where poia.purchaseOrderItem.id=?";
        return this.getHibernateTemplate().find(sql, poi.getId(), Hibernate.INTEGER);
    }

    public List getPurchaseOrderItemListWithDetails(PurchaseOrder po) {
        List poList = this.getPurchaseOrderItemList(po);
        for (Iterator iter = poList.iterator(); iter.hasNext();) {
            PurchaseOrderItem poi = (PurchaseOrderItem) iter.next();
            poi.setRecharges(new ArrayList());
            poi.setAttachments(new ArrayList());
        }
        String sql = "from PurchaseOrderItemAttachment poia where poia.purchaseOrderItem.purchaseOrder.id=? order by poia.id";
        List poiaList = this.getHibernateTemplate().find(sql, po.getId(), Hibernate.STRING);
        for (Iterator iter = poiaList.iterator(); iter.hasNext();) {
            PurchaseOrderItemAttachment poia = (PurchaseOrderItemAttachment) iter.next();
            poia.getPurchaseOrderItem().getAttachments().add(poia);
        }

        sql = "from PurchaseOrderItemRecharge poir where poir.purchaseOrderItem.purchaseOrder.id=? order by poir.id";
        List poirList = this.getHibernateTemplate().find(sql, po.getId(), Hibernate.STRING);
        for (Iterator iter = poirList.iterator(); iter.hasNext();) {
            PurchaseOrderItemRecharge poir = (PurchaseOrderItemRecharge) iter.next();
            poir.getPurchaseOrderItem().getRecharges().add(poir);
        }
        return poList;
    }

    public List getPurchaseOrderAttachmentList(PurchaseOrder po) {
        String sql="from PurchaseOrderAttachment poa where poa.purchaseOrder.id=? order by poa.id";
        return this.getHibernateTemplate().find(sql,po.getId(),Hibernate.STRING);
    }

    public List getPurchaseOrderItemList(PurchaseOrder po) {
        String sql="from PurchaseOrderItem poi where poi.purchaseOrder.id=? order by poi.id";
        return this.getHibernateTemplate().find(sql,po.getId(),Hibernate.STRING);
   }

    public PurchaseOrderAttachment getPurchaseOrderAttachment(Integer id) {
        return (PurchaseOrderAttachment) this.getHibernateTemplate().get(PurchaseOrderAttachment.class,id);
    }
    
    public List getPurchaseOrderItemIdList(PurchaseOrder po) {
        String sql="select poi.id from PurchaseOrderItem poi where poi.purchaseOrder.id=?";
        return this.getHibernateTemplate().find(sql,po.getId(),Hibernate.STRING);
   }
    
    public List getPurchaseOrderAttachmentIdList(PurchaseOrder po) {
        String sql="select poa.id from PurchaseOrderAttachment poa where poa.purchaseOrder.id=?";
        return this.getHibernateTemplate().find(sql,po.getId(),Hibernate.STRING);
    }

    public List getPaymentScheduleDetailList(PurchaseOrder po) {
        String sql="from PaymentScheduleDetail psd where psd.purchaseOrder.id=? order by psd.date";
        return this.getHibernateTemplate().find(sql,po.getId(),Hibernate.STRING);
    }

    public PaymentScheduleDetail getPaymentScheduleDetail(Integer id) {
        return (PaymentScheduleDetail) this.getHibernateTemplate().get(PaymentScheduleDetail.class,id);
    }

    public List getPaymentScheduleDetailIdList(PurchaseOrder po) {
        String sql="select psd.id from PaymentScheduleDetail psd where psd.purchaseOrder.id=?";
        return this.getHibernateTemplate().find(sql,po.getId(),Hibernate.STRING);
    }

    public void deletePaymentScheduleDetails(PurchaseOrder po) {
        String sql="from PaymentScheduleDetail psd where psd.purchaseOrder.id=?";
        this.getHibernateTemplate().delete(sql,po.getId(),Hibernate.STRING);
    }

    public void updatePurchaseOrderAttachment(PurchaseOrderAttachment poa) {
        this.getHibernateTemplate().update(poa);
    }

    public void deletePurchaseOrder(PurchaseOrder po) {
        this.getHibernateTemplate().delete(po);
    }

    public void deletePurchaseOrderHisotries(PurchaseOrder po) {
        String sql="from PurchaseOrderHistoryItem pohi where pohi.purchaseOrderHistory.purchaseOrder.id=?";
        this.getHibernateTemplate().delete(sql,po.getId(),Hibernate.STRING);
        sql="from PurchaseOrderHistory poh where poh.purchaseOrder.id=?";
        this.getHibernateTemplate().delete(sql,po.getId(),Hibernate.STRING);
    }

    public void deletePurchaseOrderItem(Integer itemId) {
        String sql="from PurchaseOrderItem poi where poi.id=?";
        this.getHibernateTemplate().delete(sql,itemId,Hibernate.INTEGER);
    }

    public void deletePurchaseOrderAttachment(Integer attachmentId) {
        String sql="from PurchaseOrderAttachment poa where poa.id=?";
        this.getHibernateTemplate().delete(sql,attachmentId,Hibernate.INTEGER);
    }

    public void insertPaymentScheduleDetail(PaymentScheduleDetail psd) {
        this.getHibernateTemplate().save(psd);
    }

    public void updatePaymentScheduleDetail(PaymentScheduleDetail psd) {
        this.getHibernateTemplate().update(psd);
    }

    public void deletePaymentScheduleDetail(Integer psdId) {
        String sql="from PaymentScheduleDetail psd where psd.id=?";
        this.getHibernateTemplate().delete(sql,psdId,Hibernate.INTEGER);
    }

    public PurchaseOrderAttachment insertPurchaseOrderAttachment(final PurchaseOrderAttachment poa,final InputStream inputStream) {
        return (PurchaseOrderAttachment) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                session.save(poa);
                PurchaseOrderAttachmentContent content = 
                    (PurchaseOrderAttachmentContent) session.get(PurchaseOrderAttachmentContent.class, poa.getId());
                try {
                    Blob fileContent = Hibernate.createBlob(inputStream);
                    content.setFileContent(fileContent);
                    session.update(content);
                } catch (IOException e) {
                    throw new HibernateException(e);
                }
                return poa;
            }

        });
    }

    public InputStream getPurchaseOrderAttachmentContent(final Integer id) {
        return (InputStream) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                PurchaseOrderAttachmentContent poac =
                   (PurchaseOrderAttachmentContent) session.get(PurchaseOrderAttachmentContent.class, id);
                if (poac != null) {
                    Blob content = poac.getFileContent();
                    if (content != null)
                        try {
                            return preRead(content.getBinaryStream());
                        } catch (IOException e) {
                            throw new HibernateException(e);
                        }
                }
                return null;
            }

        });
    }

    public void insertPurchaseOrderApproveRequest(PurchaseOrderApproveRequest poar) {
        this.getHibernateTemplate().save(poar);
        
    }

    public List getPurchaseOrderApproveRequestList(PurchaseOrder po) {
        return getHibernateTemplate().find(
                "from PurchaseOrderApproveRequest poar where poar.approveRequestId = ? order by poar.seq",
                po.getApproveRequestId(), Hibernate.STRING);
    }

    public void deletePurchaseOrderAttachments(PurchaseOrder po) {
        String sql="from PurchaseOrderAttachment poa where poa.purchaseOrder.id=?";
        this.getHibernateTemplate().delete(sql,po.getId(),Hibernate.STRING);
    }

    public PurchaseOrder getPurchaseOrderByApproveRequestId(String approveRequestId) {
        Map condition = new HashMap();
        condition.put(PurchaseOrderQueryCondition.APPROVEREQUESTID_EQ, approveRequestId);
        List result = getPurchaseOrderList(condition, 0, -1, null, false);
        if (result.size() > 0)
            return (PurchaseOrder) result.get(0);
        else
            return null;
    }

    public void savePurchaseOrderHistory(PurchaseOrderHistory history) {
        getHibernateTemplate().saveOrUpdate(history);
    }

    public void savePurchaseOrderHistoryItem(PurchaseOrderHistoryItem historyItem) {
        getHibernateTemplate().saveOrUpdate(historyItem);
        
    }

    public List getRequestorListOfPurchaseOrder(PurchaseOrder po) {
        String sql="select distinct poi.purchaseRequestItem.purchaseRequest.requestor from PurchaseOrderItem poi where poi.purchaseOrder.id=?";
        return this.getHibernateTemplate().find(sql,po.getId(),Hibernate.STRING);
    }

    private final static String ITEM_SQL_COUNT = "select count(*) from PurchaseOrderItem poi";
    private final static String ITEM_SQL_COUNT2 = "select count(*) from PurchaseOrderItem poi join poi.purchaseOrder join poi.purchaseRequestItem.purchaseRequest";
    private final static String ITEM_SQL = "from PurchaseOrderItem poi";
    private final static String ITEM_SQL2 = "select poi from PurchaseOrderItem poi join poi.purchaseOrder join poi.purchaseRequestItem.purchaseRequest";

    private final static Object[][] ITEM_QUERY_CONDITIONS = {
        { PurchaseOrderItemQueryCondition.ID_EQ, "poi.id = ?", null },
        { PurchaseOrderItemQueryCondition.ITEMSPEC_LIKE, "poi.itemSpec like ?", new LikeConvert() },
        { PurchaseOrderItemQueryCondition.NOT_RECEIVE_ALL, "poi.quantity <> (poi.receivedQuantity + poi.cancelledQuantity + poi.returnedQuantity)", null },
        { PurchaseOrderItemQueryCondition.PO_STATUS_EQ, "poi.purchaseOrder.status = ?", null }, 
        { PurchaseOrderItemQueryCondition.PO_STATUS_IN2, "poi.purchaseOrder.status in (?, ?)", null }, 
        { PurchaseOrderItemQueryCondition.PR_ID_LIKE, "poi.purchaseRequestItem.purchaseRequest.id like ?", new LikeConvert() }, 
        { PurchaseOrderItemQueryCondition.PR_REQUESTOR_OR_PO_INSPECTOR_EQ, "(poi.purchaseRequestItem.purchaseRequest.requestor.id = ? or poi.purchaseOrder.inspector.id = ?)", null }, 
    };

    private final static Object[][] ITEM_QUERY_ORDERS = {
        { PurchaseOrderItemQueryOrder.ID, "poi.id" },
        { PurchaseOrderItemQueryOrder.ITEMSPEC, "poi.itemSpec" },
        { PurchaseOrderItemQueryOrder.PR_ID, "poi.purchaseRequestItem.purchaseRequest.id" },
    };

    public int getPurchaseOrderItemListCount(final Map conditions) {
        if (conditions.containsKey(PurchaseOrderItemQueryCondition.PR_REQUESTOR_OR_PO_INSPECTOR_EQ)) {
            return getListCount(conditions, ITEM_SQL_COUNT2, ITEM_QUERY_CONDITIONS);
        }
        return getListCount(conditions, ITEM_SQL_COUNT, ITEM_QUERY_CONDITIONS);
    }

    public List getPurchaseOrderItemList(final Map conditions, final int pageNo, final int pageSize, final PurchaseOrderItemQueryOrder order, final boolean descend) {
        if (conditions.containsKey(PurchaseOrderItemQueryCondition.PR_REQUESTOR_OR_PO_INSPECTOR_EQ)) {
            return getList(conditions, pageNo, pageSize, order, descend, ITEM_SQL2, ITEM_QUERY_CONDITIONS, ITEM_QUERY_ORDERS);
        }
        return getList(conditions, pageNo, pageSize, order, descend, ITEM_SQL, ITEM_QUERY_CONDITIONS, ITEM_QUERY_ORDERS);
    }

    public void deletePurchaseOrderApproveRequest(PurchaseOrder po) {
        String sql="from PurchaseOrderApproveRequest poar where poar.approveRequestId=?";
        this.getHibernateTemplate().delete(sql,po.getApproveRequestId(),Hibernate.STRING);

        
    }

    public void deletePurchaseOrderItemAttachments(PurchaseOrderItem poi) {
        String sql = "from PurchaseOrderItemAttachment poia where poia.purchaseOrderItem.id=?";
        this.getHibernateTemplate().delete(sql, poi.getId(), Hibernate.INTEGER);
    }

    public List getFinanceNotRespondedPurchaseOrderList(final Date time, final SiteMailReminder smr) {
        return (List) getHibernateTemplate().execute( new HibernateCallback() {
            
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query q=session.createQuery(
                        "from PurchaseOrder po where "+
                        "po.status=? and po.confirmer is null "+
                        "and po.approveDate<=? and po.emailTimes<? "+
                        "and (po.emailDate is null or po.emailDate<=?)"+
                        "and po.site.id=?");
                q.setParameter(0,PurchaseOrderStatus.APPROVED);
                q.setTimestamp(1,smr.getApproveDate(time));
                q.setInteger(2,smr.getMaxTime());
                q.setTimestamp(3,smr.getEmailDate(time));
                q.setParameter(4,smr.getSite().getId());
                return q.list();
            }
        });
    }

};
