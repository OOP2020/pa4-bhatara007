import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WorldController implements Initializable {

    GraphData gd = new GraphData();

    @FXML
    private Button mainMenu;

    @FXML
    private Label lb1;

    @FXML
    private Label lb4;

    @FXML
    private Label lb2;

    @FXML
    private Label lb3;

    @FXML
    private Text text;

    @FXML
    private ComboBox<String> cb1;

    @FXML
    private Text date;

    @FXML
    private Text t1;

    @FXML
    private Text t4;

    @FXML
    private Text t3;

    @FXML
    private Text t2;

    String[] worldDataToday;
    String[] getWorldDataYesterday;

    String totalCases;
    String totalDeaths;
    String newCase;
    String newDeaths;
    String today;
    String yesterday;
    String diff1;
    String diff2;
    String diff3;
    String diff4;

    double percent1 = 0;
    double percent2 = 0;
    double percent3 = 0;
    double percent4 = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cb1.getItems().addAll(gd.getCountry());
            today = gd.getDate().get(gd.getDate().size()-1);
            yesterday = gd.getDate().get(gd.getDate().size()-2);
            worldDataToday = gd.getWorldData("World",today);
            getWorldDataYesterday = gd.getWorldData("World", yesterday);
        } catch (Exception e) {
            System.out.println("URL Error");
        }

        newCase = String.format("%,d", Integer.parseInt(worldDataToday[2]));
        newDeaths = String.format("%,d", Integer.parseInt(worldDataToday[3]));
        totalCases = String.format("%,d", Integer.parseInt(worldDataToday[4]));
        totalDeaths = String.format("%,d", Integer.parseInt(worldDataToday[5]));

        percent1 = (convertDouble(worldDataToday[4]) - convertDouble(getWorldDataYesterday[4])) * 100 /convertDouble(getWorldDataYesterday[4]);
        percent2 = (convertDouble(worldDataToday[2]) - convertDouble(getWorldDataYesterday[2])) * 100 /convertDouble(getWorldDataYesterday[2]);
        percent3 = (convertDouble(worldDataToday[3]) - convertDouble(getWorldDataYesterday[3])) * 100 /convertDouble(getWorldDataYesterday[3]);
        percent4 = (convertDouble(worldDataToday[5]) - convertDouble(getWorldDataYesterday[5])) * 100 /convertDouble(getWorldDataYesterday[5]);

        if(Double.isNaN(percent1)) percent1 = 0;
        if(Double.isNaN(percent2)) percent2 = 0;
        if(Double.isNaN(percent3)) percent3 = 0;
        if(Double.isNaN(percent4)) percent4 = 0;

        diff1 = String.format("( %.2f",percent1);
        diff2 = String.format("( %.2f",percent2);
        diff3 = String.format("( %.2f",percent3);
        diff4 = String.format("( %.2f",percent4);

        if(percent1 > 0) {diff1 = String.format("( +%.2f",percent1); t1.setStyle("-fx-fill: red");}
        if(percent2 > 0) {diff2 = String.format("( +%.2f",percent2); t2.setStyle("-fx-fill: red");}
        if(percent3 > 0) {diff3 = String.format("( +%.2f",percent3); t3.setStyle("-fx-fill: red");}
        if(percent4 > 0) {diff4 = String.format("( +%.2f",percent4); t4.setStyle("-fx-fill: red");}

        lb1.setText(totalCases);
        lb2.setText(newCase);
        lb3.setText(totalDeaths);
        lb4.setText(newDeaths);
        text.setText("World");
        date.setText("Date: " + worldDataToday[0]);
        t1.setText(diff1+"% )");
        t2.setText(diff2+"% )");
        t3.setText(diff3+"% )");
        t4.setText(diff4+"% )");
    }

    public int convertInt(String s){
        return Integer.parseInt(s);
    }

    public double convertDouble(String s){
        return Double.parseDouble(s);
    }

    public void cb1Handler(){
        try {
            worldDataToday = gd.getWorldData(cb1.getValue(),today);
            getWorldDataYesterday = gd.getWorldData(cb1.getValue(),yesterday);
        } catch (Exception e) {
            System.out.println("URL Error");
        }
        text.setStyle("-fx-font-size: 64");

        if(cb1.getValue().equalsIgnoreCase("Bonaire Sint Eustatius and Saba")){
            text.setStyle("-fx-font-size: 55");
        }
        newCase = String.format("%,d", convertInt(worldDataToday[2]));
        newDeaths = String.format("%,d", convertInt(worldDataToday[3]));
        totalCases = String.format("%,d", convertInt(worldDataToday[4]));
        totalDeaths = String.format("%,d", convertInt(worldDataToday[5]));

        percent1 = (convertDouble(worldDataToday[4]) - convertDouble(getWorldDataYesterday[4])) * 100 /convertDouble(getWorldDataYesterday[4]);
        percent2 = (convertDouble(worldDataToday[2]) - convertDouble(getWorldDataYesterday[2])) * 100 /convertDouble(getWorldDataYesterday[2]);
        percent3 = (convertDouble(worldDataToday[3]) - convertDouble(getWorldDataYesterday[3])) * 100 /convertDouble(getWorldDataYesterday[3]);
        percent4 = (convertDouble(worldDataToday[5]) - convertDouble(getWorldDataYesterday[5])) * 100 /convertDouble(getWorldDataYesterday[5]);

        if(Double.isNaN(percent1)) percent1 = 0;
        if(Double.isNaN(percent2)) percent2 = 0;
        if(Double.isNaN(percent3)) percent3 = 0;
        if(Double.isNaN(percent4)) percent4 = 0;

        diff1 = String.format("( %.2f",percent1);
        diff2 = String.format("( %.2f",percent2);
        diff3 = String.format("( %.2f",percent3);
        diff4 = String.format("( %.2f",percent4);

        t1.setStyle("-fx-fill: forestgreen");
        t2.setStyle("-fx-fill: forestgreen");
        t3.setStyle("-fx-fill: forestgreen");
        t4.setStyle("-fx-fill: forestgreen");

        if(percent1 > 0) {diff1 = String.format("( +%.2f",percent1); t1.setStyle("-fx-fill: red");}
        if(percent2 > 0) {diff2 = String.format("( +%.2f",percent2); t2.setStyle("-fx-fill: red");}
        if(percent3 > 0) {diff3 = String.format("( +%.2f",percent3); t3.setStyle("-fx-fill: red");}
        if(percent4 > 0) {diff4 = String.format("( +%.2f",percent4); t4.setStyle("-fx-fill: red");}

        lb1.setText(totalCases);
        lb2.setText(newCase);
        lb3.setText(totalDeaths);
        lb4.setText(newDeaths);
        text.setText(cb1.getValue());
        t1.setText(diff1+"% )");
        t2.setText(diff2+"% )");
        t3.setText(diff3+"% )");
        t4.setText(diff4+"% )");
    }

    @FXML
    public void mainMenuHandler(ActionEvent event) throws IOException {
        Parent chart = FXMLLoader.load(getClass().getResource("fxml/Menu.fxml"));
        Scene charScene = new Scene(chart);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(charScene);
        window.show();
    }
}
