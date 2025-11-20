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
        pollSCM('H/5 * * * *')
    }

    stages {
        stage('Checkout from GitHub') {
            steps {
                echo '📥 Получение кода из GitHub...'
                checkout scm
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

                    // Сообщение о покрытии кода (без HTML отчета)
                    echo '📊 Отчет о покрытии кода доступен в target/site/jacoco/index.html'
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