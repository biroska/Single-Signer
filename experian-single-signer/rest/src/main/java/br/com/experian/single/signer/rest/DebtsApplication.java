package br.com.experian.single.signer.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

import br.com.experian.single.signer.rest.api.impl.CmsApiServiceImpl;
import br.com.experian.single.signer.rest.api.impl.DebtssummariesApiServiceImpl;
import br.com.experian.single.signer.rest.api.impl.HelloWorldService;

public class DebtsApplication extends Application {
	
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(DebtssummariesApiServiceImpl.class);
		classes.add(HelloWorldService.class);
		classes.add(CmsApiServiceImpl.class);
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> s = new HashSet<Object>();

		ObjectMapper mapper = new ObjectMapper();
		AnnotationIntrospector primary = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
		AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector pair = new AnnotationIntrospectorPair(primary, secondary);
		
		mapper.getDeserializationConfig().with(pair);
		mapper.getSerializationConfig().with(pair);
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	    mapper.setDateFormat(new ISO8601DateFormat());
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		JacksonJaxbJsonProvider jaxbProvider = new JacksonJaxbJsonProvider();
		jaxbProvider.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		jaxbProvider.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		jaxbProvider.setMapper(mapper);
		s.add(jaxbProvider);
		
		return s;
	}

}
