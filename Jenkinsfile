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
            ecrLogin()
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
