# NoteÉtudiant

Application Android de prise de notes pour étudiants, développée en Kotlin avec architecture MVVM.

## Fonctionnalités

- **Créer** une note avec un titre, un contenu et un module
- **Consulter** le détail d'une note
- **Modifier** une note existante
- **Supprimer** une note
- **Filtrer** les notes par module via des chips cliquables
- **Rechercher** dans les titres et contenus
- **Marquer en favori** et filtrer par favoris
- **Persistance** : les notes sont conservées entre les sessions (base de données locale)

## Architecture

```
app/
└── src/main/java/com/example/android_projet/
    ├── data/
    │   ├── model/          # Note.kt, ModuleCount.kt
    │   ├── dao/            # NoteDao.kt (requêtes Room)
    │   ├── database/       # NoteDatabase.kt (singleton Room)
    │   └── repository/     # NoteRepository.kt
    ├── ui/
    │   ├── notelist/       # Liste + filtres (Fragment + ViewModel + Adapter)
    │   ├── notedetail/     # Détail d'une note (Fragment + ViewModel)
    │   └── noteedit/       # Création / édition (Fragment + ViewModel)
    ├── MainActivity.kt
    └── NoteApplication.kt
```

Pattern : **MVVM** (Model – View – ViewModel) avec Repository.

## Stack technique

| Composant | Bibliothèque |
|---|---|
| Langage | Kotlin 2.2.10 |
| UI | Fragments + View Binding + Material Design 3 |
| Navigation | Jetpack Navigation Component 2.7.7 |
| Base de données | Room 2.8.4 |
| Traitement annotations | KSP 2.2.10-2.0.2 |
| Asynchrone | Coroutines + Flow + LiveData |
| Build | AGP 9.2.1 |

## Modèle de données

```kotlin
data class Note(
    val id: Long,           // clé primaire auto-générée
    val title: String,
    val content: String,
    val module: String,
    val isFavorite: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)
```

## Prérequis

- Android Studio Hedgehog ou supérieur
- SDK Android 24+ (Android 7.0 minimum)
- SDK cible : 34 (Android 14)

## Lancer le projet

1. Cloner ou ouvrir le dossier dans Android Studio
2. Laisser Gradle synchroniser les dépendances
3. Lancer sur un émulateur ou appareil physique (API 24+)

```bash
./gradlew installDebug
```
