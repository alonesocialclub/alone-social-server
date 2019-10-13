#  alone-social-server

[![CircleCI](https://circleci.com/gh/alonesocialclub/alone-social-server.svg?style=svg)](https://circleci.com/gh/alonesocialclub/alone-social-server)
[![codecov](https://codecov.io/gh/alonesocialclub/alone-social-server/branch/master/graph/badge.svg)](https://codecov.io/gh/alonesocialclub/alone-social-server)

server for [https://alone.social](https://alone.social)

## development
```bash
$ ./gradlew bootRun
```FOOBAR


## run test
```bash
$ ./gradlew check

```

## deploy

```bash
$ ./deploy.sh
```

## API doc

http://52.78.145.107/docs/index.html

## how secret files (application-prod.yml) are handled
 
gpg --symmetric --cipher-algo AES256 src/main/resources/application-prod.yml

gpg --quiet --batch --yes --decrypt --passphrase=1234  \
--output src/main/resources/application-prod.yml src/main/resources/application-prod.yml.gpg