package controller;

import dto.MatchesPage;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Match;
import service.FinishedMatchesPersistenceService;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/matches")
public class MatchesListServlet extends HttpServlet {

    private FinishedMatchesPersistenceService service;

    public MatchesListServlet() {}

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.service = (FinishedMatchesPersistenceService)config
                .getServletContext().getAttribute("finishedService");
        if (this.service == null){
            throw new IllegalStateException("FinishedService not found in ServletContext");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageParam = req.getParameter("page");
        String filter = req.getParameter("filter_by_player_name");

        int page = pageParam == null ? 1 : Integer.parseInt(pageParam);
        int pageSize = 10;
        int offset = (page - 1) * pageSize;

        List<Match> matches = service.getMatchesPage(pageSize, offset, filter);
        int total = service.countMatches(filter);
        int totalPages = (int) Math.ceil(total/(double)pageSize);

        MatchesPage matchesPage = new MatchesPage(matches, page, totalPages, filter);

        req.setAttribute("model", matchesPage);

        req.getRequestDispatcher("/WEB-INF/matches.jsp").forward(req, resp);

    }
}
