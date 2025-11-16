# ✅ Checklist de Publication - CleanPulse

Utilisez cette checklist pour vous assurer que tout est prêt avant de publier sur le Play Store.

## 🔐 Configuration et sécurité

### Firebase
- [ ] Projet Firebase créé et configuré
- [ ] `google-services.json` téléchargé et placé dans `app/`
- [ ] Authentification Firebase activée (Google + Email/Mot de passe)
- [ ] Firestore Database créée et configurée
- [ ] Règles de sécurité Firestore mises à jour
- [ ] Cloud Storage configuré (optionnel)
- [ ] SHA-1 ajouté à Firebase Console

### AdMob
- [ ] Compte AdMob créé et vérifié
- [ ] Application AdMob créée
- [ ] Unités publicitaires créées (Bannière, Interstitiel, Récompensé)
- [ ] IDs AdMob remplacés dans `Constants.kt`
- [ ] AndroidManifest.xml mis à jour avec l'ID d'application AdMob

### Clés de signature
- [ ] Clé de signature créée (`cleanpulse-release-key.jks`)
- [ ] Clé stockée dans un endroit sûr
- [ ] Mot de passe de la clé noté (pas dans le code)
- [ ] SHA-1 de la clé de release obtenu

## 🏗️ Construction et tests

### Compilation
- [ ] APK de débogage compilé et testé
- [ ] APK de release compilé avec succès
- [ ] APK signé correctement
- [ ] Signature vérifiée avec `jarsigner`
- [ ] Aucune erreur de compilation
- [ ] Aucun avertissement critique

### Tests
- [ ] Application testée sur Android 8.0 (API 26)
- [ ] Application testée sur Android 10.0 (API 29)
- [ ] Application testée sur Android 13.0 (API 33)
- [ ] Application testée sur Android 14.0 (API 34)
- [ ] Écran d'accueil fonctionne correctement
- [ ] Écran d'analyse fonctionne correctement
- [ ] Écran de nettoyage fonctionne correctement
- [ ] Écran de résultats fonctionne correctement
- [ ] Écran de paramètres fonctionne correctement
- [ ] Écran d'historique fonctionne correctement
- [ ] Authentification Firebase fonctionne
- [ ] Publicités AdMob s'affichent
- [ ] Pas de crash ou d'erreur
- [ ] Pas de fuite mémoire
- [ ] Performance acceptable

### Permissions
- [ ] Toutes les permissions sont justifiées
- [ ] Permissions demandées au runtime si nécessaire
- [ ] Pas de permissions inutiles

## 📱 Assets et contenu

### Graphiques
- [ ] Icône 512×512 PNG créée et optimisée
- [ ] Feature Graphic 1024×500 PNG créée
- [ ] Screenshots 1080×1920 PNG créés (2-5 images)
- [ ] Tous les assets sont de haute qualité
- [ ] Tous les assets respectent les directives Google Play

### Textes
- [ ] Titre de l'application : "CleanPulse — Nettoyeur & Booster"
- [ ] Description courte complétée (80 caractères max)
- [ ] Description complète complétée (4000 caractères max)
- [ ] Mots-clés définis (5 mots-clés)
- [ ] Notes de version complétées
- [ ] Pas de fautes d'orthographe
- [ ] Textes en français ET anglais

### Informations légales
- [ ] Politique de confidentialité rédigée et accessible
- [ ] Conditions d'utilisation rédigées (optionnel)
- [ ] Email de support défini
- [ ] Site web défini (optionnel)
- [ ] Données de contact complètes

## 🎯 Configuration Play Store

### Compte développeur
- [ ] Compte Google Play Developer créé
- [ ] Frais de 25 USD payés
- [ ] Profil développeur complété
- [ ] Données bancaires vérifiées

### Application
- [ ] Application créée dans Google Play Console
- [ ] Catégorie définie : "Outils"
- [ ] Type de contenu défini : "Application"
- [ ] Cible d'âge définie : "3+"
- [ ] Classification du contenu complétée
- [ ] Données de contact remplies
- [ ] Politique de confidentialité liée

