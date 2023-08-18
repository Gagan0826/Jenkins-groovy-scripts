pipeline{
    agent any
    stages{
        stage("check for changes"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'jenkins-github', url: 'https://github.com/Gagan0826/ansible-config.git']])
            }
        }
        stage("git clone"){
            steps{
                git branch: 'main', credentialsId: 'jenkins-github', url: 'https://github.com/Gagan0826/ansible-config.git'
            }
        }
        stage("ansible"){
            steps{
                ansiblePlaybook credentialsId: 'w1-key-file', disableHostKeyChecking: true, installation: 'Ansible', inventory: '/var/lib/jenkins/workspace/ansible-config/inventory', playbook: '/var/lib/jenkins/workspace/ansible-config/main.yml'
            }
        }
    }
}
