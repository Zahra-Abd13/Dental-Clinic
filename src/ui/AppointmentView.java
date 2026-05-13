package ui;

/**
 *
 * @author My Pc
 */





import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.LocalDateTime;
import java.util.Comparator;

import com.mycompany.dentalclinic.model.*;
import com.mycompany.dentalclinic.service.*;
import com.mycompany.dentalclinic.exceptions.*;

/**
 * GUI view for managing Appointments in the Dental Clinic system.
 * Provides functionalities to add, update, delete, and sort appointments.
 */
public class AppointmentView {

    private AppointmentService service = new AppointmentService();
    private ObservableList<Appointments> data = FXCollections.observableArrayList();

    public Tab getTab() {

        //Input Fields 
        TextField id = new TextField();
        id.setPromptText("Appointment ID");

        TextField patientId = new TextField();
        patientId.setPromptText("Patient ID");

        TextField doctorId = new TextField();
        doctorId.setPromptText("Doctor ID");

        TextField dateTime = new TextField();
        dateTime.setPromptText("YYYY-MM-DDTHH:MM"); // ISO_LOCAL_DATE_TIME format

        //Buttons 
        Button bookBtn = new Button("Book Appointment");
        Button updateBtn = new Button("Update Appointment");
        Button deleteBtn = new Button("Delete Appointment");
        Button sortByDateBtn = new Button("Sort by Date");

        //Table
        TableView<Appointments> table = new TableView<>(data);

        // Column: Appointment ID
        TableColumn<Appointments, String> idCol = new TableColumn<>("ID");
        idCol.setMinWidth(120);
        idCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getId()));

        // Column: Patient Name
        TableColumn<Appointments, String> patientCol = new TableColumn<>("Patient");
        patientCol.setMinWidth(150);
        patientCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPatient().getName()));

        // Column: Doctor Name
        TableColumn<Appointments, String> doctorCol = new TableColumn<>("Doctor");
        doctorCol.setMinWidth(150);
        doctorCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDoctor().getName()));

        // Column: Date & Time
        TableColumn<Appointments, String> dateCol = new TableColumn<>("Date & Time");
        dateCol.setMinWidth(180);
        dateCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDateTime().toString()));

        // Column: Price
        TableColumn<Appointments, String> priceCol = new TableColumn<>("Price");
        priceCol.setMinWidth(100);
        priceCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(String.valueOf(c.getValue().getPrice())));

        table.getColumns().addAll(idCol, patientCol, doctorCol, dateCol, priceCol);

        //Load selected appointment into fields
        table.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldA, selected) -> {
                if (selected != null) {
                    id.setText(selected.getId());
                    patientId.setText(selected.getPatient().getId());
                    doctorId.setText(selected.getDoctor().getId());
                    dateTime.setText(selected.getDateTime().toString());
                    id.setDisable(true); // Prevent editing ID
                }
            }
        );

        //Book Appointment
        bookBtn.setOnAction(e -> {
            try {
                Patient p = PatientService.findPatientById(patientId.getText());
                Doctor d = DoctorService.findDoctorById(doctorId.getText());
                LocalDateTime dt = LocalDateTime.parse(dateTime.getText());

                Appointments a = new Appointments(id.getText(), p, d, dt);
                service.bookAppointment(a);
                data.add(a);
                clearFields(id, patientId, doctorId, dateTime);
                id.setDisable(false);
            } catch (SchedulingException ex) {
                showError(ex.getMessage());
            } catch (Exception ex) {
                showError("Invalid input or date format");
            }
        });

        //Update Appointment
        updateBtn.setOnAction(e -> {
            Appointments selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showError("Select an appointment to update");
                return;
            }
            try {
                Patient p = PatientService.findPatientById(patientId.getText());
                Doctor d = DoctorService.findDoctorById(doctorId.getText());
                LocalDateTime dt = LocalDateTime.parse(dateTime.getText());

                selected.setPatient(p);
                selected.setDoctor(d);
                selected.setDateTime(dt);
                table.refresh();
                clearFields(id, patientId, doctorId, dateTime);
                id.setDisable(false);
            } catch (Exception ex) {
                showError("Invalid update data");
            }
        });

        //Delete Appointment
        deleteBtn.setOnAction(e -> {
            Appointments selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showError("Please select an appointment to delete");
                return;
            }
            service.removeAppointment(selected);
            data.remove(selected);
            clearFields(id, patientId, doctorId, dateTime);
            id.setDisable(false);
        });

        
        sortByDateBtn.setOnAction(e -> {
            FXCollections.sort(data, Comparator.comparing(Appointments::getDateTime));
        });

       
        VBox form = new VBox(10, id, patientId, doctorId, dateTime, bookBtn, updateBtn, deleteBtn, sortByDateBtn);
        HBox layout = new HBox(20, form, table);

        Tab tab = new Tab("Appointments");
        tab.setContent(layout);
        tab.setClosable(false);
        return tab;
    }

    
    private void clearFields(TextField... fields) {
        for (TextField f : fields) f.clear();
    }

  
    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).show();
    }
}
