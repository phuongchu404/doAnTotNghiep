package com.bountysneaker.controller.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bountysneaker.conf.GlobalConfig;
import com.bountysneaker.controller.BaseController;
import com.bountysneaker.dto.CategoryBlogDTO;
import com.bountysneaker.dto.MappingModel;
import com.bountysneaker.entities.CategoryBlog;
import com.bountysneaker.exceptions.EntityNotFoundCustomException;
import com.bountysneaker.service.CategoryBlogService;
import com.bountysneaker.service.UserService;
import com.bountysneaker.valueObjects.BaseVo;
import com.bountysneaker.valueObjects.UserRequest;

@Controller
@RequestMapping("/bounty-sneaker/")
public class ManagerCategoryBlogController extends BaseController {

	@Autowired
	private CategoryBlogService categoryBlogService;

	@Autowired
	private UserService userService;

	@Autowired
	private MappingModel mappingModel;

	@Autowired
	private GlobalConfig globalConfig;

	@GetMapping("/admin/category-blog.html")
	public String index() {
		return "manager/categoryBlog/indexCategoryBlog";
	}

	@GetMapping("admin/category-blog-detail/{seo}")
	public String detail(final Model model, @PathVariable("seo") String seo) throws Exception {
		CategoryBlog category = categoryBlogService.findBySeo(seo);
		model.addAttribute("createdBy", userService.findById(category.getCreatedBy()));
		if (category.getUpdatedBy() != null) {
			model.addAttribute("updatedBy", userService.findById(category.getUpdatedBy()));
		} else {
			model.addAttribute("updatedBy", null);
		}
		model.addAttribute("category", category);
		return "manager/categoryBlog/detailCategoryBlog";
	}

	@GetMapping("admin/add-category-blog")
	public String add(Model model) {
		model.addAttribute("id_category", null);
		return "manager/categoryBlog/createOrUpdateCategoryBlog";
	}

	@GetMapping("admin/edit-category-blog/{seo}")
	public String edit(final Model model, @PathVariable("seo") String seo) throws Exception {
		model.addAttribute("id_category", categoryBlogService.findBySeo(seo).getId());
		return "manager/categoryBlog/createOrUpdateCategoryBlog";
	}

	@PostMapping("admin/add-update-category-blog")
	public ResponseEntity<Boolean> addOrUpdate(@ModelAttribute("category") CategoryBlogDTO categoryBlogDTO)
			throws Exception {
		CategoryBlog category = mappingModel.mappingModel(categoryBlogDTO);
		categoryBlogService.saveOrUpdate(category, categoryBlogDTO.getAvatar(), getUserLogined());
		return ResponseEntity.ok(Boolean.TRUE);
	}

	@GetMapping("admin/all-category-blog-active")
	public ResponseEntity<List<CategoryBlog>> getAllActive() throws Exception {
		return ResponseEntity.ok(categoryBlogService.findByStatus(true));
	}

	@GetMapping("admin/all-category-blog")
	public ResponseEntity<BaseVo<CategoryBlog>> getAll(@ModelAttribute UserRequest userRequest) throws Exception {
		userRequest.setSizeOfPage(globalConfig.getSizeManagePage());
		Page<CategoryBlog> page = categoryBlogService.findAllByUserRequest(userRequest);
		BaseVo<CategoryBlog> result = new BaseVo<CategoryBlog>(page.getContent(), page.getNumber() + 1,
				page.getTotalPages());
		return ResponseEntity.ok(result);
	}

	@GetMapping("admin/detail-category-blog")
	public ResponseEntity<CategoryBlog> detailCategory(@RequestParam("idCategory") String idCategory) throws Exception {
		return ResponseEntity.ok(categoryBlogService.findById(idCategory).get());
	}

	@PostMapping("admin/delete-category-blog")
	public ResponseEntity<Boolean> delete(@RequestParam("idCategory") Integer idCategory) throws Exception {
		return ResponseEntity.ok(categoryBlogService.deleteById(idCategory));
	}

	@PostMapping("/admin/change-detail-category-blog")
	public ResponseEntity<Boolean> changeDetail(HttpServletRequest request) throws Exception {
		CategoryBlog categoryBlog = categoryBlogService.findById(request.getParameter("id"))
				.orElseThrow(() -> new EntityNotFoundCustomException("Not found category blog"));
		if (categoryBlog == null) {
			return ResponseEntity.ok(Boolean.FALSE);
		}
		categoryBlog.setStatus(request.getParameter("status").equals("1"));
		categoryBlogService.saveOrUpdate(categoryBlog, null, getUserLogined());
		return ResponseEntity.ok(Boolean.TRUE);
	}

}
