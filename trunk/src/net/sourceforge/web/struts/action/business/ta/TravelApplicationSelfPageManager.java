/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.action.business.ta;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.DynaBean;

import net.sourceforge.model.admin.City;
import net.sourceforge.model.admin.Country;
import net.sourceforge.model.admin.Province;
import net.sourceforge.model.admin.TravelGroup;
import net.sourceforge.model.admin.TravelGroupDetail;
import net.sourceforge.model.admin.User;
import net.sourceforge.model.admin.UserDepartment;
import net.sourceforge.model.admin.UserSite;
import net.sourceforge.service.admin.CountryManager;
import net.sourceforge.service.admin.HotelManager;
import net.sourceforge.service.admin.TravelGroupManager;
import net.sourceforge.service.admin.UserManager;
import net.sourceforge.web.struts.action.ServiceLocator;
import com.shcnc.struts.form.DynaBeanComboBox;

/**
 * 编辑他人的出差申请的页面管理器(combobox 联动)
 * @author shi1
 * @version 1.0 (Nov 25, 2005)
 */
public class TravelApplicationSelfPageManager {

	/**
	 * @param form
     *      the form for the page
	 * @param user
     *      requestor
	 * @param request
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public TravelApplicationSelfPageManager(DynaBean form, User user,
			HttpServletRequest request) throws Exception {
		this.request = request;
		this.user = user;

		this.comboUserSite = new DynaBeanComboBox("site.id", "site.name", form,
				"department_site_id");
		this.comboUserDepartment = new DynaBeanComboBox("department.id",
				"department.name", form, "department_id");

		this.comboToCountry = new DynaBeanComboBox("id", "engName", form,
				"toCity_province_country_id");
		this.comboFromCountry = new DynaBeanComboBox("id", "engName", form,
				"fromCity_province_country_id");

		this.comboToProvince = new DynaBeanComboBox("id", "engName", form,
				"toCity_province_id");
		this.comboFromProvince = new DynaBeanComboBox("id", "engName", form,
				"fromCity_province_id");

		this.comboToCity = new DynaBeanComboBox("id", "engName", form,
				"toCity_id");
		this.comboFromCity = new DynaBeanComboBox("id", "engName", form,
				"fromCity_id");

	}

	/**
     * 处理页面
	 * @throws Exception
	 */
	public void process() throws Exception {
		this.processComboSite();
		this.processComboDepartment();

		this.setDefaultFromCity();
		this.setDefaultToCountry();
        
		this.processComboCountry();
        
		this.processComboToProvince();
		this.processComboFromProvince();
        
		this.processComboToCity();
        this.processComboFromCity();
        
		this.processHotelRoom();
	}
    
    private List getEnabledCountryProvinceCityList() {
        CountryManager cm = ServiceLocator.getCountryManager(request);
        return cm.listEnabledCountryProvinceCity();
    }

    private void setDefaultToCountry() {
		if (this.userDepartment != null && this.comboToCountry.isEmpty()) {
			City defaultCity = this.userSite.getSite().getCity();
			if (defaultCity != null) {
				this.comboToCountry.setValue(defaultCity.getProvince()
						.getCountry().getId().toString());
			}
		}

	}

	private void setDefaultFromCity() {
		if (this.userDepartment != null && this.comboFromCountry.isEmpty()) {
			City defaultCity = this.userSite.getSite().getCity();
			if (defaultCity != null) {
				this.comboFromCountry.setValue(defaultCity.getProvince()
						.getCountry().getId().toString());
				this.comboFromProvince.setValue(defaultCity.getProvince()
						.getId().toString());
				this.comboFromCity.setValue(defaultCity.getId().toString());
			}
		}
	}

	private void processComboFromCity() throws IllegalAccessException,
			Exception{
		this.processComboCity(this.comboFromCity, this.fromProvince);

		request.setAttribute("x_fromCityList", comboFromCity.getList());
	}

	private void processComboToCity() throws IllegalAccessException,
			Exception{
		this.processComboCity(this.comboToCity, this.toProvince);
		this.toCity = (City) this.comboToCity.getSelectedItem();
		request.setAttribute("x_toCityList", comboToCity.getList());
	}

