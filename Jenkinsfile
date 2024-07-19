pipeline {
    agent any
    tools {
        maven 'Maven'
    }
    environment {
        DOCKER_HOST = 'tcp://YOUR_LIGHTSAIL_IP:2375'
        IMAGE_NAME = 'weitalk-backend'
        CONTAINER_NAME = 'weitalk-backend-container'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Docker Build and Push') {
            steps {
                script {
                    docker.withServer(env.DOCKER_HOST) {
                        def customImage = docker.build("${env.IMAGE_NAME}:${env.BUILD_NUMBER}")
                        customImage.push()
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    docker.withServer(env.DOCKER_HOST) {
                        sh """
                            docker stop ${CONTAINER_NAME} || true
                            docker rm ${CONTAINER_NAME} || true
                            docker run -d -p 8080:8080 --name ${CONTAINER_NAME} ${IMAGE_NAME}:${BUILD_NUMBER}
                        """
                    }
                }
            }
        }
    }
}