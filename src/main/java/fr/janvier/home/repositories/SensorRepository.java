package fr.janvier.home.repositories;

import fr.janvier.home.beans.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, UUID> {
}
