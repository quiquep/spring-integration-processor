package com.quiquep.rest.json;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;


/**
 * JaxbJacksonObjectMapper.java: This is the custom JAXB JSON ObjectMapper
 * <p>
 * NOTE: The source code is provided by Gunnar Hillert in his blog posted at
 * http://hillert.blogspot.com/2011/01/marshal-json-data-using-jackson-in.html.
 * I modified a little bit to use the latest {@link DeserializationConfig} API
 * instead of deprecated ones.
 * <p>
 * Updated to Jackson2.
 * <p>
 * @author Vigil Bose
 * @author Gary Russell
 */
@SuppressWarnings("serial")
public class JaxbJacksonObjectMapper extends ObjectMapper {

	/**
	 * Annotation introspector to use for serialization process
	 * is configured separately for serialization and deserialization purposes
	 */
	public JaxbJacksonObjectMapper() {
		  final AnnotationIntrospector introspector
		      = new JacksonAnnotationIntrospector();
		  super.getDeserializationConfig()
		       .with(introspector);
		  super.getSerializationConfig()
		       .with(introspector);

	}
}
