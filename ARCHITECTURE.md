# Architecture Technique - CleanPulse

## 📐 Vue d'ensemble de l'architecture

CleanPulse suit une architecture moderne et modulaire basée sur les meilleures pratiques Android.

```
┌─────────────────────────────────────────────────────────────┐
│                    Jetpack Compose UI Layer                 │
│  (SplashScreen, HomeScreen, AnalysisScreen, etc.)          │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                    Navigation Layer                          │
│              (Navigation.kt - Wouter-like routing)          │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                    Business Logic Layer                      │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐      │
│  │ SystemManager│  │FirebaseManager│  │  AdManager   │      │
│  └──────────────┘  └──────────────┘  └──────────────┘      │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                    Data Layer                                │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐      │
│  │Preferences   │  │  Firestore   │  │   Firebase   │      │
│  │  Manager     │  │  Database    │  │   Storage    │      │
│  └──────────────┘  └──────────────┘  └──────────────┘      │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                    External Services                         │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐      │
│  │   Firebase   │  │    AdMob     │  │   Android    │      │
│  │   Backend    │  │   Services   │  │   System     │      │
│  └──────────────┘  └──────────────┘  └──────────────┘      │
└─────────────────────────────────────────────────────────────┘
```

## 🏗️ Structure des packages

```
com.cleanpulse/
├── MainActivity.kt                    # Point d'entrée de l'application
├── Constants.kt                       # Constantes globales
│
├── data/
│   ├── Models.kt                      # Modèles de données (User, StorageItem, etc.)
│   └── PreferencesManager.kt          # Gestion des préférences locales (DataStore)
│
├── firebase/
│   └── FirebaseManager.kt             # Gestion Firebase (Auth, Firestore, Analytics)
│
├── system/
│   └── SystemManager.kt               # Opérations système (nettoyage, RAM, etc.)
│
├── ads/
│   └── AdManager.kt                   # Gestion AdMob (Bannière, Interstitiel, Récompensé)
│
└── ui/
    ├── theme/
    │   ├── Theme.kt                   # Thème Material Design 3
    │   ├── Typography.kt              # Typographie
    │   └── Shape.kt                   # Formes
    │
    ├── navigation/
    │   └── Navigation.kt              # Système de navigation
    │
    └── screens/
        ├── SplashScreen.kt            # Écran de démarrage (2s animation)
        ├── HomeScreen.kt              # Écran d'accueil principal
        ├── AnalysisScreen.kt          # Analyse du stockage
        ├── CleaningScreen.kt          # Écran de nettoyage avec progression
        ├── ResultsScreen.kt           # Résultats du nettoyage
        ├── SettingsScreen.kt          # Paramètres
        └── HistoryScreen.kt           # Historique des nettoyages
```

## 🎨 Design System

### Couleurs

| Couleur | Valeur | Utilisation |
|---------|--------|-------------|
| Primary | #00B4FF | Boutons, indicateurs, accents |
| Primary Variant | #0099CC | États pressés, variantes |
| Secondary | #1A1A1A | Texte principal, arrière-plans |
| Background | #FFFFFF | Fond principal |
| Surface | #F5F5F5 | Cartes, surfaces secondaires |
| Success | #4CAF50 | Confirmations, succès |
| Warning | #FFC107 | Avertissements |
| Error | #F44336 | Erreurs |

### Typographie

La typographie utilise la police système (sans-serif) avec les styles Material Design 3 :

- **Display Large** : 57sp, Bold
- **Headline Large** : 32sp, Bold
- **Title Large** : 22sp, Bold
- **Body Large** : 16sp, Normal
- **Label Large** : 14sp, SemiBold

### Formes

- **Extra Small** : 4dp (petits éléments)
- **Small** : 8dp (boutons, chips)
- **Medium** : 12dp (cartes, dialogues)
- **Large** : 16dp (surfaces principales)
- **Extra Large** : 28dp (formes circulaires)

## 🔄 Flux de données

### Authentification

```
User → Google Sign-In → FirebaseManager.signInWithGoogle()
                     ↓
                Firebase Auth
                     ↓
                Firestore (Save User)
                     ↓
                PreferencesManager (Save Local)
                     ↓
                UI Update (HomeScreen)
```

### Nettoyage

```
User → "Nettoyer maintenant" → CleaningScreen
                            ↓
                    SystemManager.clearAppCache()
                            ↓
                    FirebaseManager.updateUserStats()
                            ↓
                    PreferencesManager.updateTotalSpaceFreed()
                            ↓
                    ResultsScreen (Show Results)
```

### Analyse du stockage

```
User → "Analyse" → AnalysisScreen
                ↓
        SystemManager.scanImages()
        SystemManager.scanVideos()
        SystemManager.scanCacheFiles()
                ↓
        Display StorageItems
                ↓
        User selects items
                ↓
        SystemManager.deleteFiles()
                ↓
        Update UI
```

## 🔐 Sécurité

### Authentification

- **Firebase Authentication** : Gestion sécurisée des utilisateurs
- **Google Sign-In** : OAuth 2.0 avec vérification SHA-1
- **Email/Mot de passe** : Hashage sécurisé par Firebase

### Données

- **Firestore Security Rules** : Accès basé sur l'authentification
- **DataStore** : Chiffrement des préférences locales
- **Cloud Storage** : Accès sécurisé aux fichiers

