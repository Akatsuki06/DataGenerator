
if [[ $TRAVIS_PULL_REQUEST == "false" ]] && [[ $TRAVIS_BRANCH == "master" ]];
then
  echo "Build and Push docker image"
  echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
  export TAG=`if [ "$TRAVIS_BRANCH" == "master" ]; then echo "stable"; else echo $TRAVIS_BRANCH; fi`
  export DOCKER_IMAGE_NAME=surazneg/data-generator
  docker build -t $DOCKER_IMAGE_NAME:$TRAVIS_COMMIT .
  docker tag $DOCKER_IMAGE_NAME:$TRAVIS_COMMIT $DOCKER_IMAGE_NAME:$TAG
  docker push $DOCKER_IMAGE_NAME:$TAG
fi