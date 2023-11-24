network:
	docker network create fc-network
	
postgres:
	docker run --name postgres15 --network fc-network -p 5432:5432 -e POSTGRES_USER=root -e POSTGRES_PASSWORD=secret -d postgres:15-alpine

createdb:
	docker exec -it postgres15 createdb --username=root --owner=root filip-club 

dropdb:
	docker exec -it postgres15 dropdb filip-club
	
showdb:
	docker exec -it postgres15 psql -U root -d filip-club

updatedb:
	mvn liquibase:update

fcimage:
	docker build -t filip-club:latest .

fcrun:
	docker run --name filip-club --network fc-network -p 8080:8080 -d filip-club:latest
	
.PHONY: network postgres15 createdb dropdb showdb updatedb fcimage fcrun