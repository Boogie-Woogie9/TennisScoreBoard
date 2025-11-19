package service;

import model.Match;
import model.MatchScore;

public class MatchScoreCalculationService {

    public boolean updateScore(MatchScore matchScore, int playerNumber){
        if (matchScore.isFinished()){
            System.out.println("Матч уже завершен! Изменение счета невозможно.");
            return true;
        }
        if (playerNumber != 1 && playerNumber != 2){
            throw new IllegalArgumentException("Некорректный номер игрока: " + playerNumber);
        }
        matchScore.setScore(playerNumber);
        if (matchScore.isFinished()){
            Match finished = matchScore.getCurrentMatch();
            System.out.println("Матч завершен! Победитель: " + finished.getWinner().getName());
            return true;
        }
        return false;
    }
}
