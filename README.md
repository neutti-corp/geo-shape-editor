# Map Shape Editor
MapShapeEditor is an intuitive tool designed for creating, editing, and managing shapes on interactive maps. For GIS professionals and developers, MapShapeEditor provides the necessary features to enhance geospatial projects with ease.

## Openlayers + D3 + P5

```text
- Java : 1.8
- Spring Boot : 2.7.3
- Spring Framework : 5.3.22
- Tomcat : 8.5.99
- Apache 또는 Nginx : 없음 (Docker 설정 안됨) 
- PostgreSQL : 15 (Docker 설정 안됨)
- PostGIS : 3.4 (Docker 설정 안됨)
```

```bash
docker cp ~/.kma-tilemap-cache/* map-server:/home/service/map-cache
docker cp /Users/neutti/Dev/Projects/map-shape-editor/target/ROOT.war map-server:/home/service/tomcat/webapps/ROOT.war
docker exec -it kgeo-map-server bash
/home/service/tomcat/bin/shutdown.sh
JAVA_OPTS="-Dspring.profiles.active=docker" /home/service/tomcat/bin/startup.sh
tail -f /home/service/tomcat/logs/catalina.out
docker commit map-server kgeo-map-server-image:v2
docker save -o kgeo-map-server-image-v2.tar kgeo-map-server-image:v2
docker load -i kgeo-map-server-image-v2.tar 
docker-compose up -d     
docker exec -it kgeo-map-server bash


docker load -i map-server-image.tar       
```