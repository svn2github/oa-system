/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/**
 * @author ych
 * @version 1.0 (2005-10-08)
 */
package net.sourceforge.web.domain;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.User;
import net.sourceforge.service.admin.UserManager;
import net.sourceforge.utils.RWLock;
import net.sourceforge.utils.SessionTempFile;
import net.sourceforge.web.struts.action.ServiceLocator;
import net.sourceforge.web.struts.action.admin.BasicAction;


/**
 * 该类保存Session列表
 * @author ych
 * @version 1.0 (2005-10-8)
 */
public class SessionList {
	
	private static final List sessionList=new ArrayList();
    
    private static int totalUserVisitCount = 0;
    
    public static void resetTotalUserVisitCount() {
        totalUserVisitCount = 0;
    }
    
    public static int getTotalUserVisitCount() {
        return totalUserVisitCount;
    }
	
	public static final RWLock lock=new RWLock();
	
	
	/**
	 * 向sessionList添加一个session
	 * @param session
	 */
	public static void addSession(HttpSession session) {
		lock.getWriteLock();
		if (!sessionList.contains(session)) {
			if (session.getAttribute(BasicAction.LOGIN_USER_KEY)!=null) {
                //User user = (User) session.getAttribute(BasicAction.LOGIN_USER_KEY);
                //user.getPrimarySite();
				sessionList.add(session);
                totalUserVisitCount++;
            }
		}	
		lock.releaseLock();
	}
	
	/**
	 * 从sessionList移除一个session
	 * @param session
	 */
	public static void removeSession(HttpSession session) {
		lock.getWriteLock();
		if (sessionList.contains(session)) { 
			sessionList.remove(session);
            SessionTempFile.clearTempFile(session);
        }
		lock.releaseLock();
	}
	
	/**
	 * 取得sessionList
	 * @return List
	 */
	public static List getSessionList() {
		return sessionList;
	}
	
	/**
	 * 根据site从sessionList中取得session列表 
	 * @param request 
	 * @param Site
	 * @return List
	 */
	public static List getSessionListBySite(Site site, HttpServletRequest request) {
		List retList=new ArrayList();
        UserManager um=ServiceLocator.getUserManager(request);
		for (int index=0;index<sessionList.size();index++) {
			try {
				HttpSession session=(HttpSession)sessionList.get(index);
				User loginUser=(User)session.getAttribute(BasicAction.LOGIN_USER_KEY);
                if (um.getUserSite(loginUser.getId(),site.getId())!=null)
					retList.add(session);
			} catch (Exception e) {}
		}
		return retList;
	}
	
	
}
