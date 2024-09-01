# Movies Microservice JPA

## Posee 2 modos:
- Cloud: En el archivo application.properties setear valor "cloud".

- Local: En el archivo application.properties setear valor "local".

Antes se deben de ejecutar los siguientes archivos para su funcionamiento:
- ./cloud.sql para crear el usuario en cloud.
- ./local.sql para crear el usuario en local.

Favor ajustar en los properties el path del wallet. En mi caso uso Linux y use el path que tengo apuntando a mi wallet en mi carpeta de usuario.

Por defecto los properties estan configurados para crear la bbdd en modo inicialización y ejecuta un sql para auto llenar la información en la tabla movie.

Este sql esta en: ./main/resources/data.sql

Miguel Pérez