### Version
- [ ] Version créée dans "Versions internes"
- [ ] APK de release téléchargé
- [ ] Numéro de version correct
- [ ] Notes de version complétées
- [ ] Pas d'erreurs de validation

## 🧪 Tests de version interne

### Testeurs
- [ ] Testeurs internes ajoutés
- [ ] Lien de test envoyé aux testeurs
- [ ] Retours collectés des testeurs
- [ ] Bugs signalés corrigés
- [ ] Performance validée par les testeurs

### Validation
- [ ] Pas d'erreurs de validation Play Store
- [ ] Pas de contenu interdit détecté
- [ ] Pas de problèmes de sécurité signalés
- [ ] Pas de problèmes de confidentialité signalés

## 🚀 Avant la publication en production

### Vérifications finales
- [ ] Tous les tests passés
- [ ] Tous les retours des testeurs adressés
- [ ] Version finale prête
- [ ] APK final signé et vérifié
- [ ] Aucun changement de dernière minute

### Documentation
- [ ] README.md à jour
- [ ] SETUP_GUIDE.md à jour
- [ ] PLAY_STORE_CONTENT.md à jour
- [ ] Tous les guides sont précis

### Déploiement
- [ ] Stratégie de déploiement décidée (progressif recommandé)
- [ ] Pourcentages de déploiement définis (10% → 50% → 100%)
- [ ] Calendrier de déploiement planifié
- [ ] Équipe notifiée du déploiement

## 📊 Après la publication

### Monitoring
- [ ] Tableau de bord Google Play Console surveillé
- [ ] Nombre de téléchargements suivi
- [ ] Taux de crash surveillé
- [ ] Avis des utilisateurs lus
- [ ] Évaluations suivies

### Support
- [ ] Email de support réactif
- [ ] Avis des utilisateurs répondus
- [ ] Bugs signalés documentés
- [ ] Plan de correction établi

### Mises à jour
- [ ] Calendrier de mises à jour planifié
- [ ] Processus de mise à jour établi
- [ ] Versioning clair défini
- [ ] Changelog maintenu

## 🔄 Mise à jour ultérieure

Pour chaque mise à jour :

- [ ] Augmenter `versionCode` dans `build.gradle.kts`
- [ ] Augmenter `versionName` dans `build.gradle.kts`
- [ ] Mettre à jour le code
- [ ] Tester sur plusieurs appareils
- [ ] Compiler l'APK de release
- [ ] Signer l'APK
- [ ] Télécharger dans Google Play Console
- [ ] Remplir les notes de version
- [ ] Publier progressivement

## 📝 Notes supplémentaires

### Points importants
- Ne partagez jamais votre clé de signature
- Conservez votre clé de signature en sécurité
- Sauvegardez régulièrement votre clé de signature
- Mettez à jour les dépendances régulièrement
- Corrigez les vulnérabilités de sécurité rapidement
- Testez chaque mise à jour avant la publication

### Ressources
- [Google Play Console Help](https://support.google.com/googleplay/android-developer)
- [Android App Quality Guidelines](https://play.google.com/about/quality-guidelines/)
- [Firebase Documentation](https://firebase.google.com/docs)
- [AdMob Best Practices](https://support.google.com/admob/answer/6001347)

---

## ✨ Statut de publication

| Étape | Statut | Date |
|-------|--------|------|
| Configuration Firebase | ⏳ Pending | - |
| Configuration AdMob | ⏳ Pending | - |
| Tests de l'application | ⏳ Pending | - |
| Création des assets | ✅ Complété | - |
| Rédaction du contenu | ✅ Complété | - |
| Tests internes | ⏳ Pending | - |
| Publication en production | ⏳ Pending | - |

---

**Dernière mise à jour** : Novembre 2025

**Prêt à publier ?** Assurez-vous que toutes les cases sont cochées ! ✅
