package sample;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Controller extends Application {

    private final TableView<Item> table = new TableView<>();
    private final ObservableList<Item> data = FXCollections.observableArrayList();
    final HBox hb = new HBox();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("TO DO LIST");
        stage.setWidth(580);
        stage.setHeight(550);

        final Label label = new Label("TO DO LIST");
        label.setFont(new Font("Arial", 20));

        CheckBoxColumn checkBoxCol = new CheckBoxColumn();
        checkBoxCol.setMinWidth(10);

        table.setEditable(true);

        TableColumn<Item, String> monthCol = new TableColumn("Month");
        monthCol.setMinWidth(25);
        monthCol.setCellValueFactory(
                new PropertyValueFactory<>("month"));

        monthCol.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
        monthCol.setOnEditCommit(
                (CellEditEvent<Item, String> t) -> {
                    ((Item) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setMonth(t.getNewValue());
                });

        TableColumn<Item, String> dayCol = new TableColumn("Day");
        dayCol.setMinWidth(25);
        dayCol.setCellValueFactory(
                new PropertyValueFactory<>("day"));

        dayCol.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
        dayCol.setOnEditCommit(
                (CellEditEvent<Item, String> t) -> {
                    ((Item) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setDay(t.getNewValue());
                });

        TableColumn<Item, String> todoCol = new TableColumn("Contents");
        todoCol.setMinWidth(300);
        todoCol.setCellValueFactory(
                new PropertyValueFactory<>("todo"));

        todoCol.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
        todoCol.setOnEditCommit(
                (CellEditEvent<Item, String> t) -> {
                    ((Item) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setTodo(t.getNewValue());
                });

        table.setItems(data);
        table.getColumns().addAll(checkBoxCol, monthCol, dayCol, todoCol);

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

            if((addMonth.getText() != null && !addMonth.getText().isEmpty()) && (addDay.getText() != null && !addDay.getText().isEmpty()) && (addTodo.getText() != null && !addTodo.getText().isEmpty())){

                    data.add(new Item(
                            addButton.isDefaultButton(),
                            addMonth.getText(),
                            addDay.getText(),
                            addTodo.getText()));
                    addMonth.clear();
                    addDay.clear();
                    addTodo.clear();

                }

                else{

                    System.out.println("入力してください");

            }

        });

        final Button deleteButton = new Button("Delete");
        deleteButton.setOnAction((ActionEvent e) -> {
        	for(int i=table.getItems().size()-1;i>=0;i--) {
        		Item item=table.getItems().get(i);
        		boolean ischecked=item.isChecked();
        		if(ischecked) {
        			data.remove(i);
        		}
        	}

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

    public static class CheckBoxColumn extends TableColumn<Item, Boolean>{

        public CheckBoxColumn(){

            this.setCellValueFactory(new PropertyValueFactory<>("checked"));
            this.setCellFactory(column -> {

                CheckBoxTableCell<Item, Boolean> cell = new CheckBoxTableCell<Item, Boolean>(index -> {

                    BooleanProperty selected = new SimpleBooleanProperty(this.getTableView().getItems().get(index).isChecked());
                    selected.addListener((ov, o, n) -> {

                        this.getTableView().getItems().get(index).setChecked(n);
                        this.getTableView().getSelectionModel().select(index);
                        Event.fireEvent(column.getTableView(), new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0,
                                MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true, true, null));

                            });

                    return selected;

                });

                return cell;

            });

        }

    }

    public static class Item {

        private final SimpleBooleanProperty checked;
        private final SimpleStringProperty month;
        private final SimpleStringProperty day;
        private final SimpleStringProperty todo;

        private Item(boolean checked, String fName, String lName, String todo) {
            this.checked = new SimpleBooleanProperty(checked);
            this.month = new SimpleStringProperty(fName);
            this.day = new SimpleStringProperty(lName);
            this.todo = new SimpleStringProperty(todo);
        }

        public SimpleBooleanProperty checkedProperty() {
            return checked;
        }

        public boolean isChecked() {
            return checked.get();
        }

        public void setChecked(boolean checked) {
            this.checked.set(checked);
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
