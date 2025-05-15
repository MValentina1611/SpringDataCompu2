**Paso a paso para  implementar REST-ENDPOINTS de un CRUD y funcionalidades generales**

igue estos pasos en orden para asegurar una implementación correcta.

1. **findAllIngredient()**: Devuelve los ingredientes del usuario autenticado
2. **addIngredient(IngredientDto)**: Crea un nuevo ingrediente
3. **updateIngredient(IngredientDto)**: Actualiza un ingrediente existente
4. **deleteIngredient(long id)**: Elimina un ingrediente
5. **findById(long id)**: Obtiene un ingrediente específico

## Paso a Paso de Implementación

### Paso 1: Crear Entidad Ingredient
- ✅ Revisar `Student.java` como modelo de referencia
- ✅ Crear `Ingredient.java` con los campos: id, name, description, quantity, category
- ✅ Añadir relación `@ManyToOne` con `User`
- ✅ Incluir getters/setters para todos los campos

**Explicación:** Las entidades son la base del modelo de datos. Cada entidad se mapea a una tabla en la base de datos mediante anotaciones JPA. La entidad `Ingredient` debe incluir una relación con `User` para saber a quién pertenece cada ingrediente, similar a cómo `Student` se relaciona con otras entidades en el proyecto de ejemplo.

### Paso 2: Crear IngredientDTO
- ✅ Revisar `StudentDTO.java` como modelo de referencia
- ✅ Crear `IngredientDTO.java` con los mismos campos que la entidad
- ✅ No incluir referencias al usuario (seguridad)
- ✅ Añadir getters/setters para todos los campos

**Explicación:** Los DTOs (Data Transfer Objects) controlan qué datos se exponen a través de la API. Es una buena práctica no exponer directamente las entidades JPA. El DTO debe contener solo los campos que necesita el cliente, sin referencias directas al usuario por razones de seguridad, similar a cómo `StudentDTO` en el proyecto omite ciertas relaciones.

### Paso 3: Crear IngredientMapper
- ✅ Revisar `StudentMapper.java` como modelo de referencia
- ✅ Crear interface `IngredientMapper` con anotación `@Mapper(componentModel = "spring")`
- ✅ Definir métodos `toDTO(Ingredient)` y `toEntity(IngredientDTO)`

**Explicación:** Los mappers son cruciales para convertir entre entidades y DTOs. Usando MapStruct con la anotación `@Mapper`, se genera automáticamente código para estas conversiones. Los métodos `toDTO` y `toEntity` permiten transformar objetos en ambas direcciones. Esto es exactamente como funciona `StudentMapper` en el proyecto de ejemplo.

### Paso 4: Crear IngredientRepository
- ✅ Revisar `StudentRepository.java` como modelo de referencia
- ✅ Crear interface `IngredientRepository` extendiendo `JpaRepository<Ingredient, Long>`
- ✅ Añadir método `List<Ingredient> findByUserId(Long userId)` para filtrar por usuario

**Explicación:** Los repositorios proporcionan acceso a la base de datos. Al extender `JpaRepository`, heredas métodos CRUD básicos (findAll, findById, save, delete). El método personalizado `findByUserId` permitirá filtrar ingredientes por su propietario, similar a cómo `StudentRepository` tiene métodos personalizados como `findByProgram`.

### Paso 5: Crear IngredientService (Interface)
- ✅ Revisar `StudentService.java` como modelo de referencia
- ✅ Definir los 5 métodos necesarios:
  * `List<IngredientDTO> findAllIngredientsByUser(String username)`
  * `void createIngredient(IngredientDTO ingredientDTO, String username)`
  * `IngredientDTO updateIngredient(IngredientDTO ingredientDTO, String username)`
  * `void deleteIngredient(long id, String username)`
  * `IngredientDTO findIngredientById(long id, String username)`

**Explicación:** La interfaz del servicio define el contrato de la lógica de negocio. Cada método corresponde a una operación específica. Observa que todos los métodos reciben el `username` como parámetro, lo que permitirá verificar permisos. Este patrón es similar a cómo `StudentService` define métodos como `getAllStudents()` o `updateStudent()`.

### Paso 6: Implementar IngredientServiceImpl
- ✅ Revisar `StudentServiceImpl.java` y `EnrollmentServiceImpl.java`
- ✅ Implementar cada método con la lógica adecuada:

| Método | Referencia | Adaptación necesaria |
|--------|------------|----------------------|
| `findAllIngredientsByUser` | `getAllStudents()` | Filtrar por usuario |
| `createIngredient` | `createStudent()` | Asignar usuario actual |
| `updateIngredient` | `updateStudent()` | Verificar permisos |
| `deleteIngredient` | `deleteEnrollmentById()` | Verificar permisos |
| `findIngredientById` | `getStudentByID()` | Verificar permisos |

