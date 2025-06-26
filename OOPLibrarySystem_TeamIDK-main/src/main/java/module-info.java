module com.practicum.demo1 {
    requires javafx.controls;

    exports com.practicum;              // <-- You forgot this one!
    exports com.practicum.models;
    exports com.practicum.controllers;
    exports com.practicum.storage;
    exports com.practicum.utils;
}
