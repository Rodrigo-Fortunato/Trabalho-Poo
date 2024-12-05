package edu.curso.boundary;

import edu.curso.controller.EventosException;
import edu.curso.controller.ParticipanteController;
import edu.curso.entity.Evento;
import edu.curso.entity.Participante;
import edu.curso.persistence.EventoDAO;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

public class ParticipanteBoundary implements ITela {

    private Label lblId = new Label("");
    private TextField txtNomeParticipante = new TextField();
    private TextField txtCpf = new TextField();
    private TextField txtTelefoneParticipante = new TextField();
    private TextField txtEmailParticipante = new TextField();
    private ComboBox<String> cbTipoIngresso = new ComboBox<>();
//    private ComboBox<Evento> cbEventos = new ComboBox<>();
    private TableView<Participante> tableParticipantes = new TableView<>();

    private ParticipanteController participanteController;
    @Override
    public Pane render() {


        try {
            participanteController = new ParticipanteController();
        } catch (EventosException e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao iniciar o sistema", ButtonType.OK).showAndWait();
        }

        BorderPane paneParticipante = new BorderPane();
        GridPane paneForm = new GridPane();
        Button btnGravar = new Button("Gravar");
        Button btnPesquisar = new Button("Pesquisar");

        btnGravar.setOnAction(e -> {
            try {
                participanteController.gravar();
            } catch (EventosException ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao gravar o conteudo", ButtonType.OK).showAndWait();
            }
            tableParticipantes.refresh();
        });

        btnPesquisar.setOnAction(e -> {
            try {
                participanteController.pesquisar();
            } catch (EventosException err) {
                new Alert(Alert.AlertType.ERROR, "Erro ao pesquisar por nome", ButtonType.OK).showAndWait();
            }
        });

        Button btnLimpar = new Button("Limpar Campos");
        btnLimpar.setOnAction(e -> participanteController.limparTudo());



        paneForm.add(lblId,0,0);
        paneForm.add(new Label("Nome:"),0,1);
        paneForm.add(txtNomeParticipante,1,1);
        paneForm.add(new Label("CPF:"),0,2);
        paneForm.add(txtCpf,1,2);
        paneForm.add(new Label("Telefone:"),0,3);
        paneForm.add(txtTelefoneParticipante,1,3);
        paneForm.add(new Label("Email:"),0,4);
        paneForm.add(txtEmailParticipante,1,4);
        paneForm.add(new Label("Tipo de Ingresso:"),0,5);
        paneForm.add(cbTipoIngresso,1,5);
        paneForm.add(btnLimpar,2,0);
//        paneForm.add(new Label("Selecionar Evento:"),0,6);
//        paneForm.add(cbTipoIngresso,1,6);
        paneParticipante.setCenter(tableParticipantes);

//        try {
//            ObservableList<Evento> eventos = FXCollections.observableArrayList(new EventoDAO().pesquisarPor(""));
//            cbEventos.setItems(eventos);
//        } catch (EventosException e) {
//            throw new RuntimeException(e);
//        }

        ObservableList<String> ingressos = FXCollections.observableArrayList("COMUM","VIP","MEIA");
        cbTipoIngresso.setItems(ingressos);

        paneForm.add(btnGravar,1,6);
        paneForm.add(btnPesquisar,2,6);

        ligacoes();
        gerarColunas();


        paneParticipante.setTop(paneForm);
        paneParticipante.setCenter(tableParticipantes);

        return paneParticipante;
    }

    private void gerarColunas() {
        TableColumn<Participante, Long> column1 = new TableColumn<>("id_Participante");
        column1.setCellValueFactory(new PropertyValueFactory<Participante, Long>("idParticipante"));

        TableColumn<Participante, String> column2 = new TableColumn<>("Nome_Participante");
        column2.setCellValueFactory(new PropertyValueFactory<Participante, String>("nomeParticipante"));

        TableColumn<Participante, String> column3 = new TableColumn<>("CPF");
        column3.setCellValueFactory(new PropertyValueFactory<Participante, String>("cpf"));

        TableColumn<Participante, String> column4 = new TableColumn<>("Telefone_Participante");
        column4.setCellValueFactory(new PropertyValueFactory<Participante, String>("telefoneParticipante"));

        TableColumn<Participante, String> column5 = new TableColumn<>("Email_Participante");
        column5.setCellValueFactory(new PropertyValueFactory<Participante, String>("emailParticipante"));

        TableColumn<Participante, String> column6 = new TableColumn<>("Tipo_Ingresso");
        column6.setCellValueFactory(new PropertyValueFactory<Participante, String>("tipoIngresso"));

        TableColumn<Participante, String> column7 = new TableColumn<>("Evento");
        column7.setCellValueFactory(new PropertyValueFactory<Participante, String>("Evento"));

        tableParticipantes.getSelectionModel().selectedItemProperty().addListener((obs,antigo,novo) ->{
            if ((novo != null)){
                participanteController.toBoundary(novo);
            }
        });
        Callback<TableColumn<Participante,Void>,TableCell<Participante,Void>> cb = new Callback<>() {

            @Override
            public TableCell<Participante, Void> call(TableColumn<Participante, Void> param) {
                TableCell<Participante,Void> cell = new TableCell<>() {

                    final Button btnApagar = new Button("Apagar");

                    {
                        btnApagar.setOnAction(e -> {
                            Participante participante = tableParticipantes.getItems().get(getIndex());

                            try {
                                participanteController.excluir(participante);
                            } catch (EventosException ex) {
                                new Alert(Alert.AlertType.ERROR, "Erro ao excluir o conteudo ", ButtonType.OK).showAndWait();
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        if (!empty) {
                            setGraphic(btnApagar);
                        } else {
                            setGraphic(null);
                        }
                    }
                };


                return cell;
            }

        };

        TableColumn<Participante, Void> column8 = new TableColumn<>("Ação");
        column8.setCellFactory(cb);

        tableParticipantes.getColumns().addAll(column1, column2, column3, column4, column5, column6,column7,column8);
        tableParticipantes.setItems(participanteController.getListaParticipantes());





    }

    private void ligacoes() {
        participanteController.idParticipanteProperty().addListener((obs,antigo,novo) ->{
            lblId.setText(String.valueOf(novo));
        });
        IntegerStringConverter integerConverter = new IntegerStringConverter();

        Bindings.bindBidirectional(participanteController.nomeParticipanteProperty(),txtNomeParticipante.textProperty());
        Bindings.bindBidirectional(participanteController.cpfProperty(),txtCpf.textProperty());
        Bindings.bindBidirectional(participanteController.telefoneParticipanteProperty(),txtTelefoneParticipante.textProperty());
        Bindings.bindBidirectional(participanteController.emailParticipanteProperty(),txtEmailParticipante.textProperty());
        Bindings.bindBidirectional(participanteController.tipoIngressoProperty(),cbTipoIngresso.valueProperty());
//        Bindings.bindBidirectional(participanteController.,cbEventos.valueProperty());

    }
}
