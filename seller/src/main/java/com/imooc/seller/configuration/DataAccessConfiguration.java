package com.imooc.seller.configuration;

import com.imooc.entity.Order;
import com.imooc.seller.repositories.OrderRepository;
import com.imooc.seller.repositoriesBackup.VerifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.EnableJpaRepositories;
import org.springframework.data.repository.config.RepositoryBeanNamePrefix;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据库相关操作配置
 */
@Configuration
public class DataAccessConfiguration {

    @Autowired
    private JpaProperties properties;


    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.primary")
    public DataSource primaryDataSource() {
        System.out.println("建primaryDataSource");
        return DataSourceBuilder.create().build();
    }
    @Bean
    @ConfigurationProperties("spring.datasource.backup")
    public DataSource backupDataSource() {
        System.out.println("建backupDataSource");
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,@Qualifier("primaryDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages(Order.class)
                .properties(getVendorProperties(dataSource))
                .persistenceUnit("primary")
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBackup(
            EntityManagerFactoryBuilder builder,@Qualifier("backupDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages(Order.class)
                .properties(getVendorProperties(dataSource))
                .persistenceUnit("backup")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory")LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(primaryEntityManagerFactory.getObject());
        System.out.println("transactionManagerPrimary");
        return transactionManager;
    }

    @Bean
    public PlatformTransactionManager transactionManagerBackup(@Qualifier("entityManagerFactoryBackup")LocalContainerEntityManagerFactoryBean backupEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(backupEntityManagerFactory.getObject());
        System.out.println("transactionManagerBackup");
        return transactionManager;
    }

    // repository 扫描的时候，并不确定哪个先扫描，查看源代码

    @Configuration
    @Primary
    @EnableTransactionManagement
    @EnableJpaRepositories(basePackageClasses = OrderRepository.class,
            entityManagerFactoryRef = "entityManagerFactory",transactionManagerRef = "transactionManager")
    public class PrimaryConfiguration { }

    @Configuration
    @EnableTransactionManagement
    @EnableJpaRepositories(basePackageClasses = OrderRepository.class,
            entityManagerFactoryRef = "entityManagerFactoryBackup",transactionManagerRef = "transactionManagerBackup")
    @RepositoryBeanNamePrefix("read")
    public class ReadConfiguration { }

    @Configuration
    @EnableTransactionManagement
    @EnableJpaRepositories(basePackageClasses = VerifyRepository.class,
            entityManagerFactoryRef = "entityManagerFactoryBackup",transactionManagerRef = "transactionManagerBackup")
    public class BackupConfiguration { }


    protected Map<String,Object> getVendorProperties(DataSource dataSource){
        Map<String,Object> vendorProperties = new LinkedHashMap<>();
        vendorProperties.putAll(properties.getHibernateProperties(dataSource));
        return vendorProperties;
    }
}
