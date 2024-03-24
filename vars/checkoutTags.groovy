def call(Map config = [:]) {
    // delete the entire workspace
    deleteDir()

    // checkout the branch that was triggering us
    git branch: 'main'
        credentialsID: 'jenkins_repo_access'
        url: 'git@github.com:mjheitland/terraform-aws-example.git'
    
    sh '''#!/usr/bin/env bash
        set -xeo pipefail
        git pull origin $BRANCH_NAME --tags
    '''
}
