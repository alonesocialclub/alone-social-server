set -e
$(aws ecr get-login --no-include-email --region ap-northeast-2)
./gradlew clean dockerPrepare dockerPush
ssh -i server.pem ec2-user@13.125.58.128 './update.sh'