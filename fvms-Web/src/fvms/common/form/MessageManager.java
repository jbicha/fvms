package fvms.common.form;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class MessageManager implements Serializable {
	private static final long serialVersionUID = 1881413500711441951L;

	private String messageText;

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
}