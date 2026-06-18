package org.example.menaandfeena_finalproject.Service;

import lombok.RequiredArgsConstructor;
import org.example.menaandfeena_finalproject.Api.ApiException;
import org.example.menaandfeena_finalproject.DTO.In.MayorVoteInDTO;
import org.example.menaandfeena_finalproject.DTO.Out.MayorVoteOutDTO;
import org.example.menaandfeena_finalproject.Model.ElectionRound;
import org.example.menaandfeena_finalproject.Model.MayorCandidate;
import org.example.menaandfeena_finalproject.Model.MayorVote;
import org.example.menaandfeena_finalproject.Model.User;
import org.example.menaandfeena_finalproject.Repository.ElectionRoundRepository;
import org.example.menaandfeena_finalproject.Repository.MayorCandidateRepository;
import org.example.menaandfeena_finalproject.Repository.MayorVoteRepository;
import org.example.menaandfeena_finalproject.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MayorVoteService {

    private final MayorVoteRepository mayorVoteRepository;
    private final MayorCandidateRepository mayorCandidateRepository;
    private final ElectionRoundRepository electionRoundRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public List<MayorVoteOutDTO> getAllMayorVotes() {
        List<MayorVoteOutDTO> mayorVoteOutDTOS = new ArrayList<>();
        for (MayorVote mayorVote : mayorVoteRepository.findAll()) {
            mayorVoteOutDTOS.add(toOutDTO(mayorVote));
        }
        return mayorVoteOutDTOS;
    }

    //Reenad
    public String addMayorVote(Integer userId, Integer candidateId, Integer roundId) {
        // 1. التحقق من وجود المستخدم
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("المستخدم غير موجود");
        }

        MayorCandidate candidate = mayorCandidateRepository.findById(candidateId).orElse(null);
        if (candidate == null) {
            throw new ApiException("المرشح غير موجود");
        }

        ElectionRound round = electionRoundRepository.findById(roundId).orElse(null);
        if (round == null) {
            throw new ApiException("الجولة الانتخابية غير موجودة");
        }

        if (!round.getStatus().equalsIgnoreCase("ACTIVE")) {
            throw new ApiException("عذراً، التصويت مغلق في هذه الدورة الانتخابية حالياً");
        }

        Integer voterAge = userService.calculateAge(user.getBirthDate());
        if (voterAge == null || voterAge < 18) {
            throw new ApiException("عذراً، يجب أن يكون عمرك 18 سنة أو أكثر لتتمكن من التصويت!");
        }

        MayorVote existingVote = mayorVoteRepository.findByUserIdAndElectionRoundId(userId, roundId);

        if (existingVote != null) {
            if (existingVote.getMayorCandidate().getId().equals(candidateId)) {
                mayorVoteRepository.delete(existingVote);
                return "🗳️ تم سحب صوتك بنجاح من المرشح " + candidate.getUser().getFullName();
            } else {
                existingVote.setMayorCandidate(candidate);
                mayorVoteRepository.save(existingVote);
                return "🗳️ تم تغيير صوتك بنجاح إلى المرشح " + candidate.getUser().getFullName();
            }
        }

        MayorVote vote = new MayorVote();
        vote.setUser(user);
        vote.setMayorCandidate(candidate);
        vote.setElectionRound(round);
        vote.setCreatedAt(LocalDateTime.now());

        mayorVoteRepository.save(vote);
        return "🗳️ تم تسجيل صوتك بنجاح للمرشح " + candidate.getUser().getFullName() + "، شكراً لمشاركتك الفعالة!";
    }

    public void updateMayorVote(Integer id, MayorVoteInDTO mayorVoteInDTO) {
        MayorVote oldMayorVote = mayorVoteRepository.findMayorVoteById(id);
        if (oldMayorVote == null) {
            throw new ApiException("Mayor vote not found");
        }
        oldMayorVote.setCreatedAt(mayorVoteInDTO.getCreatedAt());
        mayorVoteRepository.save(oldMayorVote);
    }

    public void deleteMayorVote(Integer id) {
        MayorVote oldMayorVote = mayorVoteRepository.findMayorVoteById(id);
        if (oldMayorVote == null) {
            throw new ApiException("Mayor vote not found");
        }
        mayorVoteRepository.delete(oldMayorVote);
    }

    private MayorVoteOutDTO toOutDTO(MayorVote mayorVote) {
        return new MayorVoteOutDTO(mayorVote.getId(), mayorVote.getCreatedAt());
    }
}

