package com.github.falkmarcus.blobstore.filesystem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.falkmarcus.blobstore.AbstractBlobStore;
import com.github.falkmarcus.blobstore.BlobStoreException;

public class FileSystemBlobStore extends AbstractBlobStore {

	static final Logger LOG = LoggerFactory
			.getLogger(FileSystemBlobStore.class);

	private File rootDirectory;

	public FileSystemBlobStore() {
		setRootDirectory(new File("repository"));
		if (!getRootDirectory().isDirectory() && !getRootDirectory().mkdirs()) {
			throw new BlobStoreException("failed creating root directory:"
					+ getRootDirectory());
		}
	}

	@Override
	protected Logger getLogger() {
		return LOG;
	}

	protected File ensureStoreDir(String id, boolean create) {
		if (id != null && id.length() >= 2) {
			String shortId = id.substring(id.length() - 2);
			File storeDir = new File(getRootDirectory(), shortId);

			if (create && !storeDir.exists()) {
				storeDir.mkdir();
			}

			if (!create && !storeDir.exists()) {
				return null;
			} else if (storeDir.isDirectory()) {
				return storeDir;
			} else {
				throw new BlobStoreException("expecting directory:"
						+ storeDir.getName());
			}
		} else {
			throw new BlobStoreException("missing or to short id");
		}
	}

	@Override
	public String store(byte[] content) {
		String id = SHA1(content);
		File storeDir = ensureStoreDir(id, true);
		File contentFile = new File(storeDir, id);

		if (!contentFile.exists()) {
			try {
				OutputStream stream = new FileOutputStream(contentFile);

				try {
					stream.write(content);
				} finally {
					stream.close();
				}
			} catch (IOException e) {
				throw new BlobStoreException("failed writing content file", e);
			}
		} else if (!contentFile.isFile()) {
			throw new BlobStoreException(
					"content file already existis but not a file");
		} 

		return id;
	}

	@Override
	public byte[] load(String id) {
		File storeDir = ensureStoreDir(id, false);

		if (storeDir == null) {
			return null;
		} else {
			File contentFile = new File(storeDir, id);

			if (contentFile.exists()) {
				try {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					InputStream in = new FileInputStream(contentFile);
					byte[] buffer = new byte[1024 * 4];
					int len;

					try {
						while ((len = in.read(buffer)) > 0) {
							baos.write(buffer, 0, len);
						}
					} finally {
						in.close();
					}
					return baos.toByteArray();
				} catch (IOException e) {
					throw new BlobStoreException("failed writing content file",
							e);
				}
			} else {
				return null;
			}
		}
	}

	@Override
	public boolean delete(String id) {
		File storeDir = ensureStoreDir(id, false);

		if (storeDir == null) {
			return false;
		} else {
			File contentFile = new File(storeDir, id);
			
			if (contentFile.exists()) {
				return contentFile.delete();
			} else {
				return false;
			}
		}
	}

	public File getRootDirectory() {
		return rootDirectory;
	}

	public void setRootDirectory(File rootDirectory) {
		this.rootDirectory = rootDirectory;
	}
}
