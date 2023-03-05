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
                checkout scm
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
          withAWS(credentials: 'aws') {
             //sh 'aws iam get-user'
            sh 'aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws/i4f7p8k7'
            sh 'docker tag backenddocker:latest public.ecr.aws/i4f7p8k7/backenddocker:latest'
            sh 'docker push public.ecr.aws/i4f7p8k7/backenddocker:latest'
            
          }
        }
      }
    }
     stage('SSH') {
      steps {
        echo 'SSH'
          script{
                 sh 'ssh -i "docker.pem" ec2-user@ec2-13-113-55-21.ap-northeast-1.compute.amazonaws.com'
                 sh 'docker rm $(docker stop $(docker ps -a -q --filter ancestor=public.ecr.aws/i4f7p8k7/backenddocker --format="{{.ID}}"))'
                 sh 'docker pull public.ecr.aws/i4f7p8k7/backenddocker:latest'
                 sh 'docker run -t -i -d -p 8888:8888 public.ecr.aws/i4f7p8k7/backenddocker:latest'
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
