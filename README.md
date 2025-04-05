# Monify - Automatic Expense Tracker

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.0-blue.svg?style=flat-square&logo=kotlin)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/Android-14-green.svg?style=flat-square&logo=android)](https://www.android.com/)
[![Compose](https://img.shields.io/badge/Compose-2.0.0-purple.svg?style=flat-square&logo=android)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)](LICENSE)

** Preview of this Application: PLEASE WAIT...
![](https://github.com/EstakiDev/Monify/blob/develop/app-preview.gif)

**Monify** is an Android application designed to help you automatically track your income and expenses by analyzing your bank SMS messages. It provides a simple and intuitive way to manage your finances without the hassle of manual entry.

مرسی! توضیحاتت خیلی دقیق بود و کمک کرد تصویر دقیقی از ساختار پروژه بگیرم. الان README رو طوری می‌نویسم که ساختار ماژول‌ها کاملاً مشخص باشه و حس حرفه‌ای‌بودن پروژه رو نشون بده.


---

نسخه نهایی README (به‌روز شده با ساختار واقعی ماژول‌ها):

# Monify – Personal Finance Tracker  
[![Built with Kotlin](https://img.shields.io/badge/Built%20with-Kotlin-orange?logo=kotlin)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-purple?logo=android)](https://developer.android.com/jetpack/compose)
[![Architecture: Clean + MVI](https://img.shields.io/badge/Architecture-Clean%20%2B%20MVI-blue)]()
[![Modules: Multi-Module](https://img.shields.io/badge/Project%20Structure-Multi--Module-critical)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

> **Monify** is a modern personal finance tracker app built with Kotlin and Jetpack Compose.  
> It provides users with an intuitive interface to manage their transactions while embracing clean architecture and modular design.

---

## ✨ Features

- Add, edit, and delete income/expenses
- Categorize transactions with custom icons
- View detailed financial statistics
- Local notifications for financial reminders
- Dark & Light theme
- Clean architecture with scalable codebase

---

## ⚙️ Tech Stack & Architecture

### **Core Technologies**
- **Kotlin** – Main language
- **Jetpack Compose** – UI toolkit
- **Room** – Local database
- **Coroutines + Flow** – Asynchronous & reactive programming
- **Hilt** – Dependency Injection
- **ViewModel** – Lifecycle-aware logic
- **WorkManager** – Background tasks
- **BroadcastReceiver** – System event listener
- **Notification Manager** – System notifications

### **Architecture**
- **Clean Architecture** – Separation of data, domain, and presentation
- **MVI Pattern** – Predictable unidirectional UI flow
- **Multi-Module** – For modular, testable, and scalable development

---

## 🧱 Project Modules Overview

```bash
Monify/
├── app/              # Presentation layer, DI setup, UI Screens (Jetpack Compose)
├── data/             # Data sources, Room DAOs, repository implementations
├── domain/           # Business logic, UseCases, models, repository contracts
├── ui_utils/         # Shared UI components and utilities
└── pure_kt/          # General-purpose Kotlin utilities, extensions, and tools

Module Responsibilities:

app/

Compose Screens

ViewModels

Navigation

Hilt DI Setup


data/

Room entities & DAOs

Repository implementations

Data mappers


domain/

Use Cases

Data Models

Repository Interfaces


ui_utils/

Reusable Compose components (buttons, text fields, etc.)

Theme helpers


pure_kt/

Date/time formatters

Validation logic

Extension functions




---

📱 Screenshots


---

🚀 Getting Started

1. Clone the project:



git clone https://github.com/EstakiDev/Monify.git
cd Monify

2. Open in Android Studio (Arctic Fox or newer)


3. Sync Gradle and run on emulator or device.




---

🤝 Contributing

Feel free to open issues, suggest features, or submit pull requests.
This project follows clean coding principles and aims to be beginner-friendly for new contributors.


---

📄 License

Monify is licensed under the MIT License.


---

⭐️ Support

If you find this project helpful, please star it and share it with your friends and community!

---

اگه بخوای، همین متن رو به فارسی هم برات آماده می‌کنم. یا حتی دو زبانه (EN + FA) که توی گیت‌هاب جلوه‌ی قوی‌تری داشته باشه. دوست داری فایل `README.md` نهایی‌ش رو هم آماده کنم برات تا بذاری توی ریپو؟

