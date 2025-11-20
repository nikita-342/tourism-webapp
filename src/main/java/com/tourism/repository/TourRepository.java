package com.tourism.repository;

import com.tourism.model.Tour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TourRepository {
    private static final Logger log = LoggerFactory.getLogger(TourRepository.class);
    private final String resourceName;

    public TourRepository(String resourceName) {
        this.resourceName = resourceName;
    }

    public List<Tour> loadAllTours() {
        List<Tour> tours = new ArrayList<>();

        log.info("Пытаемся загрузить ресурс: {}", resourceName);

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            if (is == null) {
                log.error("Ресурс '{}' не найден в classpath!", resourceName);
                log.error("Проверьте что файл находится в src/main/resources/data/tours.xml");
                return tours;
            }

            log.info("Ресурс найден, начинаем парсинг XML");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("tour");
            log.info("Найдено элементов tour: {}", nodeList.getLength());

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    Tour tour = new Tour();
                    tour.setId(getElementValue(element, "id"));
                    tour.setName(getElementValue(element, "name"));
                    tour.setCountry(getElementValue(element, "country"));
                    tour.setCity(getElementValue(element, "city"));
                    tour.setPrice(Double.parseDouble(getElementValue(element, "price")));
                    tour.setDuration(Integer.parseInt(getElementValue(element, "duration")));
                    tour.setHotel(getElementValue(element, "hotel"));
                    tour.setType(getElementValue(element, "type"));
                    tour.setAvailableSpots(Integer.parseInt(getElementValue(element, "availableSpots")));
                    tour.setRating(Double.parseDouble(getElementValue(element, "rating")));

                    tours.add(tour);
                    log.info("Добавлен тур: {}", tour.getName());
                }
            }

            log.info("Успешно загружено туров: {}", tours.size());

        } catch (Exception e) {
            log.error("Ошибка загрузки туров из '{}': {}", resourceName, e.getMessage());
            e.printStackTrace();
        }

        return tours;
    }

    private String getElementValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent().trim();
        }
        return "";
    }
}