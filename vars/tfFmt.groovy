def call(Map config = [:]) {
    sh '''#!/usr/bin/env bash
        set -xeo pipefail
        aws --version
        terraform --version
        terraform fmt -recursive -check
    '''
}
