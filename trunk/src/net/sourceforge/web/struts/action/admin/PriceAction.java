/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.action.admin;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import net.sourceforge.model.admin.Hotel;
import net.sourceforge.model.admin.Price;
import net.sourceforge.model.admin.query.PriceQueryCondition;
import net.sourceforge.model.admin.query.PriceQueryOrder;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.model.metadata.HotelPromoteStatus;
import net.sourceforge.service.admin.HotelManager;
import net.sourceforge.service.admin.PriceManager;
import net.sourceforge.web.struts.action.BaseAction;
import net.sourceforge.web.struts.action.ServiceLocator;
import net.sourceforge.web.struts.form.admin.PriceQueryForm;
import com.shcnc.struts.action.ActionException;
import com.shcnc.struts.action.ActionUtils;
import com.shcnc.struts.form.BeanForm;
import com.shcnc.hibernate.PersistentEnum;


/**
 * the struts class for domain model price
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class PriceAction extends BaseAction {
	/**
     * the action method for listing Price of hotel
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    PriceManager fm =ServiceLocator.getPriceManager(request);

        PriceQueryForm queryForm = (PriceQueryForm) form;

        Map conditions = constructQueryMap(queryForm);

        int pageNo = ActionUtils.parseInt(queryForm.getPageNo()).intValue();
        int pageSize = ActionUtils.parseInt(queryForm.getPageSize()).intValue();
        if (pageSize == 0) {
            pageSize = 15;
            queryForm.setPageSize(String.valueOf(pageSize));
        }
        if (ActionUtils.parseInt(queryForm.getCount()) == null) {
            int count = fm.getPriceListCount(conditions);
            queryForm.setCount(String.valueOf(count));
        }
        int pageCount = queryForm.getPageCount();
        if (pageNo >= pageCount) {
            pageNo = pageCount - 1;
            queryForm.setPageNo(String.valueOf(pageNo));
        }
        
        List result=fm.getPriceList(
        	conditions, pageNo, pageSize, PriceQueryOrder.getEnum(queryForm.getOrder()), queryForm.isDescend());
        request.setAttribute("X_RESULTLIST", result);
        return mapping.findForward("page");
    }
    
    private Map constructQueryMap(PriceQueryForm queryForm) {
        Map conditions = new HashMap();
         /*id*/
		Integer id =
			ActionUtils.parseInt(queryForm.getId());
		if (id != null) 
		{
			conditions.put(PriceQueryCondition.ID_EQ,
				 id);
		}

		/*keyPropertyList*/

		/*kmtoIdList*/

		/*mtoIdList*/
		Integer hotel_id =
			ActionUtils.parseInt(queryForm.getHotel_id());
		if (hotel_id != null) 
		{
			conditions.put(PriceQueryCondition.HOTEL_ID_EQ,
				 hotel_id);
		}


  
		/*property*/
		String room = 
			queryForm.getRoom();
		if(room != null && room.trim().length()!=0)
		{
			conditions.put(PriceQueryCondition.ROOM_LIKE,
				 room);
		}
		String price = 
			queryForm.getPrice();
		if(price != null && price.trim().length()!=0)
		{
			conditions.put(PriceQueryCondition.PRICE_EQ,
				 price);
		}		
		Integer discount =
			ActionUtils.parseInt(queryForm.getDiscount());
		if (discount != null) 
		{
			conditions.put(PriceQueryCondition.DISCOUNT_EQ,
				 discount);
		}
		String description = 
			queryForm.getDescription();
		if(description != null && description.trim().length()!=0)
		{
			conditions.put(PriceQueryCondition.DESCRIPTION_LIKE,
				 description);
		}
		String enabled = 
			queryForm.getEnabled();
		if(enabled != null && enabled.trim().length()!=0)
		{
			conditions.put(PriceQueryCondition.ENABLED_EQ,
				 enabled);
		}		
        return conditions;
    }

	private void putEnumListToRequest(HttpServletRequest request) {
		request.setAttribute("X_ENABLEDDISABLEDLIST", PersistentEnum
				.getEnumList(EnabledDisabled.class));
	}


 	private Price getPriceFromRequest(HttpServletRequest request)
			throws Exception {
        Integer id = ActionUtils.parseInt(request.getParameter("id"));
		PriceManager priceManager = ServiceLocator.getPriceManager(request);
		Price price = priceManager.getPrice(id);
		if (price == null)
			throw new ActionException("price.notFound", id);
		return price;
	}
	

    /**
     * the action method for editing price
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Price price = getPriceFromRequest(request);    
        this.checkHotel(price.getHotel(),request);
		request.setAttribute("x_price",price);
        if (!isBack(request)) {
            BeanForm priceForm = (BeanForm) getForm("/updatePrice", request);
            priceForm.populate(price, BeanForm.TO_FORM);
        }
        putEnumListToRequest(request);
        return mapping.findForward("page");
    }

	/**
     * the action method for creating new price
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward newObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Hotel h=this.getHotelFromRequest(request);
		this.checkHotel(h,request);
        if (!isBack(request)) {
            Price price = new Price();
            price.setHotel(h);
            BeanForm priceForm = (BeanForm) getForm("/insertPrice", request);
            priceForm.populate(price, BeanForm.TO_FORM);
        }
        putEnumListToRequest(request);
        return mapping.findForward("page");
    }
	private void checkHotel(Hotel hotel,HttpServletRequest request ) throws Exception
	{
		if(this.isSite(request))
		{
			if(hotel.getPromoteStatus().equals(HotelPromoteStatus.GLOBAL))
			{
				throw new ActionException("hotel.error.siteEditGlobal");
			}
			else
			{
				this.checkSite(hotel.getSite(),request);
			}
		}
	}

    private Hotel getHotelFromRequest(HttpServletRequest request) {
    	Integer id = ActionUtils.parseInt(request.getParameter("hotel_id"));
		HotelManager hotelManager = ServiceLocator.getHotelManager(request);
		Hotel hotel = hotelManager.getHotel(id);
		if (hotel == null)
			throw new ActionException("hotel.notFound", id);
		return hotel;
	}

	/**
     * the action method for updating price
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Hotel h=this.getHotelFromRequest(request);
		this.checkHotel(h,request);
		
        BeanForm priceForm = (BeanForm) form;
        Price price = new Price();
        priceForm.populate(price, BeanForm.TO_BEAN);
        price.setHotel(h);
        
        PriceManager priceManager = ServiceLocator.getPriceManager(request);
        request.setAttribute("X_OBJECT", priceManager.updatePrice(price));
        request.setAttribute("X_ROWPAGE", "price/row.jsp");
        
        return mapping.findForward("success");
    }
    
    /**
     * the action method for insert new price to db 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Hotel h=this.getHotelFromRequest(request);
		this.checkHotel(h,request);
		
        BeanForm priceForm = (BeanForm) form;
        Price price = new Price();
        priceForm.populate(price, BeanForm.TO_BEAN);
        price.setHotel(h);
        
        PriceManager priceManager = ServiceLocator.getPriceManager(request);
		request.setAttribute("X_OBJECT", priceManager.insertPrice(price));
        request.setAttribute("X_ROWPAGE", "price/row.jsp");
        
        return mapping.findForward("success");
    }	


}