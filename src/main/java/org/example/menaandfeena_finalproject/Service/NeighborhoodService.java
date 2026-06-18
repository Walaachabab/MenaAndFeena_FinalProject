package org.example.menaandfeena_finalproject.Service;

import lombok.RequiredArgsConstructor;
import org.example.menaandfeena_finalproject.Api.ApiException;
import org.example.menaandfeena_finalproject.Model.Neighborhood;
import org.example.menaandfeena_finalproject.Model.User;
import org.example.menaandfeena_finalproject.Repository.NeighborhoodRepository;
import org.example.menaandfeena_finalproject.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NeighborhoodService {

    private final NeighborhoodRepository neighborhoodRepository;
    private  final UserRepository userRepository;

    public List<Neighborhood> getAll() {
        return neighborhoodRepository.findAll();
    }

    public void add(Neighborhood neighborhood) {
        neighborhoodRepository.save(neighborhood);
    }

    public void update(Integer id, Neighborhood neighborhood) {
        Neighborhood old = neighborhoodRepository.findNeighborhoodById(id);
        if (old == null) throw new ApiException("Neighborhood not found");

        old.setName(neighborhood.getName());
        old.setCity(neighborhood.getCity());
        old.setEstimatedPopulation(neighborhood.getEstimatedPopulation());
        old.setRegisteredPopulation(neighborhood.getRegisteredPopulation());

        old.setLatitude(neighborhood.getLatitude());
        old.setLongitude(neighborhood.getLongitude());

        neighborhoodRepository.save(old);
    }

    public void delete(Integer id) {
        Neighborhood neighborhood = neighborhoodRepository.findNeighborhoodById(id);
        if (neighborhood == null) throw new ApiException("Neighborhood not found");
        neighborhoodRepository.delete(neighborhood);
    }

    //Reenad
    public Map<String, Object> getNeighborhoodDashboardByUserId(Integer userId) {
        User currentUser = userRepository.findUserById(userId);
        if (currentUser == null) {
            throw new ApiException("المستخدم غير موجود");
        }

        Neighborhood neighborhood = currentUser.getNeighborhood();
        if (neighborhood == null) {
            throw new ApiException("المستخدم غير مرتبط بأي حي حالياً");
        }

        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("message", "مرحباً بك يا " + currentUser.getFullName() + " في " + neighborhood.getName() + " اليوم!");
        dashboard.put("neighborhoodName", neighborhood.getName());
        dashboard.put("city", neighborhood.getCity());
        dashboard.put("registeredResidents", neighborhood.getRegisteredPopulation());
        dashboard.put("estimatedPopulation", neighborhood.getEstimatedPopulation());

        return dashboard;
    }

}
