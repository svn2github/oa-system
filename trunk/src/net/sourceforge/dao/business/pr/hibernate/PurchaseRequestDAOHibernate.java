/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.business.pr.hibernate;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.HibernateTemplate;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.business.pr.PurchaseRequestDAO;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.dao.convert.QuerySQLConvert;
import net.sourceforge.model.admin.SiteMailReminder;
import net.sourceforge.model.business.po.PurchaseOrderItem;
import net.sourceforge.model.business.po.PurchaseOrderItemAttachment;
import net.sourceforge.model.business.po.PurchaseOrderItemRecharge;
import net.sourceforge.model.business.pr.PurchaseRequest;
import net.sourceforge.model.business.pr.PurchaseRequestApproveRequest;
import net.sourceforge.model.business.pr.PurchaseRequestAttachment;
import net.sourceforge.model.business.pr.PurchaseRequestAttachmentContent;
import net.sourceforge.model.business.pr.PurchaseRequestHistory;
import net.sourceforge.model.business.pr.PurchaseRequestHistoryItem;
import net.sourceforge.model.business.pr.PurchaseRequestItem;
import net.sourceforge.model.business.pr.PurchaseRequestItemAttachment;
import net.sourceforge.model.business.pr.PurchaseRequestItemAttachmentContent;
import net.sourceforge.model.business.pr.PurchaseRequestItemRecharge;
import net.sourceforge.model.business.pr.PurchaseRequestPurchaser;
import net.sourceforge.model.business.pr.query.PurchaseRequestQueryCondition;
import net.sourceforge.model.business.pr.query.PurchaseRequestQueryOrder;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.model.metadata.PurchaseRequestStatus;
import com.shcnc.hibernate.CompositeQuery;

/**
 * implement for PurchaseRequestDAO
 * 
 * @author shilei
 * @version 1.0 (Dec 7, 2005)
 */
public class PurchaseRequestDAOHibernate extends BaseDAOHibernate implements PurchaseRequestDAO {
    private Log log = LogFactory.getLog(PurchaseRequestDAOHibernate.class);

    public PurchaseRequest getPurchaseRequest(String id) {
        if (id == null) {
            if (log.isDebugEnabled())
                log.debug("try to get PurchaseRequest with null id");
            return null;
        }
        return (PurchaseRequest) getHibernateTemplate().get(PurchaseRequest.class, id);
    }

    public PurchaseRequest updatePurchaseRequest(PurchaseRequest purchaseRequest) {
        getHibernateTemplate().update(purchaseRequest);
        return purchaseRequest;
    }

    public PurchaseRequest insertPurchaseRequest(PurchaseRequest purchaseRequest) {
        getHibernateTemplate().save(purchaseRequest);
        return purchaseRequest;
    }

    private final static String SQL_COUNT = "select count(*) from PurchaseRequest purchaseRequest";

    private final static String SQL = "from PurchaseRequest purchaseRequest";

