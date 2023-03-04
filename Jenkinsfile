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
        sh 'docker build -t public.ecr.aws/i4f7p8k7/backenddocker .'
        
      }
    }
    stage('Push') {
      steps {
       echo 'eeee'
      }
    }
  }
  post {
    always {
      echo 'Hello World'
    }
  }
}
