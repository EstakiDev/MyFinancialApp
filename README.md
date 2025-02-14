# My Financial App - Automatic Expense Tracker

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-blue.svg?style=flat-square&logo=kotlin)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/Android-14-green.svg?style=flat-square&logo=android)](https://www.android.com/)
[![Compose](https://img.shields.io/badge/Compose-1.7.6-purple.svg?style=flat-square&logo=android)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)](LICENSE)

**My Financial App** is an Android application designed to help you automatically track your income and expenses by analyzing your bank SMS messages. It provides a simple and intuitive way to manage your finances without the hassle of manual entry.

## Features

*   **Automatic SMS Parsing:** The app automatically reads and parses your bank SMS messages to extract transaction details.
*   **Expense and Income Tracking:** It categorizes transactions as either income or expenses, giving you a clear overview of your financial flow.
*   **Category Management:** You can manage and customize transaction categories to suit your needs.
*   **User-Friendly Interface:** Built with Jetpack Compose, the app offers a modern and responsive user interface.
*   **Permission Handling:** The app gracefully handles SMS read permission requests, ensuring user privacy.
* **Persian/Arabic Support:** The app supports Persian and Arabic languages, including right-to-left (RTL) layout.
* **Shimmer Effect:** The app uses shimmer effect in splash screen to improve user experience.
* **Loading Indicator:** The app uses a beautiful loading indicator in splash screen.

## Technologies Used

*   **Kotlin:** The primary programming language for the app.
*   **Jetpack Compose:** Modern UI toolkit for building native Android UIs.
*   **Android Architecture Components:**
    *   **ViewModel:** For managing UI-related data and lifecycle.
    *   **LiveData:** For observing data changes.
    * **Hilt:** For dependency injection.
*   **Coroutines:** For asynchronous programming.
*   *   **Flow:** For observing data changes.
*   **Material 3:** For the latest Material Design components.
* **Timber:** For logging.
* **Shimmer:** For shimmer effect.

## Getting Started

### Prerequisites

*   Android Studio (latest stable version recommended)
*   Android device or emulator with API level 26 or higher
