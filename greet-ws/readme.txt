Sample Rest Service using Spring MVC

HTTP Method : GET 
URL Template : http://localhost:8080/greet-ws/greet/greet-simple
URL : http://localhost:8080/greet-ws/greet/greet-simple
GET Headers : Accept: application/json | application/xml, X-Correlation-Id: 12345, X-System-Id: DCL, X-Message-Id: e42a92d7-f561-4990-bcbc-ac441ec26a83

HTTP Method : GET 
URL Template : http://localhost:8080/greet-ws/greet/greet-with-message/{greetMessage}
URL : http://localhost:8080/greet-ws/greet/greet-with-message/namaste
GET Headers : Accept: application/json | application/xml, X-Correlation-Id: 12345, X-System-Id: DCL, X-Message-Id: e42a92d7-f561-4990-bcbc-ac441ec26a83

HTTP Method : POST 
URL Template : http://localhost:8080/greet-ws/greet/greet-with-messagebody
URL : http://localhost:8080/greet-ws/greet/greet-with-messagebody
POST Body : {"greetMessage":"Namaste India"}
POST Headers : Accept: application/json | application/xml, Content-Type: application/json | application/xml, X-Correlation-Id: 12345 ,X-System-Id: DCL

