package br.uvv.wscarona.webservice.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageBundle {
	public static Locale locale;
	private static ResourceBundle resourceBundle;
	private static MessageFormat messageFormat;

	public static ResourceBundle getMessagesInstace() {
		resourceBundle = ResourceBundle.getBundle("Messages", locale);
		messageFormat = new MessageFormat("");
		messageFormat.setLocale(locale);
		return resourceBundle;
	}
	
	public static String getMessage(String key){
		return resourceBundle.getString(key);
	}
	
	public static String getMessage(String key, Object... params){
		messageFormat.applyPattern(resourceBundle.getString(key));
		return messageFormat.format(params);
	}
	
}
