# angular-mongo-example

http://localhost:8181/swagger-ui/#/main-controller
all 3 csv files take 1h 30min to insert into db

externalId: 004, 573
one csv import journey takes: 25 min


--------
in the base directory:
mvn -f backend\citybike clean package
docker-compose up -d

docker-compose takes around 30 min
localhost:8282 is up and in background journeys are imported about 30 min

build + start up + import take 1h


externalId: 004

-----------
to import all 3 csv takes 1h 30 min, the journey page is not rendering well

