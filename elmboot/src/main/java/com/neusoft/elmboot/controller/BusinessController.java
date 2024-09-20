package com.neusoft.elmboot.controller;

import java.util.List;

import com.neusoft.elmboot.po.UserAndBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.elmboot.po.Business;
import com.neusoft.elmboot.service.BusinessService;

@RestController
@RequestMapping("/BusinessController")
public class BusinessController {
	
	@Autowired
	private BusinessService businessService;

	@RequestMapping("/listBusinessByOrderTypeId")
	public List<Business> listBusinessByOrderTypeId(Business business) throws Exception{
		return businessService.listBusinessByOrderTypeId(business.getOrderTypeId());
	}
	
	@RequestMapping("/getBusinessById")
	public Business getBusinessById(Business business) throws Exception{
		return businessService.getBusinessById(business.getBusinessId());
	}

	@RequestMapping("/listBusinessByBusinessNameOrFoodName")
	public List<Business> listBusinessByBusinessNameOrFoodName(Business business) throws Exception{
		return businessService.listBusinessByBusinessNameOrFoodName(business.getBusinessName());
	}

	@RequestMapping("/verifyBusinessId")
	public boolean verifyBusinessId(UserAndBusiness userAndBusiness) throws Exception{

		return businessService.verifyBusinessId(userAndBusiness);
	}


}
