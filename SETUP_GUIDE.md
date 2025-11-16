# Guide Complet de Configuration et Publication - CleanPulse

## 📋 Table des matières

1. [Configuration Firebase](#configuration-firebase)
2. [Configuration AdMob](#configuration-admob)
3. [Génération des clés de signature](#génération-des-clés-de-signature)
4. [Construction de l'APK](#construction-de-lapk)
5. [Publication Play Store](#publication-play-store)
6. [Dépannage](#dépannage)

---

## 🔥 Configuration Firebase

### Étape 1 : Créer un projet Firebase

1. Accédez à [Firebase Console](https://console.firebase.google.com/)
2. Cliquez sur **"Créer un projet"**
3. Entrez le nom du projet : `CleanPulse`
4. Acceptez les conditions de service
5. Cliquez sur **"Créer un projet"**
6. Attendez que le projet soit créé (2-3 minutes)

### Étape 2 : Ajouter une application Android

1. Dans Firebase Console, cliquez sur **"Ajouter une application"**
2. Sélectionnez **Android**
3. Remplissez les informations :
   - **Nom du package** : `com.cleanpulse`
   - **Surnom de l'app** : CleanPulse
   - **SHA-1** : (voir section ci-dessous)
4. Cliquez sur **"Enregistrer l'application"**

### Étape 3 : Obtenir votre SHA-1

Le SHA-1 est une empreinte numérique de votre clé de signature. Vous en avez besoin pour Firebase.

#### Pour la clé de débogage (debug)

```bash
# Windows
keytool -list -v -keystore %USERPROFILE%\.android\debug.keystore -alias androiddebugkey -storepass android -keypass android

# macOS/Linux
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
```

Cherchez la ligne **SHA1** dans la sortie. Exemple :
```
SHA1: AA:BB:CC:DD:EE:FF:00:11:22:33:44:55:66:77:88:99:AA:BB:CC:DD
```

Copiez cette valeur (sans les deux-points) et collez-la dans Firebase Console.

#### Pour la clé de signature (release)

```bash
keytool -list -v -keystore /chemin/vers/votre/cleanpulse-release-key.jks
```

Entrez le mot de passe de votre keystore et cherchez la ligne SHA1.

### Étape 4 : Télécharger google-services.json

1. Dans Firebase Console, après avoir enregistré l'application Android
2. Cliquez sur **"Télécharger google-services.json"**
3. Sauvegardez le fichier dans : `app/google-services.json`

### Étape 5 : Configurer Firestore

1. Dans Firebase Console, allez à **Firestore Database**
2. Cliquez sur **"Créer une base de données"**
3. Sélectionnez **"Mode production"**
4. Choisissez la région : `europe-west1` (ou votre région)
5. Cliquez sur **"Créer"**

#### Configurer les règles de sécurité

1. Allez à **Firestore** → **Règles**
2. Remplacez le contenu par :

```firestore
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Users collection - only accessible by the user
    match /users/{userId} {
      allow read, write: if request.auth.uid == userId;
    }
    
    // Logs collection - write by authenticated users, read by owner
    match /logs/{document=**} {
      allow write: if request.auth != null;
      allow read: if request.auth.uid == resource.data.uid;
    }
    
    // Stats collection - only accessible by the user
    match /stats/{userId} {
      allow read, write: if request.auth.uid == userId;
    }
  }
}
```

3. Cliquez sur **"Publier"**

### Étape 6 : Configurer l'authentification Firebase

1. Dans Firebase Console, allez à **Authentication**
2. Cliquez sur **"Commencer"**
3. Activez les fournisseurs :

#### Google Sign-In

1. Cliquez sur **Google**
2. Activez le bouton
3. Entrez votre email de support dans **"Email de support du projet"**
4. Cliquez sur **"Sauvegarder"**

#### Email/Mot de passe

1. Cliquez sur **Email/Mot de passe**
2. Activez le bouton
3. Cliquez sur **"Sauvegarder"**

### Étape 7 : Configurer Cloud Storage (optionnel)

1. Dans Firebase Console, allez à **Storage**
2. Cliquez sur **"Commencer"**
3. Acceptez les règles par défaut
4. Choisissez la région : `europe-west1`
5. Cliquez sur **"Terminé"**

---

## 📱 Configuration AdMob

### Étape 1 : Créer un compte AdMob

1. Accédez à [Google AdMob](https://admob.google.com/)
2. Connectez-vous avec votre compte Google
3. Cliquez sur **"Commencer"**
4. Acceptez les conditions de service
5. Complétez votre profil

### Étape 2 : Créer une application AdMob

1. Dans AdMob, cliquez sur **"Applications"**
2. Cliquez sur **"Ajouter une application"**
3. Sélectionnez **"Google Play"**
4. Recherchez **"CleanPulse"** (ou créez une nouvelle)
5. Cliquez sur **"Ajouter"**

### Étape 3 : Créer les unités publicitaires

#### Bannière

1. Cliquez sur **"Unités publicitaires"** → **"Nouvelle unité"**
2. Sélectionnez **"Bannière"**
3. Entrez le nom : `CleanPulse Banner`
4. Cliquez sur **"Créer une unité"**
5. Copiez l'**ID d'unité publicitaire** (ex: `ca-app-pub-xxxxxxxxxxxxxxxx/xxxxxxxxxx`)

#### Interstitiel

1. Cliquez sur **"Unités publicitaires"** → **"Nouvelle unité"**
2. Sélectionnez **"Interstitiel"**
3. Entrez le nom : `CleanPulse Interstitial`
4. Cliquez sur **"Créer une unité"**
5. Copiez l'**ID d'unité publicitaire**

#### Récompensé

1. Cliquez sur **"Unités publicitaires"** → **"Nouvelle unité"**
2. Sélectionnez **"Récompensé"**
3. Entrez le nom : `CleanPulse Rewarded`
4. Cliquez sur **"Créer une unité"**
5. Copiez l'**ID d'unité publicitaire**

### Étape 4 : Configurer les IDs dans l'application

Modifiez `app/src/main/java/com/cleanpulse/Constants.kt` :

```kotlin
// AdMob Configuration - PRODUCTION IDS (Replace test IDs)
const val ADMOB_APP_ID = "ca-app-pub-xxxxxxxxxxxxxxxx~yyyyyyyyyy"
const val ADMOB_BANNER_AD_ID = "ca-app-pub-xxxxxxxxxxxxxxxx/xxxxxxxxxx"
const val ADMOB_INTERSTITIAL_AD_ID = "ca-app-pub-xxxxxxxxxxxxxxxx/xxxxxxxxxx"
const val ADMOB_REWARDED_AD_ID = "ca-app-pub-xxxxxxxxxxxxxxxx/xxxxxxxxxx"
```

Remplacez les valeurs par vos IDs réels d'AdMob.

### Étape 5 : Mettre à jour AndroidManifest.xml

Modifiez `app/src/main/AndroidManifest.xml` :

```xml
<meta-data
    android:name="com.google.android.gms.ads.APPLICATION_ID"
    android:value="ca-app-pub-xxxxxxxxxxxxxxxx~yyyyyyyyyy" />
```

Remplacez par votre ID d'application AdMob.

---

## 🔑 Génération des clés de signature

### Créer une clé de signature pour la publication

```bash
# Créer un nouveau keystore
keytool -genkey -v -keystore cleanpulse-release-key.jks \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias cleanpulse
```

Répondez aux questions :
- **Prénom et nom** : CleanPulse Developer
- **Unité organisationnelle** : Development
- **Organisation** : CleanPulse
- **Ville/Localité** : Paris
- **Province/État** : Île-de-France
- **Code du pays** : FR
- **Mot de passe du keystore** : (créez un mot de passe fort)
- **Mot de passe de la clé** : (même mot de passe ou différent)

**Important** : Conservez ce fichier `cleanpulse-release-key.jks` dans un endroit sûr. Vous en aurez besoin pour toutes les futures mises à jour de l'application.

### Configurer la signature dans Gradle

Modifiez `app/build.gradle.kts` :

```kotlin
android {
    // ... autres configurations ...
    
    signingConfigs {
        create("release") {
            storeFile = file("../cleanpulse-release-key.jks")
            storePassword = "votre_mot_de_passe_keystore"
            keyAlias = "cleanpulse"
            keyPassword = "votre_mot_de_passe_cle"
        }
    }
    
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}
```

---

## 🏗️ Construction de l'APK

### Construire l'APK de débogage

```bash
# Via ligne de commande
./gradlew assembleDebug

# L'APK sera généré dans :
# app/build/outputs/apk/debug/app-debug.apk
```

### Construire l'APK de production

```bash
# Via ligne de commande
./gradlew assembleRelease

# L'APK sera généré dans :
# app/build/outputs/apk/release/app-release.apk
```

### Vérifier la signature de l'APK

```bash
# Vérifier que l'APK est correctement signé
jarsigner -verify -verbose app/build/outputs/apk/release/app-release.apk
```

---

## 🚀 Publication Play Store

### Étape 1 : Créer un compte Google Play Developer

1. Accédez à [Google Play Console](https://play.google.com/console)
2. Cliquez sur **"Créer un compte"**
3. Acceptez les conditions de service
4. Payez les frais uniques de 25 USD
5. Complétez votre profil de développeur

### Étape 2 : Créer une application

1. Dans Google Play Console, cliquez sur **"Créer une application"**
2. Entrez le nom : `CleanPulse`
3. Sélectionnez la catégorie : **Outils**
4. Sélectionnez le type de contenu : **Application**
5. Cliquez sur **"Créer une application"**

### Étape 3 : Remplir les informations de l'application

#### Informations de base

1. Allez à **Informations sur l'application**
2. Remplissez :
   - **Titre** : CleanPulse — Nettoyeur & Booster
   - **Description courte** : Nettoyeur et optimiseur Android
   - **Description complète** : (voir PLAY_STORE_CONTENT.md)
   - **Catégorie** : Outils
   - **Type de contenu** : Application

#### Données de contact

1. Allez à **Données de contact**
2. Remplissez :
   - **Email de contact** : support@cleanpulse.app
   - **Site web** : https://cleanpulse.app
   - **Politique de confidentialité** : https://cleanpulse.app/privacy

#### Classification du contenu

1. Allez à **Classification du contenu**
2. Complétez le questionnaire
3. Acceptez la classification

#### Données de l'application

1. Allez à **Données de l'application**
2. Sélectionnez :
   - **Cible d'âge** : 3+
   - **Contenu réservé aux enfants** : Non

### Étape 4 : Télécharger les assets

1. Allez à **Présentation de l'application**
2. Téléchargez :
   - **Icône de l'application** : `assets/icon_512x512.png`
   - **Feature Graphic** : `assets/feature_graphic_1024x500.png`
   - **Screenshots** : 2-5 images 1080×1920 PNG

### Étape 5 : Créer une version de test

1. Allez à **Versions de test** → **Versions internes**
2. Cliquez sur **"Créer une version"**
3. Téléchargez votre APK : `app/build/outputs/apk/release/app-release.apk`
4. Remplissez les **notes de version** :
   ```
   Bienvenue dans CleanPulse v1.0.0 !
   
   Nouvelles fonctionnalités :
   - Interface complète avec Jetpack Compose
   - Nettoyage intelligent du cache
   - Booster RAM et refroidissement
   - Analyse du stockage
   - Authentification Firebase
   - Support multilingue (FR/EN)
   - Mode sombre
   ```
5. Cliquez sur **"Sauvegarder"**

### Étape 6 : Tester avec des testeurs internes

1. Allez à **Versions internes**
2. Cliquez sur **"Gérer les testeurs"**
3. Ajoutez les adresses email des testeurs
4. Cliquez sur **"Envoyer l'invitation"**
5. Partagez le lien de test avec les testeurs
6. Collectez les retours et corrigez les bugs

### Étape 7 : Publier en production

1. Allez à **Versions de production**
2. Cliquez sur **"Créer une version"**
3. Téléchargez votre APK de release
4. Remplissez les notes de version
5. Vérifiez toutes les informations :
   - [ ] Titre et description corrects
   - [ ] Assets téléchargés
   - [ ] APK signé correctement
   - [ ] Permissions justifiées
   - [ ] Politique de confidentialité valide
   - [ ] Pas de contenu interdit
6. Cliquez sur **"Examiner"**
7. Cliquez sur **"Confirmer le déploiement"**
8. Sélectionnez le **déploiement progressif** :
   - 10% des utilisateurs (1-2 jours)
   - 50% des utilisateurs (1-2 jours)
   - 100% des utilisateurs

### Étape 8 : Surveiller la publication

1. Allez à **Versions de production**
2. Vérifiez le statut du déploiement
3. Surveillez les retours des utilisateurs
4. Répondez aux avis et commentaires
5. Corrigez les bugs signalés

---

## 🐛 Dépannage

### Problème : "google-services.json not found"

**Solution** : Assurez-vous que `google-services.json` est placé dans `app/` (pas dans `app/src/main/`).

### Problème : "SHA-1 not found in Firebase Console"

**Solution** : Vérifiez que vous avez entré le SHA-1 correct sans les deux-points (ex: `AABBCCDD...` au lieu de `AA:BB:CC:DD...`).

### Problème : "AdMob ads not showing"

**Solution** : 
1. Vérifiez que vous utilisez les IDs de test pendant le développement
2. Remplacez par les IDs réels avant la publication
3. Attendez quelques heures après la publication pour que les annonces s'affichent

### Problème : "Compilation fails with Kotlin error"

**Solution** : 
```bash
# Nettoyez et reconstruisez
./gradlew clean
./gradlew build
```

### Problème : "APK size too large"

**Solution** : Activez la minification et la compression des ressources dans `build.gradle.kts` :
```kotlin
buildTypes {
    release {
        isMinifyEnabled = true
        isShrinkResources = true
    }
}
```

### Problème : "App crashes on startup"

**Solution** :
1. Vérifiez les logs : `adb logcat`
2. Assurez-vous que Firebase est correctement configuré
3. Vérifiez les permissions dans AndroidManifest.xml
4. Testez sur un émulateur Android 24+

---

## 📊 Après la publication

### Monitorer les performances

1. Allez à **Tableau de bord** pour voir :
   - Nombre de téléchargements
   - Nombre d'utilisateurs actifs
   - Évaluations et avis
   - Taux de crash

2. Allez à **Rapports** pour voir :
   - Statistiques d'utilisation
   - Données démographiques
   - Appareils utilisés
   - Versions Android

### Répondre aux avis

1. Allez à **Avis**
2. Lisez les avis des utilisateurs
3. Répondez aux avis (surtout les négatifs)
4. Corrigez les bugs signalés

### Planifier les mises à jour

1. Augmentez `versionCode` et `versionName` dans `build.gradle.kts`
2. Apportez les modifications
3. Reconstruisez l'APK de release
4. Téléchargez la nouvelle version
5. Publiez progressivement

---

## 🔒 Sécurité et bonnes pratiques

### Avant la publication

- [ ] Remplacez les IDs AdMob de test par les vrais IDs
- [ ] Vérifiez les règles de sécurité Firestore
- [ ] Testez sur plusieurs appareils et versions Android
- [ ] Vérifiez les permissions demandées
- [ ] Testez l'authentification Google
- [ ] Vérifiez la politique de confidentialité

### Gestion des clés

- Stockez votre keystore dans un endroit sûr (pas dans Git)
- Utilisez des variables d'environnement pour les mots de passe
- Ne partagez jamais votre keystore
- Sauvegardez votre keystore régulièrement

### Mises à jour de sécurité

- Mettez à jour les dépendances régulièrement
- Corrigez les vulnérabilités de sécurité rapidement
- Testez les mises à jour avant la publication
- Publiez les correctifs de sécurité en priorité

---

**Dernière mise à jour** : Novembre 2025
