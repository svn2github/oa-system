/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import net.sourceforge.dao.business.BaseApproveRequestDAO;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.Approvable;
import net.sourceforge.model.business.BaseApproveRequest;
import net.sourceforge.model.metadata.ApproveStatus;
import net.sourceforge.model.metadata.YesNo;

/**
 * 所有Manager类的基类，定义所有Manager的共用方法
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public class BaseManager {
    
    protected void insertRejectApproveRequest(BaseApproveRequestDAO dao, Approvable target, User user, String comment) {
        if (user == null) {
            user = new User(new Integer(1));
        }
        int seq = 1;
        BaseApproveRequest ebar = null;
        for (Iterator itor = dao.getBaseApproveRequestListByApproveRequestId(target.getApproveRequestId()).iterator(); itor.hasNext(); ) {
            BaseApproveRequest bar = (BaseApproveRequest) itor.next();
            if (bar.getApprover().equals(user)) {
                ebar = bar;
                continue;
            }
            if (bar.getSeq() != seq) {
                bar.setSeq(seq);
                dao.updateBaseApproveRequest(bar);
            }
            seq++;
        }
        if (ebar == null) {
            ebar = target.createNewApproveRequestObj();
            ebar.setApproveRequestId(target.getApproveRequestId());
            ebar.setApprover(user);
            ebar.setActualApprover(user);
            ebar.setApproveDate(new Date());
            ebar.setCanModify(YesNo.NO);
            ebar.setComment(comment);
            ebar.setStatus(ApproveStatus.REJECTED);
            ebar.setSeq(seq);
            dao.insertBaseApproveRequest(ebar);
        } else {
            ebar.setApproveDate(new Date());
            if (ebar.getComment() != null && ebar.getComment().trim().length() != 0) {
                ebar.setComment(ebar.getComment() + ";" + comment);
            } else {
                ebar.setComment(comment);
            }
            ebar.setStatus(ApproveStatus.REJECTED);
            ebar.setSeq(seq);
            dao.updateBaseApproveRequest(ebar);
        }
        
    }

    protected long getTodayTimeMillis() {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.clear();
        c1.set(Calendar.YEAR, c2.get(Calendar.YEAR));
        c1.set(Calendar.MONTH, c2.get(Calendar.MONTH));
        c1.set(Calendar.DATE, c2.get(Calendar.DATE));
        return c1.getTimeInMillis();
    }

}
