<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Туры и направления</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background: #f5f5f5;
        }
        .container {
            max-width: 1400px;
            margin: 0 auto;
            padding: 2rem;
        }
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 2rem;
            border-radius: 10px;
            margin-bottom: 2rem;
        }
        .filters {
            background: white;
            padding: 1.5rem;
            border-radius: 10px;
            margin-bottom: 2rem;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .filter-group {
            display: flex;
            gap: 1rem;
            align-items: center;
            flex-wrap: wrap;
        }
        select, input, button {
            padding: 0.5rem 1rem;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 1rem;
        }
        button {
            background: #667eea;
            color: white;
            border: none;
            cursor: pointer;
        }
        button:hover {
            background: #5a6fd8;
        }
        .reset-link {
            color: #667eea;
            text-decoration: none;
            margin-left: auto;
        }
        .reset-link:hover {
            text-decoration: underline;
        }
        .stats {
            background: white;
            padding: 1.5rem;
            border-radius: 10px;
            margin-bottom: 2rem;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .stat-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 1rem;
        }
        .stat-item {
            text-align: center;
            padding: 1rem;
            background: #f8f9fa;
            border-radius: 5px;
        }
        .stat-value {
            font-size: 1.5rem;
            font-weight: bold;
            color: #667eea;
        }
        .stat-label {
            font-size: 0.9rem;
            color: #666;
        }
        .tour-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
            gap: 2rem;
            margin-bottom: 2rem;
        }
        .tour-card {
            background: white;
            border-radius: 10px;
            padding: 1.5rem;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            transition: transform 0.3s ease;
            border: 1px solid #e0e0e0;
        }
        .tour-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 20px rgba(0,0,0,0.15);
        }
        .tour-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            margin-bottom: 1rem;
        }
        .tour-name {
            font-size: 1.2rem;
            font-weight: bold;
            color: #333;
            margin: 0;
            flex: 1;
        }
        .tour-rating {
            background: #4caf50;
            color: white;
            padding: 0.3rem 0.6rem;
            border-radius: 20px;
            font-size: 0.9rem;
            white-space: nowrap;
        }
        .tour-location {
            color: #666;
            margin-bottom: 0.5rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }
        .tour-details {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 0.5rem;
            margin: 1rem 0;
        }
        .detail-item {
            display: flex;
            flex-direction: column;
        }
        .detail-label {
            font-size: 0.8rem;
            color: #666;
        }
        .detail-value {
            font-weight: bold;
        }
        .tour-price {
            font-size: 1.5rem;
            font-weight: bold;
            color: #e91e63;
            text-align: center;
            margin: 1rem 0;
            padding: 0.5rem;
            background: #fce4ec;
            border-radius: 5px;
        }
        .spots-available {
            text-align: center;
            padding: 0.5rem;
            border-radius: 5px;
            font-weight: bold;
            margin-top: 1rem;
        }
        .spots-high { background: #e8f5e8; color: #4caf50; }
        .spots-medium { background: #fff3e0; color: #ff9800; }
        .spots-low { background: #ffebee; color: #f44336; }
        .back-link {
            display: inline-block;
            margin-top: 2rem;
            color: #667eea;
            text-decoration: none;
            font-weight: bold;
            padding: 0.5rem 1rem;
            border: 2px solid #667eea;
            border-radius: 5px;
            transition: all 0.3s ease;
        }
        .back-link:hover {
            background: #667eea;
            color: white;
        }
        .no-tours {
            text-align: center;
            padding: 3rem;
            background: white;
            border-radius: 10px;
            grid-column: 1 / -1;
        }
        .country-stats {
            background: white;
            padding: 1.5rem;
            border-radius: 10px;
            margin-top: 2rem;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .stats-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1rem;
        }
        .stats-table th,
        .stats-table td {
            padding: 0.75rem;
            text-align: left;
            border-bottom: 1px solid #eee;
        }
        .stats-table th {
            background: #f8f9fa;
            font-weight: bold;
        }
    </style>
</head>
<body>
<!-- Панель пользователя -->
<div class="user-panel" style="background: #2c3e50; color: white; padding: 0.5rem 2rem; display: flex; justify-content: space-between; align-items: center;">
    <div>
        <strong>Туристическое агентство</strong>
    </div>
    <div style="display: flex; align-items: center; gap: 1rem;">
        <span>👤 Добро пожаловать, ${currentUser}!</span>
        <a href="auth?action=logout" style="color: white; text-decoration: none; background: #e74c3c; padding: 0.3rem 0.8rem; border-radius: 3px; font-size: 0.9rem;">
            Выйти
        </a>
    </div>
</div>
<div class="container">
    <div class="header">
        <h1>🌎 Наши туры и направления</h1>
        <p>Подберите идеальный тур для вашего путешествия</p>
    </div>

    <!-- Фильтры и поиск -->
    <div class="filters">
        <form method="get" action="tours">
            <div class="filter-group">
                <input type="text" name="search" placeholder="Поиск по названию..."
                       value="${searchTerm}" style="flex: 2;">

                <select name="country">
                    <option value="">Все страны</option>
                    <c:forEach var="country" items="${countries}">
                        <option value="${country}"
                                <c:if test="${country == selectedCountry}">selected</c:if>>
                                ${country}
                        </option>
                    </c:forEach>
                </select>

                <select name="type">
                    <option value="">Все типы</option>
                    <c:forEach var="type" items="${types}">
                        <option value="${type}"
                                <c:if test="${type == selectedType}">selected</c:if>>
                                ${type}
                        </option>
                    </c:forEach>
                </select>

                <button type="submit">🔍 Применить</button>
                <a href="tours" class="reset-link">Сбросить фильтры</a>
            </div>
        </form>
    </div>

    <!-- Общая статистика -->
    <div class="stats">
        <h3>📊 Общая статистика</h3>
        <div class="stat-grid">
            <div class="stat-item">
                <div class="stat-value">${statistics.tourCount}</div>
                <div class="stat-label">Всего туров</div>
            </div>
            <div class="stat-item">
                <div class="stat-value">${statistics.avgPrice}€</div>
                <div class="stat-label">Средняя цена</div>
            </div>
            <div class="stat-item">
                <div class="stat-value">${statistics.avgDuration}</div>
                <div class="stat-label">Средняя длительность (дней)</div>
            </div>
            <div class="stat-item">
                <div class="stat-value">${statistics.totalSpots}</div>
                <div class="stat-label">Доступных мест</div>
            </div>
            <div class="stat-item">
                <div class="stat-value">${statistics.avgRating}</div>
                <div class="stat-label">Средний рейтинг</div>
            </div>
            <div class="stat-item">
                <div class="stat-value">${statistics.totalValue}€</div>
                <div class="stat-label">Общая стоимость</div>
            </div>
        </div>
    </div>

    <!-- Список туров -->
    <c:choose>
        <c:when test="${empty tours}">
            <div class="no-tours">
                <h3>😔 Туры не найдены</h3>
                <p>Попробуйте изменить параметры поиска или фильтрации</p>
                <a href="tours" class="back-link">Показать все туры</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="tour-grid">
                <c:forEach var="tour" items="${tours}">
                    <div class="tour-card">
                        <div class="tour-header">
                            <h3 class="tour-name">${tour.name}</h3>
                            <span class="tour-rating">⭐ ${tour.rating}</span>
                        </div>
                        <div class="tour-location">
                            📍 ${tour.country}, ${tour.city}
                        </div>
                        <div class="tour-details">
                            <div class="detail-item">
                                <span class="detail-label">Отель</span>
                                <span class="detail-value">${tour.hotel}</span>
                            </div>
                            <div class="detail-item">
                                <span class="detail-label">Тип тура</span>
                                <span class="detail-value">${tour.type}</span>
                            </div>
                            <div class="detail-item">
                                <span class="detail-label">Длительность</span>
                                <span class="detail-value">${tour.duration} дней</span>
                            </div>
                            <div class="detail-item">
                                <span class="detail-label">Цена за день</span>
                                <span class="detail-value">${tour.price}€</span>
                            </div>
                        </div>
                        <div class="tour-price">
                            Итого: ${tour.totalPrice}€
                        </div>
                        <div class="spots-available
                                <c:choose>
                                    <c:when test="${tour.availableSpots > 10}">spots-high</c:when>
                                    <c:when test="${tour.availableSpots > 3}">spots-medium</c:when>
                                    <c:otherwise>spots-low</c:otherwise>
                                </c:choose>">
                            <c:choose>
                                <c:when test="${tour.availableSpots > 0}">
                                    ✅ Доступно мест: ${tour.availableSpots}
                                </c:when>
                                <c:otherwise>
                                    ❌ Мест нет
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>

    <!-- Статистика по странам -->
    <c:if test="${not empty countryStats}">
        <div class="country-stats">
            <h3>🌍 Статистика по странам</h3>
            <table class="stats-table">
                <thead>
                <tr>
                    <th>Страна</th>
                    <th>Количество туров</th>
                    <th>Средняя цена (€)</th>
                    <th>Доступных мест</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="entry" items="${countryStats}">
                    <tr>
                        <td><strong>${entry.key}</strong></td>
                        <td>${entry.value.totalTours}</td>
                        <td>${entry.value.avgPrice}</td>
                        <td>${entry.value.totalSpots}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>

    <a href="index.jsp" class="back-link">← Вернуться на главную</a>
</div>
</body>
</html>