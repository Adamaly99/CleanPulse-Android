# CleanPulse — Nettoyeur & Booster

Une application Android native complète pour le nettoyage et l'optimisation des appareils, avec intégration Firebase et monétisation AdMob.

## 📋 Contenu du projet

```
CleanPulse_Android/
├── app/
│   ├── src/main/
│   │   ├── java/com/cleanpulse/
│   │   │   ├── MainActivity.kt                 # Activité principale
│   │   │   ├── Constants.kt                    # Constantes globales
│   │   │   ├── data/
│   │   │   │   ├── Models.kt                   # Modèles de données
│   │   │   │   └── PreferencesManager.kt       # Gestion des préférences
│   │   │   ├── firebase/
│   │   │   │   └── FirebaseManager.kt          # Gestion Firebase
│   │   │   ├── system/
│   │   │   │   └── SystemManager.kt            # Opérations système
│   │   │   ├── ads/
│   │   │   │   └── AdManager.kt                # Gestion AdMob
│   │   │   └── ui/
│   │   │       ├── theme/
│   │   │       │   ├── Theme.kt
│   │   │       │   ├── Typography.kt
│   │   │       │   └── Shape.kt
│   │   │       ├── navigation/
│   │   │       │   └── Navigation.kt
│   │   │       └── screens/
│   │   │           ├── SplashScreen.kt
│   │   │           ├── HomeScreen.kt
│   │   │           ├── AnalysisScreen.kt
│   │   │           ├── CleaningScreen.kt
│   │   │           ├── ResultsScreen.kt
│   │   │           ├── SettingsScreen.kt
│   │   │           └── HistoryScreen.kt
│   │   ├── res/
│   │   │   ├── values/
│   │   │   │   ├── colors.xml
│   │   │   │   ├── strings.xml
│   │   │   │   └── themes.xml
│   │   │   └── xml/
│   │   │       ├── backup_rules.xml
│   │   │       └── data_extraction_rules.xml
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## 🚀 Configuration initiale

### 1. Prérequis

- **Android Studio** 2023.1 ou plus récent
- **Android SDK** 24+ (API Level 24)
- **Kotlin** 1.9.20+
- **Gradle** 8.2.0+
- Compte **Firebase** (gratuit)
- Compte **Google Cloud Console** pour AdMob

### 2. Installation du projet

```bash
# Cloner ou extraire le projet
cd CleanPulse_Android

# Ouvrir dans Android Studio
# File → Open → Sélectionner le dossier CleanPulse_Android
```

### 3. Configuration Firebase

#### Étape 1 : Créer un projet Firebase

1. Accédez à [Firebase Console](https://console.firebase.google.com/)
2. Cliquez sur **"Créer un projet"**
3. Entrez le nom du projet : `CleanPulse`
4. Acceptez les conditions et créez le projet

#### Étape 2 : Ajouter une application Android

1. Dans Firebase Console, cliquez sur **"Ajouter une application"**
2. Sélectionnez **Android**
3. Remplissez les détails :
   - **Nom du package** : `com.cleanpulse`
   - **SHA-1** : (voir section ci-dessous)
   - **Surnom de l'app** : CleanPulse
4. Téléchargez le fichier `google-services.json`

#### Étape 3 : Obtenir votre SHA-1

```bash
# Générer le SHA-1 de votre clé de signature
# Pour la clé de débogage (debug key)
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android

