# WeatherApp

The Weather App project aims to create a functional program that retrieves weather data from OpenWeatherMap and presents it through a graphical user interface (GUI). This application assists users in accessing weather forecasts for different locations, saving favorite locations, visualizing and displaying weather information.

## Features

- User-Friendly Interface: Presents a detailed weather forecast (current, hourly, and daily) through a GUI, allowing location searches, saving favorites, and remembering the last viewed location. Present a detailed forecast.

- Weather Data Display: View detailed weather data, including temperature, precipitation, wind speed and direction, sunset and sunrise.

- Daily Summaries: Access concise daily summaries of weather conditions, including highs and lows, precipitation probabilities, and notable weather events.

- Hourly Forecasts: A three hourly weather data update of the day.

## Project Structure
The project implements functionality using object-oriented design, utilizing external libraries for implementation (design by contract) and having primary JUnit testing for the code. The project follows a standard Maven directory structure compromising MVC architecture:

- `src/main/java`: Contains the Java source code.
  - `fi.tuni.prog3.controllers`: Controllers responsible for handling user interactions.
  - `fi.tuni.prog3.models`: Models representing weather, location, and time data.
  - `fi.tuni.prog3.services`: Services responsible for fetching and processing data from API.
  - `fi.tuni.prog3.utilities`: Utility classes for common functionalities.
  - `fi.tuni.prog3.views`: Contains files defining the application's views.
- `src/main/test`: Implemented JUnit test classes for services.

- `target`: Default output directory for compiled code and generated artifacts.
- `nbproject`: NetBeans project configuration files.
- `pom.xml`: Maven build configuration file.
- `README.md`: Project documentation file (you're reading it now).

## Dependencies

The project uses Maven for build and dependency management. Ensure that you have JavaFX and other required dependencies correctly configured in your development environment. To build and run this project, ensure you have the following installed:

- Java Development Kit (JDK) 8 or higher
- Apache Maven

## Getting Started

1. **Clone the Repository:**

   ```bash
   git clone https://course-gitlab.tuni.fi/compcs140-fall2023/group3207.git
   cd WeatherApp
   
2. **Build the Project:**

   ```bash
   mvn clean install
   
3. **Run the Application:**

   ```bash
   java -jar target/weatherapp.jar
   
Alternatively, you can run the application from your IDE by opening the project in NetBeans.


## Authors

- [Ayman Asad Khan]
- [An Cao]
- [Areeba Nadeem]


