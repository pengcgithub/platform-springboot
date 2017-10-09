package com.yingfeng.commons.web.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.servlet.config.annotation.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 自定义web配置项<br/>
 *
 * @author pengc
 * @see com.yingfeng.commons.web.config
 * @since 2017/9/26
 */
@Configuration
@EnableConfigurationProperties({ ServerProperties.class})
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/static/**")
//				.addResourceLocations("classpath:/static/");
//		registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(
				StandardCharsets.UTF_8);
		//optimize AcceptCharset
		stringHttpMessageConverter.setWriteAcceptCharset(false);
		converters.add(stringHttpMessageConverter);
		converters.add(customJackson2HttpMessageConverter());
	}

	@Bean
	public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().applicationContext(applicationContext).build();
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(objectMapper) {
			@Override
			protected void writePrefix(JsonGenerator generator, Object object) throws IOException {
				String jsonpFunction =
						(object instanceof MappingJacksonValue ?
								((MappingJacksonValue) object).getJsonpFunction() :
								null);
				if (jsonpFunction != null) {
					generator.writeRaw(jsonpFunction + "(");
				}
			}

		};
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
		jsonConverter.setObjectMapper(objectMapper);
		return jsonConverter;
	}
}