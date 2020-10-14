package dad.javafx.cambiodivisa;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Cambio extends Application {
	
	private Stage primaryStage;
	
	private Button Boton;
	private TextField MonedaOrigen;
	private TextField MonedaCambio;
	private ComboBox<Divisa> DestinoOrigen;
	private ComboBox<Divisa> DestinoCambio;
	
	private Divisa euro = new Divisa("Euro", 1.0);
	private Divisa libra = new Divisa("Libra", 0.9);
	private Divisa dolar = new Divisa("Dolar", 1.17);
	private Divisa yen = new Divisa("Yen", 124.17);
	
	private Divisa[] _divisa = {euro, libra, dolar, yen};
	
	private void onCambiarAction(ActionEvent e) {
		try {
			Double origen = Double.parseDouble(MonedaOrigen.getText());
			Divisa divisaOrigen = DestinoOrigen.getSelectionModel().getSelectedItem();
			Divisa divisaCambio = DestinoCambio.getSelectionModel().getSelectedItem();
			
			Double cantidadDestino = divisaCambio.fromEuro(divisaOrigen.toEuro(origen));
			
			MonedaCambio.setText(cantidadDestino.toString());
		} catch (NumberFormatException ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(primaryStage);
			alert.setTitle("Error");
			alert.setHeaderText("Debe introducir la cantidad de origen.");
			alert.setContentText(ex.getMessage());
			
			alert.showAndWait();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		
		MonedaOrigen = new TextField("0");
		MonedaOrigen.setPrefColumnCount(4);
		MonedaOrigen.setPromptText("Indique el valor de la divisa");
		MonedaOrigen.setMaxWidth(100.0);
		
		MonedaCambio = new TextField();
		MonedaCambio.setPrefColumnCount(4);
		MonedaCambio.setMaxWidth(100.0);
		MonedaCambio.setEditable(false);
		
		DestinoOrigen = new ComboBox<Divisa>();
		DestinoOrigen.getItems().addAll(_divisa);
		DestinoOrigen.getSelectionModel().selectFirst();
		
		DestinoCambio = new ComboBox<Divisa>();
		DestinoCambio.getItems().addAll(_divisa);
		DestinoCambio.getSelectionModel().selectFirst();
		
		Boton = new Button();
		Boton.setText("Cambiar");
		Boton.setDefaultButton(true);
		Boton.setOnAction(e -> onCambiarAction(e));
		
		HBox hbOrigen = new HBox();
		hbOrigen.setSpacing(1);
		hbOrigen.setAlignment(Pos.BASELINE_CENTER);
		hbOrigen.getChildren().addAll(MonedaOrigen, DestinoOrigen);
		
		HBox hbDivisa = new HBox();
		hbDivisa.setSpacing(1);
		hbDivisa.setAlignment(Pos.BASELINE_CENTER);
		hbDivisa.getChildren().addAll(MonedaCambio, DestinoCambio);
		
		VBox root = new VBox();
		root.setSpacing(5);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(hbOrigen, hbDivisa, Boton);
		
		Scene escena = new Scene(root, 320, 200);
		
		primaryStage.setScene(escena);
		primaryStage.setTitle("Cambio de divisa");
		primaryStage.show();
	}	

	public static void main(String[] args) {
		launch(args);
	}

}