package com.example.metroclient;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MetroClientApp extends Application {

    private final MetroApiClient apiClient = new MetroApiClient();

    private ObservableList<StationClientDto> stations = FXCollections.observableArrayList();
    private ObservableList<StationClientDto> routeStations = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Информационная система метрополитена");

        Dialog<Boolean> loginDialog = createLoginDialog();
        loginDialog.showAndWait();

        BorderPane root = new BorderPane();

        ComboBox<StationClientDto> fromBox = new ComboBox<>();
        ComboBox<StationClientDto> toBox = new ComboBox<>();
        fromBox.setPrefWidth(250);
        toBox.setPrefWidth(250);

        Button loadButton = new Button("Обновить станции");
        loadButton.setOnAction(e -> {
            try {
                stations.setAll(apiClient.loadStations());
                fromBox.setItems(stations);
                toBox.setItems(stations);
            } catch (Exception ex) {
                showError("Ошибка загрузки станций: " + ex.getMessage());
            }
        });

        Button routeButton = new Button("Построить маршрут");
        Label totalTimeLabel = new Label("Время в пути: -");

        routeButton.setOnAction(e -> {
            StationClientDto from = fromBox.getValue();
            StationClientDto to = toBox.getValue();
            if (from == null || to == null) {
                showError("Выберите начальную и конечную станции");
                return;
            }
            try {
                RouteClientResponse resp = apiClient.findRoute(from.getId(), to.getId());
                if (resp.getStations() == null || resp.getStations().isEmpty()) {
                    showError("Маршрут не найден");
                } else {
                    routeStations.setAll(resp.getStations());
                    totalTimeLabel.setText("Время в пути: " + resp.getTotalMinutes() + " мин");
                }
            } catch (Exception ex) {
                showError("Ошибка построения маршрута: " + ex.getMessage());
            }
        });

        HBox topControls = new HBox(10, new Label("От:"), fromBox,
                new Label("До:"), toBox, loadButton, routeButton);
        topControls.setPadding(new Insets(10));

        TableView<StationClientDto> routeTable = new TableView<>(routeStations);
        TableColumn<StationClientDto, String> nameCol = new TableColumn<>("Станция");
        nameCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getName()));
        TableColumn<StationClientDto, String> lineCol = new TableColumn<>("Линия");
        lineCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getLineName()));
        TableColumn<StationClientDto, String> transferCol = new TableColumn<>("Пересадка");
        transferCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().isTransfer() ? "Да" : "Нет"
        ));
        routeTable.getColumns().addAll(nameCol, lineCol, transferCol);
        routeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        BorderPane.setMargin(routeTable, new Insets(10));

        root.setTop(topControls);
        root.setCenter(routeTable);
        root.setBottom(totalTimeLabel);
        BorderPane.setMargin(totalTimeLabel, new Insets(10));

        primaryStage.setScene(new Scene(root, 900, 400));
        primaryStage.show();

        loadButton.fire();
    }

    private Dialog<Boolean> createLoginDialog() {
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Вход");
        dialog.setHeaderText("Авторизация в системе");
        ButtonType loginButtonType = new ButtonType("Войти", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Имя пользователя");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Пароль");

        grid.add(new Label("Пользователь:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Пароль:"), 0, 1);
        grid.add(passwordField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                apiClient.setCredentials(usernameField.getText(), passwordField.getText());
                return true;
            }
            System.exit(0);
            return false;
        });

        return dialog;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
