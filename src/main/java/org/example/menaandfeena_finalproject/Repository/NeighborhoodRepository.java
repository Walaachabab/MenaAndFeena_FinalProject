package org.example.menaandfeena_finalproject.Repository;

import org.example.menaandfeena_finalproject.Model.Neighborhood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NeighborhoodRepository extends JpaRepository<Neighborhood, Integer>
{ Neighborhood findNeighborhoodById(Integer id);

    Optional<Neighborhood> findByName(String name);
}
