package sample;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.lang.Object;

public class Controller extends Application {

    private final TableView<Person> table = new TableView<>();
    private final ObservableList<Person> data = FXCollections.observableArrayList();
    final HBox hb = new HBox();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("TO DO LIST");
        stage.setWidth(500);
        stage.setHeight(550);
        

        final Label label = new Label("TO DO LIST");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn<Person, String> monthCol = new TableColumn("Month");
        monthCol.setMinWidth(25);
        monthCol.setCellValueFactory(
                new PropertyValueFactory<>("month"));

        monthCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
        monthCol.setOnEditCommit(
                (CellEditEvent<Person, String> t) -> {
                    ((Person) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setMonth(t.getNewValue());
                });

        TableColumn<Person, String> dayCol = new TableColumn("Day");
        dayCol.setMinWidth(25);
        dayCol.setCellValueFactory(
                new PropertyValueFactory<>("day"));

        dayCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
        dayCol.setOnEditCommit(
                (CellEditEvent<Person, String> t) -> {
                    ((Person) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setDay(t.getNewValue());
                });

        TableColumn<Person, String> todoCol = new TableColumn("Todo");
        todoCol.setMinWidth(300);
        todoCol.setCellValueFactory(
                new PropertyValueFactory<>("todo"));

        todoCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
        todoCol.setOnEditCommit(
                (CellEditEvent<Person, String> t) -> {
                    ((Person) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setTodo(t.getNewValue());
                });

        table.setItems(data);
        table.getColumns().addAll(monthCol, dayCol, todoCol);

        final TextField addMonth = new TextField();
        addMonth.setPromptText("Month");
        addMonth.setMaxWidth(monthCol.getPrefWidth());
        final TextField addDay = new TextField();
        addDay.setMaxWidth(dayCol.getPrefWidth());
        addDay.setPromptText("Day");
        final TextField addTodo = new TextField();
        addTodo.setMaxWidth(todoCol.getPrefWidth());
        addTodo.setPromptText("Todo");

       final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            data.add(new Person(
                    addMonth.getText(),
                    addDay.getText(),
                    addTodo.getText()));
            addMonth.clear();
            addDay.clear();
            addTodo.clear();
        });

        final Button deleteButton = new Button("Delete");
        deleteButton.setOnAction((ActionEvent e) -> {

        });

        hb.getChildren().addAll(addMonth, addDay, addTodo, addButton, deleteButton);
        hb.setSpacing(3);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public static class Person {

        private final SimpleStringProperty month;
        private final SimpleStringProperty day;
        private final SimpleStringProperty todo;

        private Person(String fName, String lName, String todo) {
            this.month = new SimpleStringProperty(fName);
            this.day = new SimpleStringProperty(lName);
            this.todo = new SimpleStringProperty(todo);
        }

        public String getMonth() {
            return month.get();
        }

        public void setMonth(String fName) {
            month.set(fName);
        }

        public String getDay() {
            return day.get();
        }

        public void setDay(String fName) {
            day.set(fName);
        }

        public String getTodo() {
            return todo.get();
        }

        public void setTodo(String fName) {
            todo.set(fName);
        }
    }
}

