package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;


public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane root;

 
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Simulaattori");

        initRootLayout();
        
        showPaneelit();

    }
    
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/RootLayout.fxml"));
            root = fxmlLoader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void showPaneelit() {
        try {
            // Ladataan Paneelit

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Paneelit.fxml"));
            AnchorPane paneelit = (AnchorPane) fxmlLoader.load();
            	                   
            // Sijoitetaan keskelle root-näkymää
            root.setCenter(paneelit);
            
            // Give the controller access to the main app.
            PaneelitController controller = fxmlLoader.getController();
            controller.setMainApp(this);
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
       
	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

    public static void main(String[] args) {
        launch(args);
    }
}