# Pour la clé de signature (release key)
keytool -list -v -keystore /chemin/vers/votre/keystore.jks -alias alias_name
```

Copiez la ligne **SHA1** et ajoutez-la à Firebase Console.

#### Étape 4 : Placer google-services.json

1. Téléchargez `google-services.json` depuis Firebase Console
2. Placez-le dans : `app/google-services.json`

### 4. Configuration AdMob

#### Étape 1 : Créer un compte AdMob

1. Accédez à [Google AdMob](https://admob.google.com/)
2. Créez un compte et liez-le à votre compte Google
3. Créez une application AdMob

#### Étape 2 : Obtenir les IDs d'annonces

1. Dans AdMob, créez les unités publicitaires :
   - **Bannière** : Notez l'ID
   - **Interstitiel** : Notez l'ID
   - **Récompensé** : Notez l'ID

#### Étape 3 : Configurer les IDs

Modifiez `app/src/main/java/com/cleanpulse/Constants.kt` :

```kotlin
const val ADMOB_APP_ID = "ca-app-pub-xxxxxxxxxxxxxxxx~yyyyyyyyyy"
const val ADMOB_BANNER_AD_ID = "ca-app-pub-xxxxxxxxxxxxxxxx/xxxxxxxxxx"
const val ADMOB_INTERSTITIAL_AD_ID = "ca-app-pub-xxxxxxxxxxxxxxxx/xxxxxxxxxx"
const val ADMOB_REWARDED_AD_ID = "ca-app-pub-xxxxxxxxxxxxxxxx/xxxxxxxxxx"
```

**Note** : Les IDs fournis sont des IDs de test. Remplacez-les par vos IDs réels avant la publication.

### 5. Configurer Firestore

1. Dans Firebase Console, accédez à **Firestore Database**
2. Cliquez sur **"Créer une base de données"**
3. Sélectionnez **"Mode production"**
4. Choisissez la région (ex: `europe-west1`)
5. Cliquez sur **"Créer"**

#### Règles de sécurité Firestore

Remplacez les règles par défaut par :

```firestore
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Users collection
    match /users/{userId} {
      allow read, write: if request.auth.uid == userId;
    }
    
    // Logs collection
    match /logs/{document=**} {
      allow write: if request.auth != null;
      allow read: if request.auth.uid == resource.data.uid;
    }
    
    // Stats collection
    match /stats/{userId} {
      allow read, write: if request.auth.uid == userId;
    }
  }
}
```

### 6. Configurer l'authentification Firebase

1. Dans Firebase Console, accédez à **Authentication**
2. Cliquez sur **"Commencer"**
3. Activez les fournisseurs :
   - **Google** : Cliquez sur Google, activez, et entrez votre email de support
   - **Email/Mot de passe** : Cliquez sur Email/Mot de passe, activez

## 🔨 Compilation et construction

### Construire l'APK de débogage

```bash
# Via Android Studio
# Build → Build Bundle(s) / APK(s) → Build APK(s)

# Ou via ligne de commande
./gradlew assembleDebug
```

L'APK sera généré dans : `app/build/outputs/apk/debug/app-debug.apk`

### Construire l'APK de production (release)

#### Étape 1 : Créer une clé de signature

```bash
keytool -genkey -v -keystore cleanpulse-release-key.jks \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias cleanpulse
```

Répondez aux questions pour créer la clé.

#### Étape 2 : Configurer la signature dans build.gradle.kts

Modifiez `app/build.gradle.kts` :

```kotlin
android {
    // ... autres configurations ...
    
    signingConfigs {
        create("release") {
            storeFile = file("../cleanpulse-release-key.jks")
            storePassword = "votre_mot_de_passe"
            keyAlias = "cleanpulse"
            keyPassword = "votre_mot_de_passe"
        }
    }
    
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}
```

#### Étape 3 : Construire l'APK de release

```bash
./gradlew assembleRelease
```

L'APK sera généré dans : `app/build/outputs/apk/release/app-release.apk`

## 📱 Installation et test

### Installer sur un appareil/émulateur

```bash
# Installer l'APK de débogage
adb install app/build/outputs/apk/debug/app-debug.apk

# Ou via Android Studio
# Run → Run 'app'
```

## 🎨 Assets pour le Play Store

### 1. Icône de l'application (512×512 px)

Créez une icône au format PNG avec :
- Dimensions : 512×512 pixels
- Fond blanc ou transparent
- Couleur principale : #00B4FF
- Format : PNG

**Fichier** : `assets/icon_512x512.png`

### 2. Feature Graphic (1024×500 px)

Créez une bannière au format PNG avec :
- Dimensions : 1024×500 pixels
- Couleur principale : #00B4FF
- Fond blanc
- Texte : "CleanPulse — Nettoyeur & Booster"

**Fichier** : `assets/feature_graphic_1024x500.png`

### 3. Screenshots (1080×1920 px)

Créez 2-5 screenshots au format PNG avec :
- Dimensions : 1080×1920 pixels
- Montrant les écrans principaux de l'app

## 📝 Textes pour le Play Store

### Description (Français)

```
CleanPulse — Nettoyeur & Booster

Gardez votre appareil Android rapide, propre et optimisé avec CleanPulse !

🧹 Nettoyage intelligent
- Supprimez les fichiers cache inutiles
- Libérez de l'espace de stockage
- Nettoyage en profondeur avec récompenses

⚡ Optimisation des performances
- Booster RAM
- Refroidissement du processeur
- Gestion intelligente des applications

📊 Analyse détaillée
- Visualisez l'utilisation du stockage
- Catégorisez par Images, Vidéos, Apps, Autres
- Supprimez sélectivement

🔒 Sécurité et confidentialité
- Authentification Firebase sécurisée
- Vos données restent privées
- Pas de suivi publicitaire invasif

✨ Fonctionnalités
- Interface minimaliste et moderne
- Nettoyage automatique programmé
- Historique détaillé
- Support multilingue (FR/EN)
- Mode sombre

