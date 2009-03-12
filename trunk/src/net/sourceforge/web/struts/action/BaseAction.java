/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.action;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.FastHashMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.html.Constants;

import net.sourceforge.model.admin.Department;
import net.sourceforge.model.admin.Function;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.model.metadata.MetadataDetailEnum;
import net.sourceforge.service.admin.DepartmentManager;
import net.sourceforge.service.admin.FunctionManager;
import net.sourceforge.service.admin.SiteManager;
import net.sourceforge.service.admin.UserManager;
import net.sourceforge.utils.SessionTempFile;
import net.sourceforge.web.struts.action.admin.BasicAction;
import net.sourceforge.web.struts.repeatsubmit.PreventRepeatSubmitPlugIn;
import com.shcnc.struts.action.ActionException;
import com.shcnc.utils.BeanHelper;

/**
 * 扩展Action class,提供众多helper方法
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public abstract class BaseAction extends com.shcnc.struts.action.BaseAction {
    protected Object getObjectFromRequest(String parameterName,Class clazz,String idName,HttpServletRequest request) throws Exception
    {
        String str_param_value=request.getParameter(parameterName);
        if(str_param_value==null)
            throw new ActionException("errors.parameter.notSet",parameterName);
        Class idType=BeanHelper.getProperyType(clazz,idName);
        BeanLoader loader=ServiceLocator.getBeanLoader(request);
        Object o=null;
        Serializable idValue=null;
        if(idType.equals(String.class))
        {
            idValue=str_param_value;
        }
        else if(idType.equals(Integer.class))
        {
            idValue=ActionUtils.parseInt(str_param_value);
            if(idValue==null) throw new ActionException("errors.parameter.notInteger",parameterName);
        }
        else throw new RuntimeException("unsupported id type: "+idType);
        
        o=loader.load(clazz,idName,idValue);
        return o;
    }
    
    protected Object getObjectFromRequestAndCheckExists(String parameterName,Class clazz,String idName,HttpServletRequest request) throws Exception
    {
        Object o=getObjectFromRequest(parameterName,clazz,idName,request);
        if(o==null)
           throw new ActionException(getSimpleClassName(clazz)+".notFound",request.getParameter(parameterName));
        return o;
          
    }
    // private static ApplicationContext ctx = null;

    private String getSimpleClassName(Class clazz) {
        String name=clazz.getName();
        int index=name.lastIndexOf('.');
        if(index!=-1) name=name.substring(index+1);
        return StringUtils.uncapitalize(name);
    }
    protected static Log log = LogFactory.getLog(BaseAction.class);

    
    protected String[] getParameterValues(String parameterName, HttpServletRequest request) {
        String[] retVal = request.getParameterValues(parameterName);
        if (retVal == null)
            return new String[0];
        return retVal;
    }
    /**
     * 当前语言是否英文
     * 
     * @param request
     * @return
     */
    protected boolean isEnglish(HttpServletRequest request) {
        return this.getCurrentUser(request).getLocale().equals("en");
    }

    
    protected void postGlobalMessage(String messageKey, Object arg, HttpSession session) {
        ActionMessage message = (arg == null ? new ActionMessage(messageKey) : new ActionMessage(messageKey, arg));
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, message);
        saveMessages(session, messages);
    }
    
    protected void postGlobalMessage(String messageKey, HttpSession session) {
        postGlobalMessage(messageKey,null,session);
    }
    
    /**
     * Convenience method to bind objects in Actions
     * 
     * @param name
     * @return
     */
    // protected Object getBean(String name) {
    // if (ctx == null) {
    // ctx = WebApplicationContextUtils
    // .getRequiredWebApplicationContext(servlet
    // .getServletContext());
    // }
    // return ctx.getBean(name);
    // }
    /**
     * get Current User
     * 
     * @param request
     * @return
     */
    protected User getCurrentUser(HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(BasicAction.LOGIN_USER_KEY);
        return currentUser;
    }

    /**
     * get Current Function
     * 
     * @param request
     * @return
     */
    protected Function getFunction(HttpServletRequest request) {
        return (Function) request.getAttribute(ATTR_FUNCTION);
    }

    /**
     * 是否有function
     * 
     * @param request
     * @return
     */
    protected boolean hasFunction(HttpServletRequest request) {
        return getFunction(request) != null;
    }

    /**
     * 当前function是否global级
     * 
     * @param request
     * @return
     */
    protected boolean isGlobal(HttpServletRequest request) {
        return getFunction(request).isGlobal();
    }

    /**
     * 当前function是否site级
     * 
     * @param request
     * @return
     */
    protected boolean isSite(HttpServletRequest request) {
        return getFunction(request).isSite();
    }

    /**
     * 当前function是否Department级
     * 
     * @param request
     * @return
     */
    protected boolean isDepartment(HttpServletRequest request) {
        return getFunction(request).isDepartment();
    }

    /**
     * get Granted Site Deparment List according to current user and function
     * fill department list to property departments of everty site
     * 
     * @param request
     * @return list of site
     */
    protected List getGrantedSiteDeparmentList(HttpServletRequest request) {
        UserManager um = ServiceLocator.getUserManager(request);
        return um.getGrantedSiteDeparmentList(this.getCurrentUser(request), this.getFunction(request));
    }

    /**
     * get Granted Site Deparment List according to current user and function
     * fill department list to property departments of everty site if the result
     * is empty ,throw actionException
     * 
     * @param request
     * @return list of site
     */
    protected List getAndCheckGrantedSiteDeparmentList(HttpServletRequest request) {
        List list = this.getGrantedSiteDeparmentList(request);
        if (list.isEmpty()) {
            throw new ActionException("errors.noSiteGranted");
        }
        return list;
    }

    /**
     * get Granted Site List according to current user and function
     * 
     * @param request
     * @return list of site
     */
    protected List getGrantedSiteList(HttpServletRequest request) {
        if (!this.isDepartment(request)) {
            UserManager um = ServiceLocator.getUserManager(request);
            if (this.hasGlobalPower(request)) {
                SiteManager sm = ServiceLocator.getSiteManager(request);
                return sm.getAllEnabledSiteList();
            }

            return um.getGrantedSiteList(this.getCurrentUser(request), this.getFunction(request));
        }
        throw new RuntimeException("not with department level function");

    }

    /**
     * get Granted Site List according to current user and function if result is
     * empty ,throw actoinException
     * 
     * @param request
     * @return list of site
     */
    protected List getAndCheckGrantedSiteList(HttpServletRequest request) {
        List list = this.getGrantedSiteList(request);
        if (list.isEmpty()) {
            throw new ActionException("errors.noSiteGranted");
        }
        return list;
    }

    /**
     * check whether the current user has power for department if haven't, throw
     * ActionException
     * 
     * @param department
     * @param request
     */
    protected void checkDepartment(Department department, HttpServletRequest request) {
        if (department.getEnabled().equals(EnabledDisabled.DISABLED)) {
            throw new ActionException("errors.department.disabled", department.getName());
        }

        if (department.getSite().getEnabled().equals(EnabledDisabled.DISABLED)) {
            throw new ActionException("errors.site.disabled", department.getSite().getName());
        }

        UserManager um = ServiceLocator.getUserManager(request);
        if (!um.hasDepartmentPower(department, this.getCurrentUser(request), this.getFunction(request))) {
            throw new ActionException("errors.noDepartmentPermission", department.getName());
        }
    }

    /**
     * get department according to the request parameter(id of department) and
     * check whether current user has power for the department
     * 
     * @param paramterName
     *            request paramter name
     * @param request
     * @return department
     */
    protected Department getAndCheckDepartment(String paramterName, HttpServletRequest request) {
        return this.getAndCheckDepartment(ActionUtils.parseInt(request.getParameter(paramterName)), request);

    }

    /**
     * get department according to the id and check whether current uer has
     * power for the department if no power, throw ActionException
     * 
     * @param departmentId
     *            id of department
     * @param request
     * @return
     */
    protected Department getAndCheckDepartment(Integer departmentId, HttpServletRequest request) {

        DepartmentManager dm = ServiceLocator.getDepartmentManager(request);
        Department department = dm.getDepartment(departmentId);

        if (department == null) {
            throw new ActionException("department.notFound", department.getId());
        }
        checkDepartment(department, request);
        return department;

    }

    /**
     * check whether current user has power for the user if not, throw
     * ActionException
     * 
     * @param user
     * @param request
     */
    protected void checkUser(User user, HttpServletRequest request) {
        UserManager um = ServiceLocator.getUserManager(request);
        boolean hasPower = um.hasUserPower(user, this.getCurrentUser(request), this.getFunction(request));
        if (!hasPower)
            throw new ActionException("errors.noUserPermission", user.getName());
    }

    /**
     * check whether current user has power for the user designated by parameter
     * id if not, throw ActionException
     * 
     * @param userId
     *            id of user
     * @param request
     */
    protected void checkUser(Integer userId, HttpServletRequest request) {
        getAndCheckUser(userId, request);
    }

    /**
     * get user according to id of user, and check whether the current user has
     * power for the user.
     * 
     * @param id
     *            id of user
     * @param request
     * @return User
     */
    protected User getAndCheckUser(Integer id, HttpServletRequest request) {
        UserManager um = ServiceLocator.getUserManager(request);
        User user = um.getUser(id);
        if (user == null)
            throw new ActionException("user.notFound");
        this.checkUser(user, request);
        return user;
    }

    /**
     * check whether current user has power for the site if not throw
     * ActionException
     * 
     * @param site
     * @param request
     */
    protected void checkSite(Site site, HttpServletRequest request) {

        if (site.getEnabled().equals(EnabledDisabled.DISABLED)) {
            throw new ActionException("errors.site.disabled", site.getName());
        }

        UserManager um = ServiceLocator.getUserManager(request);
        if (!um.hasSitePower(site, this.getCurrentUser(request), this.getFunction(request))) {
            throw new ActionException("errors.noSitePermission", site.getName());
        }
    }

    /**
     * get site according to the request parameter name(id of site), and check
     * wheather current user has power for it if not,throw ActionException
     * 
     * @param parameterName
     *            request parameter name
     * @param request
     * @return site
     */
    protected Site getAndCheckSite(String parameterName, HttpServletRequest request) {
        return this.getAndCheckSite(ActionUtils.parseInt(request.getParameter(parameterName)), request);
    }

    /**
     * check wheather current user has power for the site specified by site_id
     * if not,throw ActionException
     * 
     * @param site_id
     *            id of site
     * @param request
     */
    protected void checkSite(Integer site_id, HttpServletRequest request) {
        getAndCheckSite(site_id, request);
    }

    /**
     * get site from db according to site_id and check whether current user has
     * power for the site if not ,throw ActionException
     * 
     * @param site_id
     *            id of site
     * @param request
     * @return site
     */
    protected Site getAndCheckSite(Integer site_id, HttpServletRequest request) {

        SiteManager sm = ServiceLocator.getSiteManager(request);
        Site site = sm.getSite(site_id);
        if (site == null) {
            throw new ActionException("site.notFound", site_id);
        }
        checkSite(site, request);
        return site;

    }

    /**
     * return whether the current user has global power for current function
     * 
     * @param request
     * @return
     */
    protected boolean hasGlobalPower(HttpServletRequest request) {
        UserManager um = ServiceLocator.getUserManager(request);
        return um.hasGlobalPower(this.getCurrentUser(request), this.getFunction(request));
    }

    /**
     * check whether the current user has global power for current function if
     * not, throw ActionException
     * 
     * @param request
     */
    protected void checkGlobalPower(HttpServletRequest request) {
        if (!this.hasGlobalPower(request)) {
            throw new ActionException("errors.noGlobalPermission");
        }
    }

    /**
     * get token map
     * 
     * @param session
     * @return
     */
    private Map getTokenMap(HttpSession session) {
        Map tokenMap = (Map) session.getAttribute(PreventRepeatSubmitPlugIn.ATTRIBUTE_TOKENMAP);
        if (tokenMap == null) {
            tokenMap = new FastHashMap();
            session.setAttribute(PreventRepeatSubmitPlugIn.ATTRIBUTE_TOKENMAP, tokenMap);
        }
        return tokenMap;

    }

    /**
     * validate token
     * 
     * @param mapping
     * @param request
     */
    private void validateToken(ActionMapping mapping, HttpServletRequest request) {
        Map toMap = (Map) this.servlet.getServletContext().getAttribute(PreventRepeatSubmitPlugIn.ATTRIBUTE_TOMAP);
        if (toMap != null) {
            String toKey = (String) toMap.get(mapping.getPath());
            if (toKey != null) {
                Map tokenMap = this.getTokenMap(request.getSession());
                String saved = (String) tokenMap.remove(toKey);
                String token = request.getParameter(Constants.TOKEN_KEY);
                boolean invalid = true;
                if (token != null && saved != null) {
                    if (token.equals(saved)) {
                        invalid = false;
                    }
                }

                if (invalid) {
                    throw new ActionException("all.token.invalid");
                }
            }
        }
    }

    /**
     * save token
     * 
     * @param mapping
     * @param request
     */
    private void saveToken(ActionMapping mapping, HttpServletRequest request) {
        Map fromMap = (Map) this.servlet.getServletContext().getAttribute(PreventRepeatSubmitPlugIn.ATTRIBUTE_FROMMAP);
        if (fromMap != null) {
            String fromKey = (String) fromMap.get(mapping.getPath());

            if (fromKey != null) {
                Map tokenMap = this.getTokenMap(request.getSession());
                String token = this.generateToken(request);
                tokenMap.put(fromKey, token);
                request.setAttribute(Globals.TRANSACTION_TOKEN_KEY, token);
            }
        }
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // initiate function
        FunctionManager fm = ServiceLocator.getFunctionManager(request);
        fm.initiate(mapping.getModuleConfig());

        if (mapping instanceof SecureActionMapping) {
            // check login
            User currentUser = getCurrentUser(request);
            if (currentUser == null) {
                if (((SecureActionMapping) mapping).isDialog())
                    throw new ActionException("login.timeout");
                return new ActionForward(LOGIN_ACTION, true);
            }

            // validate token
            if (!this.isBack(request))
                this.validateToken(mapping, request);

            // process current function
            Integer functionId = ((SecureActionMapping) mapping).getFunctionId();
            if (functionId != null) {
                Function function = fm.getFunction(functionId);
                if (function == null) {
                    throw new ActionException("function.notFound", functionId);
                }
                request.setAttribute(ATTR_FUNCTION, function);

                if (this.isGlobal(request)) {
                    checkGlobalPower(request);
                }
                putVersionPostfixToRequest(request);

            }

        }
        // call super's execute if login ok
        ActionForward forward = super.execute(mapping, form, request, response);

        this.saveToken(mapping, request);

        putLangPostfixToRequest(request);

        return forward;
    }

    private void putLangPostfixToRequest(HttpServletRequest request) {
        if (this.getCurrentUser(request) == null)
            return;
        if (this.isEnglish(request))
            request.setAttribute(ATTR_LANG, LANG_ENG);
        else
            request.setAttribute(ATTR_LANG, LANG_CHN);
    }

    /**
     * put Version Postfix To Request according to current function
     * 
     * @param request
     */
    protected void putVersionPostfixToRequest(HttpServletRequest request) {
        if (this.isSite(request)) {
            request.setAttribute(ATTR_VERSION, POSTFIX_SITE);
        } else if (this.isGlobal(request)) {
            request.setAttribute(ATTR_VERSION, POSTFIX_GLOBAL);
        } else if (this.isDepartment(request)) {
            request.setAttribute(ATTR_VERSION, POSTFIX_DEPARTMENT);
        }
    }
    
    protected String getLocaleShortDescription(MetadataDetailEnum metadata, HttpServletRequest request) {
        if (LANG_CHN.equals(request.getAttribute(ATTR_LANG))) {
            return metadata.getChnShortDescription();
        }
        return metadata.getEngShortDescription();
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
    
    protected ActionForward downloadAttachment(InputStream in, String fileName, HttpServletRequest request) throws Exception {
        int index = SessionTempFile.createNewTempFile(request);
        OutputStream out = new FileOutputStream(SessionTempFile.getTempFile(index, request));
        byte buf[] = new byte[1024];
        for (int i = in.read(buf); i > 0; i = in.read(buf)) {
            out.write(buf, 0, i);
        }
        out.close();
        return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8"), true);
    }

    public static final String ATTR_VERSION = "x_version";

    public static final String ATTR_LANG = "x_lang";

    public static final String LANG_CHN = "chn";

    public static final String LANG_ENG = "eng";

    public static final String POSTFIX_GLOBAL = "";

    public static final String POSTFIX_SITE = "_site";

    public static final String POSTFIX_DEPARTMENT = "_department";

    public static final String FORWARD_PAGE = "page";

    public static final String FORWARD_SUCCESS = "success";
    
    public static final String FORWARD_FAIL = "fail";

    public static final String ATTR_RESULTLIST = "X_RESULTLIST";

    public static final String ATTR_SITELIST = "x_siteList";

    public static final String ATTR_ROWPAGE = "X_ROWPAGE";

    public static final String ATTR_OBJECT = "X_OBJECT";

    private static final String LOGIN_ACTION = "/login.do";

    private static final String ATTR_FUNCTION = "com.shcnc.struts.action.BaseAction.function";

    public static final String CONFIRM = "confirm";

}
