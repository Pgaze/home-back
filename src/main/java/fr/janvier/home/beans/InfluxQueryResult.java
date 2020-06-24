package fr.janvier.home.beans;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InfluxQueryResult {

    private LocalDateTime time;
    private Double value;

}
