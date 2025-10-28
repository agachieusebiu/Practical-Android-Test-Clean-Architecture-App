# 📱 Practical Android Test — Clean Architecture App

This repository contains a fully functional Android application built as part of a practical technical test.  
It demonstrates **modern Android development practices** and **clean architecture principles**, implementing all functional and non-functional requirements from the original specification.

---

## 🚀 Features
- **Three-tab interface** displaying data from REST APIs:
  - `getProductsLevel1.php`
  - `getProductsLevel2.php`
  - `getGrade.php`
- **Parallel API calls** using Kotlin Coroutines for optimized performance
- **Offline mode & caching** (5-minute cache expiration)
- **Pull-to-refresh** support across all tabs
- **Details screen navigation** for Level 1 and Level 2 products
- **Progress indicator** while fetching data
- **Error handling & fallback** for offline or failed requests

---

## ⚙️ Tech Stack
- **Language:** Kotlin  
- **Architecture:** MVVM + Clean Architecture  
- **Networking:** Retrofit  
- **Asynchronous Operations:** Kotlin Coroutines  
- **Dependency Injection:** Hilt (Dagger)  
- **Persistence:** Room / SharedPreferences  
- **Testing:** JUnit, MockK  
- **Code Quality:** Detekt, Lint  
- **Min SDK:** 30+ (Android 11)  
- **Screen Support:** xhdpi and above

---

## 🧠 Key Highlights
- Clean, SOLID-compliant Kotlin code
- Proper separation of concerns and testable architecture
- Reactive and thread-safe data flow
- Consistent UX behavior in online/offline scenarios
- Scalable and maintainable codebase following Android 2025 standards

---

## 🧰 How to Run
1. Clone this repository:
   ```bash
   git clone https://github.com/<agachieusebiu>/<Practical-Android-Test-Clean-Architecture-App>.git
   ```
2. Open the project in **Android Studio (Arctic Fox or newer)**
3. Sync Gradle and run the app on a device/emulator with **Android 11+**

---

## 🧑‍💻 Author
**Eusebiu-Gabriel Agachi**  
📍 Iași, Romania  
📧 agachi.eusebiu@gmail.com  
🔗 (#https://www.linkedin.com/in/eusebiu-agachi-1b02a7231/)

---

⭐ *If you find this project useful, consider giving it a star!*
