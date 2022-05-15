# HabrLoom
sample project for the Harb portal's article

Давайте представим, что у нас имеется открытый сервер, подключенный к Базе данных (БД). Типичный сценарий взаимодействия клиента и сервера 
следующий:

1. Клиент обращается к серверу с запросом, тот декодирует запрос клиента и инициирует выполнение SQL-запроса в Базе данных.
1. Сервер ждёт ответа БД.
1. Сервер сериализует данные из БД и возвращает их клиенту.

Напишем олдскульный сервер для обработки TCP-запросов. Именно этот способ организации обработки нацелен модернизировать проект Loom. 
Сервер состоит из двух основных классов Dispatcher и Worker. Первый в бесконечном цикле ждёт соединения клиента и запускает второго 
в одном из тредов пула.

В своём отдельном потоке Worker декодирует запрос клиента, инициирует выполнение SQL-запроса в Базе данных, 
а потом сериализует данные из БД и возвращает их клиенту.

Переход на Loom требует правки двух строчек этого проекта.
