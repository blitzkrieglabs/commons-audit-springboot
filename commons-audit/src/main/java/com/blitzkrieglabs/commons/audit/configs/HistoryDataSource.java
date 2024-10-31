package com.blitzkrieglabs.commons.audit.configs;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.blitzkrieglabs.commons.audit.repositories",
    entityManagerFactoryRef = "historicalEntityManagerFactory",
    transactionManagerRef = "historicalTransactionManager"
)
//@EntityScan(basePackages = "com.ibayad") //rollback if dynamic loading didnt work
public class HistoryDataSource {
	@Value("${history.datasource.url}")
    private String url;
	
	

    @Value("${history.datasource.username}")
    private String username;

    @Value("${history.datasource.password}")
    private String password;

    @Value("${history.datasource.driver-class-name}")
    private String driverClassName;
    
    @Value("history.datasource.packages")
    private String[] auditablePackage;
	    

    @Bean(name = "historicalDataSource")
    @ConfigurationProperties(prefix = "history.datasource")
    public DataSource historicalDataSource() {
    	return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
    }

    @Bean(name = "historicalEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean historicalEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("historicalDataSource") DataSource dataSource,
            Environment env) {
        return builder
                .dataSource(dataSource)
                .packages(auditablePackage) // package containing your audit entities
                .persistenceUnit("history")
                .properties(hibernateProperties(env, "history"))
                .build();
    }

    @Bean(name = "historicalTransactionManager")
    public PlatformTransactionManager historicalTransactionManager(
            @Qualifier("historicalEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    private Map<String, Object> hibernateProperties(Environment env, String prefix) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", env.getProperty(prefix + ".jpa.database-platform"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty(prefix + ".datasource.hibernate.ddl-auto"));
        return properties;
    }
}
