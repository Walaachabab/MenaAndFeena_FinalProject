package org.example.menaandfeena_finalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.menaandfeena_finalproject.Api.ApiResponse;
import org.example.menaandfeena_finalproject.DTO.In.ElectionRoundInDTO;
import org.example.menaandfeena_finalproject.Service.ElectionRoundService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/election-round")
@RequiredArgsConstructor
public class ElectionRoundController {

    private final ElectionRoundService electionRoundService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllElectionRounds() {
        return ResponseEntity.status(200).body(electionRoundService.getAllElectionRounds());
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addElectionRound(@RequestBody @Valid ElectionRoundInDTO electionRoundInDTO) {
        electionRoundService.addElectionRound(electionRoundInDTO);
        return ResponseEntity.status(201).body(new ApiResponse("Election round added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateElectionRound(@PathVariable Integer id, @RequestBody @Valid ElectionRoundInDTO electionRoundInDTO) {
        electionRoundService.updateElectionRound(id, electionRoundInDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Election round updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteElectionRound(@PathVariable Integer id) {
        electionRoundService.deleteElectionRound(id);
        return ResponseEntity.status(200).body(new ApiResponse("Election round deleted successfully"));
    }

    //Reenad
    @GetMapping("/get-all-rounds")
    public ResponseEntity<?> getAllRounds() {
        return ResponseEntity.status(200).body(electionRoundService.getAllRounds());
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<ApiResponse> getRoundDetails(@PathVariable Integer id) {
        String content = electionRoundService.checkAndGetRoundDetailsString(id);

        return ResponseEntity.status(200).body(new ApiResponse(content));
    }
}
