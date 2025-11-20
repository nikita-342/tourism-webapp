package com.tourism.web;

import com.tourism.model.Tour;
import com.tourism.repository.TourRepository;
import com.tourism.service.TourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class TourServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(TourServlet.class);

    private TourRepository tourRepository;
    private TourService tourService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        String resourceName = config.getInitParameter("toursResource");
        if (resourceName == null || resourceName.isBlank()) {
            resourceName = "data/tours.xml";
        }

        this.tourRepository = new TourRepository(resourceName);
        this.tourService = new TourService();

        log.info("TourServlet инициализирован");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Проверяем авторизацию
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        // Устанавливаем имя пользователя для отображения в JSP
        String username = (String) session.getAttribute("username");
        req.setAttribute("currentUser", username);

        // ... существующая логика загрузки и фильтрации туров ...
        List<Tour> allTours = tourRepository.loadAllTours();
        List<Tour> filteredTours = new ArrayList<>(allTours);

        String countryFilter = req.getParameter("country");
        String typeFilter = req.getParameter("type");
        String searchTerm = req.getParameter("search");

        if (searchTerm != null && !searchTerm.isEmpty()) {
            filteredTours = tourService.searchByName(filteredTours, searchTerm);
        }
        if (countryFilter != null && !countryFilter.isEmpty()) {
            filteredTours = tourService.filterByCountry(filteredTours, countryFilter);
        }
        if (typeFilter != null && !typeFilter.isEmpty()) {
            filteredTours = tourService.filterByType(filteredTours, typeFilter);
        }

        Map<String, Object> statistics = tourService.calculateStatistics(filteredTours);
        Map<String, Map<String, Object>> countryStats = tourService.getStatisticsByCountry(filteredTours);
        List<String> countries = tourService.getUniqueCountries(allTours);
        List<String> types = tourService.getUniqueTypes(allTours);

        req.setAttribute("tours", filteredTours);
        req.setAttribute("statistics", statistics);
        req.setAttribute("countryStats", countryStats);
        req.setAttribute("countries", countries);
        req.setAttribute("types", types);
        req.setAttribute("selectedCountry", countryFilter);
        req.setAttribute("selectedType", typeFilter);
        req.setAttribute("searchTerm", searchTerm);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/tours.jsp");
        dispatcher.forward(req, resp);
    }
}