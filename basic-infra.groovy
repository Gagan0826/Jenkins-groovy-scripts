pipeline {
    agent any
    stages {
        stage("git clone") {
            steps {
                git(
                    
                    branch: 'main',
                    credentialsId:'jenkins-github',
                    url: 'https://github.com/Gagan0826/TF-once.git'
                )
            }
        }
        
        stage("terraform init"){
            steps{
                sh 'terraform init'
            }
        }
        
         stage("terraform apply"){
            steps{
                sh 'terraform apply --auto-approve'
            }
        }
    }
}
