<h1>CLEVERTEC (homework-11)</h1>

<p>CLEVERTEC homework-11 Spring core :</p>

<ol>

<li>
Develop web service for Gift Certificates system with the following entities (many-to-many):
	<ul>
		<li>
			CreateDate, LastUpdateDate - format ISO 8601 (https://en.wikipedia.org/wiki/ISO_8601). Example: 2018-08-29T06:12:15.156. 
			More discussion here: https://stackoverflow.com/questions/3914404/how-to-get-current-moment-in-iso-8601-format-with-date-hour-and-minute.
		</li>
		<li>
			Duration - in days (expiration period)
		</li>
	</ul>
</li>

<li>
The system should expose REST APIs to perform the following operations:
	<ul>
		<li>
			CRUD operations for GiftCertificate. If new tags are passed during creation/modification – they should be created in the DB. For update 
			operation - update only fields, that pass in request, others should not be updated. Batch insert is out of scope.
		</li>
		<li>
			CRUD operations for Tags.
		</li>
		<li>
			Get certificates with tags (all params are optional and can be used in conjunction):
			<ul>
				<li>
					by tag name (ONE tag)
				</li>
				<li>
					search by part of name/description (can be implemented, using DB function call)
				</li>
				<li>
					sort by date or by name ASC/DESC (extra task: implement ability to apply both sort type at the same time).
				</li>
			</ul>
		</li>
	</ul>
</li>

<h2>Application requirements:</h2>

<li>
	JDK version: 17 – use Streams, java.time.*, etc. where it is possible.
</li>

<li>
	Application packages root: ru.clevertec.ecl.
</li>

<li>
	Any widely-used connection pool could be used.
</li>

<li>
	Spring JDBC Template should be used for data access.
</li>

<li>
	Use transactions where it’s necessary.
</li>

<li>
	Java Code Convention is mandatory (exception: margin size – 120 chars).
</li>

<li>
	Build tool: Gradle, latest version.
</li>

<li>
	Web server: Apache Tomcat.
</li>

<li>
	Application container: Spring IoC. Spring Framework, the latest version.
</li>

<li>
	Database: PostgreSQL, latest version.
</li>

<li>
	Testing: JUnit 5.+, Mockito.
</li>
	
<li>
	Service layer should be covered with unit tests not less than 80%.
</li>

<li>
	Repository layer should be tested using integration tests with an in-memory embedded database (all operations with certificates).
</li>
	
<li>
	As a mapper use Mapstruct.
</li>

<li>
	Use lombok.
</li>

<h3>General requirements:</h3>

<li>
	Code should be clean and should not contain any “developer-purpose” constructions.
</li>

<li>
	App should be designed and written with respect to OOD and SOLID principles.
</li>

<li>
	Code should contain valuable comments where appropriate.
</li>

<li>
	Public APIs should be documented (Javadoc).
</li>

<li>
	Clear layered structure should be used with responsibilities of each application layer defined.
</li>
	
<li>
	JSON should be used as a format of client-server communication messages.
</li>

<li>
	Convenient error/exception handling mechanism should be implemented: all errors should be meaningful on backend side. Example: handle 404 error:
	HTTP Status: 404
	response body    
	{
 	“errorMessage”: “Requested resource not found (id = 55)”,
 	“errorCode”: 40401
 	}
	where *errorCode” is your custom code (it can be based on http status and requested resource - certificate or tag)
</li>

<li>
	Abstraction should be used everywhere to avoid code duplication.
</li>

<li>
	Several configurations should be implemented (at least two - dev and prod).
</li>

</ol>

<p>It is forbidden to use:</p>

<ol>
	<li>
		Spring Boot.
	</li>
	<li>
		Spring Data Repositories.
	</li>
	<li>
		JPA.
	</li>
</ol>

<h4>What's done:</h4>
<ol>
<li>
	Develop web service for Gift Certificates system with entities (many-to-many): gift certificates, tags.
	Gift certificate entity has also fields CreateDate, LastUpdateDate - format ISO 8601. Example: 2018-08-29T06:12:15.156, 
	duration - in days (expiration period).
</li>

<li>
	The system expose REST APIs to perform the following operations:
	<ul>
		<li>
			CRUD operations for GiftCertificate. 
			If new tags are passed during creation/modification – they will be created in the DB. 
			For update operation - update only fields, that pass in request, others will not be updated. Batch insert is out of scope. 
		</li>
		<li>
			CRUD operations for tags
		</li>
		<li>
			Get certificates with tags (all params are optional and can be used in conjunction):
			<ul>
				<li>
					By tag name (ONE tag).
				</li>
				<li>
					Search by part of name/description (can be implemented, using DB function call).
				</li>
				<li>
					Sort by date or by name ASC/DESC (extra task: implement ability to apply both sort type at the same time).
				</li>
			</ul>
		</li>
	</ul>
</li>

<li>
	JDK version: 17 – using Streams, java.time.*, etc. where it is possible.
</li>

<li>
	Application packages root: ru.clevertec.ecl.
</li>

<li>
	Using hikariCP connection pool
</li>

<li>
	For data access using hibernate orm.
</li>

<li>
	Using transactions in DAO layer.
</li>

<li>
	Using Java code convention.
</li>

<li>
	Build tool: using Gradle latest version.
</li>

<li>
	Web server: using Apache Tomcat.
</li>

<li>
	Using spring, spring web-mvc.
</li>

<li>
	Database: PostgreSQL, latest version.
</li>

<li>
	Testing: JUnit 5.+, Mockito, AssertJ, test containers.
</li>

<li>
	Service layer covered with unit tests not less than 80%.
</li>

<li>
	Repository layer tested using integration tests with test containers.
</li>

<li>
	As a mapper used Mapstruct.
</li>

<li>
	Used lombok.
</li>

<li>
	Code is clean and not contain any “developer-purpose” constructions.
</li>

<li>
	App designed and written with respect to OOD and SOLID principles.
</li>

<li>
	Code contains valuable comments where appropriate.
</li>

<li>
	Public APIs documented (Javadoc).
</li>

<li>
	Clear layered structure used with responsibilities of each application layer defined.
</li>
	
<li>
	JSON used as a format of client-server communication messages.
</li>

<li>
	Convenient error/exception handling mechanism implemented: all errors catched on backend side.
</li>

<li>
	Abstraction used everywhere to avoid code duplication.
</li>

<li>
	Several configurations implemented (dev and test).
</li>

</ol>

<h5>How to use</h5>
<p>Поднимаем docker: docker-compose up -d</p>
<p>Проверяем ендпоинты в postman: /certificates /tags</p>