package com.example.mnarudzbe.items;

public class CommentItem {

	private String user;
	private String time;
	private String textOfComment;

	public CommentItem(String user, String time, String textOfComment) {
		this.user = user;
		this.time = time;
		this.textOfComment = textOfComment;

	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTextOfComment() {
		return textOfComment;
	}

	public void setTextOfComment(String textOfComment) {
		this.textOfComment = textOfComment;
	}

}
