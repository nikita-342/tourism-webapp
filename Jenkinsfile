pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven-3.9'
    }

    options {
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    triggers {
        pollSCM('H/5 * * * *')  // Проверять GitHub каждые 5 минут
        // ИЛИ для вебхуков: githubPush()
    }

    stages {
        stage('Checkout from GitHub') {
            steps {
                echo '📥 Получение кода из GitHub...'
                checkout scm

                // Дополнительная информация о коммите
                bat 'git log -1 --oneline'
                bat 'git branch -a'
            }
        }

        stage('Build') {
            steps {
                echo '🔨 Компиляция проекта Maven...'
                bat 'mvn -B clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                echo '🧪 Запуск всех тестов...'
                bat 'mvn -B test'
            }
            post {
                always {
                    // Публикация результатов JUnit тестов
                    junit '**/target/surefire-reports/*.xml'

                    // Публикация отчета о покрытии кода
                    publishHTML([
                        reportDir: 'target/site/jacoco',
                        reportFiles: 'index.html',
                        reportName: 'Code Coverage Report'
                    ])
                }
            }
        }

        stage('Build WAR Package') {
            steps {
                echo '📦 Создание WAR файла...'
                bat 'mvn -B package -DskipTests'
            }
            post {
                success {
                    // Архивация артефакта для скачивания
                    archiveArtifacts artifacts: 'target/*.war', fingerprint: true
                    echo '✅ WAR файл готов для скачивания'
                }
            }
        }

        stage('Quality Gate') {
            when {
                anyOf {
                    branch 'main'
                    branch 'master'
                    branch 'develop'
                }
            }
            steps {
                echo '📊 Проверка качества кода...'
                bat 'mvn -B checkstyle:checkstyle'
                // Здесь можно добавить SonarQube анализ
            }
        }

        stage('Deploy to Server') {
            when {
                branch 'main'
            }
            steps {
                echo '🚀 Автоматический деплой...'
                script {
                    // Здесь логика деплоя на сервер
                    echo 'Деплой будет выполнен для ветки main'
                    // Например: scp target/*.war user@server:/path/to/tomcat/webapps/
                }
            }
        }
    }

    post {
        always {
            echo "🔚 Сборка #${env.BUILD_NUMBER} завершена"
            echo "📊 Отчеты доступны по ссылке: ${env.BUILD_URL}"
        }
        success {
            echo '🎉 Сборка УСПЕШНА! Все тесты пройдены.'
        }
        failure {
            echo '❌ Сборка ПРОВАЛЕНА! Проверьте ошибки.'
        }
        unstable {
            echo '⚠️ Сборка НЕСТАБИЛЬНА! Упали некоторые тесты.'
        }
    }
}