    private final static Object[][] QUERY_CONDITIONS = {
        { PurchaseRequestQueryCondition.STATUS_IN_2,
            "purchaseRequest.status in(?,?)", null },
    { PurchaseRequestQueryCondition.CAN_PURCHASED_BY,
       "( purchaseRequest.purchaser.id=? or "+
           "(purchaseRequest.purchaser is null and " +
               "purchaseRequest.id in(select pp.purchaseRequest.id from PurchaseRequestPurchaser pp where pp.purchaser.id=?) ))", null },
    /* id */
    { PurchaseRequestQueryCondition.ID_EQ, "purchaseRequest.id = ?", null },
    { PurchaseRequestQueryCondition.ID_LIKE, "purchaseRequest.id like ?", new LikeConvert() },

    /* keyPropertyList */

    /* kmtoIdList */

    /* mtoIdList */
    { PurchaseRequestQueryCondition.PURCHASESUBCATEGORY_ID_EQ, "purchaseRequest.purchaseSubCategory.id = ?", null },
            { PurchaseRequestQueryCondition.DEPARTMENT_ID_EQ, "purchaseRequest.department.id = ?", null },
            { PurchaseRequestQueryCondition.DEPARTMENT_ID_IN, "purchaseRequest.department.id in ", new QuerySQLConvert() {
                public Object convert(StringBuffer sql, Object parameter) { 
                    if (parameter != null && parameter instanceof Object[]) {
                        Object[] finalParameter = (Object[])parameter;
                        if (finalParameter.length > 0) {
                            sql.append("(?");
                            for (int i=1; i < finalParameter.length;i++) {
                                sql.append(",?");
                            }
                            sql.append(")");
                        } else {
                            return finalParameter;
                        }
                    } else {
                        return parameter;
                    }
                    return parameter;
                }
            }},                      
            { PurchaseRequestQueryCondition.SITE_ID_EQ, "purchaseRequest.department.site.id = ?", null },
            { PurchaseRequestQueryCondition.CAPEX_ID_EQ, "purchaseRequest.capex.id = ?", null },
            { PurchaseRequestQueryCondition.YEARLYBUDGET_ID_EQ, "purchaseRequest.yearlyBudget.id = ?", null },
            { PurchaseRequestQueryCondition.CURRENCY_CODE_EQ, "purchaseRequest.currency.code = ?", null },
            { PurchaseRequestQueryCondition.REQUESTOR_ID_EQ, "purchaseRequest.requestor.id = ?", null },
            { PurchaseRequestQueryCondition.REQUESTOR_ID_NOT_EQ, "purchaseRequest.requestor.id <> ?", null },
            { PurchaseRequestQueryCondition.CREATOR_ID_EQ, "purchaseRequest.creator.id = ?", null },
            { PurchaseRequestQueryCondition.PURCHASER_ID_EQ, "purchaseRequest.purchaser.id = ?", null },

            /* property */
            { PurchaseRequestQueryCondition.TITLE_LIKE, "purchaseRequest.title like ?", new LikeConvert() },
            { PurchaseRequestQueryCondition.DESCRIPTION_LIKE, "purchaseRequest.description like ?", new LikeConvert() },
            { PurchaseRequestQueryCondition.STATUS_EQ, "purchaseRequest.status = ?", null },
            { PurchaseRequestQueryCondition.REQUESTDATE_EQ, "purchaseRequest.requestDate = ?", null },
            { PurchaseRequestQueryCondition.CREATEDATE_EQ, "purchaseRequest.createDate = ?", null },
            { PurchaseRequestQueryCondition.APPROVEREQUESTID_EQ, "purchaseRequest.approveRequestId = ?", null },
            { PurchaseRequestQueryCondition.REQUESTOR_NAME_LIKE, "purchaseRequest.requestor.name like ?",new LikeConvert() },
            
            { PurchaseRequestQueryCondition.CREATEDATE_GE, "purchaseRequest.createDate >= ?", null },
            { PurchaseRequestQueryCondition.CREATEDATE_LE, "purchaseRequest.createDate <= ?", null },
            { PurchaseRequestQueryCondition.CREATEDATE_LT, "purchaseRequest.createDate < ?", null },
            { PurchaseRequestQueryCondition.PURCHASECATEGORY_ID_EQ, "purchaseRequest.purchaseSubCategory.purchaseCategory.id = ?", null },
            { PurchaseRequestQueryCondition.AMOUNT_GE, "purchaseRequest.amount >= ?", null },
            { PurchaseRequestQueryCondition.AMOUNT_LE, "purchaseRequest.amount <= ?", null },
            { PurchaseRequestQueryCondition.ISDELEGATE_EQ, "purchaseRequest.isDelegate = ?", null },
            { PurchaseRequestQueryCondition.APPROVEDATE_GE, "purchaseRequest.approveDate >= ?", null },
            { PurchaseRequestQueryCondition.APPROVEDATE_LE, "purchaseRequest.approveDate <= ?", null },
        };
            

    private final static Object[][] QUERY_ORDERS = {
    /* id */
        { PurchaseRequestQueryOrder.ID, "purchaseRequest.id" },

    /* property */
        { PurchaseRequestQueryOrder.TITLE, "purchaseRequest.title" }, 
        { PurchaseRequestQueryOrder.DESCRIPTION, "purchaseRequest.description" },
        { PurchaseRequestQueryOrder.STATUS, "purchaseRequest.status" }, 
        { PurchaseRequestQueryOrder.REQUESTDATE, "purchaseRequest.requestDate" },
        { PurchaseRequestQueryOrder.CREATEDATE, "purchaseRequest.createDate" },
        { PurchaseRequestQueryOrder.APPROVEREQUESTID, "purchaseRequest.approveRequestId" },
        { PurchaseRequestQueryOrder.REQUESTOR_NAME, "purchaseRequest.requestor.name" }, 
        { PurchaseRequestQueryOrder.AMOUNT, "purchaseRequest.amount" },
        { PurchaseRequestQueryOrder.PURCHASER_NAME, "purchaseRequest.purchaser.name" }, 
        { PurchaseRequestQueryOrder.PURCHASER_CATEGORY, "purchaseRequest.purchaseSubCategory.purchaseCategory.id" },
    };

