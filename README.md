<h1>CLEVERTEC (homework-11)</h1>

<p>CLEVERTEC homework-11 spring:</p>
<ol>
<li>Взять за основу проект из лекции сервлетов</li>
<li>Придерживаться GitFlow: master -> develop -> feature/fix</li>
<li>Перевести проект на Spring 6.1.2</li>
<li>Заполнить и отправить форму</li>
</ol>

<h2>Что сделано:</h2>
<ol>
<li>За основу взят проект из лекции сервлетов</li>
<li>Разработка производится по концепции GitFlow: master -> develop -> feature/fix</li>
<li>Проект переведен на Spring 6.1.2</li>
<li>Заполнена и отправлена форма</li>
</ol>

<h3>Как запускать:</h3>
<ol>
<li>Билдим проект: .\gradlew clean build</li>
<li>Запускаем приложение в docker: docker-compose up -d</li>
</ol>

<p>Проверяем работу приложения, используем postman.</p>
<p><b>1. Для Person controller (CRUD-операции):</b></p>

<p>GET:</p>
<p>http://localhost:8080/app/persons</p>
<p>http://localhost:8080/app/persons?page=2</p>
<p>http://localhost:8080/app/persons?page=1&page_size=4</p>
<p>http://localhost:8080/app/persons/98582bb5-a09c-4f16-8b19-7247e5e68079</p>


<p>POST:</p>
<p>http://localhost:8080/app/persons</p>
<p>
{
"name": "Galya",
"surname": "Matulkina",
"email": "galya@gmail.com",
"citizenship": "Russia",
"age": 8
}
</p>

<p>PUT:</p>
<p>http://localhost:8080/app/persons</p>
<p>
{
"id": "f0a7c9a8-0a0a-4f1a-9c0d-9a0f9a0f9a0f",
"name": "Edik",
"surname": "Petrova",
"email": "edik@gmail.com",
"citizenship": "USA",
"age": 23
}
</p>

<p>
DELETE:
</p>
<p>http://localhost:8080/app/persons/605a2dcb-f4c1-4909-9f63-bdacee7ea5bf</p>

<p>
<b>
2. Для Government controller (возвращает pdf файл с государственными услугами, которыми пользовался person):
</b>
</p>

<p>
GET:
</p>
<p>
<b>
Accept header на каждый запрос должен соответствовать "application/pdf" 
для прохождения через фильтр AcceptHeaderFilter!!!
</b>
</p>
<p>http://localhost:8080/app/services/f0a7c9a8-0a0a-4f1a-9c0d-9a0f9a0f9a0f</p>
<p>http://localhost:8080/app/services/b1b079fb-465c-439f-9c50-1c8a4dbd4b31</p>
