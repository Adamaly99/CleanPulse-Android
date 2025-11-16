# 🐛 Guide de dépannage et FAQ - CleanPulse

## 🔧 Problèmes courants et solutions

### Problèmes de compilation

#### "google-services.json not found"

**Symptôme** : Erreur lors de la compilation

**Cause** : Le fichier `google-services.json` n'est pas au bon endroit

**Solution** :
1. Téléchargez `google-services.json` depuis Firebase Console
2. Placez-le dans le dossier `app/` (pas dans `app/src/main/`)
3. Nettoyez et reconstruisez : `./gradlew clean && ./gradlew build`

#### "Gradle sync failed"

**Symptôme** : Android Studio ne peut pas synchroniser le projet

**Cause** : Problème de dépendances ou de configuration Gradle

**Solution** :
```bash
# Nettoyez les caches Gradle
./gradlew clean

# Reconstruisez le projet
./gradlew build

# Ou dans Android Studio
# File → Invalidate Caches → Invalidate and Restart
```

#### "Kotlin compilation error"

**Symptôme** : Erreurs de compilation Kotlin

**Cause** : Version Kotlin incompatible ou syntaxe incorrecte

**Solution** :
1. Vérifiez la version Kotlin dans `build.gradle.kts` (doit être 1.9.20+)
2. Vérifiez la syntaxe Kotlin
3. Nettoyez et reconstruisez

#### "Cannot find symbol"

**Symptôme** : Erreur "cannot find symbol" pour une classe

**Cause** : Import manquant ou classe non trouvée

**Solution** :
1. Vérifiez que la classe existe
2. Ajoutez l'import nécessaire
3. Vérifiez le chemin du package

### Problèmes Firebase

#### "SHA-1 not found in Firebase Console"

**Symptôme** : Firebase refuse la connexion

**Cause** : SHA-1 incorrect ou mal formaté

**Solution** :
1. Obtenez le SHA-1 correct :
   ```bash
   keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
   ```
2. Copiez le SHA-1 **sans les deux-points** (ex: `AABBCCDD...` au lieu de `AA:BB:CC:DD...`)
3. Ajoutez-le à Firebase Console
4. Attendez quelques minutes pour que Firebase le reconnaisse

#### "FirebaseAuth not initialized"

**Symptôme** : Erreur Firebase Auth au démarrage

**Cause** : Firebase n'est pas correctement initialisé

**Solution** :
1. Vérifiez que `google-services.json` est présent
2. Vérifiez que le plugin Google Services est appliqué dans `build.gradle.kts`
3. Vérifiez la configuration Firebase dans `FirebaseManager.kt`

#### "Firestore permission denied"

**Symptôme** : Erreur d'accès Firestore

**Cause** : Règles de sécurité incorrectes

**Solution** :
1. Allez à Firebase Console → Firestore → Règles
2. Vérifiez les règles de sécurité
3. Assurez-vous que l'utilisateur est authentifié
4. Testez avec les règles de développement (moins restrictives)

#### "Cannot connect to Firestore"

**Symptôme** : Impossible de se connecter à Firestore

**Cause** : Problème de réseau ou de configuration

**Solution** :
1. Vérifiez la connexion Internet
2. Vérifiez que Firestore est activé dans Firebase Console
3. Vérifiez la région de Firestore
4. Vérifiez les règles de pare-feu

### Problèmes AdMob

#### "AdMob ads not showing"

**Symptôme** : Les publicités ne s'affichent pas

**Cause** : IDs AdMob incorrects ou compte non vérifié

**Solution** :
1. Vérifiez que vous utilisez les IDs de test pendant le développement
2. Vérifiez que les IDs sont correctement configurés dans `Constants.kt`
3. Attendez 24-48 heures après la publication pour que les annonces s'affichent
4. Vérifiez que votre compte AdMob est vérifié

#### "AdMob ads loading but not displaying"

**Symptôme** : Les publicités se chargent mais ne s'affichent pas

**Cause** : Problème d'affichage ou de configuration

**Solution** :
1. Vérifiez que l'espace pour la publicité est suffisant
2. Vérifiez que la publicité n'est pas cachée
3. Vérifiez les logs : `adb logcat | grep AdMob`
4. Testez avec une publicité de test

#### "Invalid Ad Unit ID"

**Symptôme** : Erreur "Invalid Ad Unit ID"

**Cause** : ID d'unité publicitaire incorrect

**Solution** :
1. Vérifiez l'ID dans AdMob Console
2. Vérifiez que l'ID est copié correctement (sans espaces)
3. Vérifiez que l'ID correspond au type de publicité (bannière, interstitiel, récompensé)

