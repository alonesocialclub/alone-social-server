./gradlew clean bootJar
$(aws ecr get-login --no-include-email --region ap-northeast-2)
docker-compose build --no-cache api
docker-compose push api
