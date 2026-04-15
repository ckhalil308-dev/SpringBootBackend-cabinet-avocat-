# API Docs

Base URL: `http://localhost:8081`

Auth header (when protected): `Authorization: Bearer <jwt>`

## Auth
- POST `/api/auth/login` — body: `{ "email": "", "password": "" }` → `AuthResponse` with token.
- POST `/api/auth/register` — body: `ClientRegisterRequest` → `ClientResponse`.

## Users (role: SECRETAIRE unless noted)
- GET `/api/users/secretaire/{id}` → `ClientResponse` (client details by id).
- GET `/api/users/secretaire/all?page=&size=` → `Page<ClientResponse>`.
- GET `/api/users/secretaire/verif?page=&size=` → non-verified clients.
- PUT `/api/users/secretaire/edit/{id}` — body: `ClientRequest` → updated `ClientResponse`.
- DELETE `/api/users/secretaire/del/{id}` → `ApiResponse`.
- PUT `/api/users/secretaire/verify/{id}` → `ClientResponse` (marks verified).
- GET `/api/users/me` (any authenticated) → current `ClientResponse`.
- GET `/api/users/secretaire/dashboard` → `DashboardStatsDTO`.

## Affaires
- POST `/api/affaires/secretaire/creer` (SECRETAIRE) — body: `AffaireRequest` → `AffaireResponse`.
- GET `/api/affaires/client/mine` (CLIENT) → list `AffaireResponse` for current client.
- GET `/api/affaires/secretaire/affaire/{clientId}` (SECRETAIRE) → list by client id.
- GET `/api/affaires/secretaire?statut=&page=&size=` (SECRETAIRE) → paged `AffaireResponse`.
- GET `/api/affaires/secretaire/{id}` (SECRETAIRE) → `AffaireResponse`.
- PUT `/api/affaires/secretaire/{id}` (SECRETAIRE) — body: `AffaireRequest` → `AffaireResponse`.
- DELETE `/api/affaires/secretaire/{id}` (SECRETAIRE) → 204.

## Audiences
- POST `/api/audiences/secretaire` (SECRETAIRE) — body: `AudienceRequest` → `AudienceResponse`.
- GET `/api/audiences/client/mes-audiences` (CLIENT) → list `AudienceResponse` for current client.
- GET `/api/audiences/secretaire/affaires/{affaireId}` (SECRETAIRE) → list by affaire.
- PUT `/api/audiences/secretaire/{id}` (SECRETAIRE) — body: `AudienceRequest` → `AudienceResponse`.
- DELETE `/api/audiences/secretaire/{id}` (SECRETAIRE) → 204.
- PUT `/api/audiences/secretaire/postpone/{id}?nouvelleDate=yyyy-MM-ddTHH:mm:ss&motif=` (SECRETAIRE) → `AudienceResponse`.

## Creneaux
- GET `/api/creneaux/client/disponibles` (CLIENT) — expects `date` path variable (current code expects `/client/disponibles/{date}` format) → list `CreneauResClient`.
- GET `/api/creneaux/client/disponible/{date}` (CLIENT) → list `CreneauResClient` for date (ISO yyyy-MM-dd).
- GET `/api/creneaux/secretaire/{id}` (SECRETAIRE) → `CreneauResSecretaire`.
- GET `/api/creneaux/client/{id}` (CLIENT) → `CreneauResClient`.
- POST `/api/creneaux/secretaire/journal?date=yyyy-MM-dd` (SECRETAIRE) → `ApiResponse` (generate day slots).
- POST `/api/creneaux/secretaire` (SECRETAIRE) — body: `CreneauRequest` → `CreneauResSecretaire`.
- PUT `/api/creneaux/secretaire/{id}` (SECRETAIRE) — body: `ModifierCreneauDto` → `CreneauResSecretaire`.
- POST `/api/creneaux/secretaire/{id}/bloquer?motif=` (SECRETAIRE) → `CreneauResSecretaire`.
- GET `/api/creneaux/secretaire/planning-semaine?dateReference=yyyy-MM-dd` (SECRETAIRE) → list `CreneauResSecretaire`.
- GET `/api/creneaux/client/planning-semaine?dateReference=yyyy-MM-dd` (CLIENT) → list `CreneauResClient`.
- DELETE `/api/creneaux/secretaire/{id}` (SECRETAIRE) → `ApiResponse`.

## Rendez-vous
- POST `/api/rdv/client/proposer` (CLIENT) — body: `RdvRequestDTO` → `RdvResponseDTO`.
- PUT `/api/rdv/secretaire/{id}/confirmer` (SECRETAIRE) → `RdvResponseDTO`.
- PUT `/api/rdv/secretaire/{id}/annuler?motifRefus=` (SECRETAIRE) → `RdvResponseDTO`.
- GET `/api/rdv/secretaire?status=` (SECRETAIRE) → list `RdvResponseDTO` filtered by status.
- GET `/api/rdv/client?status=` (CLIENT) → list `RdvResponseDTO` for current client.

## Security Roles
- Public: `/api/auth/**`
- SECRETAIRE-only: `/api/users/secretaire/**`, `/api/affaires/secretaire/**`, `/api/audiences/secretaire/**`, `/api/creneaux/secretaire/**`, `/api/rdv/secretaire/**`
- CLIENT-only: `/api/users/client/**` (none current), `/api/affaires/client/**`, `/api/audiences/client/**`, `/api/creneaux/client/**`, `/api/rdv/client/**`
- Authenticated: everything else.

