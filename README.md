# AdvertService

#### Dependencies
* scalaVersion => "2.11.7"
* Play => 2.5.9
* MongoDB - Use Mongo docker image or install mongo
    * Install mongo - https://docs.mongodb.com/manual/installation/
    * Or as a docker image - `docker pull mongo`
      > run with  
      `docker run --name my-mongo -p 27017:27017 -d mongo`  
      Based on your choice of mongo, change the following in 'conf/application.conf'  
      `mongodb.uri = "mongodb://localhost:27017/advertsDb"`

#### Instructions to test/run
* Clone the repo
> git clone git@github.com:raj-saxena/AdvertService.git
* cd into project directory
> cd AdvertService
* run tests with
> `sbt test`
* run project with
> `sbt run`

#### API signature
* GET     /api/adverts        
* POST    /api/advert    
* GET     /api/advert/:id
* DELETE  /api/advert/:id
* PATCH   /api/advert/:id

#### Sample Ad
New car ad
```json
{
	"title": "First car - new",
	"fuel": "Petrol",
	"price": 3000,
	"isNew": true
}
```

Old car ad
```json
{
	"title": "Second car - old",
	"fuel": "Diesel",
	"price": 2500,
	"isNew": false,
	"mileage": 10,
	"firstRegistration": "09-12-2016"
}
```
___

##### Note: 
Sometimes, the first request fails after the server start as the mongo connection isn't initialized at that time. Just fire the request again and it should work. Will comeback to it later.

