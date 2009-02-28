/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.aof.dao.admin.EmailDAO;
import com.aof.dao.admin.GlobalDAO;
import com.aof.dao.admin.HotelDAO;
import com.aof.dao.admin.ParameterDAO;
import com.aof.dao.admin.SiteDAO;
import com.aof.dao.admin.SupplierDAO;
import com.aof.dao.admin.UserDAO;
import com.aof.dao.business.BaseApproveRequestDAO;
import com.aof.dao.business.expense.ExpenseApproveRequestDAO;
import com.aof.dao.business.expense.ExpenseDAO;
import com.aof.dao.business.po.PurchaseOrderApproveRequestDAO;
import com.aof.dao.business.po.PurchaseOrderDAO;
import com.aof.dao.business.pr.CapexRequestApproveRequestDAO;
import com.aof.dao.business.pr.PurchaseRequestApproveRequestDAO;
import com.aof.dao.business.pr.PurchaseRequestDAO;
import com.aof.dao.business.rule.ApproverDelegateDAO;
import com.aof.dao.business.ta.TravelApplicationApproveRequestDAO;
import com.aof.dao.business.ta.TravelApplicationDAO;
import com.aof.model.admin.Email;
import com.aof.model.admin.EmailBatch;
import com.aof.model.admin.Function;
import com.aof.model.admin.GlobalMailReminder;
import com.aof.model.admin.GlobalParameter;
import com.aof.model.admin.Hotel;
import com.aof.model.admin.Site;
import com.aof.model.admin.SiteMailReminder;
import com.aof.model.admin.Supplier;
import com.aof.model.admin.User;
import com.aof.model.admin.query.EmailQueryOrder;
import com.aof.model.business.BaseApproveRequest;
import com.aof.model.business.expense.Expense;
import com.aof.model.business.po.PurchaseOrder;
import com.aof.model.business.pr.PurchaseRequest;
import com.aof.model.business.query.BaseApproveQueryCondition;
import com.aof.model.business.rule.ApproverDelegate;
import com.aof.model.business.rule.query.ApproverDelegateQueryCondition;
import com.aof.model.business.ta.TravelApplication;
import com.aof.model.metadata.ApproveStatus;
import com.aof.model.metadata.ApproverDelegateType;
import com.aof.model.metadata.GlobalMailReminderType;
import com.aof.model.metadata.SiteMailReminderType;
import com.aof.model.metadata.YesNo;
import com.aof.service.BaseManager;
import com.aof.service.admin.EmailManager;
import com.aof.service.admin.FunctionManager;

