package org.example.menaandfeena_finalproject.Service;

import lombok.RequiredArgsConstructor;
import org.example.menaandfeena_finalproject.Api.ApiException;
import org.example.menaandfeena_finalproject.DTO.LandmarkResponseDto;
import org.example.menaandfeena_finalproject.Model.Landmark;
import org.example.menaandfeena_finalproject.Model.User;
import org.example.menaandfeena_finalproject.Repository.LandmarkRepository;
import org.example.menaandfeena_finalproject.Repository.UserRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LandmarkService {

    private final LandmarkRepository landmarkRepository;
    private final UserRepository userRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Landmark> getAll() {
        return landmarkRepository.findAll();
    }

    public void add(Landmark landmark) {
        landmarkRepository.save(landmark);
    }

    public void update(Integer id, Landmark landmark) {

        Landmark old = landmarkRepository.findLandmarkById(id);
        if (old == null) {
            throw new ApiException("Landmark not found");
        }

        old.setName(landmark.getName());
        old.setType(landmark.getType());
        old.setLatitude(landmark.getLatitude());
        old.setLongitude(landmark.getLongitude());

        landmarkRepository.save(old);
    }

    public void delete(Integer id) {

        Landmark landmark = landmarkRepository.findLandmarkById(id);
        if (landmark == null) {
            throw new ApiException("Landmark not found");
        }

        landmarkRepository.delete(landmark);
    }


    //Reenad
    public void syncLandmarks(Double lat, Double lon, int radius) {

        String query =
                "[out:json][timeout:25];("
                        + "node[\"amenity\"=\"mosque\"](around:" + radius + "," + lat + "," + lon + ");"
                        + "node[\"amenity\"=\"school\"](around:" + radius + "," + lat + "," + lon + ");"
                        + "node[\"leisure\"=\"park\"](around:" + radius + "," + lat + "," + lon + ");"
                        + ");out;";

        String url = "https://overpass-api.de/api/interpreter";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("data", query);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, request, Map.class);

        Map<String, Object> responseBody = response.getBody();

        if (responseBody == null || responseBody.get("elements") == null) {
            return;
        }

        List<Map<String, Object>> elements =
                (List<Map<String, Object>>) responseBody.get("elements");

        for (Map<String, Object> el : elements) {

            Map<String, Object> tags =
                    (Map<String, Object>) el.get("tags");

            if (tags == null || tags.get("name") == null) {
                continue;
            }

            String name = tags.get("name").toString();
            String type = mapType(tags);

            Double placeLat = Double.valueOf(el.get("lat").toString());
            Double placeLon = Double.valueOf(el.get("lon").toString());

            if (landmarkRepository.existsByLatitudeAndLongitude(placeLat, placeLon)) {
                continue;
            }

            Landmark landmark = new Landmark();
            landmark.setName(name);
            landmark.setType(type);
            landmark.setLatitude(placeLat);
            landmark.setLongitude(placeLon);

            landmarkRepository.save(landmark);
        }
    }

    public List<LandmarkResponseDto> getNearby(Double lat, Double lon) {

        List<Landmark> all = landmarkRepository.findAll();
        List<LandmarkResponseDto> result = new ArrayList<>();

        if (all == null || all.isEmpty()) {
            return result;
        }

        for (Landmark l : all) {

            double distance = calculateDistance(
                    lat, lon,
                    l.getLatitude(),
                    l.getLongitude()
            );

            LandmarkResponseDto dto = new LandmarkResponseDto();
            dto.setName(l.getName());
            dto.setType(convertType(l.getType()));
            dto.setDistanceMeters(Math.round(distance));

            result.add(dto);
        }

        result.sort(Comparator.comparingLong(LandmarkResponseDto::getDistanceMeters));

        return result;
    }

    public LandmarkResponseDto getClosestByType(Double lat, Double lon, String type) {

        List<Landmark> list = landmarkRepository.findByType(type);

        if (list == null || list.isEmpty()) {
            return null;
        }

        Landmark best = null;
        double min = Double.MAX_VALUE;

        for (Landmark l : list) {

            if (l.getName() == null || l.getName().equals("غير معروف")) {
                continue;
            }

            double d = calculateDistance(lat, lon, l.getLatitude(), l.getLongitude());

            if (d < min) {
                min = d;
                best = l;
            }
        }

        if (best == null) return null;

        LandmarkResponseDto dto = new LandmarkResponseDto();
        dto.setName(best.getName());
        dto.setType(convertType(best.getType()));
        dto.setDistanceMeters(Math.round(min));

        return dto;
    }

    public String getNeighborhoodDashboard(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("المستخدم غير موجود");
        }

        Double lat = user.getLatitude();
        Double lon = user.getLongitude();

        if (lat == null || lon == null) {
            throw new ApiException("موقع المستخدم غير موجود");
        }

        String neighborhoodName =
                (user.getNeighborhood() != null)
                        ? user.getNeighborhood().getName()
                        : "غير مرتبط بحي";

        LandmarkResponseDto mosque = getClosestByType(lat, lon, "MOSQUE");
        LandmarkResponseDto school = getClosestByType(lat, lon, "SCHOOL");
        LandmarkResponseDto park = getClosestByType(lat, lon, "PARK");

        String mosqueInfo = (mosque != null)
                ? mosque.getName() + " (" + mosque.getDistanceMeters() + " م)"
                : "لا يوجد";

        String schoolInfo = (school != null)
                ? school.getName() + " (" + school.getDistanceMeters() + " م)"
                : "لا يوجد";

        String parkInfo = (park != null)
                ? park.getName() + " (" + park.getDistanceMeters() + " م)"
                : "لا يوجد";

        return "مرحباً بك في حي " + neighborhoodName + "\n"
                + "🕋 أقرب مسجد: " + mosqueInfo + "\n"
                + "🏫 أقرب مدرسة: " + schoolInfo + "\n"
                + "🌳 أقرب حديقة: " + parkInfo;
    }

    private String mapType(Map<String, Object> tags) {

        if (tags == null) return "OTHER";

        if ("mosque".equalsIgnoreCase((String) tags.get("amenity"))) return "MOSQUE";
        if ("school".equalsIgnoreCase((String) tags.get("amenity"))) return "SCHOOL";
        if ("park".equalsIgnoreCase((String) tags.get("leisure"))) return "PARK";

        return "OTHER";
    }

    private String convertType(String type) {

        if (type == null) return "معلم";

        return switch (type.toUpperCase()) {
            case "MOSQUE" -> "مسجد";
            case "SCHOOL" -> "مدرسة";
            case "PARK" -> "حديقة";
            default -> "معلم";
        };
    }

    private double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {

        double R = 6371000;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}