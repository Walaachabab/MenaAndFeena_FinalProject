package org.example.menaandfeena_finalproject.Repository;

import org.example.menaandfeena_finalproject.Model.MayorCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MayorCandidateRepository extends JpaRepository<MayorCandidate, Integer> {
    MayorCandidate findMayorCandidateById(Integer id);
        List<MayorCandidate> findByElectionRoundId(Integer roundId);
        boolean existsByUserIdAndElectionRoundId(Integer userId, Integer roundId);
    }


