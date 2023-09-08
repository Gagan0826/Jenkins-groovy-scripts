pipeline {
    agent any
    stages {
        stage("Docker login") {
            steps {
                script {
                    docker_image="663839140840.dkr.ecr.ap-south-1.amazonaws.com/python-web-app:v2"
                    docker_login = "aws ecr get-login-password --region ap-south-1 | docker login --username AWS --password-stdin 663839140840.dkr.ecr.ap-south-1.amazonaws.com"
                    docker_image_pull = "docker pull $docker_image"
                    docker_run = "docker run -d -p 80:80 $docker_image"

                    sshPublisher(
                        publishers: [
                            sshPublisherDesc(
                                configName: 'ec2-rds',
                                transfers: [
                                    sshTransfer(
                                        execCommand: docker_login)
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
                                        execCommand: docker_image_pull)
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
                                        execCommand: docker_run)
                                ]
                            )
                        ]
                    )
                }
            }
        }
    }
}
