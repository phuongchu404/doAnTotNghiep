package com.bountysneaker.controller.user;

import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bountysneaker.controller.BaseController;
import com.bountysneaker.dto.MappingModel;
import com.bountysneaker.dto.UserDTO;
import com.bountysneaker.entities.User;
import com.bountysneaker.service.UserService;
import com.bountysneaker.utils.Validate;

@Controller
@RequestMapping("/bounty-sneaker/")
public class AccountController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private MappingModel mappingModel;

	@Autowired
	private JavaMailSender emailSender;

	@GetMapping("my-account.html")
	public String getMyCccount() {
		return "user/account/myAccountUser";
	}

	@PostMapping("update-password")
	public ResponseEntity<Boolean> changePassword(@RequestParam String newPassword, @RequestParam String oldPassword)
			throws Exception {
		User currentUser = getUserLogined();
		Boolean result = userService.changePassword(newPassword, oldPassword, currentUser);
		getAccountRole();
		return ResponseEntity.ok(result);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("add-update-account")
	public ResponseEntity<JSONObject> addOrUpdate(HttpServletRequest request, @ModelAttribute UserDTO userDTO)
			throws Exception {
		Boolean typeAccount;
		typeAccount = request.getParameter("typeAccount") != null && request.getParameter("typeAccount").equals("1");
		JSONObject result = new JSONObject();
		User user = mappingModel.mappingModel(userDTO);

		if (user.getId() == null) {
			User userDb = userService.findByUserNameAndEmail(user.getUsername(), user.getEmail());
			if (!Validate.isNullOrEmptyString(userDb.getUsername())) {
				result.put("result", Boolean.FALSE);
				result.put("message", "Tên đăng nhập đã tồn tại!");
				return ResponseEntity.ok(result);
			}

		}
		userService.saveOrUpdate(user, userDTO.getAvatar(), getUserLogined(), typeAccount);
		result.put("result", Boolean.TRUE);
		result.put("message", "Thành công!");
		return ResponseEntity.ok(result);
	}

	@GetMapping("forget-password.html")
	public String forgetPassword() {
		return "user/login/forgetPassword";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("change-password-forget")
	public ResponseEntity<JSONObject> changePasswordForget(HttpServletRequest request) throws Exception {
		String email = request.getParameter("email");
		email = email.replace("-", "@");
		String password = request.getParameter("password");
		User user = userService.findUserByEmail(email);
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		userService.saveOrUpdate(user, null, getUserLogined(), null);
		JSONObject result = new JSONObject();
		result.put("message", Boolean.TRUE);
		return ResponseEntity.ok(result);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("code-request-forget-password")
	public ResponseEntity<JSONObject> checkEmail(HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		String emailReceiver = request.getParameter("email");
		emailReceiver = emailReceiver.replace("-", "@");
		User user = userService.findUserByEmail(emailReceiver);
		if (user != null) {
			result.put("result", Boolean.TRUE);
			return ResponseEntity.ok(result);
		} else {
			result.put("message", "Không tìm thấy tài khoản phù hợp");
			result.put("code", 0);
			return ResponseEntity.ok(result);
		}
	}

	@SuppressWarnings("unchecked")
	@GetMapping("send-email-code-confirm")
	public ResponseEntity<JSONObject> sendEmail(HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		String emailReceiver = request.getParameter("email");
		emailReceiver = emailReceiver.replace("-", "@");
		User user = userService.findUserByEmail(emailReceiver);
		String fullname = user.getFullname();

		Random random = new Random();
		Integer code = random.nextInt(900000) + 100000;

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		String htmlMsg = "<div>Dear " + fullname + " !</div> <br/><br/>";
		htmlMsg += "<div>Thank you for using service at the <b>Perfume Shop Shop</b>!</div> <br/>";
		htmlMsg += "<div>Your verification code: <b>" + code + "</b>!</div><br/>";
		htmlMsg += "<div>Please do not give this code to anyone to avoid loss of personal information.</div><br/>";
		htmlMsg += "<div>Thanks & regards,</div><br/>";
		htmlMsg += "<div style=\"color: chartreuse;\"><b>Electronic Device</b></div><br/>";

		message.setContent(htmlMsg, "text/html");
		helper.setTo(emailReceiver);
		helper.setSubject("[Electronic Device Shop] Forget Password.");

		emailSender.send(message);

		result.put("result", Boolean.TRUE);
		result.put("codeConfirm", code);
		return ResponseEntity.ok(result);
	}

}
