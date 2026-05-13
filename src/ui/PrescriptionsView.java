/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

/**
 *
 * @author My Pc
 */

import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.mycompany.dentalclinic.model.*;
import com.mycompany.dentalclinic.service.*;
import java.util.List;

public class PrescriptionsView {

    private ObservableList<Prescriptions> data = FXCollections.observableArrayList();

    public Tab getTab() {

        // ===== Fields =====
        TextField id = new TextField();
        id.setPromptText("Prescription ID");

        TextField patientId = new TextField();
        patientId.setPromptText("Patient ID");

        TextField doctorId = new TextField();
        doctorId.setPromptText("Doctor ID");

        TextField appointmentId = new TextField();
        appointmentId.setPromptText("Appointment ID");

        TextField notes = new TextField();
        notes.setPromptText("Notes");

        TextField medicine = new TextField();
        medicine.setPromptText("Medicine");

        // ===== Buttons =====
        Button createBtn = new Button("Create");
        Button updateBtn = new Button("Update");
        Button deleteBtn = new Button("Delete");

        // ===== Table =====
        TableView<Prescriptions> table = new TableView<>(data);
        table.setPrefWidth(900);

        TableColumn<Prescriptions, String> idCol = new TableColumn<>("ID");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getId()));

        TableColumn<Prescriptions, String> patientCol = new TableColumn<>("Patient");
        patientCol.setMinWidth(150);
        patientCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPatient().getName()));

        TableColumn<Prescriptions, String> doctorCol = new TableColumn<>("Doctor");
        doctorCol.setMinWidth(150);
        doctorCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDoctor().getName()));

        TableColumn<Prescriptions, String> appCol = new TableColumn<>("Appointment");
        appCol.setMinWidth(120);
        appCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getAppointment().getId()));

        TableColumn<Prescriptions, String> notesCol = new TableColumn<>("Notes");
        notesCol.setMinWidth(180);
        notesCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNotes()));

        TableColumn<Prescriptions, String> medsCol = new TableColumn<>("Medicines");
        medsCol.setMinWidth(200);
        medsCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(String.join(", ", c.getValue().getMedicines())));

        table.getColumns().addAll(idCol, patientCol, doctorCol, appCol, notesCol, medsCol);

        // ===== Load selected prescription =====
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldP, selected) -> {
            if (selected != null) {
                id.setText(selected.getId());
                patientId.setText(selected.getPatient().getId());
                doctorId.setText(selected.getDoctor().getId());
                appointmentId.setText(selected.getAppointment().getId());
                notes.setText(selected.getNotes());
                medicine.clear();
                id.setDisable(true); // ID cannot be changed
            }
        });

        // ===== Create Prescription =====
        createBtn.setOnAction(e -> {
            try {
                Patient p = PatientService.findPatientById(patientId.getText());
                Doctor d = DoctorService.findDoctorById(doctorId.getText());
                Appointments a = AppointmentService.findAppointmentById(appointmentId.getText());

                Prescriptions pr = new Prescriptions(p, d, id.getText(), a, notes.getText());

                if (!medicine.getText().isBlank()) {
                    pr.addMedicine(medicine.getText());
                }

                data.add(pr);
                clearFields(id, patientId, doctorId, appointmentId, notes, medicine);
                id.setDisable(false);

            } catch (Exception ex) {
                showError("Invalid input or IDs not found");
            }
        });

        // ===== Update Prescription =====
        updateBtn.setOnAction(e -> {
            Prescriptions selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showError("Select a prescription to update");
                return;
            }

            selected.setNotes(notes.getText());

            if (!medicine.getText().isBlank()) {
                selected.addMedicine(medicine.getText());
            }

            table.refresh();
            medicine.clear();
        });

        // ===== Delete Prescription =====
        deleteBtn.setOnAction(e -> {
            Prescriptions selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showError("Select a prescription to delete");
                return;
            }

            data.remove(selected);
            clearFields(id, patientId, doctorId, appointmentId, notes, medicine);
            id.setDisable(false);
        });

        VBox form = new VBox(10, id, patientId, doctorId, appointmentId, notes, medicine, createBtn, updateBtn, deleteBtn);
        HBox layout = new HBox(20, form, table);

        Tab tab = new Tab("Prescriptions");
        tab.setContent(layout);
        tab.setClosable(false);
        return tab;
    }

    // ===== Helper Methods =====
    private void clearFields(TextField... fields) {
        for (TextField f : fields) f.clear();
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).show();
    }
}
