import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BarChartController implements Initializable {
    GraphData gd = new GraphData();

    @FXML
    private Button mainMenu;

    @FXML
    private ComboBox<String> cb1;

    @FXML
    private BarChart<String,Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private ComboBox<String> cb2;

    @FXML
    private ComboBox<String> showType;

    @FXML
    private Label lb1;

    @FXML
    private ComboBox<String> view;

    @FXML
    private Label lb2;

    @FXML
    private Label alert;

    String[] countryy ;
    String[] showTypee = new String[]{"Total confirmed cases", "Total deaths", "New confirmed cases", "New deaths"};
    String[] vieww = new String[]{"BarChart","LineChart"};

    ArrayList<String> datee = new ArrayList<>();
    ArrayList<String> confirmCase = new ArrayList<>();

    String countryName = "World";
    String graphType = "Total confirmed cases";
    String casee ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alert.setText("");
        try {
            countryy = gd.getCountry();
            datee = gd.getDate();
            confirmCase = gd.getCountryConfirmCase(graphType,countryName);
        } catch (Exception e) {
            alert.setText("Please select the area.");
        }

        mainMenu.setText("Main Menu");
        lb2.setText("last update: " + datee.get(datee.size()-1));

        casee = confirmCase.get(confirmCase.size()-1);

        XYChart.Series series = new XYChart.Series();

        ObservableList<String> ob1 = FXCollections.observableArrayList(countryy);
        ObservableList<String> ob2 = FXCollections.observableArrayList(datee);
        ObservableList<String> ob3 = FXCollections.observableArrayList(showTypee);
        ObservableList<String> ob4 = FXCollections.observableArrayList(vieww);

        cb1.getItems().addAll(ob1);
        cb2.getItems().addAll(ob2);
        showType.getItems().addAll(ob3);
        view.getItems().addAll(ob4);

        showType.setValue(graphType);
        series.setName("Covid19 confirm cases");
        lb1.setText(String.format("%s : %,d cases", graphType, Integer.parseInt(casee)) );
        cb2.setValue(datee.get(datee.size()-1));

        xAxis.setLabel("Year-Month-Date");
        yAxis.setLabel("Total confirmed cases");

        barChart.setTitle(countryName);
        barChart.getData().add(series);
        barChart.setAnimated(false);

        view.setValue("BarChart");

        for(int i = 1; i < datee.size(); i++) {
            XYChart.Data<String, Number> data = new XYChart.Data<String,Number>(String.valueOf(datee.get(i)), Integer.parseInt(confirmCase.get(i)));
            series.getData().add(data);
        }

        showType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                    series.setName(showType.getValue());
                    yAxis.setLabel(showType.getValue());
                    graphType = showType.getValue();
                    series.getData().clear();
                    barChart.setTitle(countryName);
                    barChart.setStyle("-fx-font-style: italic");
                try {
                    confirmCase = gd.getCountryConfirmCase(graphType,countryName);
                } catch (Exception e) {
                    alert.setText("Please select the area.");
                }
                cb2.setValue(datee.get(datee.size()-1));
                    casee = confirmCase.get(confirmCase.size()-1);
                    lb1.setText(String.format("%s : %,d cases", graphType, Integer.parseInt(casee)) );

                    for(int i = 1; i < datee.size(); i++) {
                        XYChart.Data<String, Number> data = new XYChart.Data<String,Number>(String.valueOf(datee.get(i)), Integer.parseInt(confirmCase.get(i)));
                        series.getData().add(data);
                    }

            }
        });

        cb1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    alert.setText("");
                    series.getData().clear();
                    countryName = cb1.getValue();
                    barChart.setTitle(countryName);
                    barChart.setStyle("-fx-font-style: italic");
                    confirmCase = gd.getCountryConfirmCase(graphType,countryName);
                    showType.setValue(graphType);
                    cb2.setValue(datee.get(datee.size()-1));
                    casee = confirmCase.get(confirmCase.size()-1);

                    for(int i = 1; i < datee.size(); i++) {
                            XYChart.Data<String, Number> data = new XYChart.Data<String,Number>(String.valueOf(datee.get(i)), Integer.parseInt(confirmCase.get(i)));
                            series.getData().add(data);
                    }
                } catch (Exception e) {
                    alert.setText("Please select the area.");
                }
                lb1.setText(String.format("%s : %,d cases", graphType, Integer.parseInt(casee)) );
            }
        });

        cb2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for(int i = 0 ; i < confirmCase.size(); i++){
                    if(cb2.getValue() == datee.get(i)){
                        casee = confirmCase.get(i);
                    }
                    if((cb1.getValue() != null)) {
                        lb1.setText(String.format("%s : %,d cases", graphType, Integer.parseInt(casee)) );
                    }
                }
            }
        });
    }

    public void setScene(ActionEvent event) throws IOException {
        String fxml;
        fxml = view.getValue() + ".fxml";
        view.setValue(view.getValue());
        Parent chart = FXMLLoader.load(getClass().getResource("fxml/" +fxml));
        Scene charScene = new Scene(chart);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(charScene);
        window.show();

    }

    public void mainMenuHandler(ActionEvent event) throws IOException {
        Parent chart = FXMLLoader.load(getClass().getResource("fxml/Menu.fxml"));
        Scene charScene = new Scene(chart);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(charScene);
        window.show();
    }

    public void setAlert(String s) {
        alert.setText(s);
    }
}
