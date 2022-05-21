package com.bountysneaker.controller.user;

import java.io.IOException;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bountysneaker.controller.BaseController;
import com.bountysneaker.dto.MappingModel;
import com.bountysneaker.dto.UserDTO;
import com.bountysneaker.entities.User;
import com.bountysneaker.service.UserService;
import com.bountysneaker.utils.Validate;

@Controller
@RequestMapping("/bounty-sneaker/")
public class RegisterController extends BaseController {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private MappingModel mappingModel;

	@Autowired
	private UserService userService;

	@GetMapping("register.html")
	public String register() {
		return "user/login/register";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("code-confirm")
	public ResponseEntity<JSONObject> sendCode(HttpServletRequest request) throws IOException, MessagingException {
		String emailReceiver = request.getParameter("email");
		String fullname = request.getParameter("fullname");

		Random random = new Random();
		Integer code = random.nextInt(900000) + 100000;

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		String htmlMsg = "<div>Dear " + fullname + " !</div> <br/><br/>";
		htmlMsg += "<div>Thank you for registering as a member at <b>Bounty Sneaker</b>!</div> <br/>";
		htmlMsg += "<div>Your verification code is: <b>" + code + "</b>!</div><br/>";
		htmlMsg += "<div>Please do not give this code to anyone to avoid loss of personal information.</div><br/>";
		htmlMsg += "<div>Thanks & regards,</div><br/>";
		htmlMsg += "<div style=\"color: chartreuse;\"><b>Bounty Sneaker</b></div><br/>";

		message.setContent(htmlMsg, "text/html");
		helper.setTo(emailReceiver);
		helper.setSubject("[Bounty Sneaker] Account registration confirmation code.");

		emailSender.send(message);
		JSONObject result = new JSONObject();
		result.put("result", Boolean.TRUE);
		result.put("codeConfirm", code);
		return ResponseEntity.ok(result);
	}

	@GetMapping("check-username")
	public ResponseEntity<Boolean> checkUserExisted(HttpServletRequest request) throws Exception {
		String username = request.getParameter("username");
		User user = userService.findUserByUserName(username);
		return ResponseEntity.ok(!Validate.isNullOrEmptyString(user.getUsername()));
	}

	@GetMapping("check-email")
	public ResponseEntity<Boolean> checkEmailExisted(HttpServletRequest request) throws Exception {
		String email = request.getParameter("email");
		User user = userService.findUserByEmail(email);
		return ResponseEntity.ok(user == null ? false : !Validate.isNullOrEmptyString(user.getUsername()));
	}

	@SuppressWarnings("unchecked")
	@PostMapping("register-account")
	public ResponseEntity<JSONObject> register(@ModelAttribute UserDTO userDTO) throws IOException {
		JSONObject result = new JSONObject();
		try {
			User user = mappingModel.mappingModel(userDTO);
			userService.saveOrUpdate(user, null, null, false);
			result.put("message", Boolean.TRUE);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("message", Boolean.FALSE);
			return ResponseEntity.ok(result);
		}
	}

}
