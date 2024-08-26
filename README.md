# NewsAggregator
API Documentation

#Register User
Endpoint: POST /api/register

Description: Registers a new user and creates a verification token.

Request Body:
{
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "password": "string"
}

#Verify Registration
Endpoint: POST /verifyRegistration

Description: Verifies the user registration using the provided token.

Request Parameters:

token (string): The verification token.
Response:

On Success: "User enabled successfully"

Error Status Code: 400 Bad Request (If token is invalid or expired)

#Login
Endpoint: POST /api/login

Description: Authenticates the user and returns a JWT token.

Request Body:

{
  "email": "string",
  "password": "string"
}
Response:
{
  "token": "string",
  "expirationTime": "long"
}


NewsController
Get Preferences
Endpoint: GET /api/preferences

Description: Fetches user preferences for news.

Response:
{
  "articles": [{
  "source":{
  "id":"String",
  "name":"String"
  }
      "title": "string",
      "description": "string",
      "url": "string",
      "publishedAt": "string"
    }
  ]
}


Update Preferences
Endpoint: PUT /api/preferences?search="String"

Description: Updates user preferences based on search.

Request Parameters:

search (string): The search query to update preferences.
ex: search= cricket or soccer or apple or java etc

Response:
same json response as above with uodated response based on type


Get News
Endpoint: GET /api/news

Description: Fetches total English news articles using asynchronous programming.
writen in body to mono

Response:
same json response as above but all news articles.
