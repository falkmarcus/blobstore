package com.github.falkmarcus.jackson.test;

import java.io.IOException;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.falkmarcus.blobstore.model.Node;

public class JacksonTester {

	private static final Logger LOG = LoggerFactory.getLogger(JacksonTester.class);
	
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
				

		StringWriter writer = new StringWriter();
		
		mapper.writeValue(writer, new Node("hello"));
		
		LOG.info(writer.toString());
		
		Node node = mapper.readValue("{\"name\":\"hello\"}", Node.class);
		
		LOG.info(node.toString());
		
	}
}
