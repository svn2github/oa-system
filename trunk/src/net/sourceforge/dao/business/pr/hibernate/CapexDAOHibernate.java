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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.HibernateTemplate;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.business.pr.CapexDAO;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.dao.convert.QuerySQLConvert;
import net.sourceforge.model.admin.Department;
import net.sourceforge.model.admin.PurchaseSubCategory;
import net.sourceforge.model.business.pr.Capex;
import net.sourceforge.model.business.pr.CapexDepartment;
import net.sourceforge.model.business.pr.CapexRequest;
import net.sourceforge.model.business.pr.CapexRequestApproveRequest;
import net.sourceforge.model.business.pr.CapexRequestAttachment;
import net.sourceforge.model.business.pr.CapexRequestAttachmentContent;
import net.sourceforge.model.business.pr.CapexRequestHistory;
import net.sourceforge.model.business.pr.CapexRequestHistoryItem;
import net.sourceforge.model.business.pr.CapexRequestItem;
import net.sourceforge.model.business.pr.query.CapexRequestQueryCondition;
import net.sourceforge.model.business.pr.query.CapexRequestQueryOrder;
import net.sourceforge.model.metadata.CapexRequestStatus;
import com.shcnc.hibernate.CompositeQuery;
import com.shcnc.hibernate.QueryCondition;

public class CapexDAOHibernate extends BaseDAOHibernate implements CapexDAO {
    private Log log = LogFactory.getLog(CapexDAOHibernate.class);

    public Capex getCapex(String id) {
        if (id == null) {
            if (log.isDebugEnabled())
                log.debug("try to get Capex with null id");
            return null;
        }
        return (Capex) getHibernateTemplate().get(Capex.class, id);
    }

    public Capex insertCapex(Capex capex) {
        getHibernateTemplate().save(capex);
        return capex;
    }

