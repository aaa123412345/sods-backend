pipeline {
  agent any
  
  stages {
    stage('Build Jar') {
      steps {
        echo 'Package Jar with mvn'
        sh 'mvn clean package'
      }
    }
    stage('Docker Image') {
      steps {
        echo 'Image'
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
