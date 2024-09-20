package com.neusoft.elmboot.service;

import java.util.List;

import com.neusoft.elmboot.po.Business;
import com.neusoft.elmboot.po.UserAndBusiness;

public interface BusinessService {

	public List<Business> listBusinessByOrderTypeId(Integer orderTypeId);
	public Business getBusinessById(Integer businessId);
	public List<Business> listBusinessByBusinessNameOrFoodName(String businessNameOrFoodName);
	public Boolean verifyBusinessId(UserAndBusiness userAndBusiness) ;
}
