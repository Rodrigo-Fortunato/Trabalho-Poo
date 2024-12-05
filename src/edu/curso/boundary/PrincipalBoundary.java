package edu.curso.boundary;

import edu.curso.persistence.IDatabaseInitializer;
import edu.curso.persistence.InicializadorDBMySql;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class PrincipalBoundary extends Application {

    private Map<String, ITela> telas = new HashMap<>();


    @Override
    public void start(Stage stage) throws Exception {
        telas.put("evento", new EventoBoundary());
        telas.put("participante", new ParticipanteBoundary());

        BorderPane panePrincipal = new BorderPane();

        Label lblTelaPrincipal = new Label("Bem Vindo");
        lblTelaPrincipal.setStyle("-fx-font-size: 35px;");
        panePrincipal.setCenter(lblTelaPrincipal);

        MenuBar menuBar = new MenuBar();
        Menu menuCadastro = new Menu("Cadastro");

        MenuItem menuItemEvento = new MenuItem("Evento");
        menuItemEvento.setOnAction(e -> panePrincipal.setCenter(telas.get("evento").render()));

        MenuItem menuItemParticipante = new MenuItem("Participante");
        menuItemParticipante.setOnAction(e -> panePrincipal.setCenter(telas.get("participante").render()));

        menuCadastro.getItems().addAll(menuItemEvento, menuItemParticipante);
        menuBar.getMenus().add(menuCadastro);
        panePrincipal.setTop(menuBar);
        Scene scene = new Scene(panePrincipal, 800, 600);

        stage.setScene(scene);
        stage.setTitle("Sistema de Eventos");
        stage.show();

    }

    public static void main(String[] args) {
        launch(PrincipalBoundary.class,args);
    }
}