Téléchargez CleanPulse maintenant et profitez d'un appareil plus rapide et plus propre !
```

### Description (English)

```
CleanPulse — Cleaner & Booster

Keep your Android device fast, clean, and optimized with CleanPulse!

🧹 Smart Cleaning
- Remove unnecessary cache files
- Free up storage space
- Deep cleaning with rewards

⚡ Performance Optimization
- RAM Booster
- CPU Cooling
- Smart app management

📊 Detailed Analysis
- Visualize storage usage
- Categorize by Images, Videos, Apps, Others
- Selective deletion

🔒 Security & Privacy
- Secure Firebase authentication
- Your data stays private
- No invasive ad tracking

✨ Features
- Minimalist and modern interface
- Scheduled automatic cleaning
- Detailed history
- Multilingual support (FR/EN)
- Dark mode

Download CleanPulse now and enjoy a faster, cleaner device!
```

### Mots-clés

**Français** : nettoyeur android, booster android, optimisation, cache, stockage, performance, android cleaner

**English** : android cleaner, android booster, optimization, cache, storage, performance, device cleaner

## 🚀 Publication sur le Play Store

### Étape 1 : Créer un compte Google Play Developer

1. Accédez à [Google Play Console](https://play.google.com/console)
2. Créez un compte développeur (frais uniques de 25 USD)
3. Complétez votre profil de développeur

### Étape 2 : Créer une application

1. Dans Google Play Console, cliquez sur **"Créer une application"**
2. Entrez le nom : `CleanPulse`
3. Sélectionnez la catégorie : **Outils**
4. Cliquez sur **"Créer une application"**

### Étape 3 : Remplir les informations de l'application

1. **Informations de base**
   - Titre : CleanPulse — Nettoyeur & Booster
   - Description courte : Nettoyeur et optimiseur Android
   - Description complète : (voir section Textes ci-dessus)
   - Catégorie : Outils
   - Type de contenu : Application

2. **Données de contact**
   - Email de contact : support@cleanpulse.app
   - Site web : https://cleanpulse.app
   - Politique de confidentialité : https://cleanpulse.app/privacy

3. **Contenu**
   - Classification du contenu : Complétez le questionnaire
   - Cible d'âge : 3+ ans

### Étape 4 : Télécharger les assets

1. **Icône de l'application** (512×512 PNG)
2. **Feature Graphic** (1024×500 PNG)
3. **Screenshots** (2-5 images 1080×1920 PNG)
4. **Vidéo de présentation** (optionnel)

### Étape 5 : Créer une version de test

1. Allez dans **Versions de test** → **Versions internes**
2. Cliquez sur **"Créer une version"**
3. Téléchargez votre APK de release
4. Remplissez les notes de version
5. Cliquez sur **"Sauvegarder"**

### Étape 6 : Tester avec des testeurs internes

1. Ajoutez des adresses email de testeurs
2. Envoyez le lien de test
3. Collectez les retours

### Étape 7 : Publier en production

1. Allez dans **Versions de production**
2. Cliquez sur **"Créer une version"**
3. Téléchargez votre APK de release
4. Remplissez les notes de version
5. Vérifiez toutes les informations
6. Cliquez sur **"Examiner"** puis **"Confirmer le déploiement"**

## 🔐 Sécurité et bonnes pratiques

### Avant la publication

- [ ] Remplacez les IDs AdMob de test par les vrais IDs
- [ ] Vérifiez les règles de sécurité Firestore
- [ ] Testez sur plusieurs appareils et versions Android
- [ ] Vérifiez les permissions demandées
- [ ] Testez l'authentification Google
- [ ] Vérifiez la politique de confidentialité

### Gestion des clés

- Stockez votre clé de signature dans un endroit sûr
- Ne commitez jamais `google-services.json` ou les clés dans Git
- Utilisez des variables d'environnement pour les secrets

### Mise à jour

Pour mettre à jour l'application :

1. Augmentez `versionCode` et `versionName` dans `build.gradle.kts`
2. Reconstruisez l'APK de release
3. Téléchargez la nouvelle version dans Google Play Console
4. Publiez progressivement (10% → 50% → 100%)

## 📚 Ressources

- [Documentation Android](https://developer.android.com/)
- [Firebase Documentation](https://firebase.google.com/docs)
- [Google Mobile Ads SDK](https://developers.google.com/admob/android/quick-start)
- [Google Play Console Help](https://support.google.com/googleplay/android-developer)
- [Material Design 3](https://m3.material.io/)

## 📧 Support

Pour toute question ou problème :
- Email : support@cleanpulse.app
- Site web : https://cleanpulse.app

## 📄 Licence

CleanPulse est fourni à titre d'exemple éducatif. Adaptez-le selon vos besoins.

---

**Dernière mise à jour** : Novembre 2025
