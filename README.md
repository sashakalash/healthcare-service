# Тестирование сервиса медицинских показаний
<h2>Описание</h2>

В репозитории сервиса медицинских показаний находится код приложения для хранения медицинских показаний пациентов клиники (фамилия, имя, дата рождения, кровяное давление, температура), вся информация записывается в файл (репозиторий). Наша задача написать/добавить unit-тесты с использованием библиотеки mockito для проверки корректности работы функционала.

<h3>Что нужно сделать</h3>
<ul>
  <li>Написать тесты для проверки класса MedicalServiceImpl, сделав заглушку для класса PatientInfoFileRepository, который он использует
<ul>
  <li>Проверить вывод сообщения во время проверки давления checkBloodPressure</li>
    <li>Проверить вывод сообщения во время проверки температуры checkTemperature</li>
    <li>Проверить, что сообщения не выводятся, когда показатели в норме</li>
    </ul>
  </li>
</ul>
