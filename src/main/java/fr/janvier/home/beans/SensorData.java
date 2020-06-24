package fr.janvier.home.beans;


import lombok.Setter;
import lombok.Getter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SensorData {


    private String sensorId;
    private float sensorValue;
    private String measured;


}
