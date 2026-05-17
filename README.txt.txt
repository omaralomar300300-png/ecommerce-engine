Project Name:
High-Performance E-Commerce Backend Engine

Requirements:
- Java 17+
- Maven
- XAMPP / MySQL
- Nginx
- Apache JMeter

Database Setup:
1. Start MySQL from XAMPP.
2. Open phpMyAdmin.
3. Create a database named ecommerce_engine.
4. Import the file ecommerce_engine.sql.

Run Server 1:
mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--server.port=8082 --app.instance.name=server-8082"

Run Server 2:
mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--server.port=8083 --app.instance.name=server-8083"

Nginx:
1. Copy nginx.conf into nginx/conf folder.
2. Start Nginx.
3. Base URL:
http://localhost:8090

Main APIs:
GET  http://localhost:8090/api/products
GET  http://localhost:8090/api/wallets
GET  http://localhost:8090/api/load/instance
GET  http://localhost:8090/api/reports/daily-sales

Actuator:
GET http://localhost:8082/actuator/health
GET http://localhost:8082/actuator/metrics
GET http://localhost:8082/actuator/metrics/jvm.threads.live

JMeter:
Open ecommerce_test_plan.jmx using Apache JMeter.
The test plan contains:
- 100 users
- Ramp-up period = 10 seconds
- Loop Count = 5

The tested endpoints are:
- GET /api/products
- GET /api/wallets
- GET /api/load/instance
- GET /actuator/health

The Summary Report shows the load test results.