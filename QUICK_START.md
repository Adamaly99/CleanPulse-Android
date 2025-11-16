# 🚀 Démarrage Rapide - CleanPulse

Bienvenue dans **CleanPulse — Nettoyeur & Booster** ! Ce guide vous permettra de démarrer rapidement.

## 📦 Contenu du projet

```
CleanPulse_Android/
├── app/                          # Module application principale
│   ├── src/main/
│   │   ├── java/com/cleanpulse/  # Code source Kotlin
│   │   ├── res/                  # Ressources (couleurs, strings, thèmes)
│   │   └── AndroidManifest.xml   # Configuration de l'application
│   ├── build.gradle.kts          # Configuration Gradle du module
│   └── proguard-rules.pro        # Règles de minification
├── build.gradle.kts              # Configuration Gradle du projet
├── settings.gradle.kts           # Configuration des modules
├── README.md                      # Documentation complète
├── SETUP_GUIDE.md               # Guide détaillé de configuration
├── PLAY_STORE_CONTENT.md        # Contenu pour le Play Store
├── QUICK_START.md               # Ce fichier
└── assets/                       # Assets graphiques
    ├── icon_512x512.png         # Icône de l'application
    └── feature_graphic_1024x500.png  # Bannière Play Store
```

## ⚡ Étapes rapides (5 minutes)

### 1. Ouvrir le projet dans Android Studio

```bash
# Cloner ou extraire le projet
cd CleanPulse_Android

# Ouvrir dans Android Studio
# File → Open → Sélectionner le dossier CleanPulse_Android
```

### 2. Configurer Firebase (obligatoire)

1. Accédez à [Firebase Console](https://console.firebase.google.com/)
2. Créez un projet nommé `CleanPulse`
3. Ajoutez une application Android avec le package `com.cleanpulse`
4. Obtenez votre SHA-1 :
   ```bash
   keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
   ```
5. Téléchargez `google-services.json` et placez-le dans `app/`

### 3. Configurer AdMob (optionnel pour le test)

1. Accédez à [Google AdMob](https://admob.google.com/)
2. Créez une application et des unités publicitaires
3. Modifiez `app/src/main/java/com/cleanpulse/Constants.kt` avec vos IDs

**Note** : Les IDs de test sont déjà configurés. Vous pouvez tester l'app avec les IDs de test.

### 4. Construire et lancer l'application

```bash
# Via Android Studio
# Run → Run 'app'

# Ou via ligne de commande
./gradlew assembleDebug
adb install app/build/outputs/apk/debug/app-debug.apk
```

## 📱 Tester l'application

### Fonctionnalités principales

- **Écran d'accueil** : Indicateur de propreté circulaire, bouton "Nettoyer maintenant"
- **Analyse** : Visualisation de l'utilisation du stockage
- **Nettoyage** : Animation de progression du nettoyage
- **Résultats** : Affichage des statistiques et recommandations
- **Paramètres** : Configuration de la langue, mode sombre, nettoyage automatique
- **Historique** : Liste des nettoyages précédents

### Tester sans Firebase

Si vous n'avez pas configuré Firebase :
1. Commentez les appels Firebase dans `MainActivity.kt`
2. Testez les écrans UI sans authentification
3. Les données ne seront pas sauvegardées

## 🔧 Configuration avancée

Pour une configuration complète, consultez :
- **README.md** : Documentation complète du projet
- **SETUP_GUIDE.md** : Guide détaillé de configuration Firebase, AdMob et publication

## 📝 Fichiers importants à modifier

### Pour personnaliser l'app

| Fichier | Modification |
|---------|--------------|
| `Constants.kt` | Couleurs, IDs AdMob, URLs |
| `strings.xml` | Textes en français |
| `colors.xml` | Palette de couleurs |
| `Theme.kt` | Thème Material Design 3 |

### Pour l'authentification

| Fichier | Modification |
|---------|--------------|
| `FirebaseManager.kt` | Logique Firebase |
| `PreferencesManager.kt` | Stockage local des données |

### Pour les fonctionnalités système

| Fichier | Modification |
|---------|--------------|
| `SystemManager.kt` | Nettoyage cache, RAM, analyse |
| `AdManager.kt` | Gestion des publicités |

## 🎯 Prochaines étapes

### Avant la publication

1. **Remplacer les IDs AdMob** de test par vos IDs réels
2. **Tester sur plusieurs appareils** (Android 8+)
3. **Générer une clé de signature** pour la publication
4. **Créer un compte Google Play Developer** (25 USD)
5. **Préparer les assets** (icône, feature graphic, screenshots)

### Pour la publication

1. Construire l'APK de release
2. Signer l'APK avec votre clé
3. Télécharger sur Google Play Console
4. Remplir les informations de l'app
5. Publier progressivement (10% → 50% → 100%)

## 📚 Ressources

- **[Android Developers](https://developer.android.com/)** - Documentation Android officielle
- **[Firebase Docs](https://firebase.google.com/docs)** - Documentation Firebase
- **[Google Mobile Ads](https://developers.google.com/admob)** - Documentation AdMob
- **[Material Design 3](https://m3.material.io/)** - Design system
- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** - Framework UI

## ❓ Questions fréquentes

### Q: Comment tester sans Firebase ?
**R** : Commentez les appels Firebase dans `MainActivity.kt`. Les écrans UI fonctionneront sans authentification.

### Q: Les publicités AdMob s'affichent-elles ?
**R** : Oui, avec les IDs de test fournis. Remplacez-les par vos IDs réels avant la publication.

### Q: Quelle est la taille minimale d'Android ?
**R** : Android 7.0 (API 24). Vérifiez `minSdk` dans `build.gradle.kts`.

### Q: Comment augmenter la taille de l'APK ?
**R** : Activez la minification et la compression des ressources dans `build.gradle.kts`.

### Q: Comment mettre à jour l'app après la publication ?
**R** : Augmentez `versionCode` et `versionName`, reconstruisez l'APK, et téléchargez la nouvelle version sur Play Store.

## 🐛 Dépannage rapide

| Problème | Solution |
|----------|----------|
| `google-services.json not found` | Placez-le dans `app/` |
| `SHA-1 not found` | Vérifiez le format (sans deux-points) |
| `Compilation fails` | Exécutez `./gradlew clean && ./gradlew build` |
| `App crashes` | Vérifiez les logs avec `adb logcat` |
| `Ads not showing` | Utilisez les IDs de test pendant le développement |

## 📧 Support

Pour toute question :
- Email : support@cleanpulse.app
- Site web : https://cleanpulse.app

---

**Prêt à commencer ?** Ouvrez le projet dans Android Studio et lancez l'application ! 🎉

**Dernière mise à jour** : Novembre 2025
