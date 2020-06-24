package fr.janvier.home.controllers;

import fr.janvier.home.beans.Sensor;
import fr.janvier.home.dtos.SensorDto;
import fr.janvier.home.repositories.SensorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class SensorController {

    @Autowired
    private SensorRepository repository;


    @GetMapping("sensor")
    public ResponseEntity<List<SensorDto>> getAll(){
        log.info("Sensor requested");
        return ResponseEntity.ok(repository.findAll().parallelStream().map(SensorDto::new).collect(Collectors.toList()));
    }


    @PostMapping("sensor")
    public ResponseEntity<SensorDto> createNew(@RequestBody SensorDto dtos){
        Sensor s = new Sensor();
        s.setName(dtos.getName());
        Sensor inserted = repository.save(s);
        return ResponseEntity.ok(new SensorDto(inserted));
    }

}
