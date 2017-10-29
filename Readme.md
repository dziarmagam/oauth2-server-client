This application contains 2 configurations:
application-github.properties - configuration use for obtaining access token from github authorization server
application-keycloack.properties - configuration use for obtaining access token from keycloack authorization server

Paste and copy github or keycloack configuration to application.properties
Remember to change oauth2.client.id and oauth2.client.secret to match your registered client application.
How to register client application in github - https://developer.github.com/apps/building-integrations/setting-up-and-registering-oauth-apps/registering-oauth-apps/

configuration description:

oauth2.client.id - client id obtain after application is registered in authorization server as client
oauth2.client.secret - client secret obtain after application is registered in authorization server as client
oauth2.redirect_uri - redirection uri passed to authorization server during oauth2 flow. 
oauth2.scope - scope passed to authorization server. Multiple scope can be included separated by white space e.g user car
oauth2.authentication.uri - uri to authorization server. Used to obtain code in authorization code flow/
oauth2.access.token.endpoint - endpoint for obtaining access token to API.
oauth2.request.body.type - type of access body request send to obtain access token. It's either JSON or FORM
oauth2.token.body.type - type of response send by authorization server while obtaining access token. It's either JSON or FORM
data.api.uri - uri to API endpoint