/**
 * 操作Email的实现类
 * 
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class EmailManagerImpl extends BaseManager implements EmailManager,ResourceLoaderAware {
	
	private Log log = LogFactory.getLog(EmailManagerImpl.class);
	
    private EmailDAO dao;
    
    private GlobalDAO globalDAO;
    
    private VelocityEngine velocityEngine;
    
    private PurchaseRequestDAO purchaseRequestDAO;
    
    private ParameterDAO parameterDAO;
    
    private UserDAO userDAO;
    
    private FunctionManager functionManager;
    
    private HotelDAO hotelDAO;
    
    private ExpenseDAO expenseDAO;

    private PurchaseOrderDAO purchaseOrderDAO;
    
    private TravelApplicationDAO travelApplicationDAO;
    
    private SupplierDAO supplierDAO;
    
    private ExpenseApproveRequestDAO expenseApproveRequestDAO;
    
    private PurchaseOrderApproveRequestDAO purchaseOrderApproveRequestDAO;
    
    private PurchaseRequestApproveRequestDAO purchaseRequestApproveRequestDAO;
    
    //private CapexRequestApproveRequestDAO capexRequestApproveRequestDAO;
    
    private TravelApplicationApproveRequestDAO travelApplicationApproveRequestDAO;
    
    private ApproverDelegateDAO approverDelegateDAO;
    
    private SiteDAO siteDAO;
    
    private ResourceLoader resourceLoader;
    
    public void setExpenseDAO(ExpenseDAO expenseDAO) {
        this.expenseDAO = expenseDAO;
    }
    
    public void setHotelDAO(HotelDAO hotelDAO) {
        this.hotelDAO = hotelDAO;
    }

    public void setSupplierDAO(SupplierDAO supplierDAO) {
        this.supplierDAO = supplierDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public void setEmailDAO(EmailDAO dao) {
        this.dao = dao;
    }
    
	public void setGlobalDAO(GlobalDAO globalDao) {
		this.globalDAO=globalDao;
	}
	
    public void setCapexRequestApproveRequestDAO(CapexRequestApproveRequestDAO capexRequestApproveRequestDAO) {
        //this.capexRequestApproveRequestDAO = capexRequestApproveRequestDAO;
    }

    public void setExpenseApproveRequestDAO(ExpenseApproveRequestDAO expenseApproveRequestDAO) {
        this.expenseApproveRequestDAO = expenseApproveRequestDAO;
    }

    public void setPurchaseOrderApproveRequestDAO(PurchaseOrderApproveRequestDAO purchaseOrderApproveRequestDAO) {
        this.purchaseOrderApproveRequestDAO = purchaseOrderApproveRequestDAO;
    }

    public void setPurchaseRequestApproveRequestDAO(PurchaseRequestApproveRequestDAO purchaseRequestApproveRequestDAO) {
        this.purchaseRequestApproveRequestDAO = purchaseRequestApproveRequestDAO;
    }

    public void setTravelApplicationApproveRequestDAO(TravelApplicationApproveRequestDAO travelApplicationApproveRequestDAO) {
        this.travelApplicationApproveRequestDAO = travelApplicationApproveRequestDAO;
    }

    public void setApproverDelegateDAO(ApproverDelegateDAO approverDelegateDAO) {
        this.approverDelegateDAO = approverDelegateDAO;
    }

    public void setSiteDAO(SiteDAO siteDAO) {
        this.siteDAO = siteDAO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin#getEmail(java.lang.Integer)
     */
    public Email getEmail(Integer id)  {
        return dao.getEmail(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin#updateEmail(com.aof.model.admin.Email)
     */
    public Email updateEmail(Email email)  {
        return dao.updateEmail(email);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin#insertEmail(com.aof.model.admin.Email)
     */
    public Email insertEmail(Email email, String body)  {
        email = dao.insertEmail(email);
        dao.saveEmailBody(email.getId(), body);
        return email;
    }
    
    public EmailBatch insertEmailBatch(EmailBatch emailBatch, String body) {
        emailBatch = dao.insertEmailBatch(emailBatch);
        dao.saveEmailBatchBody(emailBatch.getId(), body);
        return emailBatch;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin#getEmailListCount(java.util.Map)
     */
    public int getEmailListCount(Map conditions)  {
        return dao.getEmailListCount(conditions);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin#getEmailList(java.util.Map, int, int, com.aof.model.admin.query.EmailQueryOrder, boolean)
     */
    public List getEmailList(Map conditions, int pageNo, int pageSize, EmailQueryOrder order, boolean descend)  {
        return dao.getEmailList(conditions, pageNo, pageSize, order, descend);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin#insertEmail(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
	public void insertEmail(Site site,String from , String to , String subject , String body)  {
		Email email=new Email();
		email.setMailFrom(from);
		email.setMailTo(to);
		email.setSubject(subject);
        email.setSite(site);
		dao.insertEmail(email);
        dao.saveEmailBody(email.getId(), body);
	}
    
    public void insertEmailBatch(Site site,String from, String to, String body,String emailTemplateName, String refNo, User user) {
        EmailBatch emailBatch=new EmailBatch();       
        emailBatch.setSite(site);
        emailBatch.setMailToUser(user);
        emailBatch.setTemplateName(emailTemplateName);
        emailBatch.setRefNo(refNo);
        emailBatch.setIsSend(YesNo.NO);
        dao.insertEmailBatch(emailBatch);
        dao.saveEmailBatchBody(emailBatch.getId(), body);
    }

	private void sendEmail(JavaMailSenderImpl sender, Email email) {
		MimeMessage message = sender.createMimeMessage();
		try {
            MimeMessageHelper helper = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_RELATED,"UTF-8");
            helper.setFrom(email.getMailFrom());
            helper.setTo(email.getMailTo());
            String subject = email.getSubject();
            helper.setSubject(subject);
            Site site = email.getSite();
            Map context = new HashMap();
            context.put("subject", subject);
            context.put("x_email_to_site", site);
            context.put("body", dao.getEmailBody(email.getId()));
            helper.setText(VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, "EMail.vm", context), true);
            final InputStream in = siteDAO.getSiteLogo(email.getSite().getId());
            if (in == null) {
                helper.addInline("logo.gif", resourceLoader.getResource("/WEB-INF/emailTemplate/resource/logo.gif"));
            } else {
                in.mark(0);
                DataSource ds = new DataSource() {

                    public String getContentType() {
                        return "image/gif";
                    }

                    public InputStream getInputStream() throws IOException {
                        in.reset();
                        return in;
                    }

                    public String getName() {
                        return "logo.gif";
                    }

                    public OutputStream getOutputStream() throws IOException {
                        return null;
                    }
                    
                };
                helper.addInline("logo.gif", ds);
            }
            //helper.addInline("header_bg.gif", resourceLoader.getResource("/WEB-INF/emailTemplate/resource/header_bg.gif"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
		
		sender.send(message);
		email.setWaitToSend(YesNo.NO);
		email.setSentTime(new Date());
		dao.updateEmail(email);
	}

	private JavaMailSenderImpl getMailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		GlobalParameter para=globalDAO.getParameter();
		sender.setHost(para.getSmtpAddress());
    	sender.setUsername(para.getSmtpUsername());
    	sender.setPassword(para.getSmtpPassword());
		return sender;
	}
	
    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin#sendEmail()
     */
	public void sendEmail(){
		JavaMailSenderImpl sender=getMailSender();
		Iterator mailItor=dao.getWaitToSendEmailList().iterator();
		while (mailItor.hasNext()) {
			Email email=(Email)mailItor.next();
			try {
				sendEmail(sender,email);
			} catch (Exception e) {
	    		log.error("send fail!\n cause:"+e.toString());
	    		System.out.println("send fail!\n cause:"+e.toString());
	    		email.setFailCount(new Integer(email.getFailCount().intValue()+1));
				dao.updateEmail(email);
	    	} 
		}
    }
    
    

    public void insertEmail(Site site,String to, String templateLocation, Map context) {
        context.putAll(emailProperty);
        context.put("dateTool",dateTool);
        context.put("numberTool",numberTool);
        context.put("signature", site.getName());
        String [] ss;
        try {
            ss=splitTitleAndContent(
                    VelocityEngineUtils.mergeTemplateIntoString(
                            this.velocityEngine,templateLocation,context));
        } catch (VelocityException e) {
            throw new RuntimeException(e);
        }
        this.insertEmail(site,emailProperty.getProperty("from"),to,ss[0],ss[1]);
    }
    
    public void insertEmailBatch(Site site,String to,String templateLocation,String refNo,Map context,String batchEmailTemplateName) {
        context.putAll(emailProperty);
        context.put("dateTool",dateTool);
        context.put("numberTool",numberTool);
        context.put("signature", site.getName());
        String ss;
        try {
            ss=VelocityEngineUtils.mergeTemplateIntoString(
                            this.velocityEngine, batchEmailTemplateName,context);
        } catch (VelocityException e) {
            throw new RuntimeException(e);
        }
        this.insertEmailBatch(site,emailProperty.getProperty("from"),to,ss,templateLocation, refNo, (User)context.get("user"));
    }
    
    /*public static void main(String[] args) 
    {
        String s="sdf\nTitle:\n122\n\nBody:\ndfff";
        String [] ss=splitTitleAndContent(s);
        System.out.println("|"+ss[0]+"|");
        System.out.println("|"+ss[1]+"|");
    }*/
    
    private static String[]  splitTitleAndContent(String s)
    {
        String[] retVal=new String[2];
        LineNumberReader reader=new LineNumberReader(new StringReader(s));
        String line;
        boolean titleRead=false;
        try {
            while(true)
            {
                line=reader.readLine();
                if(line==null) break;
                if(!titleRead && line.trim().equals("Title:"))
                {
                    line=reader.readLine();
                    if(line==null) break;
                    retVal[0]=line;
                    titleRead=true;
                }
                else if(titleRead && line.trim().equals("Body:"))
                {
                    retVal[1]=getRemain(reader);
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return retVal;
    }

    private static String getRemain(LineNumberReader reader) {
        StringBuffer sb=new StringBuffer();
        int c;
        try {
            while((c=reader.read())!=-1)
            {
                sb.append((char)c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
    
    public void init()
    {
        InputStream is =this.getClass().getResourceAsStream("/email.properties");
        emailProperty=new Properties();
        try {
            emailProperty.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally{
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }
    
    private static Properties emailProperty;
    
    private static DateTool dateTool=new DateTool();
    private static NumberTool numberTool=new NumberTool();
    
    public void mailReminder() {
        Date now=new Date();
        Set siteSet=parameterDAO.getEnabledSiteSetWithMailReminders();
        for (Iterator iter = siteSet.iterator(); iter.hasNext();) {
            Site site = (Site) iter.next();
            notifyPurchaseRequestPurchaser(now,site);
            notifyPurchaseOrderFinalConfirmer(now,site);
            notifyTravelApplicationBooker(now,site);
            notifyPurchaseRequestApprover(now,site);
            notifyPurchaseOrderApprover(now,site);
            notifyCapexRequestApprover(now,site);
            notifyTravelApplicationApprover(now,site);
            notifyExpenseApprover(now,site);
            notifyExpenseFinalConfirmer(now,site);
            notifyHotelMaintainer(now,site);
            notifySupplierMaintainer(now,site);
        }
    }
    
    public void sendBatchEmail() {
        List list = dao.getAllUnSendEmailBatch();
        User oldMailToUser = null;
        User newMailToUser = null;
        Site oldSite = null;
        Site newSite = null;
        String oldTemplate = null;
        String newTemplate = null;
        StringBuffer body = new StringBuffer();
        
        for (int i = 0; i < list.size(); i++) 
        {
            EmailBatch eb = (EmailBatch)list.get(i);
            newMailToUser = eb.getMailToUser();
            newTemplate = eb.getTemplateName();
            newSite = eb.getSite();
            
            if (newMailToUser.equals(oldMailToUser) && newTemplate.equals(oldTemplate) && newSite.equals(oldSite)) {
                body.append(dao.getEmailBatchBody(eb.getId()));
            } else {
                if (oldSite != null) {  
                    Map context =  new HashMap();
                    context.put("batchEmailContext", body.toString());
                    context.put("user", oldMailToUser);
                    insertEmail(oldSite, oldMailToUser.getEmail(), oldTemplate, context);
                }
                
                oldMailToUser = newMailToUser;
                oldSite = newSite;
                oldTemplate = newTemplate;
                body = new StringBuffer();
                body.append(dao.getEmailBatchBody(eb.getId()));
            }
            
            eb.setIsSend(YesNo.YES);
            dao.updateEmailBatch(eb);
        }
        
        if (oldSite != null) {  
            Map context =  new HashMap();
            context.put("batchEmailContext", body.toString());
            context.put("user", oldMailToUser);
            insertEmail(oldSite, oldMailToUser.getEmail(), oldTemplate, context);
        }
    }
    
    private void notifyExpenseFinalConfirmer(Date now, Site site) {
        SiteMailReminder smr=(SiteMailReminder) site.getMailReminders().get
        (SiteMailReminderType.FINANCE_NOT_RESPOND_EXPENSE_REQUEST);
        if(smr==null) return;
        if(smr.getMaxTime()<=0) return;
        List expenseList=expenseDAO.getFinanceNotRespondedExpenseList(now,smr);
        if(expenseList.isEmpty()) return;
        Map context=new HashMap();
        context.put("x_expenseList",expenseList);
        
        List userList=this.getUserList(site,"finalConfirmPurchaseOrder");
        for (Iterator iterator = userList.iterator(); iterator.hasNext();) {
            User user = (User) iterator.next();
            context.put("x_user",user);
            context.put("role", EmailManager.EMAIL_ROLE_FINANCE);
            this.insertEmail(site,user.getEmail(),"ExpenseFinalConfirmRemind.vm",context);
        }
    
        for (Iterator iter = expenseList.iterator(); iter.hasNext();) {
            Expense expense = (Expense) iter.next();
            expense.emailed(now);
            expenseDAO.updateExpense(expense);
        }
        
    }

    private void notifyHotelMaintainer(Date now, Site site) {
        GlobalMailReminder gmr = parameterDAO.getGlobalMailReminder(
                                    GlobalMailReminderType.HOTEL_MAINTAINER_NOT_RESPOND);
        if (gmr==null) return;
        if (gmr.getMaxTime()<=0) return;
        List hotelList=hotelDAO.getHotelMaintainerNotResponsedList(site,now,gmr);

        if (hotelList.size()>0) {
            List userList=this.getUserList(site,"siteHotelMaintenance");
            for (Iterator itor = userList.iterator(); itor.hasNext();) {
                User user = (User) itor.next();
                Map context=new HashMap();
                context.put("x_user",user);
                context.put("x_hotelList",hotelList);
                context.put("role", EmailManager.EMAIL_ROLE_HOTEL_MAINTAINER);
                this.insertEmail(site,user.getEmail(),"HotelPromoteRemind.vm",context);
            }
            for (Iterator itor = hotelList.iterator(); itor.hasNext();) {
                Hotel hotel = (Hotel) itor.next();
                hotel.emailed(now);
                hotelDAO.updateHotel(hotel);
            }
        }
    }
    
    private void notifySupplierMaintainer(Date now, Site site) {
        GlobalMailReminder gmr = parameterDAO.getGlobalMailReminder(GlobalMailReminderType.SUPPLIER_MAINTAINER_NOT_RESPOND);
        if (gmr==null) return;
        if (gmr.getMaxTime()<=0) return;
        List supplierList=supplierDAO.getSupplierMaintainerNotResponsedList(site,now,gmr);

        if (supplierList.size()>0) {
            List userList=this.getUserList(site,"siteSupplierMaintenance");
            for (Iterator itor = userList.iterator(); itor.hasNext();) {
                User user = (User) itor.next();
                Map context=new HashMap();
                context.put("x_user",user);
                context.put("x_supplierList",supplierList);
                context.put("role", EmailManager.EMAIL_ROLE_SUPPLIER_MAINTAINER);
                this.insertEmail(site,user.getEmail(),"SupplierPromoteRemind.vm",context);
            }
            for (Iterator itor = supplierList.iterator(); itor.hasNext();) {
                Supplier supplier = (Supplier) itor.next();
                supplier.emailed(now);
                supplierDAO.updateSupplier(supplier);
            }
        }
    }
    private void notifyTravelApplicationBooker(Date now, Site site) {
        SiteMailReminder smr=(SiteMailReminder) site.getMailReminders().get
            (SiteMailReminderType.RECEPTION_NOT_RESPOND_TRAVELING_APPLICATION);
        if(smr==null) return;
        if(smr.getMaxTime()<=0) return;
        List taList=travelApplicationDAO.getReceptionNotRespondedTravelApplication(now,smr);
        if(taList.isEmpty()) return;
        Map context=new HashMap();
        context.put("x_taList",taList);
        
        List userList=this.getUserList(site,"TravelApplicationPurchase");
        for (Iterator iterator = userList.iterator(); iterator.hasNext();) {
            User user = (User) iterator.next();
            context.put("x_user",user);
            context.put("role", EmailManager.EMAIL_ROLE_PURCHASER);
            this.insertEmail(site,user.getEmail(),"ReceptionRemind.vm",context);
        }

        for (Iterator iter = taList.iterator(); iter.hasNext();) {
            TravelApplication ta = (TravelApplication) iter.next();
            ta.emailed(now);
            travelApplicationDAO.updateTravelApplication(ta);
        }
        
    }

    private void notifyPurchaseOrderFinalConfirmer(Date now, Site site) {
        SiteMailReminder smr=(SiteMailReminder) site.getMailReminders().get
            (SiteMailReminderType.FINANCE_NOT_RESPOND_PURCHASE_ORDER);
        if(smr==null) return;
        if(smr.getMaxTime()<=0) return;
        List poList=purchaseOrderDAO.getFinanceNotRespondedPurchaseOrderList(now,smr);
        if(poList.isEmpty()) return;
        Map context=new HashMap();
        context.put("x_poList",poList);
        
        List userList=this.getUserList(site,"finalConfirmPurchaseOrder");
        for (Iterator iterator = userList.iterator(); iterator.hasNext();) {
            User user = (User) iterator.next();
            context.put("x_user",user);
            context.put("role", EmailManager.EMAIL_ROLE_FINANCE);
            this.insertEmail(site,user.getEmail(),"POFinalConfirmRemind.vm",context);
        }

        for (Iterator iter = poList.iterator(); iter.hasNext();) {
            PurchaseOrder po = (PurchaseOrder) iter.next();
            po.emailed(now);
            purchaseOrderDAO.updatePurchaseOrder(po);
        }
    }

    private void notifyPurchaseRequestPurchaser(Date now,Site site)
    {
        SiteMailReminder smr=(SiteMailReminder) site.getMailReminders().get
            (SiteMailReminderType.PURCHASER_NOT_RESPOND_PURCHASE_REQUEST);
        if(smr==null) return;
        if(smr.getMaxTime()<=0) return;
        List prList=purchaseRequestDAO.getPurchaserNotRespondedPurchaseRequestList(now,smr);
        if(prList.isEmpty()) return;
        Map userPrSetMap=new HashMap();
        
        for (Iterator iter = prList.iterator(); iter.hasNext();) {
            PurchaseRequest pr = (PurchaseRequest) iter.next();
            List userList=purchaseRequestDAO.getPurchaseRequestPurchaserList(pr);
            for (Iterator iterator = userList.iterator(); iterator.hasNext();) {
                User user = (User) iterator.next();
                if(userPrSetMap.get(user)==null) userPrSetMap.put(user,new HashSet());
                Set prSet=(Set) userPrSetMap.get(user);
                prSet.add(pr);
            }
            pr.emailed(now);
            purchaseRequestDAO.updatePurchaseRequest(pr);
        }
        
        for (Iterator iter = userPrSetMap.keySet().iterator(); iter.hasNext();) {
            User user = (User) iter.next();
            Map context=new HashMap();
            context.put("x_prList",userPrSetMap.get(user));
            context.put("x_user",user);
            context.put("role", EmailManager.EMAIL_ROLE_PURCHASER);
            this.insertEmail(site,user.getEmail(),"PurchaserRemind.vm",context);
        }
    }
    
    private void notifyPurchaseRequestApprover(Date now, Site site) {
        notifyApprover(now, site, SiteMailReminderType.APPROVER_NOT_RESPOND_PURCHASE_REQUEST, purchaseRequestApproveRequestDAO, "PRApproverRemind.vm", ApproverDelegateType.PURCHASE_REQUEST_APPROVER, EmailManager.EMAIL_ROLE_APPROVER);
    }
    
    private void notifyPurchaseOrderApprover(Date now, Site site) {
        notifyApprover(now, site, SiteMailReminderType.APPROVER_NOT_RESPOND_PURCHASE_ORDER, purchaseOrderApproveRequestDAO, "POApproverRemind.vm", ApproverDelegateType.PURCHASE_ORDER_APPROVER, EmailManager.EMAIL_ROLE_APPROVER);
    }

    private void notifyCapexRequestApprover(Date now, Site site) {
        
    }

    private void notifyTravelApplicationApprover(Date now, Site site) {
        notifyApprover(now, site, SiteMailReminderType.APPROVER_NOT_RESPOND_TRAVELING_APPLICATION, travelApplicationApproveRequestDAO, "TAApproverRemind.vm", ApproverDelegateType.TRAVEL_APPLICATION_APPROVER, EmailManager.EMAIL_ROLE_APPROVER);
    }

    private void notifyExpenseApprover(Date now, Site site) {
        notifyApprover(now, site, SiteMailReminderType.APPROVER_NOT_RESPOND_EXPENSE_REQUEST, expenseApproveRequestDAO, "ExpenseApproverRemind.vm", ApproverDelegateType.EXPENSE_APPROVER, EmailManager.EMAIL_ROLE_APPROVER);
    }

    private void notifyApprover(Date now, Site site, SiteMailReminderType reminderType, BaseApproveRequestDAO approveRequestDAO, String template, ApproverDelegateType delegateType, String role) {
        SiteMailReminder smr=(SiteMailReminder) site.getMailReminders().get(reminderType);
        if (smr == null || smr.getMaxTime() <= 0) {
            return;
        }
        long t = now.getTime();
        Map conditions = new HashMap();
        conditions.put(BaseApproveQueryCondition.SITE_ID_EQ, site.getId());
        conditions.put(BaseApproveQueryCondition.STATUS_EQ, ApproveStatus.WAITING_FOR_APPROVE);
        conditions.put(BaseApproveQueryCondition.YOUR_TURN_DATE_LE, new Date(t - smr.getDueDay() * 86400000));
        conditions.put(BaseApproveQueryCondition.LAST_EMAIL_DATE_LE, new Date(t - smr.getIntervalDay() * 86400000 + 3600000));
        conditions.put(BaseApproveQueryCondition.SENT_EMAIL_TIMES_LT, new Integer(smr.getMaxTime()));
        Map cache = new HashMap();
        for (Iterator itor = approveRequestDAO.getBaseApproveRequestList(conditions).iterator(); itor.hasNext(); ) {
            Object[] o = (Object[])itor.next();
            BaseApproveRequest bar = (BaseApproveRequest) o[0];
            notifyApprover(cache, delegateType, bar, o[1]);
            bar.setLastEmailDate(now);
            bar.setSentEmailTimes(bar.getSentEmailTimes() + 1);
            globalDAO.updateObject(bar);
        }
        Map context = new HashMap();
        for (Iterator itor = cache.keySet().iterator(); itor.hasNext(); ) {
            User approver = (User)itor.next();
            Object targetList = cache.get(approver);
            context.put("user", approver);
            context.put("targetList", targetList);
            context.put("role", role);
            this.insertEmail(site,approver.getEmail(), template, context);
        }
    }

    private void notifyApprover(Map cache, ApproverDelegateType type, BaseApproveRequest approveRequest, Object target) {
        User approver = approveRequest.getApprover();
        notifyApprover(cache, approver, target);
        Map conditions = new HashMap();
        conditions.put(ApproverDelegateQueryCondition.ORIGINALAPPROVER_ID_EQ, approver.getId());
        conditions.put(ApproverDelegateQueryCondition.TYPE_EQ, type);
        long now = this.getTodayTimeMillis();
        conditions.put(ApproverDelegateQueryCondition.FROMDATE_LT, new Date(now + 86400000));
        conditions.put(ApproverDelegateQueryCondition.TODATE_GE, new Date(now));
        for (Iterator itor = approverDelegateDAO.getApproverDelegateList(conditions, 0, -1, null, false).iterator(); itor.hasNext(); ) {
            ApproverDelegate delegate = (ApproverDelegate) itor.next();
            notifyApprover(cache, delegate.getDelegateApprover(), target);
        }
    }
    
    private void notifyApprover(Map cache, User approver, Object target) {
        List targetList = (List) cache.get(approver);
        if (targetList == null) {
            targetList = new ArrayList();
            cache.put(approver, targetList);
        }
        targetList.add(target);
    }
    
    private List getUserList(Site site,String functionName)
    {
        if (functionManager.isInitiated()) {
            Function f=functionManager.getFunction(functionName);
            return userDAO.getEnabledUserList(f,site);
        } else {
            return new ArrayList();
        }
    }

    

    public void setParameterDAO(ParameterDAO parameterDAO) {
        this.parameterDAO = parameterDAO;
    }

    public void setPurchaseRequestDAO(PurchaseRequestDAO purchaseRequestDAO) {
        this.purchaseRequestDAO = purchaseRequestDAO;
    }

    public void setPurchaseOrderDAO(PurchaseOrderDAO purchaseOrderDAO) {
        this.purchaseOrderDAO = purchaseOrderDAO;
    }

    public void setTravelApplicationDAO(TravelApplicationDAO travelApplicationDAO) {
        this.travelApplicationDAO = travelApplicationDAO;
    }

    public void setFunctionManager(FunctionManager functionManager) {
        this.functionManager = functionManager;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader=resourceLoader;
    }
    
    public void deleteEmailBatch(String refNo) {
        this.dao.deleteEmailBatch(refNo);
    }
    
    public void updateEmailBatch(EmailBatch emailBatch){
        this.dao.updateEmailBatch(emailBatch);
    }
    
    public EmailBatch findNotSendEmailBatchByRefNo(String refNo)
    {
        return this.dao.findNotSendEmailBatchByRefNo(refNo);
    }
}
