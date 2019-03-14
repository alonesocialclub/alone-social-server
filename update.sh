URL="459648971904.dkr.ecr.ap-northeast-2.amazonaws.com/alone-social-server"
docker rm $(docker stop $(docker ps -a -q --filter ancestor=$URL --format="{{.ID}}"))
docker rmi $URL
$(aws ecr get-login --no-include-email --region ap-northeast-2)
docker pull $URL
docker run -p 80:8080 -d $URL
