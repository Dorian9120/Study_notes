# Grille de tests — NoteÉtudiant

**Prérequis** : application installée, base de données vide (première installation ou données effacées).  
**Convention** : ✅ Passé · ❌ Échoué · ⚠️ Partiel

---

## Jeu de données de référence

Créer ces 5 notes dans l'ordre indiqué avant de commencer les tests de filtrage et de recherche.

| # | Titre | Module | Contenu | Favori |
|---|---|---|---|---|
| N1 | Bases de la POO | Informatique | Encapsulation, héritage, polymorphisme | Non |
| N2 | Loi de Newton | Physique | F = ma. Les trois lois du mouvement. | Oui |
| N3 | Dérivées et intégrales | Mathématiques | Règle de la chaîne, intégrale de Riemann | Non |
| N4 | Patron de conception MVC | Informatique | Modèle, Vue, Contrôleur. Séparation des responsabilités. | Oui |
| N5 | Thermodynamique | Physique | Les quatre lois de la thermodynamique | Non |

---

## 1. Création de note

### T01 — Créer une note valide (N1)
| Étape | Action | Résultat attendu | Résultat | 
|---|---|---|---|
| 1 | Ouvrir l'application | Écran liste vide, message "Aucune note pour le moment" visible | |
| 2 | Appuyer sur le bouton **+** | Écran de création s'ouvre | |
| 3 | Saisir le titre : `Bases de la POO` | Champ titre rempli | |
| 4 | Saisir le module : `Informatique` | Champ module rempli | |
| 5 | Saisir le contenu : `Encapsulation, héritage, polymorphisme` | Champ contenu rempli | |
| 6 | Appuyer sur **Enregistrer** | Retour à la liste, la note N1 apparaît | |

### T02 — Créer une note sans titre
| Étape | Action | Résultat attendu | Résultat |
|---|---|---|---|
| 1 | Appuyer sur **+** | Écran de création s'ouvre | |
| 2 | Laisser le titre vide | — | |
| 3 | Saisir le module : `Test` | — | |
| 4 | Appuyer sur **Enregistrer** | Message d'erreur "Le titre est obligatoire", note non créée | |

### T03 — Créer une note sans module
| Étape | Action | Résultat attendu | Résultat |
|---|---|---|---|
| 1 | Appuyer sur **+** | Écran de création s'ouvre | |
| 2 | Saisir le titre : `Note sans module` | — | |
| 3 | Laisser le module vide | — | |
| 4 | Appuyer sur **Enregistrer** | Message d'erreur "Le module est obligatoire", note non créée | |

### T04 — Créer les notes N2 à N5
Répéter la procédure de T01 avec les données du jeu de référence.  
**Résultat attendu** : 5 notes dans la liste, triées de la plus récente à la plus ancienne (N5, N4, N3, N2, N1).

---

## 2. Affichage de la liste

### T05 — Affichage après création du jeu de données
| Vérification | Résultat attendu | Résultat |
|---|---|---|
| 5 notes visibles dans la liste | N5, N4, N3, N2, N1 dans cet ordre | |
| Chips de modules présents | "Tous", "⭐ Favoris", "Informatique (2)", "Mathématiques (1)", "Physique (2)" | |
| Chip "Tous" sélectionné par défaut | Chip "Tous" en surbrillance | |

---

## 3. Détail d'une note

### T06 — Ouvrir le détail de N1
| Étape | Action | Résultat attendu | Résultat |
|---|---|---|---|
| 1 | Appuyer sur la note "Bases de la POO" | Écran détail s'ouvre | |
| 2 | Vérifier le titre affiché | `Bases de la POO` | |
| 3 | Vérifier le module affiché | `Informatique` | |
| 4 | Vérifier le contenu affiché | `Encapsulation, héritage, polymorphisme` | |
| 5 | Appuyer sur le bouton Retour | Retour à la liste, 5 notes toujours présentes | |

---

## 4. Modification d'une note

### T07 — Modifier le contenu de N3
| Étape | Action | Résultat attendu | Résultat |
|---|---|---|---|
| 1 | Ouvrir le détail de "Dérivées et intégrales" | Écran détail s'ouvre | |
| 2 | Appuyer sur **Modifier** | Écran d'édition s'ouvre avec les champs pré-remplis | |
| 3 | Vérifier que le titre est `Dérivées et intégrales` | Champ titre pré-rempli | |
| 4 | Ajouter au contenu : `, théorème fondamental` | Contenu mis à jour | |
| 5 | Appuyer sur **Enregistrer** | Retour à la liste | |
| 6 | Ouvrir à nouveau le détail de N3 | Le nouveau contenu est affiché | |

### T08 — Changer le module de N3
| Étape | Action | Résultat attendu | Résultat |
|---|---|---|---|
| 1 | Ouvrir l'édition de "Dérivées et intégrales" | Écran d'édition pré-rempli | |
| 2 | Remplacer le module par `Analyse` | Champ module = `Analyse` | |
| 3 | Enregistrer | Retour à la liste | |
| 4 | Vérifier les chips | Chip "Mathématiques (1)" remplacé par "Analyse (1)" | |
| 5 | Remettre le module à `Mathématiques` et enregistrer | Chips retrouvent leur état initial | |

