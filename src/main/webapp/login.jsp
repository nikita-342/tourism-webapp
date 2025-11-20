<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Вход в систему - Туристическое агентство</title>
  <style>
    body {
      font-family: 'Arial', sans-serif;
      margin: 0;
      padding: 0;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      min-height: 100vh;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .login-container {
      background: white;
      padding: 3rem;
      border-radius: 15px;
      box-shadow: 0 10px 30px rgba(0,0,0,0.2);
      width: 100%;
      max-width: 400px;
    }
    .login-header {
      text-align: center;
      margin-bottom: 2rem;
    }
    .login-header h1 {
      color: #333;
      margin-bottom: 0.5rem;
    }
    .login-header p {
      color: #666;
    }
    .form-group {
      margin-bottom: 1.5rem;
    }
    label {
      display: block;
      margin-bottom: 0.5rem;
      color: #333;
      font-weight: bold;
    }
    input[type="text"],
    input[type="password"] {
      width: 100%;
      padding: 0.75rem;
      border: 2px solid #ddd;
      border-radius: 5px;
      font-size: 1rem;
      transition: border-color 0.3s ease;
      box-sizing: border-box;
    }
    input[type="text"]:focus,
    input[type="password"]:focus {
      outline: none;
      border-color: #667eea;
    }
    .login-button {
      width: 100%;
      padding: 0.75rem;
      background: #667eea;
      color: white;
      border: none;
      border-radius: 5px;
      font-size: 1.1rem;
      font-weight: bold;
      cursor: pointer;
      transition: background 0.3s ease;
    }
    .login-button:hover {
      background: #5a6fd8;
    }
    .error-message {
      background: #ffebee;
      color: #c62828;
      padding: 0.75rem;
      border-radius: 5px;
      margin-bottom: 1rem;
      text-align: center;
      border: 1px solid #ffcdd2;
    }
    .success-message {
      background: #e8f5e8;
      color: #2e7d32;
      padding: 0.75rem;
      border-radius: 5px;
      margin-bottom: 1rem;
      text-align: center;
      border: 1px solid #c8e6c9;
    }
    .back-link {
      display: block;
      text-align: center;
      margin-top: 1.5rem;
      color: #667eea;
      text-decoration: none;
    }
    .back-link:hover {
      text-decoration: underline;
    }
    .demo-accounts {
      margin-top: 2rem;
      padding: 1rem;
      background: #f8f9fa;
      border-radius: 5px;
      font-size: 0.9rem;
    }
    .demo-accounts h4 {
      margin-top: 0;
      color: #333;
    }
  </style>
</head>
<body>
<div class="login-container">
  <div class="login-header">
    <h1>🔐 Вход в систему</h1>
    <p>Туристическое агентство</p>
  </div>

  <c:if test="${not empty param.error}">
    <div class="error-message">
      ❌ Неверное имя пользователя или пароль
    </div>
  </c:if>

  <c:if test="${not empty param.message}">
    <div class="success-message">
      ✅ Регистрация успешна! Теперь вы можете войти.
    </div>
  </c:if>

  <form action="auth" method="post">
    <input type="hidden" name="action" value="login">

    <div class="form-group">
      <label for="username">Имя пользователя:</label>
      <input type="text" id="username" name="username" required
             value="${param.username}" placeholder="Введите ваш логин">
    </div>

    <div class="form-group">
      <label for="password">Пароль:</label>
      <input type="password" id="password" name="password" required
             placeholder="Введите ваш пароль">
    </div>

    <button type="submit" class="login-button">Войти</button>
  </form>

  <div class="demo-accounts">
    <h4>Тестовые аккаунты:</h4>
    <p><strong>admin / 1234</strong> - полный доступ</p>
    <p><strong>user1 / password1</strong> - обычный пользователь</p>
    <p><strong>manager / manager123</strong> - менеджер</p>
  </div>

  <a href="index.jsp" class="back-link">← Вернуться на главную</a>
</div>
</body>
</html>