pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven-3.9'
    }

    stages {
        stage('Checkout') {
            steps {
                echo '📥 Получение кода из Git...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '🔨 Компиляция проекта...'
                bat 'mvn -B clean compile'
            }
        }

        stage('Test') {
            steps {
                echo '🧪 Запуск тестов...'
                bat 'mvn -B test'
            }
        }

        stage('Package') {
            steps {
                echo '📦 Создание WAR архива...'
                bat 'mvn -B package -DskipTests'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.war', fingerprint: true
                    echo '✅ WAR файл успешно создан!'
                }
            }
        }
    }

    post {
        always {
            echo '🏁 Сборка завершена!'
        }
        success {
            echo '🎉 Сборка успешно завершена!'
        }
        failure {
            echo '❌ Сборка завершилась с ошибками!'
        }
    }
}