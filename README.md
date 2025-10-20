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
docker cp ~/.kma-tilemap-cache map-server:/root/service/map-cache
docker cp /Users/neutti/Dev/Projects/map-shape-editor/target/ROOT.war map-server:/root/service/tomcat/webapps/ROOT.war
JAVA_OPTS="-Dspring.profiles.active=docker" ./bin/startup.sh
```