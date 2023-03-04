pipeline {
  agent any
  tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven3"
  }
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
