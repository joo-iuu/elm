package com.neusoft.elmboot.service.impl;

import java.util.List;

import com.neusoft.elmboot.po.UserAndBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.elmboot.mapper.BusinessMapper;
import com.neusoft.elmboot.po.Business;
import com.neusoft.elmboot.service.BusinessService;

@Service
public class BusinessServiceImpl implements BusinessService{
	
	@Autowired
	private BusinessMapper businessMapper;

	@Override
	public List<Business> listBusinessByOrderTypeId(Integer orderTypeId) {
		return businessMapper.listBusinessByOrderTypeId(orderTypeId);
	}
	
	@Override
	public Business getBusinessById(Integer businessId) {
		return businessMapper.getBusinessById(businessId);
	}

	@Override
	public  List<Business> listBusinessByBusinessNameOrFoodName(String businessNameOrFoodName) {
		return businessMapper.listBusinessByBusinessNameOrFoodName(businessNameOrFoodName);
	}

	@Override
	public Boolean verifyBusinessId(UserAndBusiness userAndBusiness){
		return businessMapper.verifyBusinessId(userAndBusiness)!=null;
	}
}
