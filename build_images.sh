cd alg_components/exhaust_analyzer
./gradlew bootJar
docker build -t exhaust_analyzer:1.0 -f docker/Dockerfile .

cd ../liquid_analyzer
./gradlew bootJar
docker build -t liquid_analyzer:1.0 -f docker/Dockerfile .

cd ../motor_analyzer
./gradlew bootJar
docker build -t motor_analyzer:1.0 -f docker/Dockerfile .

# cd ../../analyse_dashboard
# # Build .jar/.war package
# docker build -t ui-dashboard:1.0 -f docker/Dockerfile .
