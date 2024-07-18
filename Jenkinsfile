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
                    sh '''
                        sudo docker stop $(sudo docker ps -q --filter ancestor=weitalk-backend) || true
                        sudo docker rm $(sudo docker ps -aq --filter ancestor=weitalk-backend) || true
                        sudo docker run -d -p 8081:80 weitalk-backend
                    '''
                }
        }
    }
}