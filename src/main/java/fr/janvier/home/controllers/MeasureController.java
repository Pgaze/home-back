package fr.janvier.home.controllers;

import fr.janvier.home.beans.InfluxQueryResult;
import fr.janvier.home.beans.Sensor;
import fr.janvier.home.beans.SensorData;
import fr.janvier.home.dtos.SensorOverview;
import fr.janvier.home.repositories.SensorRepository;
import fr.janvier.home.utils.InfluxUtils;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.FloatControl;
import java.sql.Time;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class MeasureController {

    @Autowired
    InfluxDB influxConnection;

    @Autowired
    SensorRepository repository;

    @GetMapping(value = "/overview/{id}")
    private ResponseEntity<SensorOverview> getOverview(@PathVariable("id") UUID id) throws InterruptedException {
        log.info("UUID : {}", id);
        Optional<Sensor> queryResult = repository.findById(id);
        log.info("Optional : {}", queryResult);
        CountDownLatch latch = new CountDownLatch(6);
        if (queryResult.isPresent()) {
            Sensor s = queryResult.get();
            SensorOverview overwiew = new SensorOverview(s);
            String influxTable = s.getMeasured().toLowerCase();
            String query = "select min(value)  from " + influxTable + " where sensorId='" + s.getMeasurementId() + "'";
            log.info("Query:{}", query);
            influxConnection.query(new Query(query), queryResult1 -> {
                InfluxQueryResult r = InfluxUtils.getFieldInResultOneLine(queryResult1,"min");
                log.info("InfluxQueryResult1: {}" ,r);
                overwiew.setMinimumInformation(r.getValue(),r.getTime());
                latch.countDown();
            }, throwable -> {
                log.error("Error", throwable);
            });

            influxConnection.query(new Query("select max(value)  from " + influxTable + " where sensorId='" + s.getMeasurementId() + "'"), queryResult1 -> {
                InfluxQueryResult r = InfluxUtils.getFieldInResultOneLine(queryResult1,"max");
                log.info("InfluxQueryResult2: {}" ,r);
                overwiew.setMaxInformation(r.getValue(),r.getTime());
                latch.countDown();
                }, throwable -> {
                log.error("Error", throwable);
            });

            influxConnection.query(new Query("select last(value)  from " + influxTable + " where sensorId='" + s.getMeasurementId() + "'"), queryResult1 -> {
                InfluxQueryResult r = InfluxUtils.getFieldInResultOneLine(queryResult1,"last");
                log.info("InfluxQueryResult3: {}" ,r);
                overwiew.setLastInformation(r.getValue(),r.getTime());
                latch.countDown();
                }, throwable -> {
                log.error("Error", throwable);
            });

            influxConnection.query(new Query("select mean(value)  from " + influxTable + " where time > now() -1d and sensorId='" + s.getMeasurementId() + "'"), queryResult1 -> {
                InfluxQueryResult r = InfluxUtils.getFieldInResultOneLine(queryResult1,"mean");
                log.info("InfluxQueryResult4: {}" ,r);
                overwiew.setMeanInformation(r.getValue(),null);
                latch.countDown();
                }, throwable -> {
                log.error("Error", throwable);
            });

            influxConnection.query(new Query("select mean(value)  from " + influxTable + " where time > now() -7d and sensorId='" + s.getMeasurementId() + "'"), queryResult1 -> {
                InfluxQueryResult r = InfluxUtils.getFieldInResultOneLine(queryResult1,"mean");
                log.info("InfluxQueryResult5: {}" ,r);
                overwiew.setMeanWeek(r.getValue());
                latch.countDown();
                }, throwable -> {
                log.error("Error", throwable);
            });

            influxConnection.query(new Query("select mean(value)  from " + influxTable + " where time > now() -30d and sensorId='" + s.getMeasurementId() + "'"), queryResult1 -> {
                InfluxQueryResult r = InfluxUtils.getFieldInResultOneLine(queryResult1,"mean");
                log.info("InfluxQueryResult6: {}" ,r);
                overwiew.setMeanMonth(r.getValue());
                latch.countDown();
            }, throwable -> {
                log.error("Error", throwable);
            });
            latch.await();
            log.info("On return : {}",overwiew);
            return ResponseEntity.of(Optional.of(overwiew));
        } else {
            return new ResponseEntity<SensorOverview>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value = "/measure")
    private void receiveData(@RequestBody SensorData data) {
        log.info("Data: {}", data);
        Point p = Point.measurement(data.getMeasured())
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("value", data.getSensorValue())
                .addField("sensorId", data.getSensorId())
                .build();
        influxConnection.write(p);
    }

}
