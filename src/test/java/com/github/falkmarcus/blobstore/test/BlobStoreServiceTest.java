package com.github.falkmarcus.blobstore.test;

import static org.junit.Assert.*;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Test;

import com.github.falkmarcus.blobstore.BlobStore;
import com.github.falkmarcus.blobstore.filesystem.FileSystemBlobStore;

public class BlobStoreServiceTest {

	@Test
	public void test1() {
		BlobStore store = createBlobStore();
		
		byte[] content = "test1".getBytes(Charset.forName("UTF8"));
		
		String id = store.store(content);

		System.out.println(id);
		
		byte[] content2 = store.load("123");
		
		assertNull(content2);
	}

	private BlobStore createBlobStore() {
		//return new InMemomeryBlobStore();
		return new FileSystemBlobStore();
	}

	@Test
	public void test2() {
		BlobStore store = createBlobStore();
		
		byte[] content = "test2".getBytes(Charset.forName("UTF8"));
		
		String id = store.store(content);

		System.out.println(id);
		
		byte[] content2 = store.load(id);
		
		assertTrue(Arrays.equals(content, content2));
		
		assertTrue(store.delete(id));
		
		assertNull(store.load(id));
	}
	
	@Test
	public void test3() {
		test2();
	}

}
