#  alone-social-server

[![codecov](https://codecov.io/gh/alonesocialclub/alone-social-server/branch/master/graph/badge.svg)](https://codecov.io/gh/alonesocialclub/alone-social-server)

server for [https://alone.social](https://alone.social)

## development
```bash
$ ./gradlew bootRun
```

## run test
```bash
$ ./gradlew check

```

## deploy

If master branch has pushed, Github actions will be triggered

## API doc

http://52.78.145.107/docs/index.html

## health check

http://52.78.145.107/actuator/health
## how secret files (like application-prod.yml) are handled
 
```
# enc
gpg --symmetric --cipher-algo AES256 src/main/resources/application-prod.yml
gpg --symmetric --cipher-algo AES256 src/main/resources/alone-social-club-firebase-adminsdk.json
gpg --symmetric --cipher-algo AES256 server.pem

# dec
gpg --quiet --batch --yes --decrypt --passphrase=1234  \
--output src/main/resources/application-prod.yml src/main/resources/application-prod.yml.gpg
```
If you update production secrets, you also have to gpg files using above commands
