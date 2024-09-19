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
        basePackages = "com.vid.envio_correos.repositories",
        entityManagerFactoryRef = "entityManagerFactory1",
        transactionManagerRef = "transactionManager1"
)
public class DataSourceConfig1 {

    @Primary
    @Bean(name = "dataSource1")
    public DataSource dataSource1() {
        // Configura el DataSource aquí (ej. usando HikariCP)
        return DataSourceBuilder.create()
                .url("jdbc:oracle:thin:@//serora2pruebas:1521/pruVID")
                .username("PPQUERYX7")
                .password("PPQu3ryx7#VID")
                .driverClassName("oracle.jdbc.OracleDriver")
                .build();
    }

    @Primary
    @Bean(name = "entityManagerFactory1")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory1(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource1())
                .packages("com.vid.envio_correos.models") // Paquete de entidades
                .persistenceUnit("db1")
                .build();
    }

    @Primary
    @Bean(name = "transactionManager1")
    public PlatformTransactionManager transactionManager1(
            @Qualifier("entityManagerFactory1") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}