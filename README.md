CouponManagement Application:

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
8. Docker

How to add Environmental variables in MAC:

1. Go to Terminal
2. Type: nano .zprofile
3. add your variable for example: export JDBC_USER=rameel
4. Press ctrl+x
5. Press Y
6. Press enter
7. Restart terminal
8. type env and see your latest variable here
9. Restart Intellij 
10. In Intellij go to run configurations, then environmental variable and check your new variable

Run Docker locally:

1. JDBC url is different for localhost and docker
2. In terminal: docker-compose up --build

