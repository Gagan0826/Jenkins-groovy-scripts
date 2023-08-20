pipeline {
    agent any
    stages {
        stage("Removing all containers") {
            steps {
                script {
                    remove_all_containers='''docker container rm -f $(docker container ls -aq)'''
                    remove_all_images='''docker rmi $(docker image ls -q)'''
                    sshPublisher(
                    publishers: [
                        sshPublisherDesc(
                            configName: 'ec2-rds',
                            transfers: [
                                sshTransfer(
                                    execCommand: remove_all_containers
                                    )
                                ]
                            )
                        ]   
                    )   
                }
            }
        }
        stage("deleteing All images") {
            steps {
                script {
                    sshPublisher(
                    publishers: [
                        sshPublisherDesc(
                            configName: 'ec2-rds',
                            transfers: [
                                sshTransfer(
                                    execCommand:remove_all_images
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
