package com.vic.ck.console.platform.controller;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vic.base.pager.CommonLookup;
import com.vic.base.pager.PageInfo;
import com.vic.base.pager.Remind;
import com.vic.ck.console.BaseConsoleController;
import com.vic.ck.console.platform.service.PlatformMerchantCategoryService;
import com.vic.ck.entity.PlatformMerchantCategory;



/**
 * 商家分类
 * 
 */
@Controller
@RequestMapping("/console/platform/merchantCategory")
public class PlatformMerchantCategoryController extends BaseConsoleController{
	

	@Resource
	private PlatformMerchantCategoryService platformMerchantCategoryService;
	
    /**列表页*/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String page(Model model) {
		PageInfo<PlatformMerchantCategory> pager =  platformMerchantCategoryService.list(getLookup());
		model.addAttribute("pager",pager);
		return "console/platform/merchantCategory/list";
	}
	
	
	/**查询*/
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String search(CommonLookup lookup) {
		setLookup(lookup);
		return "redirect:/console/platform/merchantCategory/";
	}
	
	/**前往新增页面*/
	@RequestMapping( value = "/0", method = RequestMethod.GET)
	public String add(Model model){
		return "console/platform/merchantCategory/form"; 
	}
	
	/**前往修改页面*/
	@RequestMapping( value = "/{id}", method = RequestMethod.GET)
	public String editor(@PathVariable int id, Model model){
		PlatformMerchantCategory entity = platformMerchantCategoryService.findById(id);
		model.addAttribute("entity", entity);
		return "console/platform/merchantCategory/form"; 
	}
	
	/**保存*/
	@RequestMapping( value = "/{id}", method = RequestMethod.POST)
	public String save(@PathVariable int id, PlatformMerchantCategory entity, RedirectAttributes attr){
		entity.setId(id);
		if(id == 0) {//to add
			platformMerchantCategoryService.insert(entity);
			attr.addFlashAttribute(Remind.success());
		}else if(id > 0 ){ //to update
			platformMerchantCategoryService.update(entity);
			attr.addFlashAttribute(Remind.success());
		}else {
			attr.addFlashAttribute(Remind.danger());
		}
		return "redirect:/console/platform/merchantCategory/";
	}
	
	/**单个删除*/
	@RequestMapping( value = "/{id}/remove", method = RequestMethod.GET)
	public String delete(@PathVariable int id, RedirectAttributes attributes){
		platformMerchantCategoryService.delete(id);
		attributes.addFlashAttribute(Remind.success());
		return "redirect:/console/platform/merchantCategory/";
	}
	
	/**批量删除*/
	@RequestMapping( value = "/remove", method = RequestMethod.POST)
	public String batchDelete(int[] ids, RedirectAttributes attributes){
		platformMerchantCategoryService.delete(ids);
		attributes.addFlashAttribute(Remind.success());
		return "redirect:/console/platform/merchantCategory/";
	}
	
}
