package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MatchScore;
import service.FinishedMatchesPersistenceService;
import service.MatchScoreCalculationService;
import service.OngoingMatchesService;
import java.io.IOException;
import java.util.UUID;

@WebServlet(urlPatterns = "/match-score")
public class MatchScoreServlet extends HttpServlet {

    private OngoingMatchesService ongoingMatchesService;
    private MatchScoreCalculationService matchScoreCalculationService;
    private FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.ongoingMatchesService = (OngoingMatchesService) config
                .getServletContext().getAttribute("ongoingService");
        this.matchScoreCalculationService = (MatchScoreCalculationService) config
                .getServletContext().getAttribute("matchScoreService");
        this.finishedMatchesPersistenceService = (FinishedMatchesPersistenceService) config
                .getServletContext().getAttribute("finishedService");

        if (ongoingMatchesService == null || matchScoreCalculationService == null || finishedMatchesPersistenceService == null){
            throw new IllegalStateException("One or more services not found in ServletContext");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidParam = req.getParameter("uuid");
        if (uuidParam == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "UUID is required");
            return;
        }

        UUID uuid = UUID.fromString(uuidParam);
        MatchScore matchScore = ongoingMatchesService.getMatchScore(uuid);

        if (matchScore == null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Match not found");
            return;
        }

        req.setAttribute("uuid", uuid);
        req.setAttribute("matchScore", matchScore);
        req.getRequestDispatcher("/WEB-INF/match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidParam = req.getParameter("uuid");
        if (uuidParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "UUID is missing");
            return;
        }

        UUID uuid = UUID.fromString(uuidParam);
        MatchScore matchScore = ongoingMatchesService.getMatchScore(uuid);

        if (matchScore == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Match not found");
            return;
        }

        int playerNumber;
        try {
            playerNumber = Integer.parseInt(req.getParameter("player"));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid player number");
            return;
        }

        boolean finished = matchScoreCalculationService.updateScore(matchScore, playerNumber);

        if (finished) {
            ongoingMatchesService.removeMatchScore(uuid);
            finishedMatchesPersistenceService.saveFinishedMatch(matchScore.getCurrentMatch());
            req.setAttribute("matchScore", matchScore);
            req.getRequestDispatcher("/WEB-INF/match-finished.jsp").forward(req, resp);
        } else {
            // матч продолжается — просто форвардим на страницу счета
            req.setAttribute("uuid", uuid);
            req.setAttribute("matchScore", matchScore);
            req.getRequestDispatcher("/WEB-INF/match-score.jsp").forward(req, resp);
        }
    }
}
