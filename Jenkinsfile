pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh 'docker build -t weitalk-backend .'
            }
        }
        stage('Deploy') {
            steps {
                sh 'docker run -d -p 8080:8080 weitalk-backend'
            }
        }
    }
}