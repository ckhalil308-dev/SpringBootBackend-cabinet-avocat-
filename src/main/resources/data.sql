-- Seed data without touching users (expects client/secretaire users to already exist)
DELETE FROM document;
DELETE FROM audience;
DELETE FROM rendez_vous;
DELETE FROM affaire;
DELETE FROM creneau;
DELETE FROM users;

INSERT INTO users (id, nom, prenom, numTelephone, email, password, role, estVerifie) VALUES
  (1, 'Secretaire', 'Sophie', 11111111, 'secretaire@example.com', 'Secret123!', 'SECRETAIRE', true),
  (2, 'Client', 'Alice', 22222222, 'alice.client@example.com', 'Client123!', 'CLIENT', true),
  (3, 'Client', 'Bob', 33333333, 'bob.client@example.com', 'Client234!', 'CLIENT', true);

INSERT INTO creneau (id, dateJour, heureDebut, heureFin, titre, reserve) VALUES
  (1, '2024-06-10', '09:00:00', '09:30:00', 'Consultation initiale', false),
  (2, '2024-06-11', '11:00:00', '11:30:00', 'Reunion strategie', false),
  (3, '2024-06-12', '15:00:00', '15:30:00', 'Preparation audience', true);

INSERT INTO affaire (id, titre, description, numeroDossier, dateOuverture, statut, client_id) VALUES
  (1, 'Litige commercial', 'Conflit sur contrat de prestation', 'DOS-2024-001', '2024-05-15', 'EN_COURS', 2),
  (2, 'Droit du travail', 'Licenciement conteste', 'DOS-2024-002', '2024-05-20', 'GAGNEE', 3);

INSERT INTO audience (id, dateAudience, tribunal, salle, remarques, affaire_id) VALUES
  (1, '2024-06-18T10:00:00', 'Tribunal de Paris', 'A1', 'Audience preliminaire', 1),
  (2, '2024-06-20T14:30:00', 'Tribunal de Lyon', 'B2', 'Temoins requis', 2);

INSERT INTO document (id, nom, type, cheminFichier, dateUpload, affaire_id) VALUES
  (1, 'Contrat.pdf', 'PDF', '/docs/contrat.pdf', '2024-05-16T09:00:00', 1),
  (2, 'LettreLicenciement.pdf', 'PDF', '/docs/licenciement.pdf', '2024-05-21T14:30:00', 2);

INSERT INTO rendez_vous (id, motif, message, status, dateCreation, client_id, creneau_id) VALUES
  (1, 'Consultation', 'Premier echange', 'PENDING', '2024-06-08T08:00:00', 2, 1),
  (2, 'Suivi', 'Mise a jour dossier', 'CONFIRMED', '2024-06-08T09:00:00', 3, 3);
