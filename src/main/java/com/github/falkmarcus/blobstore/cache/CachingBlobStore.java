package com.github.falkmarcus.blobstore.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.falkmarcus.blobstore.AbstractBlobStore;
import com.github.falkmarcus.blobstore.BlobStore;

public class CachingBlobStore extends AbstractBlobStore {
	static final Logger LOG = LoggerFactory.getLogger(AbstractBlobStore.class);

	private BlobStore blobStore;
	
	@Override
	protected Logger getLogger() {
		return LOG;
	}

	@Override
	public String store(byte[] content) {
		return getBlobStore().store(content);
	}

	@Override
	public boolean delete(String id) {		
		return getBlobStore().delete(id);
	}

	@Override
	public byte[] load(String id) {
		return getBlobStore().load(id);
	}

	public BlobStore getBlobStore() {
		return blobStore;
	}

	public void setBlobStore(BlobStore blobStore) {
		this.blobStore = blobStore;
	}
}
