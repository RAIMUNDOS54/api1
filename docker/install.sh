docker stop api1api
docker rm api1api
docker build -t api1-api .
docker tag api1-api exactaworks/api1-api
docker run -d -p 8080:8080 --name api1api api1-api
