package org.example.menaandfeena_finalproject.Controller;

import lombok.RequiredArgsConstructor;
import org.example.menaandfeena_finalproject.Api.ApiResponse;
import org.example.menaandfeena_finalproject.DTO.In.MayorVoteInDTO;
import org.example.menaandfeena_finalproject.Service.MayorVoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mayor-vote")
@RequiredArgsConstructor
public class MayorVoteController {

    private final MayorVoteService mayorVoteService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllMayorVotes() {
        return ResponseEntity.status(200).body(mayorVoteService.getAllMayorVotes());
    }

//Reenad
    @PostMapping("/add/{userId}/{candidateId}/{roundId}")
    public ResponseEntity<ApiResponse> addMayorVote(
            @PathVariable Integer userId,
            @PathVariable Integer candidateId,
            @PathVariable Integer roundId) {

        String resultMessage = mayorVoteService.addMayorVote(userId, candidateId, roundId);

        return ResponseEntity.status(200).body(new ApiResponse(resultMessage));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateMayorVote(@PathVariable Integer id, @RequestBody MayorVoteInDTO mayorVoteInDTO) {
        mayorVoteService.updateMayorVote(id, mayorVoteInDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Mayor vote updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteMayorVote(@PathVariable Integer id) {
        mayorVoteService.deleteMayorVote(id);
        return ResponseEntity.status(200).body(new ApiResponse("Mayor vote deleted"));
    }
}
