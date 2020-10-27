//package com.epam.esm.sokolov.config;
//
//import org.springframework.context.annotation.*;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//
//import javax.sql.DataSource;
//
////@Profile(":test")
//@Configuration
//@PropertySource("classpath:jdbc.properties")
//@ComponentScan("com.epam.esm.sokolov")
//public class DatabaseTestConfig {
//    @Bean
//    public DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript("classpath:schema.sql")
//                .addScript("classpath:test-data.sql")
//                .build();
//    }
//}
