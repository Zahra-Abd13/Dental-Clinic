/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.mycompany.dentalclinic.model.*;
import com.mycompany.dentalclinic.service.*;
import com.mycompany.dentalclinic.exceptions.*;


public class PaymentView {

    private ObservableList<Payment> data = FXCollections.observableArrayList();

    public Tab getTab() {

        //Fields
        TextField id = new TextField();
        id.setPromptText("Payment ID");

        TextField appointmentId = new TextField();
        appointmentId.setPromptText("Appointment ID");

        TextField method = new TextField();
        method.setPromptText("Payment Method");

        Button addBtn = new Button("Add Payment");
        Button editBtn = new Button("Edit Selected Payment");

        //Table
        TableView<Payment> table = new TableView<>(data);
        table.setPrefWidth(700);

        TableColumn<Payment, String> idCol = new TableColumn<>("Payment ID");
        idCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getId()));
        idCol.setPrefWidth(100);

        TableColumn<Payment, String> patientCol = new TableColumn<>("Patient");
        patientCol.setCellValueFactory(c -> {
            Appointments a = c.getValue().getAppointment();
            return new javafx.beans.property.SimpleStringProperty(
                    (a != null && a.getPatient() != null) ? a.getPatient().getName() : "Unknown"
            );
        });
        patientCol.setPrefWidth(150);

        TableColumn<Payment, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                String.valueOf(c.getValue().getAmount())
        ));
        amountCol.setPrefWidth(100);

        TableColumn<Payment, String> methodCol = new TableColumn<>("Payment Method");
        methodCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getMethod()));
        methodCol.setPrefWidth(150);

        table.getColumns().addAll(idCol, patientCol, amountCol, methodCol);

        //Add Payment
        addBtn.setOnAction(e -> {
            String payId = id.getText().trim();
            String appId = appointmentId.getText().trim();
            String payMethod = method.getText().trim();

            if (payId.isEmpty() || appId.isEmpty() || payMethod.isEmpty()) {
                showError("All fields are required");
                return;
            }

            boolean exists = data.stream().anyMatch(p -> p.getId().equals(payId));
            if (exists) {
                showError("Payment ID already exists");
                return;
            }

            try {
                Appointments a = AppointmentService.findAppointmentById(appId);

                if (a == null) {
                    showError("Appointment ID not found");
                    return;
                }
                if (a.getPatient() == null) {
                    showError("Appointment has no patient assigned");
                    return;
                }
                if (a.getDoctor() == null) {
                    showError("Appointment has no doctor assigned");
                    return;
                }

                Payment p = new Payment(payId, a, payMethod);
                data.add(p);
                clearFields(id, appointmentId, method);

            } catch (NotFoundException ex) {
                showError("Appointment ID not found");
            } catch (Exception ex) {
                ex.printStackTrace();
                showError("Unexpected error: " + ex.getMessage());
            }
        });

        //Edit Payment Method
        editBtn.setOnAction(e -> {
            Payment selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showError("Select a payment to edit");
                return;
            }

            String newMethod = method.getText().trim();
            if (newMethod.isEmpty()) {
                showError("Enter a new payment method");
                return;
            }

            selected.setMethod(newMethod);
            table.refresh();
            clearFields(method);
        });

        VBox form = new VBox(10, id, appointmentId, method, new HBox(10, addBtn, editBtn));
        HBox layout = new HBox(20, form, table);

        Tab tab = new Tab("Payments");
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
