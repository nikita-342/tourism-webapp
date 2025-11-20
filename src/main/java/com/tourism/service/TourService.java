package com.tourism.service;

import com.tourism.model.Tour;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TourService {

    // Фильтрация по стране
    public List<Tour> filterByCountry(List<Tour> tours, String country) {
        if (country == null || country.isEmpty()) {
            return tours;
        }
        return tours.stream()
                .filter(tour -> tour.getCountry().equalsIgnoreCase(country))
                .collect(Collectors.toList());
    }

    // Фильтрация по типу тура
    public List<Tour> filterByType(List<Tour> tours, String type) {
        if (type == null || type.isEmpty()) {
            return tours;
        }
        return tours.stream()
                .filter(tour -> tour.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    // Поиск по названию
    public List<Tour> searchByName(List<Tour> tours, String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return tours;
        }
        return tours.stream()
                .filter(tour -> tour.getName().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Расчет статистики
    public Map<String, Object> calculateStatistics(List<Tour> tours) {
        double avgPrice = tours.stream()
                .mapToDouble(Tour::getPrice)
                .average()
                .orElse(0.0);

        double avgDuration = tours.stream()
                .mapToDouble(Tour::getDuration)
                .average()
                .orElse(0.0);

        double avgRating = tours.stream()
                .mapToDouble(Tour::getRating)
                .average()
                .orElse(0.0);

        int totalSpots = tours.stream()
                .mapToInt(Tour::getAvailableSpots)
                .sum();

        double totalValue = tours.stream()
                .mapToDouble(tour -> tour.getPrice() * tour.getAvailableSpots())
                .sum();

        return Map.of(
                "avgPrice", Math.round(avgPrice * 100.0) / 100.0,
                "avgDuration", Math.round(avgDuration * 100.0) / 100.0,
                "avgRating", Math.round(avgRating * 100.0) / 100.0,
                "totalSpots", totalSpots,
                "totalValue", Math.round(totalValue * 100.0) / 100.0,
                "tourCount", tours.size()
        );
    }

    // Статистика по странам
    public Map<String, Map<String, Object>> getStatisticsByCountry(List<Tour> tours) {
        return tours.stream()
                .collect(Collectors.groupingBy(
                        Tour::getCountry,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                countryTours -> {
                                    double avgPrice = countryTours.stream()
                                            .mapToDouble(Tour::getPrice)
                                            .average()
                                            .orElse(0.0);

                                    int totalTours = countryTours.size();
                                    int totalSpots = countryTours.stream()
                                            .mapToInt(Tour::getAvailableSpots)
                                            .sum();

                                    return Map.of(
                                            "avgPrice", Math.round(avgPrice * 100.0) / 100.0,
                                            "totalTours", totalTours,
                                            "totalSpots", totalSpots
                                    );
                                }
                        )
                ));
    }

    // Получение уникальных стран
    public List<String> getUniqueCountries(List<Tour> tours) {
        return tours.stream()
                .map(Tour::getCountry)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    // Получение уникальных типов туров
    public List<String> getUniqueTypes(List<Tour> tours) {
        return tours.stream()
                .map(Tour::getType)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}