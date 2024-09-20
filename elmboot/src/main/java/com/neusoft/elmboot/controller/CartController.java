package com.neusoft.elmboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.elmboot.po.Cart;
import com.neusoft.elmboot.service.CartService;

@RestController
@RequestMapping("/CartController")
public class CartController {

	@Autowired
	private CartService cartService;

	//userId:this.user.userId,
	//businessId:this.businessId
	@RequestMapping("/listCart")
	public List<Cart> listCart(Cart cart) throws Exception{
		return cartService.listCart(cart);
	}
	
	@RequestMapping("/saveCart")
	public int saveCart(Cart cart) throws Exception{
		return cartService.saveCart(cart);
	}
	
	@RequestMapping("/updateCart")
	public int updateCart(Cart cart) throws Exception{
		return cartService.updateCart(cart);
	}
	
	@RequestMapping("/removeCart")
	public int removeCart(Cart cart) throws Exception{
		return cartService.removeCart(cart);
	}

    //businessId:this.businessId,
    //userId:this.user.userId,
    //foodId:this.foodArr[index].foodId

	//businessId:this.businessId,
	//userId:this.user.userId,
	@RequestMapping("/clearCart")
	public int clearCart(Cart cart) throws Exception{
		List<Cart> list = cartService.listCart(cart);
		for(Cart c:list){
			if(cartService.removeCart(c)==0){
				return 0;
			}
		}
		return 1;
	}

}
