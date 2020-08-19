https://www.http4k.org/tutorials/tdding_http4k/

A simple test with http4k - also comparing the different testing libraries (assertk, atrium, kluent, stdlib, strikt) available for kotlin (see https://github.com/jgroeneveld/http4ktest/tree/master/src/test/kotlin/http4ktest/adapter/api)

## Using google jib to deploy

- Build docker image and push `./gradlew jib --image gcr.io/PROJECT/NAME`
- Deploy: `gcloud beta run deploy PROJECT --image gcr.io/PROJECT/NAME --concurrency=default --allow-unauthenticated`

## Local image

- Build docker image locally: `./gradlew jibDockerBuild`
- Run locally `docker run -p 9000:9000 NAME`

## Decisions

- Use klaxon for json instead of http4k lenses.
