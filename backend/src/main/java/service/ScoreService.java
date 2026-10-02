package com.athalla.backend.service;

import com.athalla.backend.model.Score;
import com.athalla.backend.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    public Score createScore(Score score) {
        return scoreRepository.save(score);
    }

    public Optional<Score> getScoreByID(UUID scoreId) {
        return scoreRepository.findById(scoreId);
    }
    public List<Score> getAllScores(){
        return scoreRepository.findAll();
        // TODO: Use scoreRepository to find all scores in the database, then return the result
        // hint: Call the same method as the code you wrote in TP number 4
    }
    public List<Score> getRecentScores(){
        return scoreRepository.findAllByOrderByCreatedAtDesc();
        // TODO: Use scoreRepository to find all scores in the database ordered by newest creation, then return the result
    }
    public List<Score> getScoreAboveValue(Integer minValue){
        return scoreRepository.findByPointGreaterThan(minValue);
        // TODO: Use scoreRepository to find all scores in the database whose points are above a certain value
        // use minValue as the lower bound of the point value
    }
    public List<Score> getLeaderboard(Integer limit) {
        return scoreRepository.findTopScores(limit);
        // TODO: Use scoreRepository to find the Top Scores and provide the appropriate parameter
    }
    public void deleteScore(UUID scoreId) {
        Score score = scoreRepository.findById(scoreId)
            .orElseThrow(()-> new RuntimeException("Score with ID " + scoreId + " was not found"));
            scoreRepository.delete(score);
        // TODO:
        // 1. Find the score you want to delete using scoreRepository, then store that score (hint: see how it's done in getScoreById())
        // 2. Check whether the score was found or not with `.orElseThrow(()-> new RuntimeException("Score with ID " + scoreId + " was not found"));`
        // 3. Call delete() from scoreRepository to delete the score stored earlier
    }




}