### Problèmes de permissions

#### "Permission denied: READ_EXTERNAL_STORAGE"

**Symptôme** : L'app ne peut pas accéder au stockage

**Cause** : Permission non accordée ou demande incorrecte

**Solution** :
1. Vérifiez que la permission est déclarée dans `AndroidManifest.xml`
2. Demandez la permission au runtime (Android 6+)
3. Vérifiez que l'utilisateur a accordé la permission
4. Testez sur Android 11+ avec `MANAGE_EXTERNAL_STORAGE`

#### "Permission denied: PACKAGE_USAGE_STATS"

**Symptôme** : L'app ne peut pas accéder aux statistiques d'utilisation

**Cause** : Permission système protégée

**Solution** :
1. Vérifiez que la permission est déclarée dans `AndroidManifest.xml`
2. Notez que cette permission ne peut pas être demandée au runtime
3. L'utilisateur doit l'accorder manuellement dans les paramètres
4. Vérifiez les logs pour les erreurs d'accès

### Problèmes de performance

#### "App crashes or freezes"

**Symptôme** : L'app plante ou se fige

**Cause** : Opération longue sur le thread principal ou fuite mémoire

**Solution** :
1. Vérifiez les logs : `adb logcat`
2. Utilisez Coroutines pour les opérations asynchrones
3. Vérifiez les fuites mémoire avec Android Profiler
4. Testez sur un appareil avec moins de RAM

#### "High memory usage"

**Symptôme** : L'app consomme beaucoup de mémoire

**Cause** : Fuites mémoire ou chargement excessif d'images

**Solution** :
1. Utilisez Android Profiler pour identifier les fuites
2. Libérez les ressources non utilisées
3. Compressez les images
4. Utilisez le lazy loading

#### "Slow startup"

**Symptôme** : L'app met du temps à démarrer

**Cause** : Initialisation lente ou opérations bloquantes

**Solution** :
1. Déplacez les opérations longues vers les Coroutines
2. Utilisez le lazy loading pour Firebase
3. Optimisez les ressources
4. Profiliez l'app avec Android Profiler

### Problèmes de réseau

#### "Cannot connect to Firebase"

**Symptôme** : Impossible de se connecter à Firebase

**Cause** : Problème de réseau ou de configuration

**Solution** :
1. Vérifiez la connexion Internet
2. Vérifiez les pare-feu
3. Vérifiez la configuration Firebase
4. Testez avec un VPN si nécessaire

#### "Timeout connecting to Firestore"

**Symptôme** : Délai d'attente lors de la connexion à Firestore

**Cause** : Réseau lent ou serveur surchargé

**Solution** :
1. Vérifiez la vitesse Internet
2. Réessayez après quelques secondes
3. Implémentez une logique de retry
4. Vérifiez l'état du serveur Firebase

### Problèmes de signature

#### "APK not signed"

**Symptôme** : Erreur lors de la signature de l'APK

**Cause** : Clé de signature manquante ou incorrecte

**Solution** :
1. Vérifiez que le keystore existe
2. Vérifiez le chemin du keystore dans `build.gradle.kts`
3. Vérifiez le mot de passe du keystore
4. Recréez le keystore si nécessaire

#### "Invalid keystore format"

**Symptôme** : Erreur "Invalid keystore format"

**Cause** : Fichier keystore corrompu ou mauvais format

**Solution** :
1. Vérifiez que le fichier est un keystore valide
2. Recréez le keystore si nécessaire
3. Vérifiez le format (JKS par défaut)

### Problèmes de publication

#### "App rejected by Play Store"

**Symptôme** : L'app est rejetée lors de la publication

**Cause** : Violation des directives Play Store

**Solution** :
1. Lisez le message de rejet
2. Corrigez le problème identifié
3. Testez à nouveau
4. Soumettez une nouvelle version

#### "Cannot upload APK"

**Symptôme** : Erreur lors du téléchargement de l'APK

**Cause** : APK invalide ou problème de compte

**Solution** :
1. Vérifiez que l'APK est valide
2. Vérifiez que le versionCode est unique
3. Vérifiez que le compte est vérifié
4. Attendez quelques heures et réessayez

---

## ❓ Questions fréquemment posées (FAQ)

### Installation et configuration

**Q: Quelle version d'Android est requise ?**  
R: Android 7.0 (API 24) minimum. L'app est optimisée pour Android 8.0+ (API 26+).

**Q: Comment installer l'app sur un appareil ?**  
R: Connectez l'appareil via USB, activez le débogage USB, puis exécutez `./gradlew installDebug`.

