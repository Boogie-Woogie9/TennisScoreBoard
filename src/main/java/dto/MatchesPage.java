package dto;

import model.Match;

import java.util.List;

public record MatchesPage(
        List<Match> matches,
        int page,
        int totalPages,
        String filter
) {}