package org.example.menaandfeena_finalproject.Repository;

import org.example.menaandfeena_finalproject.Model.Landmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LandmarkRepository extends JpaRepository<Landmark, Integer> {

    Landmark findLandmarkById(Integer id);

    boolean existsByLatitudeAndLongitude(Double placeLat, Double placeLon);

    List<Landmark> findByType(String type);
}
