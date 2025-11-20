<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Туристическое агентство</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            color: #333;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem;
        }
        .header {
            text-align: center;
            color: white;
            margin-bottom: 3rem;
        }
        .header h1 {
            font-size: 3rem;
            margin-bottom: 0.5rem;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
        }
        .header p {
            font-size: 1.2rem;
            opacity: 0.9;
        }
        .button {
            display: inline-block;
            padding: 1rem 2rem;
            background: #ff6b6b;
            color: white;
            text-decoration: none;
            border-radius: 50px;
            font-size: 1.1rem;
            font-weight: bold;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(255,107,107,0.4);
            margin: 0.5rem;
        }
        .button:hover {
            background: #ff5252;
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(255,107,107,0.6);
        }
        .features {
            display: flex;
            justify-content: center;
            gap: 2rem;
            margin-top: 3rem;
            flex-wrap: wrap;
        }
        .feature {
            background: white;
            padding: 1.5rem;
            border-radius: 10px;
            text-align: center;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            flex: 1;
            min-width: 200px;
            max-width: 300px;
        }
        .feature h3 {
            color: #667eea;
            margin-top: 0;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>🌍 Туристическое агентство</h1>
        <p>Откройте для себя мир с нашими эксклюзивными турами</p>
        <div>
            <a class="button" href="login.jsp">Войти в систему</a>
            <a class="button" href="tours?type=Пляжный отдых">Пляжный отдых</a>
            <a class="button" href="tours?type=Экскурсионный">Экскурсии</a>
        </div>
    </div>

    <div class="features">
        <div class="feature">
            <h3>🚀 Быстро</h3>
            <p>Мгновенное бронирование туров онлайн</p>
        </div>
        <div class="feature">
            <h3>💰 Выгодно</h3>
            <p>Лучшие цены на рынке туристических услуг</p>
        </div>
        <div class="feature">
            <h3>⭐ Надежно</h3>
            <p>Гарантия качества и поддержка 24/7</p>
        </div>
        <div class="feature">
            <h3>🌎 Разнообразие</h3>
            <p>Более 50 направлений по всему миру</p>
        </div>
    </div>
</div>
</body>
</html>