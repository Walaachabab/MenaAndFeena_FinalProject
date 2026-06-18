package org.example.menaandfeena_finalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.menaandfeena_finalproject.Api.ApiResponse;
import org.example.menaandfeena_finalproject.DTO.In.MayorCandidateInDTO;
import org.example.menaandfeena_finalproject.Service.MayorCandidateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mayor-candidate")
@RequiredArgsConstructor
public class MayorCandidateController {

    private final MayorCandidateService mayorCandidateService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllMayorCandidates() {
        return ResponseEntity.status(200).body(mayorCandidateService.getAllMayorCandidates());
    }

    @PostMapping("/add/{userId}/{roundId}")
    public ResponseEntity<ApiResponse> addMayorCandidate(@PathVariable Integer userId, @PathVariable Integer roundId) {
        mayorCandidateService.addMayorCandidate(userId, roundId);
        return ResponseEntity.status(201).body(new ApiResponse("تم تقديم طلب الترشح لمنصب عمدة الحي بنجاح وفحص الشروط القانونية مسبقاً."));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateMayorCandidate(@PathVariable Integer id, @RequestBody @Valid MayorCandidateInDTO mayorCandidateInDTO) {
        mayorCandidateService.updateMayorCandidate(id, mayorCandidateInDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Mayor candidate updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteMayorCandidate(@PathVariable Integer id) {
        mayorCandidateService.deleteMayorCandidate(id);
        return ResponseEntity.status(200).body(new ApiResponse("Mayor candidate deleted successfully"));
    }

    //Reenad
    @GetMapping("/get-by-round/{roundId}")
    public ResponseEntity<?> getCandidatesByRound(@PathVariable Integer roundId) {
        return ResponseEntity.status(200).body(mayorCandidateService.getCandidatesForRound(roundId));
    }

    @GetMapping("/election-page/{roundId}")
    public ResponseEntity<ApiResponse> getElectionPage(@PathVariable Integer roundId) {
        String content = mayorCandidateService.getElectionPageData(roundId);
        return ResponseEntity.status(200).body(new ApiResponse(content));
    }

    @GetMapping("/details/{candidateId}")
    public ResponseEntity<ApiResponse> getCandidateDetails(@PathVariable Integer candidateId) {
        String content = mayorCandidateService.getCandidateDetails(candidateId);
        return ResponseEntity.status(200).body(new ApiResponse(content));
    }
}

