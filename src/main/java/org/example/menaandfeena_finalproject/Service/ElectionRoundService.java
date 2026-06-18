package org.example.menaandfeena_finalproject.Service;

import lombok.RequiredArgsConstructor;
import org.example.menaandfeena_finalproject.Api.ApiException;
import org.example.menaandfeena_finalproject.DTO.In.ElectionRoundInDTO;
import org.example.menaandfeena_finalproject.DTO.Out.ElectionRoundOutDTO;
import org.example.menaandfeena_finalproject.Model.ElectionRound;
import org.example.menaandfeena_finalproject.Repository.ElectionRoundRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ElectionRoundService {

    private final ElectionRoundRepository electionRoundRepository;

    //Reenad
    public String checkAndGetRoundDetailsString(Integer id) {
        ElectionRound round = electionRoundRepository.findElectionRoundById(id);
        if (round == null) {
            throw new ApiException("الجولة الانتخابية غير موجودة");
        }

        LocalDate today = LocalDate.now();

        if (round.getStatus().equalsIgnoreCase("ACTIVE") && today.isAfter(round.getEndDate())) {
            round.setStatus("CLOSED");
            electionRoundRepository.save(round);
        }

        long daysRemaining = 0;
        if (round.getStatus().equalsIgnoreCase("ACTIVE") && !today.isAfter(round.getEndDate())) {
            daysRemaining = ChronoUnit.DAYS.between(today, round.getEndDate());
        }

        String roundStatusArabic = round.getStatus().equalsIgnoreCase("ACTIVE") ? "نشطة ومتاحة للتصويت والترشح" : "مغلقة ومنتهية";

        return "🗳️ تفاصيل الجولة الانتخابية الحالية للحي:\n"
                + "🆔 رقم الدورة الانتخابية: " + round.getId() + "\n"
                + "📅 تاريخ البدء: " + round.getStartDate() + " | تاريخ الإغلاق: " + round.getEndDate() + "\n"
                + "📌 حالة الجولة الحالية: " + roundStatusArabic + "\n"
                + "===================================\n"
                + "⏳ الوقت المتبقي لإغلاق الصناديق: " + (round.getStatus().equalsIgnoreCase("ACTIVE") ? daysRemaining + " أيام متبقية فعلياً" : "انتهت فترة التصويت");
    }

    public List<ElectionRoundOutDTO> getAllElectionRounds() {
        List<ElectionRoundOutDTO> electionRoundOutDTOS = new ArrayList<>();
        for (ElectionRound electionRound : electionRoundRepository.findAll()) {
            if (electionRound.getStatus().equalsIgnoreCase("ACTIVE") && LocalDate.now().isAfter(electionRound.getEndDate())) {
                electionRound.setStatus("CLOSED");
                electionRoundRepository.save(electionRound);
            }
            electionRoundOutDTOS.add(toOutDTO(electionRound));
        }
        return electionRoundOutDTOS;
    }

    public void addElectionRound(ElectionRoundInDTO electionRoundInDTO) {
        ElectionRound electionRound = new ElectionRound();
        electionRound.setStartDate(electionRoundInDTO.getStartDate());
        electionRound.setEndDate(electionRoundInDTO.getEndDate());

        if (LocalDate.now().isAfter(electionRoundInDTO.getEndDate())) {
            electionRound.setStatus("CLOSED");
        } else {
            electionRound.setStatus(electionRoundInDTO.getStatus());
        }

        electionRoundRepository.save(electionRound);
    }

    public void updateElectionRound(Integer id, ElectionRoundInDTO electionRoundInDTO) {
        ElectionRound oldElectionRound = electionRoundRepository.findElectionRoundById(id);
        if (oldElectionRound == null) {
            throw new ApiException("Election round not found");
        }
        oldElectionRound.setStartDate(electionRoundInDTO.getStartDate());
        oldElectionRound.setEndDate(electionRoundInDTO.getEndDate());
        oldElectionRound.setStatus(electionRoundInDTO.getStatus());
        electionRoundRepository.save(oldElectionRound);
    }

    public void deleteElectionRound(Integer id) {
        ElectionRound electionRound = electionRoundRepository.findElectionRoundById(id);
        if (electionRound == null) {
            throw new ApiException("Election round not found");
        }
        electionRoundRepository.delete(electionRound);
    }

    private ElectionRoundOutDTO toOutDTO(ElectionRound electionRound) {
        return new ElectionRoundOutDTO(electionRound.getId(), electionRound.getStartDate(), electionRound.getEndDate(), electionRound.getStatus());
    }

    public List<ElectionRound> getAllRounds() {
        return electionRoundRepository.findAll();
    }
}
