package fr.janvier.home.configurations;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class InfluxDbConfiguration {

    @Value("${influx.url}")
    private String influxUrl;


    @Value("${influx.user}")
    private String influxUser;

    @Value("${influx.password}")
    private String influxPassword;
    @Value("${influx.database}")
    private String influxDatabase;


    @Bean
    public InfluxDB influxDb(){
        InfluxDB influxDB = InfluxDBFactory.connect(influxUrl,influxUser,influxPassword);
        influxDB.setDatabase(influxDatabase);
        return influxDB;
    }


}
