/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.mycompany.dentalclinic.model.Patient;
import com.mycompany.dentalclinic.service.PatientService;
import com.mycompany.dentalclinic.exceptions.*;





public class PatientView {

    private PatientService service = new PatientService();
    private ObservableList<Patient> data = FXCollections.observableArrayList();

    public Tab getTab() {

        //Fields
        TextField id = new TextField();
        id.setPromptText("ID");

        TextField name = new TextField();
        name.setPromptText("Name");

        TextField age = new TextField();
        age.setPromptText("Age");

        TextField phone = new TextField();
        phone.setPromptText("Phone");

        TextField email = new TextField();
        email.setPromptText("Email");

        TextField history = new TextField();
        history.setPromptText("Medical History");

        //Buttons
        Button addBtn = new Button("Add Patient");
        Button updateBtn = new Button("Update Patient");
        Button deleteBtn = new Button("Delete Patient");

        //Table
        TableView<Patient> table = new TableView<>(data);

        TableColumn<Patient, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getId()));
        idCol.setMinWidth(100);

        TableColumn<Patient, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getName()));
        nameCol.setMinWidth(150);

        TableColumn<Patient, String> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(String.valueOf(c.getValue().getAge())));
        ageCol.setMinWidth(60);

        TableColumn<Patient, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPhone()));
        phoneCol.setMinWidth(120);

        TableColumn<Patient, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getEmail()));
        emailCol.setMinWidth(180);

        TableColumn<Patient, String> historyCol = new TableColumn<>("Medical History");
        historyCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getMedicalHistory()));
        historyCol.setMinWidth(250);

        table.getColumns().addAll(idCol, nameCol, ageCol, phoneCol, emailCol, historyCol);

        //Load selected patient into fields
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldP, selected) -> {
            if (selected != null) {
                id.setText(selected.getId());
                name.setText(selected.getName());
                age.setText(String.valueOf(selected.getAge()));
                phone.setText(selected.getPhone());
                email.setText(selected.getEmail());
                history.setText(selected.getMedicalHistory());
                id.setDisable(true); // ID cannot be changed
            }
        });

        //Add Patient
        addBtn.setOnAction(e -> {
            try {
                Patient p = new Patient(
                        id.getText(),
                        name.getText(),
                        Integer.parseInt(age.getText()),
                        phone.getText(),
                        email.getText(),
                        history.getText()
                );
                service.addPatient(p);
                data.add(p);
                clearFields(id, name, age, phone, email, history);
                id.setDisable(false);

            } catch (NumberFormatException ex) {
                showError("Age must be a number");
            } catch (InvalidDataException ex) {
                showError(ex.getMessage());
            }
        });

        // Update Patient 
        updateBtn.setOnAction(e -> {
            Patient selected = table.getSelectionModel().getSelectedItem();

            if (selected == null) {
                showError("Select a patient to update");
                return;
            }

            try {
                selected.setName(name.getText());
                selected.setAge(Integer.parseInt(age.getText()));
                selected.setPhone(phone.getText());
                selected.setEmail(email.getText());
                selected.setMedicalHistory(history.getText());

                table.refresh();
                clearFields(id, name, age, phone, email, history);
                id.setDisable(false);

            } catch (NumberFormatException ex) {
                showError("Age must be a number");
            }
        });

        // Delete Patient
        deleteBtn.setOnAction(e -> {
            Patient p = table.getSelectionModel().getSelectedItem();
            if (p != null) {
                try {
                    service.removePatient(p.getId());
                    data.remove(p);
                    clearFields(id, name, age, phone, email, history);
                    id.setDisable(false);

                } catch (NotFoundException ex) {
                    showError(ex.getMessage());
                }
            }
        });

        VBox form = new VBox(10, id, name, age, phone, email, history, addBtn, updateBtn, deleteBtn);
        HBox layout = new HBox(20, form, table);
        layout.setPrefHeight(400);
        layout.setPrefWidth(900);

        Tab tab = new Tab("Patients");
        tab.setContent(layout);
        tab.setClosable(false);
        return tab;
    }

    //Helper Methods
    private void clearFields(TextField... fields) {
        for (TextField f : fields) f.clear();
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).show();
    }
}
