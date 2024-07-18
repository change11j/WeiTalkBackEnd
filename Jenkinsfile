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
                          # 停止所有相關的容器
                                     docker stop $(docker ps -a -q --filter ancestor=weitalk-backend --format="{{.ID}}") || true

                                     # 刪除所有相關的容器
                                     docker rm $(docker ps -a -q --filter ancestor=weitalk-backend --format="{{.ID}}") || true

                                     # 等待一段時間，確保端口釋放
                                     sleep 10

                                     # 檢查端口是否仍被佔用
                                     if lsof -i :8081; then
                                         echo "Port 8081 is still in use. Attempting to kill the process."
                                         sudo fuser -k 8081/tcp
                                         sleep 5
                                     fi

                                     # 運行新的容器
                                     docker run -d -p 8081:80 weitalk-backend
                    '''
                }
        }
    }
}