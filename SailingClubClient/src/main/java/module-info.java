/**
 * Sailing club module with all the class and controller for the application
 */
module it.unipr.ieet.project.sailingclub {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.unipr.ieet.project.sailingclub to javafx.fxml;
    exports it.unipr.ieet.project.sailingclub;
}