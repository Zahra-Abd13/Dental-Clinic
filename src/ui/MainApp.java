/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

/**
 *
 * @author My Pc
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        TabPane root = new TabPane();

        root.getTabs().addAll(
                new PatientView().getTab(),
                new DoctorView().getTab(),
                new AppointmentView().getTab(),
                new PrescriptionsView().getTab() , 
                new PaymentView().getTab()
        );

        Scene scene = new Scene(root, 900, 500);
        stage.setTitle("Dental Clinic Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
