#

# 빠른 실행

```bash
./gradlew bootRun --args='--spring.profiles.active=local'

```
# 인텔리제이로 실행시 VM 옵션추가
```
-Dspring.profiles.active=local
```

# 도커 배포 
프로젝트 루트 디릭터리에서 실행
```dockerfile
docker-compose down && docker-compose up -d --build
```