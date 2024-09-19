package com.vid.envio_correos.configuration;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.vid.envio_correos.repositories2", //
        entityManagerFactoryRef = "entityManagerFactory2",
        transactionManagerRef = "transactionManager2"
)
public class DataSourceConfig2 {

    @Bean(name = "dataSource2")
    public DataSource dataSource2() {
        return DataSourceBuilder.create()
                .url("jdbc:oracle:thin:@//serorapru12:1539/pruvid12.congregacion.local")
                .username("PRAPPVID")
                .password("PRAppVID$*")
                .driverClassName("oracle.jdbc.OracleDriver")
                .build();
    }

    @Bean(name = "entityManagerFactory2")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory2(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource2())
                .packages("com.vid.envio_correos.models") // Paquete de entidades
                .persistenceUnit("db2")
                .build();
    }

    @Bean(name = "transactionManager2")
    public PlatformTransactionManager transactionManager2(
            @Qualifier("entityManagerFactory2") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}