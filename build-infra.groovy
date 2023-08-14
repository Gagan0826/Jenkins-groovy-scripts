pipeline{
    agent any
    stages{
        stage("github-repo-triggered"){
            steps{
                checkout scmGit(branches: [[name: '*/master']], browser: github(''), extensions: [], userRemoteConfigs: [[credentialsId: 'jenkins-github', url: 'https://github.com/Gagan0826/Terraform-infra.git']])
            }
        }
        stage("git clone"){
            steps{
            git credentialsId: 'jenkins-github', url: 'https://github.com/Gagan0826/Terraform-infra.git'
            }
        }
        stage("Terraform init"){
            steps{
            sh "terraform init"
            }
        }
        stage("Terraform apply"){
            steps{
            sh "terraform apply --auto-approve"
            }
        }
    }
}
