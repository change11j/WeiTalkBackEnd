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
                                docker stop $(docker ps -a -q --filter ancestor=weitalk-backend --format="{{.ID}}") || true
                                docker rm $(docker ps -a -q --filter ancestor=weitalk-backend --format="{{.ID}}") || true
                                CONTAINER_ID=$(docker run -d -P weitalk-backend)
                                echo "Container ID: $CONTAINER_ID"
                                EXPOSED_PORT=$(docker port $CONTAINER_ID 80 | cut -d ':' -f 2)
                                echo "Application is running on port $EXPOSED_PORT"
                            '''
                }
        }
    }
}