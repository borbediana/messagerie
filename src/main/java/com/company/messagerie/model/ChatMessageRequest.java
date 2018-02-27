package com.company.messagerie.rest;

public class MessageRequest {

	private String message;
	private String author;
	
	public MessageRequest() {
		super();
		// default constructor
	}
	public MessageRequest(String message, String author) {
		this.message = message;
		this.author = author;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	@Override
	public String toString() {
		return "MessageRequest [message=" + message + ", author=" + author + "]";
	}
}
