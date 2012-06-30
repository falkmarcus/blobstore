package com.github.falkmarcus.blobstore;


public interface BlobStore {

	public abstract String store(byte[] content);

	public abstract byte[] load(String id);

	public abstract boolean delete(String id);
}