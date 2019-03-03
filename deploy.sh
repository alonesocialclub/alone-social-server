set -e
./gradlew clean bootJar
$(aws ecr get-login --no-include-email --region ap-northeast-2)
docker-compose build --no-cache api
docker-compose push api
ssh -i server.pem ec2-user@13.125.58.128 './update.sh'