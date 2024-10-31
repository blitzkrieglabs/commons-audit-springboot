package com.ibayad.ccs.audit.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.ibayad.ccs.applications.repositories",
    entityManagerFactoryRef = "primaryEntityManagerFactory",
    transactionManagerRef = "primaryTransactionManager"
)
@EntityScan(basePackages="com.ibayad.ccs.applications.domains")
public class DefaultDataSource {

	 @Value("${primary.datasource.url}")
	    private String url;

	    @Value("${primary.datasource.username}")
	    private String username;

	    @Value("${primary.datasource.password}")
	    private String password;

	    @Value("${primary.datasource.driver-class-name}")
	    private String driverClassName;

	    private static final String PREFIX = "primary";

	    @Primary
	    @Bean(name = "primaryDataSource")
	    @ConfigurationProperties(prefix = PREFIX + ".datasource")
	    public DataSource primaryDataSource() {
	        return DataSourceBuilder.create()
	                .url(url)
	                .username(username)
	                .password(password)
	                .driverClassName(driverClassName)
	                .build();
	    }

	    @Primary
	    @Bean(name = "primaryEntityManagerFactory")
	    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
	            EntityManagerFactoryBuilder builder,
	            @Qualifier("primaryDataSource") DataSource dataSource,
	            Environment env) {
	        Map<String, Object> properties = new HashMap<>();
	        properties.put("hibernate.dialect", env.getProperty(PREFIX + ".jpa.database-platform"));

	        return builder
	                .dataSource(dataSource)
	                .packages("com.ibayad.ccs.applications.domains") // package containing your entities
	                .persistenceUnit("primary")
	                .properties(properties)
	                .build();
	    }

	    @Primary
	    @Bean(name = "primaryTransactionManager")
	    public PlatformTransactionManager primaryTransactionManager(
	            @Qualifier("primaryEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
	        return new JpaTransactionManager(entityManagerFactory.getObject());
	    }
}
