# Демонстрационное приложение с интеграцией проверок в CI/CD

[![CI Build](https://github.com/reomor/kotlin-project-cicd/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/reomor/kotlin-project-cicd/actions/workflows/build.yml)

## Описание

## Задачи

- SCH-1 Разработка Kotlin/Spring-Boot приложения с одним контроллеров
- SCH-2 Разработка тестов к приложению
- SCH-3 Добавление проверки зависимостей OWASP-уязвимостям
- SCH-4 JavaCodeCoverage отчет
- SCH-5 Проверка доступности обновлений и финальности для пакетов
- SCH-6 Добавление проверки стиля Java
- SCH-7 Добавление проверки стиля Kotlin
- SCH-8 Статический анализ Kotlin с Detekt
- SCH-9 Статический анализ Kotlin с PMD
- SCH-10 Добавление CI в Github Actions
- SCH-11 Build scripts

## Развертывание

## Проверки

- Проверка наличия критических известных уязвимостей в зависимостях [owasp-dependency-checker](https://owasp.org/www-project-dependency-check/)
- Построение отчета о тестировании и покрытии [jacoco](https://docs.gradle.org/current/userguide/jacoco_plugin.html)
- Проверка наличия обновлений для пакетов [gradle-versions-plugin](https://github.com/ben-manes/gradle-versions-plugin)
- Проверка стиля Java [checkstyle_plugin](https://docs.gradle.org/current/userguide/checkstyle_plugin.html) [checkstyle](https://checkstyle.org/)
- Проверка стиля Kotlin [klint](https://github.com/JLLeitschuh/ktlint-gradle) максимально жОский, умеем сам форматировать по правилам
- Статический анализ Kotlin [detekt](https://github.com/detekt/detekt)
- Статический анализ Kotlin [pmd](https://docs.gradle.org/current/userguide/pmd_plugin.html)

## Стек

- Kotlin/Java 17
- SpringBoot 2.7.3
- Cucumber
