def call(Map config = [:]) {
    sh '''#!/usr/bin/env bash
        set -xeo pipefail
        rm -rf $WORKSPACE/.artifacts
        mkdir $WORKSPACE/.artifacts
        cp Jenkinsfile $WORKSPACE/.artifacts/Jenkinsfile
    '''
}
