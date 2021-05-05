## Dorm

#### 1. Install JDK, NPM, MySQL, Maven and optionally Android Studio


#### 2. To run backend:
a) Create `dorm` schema in mysql server

b) From backend directory run `mvn install` and then `java -jar target/backend-0.0.1-SNAPSHOT.jar`


#### 3. To run frontend 
a) Change directory to frontend and run `npm install`

b) To start web app client run `npm run start`

c) To create an android client run `npm run build-mobile` - this will create and andorid project which should be runable with Android Studio


#### 4. To encrypt properties with jasypt:
a) add `DEC(property)` to properties you want to encrypt

b) run `mvn jasypt:encrypt -Djasypt.encryptor.password=yourEncryptionPassword`

c) add `-Djasypt.encryptor.password="yourEncryptionPassword"` to VM options of run configuration