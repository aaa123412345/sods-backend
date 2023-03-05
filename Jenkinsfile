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
        configFileProvider(
            [configFile(fileId: '93b8fbf4-4cba-47da-bc30-7102ed4d7524', variable: 'Config')]) {
             // some block
              echo " =========== ^^^^^^^^^^^^ Reading config from pipeline script "
              sh "cat ${env.Config}"
              sh "cp ${env.Config} sods-application/src/main/resources/application.yml"
              echo " =========== ~~~~~~~~~~~~ ============ "
            //sh 'mvn clean package'
        }
        
      }
    }
     /*
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
     stage('SSH to AWS EC2') {
      steps {
        echo 'SSH'
        sshagent(credentials:['ssh']){
          sh '''
             set -ev
             ssh -o StrictHostKeyChecking=no -l ec2-user ec2-13-113-55-21.ap-northeast-1.compute.amazonaws.com << EOF
             docker rm $(docker stop $(docker ps -a -q --filter ancestor=public.ecr.aws/i4f7p8k7/backenddocker --format="{{.ID}}"))
             docker pull public.ecr.aws/i4f7p8k7/backenddocker:latest
             docker run -t -i -d -p 8888:8888 public.ecr.aws/i4f7p8k7/backenddocker:latest
             exit
          '''
       
        }
        
        
      }
    }*/
    
    
    
  }
  
  post {
    always {
      echo 'Hello World'
    }
  }
}
