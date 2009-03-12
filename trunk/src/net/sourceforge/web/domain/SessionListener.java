/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.domain;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 该类监听Session的创建和销毁
 * @author ych
 * @version 1.0 (2005-10-9)
 */
public class SessionListener implements HttpSessionListener {

	/**
     * 在Session创建时调用 
	 */
	public void sessionCreated(HttpSessionEvent event) {

	}

    /**
     * 在Session销毁时调用
     */
	public void sessionDestroyed(HttpSessionEvent event) {
		SessionList.removeSession(event.getSession());
	}

}
