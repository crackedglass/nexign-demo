# Nexign Demo Service

## Запуск
### Используя Maven
```bash
git clone https://github.com/crackedglass/nexign-demo.git
cd nexign-demo
./mvnw install
cd impl
../mvnw spring-boot:run
```


### Используя jar
```bash
git clone https://github.com/crackedglass/nexign-demo.git
cd nexign-demo/build
java -jar nexign-demo-1.0-SNAPSHOT.jar
```

## Как использовать

### Доступные эндпоинты:
GET /udrs/numbers/{number} - Получение UDR по номеру, в качестве параметра доступен month  
GET /udrs/month/{month} - Получение всех UDR по месяцу

Также, после запуска доступна страница Swagger   
[http://localhost:8080/swagger-ui.html](http:localhost:8080/swagger-ui.html) 

В build/application.yml (или impl/src/main/resources) можно менять проперти 
``` 
generator:
  subscribers: 
    amount: 100 # Отвечает за кол-во генерируемых абонентов
  cdrs:
    amount: 10000 # Отвечает за кол-во генерируемых записей cdr
```

## Фишки

- Настроены миграции liquibase
- Из миграций генерируются классы для работы с бд с помощью jooq-codegen-maven (см impl/pom.xml)
- Максимально задействованы внутренние функции бд, благодаря гибкости JOOQ
- Реализован Swagger
- Сервис разбит на 3 модуля: api, db, impl.  
**api** - содержит в себе интерфейсы контроллеров и дто, также возможна реализация feign клиентов, что предоставляет удобный способ обращения к нашему сервису.  
**db** - содержит файл миграций, также может содержать в себе отдельную конфигурацию кодогенерации jooq.  
**impl** - непосредственно реализация функционала сервиса.
 

## Используемые библиотеки и инструменты:

- Spring Boot 3
- Spring Web
- Java 17
- Maven
- H2
- Liquibase
- JOOQ
- Swagger
- Faker (Для генерации номеров)
