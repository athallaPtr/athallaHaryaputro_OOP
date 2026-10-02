package com.athalla.backend.controller;

import com.athalla.backend.model.Score;
import com.athalla.backend.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/scores")
@CrossOrigin(origins = "*")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/{scoreId}")
    public ResponseEntity<?> getScoreById(@PathVariable UUID scoreId) {
        Optional<Score> score = scoreService.getScoreByID(scoreId);
        if (score.isPresent()) {
            return ResponseEntity.ok(score.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Score not found");
        }
    }

    @PostMapping
    public ResponseEntity<?> createScore(@RequestBody Score score) {
        try {
            Score newScore = scoreService.createScore(score);
            return ResponseEntity.status(HttpStatus.CREATED).body(newScore);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<List<Score>> getAllScores() {
        list<Score> scores = scoreService.getAllScores()
            return ResponseEntity.ok(scores);
        // 2. Use scoreService to call getAllScores() and store those scores in a variable using List
        // 3. Return the variable containing those scores
    }

    // TODO:
// 1. Add the appropriate annotation for a GET endpoint along with the appropriate endpoint
    @GetMapping("/Leaderboard")
    public  ResponseEntity<List<Score>> getLeaderboardByPoint(
        @RequestParam(defaultValue = "10") Integer limit) {
        List<Score> scores = scoreService.getLeaderboard(limit);
        return ResponseEntity.ok(scores);
	/* 2. add the '@RequestParam' parameter with defaultValue 10
	   3. as well as Integer limit */){
        // 4. Use scoreService to call getLeaderboard() with the appropriate parameter
        //    and store those scores in a variable using List
        // 5. Return the variable containing those scores
    }

    // TODO:
// 1. Add the appropriate annotation for a GET endpoint along with the appropriate endpoint
        @GetMapping("/above/{minValue}")
    public ResponseEntity<List<Score>> getScoresAboveValue(
        @PathVariable Integer minValue) {
    List<Score> scores = scoreService.getScoreAboveValue(minValue);
    return  ResponseEntity.ok(scores);
        /* 2. add '@PathVariable' for Integer minValue*/){
        // 3. Use scoreService to call getScoreAboveValue() with the appropriate parameter
        //    and store those scores in a variable using List
        // 4. Return the variable containing those scores
    }

            // TODO:
// 1. Add the appropriate annotation for a GET endpoint along with the appropriate endpoint
            @GetMapping("/recent")
            public ResponseEntity<List<Score>> getRecentScores(){

                List<Score> scores = scoreService.getRecentScores();
                return ResponseEntity.ok(scores);
                // 2. Use scoreService to call getRecentScores() with the appropriate parameter
                //    and store those scores in a variable using List
                // 3. Return the variable containing those scores
            }



