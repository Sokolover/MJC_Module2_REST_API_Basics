package com.epam.esm.sokolov.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:jdbc.properties")
@ComponentScan("com.epam.esm.sokolov")
@EnableTransactionManagement//todo попробовать поубирать и поинжектить бин PlatformTransactionManager
public class SpringJdbcConfig {

    private final Environment environment;

    public SpringJdbcConfig(Environment environment) {
        this.environment = environment;
    }

    @Profile("dev")
    @Bean
    public BasicDataSource mysqlDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getProperty("jdbc.url"));
        dataSource.setUsername(environment.getProperty("jdbc.user"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));
        dataSource.setMinIdle(Integer.parseInt(environment.getProperty("jdbc.minIdle")));
        dataSource.setMaxIdle(Integer.parseInt(environment.getProperty("jdbc.maxIdle")));
        dataSource.setMaxOpenPreparedStatements(Integer.parseInt(environment.getProperty("jdbc.maxOpenStatements")));
        return dataSource;
    }

    //todo прикрутить куда-то
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
