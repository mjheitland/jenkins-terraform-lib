def call(Map config = [:]) {
    sh '''#!/usr/bin/env bash
        set -xeo pipefail
        ls -al $WORKSPACE/.artifacts
    '''
}
