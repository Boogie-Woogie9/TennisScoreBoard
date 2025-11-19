package util;

import dao.MatchDao;
import dao.PlayerDao;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import service.FinishedMatchesPersistenceService;
import service.MatchScoreCalculationService;
import service.OngoingMatchesService;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        // === DAO ===
        MatchDao matchDao = new MatchDao();
        PlayerDao playerDao = new PlayerDao();

        // === SERVICE ===
        OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        FinishedMatchesPersistenceService finishedMatchesPersistenceService =
                new FinishedMatchesPersistenceService(matchDao);

        // === CONTEXT ===
        sce.getServletContext().setAttribute("ongoingService", ongoingMatchesService);
        sce.getServletContext().setAttribute("matchScoreService", matchScoreCalculationService);
        sce.getServletContext().setAttribute("finishedService", finishedMatchesPersistenceService);

        System.out.println("[OK]: Application context initialized successfully.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[OK]: Application context destroyed.");
    }
}
