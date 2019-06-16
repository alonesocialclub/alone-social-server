set -e
$(aws ecr get-login --no-include-email --region ap-northeast-2)
./gradlew clean dockerPrepare dockerPush
ssh -i server.pem ec2-user@52.78.145.107 './update.sh'