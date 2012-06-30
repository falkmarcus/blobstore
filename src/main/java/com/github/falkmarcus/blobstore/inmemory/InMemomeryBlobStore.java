package com.github.falkmarcus.blobstore.inmemory;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.falkmarcus.blobstore.AbstractBlobStore;
import com.github.falkmarcus.blobstore.BlobStore;
import com.github.falkmarcus.blobstore.filesystem.FileSystemBlobStore;

public class InMemomeryBlobStore extends AbstractBlobStore implements BlobStore {

	static final Logger LOG = LoggerFactory
			.getLogger(InMemomeryBlobStore.class);

	private Map<String, BinaryContent> store = new HashMap<String, BinaryContent>();

	@Override
	protected Logger getLogger() {
		return LOG;
	}
	
	@Override
	public String store(byte[] content) {
		String id = SHA1(content);

		BinaryContent result = store.get(id);

		if (result == null) {
			result = new BinaryContent(id, content);
			store.put(id, result);
		}

		return result.getId();
	}

	@Override
	public byte[] load(String id) {
		BinaryContent content = store.get(id);

		if (content != null) {
			return content.getContent();
		} else {
			return null;
		}
	}

	@Override
	public boolean delete(String id) {
		return store.remove(id) != null;
	}

}
