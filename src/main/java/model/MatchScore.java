package model;

import java.util.HashMap;
import java.util.Map;

public class MatchScore {
    private final Match currentMatch;
    private final Map<Integer, String> scoresView;
    boolean finished;
    boolean tiebreak;
    int score1 = 0;
    int score2 = 0;
    int games1 = 0;
    int games2 = 0;
    int set1 = 0;
    int set2 = 0;

    public MatchScore(String player1Name, String player2Name){
        this.currentMatch = new Match(player1Name, player2Name);
        this.scoresView = new HashMap<>();
        this.scoresView.put(0,"00");
        this.scoresView.put(1,"15");
        this.scoresView.put(2,"30");
        this.scoresView.put(3,"40");
    }
    public void setScore(int player){
        if (player == 1) score1++; else score2++;

        if(tiebreak){
            checkTiebreak();
        } else if (score1 >= 4 || score2 >= 4) checkGames();
    }

    private void checkGames() {
        // проверяем, не начинается ли тайбрейк
        if (games1 == 6 && games2 == 6) {
            tiebreak = true;
            score1 = score2 = 0;
            return;
        }

        if (Math.abs(score1 - score2)  >= 2) {
            if (score1 > score2) games1++; else games2++;
            score1 = score2 = 0; //обнуление очков
        }
        if (games1 >= 6 || games2 >= 6) checkSets();
    }

    private void checkSets() {
        if (Math.abs(games1 - games2) >= 2) {
            if (games1 > games2) set1 += 1; else set2 += 1;
            games1 = games2 = 0; //обнуление геймов
            checkWinner();
        }
    }

    private void checkTiebreak() {
        // Победа в тайбрейке — минимум 7 очков и разница 2
        if ((score1 >= 7 || score2 >= 7) && Math.abs(score1 - score2) >= 2) {
            if (score1 > score2) {
                set1++;
            } else {
                set2++;
            }

            // Тайбрейк завершён
            tiebreak = false;
            games1 = games2 = 0;
            score1 = score2 = 0;

            checkWinner();
        }
    }

    private void checkWinner() {
        if (set1 == 2 || set2 == 2) {
            currentMatch.setWinner(set1 == 2 ? currentMatch.getPlayer1() : currentMatch.getPlayer2());
            finished = true;
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public String getScoreString(int score){
        return this.scoresView.getOrDefault(score, "A");
    }
}
