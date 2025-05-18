# Guest Api

Private project to help the welcoming group of the church where I attend.
I was extremely uncomfortable with manually tracking visitors and warnings 
so I thought I'd create this project to avoid errors and improve tracking 
and give visibility to the number of visits etc.

<h4>I know I could have done this on Google forms 😝</h4>


### Prerequisites

Requirements for the software and other tools to build, test and push

- [Java](https://www.java.com/pt-BR/)
- [Maven](https://maven.apache.org/)
- [Mongo](https://hub.docker.com/_/mongo)
- [Docker](https://www.docker.com/)
- [Kubernetes](https://kubernetes.io/docs/home/) - optional
- [Postman-Collection-Guest](./docs/Collection-Guest.postman_collection.json)

## Todo - 😅 👨‍💻

- [x] Initial work - 🥳
- [X] Dockerfile - 🥹
- [X] Kubernetes - 😝
- [X] JWT - 🫣
- [ ] Test units - 😏
- [ ] Integration test - 🤓

## Running the tests

```
    mvn clean install
```

## Deployment

```
    docker build -t <name-space>/guest-api:<tag> <path>/Dockerfile
```

## Start API 

```
    docker run -it --name guest-api -e DATA_SOURCE_URL=mongodb://localhost:27017/guest -e JWT_SECRET=<sua-secret-aqui> -p 8080:8080 <name-space>/guest-api:<tag>
```

### Contributing Authors

* **Nilo Jose de Andrade Neto** - *Initial work*
