# customer-management-reactive-kotlin
This example is to enable the customers of the organisation to manage their profile (end to end i.e. create, update and delete) through web channel and mobile (app) channel. 

This example provide reactive API to list, get, create, update, delete customers with the Oauth2 authentication with the following assumtions:
1. The backend system is an enterprise CRM and is not yet ready to expose REST APIs. The CRM team is in the process of designing their REST APIs. 
2. Oauth2 Client and users are set in-memory. Will be updated to DB based authentication in stage 2
3. Mobile and Web FE will use the same oauth mechanism with different client ID and different expiry time. 
4. When create a customer, the user pool won't be updated automatically. There is some other service to update the logon user pool after the users are stored in DB

High level design: 
1. Provides 5 apis (list, show, update, create, delete)
2. APIs access is prtected by Oauth2 authentication. Authorication server is seperated (One runable auth server is https://github.com/dorjear/oauth-server) . So the system contains 2 microservies with Spring Boot. So that the authorication service will be used by other microservies as well. 
3. API contract is defined by swagger generated from the controller. The swagger can be access with a basic authentication (username and password are both "swagger")
4. Only users with admin group can access list/create/delete. Customer can access show/update on their own profile. 
5. Currently customer profile is stored in DB (in-memory) and is going to integration a downstream CRM system with restful webservices. The implementaion will be marked as TODO

