pipeline {
    agent any

    tools {
        maven 'Maven-3.9.12'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/harshvibhoresharma/shopping-app.git'
            }
        }

        stage('Build and Run Tests') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('Trivy Security Scan') {
            steps {
                bat '"C:\\Program Files\\trivy.exe" fs --severity HIGH,CRITICAL --exit-code 1 .'
            }
        }

    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }

}
