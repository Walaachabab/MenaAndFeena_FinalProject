package org.example.menaandfeena_finalproject.Repository;


import org.example.menaandfeena_finalproject.Model.InitiativeParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InitiativeParticipationRepository extends JpaRepository<InitiativeParticipation, Integer> {
    InitiativeParticipation findInitiativeParticipationById(Integer id);

    int countByUserId(Integer id);

    List<InitiativeParticipation> findByUserId(Integer id);
}
