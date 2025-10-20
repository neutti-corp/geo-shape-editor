## 폴더
- 지도 이미지(국토지리정보원) : /home/service/map-cache
- 서버 톰캣 : /home/service/tomcat

## 포트
- 10000 : DB (Postgres)
- 10001 : 웹 (Tomcat)

## 실행
```bash
# 이미지 로드
docker load -i kgeo-map-server-image-v2.tar
# 컨테이너 로드
docker-compose up -d
# 컨테이너 접속     
docker exec -it kgeo-map-server bash       
```

## 접속
http://localhost:10001/map/index.html