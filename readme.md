# 🏎️ Приложение автомобильного салона

## Основные функции

- 🔒 Разграничение прав доступа
- 📊 Учет клиентов и автомобилей
- ✅ Оформление заказов
- 🔍 Просмотр истории заказов
- 📦 Управление каталогом

## Порядок действий

### БД и Java APP

1. Создайте папку для проекта
2. Выполните `git clone https://github.com/VictoriaTwix/demo`
3. Перейдите в `demo/Database/` и выполните `docker compose up`

### Порядок действий в настольном приложении

1. Войдите как пользователь:
   - Логин: 123
   - Пароль: 123
   
2. Оформите заказ на странице автомобилей в наличии.

3. Выйдите из аккаунта пользователя.

4. Зайдите как менеджер, просмотрите заказы от пользователей.

5. Выйдите из личного кабинета менеджера.

6. Зайдите как директор:
   - Логин: dir
   - Пароль: 123
   
7. Просмотрите отчет по продажам.

### Инструкция пользователя

На главной странице отображается информация о салоне и возможность входа в личный кабинет.

### Страница авторизации

Войдите с логином и паролем для доступа ко всем функциям.

### Личные кабинеты

Пользователь: просмотр заказов, оформление заказов

Менеджер: редактирование/удаление/добавление товаров

Директор: просмотр отчета по продажам

### Дополнительные функции

Менеджер может добавлять, редактировать и удалять товары через контекстное меню.