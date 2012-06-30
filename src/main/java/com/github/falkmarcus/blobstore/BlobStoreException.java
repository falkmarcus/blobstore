package com.github.falkmarcus.blobstore;

public class BlobStoreException extends RuntimeException {

	public BlobStoreException() {
		super();
	}

	public BlobStoreException(String message, Throwable cause) {
		super(message, cause);
	}

	public BlobStoreException(String message) {
		super(message);
	}

	public BlobStoreException(Throwable cause) {
		super(cause);
	}

}
