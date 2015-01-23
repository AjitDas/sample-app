Sample Rest Service using Spring

HTTP Method : POST 
URL Template : http://localhost:8080/user-service/user/create/{id}
URL : http://localhost:8080/user-service/user/create/1
POST Body : {"id":1,"username":"ajidas","firstname":"Ajit","lastname":"Das","dob":"1983-07-08"}
POST Headers : Accept: application/json | application/xml, Content-Type: application/json | application/xml, X-Correlation-Id: 12345 ,X-System-Id: DCL

HTTP Method : GET 
URL Template : http://localhost:8080/user-service/user/find/{id}
URL : http://localhost:8080/user-service/user/find/1
GET Headers : Accept: application/json, X-Correlation-Id: 12345, X-System-Id: DCL, X-Message-Id: e42a92d7-f561-4990-bcbc-ac441ec26a83

HTTP Method : PUT 
URL Template : http://localhost:8080/user-service/user/update/{id}
URL : http://localhost:8080/user-service/user/update/1
PUT Body : {"id":1,"username":"ajidas3","firstname":"Ajit1UpdatedOne","lastname":"Das1UpdatedTwo","dob":"1983-07-08"}
PUT Headers : Accept: application/json | application/xml, Content-Type: application/json | application/xml, X-Correlation-Id: 12345, X-System-Id: DCL, X-Message-Id: e42a92d7-f561-4990-bcbc-ac441ec26a83

HTTP Method : DELETE 
URL template : http://localhost:8080/user-service/user/delete/{id}
URL : http://localhost:8080/user-service/user/delete/1
DELETE Headers : Accept: application/json, X-Correlation-Id: 12345, X-System-Id: DCL, X-Message-Id: e42a92d7-f561-4990-bcbc-ac441ec26a83

HTTP Method : GET 
URL Template : http://localhost:8080/user-service/weather/cities-by-country/{countryName}
URL : http://localhost:8080/user-service/weather/cities-by-country/India , http://localhost:8080/user-service/weather/cities-by-country/United States
GET Headers : Accept: application/json | application/xml, X-Correlation-Id: 12345, X-System-Id: DCL, X-Message-Id: e42a92d7-f561-4990-bcbc-ac441ec26a83

HTTP Method : GET 
URL Template : http://localhost:8080/user-service/weather/get-current-weather/{countryName}/{cityName}
URL : http://localhost:8080/user-service/weather/get-current-weather/India/Bhubaneswar , http://localhost:8080/user-service/weather/get-current-weather/United States/Orlando
GET Headers : Accept: application/json | application/xml, X-Correlation-Id: 12345, X-System-Id: DCL, X-Message-Id: e42a92d7-f561-4990-bcbc-ac441ec26a83

HTTP Method : GET 
URL Template : http://localhost:8080/user-service/weather/get-all-cities-current-weather/{countryName}
URL : http://localhost:8080/user-service/weather/get-all-cities-current-weather/India , http://localhost:8080/user-service/weather/get-all-cities-current-weather/United States
GET Headers : Accept: application/json | application/xml, X-Correlation-Id: 12345, X-System-Id: DCL, X-Message-Id: e42a92d7-f561-4990-bcbc-ac441ec26a83

