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
                 app = docker.build("backenddocker")
                }
        
      }
    }
    stage('Push To ECR') {
      steps {
        sh 'rm -f ~/.dockercfg ~/.docker/config.json || true'
        script{
          withAWS(credentials: 'aws', region: 'ap-northeast') {
            sh 'aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws/i4f7p8k7'
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
