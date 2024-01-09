pipeline{
    agent any
    tools {
        maven 'Maven-3.9.6'
        jdk 'Java-17'
    }
    stages {
        stage('build') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/placidenduwayo1/k8s-ingress-kafka-avro-cacmer-back.git']])
                dir('./cacmer-bs-ms-modules/k8s-ingress-kafka-avro-clean-archi-cacmer-bs-ms-account/'){
                    sh 'mvn clean install'
                }
                 dir('./cacmer-bs-ms-modules/k8s-ingress-clean-archi-cacmer-risk-evaluator/'){
                    sh 'mvn clean install'
                }
                dir('./cacmer-bs-ms-modules/k8s-ingress-kafka-avro-clean-archi-cacmer-bs-ms-address/'){
                    sh 'mvn clean install'
                }
            }
            post {
                success {
                   dir('./cacmer-bs-ms-modules/k8s-ingress-kafka-avro-clean-archi-cacmer-bs-ms-account/'){
                        archiveArtifacts '**/target/*.jar'
                    }
                   dir('./cacmer-bs-ms-modules/k8s-ingress-clean-archi-cacmer-risk-evaluator/'){
                        archiveArtifacts '**/target/*.jar'
                    }
                    dir('./cacmer-bs-ms-modules/k8s-ingress-kafka-avro-clean-archi-cacmer-bs-ms-address/'){
                        archiveArtifacts '**/target/*.jar'
                    }
                }
            }
        }
        stage ('test'){
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/placidenduwayo1/k8s-ingress-kafka-avro-cacmer-back.git']])
                dir('./cacmer-bs-ms-modules/k8s-ingress-kafka-avro-clean-archi-cacmer-bs-ms-account/'){
                    sh 'mvn test'
                }
                dir('./cacmer-bs-ms-modules/k8s-ingress-clean-archi-cacmer-risk-evaluator/'){
                    sh 'mvn test'
                }
                dir('./cacmer-bs-ms-modules/k8s-ingress-kafka-avro-clean-archi-cacmer-bs-ms-address/'){
                        sh 'mvn test'
                }
            }
        }
        stage('docker-build'){
            steps {
                script {
                    sh 'docker compose -f ./docker-images-deploy/cacm-docker-compose.yml down'
                    sh 'docker compose -f ./docker-images-deploy/cacm-docker-compose.yml build'
                    sh 'docker system prune -f'
                }
            }
        }
    }
}