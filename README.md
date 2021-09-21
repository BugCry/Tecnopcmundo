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
