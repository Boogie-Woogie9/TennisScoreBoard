package service;

import model.Match;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {
    private final Map<Long, Match> ongoingMatches = new ConcurrentHashMap<>();

    public void addMatch(Match match){
        ongoingMatches.put(match.getId(), match);
    }

    public Match getMatch(Long id){
        return ongoingMatches.get(id);
    }

    public void removeMatch(Long id){
        ongoingMatches.remove(id);
    }
}
