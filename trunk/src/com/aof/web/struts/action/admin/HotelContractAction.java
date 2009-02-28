/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.action.admin;

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

import com.aof.model.admin.Hotel;
import com.aof.model.admin.HotelContract;
import com.aof.model.admin.query.HotelContractQueryCondition;
import com.aof.model.admin.query.HotelContractQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.model.metadata.HotelPromoteStatus;
import com.aof.service.admin.HotelContractManager;
import com.aof.service.admin.HotelManager;
import com.aof.web.struts.action.BaseAction;
import com.aof.web.struts.action.ServiceLocator;
import com.aof.web.struts.form.admin.HotelContractQueryForm;
import com.shcnc.hibernate.PersistentEnum;
import com.shcnc.servlet.DownloadUploadHelper;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;


/**
 * the action class for domain model hotel contract
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class HotelContractAction extends BaseAction {
    
    /**
     * the action method for searching hotel contract
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HotelContractQueryForm queryForm = (HotelContractQueryForm) form;
        getHotelFromRequest(request);

        Map conditions = constructQueryMap(queryForm);

        HotelContractManager fm = ServiceLocator.getHotelContractManager(request);
        List result = fm.getHotelContractList(conditions, 0, -1, HotelContractQueryOrder.ID, false);
        request.setAttribute("X_RESULTLIST", result);
        return mapping.findForward("page");
    }

    private Map constructQueryMap(HotelContractQueryForm queryForm) {
        Map conditions = new HashMap();

        Integer id = ActionUtils.parseInt(queryForm.getId());
        if (id != null) {
            conditions.put(HotelContractQueryCondition.ID_EQ, id);
        }

        Integer hotel_id = ActionUtils.parseInt(queryForm.getHotel_id());
        if (hotel_id != null) {
            conditions.put(HotelContractQueryCondition.HOTEL_ID_EQ, hotel_id);
        }

        String fileName = queryForm.getFileName();
        if (fileName != null && fileName.trim().length() != 0) {
            conditions.put(HotelContractQueryCondition.FILENAME_LIKE, fileName);
        }

        String description = queryForm.getDescription();
        if (description != null && description.trim().length() != 0) {
            conditions.put(HotelContractQueryCondition.DESCRIPTION_LIKE, description);
        }

        String uploadDate = queryForm.getUploadDate();
        if (uploadDate != null && uploadDate.trim().length() != 0) {
            conditions.put(HotelContractQueryCondition.UPLOADDATE_EQ, uploadDate);
        }
        return conditions;
    }

    private void putEnumListToRequest(HttpServletRequest request) {
        request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum.getEnumList(EnabledDisabled.class));
    }

    private HotelContract getHotelContractFromRequest(HttpServletRequest request) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
        HotelContractManager hotelContractManager = ServiceLocator.getHotelContractManager(request);
        HotelContract hotelContract = hotelContractManager.getHotelContract(id);
        if (hotelContract == null)
            throw new ActionException("hotelContract.notFound", id);
        return hotelContract;
    }

    /**
     * the action method for editing hotel contract
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HotelContract hotelContract = getHotelContractFromRequest(request);
        Hotel hotel = hotelContract.getHotel();

        this.checkHotel(hotel, request);

        request.setAttribute("x_hotelContract", hotelContract);

        if (!isBack(request)) {
            BeanForm hotelContractForm = (BeanForm) getForm("/updateHotelContract", request);
            hotelContractForm.populate(hotelContract, BeanForm.TO_FORM);
        }
        putEnumListToRequest(request);
        return mapping.findForward("page");
    }

    private void checkHotel(Hotel hotel, HttpServletRequest request) throws Exception {
        if (this.isSite(request)) {
            if (hotel.getPromoteStatus().equals(HotelPromoteStatus.GLOBAL)) {
                throw new ActionException("hotel.error.siteEditGlobal");
            } else {
                this.checkSite(hotel.getSite(), request);
            }
        }
    }

    /**
     * the action method for creating hotel contract
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Hotel hotel = this.getHotelFromRequest(request);

        this.checkHotel(hotel, request);

        if (!isBack(request)) {
            HotelContract hotelContract = new HotelContract();
            hotelContract.setHotel(hotel);
            BeanForm hotelContractForm = (BeanForm) getForm("/insertHotelContract", request);
            hotelContractForm.populate(hotelContract, BeanForm.TO_FORM);
        }
        putEnumListToRequest(request);
        return mapping.findForward("page");
    }

    private Hotel getHotelFromRequest(HttpServletRequest request) throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("hotel_id"));
        HotelManager hotelManager = ServiceLocator.getHotelManager(request);
        Hotel hotel = hotelManager.getHotel(id);
        if (hotel == null)
            throw new ActionException("hotel.notFound", id);
        return hotel;
    }

    /**
     * the action method for updating hotel contract
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Hotel hotel = this.getHotelFromRequest(request);
        this.checkHotel(hotel, request);

        BeanForm hotelContractForm = (BeanForm) form;
        HotelContract hotelContract = new HotelContract();
        hotelContractForm.populate(hotelContract, BeanForm.TO_BEAN);

        HotelContractManager hotelContractManager = ServiceLocator.getHotelContractManager(request);
        request.setAttribute("X_OBJECT", hotelContractManager.updateHotelContract(hotelContract));
        request.setAttribute("X_ROWPAGE", "hotelContract/row.jsp");

        return mapping.findForward("success");
    }

    /**
     * the action method for downloading hotel contract
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HotelContract hotelContract = this.getHotelContractFromRequest(request);
        Hotel hotel = hotelContract.getHotel();
        this.checkHotel(hotel, request);

        InputStream in = ServiceLocator.getHotelContractManager(request).getHotelContractContent(hotelContract.getId());
        if (in != null) {
            try {
                if (hotelContract.getFileSize() == 0) {
                    throw new ActionException("hotelContract.error.fileSize.zero");
                } else {
                    DownloadUploadHelper.download(
                            in, 
                            hotelContract.getFileName(), 
                            DownloadUploadHelper.getMime(hotelContract.getFileName()), hotelContract.getFileSize(),
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
     * the action method for inserting new hotel contract to db
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Hotel hotel = this.getHotelFromRequest(request);
        this.checkHotel(hotel, request);

        BeanForm hotelContractForm = (BeanForm) form;
        HotelContract hotelContract = new HotelContract();
        hotelContractForm.populate(hotelContract, BeanForm.TO_BEAN);
        hotelContract.setHotel(hotel);

        FormFile file = (FormFile) hotelContractForm.get("fileContent");
        hotelContract.setFileName(file.getFileName());

        HotelContractManager hotelContractManager = ServiceLocator.getHotelContractManager(request);
        HotelContract newHc = null;
        if (file.getFileSize() > 0) {
            hotelContract.setFileSize(file.getFileSize());
            newHc = hotelContractManager.insertHotelContract(hotelContract, file.getInputStream());
        } else {
            throw new ActionException("hotelContract.error.fileSize.zero");
        }
        request.setAttribute("X_OBJECT", newHc);
        request.setAttribute("X_ROWPAGE", "hotelContract/row.jsp");

        return mapping.findForward("success");
    }

}