    public int getPurchaseRequestListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getPurchaseRequestList(final Map conditions, final int pageNo, final int pageSize, final PurchaseRequestQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

    public String getMaxPurchaseRequestIdBeginWith(final String prefix) {

        List l = (List) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String sql = "select max(pr.id) from PurchaseRequest pr";
                CompositeQuery query = new CompositeQuery(sql, "", session);
                query.createQueryCondition("pr.id like ?").appendParameter(prefix + "%");
                return query.list();
            }

        });

        if (l.isEmpty())
            return null;
        else
            return (String) l.get(0);
    }

    public List getPurchaseRequestItemList(PurchaseRequest pr) {
        String sql = "from PurchaseRequestItem pri where pri.purchaseRequest.id = ? order by pri.id";
        List priList = this.getHibernateTemplate().find(sql, pr.getId(), Hibernate.STRING);
        return priList;
    }

    public List getPurchaseRequestItemListWithDetails(PurchaseRequest pr) {
        List priList = this.getPurchaseRequestItemList(pr);
        for (Iterator iter = priList.iterator(); iter.hasNext();) {
            PurchaseRequestItem pri = (PurchaseRequestItem) iter.next();
            pri.setAttachments(new ArrayList());
            pri.setRecharges(new ArrayList());
        }

        String sql = "from PurchaseRequestItemRecharge prir where prir.purchaseRequestItem.purchaseRequest.id = ? order by prir.id";
        List prirList = getHibernateTemplate().find(sql, pr.getId(), Hibernate.STRING);
        for (Iterator iter = prirList.iterator(); iter.hasNext();) {
            PurchaseRequestItemRecharge prir = (PurchaseRequestItemRecharge) iter.next();
            PurchaseRequestItem pri = prir.getPurchaseRequestItem();
            pri.getRecharges().add(prir);
        }

        sql = "from PurchaseRequestItemAttachment pria where pria.purchaseRequestItem.purchaseRequest.id = ? order by pria.id";
        List priaList = getHibernateTemplate().find(sql, pr.getId(), Hibernate.STRING);
        for (Iterator iter = priaList.iterator(); iter.hasNext();) {
            PurchaseRequestItemAttachment pria = (PurchaseRequestItemAttachment) iter.next();
            PurchaseRequestItem pri = pria.getPurchaseRequestItem();
            pri.getAttachments().add(pria);
        }

        return priList;
    }

    public List getPurchaseRequestAttachmentList(PurchaseRequest pr) {
        return getHibernateTemplate().find("from PurchaseRequestAttachment pra where pra.purchaseRequest.id = ?", pr.getId(), Hibernate.STRING);
    }

    public List getPurchaseRequestApproveRequestList(PurchaseRequest pr) {
        return getHibernateTemplate().find("from PurchaseRequestApproveRequest prar where prar.approveRequestId = ? order by prar.seq",
                pr.getApproveRequestId(), Hibernate.STRING);
    }

    public void deletePurchaseRequestHistories(PurchaseRequest pr) {
        HibernateTemplate template = getHibernateTemplate();
        String id = pr.getId();
        template.delete("from PurchaseRequestHistoryItem prhi where prhi.purchaseRequestHistory.purchaseRequest.id = ?", id, Hibernate.STRING);
        template.delete("from PurchaseRequestHistory prh where prh.purchaseRequest.id = ?", id, Hibernate.STRING);
    }

    public void deletePurchaseRequest(PurchaseRequest pr) {
        this.deletePurchaseRequestHistories(pr);
        this.deletePurchaseRequestItems(pr);

        String id = pr.getId();

        HibernateTemplate template = getHibernateTemplate();
        template
                .delete(
                        "from PurchaseRequestApproveRequest prar where prar.approveRequestId in (select prh.approveRequestId from PurchaseRequestHistory prh where prh.purchaseRequest.id = ?)",
                        id, Hibernate.STRING);

        template.delete("from PurchaseRequestAttachment pra where pra.purchaseRequest.id = ?", id, Hibernate.STRING);

        template.delete("from PurchaseRequestPurchaser prp where prp.purchaseRequest.id = ?", id, Hibernate.STRING);

        template.delete("from PurchaseRequest pr where pr.id = ?", id, Hibernate.STRING);
    }

    public void deletePurchaseRequestItems(PurchaseRequest pr) {
        HibernateTemplate template = getHibernateTemplate();
        String id = pr.getId();
        template.delete("from PurchaseRequestItemAttachment pria where pria.purchaseRequestItem.purchaseRequest.id = ?", id, Hibernate.STRING);
        template.delete("from PurchaseRequestItemRecharge prir where prir.purchaseRequestItem.purchaseRequest.id = ?", id, Hibernate.STRING);
        template.delete("from PurchaseRequestItem pri where pri.purchaseRequest.id = ?", id, Hibernate.STRING);
    }

    public void insertPurchaseRequestItem(PurchaseRequestItem pri) {
        this.getHibernateTemplate().save(pri);
    }

    public void insertPurchaseRequestItemRecharge(PurchaseRequestItemRecharge prir) {
        this.getHibernateTemplate().save(prir);
    }

    public void updatePurchaseRequestItem(PurchaseRequestItem pri) {
        this.getHibernateTemplate().update(pri);
    }

    public void deletePurchaseRequestItemRecharges(PurchaseRequestItem pri) {
        this.getHibernateTemplate().delete("from PurchaseRequestItemRecharge prir where prir.purchaseRequestItem.id = ?", pri.getId(), Hibernate.INTEGER);
    }

    public void deletePurchaseRequestItemAttachments(PurchaseRequestItem pri) {
        this.getHibernateTemplate().delete("from PurchaseRequestItemAttachment pria where pria.purchaseRequestItem.id = ?", pri.getId(), Hibernate.INTEGER);
    }

    public List getPurchaseRequestItemIdList(PurchaseRequest pr) {
        String sql = "select pri.id from PurchaseRequestItem pri where pri.purchaseRequest.id=?";
        return this.getHibernateTemplate().find(sql, pr.getId(), Hibernate.STRING);
    }

    public void deletePurchaseRequestItem(Integer itemId) {
        String sql = "from PurchaseRequestItemRecharge prir where prir.purchaseRequestItem.id=?";
        this.getHibernateTemplate().delete(sql, itemId, Hibernate.INTEGER);

        sql = "from PurchaseRequestItemAttachment pria where pria.purchaseRequestItem.id=?";
        this.getHibernateTemplate().delete(sql, itemId, Hibernate.INTEGER);

        sql = "from PurchaseRequestItem pri where pri.id=?";
        this.getHibernateTemplate().delete(sql, itemId, Hibernate.INTEGER);
    }

    public void updatePurchaseRequestItemAttachment(PurchaseRequestItemAttachment pria) {
        this.getHibernateTemplate().update(pria);
    }

    public void updatePurchaseRequestAttachment(PurchaseRequestAttachment pria) {
        this.getHibernateTemplate().update(pria);
    }

    public PurchaseRequestItemAttachment getPurchaseRequestItemAttachment(Integer id) {
        return (PurchaseRequestItemAttachment) this.getHibernateTemplate().get(PurchaseRequestItemAttachment.class, id);
    }

    public PurchaseRequestAttachment getPurchaseRequestAttachment(Integer id) {
        return (PurchaseRequestAttachment) this.getHibernateTemplate().get(PurchaseRequestAttachment.class, id);
    }

    public InputStream getPurchaseRequestItemAttachmentContent(final Integer id) {
        return (InputStream) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                PurchaseRequestItemAttachmentContent priac = (PurchaseRequestItemAttachmentContent) session.get(PurchaseRequestItemAttachmentContent.class, id);
                if (priac != null) {
                    Blob content = priac.getFileContent();
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

    public InputStream getPurchaseRequestAttachmentContent(final Integer id) {
        return (InputStream) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                PurchaseRequestAttachmentContent priac = (PurchaseRequestAttachmentContent) session.get(PurchaseRequestAttachmentContent.class, id);
                if (priac != null) {
                    Blob content = priac.getFileContent();
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

    public PurchaseRequestItemAttachment insertPurchaseRequestItemAttachment(final PurchaseRequestItemAttachment pria, final InputStream inputStream) {
        return (PurchaseRequestItemAttachment) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                session.saveOrUpdate(pria);
                PurchaseRequestItemAttachmentContent content = (PurchaseRequestItemAttachmentContent) session.get(PurchaseRequestItemAttachmentContent.class,
                        pria.getId());
                try {
                    Blob fileContent = Hibernate.createBlob(inputStream);
                    content.setFileContent(fileContent);
                    session.update(content);
                } catch (IOException e) {
                    throw new HibernateException(e);
                }
                return pria;
            }

        });
    }

    public PurchaseRequestAttachment insertPurchaseRequestAttachment(final PurchaseRequestAttachment pra, final InputStream inputStream) {
        return (PurchaseRequestAttachment) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                session.saveOrUpdate(pra);
                PurchaseRequestAttachmentContent content = (PurchaseRequestAttachmentContent) session.get(PurchaseRequestAttachmentContent.class, pra.getId());
                try {
                    Blob fileContent = Hibernate.createBlob(inputStream);
                    content.setFileContent(fileContent);
                    session.update(content);
                } catch (IOException e) {
                    throw new HibernateException(e);
                }
                return pra;
            }

        });
    }

    public List getPurchaseRequestItemAttachmentIdList(PurchaseRequestItem pri) {
        String sql = "select pria.id from PurchaseRequestItemAttachment pria where pria.purchaseRequestItem.id=?";
        return this.getHibernateTemplate().find(sql, pri.getId(), Hibernate.INTEGER);
    }

    public void deletePurchaseRequestItemAttachment(Integer itemAttachmentId) {
        String sql = "from PurchaseRequestItemAttachment pria where pria.id=?";
        this.getHibernateTemplate().delete(sql, itemAttachmentId, Hibernate.INTEGER);

    }

    public List getPurchaseRequestAttachmentIdList(PurchaseRequest pr) {
        String sql = "select pra.id from PurchaseRequestAttachment pra where pra.purchaseRequest.id=?";
        return this.getHibernateTemplate().find(sql, pr.getId(), Hibernate.STRING);
    }

    public void deletePurchaseRequestAttachment(Integer id) {
        String sql = "from PurchaseRequestAttachment pra where pra.id=?";
        this.getHibernateTemplate().delete(sql, id, Hibernate.INTEGER);
    }

    public void insertPurchaseRequestApproveRequest(PurchaseRequestApproveRequest prar) {
        this.getHibernateTemplate().save(prar);
    }

    public PurchaseRequest getPurchaseRequestByApproveRequestId(String approveRequestId) {
        Map condition = new HashMap();
        condition.put(PurchaseRequestQueryCondition.APPROVEREQUESTID_EQ, approveRequestId);
        List result = getPurchaseRequestList(condition, 0, -1, null, false);
        if (result.size() > 0)
            return (PurchaseRequest) result.get(0);
        else
            return null;
    }

    public List getPurchaseRequestItemAttachments(PurchaseRequestItem pri) {
        String sql = "from PurchaseRequestItemAttachment pria where pria.purchaseRequestItem.id=?";
        return this.getHibernateTemplate().find(sql, pri.getId(), Hibernate.INTEGER);
    }

    public List getPurchaseRequestItemRecharges(PurchaseRequestItem pri) {
        String sql = "from PurchaseRequestItemRecharge prir where prir.purchaseRequestItem.id=?";
        return this.getHibernateTemplate().find(sql, pri.getId(), Hibernate.INTEGER);
    }

    public PurchaseRequestItem getPurchaseRequestItem(Integer id) {
        PurchaseRequestItem pri = (PurchaseRequestItem) this.getHibernateTemplate().get(PurchaseRequestItem.class, id);

        return pri;
    }

    public void savePurchaseRequestHistory(PurchaseRequestHistory history) {
        getHibernateTemplate().saveOrUpdate(history);
    }

    public void savePurchaseRequestHistoryItem(PurchaseRequestHistoryItem historyItem) {
        getHibernateTemplate().saveOrUpdate(historyItem);
    }

    public List getPurchaseOrderItemList(PurchaseRequest pr) {
        String sql = "from PurchaseOrderItem poi where poi.purchaseRequestItem.purchaseRequest.id = ? order by poi.id";
        return this.getHibernateTemplate().find(sql, pr.getId(), Hibernate.STRING);
    }

    public List getPurchaseOrderItemIdList(PurchaseRequest pr) {
        String sql = "select poi.id from PurchaseOrderItem poi where poi.purchaseRequestItem.purchaseRequest.id=?";
        return this.getHibernateTemplate().find(sql, pr.getId(), Hibernate.STRING);
    }

    public void savePurchaseRequestPurchaser(PurchaseRequestPurchaser purchaser) {
        getHibernateTemplate().saveOrUpdate(purchaser);
    }

    public List getPurchaseOrderItemListWithDetails(PurchaseRequest pr) {
        List poList = this.getPurchaseOrderItemList(pr);
        for (Iterator iter = poList.iterator(); iter.hasNext();) {
            PurchaseOrderItem poi = (PurchaseOrderItem) iter.next();
            poi.setRecharges(new ArrayList());
            poi.setAttachments(new ArrayList());
        }
        String sql = "from PurchaseOrderItemAttachment poia where poia.purchaseOrderItem.purchaseRequestItem.purchaseRequest.id=? order by poia.id";
        List poiaList = this.getHibernateTemplate().find(sql, pr.getId(), Hibernate.STRING);
        for (Iterator iter = poiaList.iterator(); iter.hasNext();) {
            PurchaseOrderItemAttachment poia = (PurchaseOrderItemAttachment) iter.next();
            poia.getPurchaseOrderItem().getAttachments().add(poia);
        }

        sql = "from PurchaseOrderItemRecharge poir where poir.purchaseOrderItem.purchaseRequestItem.purchaseRequest.id=? order by poir.id";
        List poirList = this.getHibernateTemplate().find(sql, pr.getId(), Hibernate.STRING);
        for (Iterator iter = poirList.iterator(); iter.hasNext();) {
            PurchaseOrderItemRecharge poir = (PurchaseOrderItemRecharge) iter.next();
            poir.getPurchaseOrderItem().getRecharges().add(poir);
        }
        return poList;
    }

    public PurchaseRequestItem getPurchaseRequestItemWithDetails(Integer id) {
        PurchaseRequestItem pri = this.getPurchaseRequestItem(id);
        pri.setAttachments(this.getPurchaseRequestItemAttachments(pri));
        pri.setRecharges(this.getPurchaseRequestItemRecharges(pri));
        return pri;
    }

    public boolean isPurchaseRequestItemInUse(PurchaseRequestItem pri) {
        String sql="select poi.id from PurchaseOrderItem poi where poi.purchaseRequestItem.id=?";
        return this.getHibernateTemplate().iterate(sql,pri.getId(),Hibernate.INTEGER).hasNext();
    }

    public void deletePurchaseRequestApproveRequest(PurchaseRequest pr) {
        String sql="from PurchaseRequestApproveRequest prar where prar.approveRequestId=?";
        this.getHibernateTemplate().delete(sql,pr.getApproveRequestId(),Hibernate.STRING);
        
    }

    public boolean purchaseRequestHasPOItem(PurchaseRequest pr) {
        String sql="select poi.id from PurchaseOrderItem poi where poi.purchaseRequestItem.purchaseRequest.id=?";
        return this.getHibernateTemplate().iterate(sql,pr.getId(),Hibernate.STRING).hasNext();
    }

    public void deletePurchaseRequestPurchaserByPurchaseRequest(PurchaseRequest pr) {
        String sql="from PurchaseRequestPurchaser prp where prp.purchaseRequest.id=?";
        this.getHibernateTemplate().delete(sql,pr.getId(),Hibernate.STRING);
    }

    public List getPurchaserNotRespondedPurchaseRequestList(SiteMailReminder smr) {
        // TODO Auto-generated method stub
        return null;
    }

    public List getPurchaserNotRespondedPurchaseRequestList(final Date time, final SiteMailReminder smr) {
        return (List) getHibernateTemplate().execute( new HibernateCallback() {
            
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query q=session.createQuery(
                        "from PurchaseRequest pr where "+
                        "pr.status=? and pr.purchaser is null "+
                        "and pr.approveDate<=? and pr.emailTimes<? "+
                        "and (pr.emailDate is null or pr.emailDate<=?)"+
                        "and pr.department.site.id=?");
                q.setParameter(0,PurchaseRequestStatus.APPROVED);
                q.setTimestamp(1,smr.getApproveDate(time));
                q.setInteger(2,smr.getMaxTime());
                q.setTimestamp(3,smr.getEmailDate(time));
                q.setParameter(4,smr.getSite().getId());
                return q.list();
            }
        });
    }

    public List getPurchaseRequestPurchaserList(final PurchaseRequest pr) {
        return (List) getHibernateTemplate().execute( new HibernateCallback() {
            
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query q=session.createQuery(
                        "select prp.purchaser from PurchaseRequestPurchaser prp where prp.purchaseRequest.id=? and prp.purchaser.enabled=?");
                q.setString(0,pr.getId());
                q.setParameter(1,EnabledDisabled.ENABLED.getEnumCode());
                return q.list();
            }
        });

    }

}
