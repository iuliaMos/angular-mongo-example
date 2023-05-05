# Helsinki city bike web application using Java, Spring Boot, Docker, MongoDB and Angular 


## Description
This web application displays data from journeys made from city bikes in the Helsinki, stations and also offers possibility to add new entries.
This project consists of two parts: Backend using Java, Spring Boot, MongoDB and Frontend using Angular.

The dataset is owned by City Bike Finland and it can be downloaded from the following:
 - https://dev.hsl.fi/citybikes/od-trips-2021/2021-05.csv
 - https://dev.hsl.fi/citybikes/od-trips-2021/2021-06.csv
 - https://dev.hsl.fi/citybikes/od-trips-2021/2021-07.csv

The information about Helsinki Region Transport’s (HSL) city bicycle stations:
- Dataset: https://opendata.arcgis.com/datasets/726277c507ef4914b0aec3cbcfcbfafc_0.csv
- License and information: https://www.avoindata.fi/data/en/dataset/hsl-n-kaupunkipyoraasemat/resource/a23eef3a-cc40-4608-8aa2-c730d17e8902

## Pre requirements
Pre requirements for starting the application: Java and maven installed (Java 15 and maven 3.6.3), Docker and ports available: 8181, 8282, 27017.
Also, when running docker-compose and starting the application a connection to the internet is required.

## Installation:

1. Clone the repository `git clone https://github.com/iuliaMos/angular-mongo-example.git`
2. Run `mvn -f backend\citybike clean package` in the main directory (requires Java 15 and maven 3.6.3)
3. Run `docker-compose up -d` in the main directory (requires having Docker installed)
   - It will start one network with 3 services: mongo 4.4, backend app and frontend app
   
During the installation 2 Docker images will be created, Backend app image takes several minutes to build but Frontend app image uses Angular and will take up to 30 min to download [Node](https://nodejs.org/en).
After docker-compose command finishes it is required to wait several minutes in order for tomcat to start and angular app on [ngnix](https://www.nginx.com/).
The backend app is configured to download stations csv and journeys(just 2021-05.csv) on startup. It can take another 30 minutes to import one csv journeys data in background. DB import is skipped if stations are already imported.
Also, the frontend app can be accessed at: http://localhost:8282 it uses the backend as soon as the Tomcat starts, but it shows partial data until all journeys are imported.
The home page displays a map([OpenLayers](https://openlayers.org/)) with all the stations on the map.
So the build + start up + import could take 1h.

## Application details
Frontend app can be accessed at : http://localhost:8282 \
Backend app can be accessed at : http://localhost:8181 \
The REST API documentation can be found at: http://localhost:8181/swagger-ui/#/main-controller. \
The application contains a following menu:
   - Home - displays a map with all stations and when a station icon is clicked a popup with station name will be shown.
   - Station - displays stations inside a grid. Grid columns are sortable and filterable. Also, it is possible to change grid's page size in the select above. The **Reset filter** button will reset all filters from grid. The **Add new station** button will open a dialog where you can add a new station. The dialog is validated on frontend and on backend. In each entry from grid the **Info** column will display station's info: map with the position, average distances, total number of journeys and top 5 journeys.
   - Journey - displays journeys inside a grid. Grid columns are sortable and filterable. There is possibility to **Reset filters*** and **Add a new journey**. Adding a new journey will not validate station unique key.

Station's info looks like this:
![](https://github.com/iuliaMos/angular-mongo-example/blob/main/station-info.png)


The backend is tested using JUnit and [Mockito](https://site.mockito.org/) for Unit Tests and [SpringBootTests](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#features.testing) for Integration Tests.
There are no tests for frontend.

### Functionalities accomplished and in progress:
- [x] Data is validated before importing from CSV (like ignore journeys with less than 10 sec and with a distance with less than 10 m)
- [x] Each grid is filtered, sorted, paginated in backend
- [x] Add new entity with partial validation
- [x] Single Station view (Info) contains:
  - a map with the position of the station:
  - total number of journeys (from the station and at the station)
  - average distance of a journey (starting from the station and ending at the station)
  - Top 5 most popular return stations for journeys starting from the station
  - Top 5 most popular departure stations for journeys ending at the station
- [x] IT use Embedded MongoDB loading data and cleaning before each test
- [x] Uses [GeoJsonPoint](https://www.mongodb.com/docs/manual/reference/geojson/) for storing geolocation coordinates
- [ ] Missing relation between Journey and Station
- [ ] Adding a new Journey does not validate if the station exists (this can be accomplished with an inline auto complete list)
- [ ] There is possibility to add more types of filtering per column
- [ ] Add Geospatial functionalities like: nearest stations from the position on the map)
- [ ] Increase performance of the Mongo DB (like sharding)
- [ ] Angular Lazy-load components
- [ ] Improve css and style
- [ ] Adding a new journey: departureDate and returnDate should use a DateTime picker on click field(now DateTime picker is available on Space press)

## Author
[Iulia Moscalenco](https://github.com/iuliaMos)