https://www.http4k.org/tutorials/tdding_http4k/

## Using google jib to deploy

- Build docker image and push `./gradlew jib --image gcr.io/PROJECT/NAME`
- Deploy: `gcloud beta run deploy PROJECT --image gcr.io/PROJECT/NAME --concurrency=default --allow-unauthenticated`

## Local image

- Build docker image locally: `./gradlew jibDockerBuild`

## Decisions

- Use klaxon for json instead of http4k lenses.