**Explicación:** La implementación del servicio contiene toda la lógica de negocio. Cada método debe:
1. Buscar el usuario actual por username
2. Realizar validaciones apropiadas
3. Ejecutar la operación en el repositorio
4. Convertir resultados usando el mapper

Es crucial añadir verificaciones de permisos para garantizar que solo el propietario (o usuarios con permisos especiales como jdoe) puedan modificar o eliminar ingredientes.

### Paso 7: Configurar Seguridad
- ✅ Revisar `WebSecurityConfig.java`
- ✅ Asegurar que:
  * Rutas con `/api/ingredients/**` requieren autenticación
  * GET `/api/ingredients` y GET `/api/ingredients/{id}` permiten acceso a usuarios con role "READ_INGREDIENT"
  * POST, PUT y DELETE requieren roles específicos (solo para usuarioesoecifico)
- ✅ Revisar `JwtAuthenticationFilter.java` para entender procesamiento de tokens

**Explicación:** La configuración de seguridad define qué endpoints están protegidos y qué roles tienen acceso a cada operación. En `WebSecurityConfig`, se configura que:
- Las rutas de autenticación son públicas
- Las rutas API requieren autenticación
- Ciertos endpoints requieren permisos específicos

Esto, junto con `JwtAuthenticationFilter` que procesa tokens JWT, garantiza que solo usuarios autorizados accedan a las funcionalidades.

### Paso 8: Implementar IngredientController
- ✅ Revisar `StudentRestController.java` y `EnrollmentRestController.java`
- ✅ Crear clase con anotación `@RestController` y `@RequestMapping("/api/ingredients")`
- ✅ Implementar los 5 endpoints con las anotaciones correctas:

| Endpoint | Anotación | Método de servicio |
|----------|-----------|-------------------|
| findAllIngredient | `@GetMapping` | findAllIngredientsByUser |
| addIngredient | `@PostMapping` | createIngredient |
| updateIngredient | `@PutMapping` | updateIngredient |
| deleteIngredient | `@DeleteMapping("/{id}")` | deleteIngredient |
| findById | `@GetMapping("/{id}")` | findIngredientById |

- ✅ Usar `Authentication` para obtener el usuario actual

**Explicación:** El controlador expone los endpoints REST y maneja las peticiones HTTP. Cada método:
1. Está anotado con el verbo HTTP apropiado (`@GetMapping`, `@PostMapping`, etc.)
2. Recibe parámetros vía `@RequestBody` o `@PathVariable` según sea necesario
3. Obtiene el usuario autenticado del objeto `Authentication`
4. Llama al método correspondiente del servicio
5. Devuelve la respuesta HTTP adecuada

Este enfoque es idéntico a cómo `StudentRestController` y `EnrollmentRestController` manejan sus endpoints.

## Lista de Verificación Final

Antes de entregar, verifica:

- [ ] Entidad Ingredient creada correctamente con relación a User
- [ ] DTO, Mapper y Repository implementados
- [ ] Interface de servicio define los 5 métodos requeridos
- [ ] Implementación del servicio incluye verificación de permisos
- [ ] Configuración de seguridad diferencia entre jdoe y asmith
- [ ] Endpoints usan los métodos HTTP correctos (GET, POST, PUT, DELETE)
- [ ] Todos los endpoints obtienen el usuario actual del objeto Authentication
- [ ] Los nombres de endpoints siguen la convención RESTful

## Archivos de Referencia del Proyecto

| Componente | Archivo de Referencia | Sección relevante |
|------------|------------------------|-------------------|
| **Entidad** | `Student.java` | Anotaciones, relaciones, campos |
| **DTO** | `StudentDTO.java` | Estructura simple sin relaciones |
| **Mapper** | `StudentMapper.java` | Métodos toDTO y toEntity |
| **Repositorio** | `StudentRepository.java` | Extensión JpaRepository, métodos query |
| **Servicio (Interface)** | `StudentService.java` | Definición de métodos |
| **Servicio (Impl)** | `StudentServiceImpl.java` | Implementación de CRUD |
| **Eliminación** | `EnrollmentServiceImpl.java` | Método deleteEnrollmentById |
| **Controlador GET/POST** | `StudentRestController.java` | Endpoints findAll, create, update |
| **Controlador DELETE** | `EnrollmentRestController.java` | Endpoint delete |
| **Seguridad JWT** | `JwtAuthenticationFilter.java` | Extracción de claims y roles |
| **Configuración** | `WebSecurityConfig.java` | Reglas de acceso por endpoint |

## Cómo realizar pruebas

1. Inicia la aplicación
2. Obtén tokens JWT haciendo login con ambos usuarios (jdoe y asmith)
3. Prueba los 5 endpoints usando Postman o similar
4. Verifica que jdoe puede hacer todo mientras asmith solo puede listar

---
