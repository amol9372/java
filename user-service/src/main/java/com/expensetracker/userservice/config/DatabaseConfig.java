package com.expensetracker.userservice.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.Jedis;

@Configuration
public class DatabaseConfig {

    // @Bean
    // public DataSource getDataSource() {
    // DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
    // dataSourceBuilder.driverClassName("org.postgresql.Driver");
    // dataSourceBuilder.url("jdbc:postgresql://postgres-test-db.cqju9q6izhxh.ap-south-1.rds.amazonaws.com/postgres");
    // dataSourceBuilder.username("postgres");
    // dataSourceBuilder.password("avizva9372");
    // return dataSourceBuilder.build();
    // }

    @Bean
    public Jedis jedisClient() {
        var jedis = new Jedis();
        return jedis;
    }

}
