pipeline {
  agent any
  
  tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven3"
  }
  stages {
    stage('Clone repository') { 
            steps { 
                script{
                   retry(count: 3) {
                      checkout scm
                   }
               
                }
            }
        }
    stage('Copy application.yml to directory'){
      steps {
        echo 'Copy file'
        configFileProvider(
            [configFile(fileId: '93b8fbf4-4cba-47da-bc30-7102ed4d7524', variable: 'Config')]) {
             // some block
              
   
              sh "mkdir -p sods-application/src/main/resources"
              sh "touch sods-application/src/main/resources/application.yml"
              sh "cp ${env.Config} sods-application/src/main/resources/application.yml"
              
             
        }
        
      }
    }
    
    stage('Build Jar') {
      steps {
        echo 'Package Jar with mvn'
        sh 'mvn clean package'
        
      }
    }
     
    stage('Docker Build Image') {
      steps {
        echo 'Image'
          script{
                 sh 'docker build -t backenddocker .'
                }
        
      }
    }
    stage('Push To ECR') {
      steps {
        script{
           retry(count: 3) {
               withAWS(credentials: 'aws') {
                 //sh 'aws iam get-user'
                sh 'aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws/i4f7p8k7'
                sh 'docker tag backenddocker:latest public.ecr.aws/i4f7p8k7/backenddocker:latest'
                sh 'docker push public.ecr.aws/i4f7p8k7/backenddocker:latest'
            
                }
            }
         
        }
      }
    }
    stage('Remove Local Docker Image') {
      steps {
        echo 'Remove Local Docker Image'
          script{
                 sh 'docker rmi backenddocker'
                 sh 'docker rmi public.ecr.aws/i4f7p8k7/backenddocker:latest'
                }
      }
    }
     stage('SSH to AWS EC2') {
      steps {
        echo 'SSH'
        retry(count: 3) {
          withCredentials([awsCredentials(credentialsId: 'backend')]) {
            sshagent(credentials:['ssh']){
              sh '''
                set -ev
                ssh -o StrictHostKeyChecking=no -l ec2-user ec2-13-113-55-21.ap-northeast-1.compute.amazonaws.com << EOF
                docker stop backend || true
                docker rm backend || true
                docker rmi -f public.ecr.aws/i4f7p8k7/backenddocker || true
                docker pull public.ecr.aws/i4f7p8k7/backenddocker:latest
                docker run -t -i -d -e AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID -e AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY -p  8888:8888 --name="backend" public.ecr.aws/i4f7p8k7/backenddocker:latest
                exit
              '''
            }
          }
        }
        
        
      }
    }
    
    
    
  }
  
  post {
    always {
      echo 'Hello World'
    }
  }
}
