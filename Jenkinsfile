pipeline{
    agent any
    tools {
        maven 'Maven-3.9.6'
        jdk 'Java-17'
    }
    stages {
        stage ('git-repo-checkout') {
            checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/placidenduwayo1/k8s-ingress-kafka-avro-aepc-back.git']])
             dir('../cacmer-bs-ms-modules/k8s-ingress-kafka-avro-clean-archi-cacmer-bs-ms-account'){
                sh 'mvn clean install'
           }
        }
    }
}