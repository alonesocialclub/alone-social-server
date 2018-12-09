./gradlew clean build
$(aws ecr get-login --no-include-email --region ap-northeast-1)
docker-compose push
