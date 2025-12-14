# WhatsApp Clone - Jetpack Compose

Bienvenue dans le projet **CatFlow**. Ce projet est une démonstration d'une application de messagerie instantanée moderne construite entièrement avec **Jetpack Compose** et **Kotlin**, utilisant **Firebase** comme backend.

## 📱 Fonctionnalités

*   **Authentification** : Connexion via Google et vérification par téléphone (Firebase Auth).
*   **Messagerie en temps réel** : Envoi et réception de messages instantanés avec Firebase Firestore.
*   **Interface Utilisateur Moderne** : UI déclarative construite avec Jetpack Compose.
*   **Navigation** : Utilisation de Navigation Component pour la gestion des écrans.
*   **Gestion d'état** : Architecture MVVM avec ViewModel et State/LiveData.
*   **Médias** : Chargement d'images de profil et partages de photos (Firebase Storage + Glide).
*   **Statuts et Appels** : (En cours de développement) Interface pour les onglets Statut et Appels.

## 🛠 Stack Technique

*   **Langage** : [Kotlin](https://kotlinlang.org/)
*   **UI Toolkit** : [Jetpack Compose](https://developer.android.com/jetpack/compose)
*   **Architecture** : MVVM (Model-View-ViewModel)
*   **Backend** :
    *   [Firebase Authentication](https://firebase.google.com/docs/auth) (Google Sign-In)
    *   [Firebase Firestore](https://firebase.google.com/docs/firestore) (Base de données NoSQL)
    *   [Firebase Storage](https://firebase.google.com/docs/storage) (Stockage de fichiers)
*   **Asynchrone** : [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & Flow
*   **Navigation** : [Jetpack Navigation](https://developer.android.com/guide/navigation)
*   **Chargement d'images** : [Glide](https://github.com/bumptech/glide)

## 🚀 Installation et Configuration

Pour exécuter ce projet localement, suivez ces étapes :

### Prérequis
*   Android Studio (Dernière version stable recommandée)
*   Compte Firebase

### Étapes

1.  **Cloner le dépôt**
    ```bash
    git clone https://github.com/votre-nom/whatsapp-clone.git
    cd whatsapp-clone
    ```

2.  **Configuration Firebase**
    *   Créez un nouveau projet sur la [Console Firebase](https://console.firebase.google.com/).
    *   Activez **Authentication** (Google Sign-In).
    *   Activez **Cloud Firestore** et **Storage**.
    *   Téléchargez le fichier `google-services.json` depuis les paramètres de votre projet Firebase.
    *   Placez le fichier `google-services.json` dans le dossier `app/` du projet.

3.  **Ouvrir dans Android Studio**
    *   Ouvrez le projet et laissez Gradle se synchroniser.

4.  **Exécuter**
    *   Connectez un appareil Android ou lancez un émulateur.
    *   Appuyez sur le bouton **Run**.

## 📂 Structure du Projet

```text
com.mindorks.sample.whatsapp
├── data             # Modèles de données et repositories
├── screen           # Écrans de l'interface utilisateur (Auth, Chat, Main, etc.)
│   ├── auth         # Écrans d'authentification et configuration de profil
│   ├── chat         # Liste des discussions et écran de chat
│   ├── main         # Écran principal (Tabs, AppBar)
│   ├── settings     # Paramètres de l'application
│   └── splash       # Écran de démarrage
├── utils            # Classes utilitaires et extensions
└── MainActivity.kt  # Point d'entrée de l'application
```

## 🤝 Contribuer

Les contributions sont les bienvenues ! Si vous souhaitez améliorer ce projet :
1.  Forkez le projet.
2.  Créez votre branche de fonctionnalité (`git checkout -b feature/AmazingFeature`).
3.  Commitez vos changements (`git commit -m 'Add some AmazingFeature'`).
4.  Pushez vers la branche (`git push origin feature/AmazingFeature`).
5.  Ouvrez une Pull Request.

## 📄 Licence

Ce projet est sous licence MIT - voir le fichier [LICENSE](LICENSE) pour plus de détails.

---
*Ce projet est à des fins éducatives pour l'apprentissage de Jetpack Compose.*
