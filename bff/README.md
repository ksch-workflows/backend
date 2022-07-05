# Backend for Frontend

Modern web applications are often implemented as Single Page Application (SPA) where the server provides only the raw data instead of rendering the website.
The frontend takes the raw data and dynamically generates the graphical user interface (GUI) of the website with JavaScript, the browser's scripting language.
When a user interacts with the website, the SPA fetches data via the server's API.
The Backend for Frontend (BFF) module allows SPAs to create a session in which the access tokens for the backend's [REST API][1] are stored.

## Overview

### User authentication flow

When the users initially request access to a workstation website, they are redirected to the login process of the authorization server.
When the clients are redirected, their session contains an access token.
Then they can proceed working with the website.

_Overview_:

![Login flow](./doc/login-flow.png)

_Details_:

1. The users request the workstation website, e.g. the one for the registration desk, in their browser.
2. That request is intercepted by the [`LoginInterceptor`](#).
3. If there is no access token available in the user's session, they are redirected to the authorize URL of the authorization server. The intercepted URL gets stored in the session attributes.
4. After successful authentication and authorization, the authorization server redirects the client to the app's callback URL, with the authorization code as query parameter.
5. With the authorization code grant and the app's client ID and client secret, the app calls the token endpoint of the authorization server to generate the access token.
6. After the access token has been stored in the user session, the user is eventually redirected to the originally intercepted URL.

### API access flow

After the users went through the authentification flow, their browser will have a session cookie with an identifier for the session which contains an access token. Whenever the SPA makes an HTTP request to the backend, the browser automatically adds the session cookie to the request. The BFF uses this session cookie to lookup the access token belonging to the session and adds it to the HTTP request. The API request handler then reads the access token from the authorization header, just as it would do for API clients which add the authorization header on their own.

_Overview_:

![API access flow](./doc/api-access-flow.png)

_Details_:

1. When the users perform an action in the app, the app will make an HTTP call to the backend's API, e.g. `POST /patients` to create a new patient entity in the system.
2. Before that request is handled by the [`PatientController`](#) which will take care of the patient creation, the request is pre-processed by the [`TokenFilter`](#). With the help of [Spring Session](#), it reads out the access token from the session belonging to the request. Then it adds the `Authorization` header with the access token to the request.
3. Afterwards, there will be yet another pre-processor befor the request can be handled by the `PatientController`. Spring Security reads out the `Authorization` header from the request.
4. It then checks whether the signature included in the access token matches with the public signing key of the authorization server. If not, it declines further processing.
5. Eventually the request reaches the `PatientController` which call the busines logic required for the patient creation.

### Development

During development, the server which hosts the SPA is a different one than the one which hosts the API, i.e. the SPA and the API are running on different domains.
For security reasons, the browser restricts setting and sending of cookies to websites running on the same domain.
Instead of trying to bend backwards to disable those security constraints during development, they are side-stepped altogether.
Instead of relying on the BFF to enrich the API requests with the authorization header, during development, the SPA is sending the authorization header by itself.

This process is enabled by a [dummy authorization server](https://github.com/ksch-workflows/noauth) which allows the generatation of access tokens without providing real credentials and which premits the usage of any access token that gets provided.

_Example request for generation of access token_:

```text
$ curl --request POST \
>   --header "content-type: application/x-www-form-urlencoded" \
>   --data "client_id=jnebdD0fczAHoEBVrr6lE7OAuYchc2ZR" \
>   --data "client_secret=xxxxx" \
>   --data "grant_type=authorization_code" \
>   --data "redirect_uri=http://localhost/callback" \
>   --data "code=W7S4aFZ" \
>   "http://localhost:7777/oauth/token"
{"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2NTYxNTU4ODIsImV4cCI6MTY1NjI0MjI4MiwiaXNzIjoiaHR0cHM6Ly9ub2F1dGgtZ2Eyc3BlYm94YS1ldy5hLnJ1bi5hcHAvIn0.odl615u30IcW9acGI1NL62E1D5e0wONHaeJk73tGWIE","refresh_token":"UrX8t8t0P0tCgBmahnfWV0TgSAwruCccgDzTbFvS18QopY","id_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuaWNrbmFtZSI6Impkb2UiLCJuYW1lIjoiSm9obiBEb2UiLCJlbWFpbCI6Impkb2VAbm9hdXRoLWdhMnNwZWJveGEtZXcuYS5ydW4uYXBwIiwic3ViIjoiYXV0aDB8NjFjMzA2MDYyMDY4MGQwMDY5NmUwOWEyIiwiYXVkIjoiam5lYmREMGZjekFIb0VCVnJyNmxFN09BdVljaGMyWlIiLCJpYXQiOjE2NTYxNTU4ODIsImV4cCI6MTY1NjI0MjI4MiwiaXNzIjoiaHR0cHM6Ly9ub2F1dGgtZ2Eyc3BlYm94YS1ldy5hLnJ1bi5hcHAvIn0.Na3uUYi-4VEA1FTmNqapTXcpCMRBJo0ZFUB-B3oEOa8","scope":"openid profile email offline_access","expires_in":86400,"token_type":"Bearer"}
```

When started in development mode, the Spring Boot application is configured to use opaque token validation.
With this configuration, whenever an API request reaches the server, it asks the dummy authorization server whether the provided access token is valid.
The dummy authorization server then verifies the validity of any provided access token.

_Example request for verification of access token_:

```text
$ echo "token=$ACCESS_TOKEN" | curl -X POST --data @'-' -s localhost:7777/token-info
{
  "active": true,
  "client_id": null,
  "scope": null
}
```

## Terminology

| Key      | Value                                                                                                                                                        |
| -------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| Cookie   | A key/value pair stored in the browser which is automatically sent to the backend with each API request.                                                     |
| Frontend | The part of a program which is focued on human-to-machine communication.                                                                                     |
| IoT      | Acronym for Internet of Things, small devices which are connected to the Internet, e.g. sensors or food delivery robots.                                     |
| OAuth2   | A commonly used protocol for the delegation of authorities of resource owner to API clients.                                                                 |
| SPA      | Acronym for Single Page Application, a web application where the website is rendered in the frontend via JavaScript instead of being rendered on the server. |

## References

**Register tokenfilter before spring security**

- https://stackoverflow.com/questions/61075273/java-lang-illegalargumentexception-cannot-register-after-unregistered-filter-cl
- https://stackoverflow.com/questions/30855252/how-do-i-enable-logging-for-spring-security
- https://stackoverflow.com/questions/34229750/invoke-a-filter-before-spring-security-filter-chain-in-boot
- https://stackoverflow.com/questions/34229750/invoke-a-filter-before-spring-security-filter-chain-in-boot
- https://www.youtube.com/watch?v=a2ZkCbTkH4Q
- https://www.baeldung.com/spring-security-custom-filter

[1]: https://en.wikipedia.org/wiki/Representational_state_transfer
