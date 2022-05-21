package com.bountysneaker.controller.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
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
import com.bountysneaker.dto.BlogDTO;
import com.bountysneaker.dto.MappingModel;
import com.bountysneaker.entities.Blog;
import com.bountysneaker.service.BlogService;
import com.bountysneaker.service.CategoryBlogService;
import com.bountysneaker.service.UserService;
import com.bountysneaker.utils.ConvertUtils;
import com.bountysneaker.valueObjects.UserRequest;

@Controller
@RequestMapping("/bounty-sneaker/")
public class ManagerBlogController extends BaseController {

	@Autowired
	private BlogService blogService;

	@Autowired
	private UserService userService;

	@Autowired
	private CategoryBlogService categoryBlogService;

	@Autowired
	private GlobalConfig globalConfig;

	@Autowired
	private MappingModel mappingModel;

	@GetMapping("admin/blog.html")
	public String index() {
		return "manager/blog/managerBlog";
	}

	@GetMapping("admin/blog-detail/{seo}")
	public String detail(Model model, @PathVariable("seo") String seo) throws Exception {
		Blog blog = blogService.findBySeo(seo);
		model.addAttribute("createdBy", userService.findById(blog.getCreatedBy()));
		if (blog.getUpdatedBy() != null) {
			model.addAttribute("updatedBy", userService.findById(blog.getUpdatedBy()));
		} else {
			model.addAttribute("updatedBy", null);
		}
		model.addAttribute("blog", blog);
		return "manager/blog/managerDetailBlog";
	}

	@GetMapping("admin/add-blog")
	public String add(final Model model) {
		model.addAttribute("id_blog", null);
		return "manager/blog/createOrUpdateBlog";
	}

	@GetMapping("admin/edit-blog/{seo}")
	public String edit(final Model model, @PathVariable("seo") String seo) throws Exception {
		model.addAttribute("id_blog", blogService.findBySeo(seo).getId());
		return "manager/blog/createOrUpdateBlog";
	}

	@PostMapping("admin/add-update-blog")
	public ResponseEntity<Boolean> addOrUpdate(@ModelAttribute("Blog") BlogDTO blogDTO) throws Exception {
		blogDTO.setDetail(blogDTO.getDetail().replaceFirst(",", " ").trim());
		Blog blog = mappingModel.mappingModel(blogDTO);
		blog.setCategoryBlog(categoryBlogService.findById(blogDTO.getId_category_blog().toString()).get());
		blogService.saveOrUpdate(blog, blogDTO.getAvatar(), getUserLogined());
		return ResponseEntity.ok(Boolean.TRUE);
	}

	@GetMapping("admin/all-blog-active")
	public ResponseEntity<List<Blog>> getAllActive() throws Exception {
		return ResponseEntity.ok(blogService.findByStatus(true));
	}

	@GetMapping("admin/all-blog")
	public ResponseEntity<Map<String, Object>> getAll(@RequestParam("currentPage") Integer currentPageStr,
			@RequestParam("keySearch") String keySearch) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		UserRequest userRequest = new UserRequest(currentPageStr, globalConfig.getSizeManagePage(), keySearch, null,
				null);
		Page<Blog> page = blogService.getListBlogByFilter(userRequest);
		result.put("listBlog", page.getContent());
		result.put("currentPage", page.getNumber() + 1);
		result.put("totalPage", page.getTotalPages());
		return ResponseEntity.ok(result);
	}

	@GetMapping("admin/detail-blog")
	public ResponseEntity<JSONObject> detailBlog(@RequestParam("idBlog") String idBlog) throws Exception {
		return ResponseEntity.ok(mappingModel.mappingModel(blogService.findById(idBlog)));
	}

	@PostMapping("admin/delete-blog")
	public ResponseEntity<Boolean> delete(@RequestParam("idBlog") Integer idBlog) throws Exception {
		return blogService.deleteById(idBlog) ? ResponseEntity.ok(Boolean.TRUE) : ResponseEntity.ok(Boolean.FALSE);
	}

	@PostMapping("/admin/change-detail-blog")
	public ResponseEntity<Boolean> changeDetail(HttpServletRequest request) throws Exception {
		Integer type = ConvertUtils.convertStringToInt(request.getParameter("type"), null);
		Blog blog = blogService.findById(request.getParameter("id"));
		if (blog == null) {
			return ResponseEntity.ok(Boolean.FALSE);
		}
		if (type != null && type == 0) {
			blog.setStatus(request.getParameter("status").equals("1"));
		} else if (type != null && type == 1) {
			blog.setIsHot(request.getParameter("isHot").equals("1"));
		}
		blogService.saveOrUpdate(blog, null, getUserLogined());
		return ResponseEntity.ok(Boolean.TRUE);
	}

}
