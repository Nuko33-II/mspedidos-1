# Despliegue de mspedidos (Sistema de Reservas) en Render

## Opción A — Blueprint automático (`render.yaml`)
1. Sube este proyecto a un repo de GitHub (solo esta carpeta `mspedidos`, ya es
   autocontenida: no depende de ningún `pom.xml` padre externo).
2. En Render: **New → Blueprint** → conecta el repo. Render detecta `render.yaml`
   y crea el servicio automáticamente con el healthcheck y las variables ya definidas.

## Opción B — Manual
1. **New → Web Service** → conecta el repo → Runtime: **Docker**.
2. Dockerfile Path: `Dockerfile` / Docker Context: `.`
3. Variables de entorno:
   ```
   SPRING_PROFILES_ACTIVE=prod
   DB_HOST=mysql-tucuenta.alwaysdata.net
   DB_PORT=3306
   DB_NAME=tucuenta_pedidos
   DB_USER=tu_usuario_alwaysdata
   DB_PASSWORD=tu_password_alwaysdata
   ```
   Estos 5 datos los ves en el panel de AlwaysData en **Databases → MySQL**.
   No necesitas configurar `PORT` manualmente: Render lo inyecta solo y
   `application.yml` ya lo lee con `${PORT:8080}`.
4. Activa **Generate Domain** para obtener la URL pública.

## Base de datos: AlwaysData (MySQL)
1. Crea tu cuenta en https://www.alwaysdata.com (gratis).
2. **Databases → MySQL → Add a database**, ponle nombre (ej: `tucuenta_pedidos`).
3. Crea o usa un **Database user** con todos los privilegios sobre esa base.
4. Anota host, puerto, nombre de base, usuario y contraseña — van directo en las
   variables `DB_HOST` / `DB_PORT` / `DB_NAME` / `DB_USER` / `DB_PASSWORD` de Render.
5. **No necesitas crear tablas a mano**: Flyway corre automáticamente al iniciar la app
   y ejecuta `src/main/resources/db/migration/V1__crear_tabla_pedidos.sql`, que crea
   la tabla `pedidos`.

## Verificar que quedó arriba
```bash
curl https://<tu-servicio>.onrender.com/actuator/health
curl https://<tu-servicio>.onrender.com/api/v1/pedidos
curl https://<tu-servicio>.onrender.com/swagger-ui.html
```
Si `GET /api/v1/pedidos` responde `200 OK` con una lista (vacía si aún no has creado
pedidos), Flyway corrió bien y la app está conectada a MySQL real, no en memoria.

## Qué se ajustó respecto a la versión original del microservicio
- **Se independizó del `pom.xml` padre multi-módulo**: el `.rar` traía este módulo
  apuntando a un `<parent>` (`SistemaDeReservas-parent`) que vive en otro repo/carpeta
  y que Render no puede resolver si solo subes `mspedidos`. Ahora el `pom.xml` usa
  directamente `spring-boot-starter-parent` (igual que `ms-pedidos1`), autocontenido.
- **Corrección de paquete**: `MsPedidosApplication.java` estaba físicamente en
  `com/SistemaDeReserva/` pero declaraba `package com.SistemaDeReserva.mspedidos;`.
  Se movió el archivo a la carpeta correcta para que compile de forma consistente.
- **Se agregó `spring-boot-starter-actuator`** (no estaba en el pom original) para
  exponer `/actuator/health`, usado como healthcheck de Render.
- **Se agregó Lombok explícitamente** como dependencia (antes dependía de que el
  padre externo la trajera; ya se usa en el código con `@Data`, `@Builder`, `@Slf4j`).
- **YAML multi-perfil**: se reemplazó `application.properties` (fijo a MySQL local)
  por `application.yml` (base) + `application-dev.yml` / `-docker.yml` / `-prod.yml`,
  igual patrón que `ms-pedidos1`.
- **Puerto dinámico**: `server.port: ${PORT:8085}` en vez de un puerto fijo, para que
  Render pueda inyectar el suyo.
- **Swagger/OpenAPI**: se agregó `springdoc-openapi-starter-webmvc-ui` con versión
  explícita, disponible en `/swagger-ui.html`.
- **Dockerfile de una sola etapa de build limpia** (sin el truco de instalar un
  "parent-pom" falso en el repositorio Maven local), igual estructura que `ms-pedidos1`.
- `render.yaml` para desplegar con un clic vía Blueprint.
- `.dockerignore` agregado para builds más rápidos y limpios.

## Nota sobre el `pom.xml` padre original
Si en tu repositorio real sí tienes el proyecto multi-módulo completo
(`SistemaDeReservas-parent` + varios microservicios como submódulos) y prefieres
mantener esa estructura para desarrollo local, puedes seguir usándola ahí. Pero
**para desplegar en Render como servicio independiente** (igual que hiciste con
`ms-pedidos1`), este `mspedidos` autocontenido es el que debes subir a su propio
repo o usar como `dockerContext`, ya que Render construye la imagen sin acceso al
resto del monorepo.
