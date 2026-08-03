# API Gateway — InkluSport

Puerto: **8080**

Punto de entrada único de la plataforma. En local: `http://localhost:8080`.
En público (Cloudflare Tunnel): `https://inklusport.inklusport.uk` (o `http://`
según la config del túnel). El hostname debe apuntar a `http://gateway-service:8080`.

No usa Eureka: enruta por URL fija (localhost en local, nombres Docker en compose).

## Rutas

| Entrada (gateway) | Upstream | Servicio |
|-------------------|----------|----------|
| `/api/auth/**` | `:3001` | ink-ms-auth |
| `/api/plans/**`, `/api/subscriptions/**`, `/api/payments/**`, … | `:3005` | ink-ms-suscriptions |
| `/api/users/**`, `/api/admin/**`, `/api/internal/**` | `:3002` | ink-ms-users |
| `/api/sports/**`, `/api/events/**`, `/api/routines/**`, `/api/routine-registrations/**`, … | `:3003` | ink-ms-sports |
| `/api/preferences/**`, `/api/notifications/**`, `/api/voice/**` | `:3004` | ink-ms-accesibility |
| `/api/analytics/**`, `/api/dashboard/**`, `/api/reports/**` | `:3006` | ink-ms-reports |
| `/api/ai/**` | `:3008` | ink-ms-ai-assistant |

Sin `StripPrefix`: el path llega igual al microservicio.

## Asistente IA (chatbot)

Timeouts del gateway para IA: **180 s** (el LLM puede tardar).

| Método | Ruta pública | RF | Descripción |
|--------|--------------|----|-------------|
| GET | `/api/ai/health` | — | Estado + mapa RF |
| GET | `/api/ai/diagnostico` | — | Ping auth/users/sports/a11y/reports |
| POST | `/api/ai/chat/` | — | Chat del agente |
| POST | `/api/ai/chat/stream` | — | Chat SSE |
| POST | `/api/ai/rutinas/generar` | RF44 | Sesión adaptada (IA) |
| POST | `/api/ai/ejercicios/adaptar` | RF42 | Adaptar ejercicio (+ `/rutinas/adaptar`) |
| POST | `/api/ai/planes/generar` | RF44 | Plan multi-semana |
| POST | `/api/ai/riesgo/lesiones/{userId}` | RF43 | Riesgo de lesión (+ `/riesgo/evaluar`) |
| POST | `/api/ai/fatiga/rpe` | RF45 | Fatiga percibida (`detectar` omitido) |
| POST | `/api/ai/voz/comando` | RF46 | Comando de voz |
| GET | `/api/ai/dashboard/{userId}` | RF47 | Dashboard agregado |
| GET | `/api/ai/progreso/comparativa/{userId}` | RF48 | Comparativa (+ `/historial/comparar`) |
| GET | `/api/ai/recomendacion/eventos/{id}` | RF49 | Eventos |
| GET | `/api/ai/deportes/filtrar/{id}` | RF50/51 | Deportes por perfil |
| POST | `/api/ai/deteccion/discapacidad` | RF52 | Sugerir discapacidad |
| POST | `/api/ai/competencia/modo/{userId}` | RF53 | Modo competencia (+ `/analizar`) |
| POST | `/api/ai/alertas/{entrenadorId}` | RF55 | Alertas (+ `/alertas/entrenador`) |
| POST | `/api/ai/quiz/...` | — | Quices organizador/entrenador |
| POST | `/api/routines` | — | Entrenador crea rutina (sports) |
| POST | `/api/routines/{id}/publish` | — | Publicar rutina (exige quiz ≥ 75) |
| POST | `/api/routine-registrations` | — | Usuario se inscribe a rutina |

Omitidos a propósito: **RF41** (visión artificial), **RF54** (wearables vendor), **RF45 detectar**.

### Ejemplo Postman (público)

```http
POST https://inklusport.inklusport.uk/api/ai/chat/
Content-Type: application/json
Authorization: Bearer <jwt>

{
  "mensaje": "¿Qué eventos hay esta semana?",
  "disability_type": "visual"
}
```

Login previo: `POST /api/auth/login` → usar el token en `Authorization`.

Health rápido:

```http
GET https://inklusport.inklusport.uk/api/ai/health
```

## CORS

Orígenes permitidos: `localhost` / `127.0.0.1` (cualquier puerto),
`https://inklusport.inklusport.uk` y `http://inklusport.inklusport.uk`.

## Fallbacks

Endpoints locales del gateway (útiles si se añade circuit breaker):

- `/fallback/auth`
- `/fallback/users`
- `/fallback/sports`
- `/fallback/ai`
