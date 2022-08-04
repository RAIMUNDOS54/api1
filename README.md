# api1
api0 microservice project, Reactive; using R2DBC and WebFlux enabled.
Contents

1. Introduction 

2. Detailed Information

3. Final Words 



1. Introduction 

For Postman, Swagger and API Endpoints, go directly to the Section 2. Detailed Information 

Each project has two endpoints (one in Azure and another one in the Oracle Cloud on-premises VM). 

The api2 (.NET 7 project) on Azure is not running yet, as their support for .NET 7 preview is below the version used (7.0.0-preview.6). Nevertheless, this is something I'm working on and I can let you guys know when it is done. It is important to note that in this project (api2), it was attempted to simulate as best as possible with the given time the same Java architecture used in the two previous projects (api0 and api1). 

The first project using Hibernate takes advantage of the H2 embedded database, as it seemed a good way to "test" the "Java territory" for me. 

In the following projects the database used was SQL Server. Since WebFlux don't work well, my understanding at the time, with foreign one-to-many foreign keys with associated collections, a comma-delimited string list is used both in the api1 and the api2 for forwad compatibility, beginning in the newer version of the api (api1). Also, because two projects using the same database are the majority, so this is the little democracy of this little architecture. 

The CreateExpense methods should work fine with JSON objects in the first api (api0) regarding the TAGS field, and in the subsequent projects (api1, api2) a normal string will do fine. 

Azure App Service (lightweight-option) endopoints seem to be unstable while working with WebFlux, but do alright with a default Hibernate, JPA and Spring Boot project. 

Anyway, the endpoints on Oracle Cloud on-premises are up for a fight I guess, since they did not show any downtime during the few humble tests I've done. The Postman URIs are configured by default with a {{URL}} variable using them. But you guys can try the {{URL2}} to check the Azure App Service status for all APIs. Both [DEV] and [PROD] Postman Collections are available below. 

The SQL Server database is also hosted on this OCI VM Machine. 

2. Detailed Information: 

api0:

api1:

api2: 

3. Final Words 

It was nice to take this little dive in the Java world. Thanks for the opportunity. I hope you guys like the .NET 7 example as well. Since you mentioned, I thought it would be a good idea to use the rest of the time after I was healed not investing in the Spring Cloud, but showing a new technology for you guys (I guess, at least for me it is) that I tend to trust and intend to use a lot in the future (even though I don't have any preferences). 

Once again, thanks, and let me know if you guys want to talk about anything about me, what was done, why and such. It was a though, but joyful and spontaneous ride! I wish I wasn't sick. But, if there's only one way to go, this is the way to go!

See ya!
