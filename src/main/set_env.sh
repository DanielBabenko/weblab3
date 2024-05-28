#!/bin/bash

setEnvVar() {
    echo -n "$1 [${2:-}]: "
    read value
    if [ -z "$value" ];then
        value="$2"
    fi
    export "$1=$value"
}

setEnvVar "WEB3_DB_HOST" "localhost"
setEnvVar "WEB3_DB_PORT" "5432"
setEnvVar "WEB3_DB_USER" ""
setEnvVar "WEB3_DB_PASSWORD" ""