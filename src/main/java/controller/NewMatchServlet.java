package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MatchScore;
import service.OngoingMatchesService;

import java.io.IOException;
import java.util.UUID;

@WebServlet(urlPatterns = "/new-match")
public class NewMatchServlet extends HttpServlet {

    private OngoingMatchesService ongoingMatchesService;

    public NewMatchServlet() {}

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.ongoingMatchesService = (OngoingMatchesService) config
                .getServletContext().getAttribute("ongoingService");
        if (this.ongoingMatchesService == null){
            throw new IllegalStateException("OngoingService not found in ServletContext");
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String player1Name = req.getParameter("player1");
        String player2Name = req.getParameter("player2");

        if (player1Name == null || player2Name == null || player1Name.isBlank() || player2Name.isBlank()){
            req.setAttribute("error", "Имена игроков не должны быть пустыми");
            req.getRequestDispatcher("/WEB-INF/new-match.jsp").forward(req, resp);
            return;
        }

        if (player1Name.equals(player2Name)){
            req.setAttribute("error", "Игроки должны быть разными");
            req.getRequestDispatcher("/WEB-INF/new-match.jsp").forward(req, resp);
            return;
        }

        MatchScore matchScore = new MatchScore(player1Name, player2Name);

        UUID uuid = UUID.randomUUID();
        ongoingMatchesService.addMatchScore(uuid, matchScore);

        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid);
    }
}
