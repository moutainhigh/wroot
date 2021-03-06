package com.vic.ck.console.platform.controller;

import javax.annotation.Resource;

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
import com.vic.ck.console.platform.service.PlatformBootPageService;
import com.vic.ck.entity.PlatformBootPage;



/**
 * 系统启动页
 * 
 */
@Controller
@RequestMapping("/console/platform/boot")
public class PlatformBootPageController extends BaseConsoleController{
	

	@Resource
	private PlatformBootPageService platformBootPageService;
	
    /**列表页*/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String page(Model model) {
		PageInfo<PlatformBootPage> pager =  platformBootPageService.list(getLookup());
		model.addAttribute("pager",pager);
		return "console/platform/boot/list";
	}
	
	
	/**查询*/
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String search(CommonLookup lookup) {
		setLookup(lookup);
		return "redirect:/console/platform/boot/";
	}
	
	/**前往新增页面*/
	@RequestMapping( value = "/0", method = RequestMethod.GET)
	public String add(Model model){
		return "console/platform/boot/form"; 
	}
	
	/**前往修改页面*/
	@RequestMapping( value = "/{id}", method = RequestMethod.GET)
	public String editor(@PathVariable int id, Model model){
		PlatformBootPage entity = platformBootPageService.findById(id);
		model.addAttribute("entity", entity);
		return "console/platform/boot/form"; 
	}
	
	/**保存*/
	@RequestMapping( value = "/{id}", method = RequestMethod.POST)
	public String save(@PathVariable int id, PlatformBootPage entity, RedirectAttributes attr){
		entity.setId(id);
		if(id == 0) {//to add
			platformBootPageService.insert(entity);
			attr.addFlashAttribute(Remind.success());
		}else if(id > 0 ){ //to update
			platformBootPageService.update(entity);
			attr.addFlashAttribute(Remind.success());
		}else {
			attr.addFlashAttribute(Remind.danger());
		}
		return "redirect:/console/platform/boot/";
	}
	
	/**单个删除*/
	@RequestMapping( value = "/{id}/remove", method = RequestMethod.GET)
	public String delete(@PathVariable int id, RedirectAttributes attributes){
		platformBootPageService.delete(id);
		attributes.addFlashAttribute(Remind.success());
		return "redirect:/console/platform/boot/";
	}
	
	/**修改状态*/
	@RequestMapping( value = "/{id}/status", method = RequestMethod.GET)
	public String status(@PathVariable int id, int status, RedirectAttributes attributes){
		PlatformBootPage entity = new PlatformBootPage();
		entity.setId(id); entity.setStatus(status);
		platformBootPageService.update(entity);
		attributes.addFlashAttribute(Remind.success());
		return "redirect:/console/platform/boot/";
	}
	
	
	/**批量删除*/
	@RequestMapping( value = "/remove", method = RequestMethod.POST)
	public String batchDelete(int[] ids, RedirectAttributes attributes){
		platformBootPageService.delete(ids);
		attributes.addFlashAttribute(Remind.success());
		return "redirect:/console/platform/boot/";
	}
	
}
