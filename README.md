# inventarioTecnoPcMundo

## Development

Antes de que pueda construir este proyecto, debe instalar y configurar las siguientes dependencias en su máquina:

1. [Node.js] []: usamos Node para ejecutar un servidor web de desarrollo y construir el proyecto.
   Dependiendo de su sistema, puede instalar Node desde la fuente o como un paquete empaquetado previamente.

Después de instalar Node, debería poder ejecutar el siguiente comando para instalar herramientas de desarrollo.
Solo necesitará ejecutar este comando cuando las dependencias cambien en [package.json](package.json).

```
npm install
```

Usamos scripts npm y [Angular CLI] [] con [Webpack] [] como nuestro sistema de compilación.

Ejecute los siguientes comandos en dos terminales separados para crear una experiencia de desarrollo maravillosa donde su navegador
se actualiza automáticamente cuando los archivos cambian en su disco duro.

```
./mvnw
npm start
```

Npm también se usa para administrar las dependencias de CSS y JavaScript que se usan en esta aplicación. Puede actualizar las dependencias
especificando una versión más reciente en [package.json] (package.json). También puede ejecutar `npm update` y` npm install` para administrar las dependencias.
Agregue la marca `help` en cualquier comando para ver cómo puede usarlo. Por ejemplo, `npm help update`.

El comando `npm run` listará todos los scripts disponibles para ejecutar en este proyecto.

### PWA Support

Compatibilidad con PWA (aplicación web progresiva) y está desactivado de forma predeterminada. Uno de los componentes principales de una PWA es un trabajador de servicios.

El código de inicialización del trabajador del servicio está deshabilitado de forma predeterminada. Para habilitarlo, descomente el siguiente código en `src / main / webapp / app / app.module.ts`:

```typescript
ServiceWorkerModule.register('ngsw-worker.js', { enabled: false }),
```

## Building for production

### Packaging as jar

Para construir el proyecto final y optimizar la aplicación inventarioTecnoPcMundo para producción, ejecute:

```
./mvnw -Pprod clean verify
```

Esto concatenará y minimizará los archivos CSS y JavaScript del cliente. También modificará `index.html` para que haga referencia a estos nuevos archivos.
Para asegurarse de que todo funcionó, ejecute:

```
java -jar target/*.jar
```

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

Refer to [Using JHipster in production][] for more details.

### Packaging as war

Para empaquetar su aplicación como un .war para implementarla en un servidor de aplicaciones, ejecute:

```
./mvnw -Pprod,war clean verify
```

## Testing

Para iniciar las pruebas de su aplicación, ejecute:

```
./mvnw verify
```

### Client tests

[Jest] [] ejecuta las pruebas unitarias. Están ubicados en [src / test / javascript /] (src / test / javascript /) y se pueden ejecutar con:

```
npm test
```

Las pruebas de interfaz de usuario de un extremo a otro son impulsadas por [Cypress] []. Están ubicados en [src / test / javascript / cypress] (src / test / javascript / cypress)
y se puede ejecutar iniciando Spring Boot en una terminal (`./mvnw spring-boot: run`) y ejecutando las pruebas (` npm run e2e`) en una segunda.

### E2E Webapp Code Coverage

Al usar Cypress, puede generar un informe de cobertura de código ejecutando su servidor de desarrollo con código instrumentado:

Construya su aplicación Angular con código instrumentado:

     npm ejecutar webapp: instrumenter

Inicie su backend sin compilar el frontend:

     npm ejecutar backend: iniciar

Empiece a probar Cypress de principio a fin:

     npm run e2e: cypress: cobertura

El informe de cobertura se genera en `. /Cover / lcov-report /`

### Code quality

La sonda se utiliza para analizar la calidad del código. Puede iniciar un servidor Sonar local (accesible en http: // localhost: 9001) con:

''
docker-compose -f src / main / docker / sonar.yml up -d
''

Nota: hemos desactivado la autenticación en [src / main / docker / sonar.yml] (src / main / docker / sonar.yml) para una experiencia inmediata al probar SonarQube, para casos de uso reales, vuelva a activarlo.

Puede ejecutar un análisis de Sonar usando el [sonar-scanner] (https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) o usando el complemento maven.

Luego, ejecute un análisis de sonda:

''
./mvnw -Pprod clean verificar sonar: sonar
''

Si necesita volver a ejecutar la fase Sonar, asegúrese de especificar al menos la fase `initialize` ya que las propiedades de Sonar se cargan desde el archivo sonar-project.properties.

''
./mvnw inicializar sonar: sonar
''

Para obtener más información, consulte la [Página de calidad del código] [].

## Uso de Docker para simplificar el desarrollo (opcional)

Puede utilizar Docker para mejorar su experiencia de desarrollo de JHipster. Hay varias configuraciones de docker-compose disponibles en la carpeta [src / main / docker] (src / main / docker) para iniciar los servicios de terceros requeridos.

Por ejemplo, para iniciar una base de datos mysql en un contenedor docker, ejecute:

''
docker-compose -f src / main / docker / mysql.yml up -d
''

Para detenerlo y quitar el contenedor, ejecute:

''
docker-compose -f src / main / docker / mysql.yml abajo
''

También puede dockerizar completamente su aplicación y todos los servicios de los que depende.
Para lograr esto, primero cree una imagen de la ventana acoplable de su aplicación ejecutando:

''
./mvnw -Pprod verificar jib: dockerBuild
''

Entonces corre:

''
docker-compose -f src / main / docker / app.yml up -d
''

Para obtener más información, consulte [Uso de Docker y Docker-Compose] [], esta página también contiene información sobre el subgenerador de docker-compose (`jhipster docker-compose`), que puede generar configuraciones de docker para uno o varios JHipster aplicaciones.

## Integración continua (opcional)

Para configurar CI para su proyecto, ejecute el subgenerador ci-cd (`jhipster ci-cd`), esto le permitirá generar archivos de configuración para varios sistemas de Integración Continua. Consulte la página [Configuración de la integración continua] [] para obtener más información.

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 7.1.0 archive]: https://www.jhipster.tech/documentation-archive/v7.1.0
[using jhipster in development]: https://www.jhipster.tech/documentation-archive/v7.1.0/development/
[using docker and docker-compose]: https://www.jhipster.tech/documentation-archive/v7.1.0/docker-compose
[using jhipster in production]: https://www.jhipster.tech/documentation-archive/v7.1.0/production/
[running tests page]: https://www.jhipster.tech/documentation-archive/v7.1.0/running-tests/
[code quality page]: https://www.jhipster.tech/documentation-archive/v7.1.0/code-quality/
[setting up continuous integration]: https://www.jhipster.tech/documentation-archive/v7.1.0/setting-up-ci/
[node.js]: https://nodejs.org/
[webpack]: https://webpack.github.io/
[angular cli]: https://cli.angular.io/
[browsersync]: https://www.browsersync.io/
[jest]: https://facebook.github.io/jest/
[jasmine]: https://jasmine.github.io/2.0/introduction.html
[cypress]: https://www.cypress.io/
[leaflet]: https://leafletjs.com/
[definitelytyped]: https://definitelytyped.org/
