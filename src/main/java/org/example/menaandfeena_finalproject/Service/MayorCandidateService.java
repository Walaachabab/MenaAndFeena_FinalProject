package org.example.menaandfeena_finalproject.Service;

import lombok.RequiredArgsConstructor;
import org.example.menaandfeena_finalproject.Api.ApiException;
import org.example.menaandfeena_finalproject.DTO.In.MayorCandidateInDTO;
import org.example.menaandfeena_finalproject.DTO.Out.*;
import org.example.menaandfeena_finalproject.Model.*;
import org.example.menaandfeena_finalproject.Repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MayorCandidateService {

    private final MayorCandidateRepository mayorCandidateRepository;
    private final ElectionRoundRepository electionRoundRepository;
    private final UserRepository userRepository;
    private final MayorVoteRepository mayorVoteRepository;
    private final UserService userService;
    private final InitiativeParticipationRepository initiativeParticipationRepository;
    private final IssueReportRepository issueReportRepository;
    private final EventRegistrationRepository eventRegistrationRepository;

    public List<MayorCandidateOutDTO> getAllMayorCandidates() {
        List<MayorCandidateOutDTO> mayorCandidateOutDTOS = new ArrayList<>();
        for (MayorCandidate mayorCandidate : mayorCandidateRepository.findAll()) {
            mayorCandidateOutDTOS.add(toOutDTO(mayorCandidate));
        }
        return mayorCandidateOutDTOS;
    }

    //Reenad
    public void addMayorCandidate(Integer userId, Integer roundId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("Associated user not found");
        }

        ElectionRound round = electionRoundRepository.findById(roundId).orElse(null);
        if (round == null) {
            throw new ApiException("Election round not found");
        }

        if (!round.getStatus().equalsIgnoreCase("ACTIVE")) {
            throw new ApiException("This election round is closed for nominations");
        }

        Integer age = userService.calculateAge(user.getBirthDate());
        if (age == null || age < 30) {
            throw new ApiException("عذراً، يجب ألا يقل عمر المرشح لمنصب العمدة عن 30 عاماً!");
        }

        boolean alreadyApplied = mayorCandidateRepository.existsByUserIdAndElectionRoundId(userId, roundId);
        if (alreadyApplied) {
            throw new ApiException("You have already applied as a candidate for this round");
        }

        MayorCandidate candidate = new MayorCandidate();
        candidate.setUser(user);
        candidate.setElectionRound(round);
        candidate.setStatus("PENDING");
        candidate.setAppliedAt(LocalDateTime.now());

        mayorCandidateRepository.save(candidate);
    }

    public List<CandidateResponseDto> getCandidatesForRound(Integer roundId) {
        ElectionRound round = electionRoundRepository.findById(roundId).orElse(null);
        if (round == null) {
            throw new ApiException("Election round not found");
        }

        List<CandidateResponseDto> dtos = new ArrayList<>();
        List<MayorCandidate> candidates = mayorCandidateRepository.findByElectionRoundId(roundId);

        for (MayorCandidate candidate : candidates) {
            int voteCount = mayorVoteRepository.countByMayorCandidateId(candidate.getId());

            CandidateResponseDto dto = new CandidateResponseDto(
                    candidate.getId(),
                    candidate.getUser().getFullName(),
                    candidate.getUser().getEmail(),
                    candidate.getUser().getGender(),
                    candidate.getAppliedAt(),
                    voteCount,
                    candidate.getStatus()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    public void updateMayorCandidate(Integer id, MayorCandidateInDTO mayorCandidateInDTO) {
        MayorCandidate oldMayorCandidate = mayorCandidateRepository.findMayorCandidateById(id);
        if (oldMayorCandidate == null) {
            throw new ApiException("Mayor candidate not found");
        }
        oldMayorCandidate.setAppliedAt(mayorCandidateInDTO.getAppliedAt());
        oldMayorCandidate.setStatus(mayorCandidateInDTO.getStatus());
        mayorCandidateRepository.save(oldMayorCandidate);
    }

    public void deleteMayorCandidate(Integer id) {
        MayorCandidate mayorCandidate = mayorCandidateRepository.findMayorCandidateById(id);
        if (mayorCandidate == null) {
            throw new ApiException("Mayor candidate not found");
        }
        mayorCandidateRepository.delete(mayorCandidate);
    }

    private MayorCandidateOutDTO toOutDTO(MayorCandidate mayorCandidate) {
        return new MayorCandidateOutDTO(mayorCandidate.getId(), mayorCandidate.getAppliedAt(), mayorCandidate.getStatus());
    }
    public String getElectionPageData(Integer roundId) {
        ElectionRound round = electionRoundRepository.findById(roundId).orElse(null);
        if (round == null) throw new ApiException("Election round not found");

        long days = 2; long hours = 14; long minutes = 35;

        List<MayorCandidate> candidates = mayorCandidateRepository.findByElectionRoundId(roundId);

        StringBuilder sb = new StringBuilder();
        sb.append("🗳️ لوحة جولة انتخابات عمدة الحي الحية:\n");
        sb.append("⏳ الوقت المتبقي لإغلاق صناديق الاقتراع: ").append(days).append(" أيام و ").append(hours).append(" ساعة و ").append(minutes).append(" دقيقة\n");
        sb.append("===================================\n");
        sb.append("👥 قائمة المرشحين الحاليين في هذه الدورة:\n");

        int count = 0;
        for (int i = 0; i < candidates.size(); i++) {
            MayorCandidate candidate = candidates.get(i);
            if (candidate.getStatus().equalsIgnoreCase("APPROVED") || candidate.getStatus().equalsIgnoreCase("PENDING")) {
                int voteCount = mayorVoteRepository.countByMayorCandidateId(candidate.getId());

                sb.append("🆔 رقم المرشح: ").append(candidate.getId()).append("\n")
                        .append("👤 الاسم الكامل: ").append(candidate.getUser().getFullName()).append("\n")
                        .append("📊 إجمالي الأصوات الحالية: ").append(voteCount).append(" صوت\n")
                        .append("-----------------------------------\n");
                count++;
            }
        }

        if (count == 0) {
            sb.append("لا يوجد مرشحين معتمدين متاحين للتصويت حالياً.\n");
        }

        return sb.toString();
    }

    public String getCandidateDetails(Integer candidateId) {
        MayorCandidate candidate = mayorCandidateRepository.findById(candidateId).orElse(null);
        if (candidate == null) {
            throw new ApiException("Mayor candidate not found");
        }

        User user = candidate.getUser();
        String neighborhoodName = user.getNeighborhood() != null ? user.getNeighborhood().getName() : "النرجس";

        int joinedInitiativesCount = initiativeParticipationRepository.countByUserId(user.getId());
        int resolvedIssuesCount = issueReportRepository.countByReporterIdAndStatus(user.getId(), "COMPLETED");
        int organizedEventsCount = eventRegistrationRepository.countByUserIdAndStatus(user.getId(), "CONFIRMED");
        int initiativeParticipants = user.getNeighborhood() != null ? user.getNeighborhood().getRegisteredPopulation() * 2 : 120;

        int year = user.getCreatedAt() != null ? user.getCreatedAt().getYear() : 2024;
        String memberSinceYear = "عضو في الحي منذ " + year;

        StringBuilder sb = new StringBuilder();
        sb.append("🪪 بطاقة تفاصيل ملف المرشح الرقمي:\n");
        sb.append("👤 الاسم الكامل: ").append(user.getFullName()).append("\n");
        sb.append("🏡 الحي السكني: ").append(neighborhoodName).append(" (").append(memberSinceYear).append(")\n");
        sb.append("📌 الحالة الحالية: مرشح معتمد ومنظم للخدمات\n");
        sb.append("===================================\n");
        sb.append("📊 عدادات الأنشطة والتفاعلات للمرشح بالحي:\n");
        sb.append("🗓️ فعاليات مسجل بتنظيمها: ").append(organizedEventsCount).append(" فعالية\n");
        sb.append("🌱 مبادرات تطوعية ساهم بها: ").append(joinedInitiativesCount).append(" مبادرة\n");
        sb.append("🚨 بلاغات رفعها وتم حلها ومتابعتها: ").append(resolvedIssuesCount).append(" بلاغ ناجح\n");
        sb.append("👥 جيران تفاعلوا معه في برامجه: ").append(initiativeParticipants).append(" جار بالحي\n");
        sb.append("===================================\n");

        sb.append("🌱 المبادرات والأنشطة التي دعمها بالكامل:\n");
        List<InitiativeParticipation> participations = initiativeParticipationRepository.findByUserId(user.getId());
        int initCount = 0;
        for (int i = 0; i < participations.size(); i++) {
            InitiativeParticipation p = participations.get(i);
            if (p.getInitiative() != null) {
                sb.append("  - ").append(p.getInitiative().getTitle()).append("\n");
                initCount++;
            }
        }
        if (initCount == 0) sb.append("  - لم ينضم لمبادرات مسجلة بعد.\n");

        sb.append("\n🗓️ الفعاليات القائمة المسجل بحضورها:\n");
        List<EventRegistration> registrations = eventRegistrationRepository.findByUserId(user.getId());
        int evCount = 0;
        for (int i = 0; i < registrations.size(); i++) {
            EventRegistration reg = registrations.get(i);
            if (reg.getEvent() != null) {
                sb.append("  - ").append(reg.getEvent().getTitle()).append("\n");
                evCount++;
            }
        }
        if (evCount == 0) sb.append("  - لم يسجل في فعاليات قريبة بالحي بعد.\n");

        return sb.toString();
    }
}

