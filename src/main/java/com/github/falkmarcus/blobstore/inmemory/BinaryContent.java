package com.github.falkmarcus.blobstore.inmemory;

public class BinaryContent {
	public BinaryContent(String id, byte[] content) {
		super();
		this.id = id;
		this.content = content;
	}
	
	private String id;
	private byte[] content;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}	
}
