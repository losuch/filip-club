network:
	docker network create fc-network
	
postgres:
	docker run --name postgres --network fc-network -p 5432:5432 -e POSTGRES_USER=root -e POSTGRES_PASSWORD=secret -d postgres:15-alpine

createdb:
	docker exec -it postgres createdb --username=root --owner=root filip-club 

dropdb:
	docker exec -it postgres dropdb filip-club
	
showdb:
	docker exec -it postgres psql -U root -d filip-club

fcimage:
	docker build -t filip-club:latest .

fcrun:
	docker run --name filip-club --network fc-network -p 8080:8080 -d filip-club:latest
	
.PHONY: network postgres createdb dropdb showdb fcimage fcrun