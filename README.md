# 🍽️ FoodHub App

![Meals App](app/src/main/res/drawable/Splash.png)

## 📋 Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Architecture](#architecture)
- [Libraries and Tools Used](#libraries-and-tools-used)
- [Installation](#installation)
- [Usage](#usage)
- [License](#license)

## 📖 Introduction
The **FoodHub App** is an Android application built with Kotlin that allows users to explore a variety of meals, view their recipes, watch preparation videos, and search for meals. Additionally, users can mark meals as their favorites for easy access later.

## ✨ Features
- 🍜 Display a list of meals.
- 📝 View detailed recipes for each meal.
- 🎥 Watch videos to learn how to prepare the meals.
- 🔍 Search for meals by name.
- ❤️ Mark meals as favorites.
- 📦 Offline caching for favorite meals.

## 🏗️ Architecture
The application follows the **MVVM (Model-View-ViewModel)** architecture pattern. The following components are used:
- **Model:** Represents the data (Meal, Recipe) and business logic.
- **ViewModel:** Serves as a bridge between the View and the Model, managing UI-related data.
- **View:** Responsible for displaying the data (XML layouts).

## 🛠️ Libraries and Tools Used
- **🟦 Kotlin**: Programming language used to build the app.
- **🗂️ MVVM**: Architecture pattern that separates the UI logic from the business logic.
- **📚 Repository Pattern**: Manages data operations and decouples the data layer from the rest of the app.
- **💾 Room**: Local database used for storing favorite meals and offline data caching.
- **🌐 Retrofit**: For network operations, fetching meal data from a RESTful API.
- **⚡ OkHttp**: HTTP client for efficient networking.
- **🖼️ Glide**: For loading and caching images efficiently.
- **🔐 SharedPreferences**: For storing simple key-value pairs, like user preferences.
- **🖌️ XML**: Used for designing the app’s UI.

## 🛠️ Installation
To run the project locally, follow these steps:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/meals-app.git