    public Capex updateCapex(Capex capex) {
        String id = capex.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a capex with null id");
        }
        Capex oldCapex = getCapex(id);
        if (oldCapex != null) {
            try {
                PropertyUtils.copyProperties(oldCapex, capex);
            } catch (Exception e) {
                throw new RuntimeException("copy error£º" + e);
            }
            getHibernateTemplate().update(oldCapex);
            return oldCapex;
        } else {
            throw new RuntimeException("capex not found");
        }
    }

    public CapexRequest getCapexRequest(Integer id) {
        if (id == null) {
            if (log.isDebugEnabled())
                log.debug("try to get CapexRequest with null id");
            return null;
        }
        return (CapexRequest) getHibernateTemplate().get(CapexRequest.class, id);
    }

    public CapexRequest saveCapexRequest(CapexRequest capexRequest) {
        getHibernateTemplate().saveOrUpdate(capexRequest);
        return capexRequest;
    }

    private final static String SQL_COUNT = "select count(*) from CapexRequest cr";

    private final static String SQL = "from CapexRequest cr";

    private final static Object[][] QUERY_CONDITIONS = {
    /* id */
    { CapexRequestQueryCondition.ID_EQ, "cr.id = ?", null },

    /* keyPropertyList */

    /* kmtoIdList */

    /* mtoIdList */
    { CapexRequestQueryCondition.CAPEX_ID_LIKE, "cr.capex.id like ?", new LikeConvert() }, 
    { CapexRequestQueryCondition.CAPEX_ID_EQ, "cr.capex.id = ?", null },
            { CapexRequestQueryCondition.REQUESTOR_ID_EQ, "cr.requestor.id = ?", null },

            /* property */
            { CapexRequestQueryCondition.TYPE_EQ, "cr.type = ?", null }, { CapexRequestQueryCondition.TITLE_LIKE, "cr.title like ?", new LikeConvert() },
            { CapexRequestQueryCondition.DESCRIPTION_LIKE, "cr.description like ?", new LikeConvert() },
            { CapexRequestQueryCondition.STATUS_EQ, "cr.status = ?", null }, { CapexRequestQueryCondition.STATUS_NE, "cr.status <> ?", null },
            { CapexRequestQueryCondition.AMOUNT_EQ, "cr.amount = ?", null }, { CapexRequestQueryCondition.REQUESTDATE_EQ, "cr.requestDate = ?", null },
            { CapexRequestQueryCondition.REQUESTDATE_EQ, "cr.requestDate = ?", null },
            { CapexRequestQueryCondition.REQUESTDATE_GE, "cr.requestDate >= ?", null },
            { CapexRequestQueryCondition.REQUESTDATE_LE, "cr.requestDate <= ?", null },
            { CapexRequestQueryCondition.REQUESTDATE_LT, "cr.requestDate < ?", null },
            { CapexRequestQueryCondition.CREATEDATE_EQ, "cr.createDate = ?", null },
            { CapexRequestQueryCondition.CREATEDATE_GE, "cr.createDate >= ?", null },
            { CapexRequestQueryCondition.CREATEDATE_LE, "cr.createDate <= ?", null },
            { CapexRequestQueryCondition.CREATEDATE_LT, "cr.createDate < ?", null },
            { CapexRequestQueryCondition.APPROVEDATE_EQ, "cr.approveDate = ?", null },
            { CapexRequestQueryCondition.APPROVEDATE_GE, "cr.approveDate >= ?", null },
            { CapexRequestQueryCondition.APPROVEDATE_LE, "cr.approveDate <= ?", null },
            { CapexRequestQueryCondition.APPROVEDATE_LT, "cr.approveDate < ?", null },
            { CapexRequestQueryCondition.APPROVEREQUESTID_EQ, "cr.approveRequestId = ?", null },
            { CapexRequestQueryCondition.CAPEX_SITE_ID_EQ, "cr.capex.site.id = ?", null },
            { CapexRequestQueryCondition.CAPEX_DEPARTMENT_ID_EQ, "cr.capex.id in(select cd.capex.id from CapexDepartment cd where cd.department.id=?)", null },
            { CapexRequestQueryCondition.CAPEX_DEPARTMENT_ID_IN, "cr.capex.id in(select cd.capex.id from CapexDepartment cd where cd.department.id in  ", new QuerySQLConvert() {
                public Object convert(StringBuffer sql, Object parameter) { 
                    if (parameter != null && parameter instanceof Object[]) {
                        Object[] finalParameter = (Object[])parameter;
                        if (finalParameter.length > 0) {
                            sql.append("(?");
                            for (int i=1; i < finalParameter.length;i++) {
                                sql.append(",?");
                            }
                            sql.append("))");
                        } else {
                            return finalParameter;
                        }
                    } else {
                        return parameter;
                    }
                    return parameter;
                }
            }},            
            { CapexRequestQueryCondition.IS_LAST_APPROVED, "cr.capex.lastApprovedRequest.id = cr.id", null },
            { CapexRequestQueryCondition.CAPEX_YEAR_EQ, "cr.capex.year = ?", null },
            { CapexRequestQueryCondition.AMOUNT_GE, "cr.amount >= ?", null },
            { CapexRequestQueryCondition.AMOUNT_LE, "cr.amount <= ?", null },
            { CapexRequestQueryCondition.CAPEX_REMAINAMOUNT_GE, "cr.amount - cr.capex.actualAmount >= ?", null },
            { CapexRequestQueryCondition.CAPEX_REMAINAMOUNT_LE, "cr.amount - cr.capex.actualAmount <= ?", null },

    };

    private final static Object[][] QUERY_ORDERS = { { CapexRequestQueryOrder.CAPEX_ID, "cr.capex.id" },
            { CapexRequestQueryOrder.CAPEX_YEARLYBUDGET_ID, "cr.capex.yearlyBudget.id" }, { CapexRequestQueryOrder.TYPE, "cr.type" },
            { CapexRequestQueryOrder.TITLE, "cr.title" }, { CapexRequestQueryOrder.DESCRIPTION, "cr.description" },
            { CapexRequestQueryOrder.STATUS, "cr.status" }, { CapexRequestQueryOrder.AMOUNT, "cr.amount" },
            { CapexRequestQueryOrder.REQUESTDATE, "cr.requestDate" }, };

    public int getCapexRequestListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getCapexRequestList(final Map conditions, final int pageNo, final int pageSize, final CapexRequestQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }

    public String getLastCapexNo(String prefix) {
        String result = (String) getHibernateTemplate().find("select max(c.id) from Capex c where c.id like ?", prefix + "_____", Hibernate.STRING).get(0);
        if (result == null) {
            return prefix + "00000";
        }
        return result;
    }

    public List getCapexDepartmentListForCapex(Capex capex) {
        return getHibernateTemplate().find("from CapexDepartment cd where cd.capex.id = ?", capex.getId(), Hibernate.STRING);
    }

    public CapexDepartment saveCapexDepartment(CapexDepartment capexDepartment) {
        getHibernateTemplate().save(capexDepartment);
        return capexDepartment;
    }

    public List getCapexRequestItemListForCapex(CapexRequest cr) {
        return getHibernateTemplate().find("from CapexRequestItem cri where cri.capexRequest.id = ?", cr.getId(), Hibernate.INTEGER);
    }

    public List getCapexRequestAttachmentListForCapexRequest(CapexRequest cr) {
        return getHibernateTemplate().find("from CapexRequestAttachment cra where cra.capexRequest.id = ?", cr.getId(), Hibernate.INTEGER);
    }

    public List getCapexRequestApproveRequestListForCapexRequest(CapexRequest cr) {
        return getHibernateTemplate().find("from CapexRequestApproveRequest crar where crar.approveRequestId = ? order by crar.seq", cr.getApproveRequestId(),
                Hibernate.STRING);
    }

    public CapexRequestAttachment getCapexRequestAttachment(Integer id) {
        return (CapexRequestAttachment) getHibernateTemplate().get(CapexRequestAttachment.class, id);
    }

    public InputStream getCapexRequestAttachmentContent(final Integer id) {
        return (InputStream) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                CapexRequestAttachmentContent crac = (CapexRequestAttachmentContent) session.get(CapexRequestAttachmentContent.class, id);
                if (crac != null) {
                    Blob content = crac.getFileContent();
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

    public CapexRequestAttachment saveCapexRequestAttachment(final CapexRequestAttachment capexRequestAttachment, final InputStream inputStream) {
        return (CapexRequestAttachment) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                session.saveOrUpdate(capexRequestAttachment);
                CapexRequestAttachmentContent content = (CapexRequestAttachmentContent) session.get(CapexRequestAttachmentContent.class, capexRequestAttachment
                        .getId());
                try {
                    Blob fileContent = Hibernate.createBlob(inputStream);
                    content.setFileContent(fileContent);
                    session.update(content);
                } catch (IOException e) {
                    throw new HibernateException(e);
                }
                return capexRequestAttachment;
            }

        });
    }

    public void deleteCapexRequestAttachment(CapexRequestAttachment capexRequestAttachment) {
        getHibernateTemplate().delete(capexRequestAttachment);
    }

    public CapexRequestItem saveCapexRequestItem(CapexRequestItem item) {
        getHibernateTemplate().saveOrUpdate(item);
        return item;
    }

    public void deleteCapexRequestItem(CapexRequestItem item) {
        getHibernateTemplate().delete(item);
    }

    public CapexRequestApproveRequest saveCapexRequestApproveRequest(CapexRequestApproveRequest approveRequest) {
        getHibernateTemplate().save(approveRequest);
        return approveRequest;
    }

    public CapexRequest getCapexRequestByApproveRequestId(String approveRequestId) {
        Map condition = new HashMap();
        condition.put(CapexRequestQueryCondition.APPROVEREQUESTID_EQ, approveRequestId);
        List result = getCapexRequestList(condition, 0, -1, null, false);
        if (result.size() > 0)
            return (CapexRequest) result.get(0);
        else
            return null;
    }

    public List getRequestApprovedCapexList(final PurchaseSubCategory psc, final Department department) {
        return (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String s = "from Capex capex";
                CompositeQuery q = new CompositeQuery(s, "capex.id", session);
                QueryCondition qc = q
                        .createQueryCondition("((capex.purchaseCategory is null) or (capex.purchaseCategory.id =? and capex.purchaseSubCategory is null) or (capex.purchaseSubCategory.id =?)) ");
                qc.appendParameter(psc.getPurchaseCategory().getId());
                qc.appendParameter(psc.getId());
                q.createQueryCondition("capex.lastApprovedRequest is not null");
                q.createQueryCondition("capex.site.id=?").appendParameter(department.getSite().getId());
                q.createQueryCondition("capex.id in(select cd.capex.id from CapexDepartment cd where cd.department.id=?)").appendParameter(department.getId());
                return q.list();
            }

        });
    }

    public void saveCapexRequestHistory(CapexRequestHistory history) {
        getHibernateTemplate().saveOrUpdate(history);
    }

    public void saveCapexRequestHistoryItem(CapexRequestHistoryItem historyItem) {
        getHibernateTemplate().saveOrUpdate(historyItem);
    }

    public void deleteCapexRequest(CapexRequest capexRequest) {
        HibernateTemplate template = getHibernateTemplate();
        Integer id = capexRequest.getId();
        template.delete("from CapexRequestHistoryItem crhi where crhi.capexRequestHistory.capexRequest.id = ?", id, Hibernate.INTEGER);
        template
                .delete(
                        "from CapexRequestApproveRequest crar where crar.approveRequestId in (select crh.approveRequestId from CapexRequestHistory crh where crh.capexRequest.id = ?)",
                        id, Hibernate.INTEGER);
        template.delete("from CapexRequestHistory crh where crh.capexRequest.id = ?", id, Hibernate.INTEGER);
        template.delete("from CapexRequestItem cri where cri.capexRequest.id = ?", id, Hibernate.INTEGER);
        template.delete("from CapexRequestAttachment cra where cra.capexRequest.id = ?", id, Hibernate.INTEGER);
        template.delete("from CapexRequest cr where cr.id = ?", id, Hibernate.INTEGER);
    }

    public Department[] getDepartments(final Capex cp) {
        List l = (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String s = "select cpm.department from CapexDepartment cpm";
                CompositeQuery q = new CompositeQuery(s, "cpm.department.id", session);
                q.createQueryCondition("cpm.capex.id=?").appendParameter(cp.getId());
                return q.list();
            }

        });
        return (Department[]) l.toArray(new Department[l.size()]);
    }

    public void withdrawCapexRequest(CapexRequest capexRequest) {
        HibernateTemplate template = getHibernateTemplate();
        template.delete("from CapexRequestApproveRequest crar where crar.approveRequestId = ?", capexRequest.getApproveRequestId(), Hibernate.STRING);
        capexRequest.setStatus(CapexRequestStatus.DRAFT);
        capexRequest.setApproveRequestId(null);
        template.update(capexRequest);
    }

}
