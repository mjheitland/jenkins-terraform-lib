def call(Map config = [:]) {
    if (!config.nextVersion) {
        error("Error: please add mandatory parameter 'nextVersion' to the function call, e.g. addTags(nextVersion: \"1.2.3\")")
        exit 1
    }
    env.NEXT_VERSION = config.nextVersion
    if (!config.gitUserName) {
        error("Error: please add mandatory parameter 'gitUserName' to the function call, e.g. addTags(gitUserName: \"mjheitland\")")
        exit 1
    }
    env.GIT_USER_NAME = config.gitUserName
    if (!config.gitUserEmail) {
        error("Error: please add mandatory parameter 'gitUserEmail' to the function call, e.g. addTags(gitUserEmail: \"mjheitland@gmail.com\")")
        exit 1
    }
    env.GIT_USER_EMAIL = config.gitUserEmail

    sh '''#!/usr/bin/env bash
        set -xeo pipefail
        echo "next version: $NEXT_VERSION"
        major_version="$(cut -d'.' -f1 <<<"${NEXT_VERSION}")"
        minor_version="$(cut -d'.' -f2 <<<"${NEXT_VERSION}")"
        patch_version="$(cut -d'.' -f3 <<<"${NEXT_VERSION}")"

        git config --local user.name $GIT_USER_NAME
        git config --local user.email $GIT_USER_EMAIL

        git push origin ":${major_version}" || true
        git push origin ":${major_version}.${minor_version}" || true
        git push origin ":${major_version}.${minor_version}.${patch_version}" || true
        git tag -d "${major_version}" || true
        git tag -d "${major_version}.${minor_version}" || true
        git tag -d "${major_version}.${minor_version}.${patch_version}" || true

        git tag -a "${major_version}" -m "Release ${major_version}"
        git tag -a "${major_version}.${minor_version}" -m "Release ${major_version}.${minor_version}"
        git tag -a "${major_version}.${minor_version}.${patch_version}" -m "Release ${major_version}.${minor_version}.${patch_version}"
        git push origin "${major_version}"
        git push origin "${major_version}.${minor_version}"
        git push origin "${major_version}.${minor_version}.${patch_version}"
    '''
}
