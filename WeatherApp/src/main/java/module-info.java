module fi.tuni.progthree.weatherapp {
    requires javafx.controls;
    exports fi.tuni.prog3.weatherapp;
    requires com.google.gson;
    requires org.json;
    opens fi.tuni.prog3.weatherapp.models;
}
