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

import com.mycompany.dentalclinic.model.Doctor;
import com.mycompany.dentalclinic.service.DoctorService;
import com.mycompany.dentalclinic.exceptions.*;

public class DoctorView {

    private DoctorService service = new DoctorService();
    private ObservableList<Doctor> data = FXCollections.observableArrayList();

    public Tab getTab() {

        // Fields
        TextField id = new TextField();
        id.setPromptText("ID");

        TextField name = new TextField();
        name.setPromptText("Name");

        TextField phone = new TextField();
        phone.setPromptText("Phone");

        TextField email = new TextField();
        email.setPromptText("Email");

        TextField fee = new TextField();
        fee.setPromptText("Fee");

        //Buttons 
        Button addBtn = new Button("Add Doctor");
        Button updateBtn = new Button("Update Doctor");
        Button deleteBtn = new Button("Delete Doctor");

        // Table 
        TableView<Doctor> table = new TableView<>(data);
        table.setPrefWidth(750);

        TableColumn<Doctor, String> idCol =
                new TableColumn<>("ID");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getId()
                ));

        TableColumn<Doctor, String> nameCol =
                new TableColumn<>("Name");
        nameCol.setMinWidth(150);
        nameCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getName()
                ));

        TableColumn<Doctor, String> phoneCol =
                new TableColumn<>("Phone");
        phoneCol.setMinWidth(130);
        phoneCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getPhone()
                ));

        TableColumn<Doctor, String> emailCol =
                new TableColumn<>("Email");
        emailCol.setMinWidth(180);
        emailCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getEmail()
                ));

        TableColumn<Doctor, String> feeCol =
                new TableColumn<>("Fee");
        feeCol.setMinWidth(100);
        feeCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        String.valueOf(c.getValue().getFee())
                ));

        table.getColumns().addAll(
                idCol, nameCol, phoneCol, emailCol, feeCol
        );

        //Load selected doctor
        table.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldD, selected) -> {
                    if (selected != null) {
                        id.setText(selected.getId());
                        name.setText(selected.getName());
                        phone.setText(selected.getPhone());
                        email.setText(selected.getEmail());
                        fee.setText(String.valueOf(selected.getFee()));
                        id.setDisable(true); // ID ثابت
                    }
                }
        );

        // ===== Add Doctor =====
        addBtn.setOnAction(e -> {
            try {
                Doctor d = new Doctor(
                        id.getText(),
                        name.getText(),
                        phone.getText(),
                        email.getText(),
                        Double.parseDouble(fee.getText())
                );

                service.addDoctor(d);
                data.add(d);
                clearFields(id, name, phone, email, fee);

            } catch (NumberFormatException ex) {
                showError("Fee must be a number");
            } catch (InvalidDataException ex) {
                showError(ex.getMessage());
            }
        });

        // Update Doctor 
        updateBtn.setOnAction(e -> {
            Doctor selected =
                    table.getSelectionModel().getSelectedItem();

            if (selected == null) {
                showError("Select a doctor to update");
                return;
            }

            try {
                selected.setName(name.getText());
                selected.setPhone(phone.getText());
                selected.setEmail(email.getText());
                selected.setFee(Double.parseDouble(fee.getText()));

                table.refresh();
                clearFields(id, name, phone, email, fee);
                id.setDisable(false);

            } catch (NumberFormatException ex) {
                showError("Fee must be a number");
            }
        });

        //Delete Doctor
        deleteBtn.setOnAction(e -> {
            Doctor d = table.getSelectionModel().getSelectedItem();
            if (d == null) {
                showError("Please select a doctor to delete");
                return;
            }

            try {
                service.removeDoctor(d.getId());
                data.remove(d);
                clearFields(id, name, phone, email, fee);
                id.setDisable(false);

            } catch (NotFoundException ex) {
                showError(ex.getMessage());
            }
        });

        VBox form = new VBox(10,
                id, name, phone, email, fee,
                addBtn, updateBtn, deleteBtn
        );

        HBox layout = new HBox(20, form, table);

        Tab tab = new Tab("Doctors");
        tab.setContent(layout);
        tab.setClosable(false);
        return tab;
    }

    //Helpers 
    private void clearFields(TextField... fields) {
        for (TextField f : fields) f.clear();
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).show();
    }
}
