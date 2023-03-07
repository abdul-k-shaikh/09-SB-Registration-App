package com.abd.props;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@EnableAutoConfiguration
@ConfigurationProperties(prefix = "reg-app")
public class AppProperties {
  private Map<String, String> messages = new HashMap<>();

	/*above we are using @Data to generate getters and setters like below we can do it manually
	 * 
	 * public Map<String, String> getMessages() { return messages; }
	 * 
	 * public void setMessages(Map<String, String> messages) { this.messages =
	 * messages; }
	 */
  
  
}
