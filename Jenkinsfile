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
                    junit '**/target/surefire-reports/*.xml'
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
                    archiveArtifacts artifacts: 'target/*.war', fingerprint: true
                    echo '✅ WAR файл готов для скачивания'
                }
            }
        }
    }

    post {
        always {
            echo "🔚 Сборка #${env.BUILD_NUMBER} завершена"
        }
        success {
            echo '🎉 Сборка УСПЕШНА! Все тесты пройдены.'
        }
        failure {
            echo '❌ Сборка ПРОВАЛЕНА! Проверьте ошибки.'
        }
    }
}