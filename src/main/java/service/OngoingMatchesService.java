package service;

import model.MatchScore;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {
    private final Map<UUID, MatchScore> ongoingMatches = new ConcurrentHashMap<>();

    public void addMatchScore(UUID uuid, MatchScore matchScore){
        ongoingMatches.put(uuid, matchScore);
    }

    public MatchScore getMatchScore(UUID uuid){
        return ongoingMatches.get(uuid);
    }

    public void removeMatchScore(UUID uuid){
        ongoingMatches.remove(uuid);
    }
}
