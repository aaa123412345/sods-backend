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
        sh 'rm -f ~/.dockercfg ~/.docker/config.json || true'
        withEnv(["AWS_ACCESS_KEY_ID=${env.AWS_ACCESS_KEY_ID}", "AWS_SECRET_ACCESS_KEY=${env.AWS_SECRET_ACCESS_KEY}", "AWS_DEFAULT_REGION=${env.AWS_DEFAULT_REGION}"]) {
          sh 'docker login -u AWS -p $(aws ecr-public get-login-password --region us-east-1) public.ecr.aws/i4f7p8k7'
          sh 'docker tag backenddocker:latest public.ecr.aws/i4f7p8k7/backenddocker:latest'
          sh 'docker push public.ecr.aws/i4f7p8k7/backenddocker:latest'
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
