# filip-club

This is POC backend application for filip-club website

## filip-club backend deployment

login docker into ECR:

`aws ecr get-login-password --region eu-central-1 --profile github-ci | docker login --username AWS --password-stdin 396315277532.dkr.ecr.eu-central-1.amazonaws.com/filip-club`

pull and run image from ECR:

`docker run --name filip-club --network fc-network -p 8080:8080 -d 396315277532.dkr.ecr.eu-central-1.amazonaws.com/filip-club`
