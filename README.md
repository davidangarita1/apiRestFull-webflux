# API Restfull con Webflux
Para esta ejemplo vamos crear una aplicaciones usando el inicializador de Spring Boot una aplicación con las dependencias Web y Mongo Reactivas. 

Siguiendo la estructura típica de un proyecto en Spring vamos a definir el contenido de nuestras paquetes y clases.

## Config
```java
@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {
}
```
Esta clase nos permite configurar al api rest como un recurso que pueda ser visto como un publicador para suscriptores externos al aplicativo.

## Collections

```java
@Document
public class Dato {
    @Id
    private String id;
    private String informacion;
    //Getters and Setters
}
```

Una colección de datos típica para ser usada en una base de datos Mongo



## Repositories
```java
public interface Repositorio extends ReactiveMongoRepository<Dato, String> {
}
```
En este caso el repositorio extiende del repositorio reactivo de mongo, mongodb soporta flujos reactivos sin necesidad de configuraciones adicionales a la base de datos.



## Service
```java
@Service
public class Servicio {
    @Autowired
    Repositorio repositorio;
    public Mono<Dato> crear(Dato dato){
        return repositorio.save(dato);
    }
    public Flux<Dato> buscarTodos() {
        return repositorio.findAll();
    }
}
```
En esta clase de servicio ya podemos ver el uso de Mono o Flux para los datos específicos que se manejan en la aplicación



## Controller
```java
@RestController
@RequestMapping("/datos")
public class Controlador {
    @Autowired
    Servicio servicio;
    @PostMapping("/crear")
    public ResponseEntity<Mono<Dato>> guardar(@RequestBody Dato dato) {
        return new ResponseEntity(servicio.crear(dato), HttpStatus.CREATED);
    }
    @GetMapping()
    public ResponseEntity<List<Dato>> traerTodos() {
        return new ResponseEntity(servicio.buscarTodos(), HttpStatus.OK);
    }
}
```
Podemos evidenciar que las respuestas de los servicios serán también flujos de Mono o Flux

Con esto hemos completado un API básico en Spring usando WebFlux.