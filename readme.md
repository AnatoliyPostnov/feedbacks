Hа сервере root@81.163.25.109 надо перейти в репозиторий с проектом root@81.163.25.109/feedbacks.git

Для простоты все push-и идут сразу в ветку develop без merge request.

Скачать проект на локальную машину git clone root@81.163.25.109/feedbacks.git

Сборка и развертывание проекта происходит с помощью докера

Требуется 
 1. Перейти в root@81.163.25.109/feedbacks.git 
 2. Выполнить docker image build -t feedbacks .
 3. Выполнить docker container run --name feedbacks-container --network="host" -d -p 8081:8081 feedbacks 
    (перед этим можно удалить все запущенные контейнеры docker rm -v -f $(docker ps -qa))
