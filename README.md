# helsinki-city-bike
 
This is a Spring Boot web-based application that displays data on city bike journeys and bike stations in the cities of Helsinki and Espoo, and statistics on bike traffic from and to any given station.

## Features

* View a list of bike journeys, including details such as start station, end station, distance, and duration.
* View a sorted list of bike stations 
* Import bike trip and bike station data from .csv files (stored within the /resources/data directory) into an in-memory database.
* Access endpoints for each bike station, which display address, city, and bike trip statistics

## Technologies used

* Java 17
* Spring Boot
* Spring Data JPA / H2
* Thymeleaf
* Bootstrap

## How to run

* Ensure you have JDK 17 or higher installed
* Clone the repository or download it as a ZIP file
* Open the project in a suitable IDE
* Build the project to resolve dependencies
* Run CitybikeApplication.java to start app the application. Note that a first time setup might require some time to import the .csv data
* Access the application on port 8080 by navigating to http://localhost:8080 on a browser

## How to use

Once the application is running, you will see buttons leading to two separate endpoints:

* /trips displays a paginated list of bike trips
* /stations displays a paginated list of bike stations

Clicking on a station name in the stations list will lead you to a page displaying more detailed information about that specific station. 

Trying to access a non-existing page will lead to an error page that displays a button to get back to the homepage.

## Credits

The data used in this application is owned by City Bike Finland and can be accessed through the following links:

* https://dev.hsl.fi/citybikes/od-trips-2021/2021-05.csv
* https://dev.hsl.fi/citybikes/od-trips-2021/2021-06.csv
* https://dev.hsl.fi/citybikes/od-trips-2021/2021-07.csv
* https://opendata.arcgis.com/datasets/726277c507ef4914b0aec3cbcfcbfafc_0.csv


