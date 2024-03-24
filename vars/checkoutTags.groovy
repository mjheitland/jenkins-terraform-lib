def call(Map config = [:]) {
    // delete the entire workspace
    deleteDir()

    // checkout the branch that was triggering us
    checkout scm

    sh '''#!/usr/bin/env bash
        set -xeo pipefail
        git pull origin $BRANCH_NAME --tags
    '''
}
