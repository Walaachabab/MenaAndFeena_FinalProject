package org.example.menaandfeena_finalproject.Service;

import lombok.RequiredArgsConstructor;
import org.example.menaandfeena_finalproject.Api.ApiException;
import org.example.menaandfeena_finalproject.DTO.In.UserRegisterRequestDto;
import org.example.menaandfeena_finalproject.DTO.Out.*;
import org.example.menaandfeena_finalproject.Model.*;
import org.example.menaandfeena_finalproject.Repository.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final NeighborhoodRepository neighborhoodRepository;
    private final InitiativeParticipationRepository initiativeParticipationRepository;
    private final IssueReportRepository issueReportRepository;
    private final EventRegistrationRepository eventRegistrationRepository;
    private final OpenAIService openAIService;

    private final RestTemplate restTemplate = new RestTemplate();

    // حساب العمر
    public Integer calculateAge(LocalDate birthDate) {
        if (birthDate == null) return null;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void add(User user) {
        userRepository.save(user);
    }

    public void update(Integer id, User user) {
        User old = userRepository.findUserById(id);
        if (old == null) throw new ApiException("User not found");
        old.setFullName(user.getFullName());
        old.setEmail(user.getEmail());
        old.setPassword(user.getPassword());
        old.setPhone(user.getPhone());
        old.setNationalId(user.getNationalId());
        old.setBirthDate(user.getBirthDate());
        old.setGender(user.getGender());
        old.setStatus(user.getStatus());
        old.setYearsInNeighborhood(user.getYearsInNeighborhood());
        old.setIsVerified(user.getIsVerified());
        userRepository.save(old);
    }

    public void delete(Integer id) {
        User user = userRepository.findUserById(id);
        if (user == null) throw new ApiException("User not found");
        userRepository.delete(user);
    }

    //Reenad
    public UserRegisterResponseDto registerUser(UserRegisterRequestDto dto) {
        String nationalId = dto.getNationalId();
        if (nationalId == null || nationalId.length() != 10 || (!nationalId.startsWith("1") && !nationalId.startsWith("2"))) {
            throw new ApiException("الهوية الوطنية غير صالحة");
        }

        String districtName = "النرجس";
        String cityName = "الرياض";

        if (dto.getLatitude() != null && dto.getLongitude() != null) {
            Map<String, Object> geoData = fetchDistrictFromCoordinates(dto.getLatitude(), dto.getLongitude());
            districtName = (String) geoData.getOrDefault("district", districtName);
            cityName = (String) geoData.getOrDefault("city", cityName);
        }

        Neighborhood neighborhood = neighborhoodRepository.findByName(districtName).orElse(null);

        if (neighborhood == null) {
            neighborhood = new Neighborhood();
            neighborhood.setName(districtName);
            neighborhood.setCity(cityName);
            neighborhood.setEstimatedPopulation(30000);
            neighborhood.setRegisteredPopulation(0);
            neighborhood.setLatitude(dto.getLatitude() != null ? dto.getLatitude() : 24.7136);
            neighborhood.setLongitude(dto.getLongitude() != null ? dto.getLongitude() : 46.6753);
            neighborhood = neighborhoodRepository.save(neighborhood);
        }

        neighborhood.setRegisteredPopulation(neighborhood.getRegisteredPopulation() + 1);
        neighborhoodRepository.save(neighborhood);

        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhone(dto.getPhone());
        user.setNationalId(dto.getNationalId());
        user.setBirthDate(dto.getBirthDate());
        user.setGender(dto.getGender());
        user.setStatus("RESIDENT");
        user.setIsVerified(true);
        user.setNeighborhood(neighborhood);
        user.setLatitude(dto.getLatitude());
        user.setLongitude(dto.getLongitude());

        User savedUser = userRepository.save(user);

        return new UserRegisterResponseDto(
                savedUser.getId(), savedUser.getFullName(), savedUser.getEmail(),
                neighborhood.getName(), neighborhood.getCity(), savedUser.getCreatedAt()
        );
    }

    /*public UserProfileResponseDto loginUser(String email, String password) {
        User user = userRepository.findUserByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            throw new ApiException("البريد الإلكتروني أو كلمة المرور غير صحيحة");
        }
        return getUserProfile(user.getId());
    }*/

    private Map<String, Object> fetchDistrictFromCoordinates(Double lat, Double lon) {

        String systemPrompt = "أنت خبير خرائط ومعالم جغرافية في المملكة العربية السعودية. "
                + "ستستقبل خطوط طول وعرض (Latitude, Longitude) في مدينة الرياض، "
                + "ومهمتك الوحيدة هي إعطائي اسم الحي الفعلي الذي تقع فيه هذه الإحداثيات باللغة العربية فقط. "
                + "ممنوع كتابة أي مقدمات، وممنوع كتابة كلمة 'حي'، وممنوع كتابة جمل مثل 'هذه الإحداثيات تقع في'. "
                + "أعطني اسم الحي مباشرة ككلمة مجردة (مثال: قرطبة، الملقا، النرجس، العليا). "
                + "إذا لم تكن متأكداً، اكتب: النرجس";

        String userContent = "الإحداثيات هي: Latitude = " + lat + ", Longitude = " + lon;

        try {
            String aiResponse = openAIService.askAI(systemPrompt, userContent);

            String districtName = aiResponse != null ? aiResponse.trim().replaceAll("[.\\n]", "") : "النرجس";

            if ("ERROR_FALLBACK".equals(districtName)) {
                districtName = "النرجس";
            }

            return Map.of(
                    "district", districtName,
                    "city", "الرياض"
            );

        } catch (Exception e) {
        }

        return Map.of("district", "النرجس", "city", "الرياض");
    }

    public String getUserProfile(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("المستخدم غير موجود");
        }

        Integer calculatedAge = calculateAge(user.getBirthDate());
        String neighborhoodName = (user.getNeighborhood() != null) ? user.getNeighborhood().getName() : "غير مرتبط بحي";

        return "🪪 ملف جاري الرقمي:\n"
                + "الاسم الكامل: " + user.getFullName() + "\n"
                + "البريد الإلكتروني: " + user.getEmail() + "\n"
                + "رقم الجوال: " + user.getPhone() + "\n"
                + "الهوية الوطنية: " + user.getNationalId() + "\n"
                + "العمر الحالي: " + (calculatedAge != null ? calculatedAge + " سنة" : "غير محدد") + "\n"
                + "الجنس: " + (user.getGender() != null ? user.getGender() : "غير محدد") + "\n"
                + "الصفة/الحالة: " + user.getStatus() + "\n"
                + "الحي السكني: " + neighborhoodName;
    }

    public String getNeighborhoodStats(Integer neighborhoodId) {
        List<User> residents = userRepository.findByNeighborhoodId(neighborhoodId);

        int males = 0; int females = 0; int children = 0; int adults = 0; int seniors = 0;

        for (int i = 0; i < residents.size(); i++) {
            User user = residents.get(i);
            Integer age = calculateAge(user.getBirthDate());

            if (user.getGender() != null) {
                if ("MALE".equalsIgnoreCase(user.getGender())) males++;
                else if ("FEMALE".equalsIgnoreCase(user.getGender())) females++;
            }

            if (age != null) {
                if (age < 12) children++;
                else if (age <= 60) adults++;
                else seniors++;
            }
        }

        return "📊 إحصائيات وبنية الحي السكاني الحية:\n"
                + "إجمالي جيران الحي المسجلين: " + residents.size() + " ساكن\n"
                + "👨 عدد الذكور: " + males + "\n"
                + "👩 عدد الإناث: " + females + "\n"
                + "===================================\n"
                + "👶 عدد الأطفال (أقل من 12): " + children + "\n"
                + "🧑 عدد البالغين (12 - 60): " + adults + "\n"
                + "🧓 عدد كبار السن (أكبر من 60): " + seniors;
    }

    public String getNeighborhoodResidents(Integer userId) {
        User currentUser = userRepository.findUserById(userId);
        if (currentUser == null) {
            throw new ApiException("المستخدم غير موجود");
        }
        if (currentUser.getNeighborhood() == null) {
            throw new ApiException("أنت غير مرتبط بأي حي سكني حالياً لتصفح جيرانك");
        }

        List<User> allResidents = userRepository.findByNeighborhoodId(currentUser.getNeighborhood().getId());
        StringBuilder sb = new StringBuilder("📱 بطاقات تواصل الجيران في حي " + currentUser.getNeighborhood().getName() + ":\n");

        int residentCount = 0;
        for (int i = 0; i < allResidents.size(); i++) {
            User resident = allResidents.get(i);
            if (!resident.getId().equals(userId)) {
                sb.append("- ").append(resident.getFullName())
                        .append(" (الجنس: ").append(resident.getGender() != null ? resident.getGender() : "غير محدد").append(")")
                        .append(" | جوال: ").append(resident.getPhone()).append("\n");
                residentCount++;
            }
        }

        if (residentCount == 0) {
            return "لا يوجد جيران مسجلين معك في هذا الحي حالياً.";
        }

        return sb.toString();
    }

    public String getUserActivityLog(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("المستخدم غير موجود");
        }

        StringBuilder sb = new StringBuilder("⏳ سجل آخر الأنشطة والتفاعلات الحية لـ " + user.getFullName() + ":\n");

        List<IssueReport> issues = issueReportRepository.findByReporterId(user.getId());
        sb.append("🚨 البلاغات المرفوعة بالحي:\n");
        if (issues == null || issues.isEmpty()) {
            sb.append("  - لا يوجد بلاغات مرفوعة لك حالياً.\n");
        } else {
            for (int i = 0; i < issues.size(); i++) {
                IssueReport issue = issues.get(i);
                sb.append("  - ").append(issue.getTitle()).append(" [الحالة: ").append(issue.getStatus()).append("]\n");
            }
        }

        List<EventRegistration> registrations = eventRegistrationRepository.findByUserId(user.getId());
        sb.append("\n🗓️ الفعاليات المسجل بها بالحي:\n");
        if (registrations == null || registrations.isEmpty()) {
            sb.append("  - لا يوجد فعاليات مسجل بها حالياً.\n");
        } else {
            for (int i = 0; i < registrations.size(); i++) {
                EventRegistration reg = registrations.get(i);
                if (reg.getEvent() != null) {
                    sb.append("  - ").append(reg.getEvent().getTitle()).append(" [التسجيل: ").append(reg.getStatus()).append("]\n");
                }
            }
        }

        List<InitiativeParticipation> participations = initiativeParticipationRepository.findByUserId(user.getId());
        sb.append("\n🌱 المبادرات التطوعية المشترك بها:\n");
        if (participations == null || participations.isEmpty()) {
            sb.append("  - لا يوجد مبادرات مشترك بها حالياً.\n");
        } else {
            for (int i = 0; i < participations.size(); i++) {
                InitiativeParticipation part = participations.get(i);
                if (part.getInitiative() != null) {
                    sb.append("  - ").append(part.getInitiative().getTitle()).append(" [الحالة: ").append(part.getStatus()).append("]\n");
                }
            }
        }

        return sb.toString();
    }


}
