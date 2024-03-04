def call(Map config = [:]) {
    sh '''#!/usr/bin/env bash
        set -xeo pipefail
        rm -rf .artifacts
        mkdir .artifacts
        cp Jenkinsfile .artifacts/Jenkinsfile
    '''
}
