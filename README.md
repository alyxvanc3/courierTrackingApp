There are 2 endpoints called /log and /getTotalTravelDistance. Both are get requests. They can be tested via postman or curl. 

I used Intellij Idea’s console for logging. 

1)An example for log endpoint:
I changed the longitude a little bit to stay inside the first store’s zone.

Postman: 

GET	 localhost:8080/log

{
 "time": "2020-04-02T22:30:11.333Z",
 "id": 2,
 "latitude": 40.9923307,
 "longitude": 29.1245
}

CURL:

curl --location --request GET 'localhost:8080/log' \
--header 'Content-Type: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
 "time": "2020-04-02T22:30:11.333Z",
 "id": 2,
 "latitude": 40.9923307,
 "longitude": 29.1245
}
'

2) Another Example for log
This will be near to second store

curl --location --request GET 'localhost:8080/log' \
--header 'Content-Type: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
 "time": "2020-04-02T22:32:11.333Z",
 "id": 2,
 "latitude": 40.986106,
 "longitude": 29.117
}
'

GET 	localhost:8080/log

{
 "time": "2020-04-02T22:32:11.333Z",
 "id": 2,
 "latitude": 40.986106,
 "longitude": 29.117
}


3) An example for total distance

curl --location --request GET 'localhost:8080/travelDistance?id=2'

GET localhost:8080/travelDistance?id=2




