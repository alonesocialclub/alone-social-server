set -e
./gradlew clean dockerPrepare dockerPush
$(aws ecr get-login --no-include-email --region ap-northeast-2)
ssh -i server.pem ec2-user@13.125.58.128 './update.sh'