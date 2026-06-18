package org.example.menaandfeena_finalproject.Controller;

import lombok.RequiredArgsConstructor;
import org.example.menaandfeena_finalproject.Api.ApiResponse;
import org.example.menaandfeena_finalproject.DTO.In.LandmarkSyncRequestDto;
import org.example.menaandfeena_finalproject.DTO.LandmarkResponseDto;
import org.example.menaandfeena_finalproject.Model.Landmark;
import org.example.menaandfeena_finalproject.Service.LandmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/landmark")
@RequiredArgsConstructor
public class LandmarkController {

    private final LandmarkService landmarkService;


    //Reenad
    // ================= GET ALL =================
    @GetMapping("/get-all")
    public ResponseEntity<List<Landmark>> getAll() {
        return ResponseEntity.ok(landmarkService.getAll());
    }

    // ================= SYNC =================
    @PostMapping("/sync")
    public ResponseEntity<ApiResponse> sync(@RequestBody LandmarkSyncRequestDto dto) {
        landmarkService.syncLandmarks(dto.getLat(), dto.getLon(), dto.getRadius());
        return ResponseEntity.ok(new ApiResponse("تم تحديث المعالم بنجاح"));
    }

    // ================= NEARBY =================
    @GetMapping("/nearby")
    public ResponseEntity<List<LandmarkResponseDto>> nearby(
            @RequestParam Double lat,
            @RequestParam Double lon) {

        return ResponseEntity.ok(landmarkService.getNearby(lat, lon));
    }

    // ================= DASHBOARD =================
    @GetMapping("/neighborhood-dashboard/{userId}")
    public ResponseEntity<ApiResponse> dashboard(@PathVariable Integer userId) {
        return ResponseEntity.ok(
                new ApiResponse(landmarkService.getNeighborhoodDashboard(userId))
        );
    }
}