**Q: Puis-je tester sans Firebase ?**  
R: Oui, commentez les appels Firebase dans `MainActivity.kt`. Les écrans UI fonctionneront sans authentification.

**Q: Comment tester les publicités AdMob ?**  
R: Utilisez les IDs de test fournis dans `Constants.kt`. Ils affichent des publicités de test sans générer de revenus.

### Développement

**Q: Comment ajouter une nouvelle fonctionnalité ?**  
R: Créez un nouvel écran dans `ui/screens/`, ajoutez une route dans `Navigation.kt`, et intégrez la logique métier.

**Q: Comment modifier les couleurs ?**  
R: Modifiez `colors.xml` ou `Theme.kt` pour les couleurs du thème Material Design 3.

**Q: Comment ajouter une nouvelle langue ?**  
R: Créez un fichier `strings.xml` dans `res/values-[code_langue]/` (ex: `values-en/`).

**Q: Comment déboguer l'app ?**  
R: Utilisez `adb logcat` pour voir les logs, ou utilisez Android Studio Debugger.

### Firebase

**Q: Comment réinitialiser Firebase ?**  
R: Supprimez le projet dans Firebase Console et créez-en un nouveau.

**Q: Puis-je utiliser plusieurs projets Firebase ?**  
R: Oui, créez plusieurs `google-services.json` et basculez entre eux selon les besoins.

**Q: Comment sauvegarder les données Firebase ?**  
R: Utilisez l'export de Firestore dans Firebase Console ou implémentez une logique de sauvegarde.

**Q: Combien de données puis-je stocker dans Firestore ?**  
R: Plan gratuit : 1 GB de stockage. Plan payant : illimité (facturation à l'utilisation).

### AdMob

**Q: Combien de revenus puis-je générer ?**  
R: Cela dépend du nombre d'utilisateurs, du taux de clics (CTR) et du coût par clic (CPC).

**Q: Puis-je tester les publicités sur un émulateur ?**  
R: Oui, utilisez les IDs de test. Les publicités réelles ne s'affichent pas sur les émulateurs.

**Q: Comment augmenter les revenus AdMob ?**  
R: Augmentez le nombre d'utilisateurs, optimisez le placement des publicités, et utilisez tous les formats.

**Q: Quand les revenus AdMob s'affichent-ils ?**  
R: Les revenus s'affichent en temps réel dans AdMob Console (avec un délai de 24h).

### Publication

**Q: Combien coûte la publication sur Play Store ?**  
R: Frais uniques de 25 USD pour créer un compte développeur.

**Q: Combien de temps prend la révision de l'app ?**  
R: Généralement 2-4 heures, mais peut prendre jusqu'à 24 heures.

**Q: Puis-je mettre à jour l'app après la publication ?**  
R: Oui, augmentez le versionCode, reconstruisez l'APK, et téléchargez la nouvelle version.

**Q: Comment gérer les avis négatifs ?**  
R: Répondez aux avis, corrigez les bugs signalés, et publiez des mises à jour.

**Q: Comment augmenter le nombre de téléchargements ?**  
R: Optimisez la description et les screenshots, encouragez les avis positifs, et faites du marketing.

### Sécurité et confidentialité

**Q: Comment protéger les données utilisateur ?**  
R: Utilisez Firebase Security Rules, chiffrez les données sensibles, et respectez la RGPD.

**Q: Dois-je avoir une politique de confidentialité ?**  
R: Oui, c'est obligatoire pour la publication sur Play Store.

**Q: Comment gérer les permissions ?**  
R: Demandez les permissions au runtime, expliquez pourquoi vous les avez besoin, et respectez la vie privée.

**Q: Comment sécuriser ma clé de signature ?**  
R: Stockez-la dans un endroit sûr, ne la partagez pas, et sauvegardez-la régulièrement.

---

## 📞 Obtenir de l'aide

### Ressources officielles

- [Android Developers](https://developer.android.com/)
- [Firebase Documentation](https://firebase.google.com/docs)
- [Google Mobile Ads](https://developers.google.com/admob)
- [Google Play Console Help](https://support.google.com/googleplay/android-developer)

### Communautés

- [Stack Overflow](https://stackoverflow.com/questions/tagged/android)
- [Android Reddit](https://www.reddit.com/r/androiddev/)
- [Firebase Community](https://firebase.community/)

### Support

- Email : support@cleanpulse.app
- Site web : https://cleanpulse.app
- GitHub Issues : (si applicable)

---

**Dernière mise à jour** : Novembre 2025

**Vous n'avez pas trouvé votre réponse ?** Consultez la documentation officielle ou contactez le support.
