package fr.janvier.home.beans;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Sensor {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name="id", columnDefinition = "BINARY(16)")
    //@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDBinaryType")
    private UUID id;

    @Column
    private String name;

    @Column
    private String measurementId;

    @Column
    private String measured;

    @Column
    private String location;

    @Column
    private String description;

}