	private void processComboCity(DynaBeanComboBox comboCity, Province province)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		if (province == null) {
			comboCity.setEmptyList();
		} else {
			comboCity.setList(province.getEnabledCityList());
		}
	}

	private void processComboFromProvince() throws IllegalAccessException,
			Exception{
		this.processComboProvince(this.comboFromProvince, this.fromCountry);
		this.fromProvince = (Province) this.comboFromProvince.getSelectedItem();
		request.setAttribute("x_fromProvinceList", comboFromProvince.getList());

	}

	private void processComboToProvince() throws IllegalAccessException,
			Exception{
		this.processComboProvince(this.comboToProvince, this.toCountry);
		this.toProvince = (Province) this.comboToProvince.getSelectedItem();
		request.setAttribute("x_toProvinceList", comboToProvince.getList());
	}

	private void processComboProvince(DynaBeanComboBox comboPrivince,
			Country country) throws IllegalAccessException,
			Exception{
		if (country == null) {
			comboPrivince.setEmptyList();
		} else {
			comboPrivince.setList(country.getEnabledProvinceList());
		}
	}

	private void processComboCountry() throws IllegalAccessException,
			Exception{
        List countryList=this.getEnabledCountryProvinceCityList();
        request.setAttribute("x_countryList", countryList);
        
		comboToCountry.setList(countryList);
		comboFromCountry.setList(countryList);

		this.toCountry = (Country) this.comboToCountry.getSelectedItem();
		this.fromCountry = (Country) this.comboFromCountry.getSelectedItem();
	}

	private void processHotelRoom() {
		if (userSite != null && toCity != null) {
			TravelGroup tg = userSite.getTravelGroup();
			TravelGroupManager tgm = ServiceLocator
					.getTravelGroupManager(request);
			TravelGroupDetail tgd = tgm.getHotelTravelGroupDetail(tg);

			boolean isLocal = false;
			if (userSite.getSite().getCity() != null) {
				isLocal=userSite.getSite().getCity().getProvince().getCountry().equals(
						toCity.getProvince().getCountry());
			}

			HotelManager hm = ServiceLocator.getHotelManager(request);
			List hotelList = hm.getEnabledHotelRoomList(toCity, isLocal, tgd);
			request.setAttribute("x_hotelList", hotelList);

		} else {
			request.setAttribute("x_hotelList", new ArrayList());

		}
	}

	private void processComboDepartment() throws Exception {
		if (userSite == null) {
			comboUserDepartment.setEmptyList();
		} else {
			UserManager um = ServiceLocator.getUserManager(request);
			this.comboUserDepartment.setList(um.getEnabledUserDepartmentList(
					user, userSite.getSite()));
		}
		this.userDepartment = (UserDepartment) this.comboUserDepartment
				.getSelectedItem();

		request.setAttribute("x_userDepartmentList", this.comboUserDepartment
				.getList());

	}

	private void processComboSite() throws Exception {
		UserManager um = ServiceLocator.getUserManager(request);

		this.comboUserSite.setList(um.getEnabledUserSiteList(user));
		this.userSite = (UserSite) this.comboUserSite.getSelectedItem();

		request.setAttribute("x_userSiteList", this.comboUserSite.getList());
	}

	private DynaBeanComboBox comboUserSite;

	private DynaBeanComboBox comboUserDepartment;

	private DynaBeanComboBox comboToCountry;

	private DynaBeanComboBox comboToProvince;

	private DynaBeanComboBox comboToCity;

	private DynaBeanComboBox comboFromCountry;

	private DynaBeanComboBox comboFromProvince;

	private DynaBeanComboBox comboFromCity;

	private HttpServletRequest request;

	private User user = null;

	private UserSite userSite = null;

	private UserDepartment userDepartment = null;

	private Country toCountry = null;

	private Country fromCountry = null;

	private Province fromProvince = null;

	private Province toProvince = null;

	private City toCity = null;
}
