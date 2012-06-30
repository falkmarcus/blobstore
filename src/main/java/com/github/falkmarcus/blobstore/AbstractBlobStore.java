package com.github.falkmarcus.blobstore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.falkmarcus.blobstore.inmemory.InMemomeryBlobStore;

public abstract class AbstractBlobStore implements BlobStore {

	public AbstractBlobStore() {
		super();
	}

	protected abstract Logger getLogger();

	protected String SHA1(byte[] content) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(content, 0, content.length);
			return convertToHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			getLogger().error("failed", e);

			throw new BlobStoreException("failed to create message digeset", e);
		}
	}

	protected String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);
		}

		return buf.toString();
	}

}