---

## 5. Suppression d'une note

### T09 — Supprimer N5
| Étape | Action | Résultat attendu | Résultat |
|---|---|---|---|
| 1 | Ouvrir le détail de "Thermodynamique" | Écran détail s'ouvre | |
| 2 | Appuyer sur **Supprimer** | Note supprimée, retour à la liste | |
| 3 | Vérifier la liste | 4 notes restantes, "Thermodynamique" absente | |
| 4 | Vérifier les chips | "Physique (2)" devient "Physique (1)" | |

> Recréer N5 après ce test pour ne pas perturber les tests suivants.

---

## 6. Filtrage par module

> Prérequis : les 5 notes du jeu de référence sont présentes.

### T10 — Filtrer par "Informatique"
| Étape | Action | Résultat attendu | Résultat |
|---|---|---|---|
| 1 | Appuyer sur le chip `Informatique (2)` | Chip sélectionné | |
| 2 | Vérifier la liste | 2 notes : "Patron de conception MVC" et "Bases de la POO" | |
| 3 | Vérifier les autres notes | N2, N3, N5 ne sont pas affichées | |

### T11 — Filtrer par "Physique"
| Étape | Action | Résultat attendu | Résultat |
|---|---|---|---|
| 1 | Appuyer sur le chip `Physique (2)` | — | |
| 2 | Vérifier la liste | 2 notes : "Thermodynamique" et "Loi de Newton" | |

### T12 — Filtrer par "Mathématiques"
| Étape | Action | Résultat attendu | Résultat |
|---|---|---|---|
| 1 | Appuyer sur le chip `Mathématiques (1)` | — | |
| 2 | Vérifier la liste | 1 note : "Dérivées et intégrales" | |

### T13 — Revenir à "Tous"
| Étape | Action | Résultat attendu | Résultat |
|---|---|---|---|
| 1 | Appuyer sur le chip `Tous` | Chip "Tous" sélectionné | |
| 2 | Vérifier la liste | Les 5 notes sont à nouveau affichées | |

---

## 7. Recherche

### T14 — Recherche par titre
| Étape | Action | Résultat attendu | Résultat |
|---|---|---|---|
| 1 | Saisir `Newton` dans la barre de recherche | — | |
| 2 | Vérifier la liste | 1 note : "Loi de Newton" | |
| 3 | Effacer la recherche | Les 5 notes réapparaissent | |

### T15 — Recherche par contenu
| Étape | Action | Résultat attendu | Résultat |
|---|---|---|---|
| 1 | Saisir `héritage` dans la barre de recherche | — | |
| 2 | Vérifier la liste | 1 note : "Bases de la POO" | |

### T16 — Recherche sans résultat
| Étape | Action | Résultat attendu | Résultat |
|---|---|---|---|
| 1 | Saisir `zzzzz` dans la barre de recherche | — | |
| 2 | Vérifier la liste | Message "Aucune note pour le moment" affiché | |

---

## 8. Favoris

### T17 — Filtrer par favoris
| Étape | Action | Résultat attendu | Résultat |
|---|---|---|---|
| 1 | Appuyer sur le chip `⭐ Favoris` | Chip sélectionné | |
| 2 | Vérifier la liste | 2 notes : "Patron de conception MVC" (N4) et "Loi de Newton" (N2) | |

### T18 — Marquer une note en favori depuis la liste
| Étape | Action | Résultat attendu | Résultat |
|---|---|---|---|
| 1 | Appuyer sur l'icône étoile de "Bases de la POO" (N1) | Étoile activée | |
| 2 | Appuyer sur le chip `⭐ Favoris` | 3 notes affichées (N1, N2, N4) | |
| 3 | Désactiver le favori de N1 | N1 disparaît des favoris | |

---

## 9. Persistance des données

### T19 — Les données survivent à un redémarrage
| Étape | Action | Résultat attendu | Résultat |
|---|---|---|---|
| 1 | Fermer complètement l'application | — | |
| 2 | Rouvrir l'application | Les 5 notes sont toujours présentes | |
| 3 | Vérifier les chips de modules | Informatique (2), Mathématiques (1), Physique (2) | |

---

## Récapitulatif

| ID | Scénario | Résultat |
|---|---|---|
| T01 | Créer une note valide | |
| T02 | Créer sans titre | |
| T03 | Créer sans module | |
| T04 | Créer le jeu de données complet | |
| T05 | Affichage de la liste | |
| T06 | Détail d'une note | |
| T07 | Modifier le contenu | |
| T08 | Changer de module | |
| T09 | Supprimer une note | |
| T10 | Filtrer par Informatique | |
| T11 | Filtrer par Physique | |
| T12 | Filtrer par Mathématiques | |
| T13 | Revenir à Tous | |
| T14 | Recherche par titre | |
| T15 | Recherche par contenu | |
| T16 | Recherche sans résultat | |
| T17 | Filtrer les favoris | |
| T18 | Marquer en favori | |
| T19 | Persistance après redémarrage | |
