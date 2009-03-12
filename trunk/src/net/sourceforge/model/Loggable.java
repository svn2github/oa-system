/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model;

import net.sourceforge.model.admin.Site;

/**
 * ֧����־��¼�Ķ���Ӧ��ʵ�ָýӿ�
 * 
 * @author nicebean
 * @version 1.0 (2006-02-15)
 */
public interface Loggable {
    
    /**
     * ���ض����Site
     * @return Site����
     */
    public Site getLogSite();
    
    /**
     * ������־�б�ʾ�ö��������
     * @return
     */
    public String getLogTargetName();

    /**
     * ���ض�������־�ı�ʾ��id
     * @return
     */
    public String getLogTargetId();
    
    /**
     * ����ָ��action��Ӧ��Key Field��Ϣ�����action����֧�֣�����null
     * @param action
     * @return
     */
    public String[][] getLogFieldInfo(final String action);
    
}