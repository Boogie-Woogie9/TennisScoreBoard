package service;

import dao.MatchDao;
import model.Match;
import java.util.List;

public class FinishedMatchesPersistenceService {
    private MatchDao matchDao;

    public FinishedMatchesPersistenceService(MatchDao matchDao) {
        this.matchDao = matchDao;
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

    public List<Match> getMatchesPage(int limit, int offset, String filter){
        return matchDao.findPage(limit, offset, filter);
    }

    public int countMatches(String filter){
        return matchDao.countAll(filter);
    }

    public void deleteMatch(Match match){
        matchDao.delete(match);
        System.out.println("Матч удален: " + match.getId());
    }
}