pipeline {
    agent any
    stages {
        stage("Docker login") {
            steps {
                script {
                    sshPublisher(
                    publishers: [
                        sshPublisherDesc(
                            configName: 'ec2-rds',
                            transfers: [
                                sshTransfer(
                                    execCommand: '''
                                        aws ecr get-login-password --region ap-south-1 | docker login --username AWS --password-stdin 663839140840.dkr.ecr.ap-south-1.amazonaws.com
                                        '''
                                    )
                                ]
                            )
                        ]   
                    )   

                }
            }
        }
        stage("Pull Image From ECR") {
            steps {
                script {
                    sshPublisher(
                    publishers: [
                        sshPublisherDesc(
                            configName: 'ec2-rds',
                            transfers: [
                                sshTransfer(
                                    execCommand: '''
                                        docker pull 663839140840.dkr.ecr.ap-south-1.amazonaws.com/python-web-app:v5
                                        '''
                                    )
                                ]
                            )
                        ]   
                    )   

                }
            }
        }
        stage("Run the image") {
            steps {
                script {
                    sshPublisher(
                    publishers: [
                        sshPublisherDesc(
                            configName: 'ec2-rds',
                            transfers: [
                                sshTransfer(
                                    execCommand: '''
                                        docker run -d -p 80:80 663839140840.dkr.ecr.ap-south-1.amazonaws.com/python-web-app:v2
                                        '''
                                    )
                                ]
                            )
                        ]   
                    )   
                }
            }
        }
    }
}
