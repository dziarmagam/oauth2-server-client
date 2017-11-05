# OAuth 2 authorization code flow example.

### This application contains 2 configurations:
1. `application-github.properties` - configuration use for obtaining access token from github authorization server
2. `application-keycloack.properties` - configuration use for obtaining access token from keycloack authorization server


Paste and copy github or keycloack configuration to `application.properties`

Remember to change `oauth2.client.id` and `oauth2.client.secret` to match your registered client application.

ow to register client application in github - https://developer.github.com/apps/building-integrations/setting-up-and-registering-oauth-apps/registering-oauth-apps/

#### Note

This example contain minimal amount of additional frameworks and API in order
to keep the code as simple as possible and demonstrate how HTTP communications looks like in OAuth2.

### Configuration description:
```
oauth2.client.id - client id obtain after application is registered in authorization server as client
oauth2.client.secret - client secret obtain after application is registered in authorization server as client
oauth2.redirect_uri - redirection uri passed to authorization server during oauth2 flow
oauth2.scope - scope passed to authorization server. Multiple scope can be included separated by white space e.g user car
oauth2.authentication.uri - uri to authorization server. Used to obtain code in authorization code flow
oauth2.access.token.endpoint - uri to endpoint for obtaining access token to API
oauth2.request.body.type - type of access body request send to obtain access token. It's either JSON or FORM
oauth2.token.body.type - type of response format send by authorization server while obtaining access token. It's either JSON or FORM
data.api.uri - uri to API endpoint
```
