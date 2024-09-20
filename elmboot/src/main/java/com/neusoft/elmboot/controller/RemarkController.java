package com.neusoft.elmboot.controller;

import com.neusoft.elmboot.po.Remark;
import com.neusoft.elmboot.service.RemarkService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/RemarkController")
public class RemarkController {

    @Autowired
    private RemarkService remarkService;

    //需要businessId
    @RequestMapping("/listRemarksByBussinessId")
    public List<Remark> listRemarksByBussinessId(Remark remark){
        return remarkService.listRemarksByBussinessId(remark.getBusinessId());
    }

    //需要userId,businessId,userName,remark
    @RequestMapping("/saveRemarks")
    public int saveRemarks(Remark remark) {
        return remarkService.saveRemarks(remark);
    }

    //需要userId
    @RequestMapping("/removeAllRemarksByUserId")
    public List<Remark> removeAllRemarksByUserId(String userId){
        return remarkService.removeAllRemarksByUserId(userId);
    }

    @RequestMapping("/removeOneRemarksByUserId")
    public List<Remark> removeOneRemarksByUserId(String userId,  int remarkId){
        return remarkService.removeOneRemarksByUserId(userId, remarkId);
    }
}
