# API Gateway - guia rapida

Puerto: **8080**

Este gateway **no usa Eureka** todavia. Solo reenvia peticiones a cada microservicio por su URL fija (localhost + puerto).

## Que es Eureka (en criollo)

Eureka es como una **lista telefonica** de microservicios:

1. Cada MS al arrancar dice: "hola, yo soy users y estoy en tal IP/puerto".
2. El gateway pregunta a Eureka: "donde esta users?" y enruta solo.

En este repo **eureka-server existe como carpeta** pero **no hay codigo ni configuracion** en users, sports ni IA. Por eso aqui usamos rutas directas.

Cuando el equipo implemente Eureka de verdad, se cambia `uri: http://localhost:8087` por `lb://ai-assistant-ms`.

## Rutas actuales

| Entrada (gateway) | Va hacia | MS |
|-------------------|----------|-----|
| `http://localhost:8080/api/ia/**` | `http://localhost:8087` | ai-assistant-ms |
| `http://localhost:8080/api/users/**` | `http://localhost:3002` | ink-ms-users |
| `http://localhost:8080/api/sports/**` | `http://localhost:3003` | ink-ms-sports |
| `http://localhost:8080/api/auth/**` | `http://localhost:3001` | ink-ms-auth |

## Probar IA por el gateway

1. Levanta Mongo (si usas persistencia en IA).
2. Levanta IA: `cd ai-assistant-ms && mvn spring-boot:run`
3. Levanta gateway: `cd api-gateway && mvn spring-boot:run`
4. Postman:

```http
POST http://localhost:8080/api/ia/analisis
Content-Type: application/json

{
  "usuarioId": "user-001",
  "tipoDiscapacidad": "MOTORA",
  "rangoMovimiento": 65,
  "simetria": 70,
  "estabilidad": 60
}
```

Es la misma ruta que en el MS directo, solo cambia el puerto **8080** en vez de **8087**.

## Si un MS no responde

El gateway devuelve error 502/503 si el servicio de atras no esta prendido. Prende primero el microservicio y despues el gateway.
