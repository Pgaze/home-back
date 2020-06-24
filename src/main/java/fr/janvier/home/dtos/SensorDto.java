package fr.janvier.home.dtos;

import fr.janvier.home.beans.Sensor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SensorDto {

    private String id;
    private String name;

    public SensorDto(Sensor s){
        this.id = s.getId().toString();
        this.name = s.getName();
    }

}
