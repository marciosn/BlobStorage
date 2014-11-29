package com.marciosn.cloud.storage.blobs.controll.rep;

public class Blob {

	private String uri;
	
	public Blob(String uri){
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
