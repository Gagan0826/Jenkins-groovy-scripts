pipeline{
    agent any
    stages{
        stage("checkout git repo"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'jenkins-github', url: 'https://github.com/Gagan0826/web-app.git']])
            }
        }
        stage("clone web-app from git"){
            steps{
                git branch: 'main', credentialsId: 'jenkins-github', url: 'https://github.com/Gagan0826/web-app.git'
            }
        }

        stage("docker build"){
            steps{
                sh "docker build -t web-app-image ."
            }
        }
        
        stage("login to ecr"){
            steps{
                sh "aws ecr get-login-password --region ap-south-1 | docker login --username AWS --password-stdin 663839140840.dkr.ecr.ap-south-1.amazonaws.com"
            }
        }
         
        stage("tag image"){
            steps{
                sh "docker tag web-app-image:latest 663839140840.dkr.ecr.ap-south-1.amazonaws.com/python-web-app:''v\$(expr \${BUILD_NUMBER} - 40)''"
            }
        }
        stage("push to ecr"){
            steps{
                sh "docker push 663839140840.dkr.ecr.ap-south-1.amazonaws.com/python-web-app:''v\$(expr \${BUILD_NUMBER} - 40)''"
            }
        }
    }
}
