
package com.vkakarla.springboot.exceptionhandling.junits.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClientURI;

@SuppressWarnings("deprecation")
@Configuration
@EnableMongoRepositories(basePackages = "com.vkakarla.springboot.exceptionhandling.junits.repository")
public class DataStoreSetup {

	@Value("${spring.data.mongodb.uri}")
	private String mongodbUri;


	@Bean
	public MongoDbFactory mongoDbFactory() {
		return new SimpleMongoDbFactory(new MongoClientURI(mongodbUri));
	}

	@Bean
	public MongoTemplate mongoTemplate() {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

		return mongoTemplate;

	}

}
