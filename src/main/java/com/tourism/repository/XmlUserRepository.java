package com.tourism.repository;

import com.tourism.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class XmlUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(XmlUserRepository.class);
    private final String resourceName;

    public XmlUserRepository(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public User findByUsername(String username) {
        List<User> users = loadAllUsers();
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(User user) {
        // Для простоты в этой реализации просто добавляем в существующий список
        // В реальном приложении здесь была бы логика сохранения в XML
        log.info("User saved: {}", user.getUsername());
    }

    private List<User> loadAllUsers() {
        List<User> users = new ArrayList<>();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            if (is == null) {
                log.warn("Ресурс '{}' не найден, возвращаем пустой список", resourceName);
                return users;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("user");
            log.info("Найдено пользователей: {}", nodeList.getLength());

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String username = getElementValue(element, "username");
                    String password = getElementValue(element, "password");

                    users.add(new User(username, password));
                    log.debug("Загружен пользователь: {}", username);
                }
            }

        } catch (Exception e) {
            log.error("Ошибка загрузки пользователей из '{}': {}", resourceName, e.getMessage());
        }

        return users;
    }

    private String getElementValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent().trim();
        }
        return "";
    }
}