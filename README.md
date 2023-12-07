# RestaurantAppJetpackNew

Az alkalmazás elérhető a Google Play-en: https://play.google.com/store/apps/details?id=hu.zsof.restaurantappjetpacknew

Ehhez biztosítva van a szerver folyamatos működése.

Teszteléshez elérhető teszt felhasználók:

**Sima felhasználó:**
  - Email: user@test.hu
  - Jelszó: Alma1234

**Tulajdonos:**
  - Email: owner@test.hu
  - Jelszó: Alma1234

**Adminisztrátor:**
  - Email: admin@test.hu
  - Jelszó: Alma1234
    

Azonban lokális futtatásra is lehetőség van, ehhez szükséges egy saját adatbázis biztosítása. 
A Backend-hez tartozó README fájlban egy H2 adatbázis létrehozását, és a Backend-del való összekapcsolást mutatom be:
https://github.com/zsof/restaurantApp/tree/master

Androidon teendők:
1. lépés: A projekt klónozása
2. lépés: A *Constants.kt* fájlban a **BASE_URL** helyére a lokális Backend elérhetőséget kell írni
3. lépés: A *Manifest* fájlban az **android:usesCleartextTraffic="false"**-t át kell írni *"true"*-ra
4. lépés: Futtatás után az alkalmazás értelemszerűen használható. Regisztrációt követően az email-címre érkező verifikációs email-en a linkre kattintás kötelező, ezzel válik érvényessé a regisztrálás.

### About
This project is being prepared as thesis work of the BME master's degree in Computer Science. The application consists of an Android project and a Spring Boot backend written in Kotlin. The Android part is completed with XML description language and in Jetpack Compose.

This code is written in Jetpack Compose and here is the codebase with XML: https://github.com/zsof/AndroidRestaurantApplication

The Spring Boot backend can be found here: https://github.com/zsof/restaurantApp

### The Application
The App is a Restaurant/Bakery/Patessiere... registration app, where users can filter for the right conditions, such as whether lactose-free food is prepared, the place is family-friendly, dogs can be brought in, or whether there is free parking. They can also see the places on the map, which one is closest to them, or they can also save the given place in a list of favorites.

Other information about the places is also available in the detailed view of the place, such as opening hours, website/phone number, additional photos or reviews.

### Techstack
- The app is based on the MVVM architecture
- It communicates with the backend via a Rest api, using Retrofit for this
- Part of the data will be saved in a local database, using Room libary
- The app displays the images using Coil
- Dependency Injection - Dagger Hilt
- Coroutines
- Firebase
- Google Map Api
- The app will be published on Google Play
