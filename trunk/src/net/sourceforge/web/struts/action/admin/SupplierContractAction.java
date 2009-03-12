/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.action.admin;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import net.sourceforge.model.admin.Supplier;
import net.sourceforge.model.admin.SupplierContract;
import net.sourceforge.model.admin.query.SupplierContractQueryCondition;
import net.sourceforge.model.admin.query.SupplierContractQueryOrder;
import net.sourceforge.model.metadata.SupplierPromoteStatus;
import net.sourceforge.service.admin.SupplierContractManager;
import net.sourceforge.service.admin.SupplierManager;
import net.sourceforge.web.struts.action.BaseAction;
import net.sourceforge.web.struts.action.ServiceLocator;
import net.sourceforge.web.struts.form.admin.SupplierContractQueryForm;
import com.shcnc.servlet.DownloadUploadHelper;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;

/**
 * SupplierContract的Action类
 * @author ych
 * @version 1.0 (Nov 14, 2005)
 */
public class SupplierContractAction extends BaseAction {
    /**
     * 查询SupplierContract
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		SupplierContractQueryForm queryForm = (SupplierContractQueryForm) form;

        Map conditions = constructQueryMap(queryForm);

        SupplierContractManager scm = ServiceLocator.getSupplierContractManager(request);
        List result = scm.getSupplierContractList(conditions, 0, -1, SupplierContractQueryOrder.ID, false);
        request.setAttribute("X_RESULTLIST", result);
        return mapping.findForward("page");
	    
    }
    
    /**
     * 构建查询map
     * @param queryForm
     * @return
     */
    private Map constructQueryMap(SupplierContractQueryForm queryForm) {
        Map conditions = new HashMap();
		Integer id =
			ActionUtils.parseInt(queryForm.getId());
		if (id != null) 
		{
			conditions.put(SupplierContractQueryCondition.ID_EQ,
				 id);
		}

		String Supplier_id = queryForm.getSupplier_id();
		if(Supplier_id != null && Supplier_id.trim().length()!=0)
		{
			conditions.put(SupplierContractQueryCondition.SUPPLIER_ID_EQ,Supplier_id);
		}

        return conditions;
    }


    private void checkSupplier(Supplier supplier, HttpServletRequest request) throws Exception {
        if (this.isSite(request)) {
            if (supplier.getPromoteStatus().equals(SupplierPromoteStatus.GLOBAL)) {
                throw new ActionException("supplier.error.siteEditGlobal");
            } else {
                this.checkSite(supplier.getSite(), request);
            }
        }
        if(this.isGlobal(request)) {
            if (!supplier.getPromoteStatus().equals(SupplierPromoteStatus.GLOBAL))
                throw new ActionException("supplier.error.globalEditSite");
        }
    }
    
 	private SupplierContract getSupplierContractFromRequest(HttpServletRequest request)
			throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
		SupplierContractManager supplierContractManager =ServiceLocator.getSupplierContractManager(request);
		SupplierContract supplierContract = supplierContractManager.getSupplierContract(id);
		if (supplierContract == null)
			throw new ActionException("supplierContract.notFound", id);
		return supplierContract;
	}
 	
 	private Supplier getSupplierFromRequest(HttpServletRequest request) throws Exception {       
        Integer id = new Integer(request.getParameter("supplier_id"));
        SupplierManager supplierManager = ServiceLocator.getSupplierManager(request);
        Supplier supplier = supplierManager.getSupplier(id);
        if (supplier == null)
            throw new ActionException("supplier.notFound", id);
        return supplier;
    }
	

    /**
     * 编辑SupplierContract
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	SupplierContract supplierContract = getSupplierContractFromRequest(request);
        Supplier supplier = supplierContract.getSupplier();
        this.checkSupplier(supplier, request);
        request.setAttribute("x_supplierContract", supplierContract);

        if (!isBack(request)) {
            BeanForm supplierContractForm = (BeanForm) getForm("/updateSupplierContract", request);
            supplierContractForm.populate(supplierContract, BeanForm.TO_FORM);
        }
        
        return mapping.findForward("page");
    	
    }

    /**
     * 新增SupplierContract
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Supplier supplier = this.getSupplierFromRequest(request);

        this.checkSupplier(supplier, request);

        if (!isBack(request)) {
            SupplierContract supplierContract = new SupplierContract();
            supplierContract.setSupplier(supplier);
            BeanForm supplierContractForm = (BeanForm) getForm("/insertSupplierContract", request);
            supplierContractForm.populate(supplierContract, BeanForm.TO_FORM);
        }
        
        return mapping.findForward("page");
    }

    /**
     * 更新SupplierContract
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	Supplier supplier = this.getSupplierFromRequest(request);
        this.checkSupplier(supplier, request);

        BeanForm supplierContractForm = (BeanForm) form;
        SupplierContract supplierContract = new SupplierContract();
        supplierContractForm.populate(supplierContract, BeanForm.TO_BEAN);

        SupplierContractManager supplierContractManager = ServiceLocator.getSupplierContractManager(request);
        request.setAttribute("X_OBJECT", supplierContractManager.updateSupplierContract(supplierContract));
        request.setAttribute("X_ROWPAGE", "supplierContract/row.jsp");

        return mapping.findForward("success");
    }
    

    /**
     * 下载SupplierContract
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SupplierContract supplierContract = this.getSupplierContractFromRequest(request);
        //Supplier supplier = supplierContract.getSupplier();
        //this.checkSupplier(supplier, request);

        InputStream in = ServiceLocator.getSupplierContractManager(request).getSupplierContractContent(supplierContract.getId());
        if (in != null) {
            try {
                if (supplierContract.getFileSize() == 0) {
                    throw new ActionException("supplierContract.error.fileSize.zero");
                } else {
                    DownloadUploadHelper.download(
                            in, 
                            supplierContract.getFileName(), 
                            DownloadUploadHelper.getMime(supplierContract.getFileName()), supplierContract.getFileSize(),
                            request,
                            response,
                            true);
                }
            } finally {
                in.close();
            }
        }
        return null;
    }

    /**
     * 插入新增的SupplierContract
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Supplier supplier = this.getSupplierFromRequest(request);
        this.checkSupplier(supplier, request);

        BeanForm supplierContractForm = (BeanForm) form;
        SupplierContract supplierContract = new SupplierContract();
        supplierContractForm.populateToBean(supplierContract,request);
        supplierContract.setSupplier(supplier);

        FormFile file = (FormFile) supplierContractForm.get("fileContent");
        supplierContract.setFileName(file.getFileName());

        SupplierContractManager supplierContractManager = ServiceLocator.getSupplierContractManager(request);
        SupplierContract newHc = null;
        if (file.getFileSize() > 0) {
            supplierContract.setFileSize(file.getFileSize());
            newHc = supplierContractManager.insertSupplierContract(supplierContract, file.getInputStream());
        } else {
            throw new ActionException("supplierContract.error.fileSize.zero");
        }
        request.setAttribute("X_OBJECT", newHc);
        request.setAttribute("X_ROWPAGE", "supplierContract/row.jsp");

        return mapping.findForward("success");
    }

}