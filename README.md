# MetaData-Extraction-From-a-DB-SpringBoot-MySql-MongoDB

### Simply import project as springBoot project and run it.
### The project will run at port 8080 by default,if you want to change the running port.
#### Simply add server.port=${port number} in application.properties file under src/main/resources.


## The Database(Default MySql) whose metadata has to be fetched can be configure by its jdbc url,username and password under same src/main/resource/application.properties file.
## Same is applicable for the database(Default MongoDb) to which the extracted metaData will be saved.


### There are two endpoints in this api-
### 1. /oldDb/insertData : This endpoint can be used to execute any native sql query over the database whose metadata is required.
### The request structure for this endpoint is a json and Http method is Post .Ex-
{
  "query":" Valid sql query"
}

### 2. /extractMetaData: This is the main endpoint that will fetch all metaData from the old Db(MYSql) and save it in the new One(MongoDB).
### The Http method for this endpoint is any(get,post) etc.And it doesn't require any request body.
