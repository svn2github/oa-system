/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.action.admin;

import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import net.sourceforge.model.admin.MetadataDetail;
import net.sourceforge.model.metadata.MetadataType;
import net.sourceforge.service.admin.MetadataManager;
import net.sourceforge.utils.SessionTempFile;
import net.sourceforge.web.struts.action.BaseAction;
import net.sourceforge.web.struts.form.BaseSessionQueryForm;
import net.sourceforge.web.struts.form.admin.MetadataForm;
import com.shcnc.hibernate.PersistentEnum;

import com.shcnc.utils.BeanUtils;
import com.shcnc.utils.ExportUtil;
import com.shcnc.utils.Exportable;
import net.sourceforge.web.struts.action.ServiceLocator;

/**
 * Metadata的Action类
 * @author ych
 * @version 1.0 (Nov 10, 2005)
 */
public class MetadataAction extends BaseAction {
    /**
     * 查询所有Metadata
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		MetadataManager mm = ServiceLocator.getMetadataManager(request);
		BaseSessionQueryForm queryForm = (BaseSessionQueryForm) form;
        
		
		String exportType = queryForm.getExportType();
        if (exportType != null && exportType.length() > 0) {
            List data =mm.getMetadataList(0,-1);
            int index = SessionTempFile.createNewTempFile(request);
            String fileName = "metadata";
            String suffix = ExportUtil.export(exportType, data, request, new FileOutputStream(SessionTempFile.getTempFile(index, request)), new Exportable() {

                        public void exportHead(List row, HttpServletRequest request) throws Exception {
                            MessageResources messages = getResources(request);
                            row.add(messages.getMessage(getLocale(request), "metadata.code"));
                            row.add(messages.getMessage(getLocale(request), "metadata.description"));
                        }

                        public void exportRow(List row, Object data, HttpServletRequest request) throws Exception {
                            row.add(BeanUtils.getProperty(data, "id"));
                            row.add(BeanUtils.getProperty(data, "description"));
                        }
                    });
            return new ActionForward("download/" + index + "/" + URLEncoder.encode(fileName, "UTF-8") + '.' + suffix, true);
        }
        
        
        if(queryForm.isFirstInit())
		{
			queryForm.init(mm.getMetadataListCount());
		}
		else
		{
			queryForm.init();
		}
		int pageNo=queryForm.getPageNoAsInt();
		int pageSize=queryForm.getPageSizeAsInt();
		
        List result=mm.getMetadataList(pageNo,pageSize);
        
        request.setAttribute("X_RESULTLIST", result);
        return mapping.findForward("page");
    }

	/**
	 * 编辑metadataDetail
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MetadataForm mForm=(MetadataForm)form;
		MetadataType mt = (MetadataType) PersistentEnum.fromEnumCode(MetadataType.class, new Integer(mForm.getId()));
		mForm.setDescription(mt.getLabel());
        request.setAttribute("X_METADATADETAILLIST", PersistentEnum.getEnumList(mt.getDetailClass()));
        return mapping.findForward("page");
    }
	/**
     * 更新metadataDetail
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MetadataForm mForm=(MetadataForm)form;
		MetadataType mt = (MetadataType) PersistentEnum.fromEnumCode(MetadataType.class, new Integer(mForm.getId()));
		mt.setLabel(mForm.getDescription());
		MetadataManager mm =ServiceLocator.getMetadataManager(request);
		mm.saveMetadata(mt);
		
		String detailId[]=request.getParameterValues("detailId");
        String engFull[]=request.getParameterValues("engFull");
        String chnFull[]=request.getParameterValues("chnFull");
        String engShort[]=request.getParameterValues("engShort");
        String chnShort[]=request.getParameterValues("chnShort");
        String color[]=request.getParameterValues("color");
        for (int index=0;index<detailId.length;index++) {
        	MetadataDetail md = mm.getMetadataDetail(new Integer(detailId[index]),new Integer(mForm.getId()));
        	md.setEngDescription(engFull[index]);
        	md.setEngShortDescription(engShort[index]);
        	md.setChnDescription(chnFull[index]);
        	md.setChnShortDescription(chnShort[index]);
        	md.setColor(color[index]);
        	mm.saveMetadataDetail(md);
        }
		final ActionForward forward=new ActionForward();
        forward.setRedirect(true);
        forward.setPath("editMetadata.do?id="+mt.getEnumCode().toString());
        return forward;
    }
	
}