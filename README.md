Coupon Application:

USE Cases:
1. New coupons can be created.
2. Coupons can be redeemed once and Multiple-times based on the type.

Technology Used:
1. Java version 17
2. Spring Boot
3. JPA
4. MariaDB
5. Flyway
6. Mockito
7. Design pattern: MVC
8. Dockerized app with Docker compose
9. Images stored in AWS ECR
10. CI: GitHub actions workflows

How to add Environmental variables in MAC:

1. Go to Terminal
2. Type: nano .zprofile
3. add your variable for example: export JDBC_USER=XXXXX
4. Press ctrl+x
5. Press Y
6. Press enter
7. Restart terminal
8. Type env in terminal and see your latest variable here
9. Restart Intellij 
10. In Intellij go to run configurations, then environmental variable and check your new variable

Run in localhost:

1. Install mariadb: https://dev.to/timo_reusch/quickly-setting-up-mariadb-on-macos-441l
2. Run mariadb server from MAC terminal: mysql.server start
3. Run app from Intellij configurations
4. To test in browser: http://localhost:8080/coupons


Run Docker locally:

1. Pre-req: Dockerfile & docker-compose.yml
2. Note: JDBC url is different for application.properties and for docker-compose.yml
3. Run mvn vlean install first to create a latest JAR file
4. Stop the mysql server if already running, In terminal: lsof -i :3306 fetch the PID and then kill <PID>
5. In terminal: docker-compose up --build
  
Docker commands to stop and clean-up everything locally:

1. Stop and remove containers: docker-compose down
2. Clean up unused resources: docker system prune -f
3. (Optional) Remove volumes: docker volume prune -f

