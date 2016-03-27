package br.uvv.wscarona.webservice.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageBundle {
	public static Locale locale;
	private static ResourceBundle resourceBundle;
	private static MessageFormat messageFormat;

	public static ResourceBundle getMessagesInstace() {
		resourceBundle = ResourceBundle.getBundle("br.uvv.wscarona.i18n.Messages", locale);
		messageFormat = new MessageFormat("");
		messageFormat.setLocale(locale);
		return resourceBundle;
	}

	public static void addError(String keyMsg, ListMessageException list) {
		list.getErros().add(new MessageException(resourceBundle.getString(keyMsg)));
	}
	
	public static void addRquiredField(String keyField, ListMessageException list) {
		Object[] params = new String[]{resourceBundle.getString(keyField)};
		addError("error.required.field", list, params);
	}

	public static void addError(String keyMsg, ListMessageException list, Object... params) {
		messageFormat.applyPattern(resourceBundle.getString(keyMsg));
		list.getErros().add(new MessageException(messageFormat.format(params)));
	}
}
