package service;

import dao.MatchDao;
import model.Match;
import java.util.List;

public class FinishedMatchesPersistenceService {
    private final MatchDao matchDao;

    public FinishedMatchesPersistenceService() {
        matchDao = new MatchDao();
    }

    public void saveFinishedMatch(Match match){
        if (match.getWinner() == null){
            throw new IllegalArgumentException("Матч нельзя сохранить: победитель еще не определен.");
        }
        matchDao.save(match);
        System.out.println("Матч сохранен: " + match.getPlayer1().getName() + " vs " + match.getPlayer2().getName());
    }

    public Match getMatchById(Long matchId){
        return matchDao.findById(matchId);
    }

    public List<Match> getAllMatches(){
        return matchDao.findAll();
    }

    public void deleteMatch(Match match){
        matchDao.delete(match);
        System.out.println("Матч удален: " + match.getId());
    }
}