### Permissions

- **READ_EXTERNAL_STORAGE** : Accès en lecture au stockage
- **WRITE_EXTERNAL_STORAGE** : Accès en écriture au stockage
- **MANAGE_EXTERNAL_STORAGE** : Gestion complète du stockage (Android 11+)
- **PACKAGE_USAGE_STATS** : Accès aux statistiques d'utilisation
- **FOREGROUND_SERVICE** : Service de nettoyage en arrière-plan

## 📊 Modèles de données

### User

```kotlin
data class User(
    val uid: String,              // Firebase UID
    val email: String,            // Email utilisateur
    val displayName: String,      // Nom d'affichage
    val photoUrl: String,         // URL de la photo
    val createdAt: Date,          // Date de création
    val lastLogin: Date,          // Dernière connexion
    val totalSpaceFreed: Long,    // Espace total libéré
    val totalRamFreed: Long,      // RAM totale libérée
    val cleaningCount: Int        // Nombre de nettoyages
)
```

### StorageItem

```kotlin
data class StorageItem(
    val id: String,               // Identifiant unique
    val name: String,             // Nom du fichier
    val size: Long,               // Taille en bytes
    val category: StorageCategory, // Catégorie (Images, Vidéos, etc.)
    val path: String,             // Chemin du fichier
    val lastModified: Date,       // Dernière modification
    val isSelected: Boolean       // Sélectionné pour suppression
)
```

### CleaningResult

```kotlin
data class CleaningResult(
    val totalItemsDeleted: Int,   // Nombre d'éléments supprimés
    val spaceFreed: Long,         // Espace libéré
    val ramFreed: Long,           // RAM libérée
    val duration: Long,           // Durée du nettoyage
    val timestamp: Date,          // Timestamp du nettoyage
    val deletedItems: List<String>, // Liste des fichiers supprimés
    val success: Boolean          // Succès de l'opération
)
```

## 🎯 Décisions de conception

### Pourquoi Jetpack Compose ?

- **Moderne** : Framework UI déclaratif et réactif
- **Performant** : Recomposition efficace
- **Maintenable** : Code plus lisible et testable
- **Flexible** : Personnalisation facile

### Pourquoi Material Design 3 ?

- **Cohérent** : Design language officiel de Google
- **Accessible** : Conformité WCAG 2.1
- **Moderne** : Couleurs dynamiques, animations fluides
- **Reconnaissable** : Utilisateurs familiers avec le design

### Pourquoi Firebase ?

- **Authentification** : Gestion sécurisée des utilisateurs
- **Firestore** : Base de données en temps réel
- **Analytics** : Suivi des événements
- **Storage** : Stockage cloud sécurisé
- **Gratuit** : Plan gratuit généreux

### Pourquoi AdMob ?

- **Monétisation** : Revenus publicitaires
- **Intégration** : Facile à intégrer avec Firebase
- **Flexibilité** : Bannière, Interstitiel, Récompensé
- **Officiel** : Service Google

### Pourquoi DataStore ?

- **Moderne** : Remplace SharedPreferences
- **Asynchrone** : Basé sur Coroutines
- **Chiffré** : Chiffrement par défaut
- **Type-safe** : Typage statique

## 🔄 Cycle de vie de l'application

```
MainActivity.onCreate()
        ↓
    ThemeProvider
        ↓
    AppNavigation
        ↓
    SplashScreen (2s animation)
        ↓
    HomeScreen
        ↓
    User Navigation
        ├─→ AnalysisScreen
        ├─→ CleaningScreen → ResultsScreen
        ├─→ SettingsScreen
        └─→ HistoryScreen
```

## 🧪 Stratégie de test

### Tests unitaires

- Tester `SystemManager` (nettoyage, analyse)
- Tester `FirebaseManager` (authentification, Firestore)
- Tester `PreferencesManager` (stockage local)

### Tests d'intégration

- Tester le flux d'authentification complet
- Tester le flux de nettoyage complet
- Tester la sauvegarde et récupération des données

### Tests UI

- Tester la navigation entre les écrans
- Tester les interactions utilisateur
- Tester les animations

## 📈 Performance

### Optimisations

- **Minification** : Réduction de la taille de l'APK
- **Compression des ressources** : Réduction des assets
- **Lazy loading** : Chargement à la demande
- **Coroutines** : Opérations asynchrones
- **Caching** : Mise en cache des données

### Métriques cibles

- **Taille APK** : < 50 MB
- **Temps de démarrage** : < 2 secondes
- **Consommation mémoire** : < 100 MB
- **Taux de crash** : < 0.1%

## 🔄 Mise à jour et maintenance

### Versioning

- **versionCode** : Numéro interne (incrémenté à chaque build)
- **versionName** : Version publique (ex: 1.0.0)

### Processus de mise à jour

1. Augmenter `versionCode` et `versionName`
2. Apporter les modifications
3. Tester sur plusieurs appareils
4. Compiler l'APK de release
5. Signer l'APK
6. Télécharger sur Play Store
7. Publier progressivement

## 📚 Ressources et références

- [Android Architecture Components](https://developer.android.com/topic/architecture)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [Firebase Documentation](https://firebase.google.com/docs)
- [Google Mobile Ads SDK](https://developers.google.com/admob/android/quick-start)

---

**Dernière mise à jour** : Novembre 2025
