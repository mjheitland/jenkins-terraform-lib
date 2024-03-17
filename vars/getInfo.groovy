def call(Map config = [:]) {
    sh '''#!/usr/bin/env bash
        set -xeo pipefail
        pwd
        ls -al
        git --version
        git remote -v
        git tag
        echo "branch: $BRANCH_NAME"
        uname -a
        env | sort
        python3 --version
        aws --version
        terraform --version
    '''
}
