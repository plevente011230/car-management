# Build
mvn clean package && docker build -t org.example/car-management .

# RUN

docker rm -f car-management || true && docker run -d -p 8080:8080 -p 4848:4848 --name car-management org.example/car-management 