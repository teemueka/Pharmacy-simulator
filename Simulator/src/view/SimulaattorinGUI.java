package view;


import java.text.DecimalFormat;
import controller.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;



public class SimulaattorinGUI extends Application implements ISimulaattorinUI {

    //Kontrollerin esittely (tarvitaan käyttöliittymässä)
    private IKontrolleriForV kontrolleri;

    // Käyttöliittymäkomponentit:

    private Spinner a_staff;
    private Spinner h_staff;
    private Spinner r_staff;
    private Spinner k_staff;
    private TextField aika;
    private TextField viive;
    private Label tulos;
    private Label palveltu;
    private Label menetetty;
    private Label tyytyvaisyys;
    private Label average;
    private Label tulot;
    private Label menot;
    private Label aikaLabel;
    private Label viiveLabel;
    private Label tulosLabel;
    private Label palveltuLabel;
    private Label menetettyLabel;
    private Label tyytyvaisyysLabel;
    private Label averageLabel;
    private Label tulotLabel;
    private Label menotLabel;

    private Button kaynnistaButton;
    private Button hidastaButton;
    private Button nopeutaButton;

    private IVisualisointi naytto;
    private Image image = new Image("file:Simulator/Logo.png");


    @Override
    public void init() {

        Trace.setTraceLevel(Level.INFO);

        kontrolleri = new Kontrolleri(this);
    }

    @Override
    public void start(Stage primaryStage) {
        // Käyttöliittymän rakentaminen
        try {

            naytto = new Visualisointi(800, 400);


            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    Platform.exit();
                    System.exit(0);
                }
            });


            primaryStage.setTitle("Simulaattori");
            primaryStage.getIcons().add(image);


            kaynnistaButton = new Button();
            kaynnistaButton.setText("Käynnistä simulointi");
            kaynnistaButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    kontrolleri.kaynnistaSimulointi();
                    kaynnistaButton.setDisable(true);

                }
            });

            a_staff = new Spinner(1, 99, 1);
            h_staff = new Spinner(1, 99, 1);
            r_staff = new Spinner(1, 99, 1);
            k_staff = new Spinner(1, 99, 1);


            hidastaButton = new Button();
            hidastaButton.setText("Hidasta");
            hidastaButton.setOnAction(e -> kontrolleri.hidasta());

            nopeutaButton = new Button();
            nopeutaButton.setText("Nopeuta");
            nopeutaButton.setOnAction(e -> kontrolleri.nopeuta());

            aikaLabel = new Label("Simulointiaika:");
            aikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            aika = new TextField("Syötä aika");
            aika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            aika.setPrefWidth(150);

            viiveLabel = new Label("Viive:");
            viiveLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            viive = new TextField("1");
            viive.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            viive.setPrefWidth(150);

            tulosLabel = new Label("Kokonaisaika:");
            tulosLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            tulos = new Label();
            tulos.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            tulos.setPrefWidth(150);

            palveltuLabel = new Label("Palveltu:");
            palveltuLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            palveltu = new Label();
            palveltu.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            palveltu.setPrefWidth(150);

            menetettyLabel = new Label("Menetetty:");
            menetettyLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            menetetty = new Label();
            menetetty.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            menetetty.setPrefWidth(150);

            tyytyvaisyysLabel = new Label("Tyytyväisyys:");
            tyytyvaisyysLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            tyytyvaisyys = new Label();
            tyytyvaisyys.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            tyytyvaisyys.setPrefWidth(150);

            averageLabel = new Label("Ajan keskiarvo:");
            averageLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            average = new Label();
            average.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            average.setPrefWidth(150);

            tulotLabel = new Label("Tuotettu raha:");
            tulotLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            tulot = new Label();
            tulot.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            tulot.setPrefWidth(150);

            menotLabel = new Label("Menetetty raha:");
            menotLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            menot = new Label();
            menot.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            menot.setPrefWidth(150);


            VBox staff_bar = new VBox();
            staff_bar.setPadding(new Insets(10, 10, 10, 10)); // marginaalit ylÃ¤, oikea, ala, vasen
            staff_bar.setSpacing(10);   // komponenttien vÃ¤limatka 10 pikseliÃ¤

            staff_bar.getChildren().addAll(new Label("Apteekin henkilökunta"), a_staff, new Label("Hyllyjen henkilökunta"), h_staff, new Label("Reseptin henkilökunta"), r_staff, new Label("Kassan henkilökunta"), k_staff);

            HBox hBox = new HBox();
            hBox.setPadding(new Insets(15, 12, 15, 12)); // marginaalit ylÃ¤, oikea, ala, vasen
            hBox.setSpacing(10);   // noodien välimatka 10 pikseliä

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setVgap(10);
            grid.setHgap(5);

            grid.add(aikaLabel, 0, 0);   // sarake, rivi
            grid.add(aika, 1, 0);          // sarake, rivi
            grid.add(viiveLabel, 0, 1);      // sarake, rivi
            grid.add(viive, 1, 1);           // sarake, rivi
            grid.add(tulosLabel, 0, 2);      // sarake, rivi
            grid.add(tulos, 1, 2);           // sarake, rivi
            grid.add(palveltuLabel, 0, 3);      // sarake, rivi
            grid.add(palveltu, 1, 3);           // sarake, rivi
            grid.add(menetettyLabel, 0, 4);      // sarake, rivi
            grid.add(menetetty, 1, 4);           // sarake, rivi
            grid.add(tyytyvaisyysLabel, 0, 5);
            grid.add(tyytyvaisyys, 1, 5);
            grid.add(averageLabel, 0, 6);
            grid.add(average, 1, 6);
            grid.add(tulotLabel, 0, 7);
            grid.add(tulot, 1, 7);
            grid.add(menotLabel, 0, 8);
            grid.add(menot, 1, 8);
            grid.add(kaynnistaButton, 0, 9);  // sarake, rivi
            grid.add(nopeutaButton, 0, 10);   // sarake, rivi
            grid.add(hidastaButton, 1, 10);   // sarake, rivi



            // TÃ¤ytetÃ¤Ã¤n boxi:
            hBox.getChildren().addAll(staff_bar, grid, (Canvas) naytto);

            Scene scene = new Scene(hBox);
            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Käyttöliittymän rajapintametodit (kutsutaan kontrollerista)

    @Override
    public double getAika() {
        return Double.parseDouble(aika.getText());
    }

    @Override
    public long getViive() {
        return Long.parseLong(viive.getText());
    }

    @Override
    public int getA_staff() {
        return (int) a_staff.getValue();
    }

    @Override
    public int getH_staff() {
        return (int) h_staff.getValue();
    }

    @Override
    public int getR_staff() {
        return (int) r_staff.getValue();
    }

    @Override
    public int getK_staff() {
        return (int) k_staff.getValue();
    }


    @Override
    public void setLoppuaika(double aika) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.tulos.setText(formatter.format(aika));
    }

    @Override
    public void setPalveltu(int asiakas) {
        this.palveltu.setText(Integer.toString(asiakas));
    }

    @Override
    public void setMenetetty(int asiakas) {
        this.menetetty.setText(Integer.toString(asiakas));
    }





    @Override
    public IVisualisointi getVisualisointi() {
        return naytto;
    }
}




