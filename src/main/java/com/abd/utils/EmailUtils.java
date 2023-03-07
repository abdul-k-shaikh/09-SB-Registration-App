package com.abd.utils;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.abd.exception.RegAppException;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender mailSender;

	public boolean sendEmail(String subject, String body, String to) {
		boolean isMailSent = false;
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			// Setting up necessary details
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setText(body,true);
			// Sending the mail
			//commenting for testing other functionality
			mailSender.send(mimeMessageHelper.getMimeMessage());
			isMailSent=true;
		}
		// Catch block to handle the exceptions
		catch (Exception e) {
			throw new RegAppException(e.getMessage());
		}
		return isMailSent;
	}
}
