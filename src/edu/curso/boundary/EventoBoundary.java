package edu.curso.boundary;

import edu.curso.controller.EventoController;
import edu.curso.controller.EventosException;
import edu.curso.entity.Evento;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.time.LocalDate;
import java.util.Date;


public class EventoBoundary implements ITela {


    private Label lblId = new Label("");
    private TextField txtNomeEvento = new TextField();
    private DatePicker dataEvento = new DatePicker();
    private TextField txtLocalEvento = new TextField();
    private TextField txtCapacidadeMaxima = new TextField();
    private TextField txtLogradouro = new TextField();
    private TextField txtNumero = new TextField();
    private TextField txtBairro = new TextField();
    private TextField txtCidade = new TextField();
    private ComboBox<String> cbEstado = new ComboBox<>();
    private TextField txtCep = new TextField();
    private TextField txtComplementoEndereco = new TextField();
    private TextField txtDescricaoEvento = new TextField();
    private TableView<Evento> tableEvento = new TableView<>();
    private EventoController eventoController;

    @Override
    public Pane render() {

        try {
            eventoController = new EventoController();
        } catch (EventosException e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao iniciar o sistema", ButtonType.OK).showAndWait();
        }

        BorderPane paneEvento = new BorderPane();
        GridPane paneForm = new GridPane();
        Button btnGravar = new Button("Gravar");
        Button btnPesquisar = new Button("Pesquisar");
        btnGravar.setOnAction(e -> {
            try {
                eventoController.gravar();
            } catch (EventosException ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao gravar o conteudo", ButtonType.OK).showAndWait();
            }
            tableEvento.refresh();
        });
        btnPesquisar.setOnAction(e -> {
            try {
                eventoController.pesquisar();
            } catch (EventosException err) {
                new Alert(Alert.AlertType.ERROR, "Erro ao pesquisar por nome", ButtonType.OK).showAndWait();
            }
        });
        Button btnLimpar = new Button("Limpar Campos");
        btnLimpar.setOnAction(e -> eventoController.limparTudo());


        paneForm.add(lblId, 0, 0);
        paneForm.add(new Label("Nome:"), 0, 1);
        paneForm.add(txtNomeEvento, 1, 1);
        paneForm.add(new Label("Data Evento:"), 0, 2);
        paneForm.add(dataEvento, 1, 2);
        paneForm.add(new Label("Local:"), 0, 3);
        paneForm.add(txtLocalEvento, 1, 3);
        paneForm.add(new Label("Capacidade Maxima:"), 0, 4);
        paneForm.add(txtCapacidadeMaxima, 1, 4);
        paneForm.add(new Label("Logradouro:"), 0, 5);
        paneForm.add(txtLogradouro, 1, 5);
        paneForm.add(new Label("Número:"), 0, 6);
        paneForm.add(txtNumero, 1, 6);
        paneForm.add(new Label("Bairro:"), 0, 7);
        paneForm.add(txtBairro, 1, 7);
        paneForm.add(new Label("Cidade:"), 0, 8);
        paneForm.add(txtCidade, 1, 8);
        paneForm.add(new Label("Estado:"), 0, 9);
        paneForm.add(cbEstado, 1, 9);
        paneForm.add(new Label("Cep"), 0, 10);
        paneForm.add(txtCep, 1, 10);
        paneForm.add(new Label("Complemento"), 0, 11);
        paneForm.add(txtComplementoEndereco, 1, 11);
        paneForm.add(new Label("Descrição do Evento"), 0, 12);
        paneForm.add(txtDescricaoEvento, 1, 12);
        paneForm.add(btnLimpar,2,0);


        ObservableList<String> estados = FXCollections.observableArrayList(  "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA",
                "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN",
                "RS", "RO", "RR", "SC", "SP", "SE", "TO");
        cbEstado.setItems(estados);

        paneForm.add(btnGravar, 1, 13);
        paneForm.add(btnPesquisar, 2, 13);

        ligacoes();
        gerarColunas();

        paneEvento.setTop(paneForm);
        paneEvento.setCenter(tableEvento);

        return paneEvento;
    }

    private void gerarColunas() {
        TableColumn<Evento, Long> column1 = new TableColumn<>("id_Evento");
        column1.setCellValueFactory(new PropertyValueFactory<Evento, Long>("idEvento"));

        TableColumn<Evento, String> column2 = new TableColumn<>("Nome_Evento");
        column2.setCellValueFactory(new PropertyValueFactory<Evento, String>("nomeEvento"));

        TableColumn<Evento, LocalDate> column3 = new TableColumn<>("Data_Evento");
        column3.setCellValueFactory(new PropertyValueFactory<Evento, LocalDate>("dataEvento"));

        TableColumn<Evento, String> column4 = new TableColumn<>("Local_Evento");
        column4.setCellValueFactory(new PropertyValueFactory<Evento, String>("localEvento"));

        TableColumn<Evento, Integer> column5 = new TableColumn<>("Capacidade_Maxima");
        column5.setCellValueFactory(new PropertyValueFactory<Evento, Integer>("capacidadeMaxima"));

        TableColumn<Evento, String> column6 = new TableColumn<>("Logradouro");
        column6.setCellValueFactory(new PropertyValueFactory<Evento, String>("logradouro"));

        TableColumn<Evento, Integer> column7 = new TableColumn<>("Numero");
        column7.setCellValueFactory(new PropertyValueFactory<Evento, Integer>("numero"));

        TableColumn<Evento, String> column8 = new TableColumn<>("Bairro");
        column8.setCellValueFactory(new PropertyValueFactory<Evento, String>("bairro"));

        TableColumn<Evento, String> column9 = new TableColumn<>("Cidade");
        column9.setCellValueFactory(new PropertyValueFactory<Evento, String>("cidade"));

        TableColumn<Evento, String> column10 = new TableColumn<>("Estado");
        column10.setCellValueFactory(new PropertyValueFactory<Evento,String>("estado"));

        TableColumn<Evento, String> column11 = new TableColumn<>("CEP");
        column11.setCellValueFactory(new PropertyValueFactory<Evento, String>("cep"));

        TableColumn<Evento, String> column12 = new TableColumn<>("Complemento_Endereço");
        column12.setCellValueFactory(new PropertyValueFactory<Evento, String>("complementoEndereco"));

        TableColumn<Evento, String> column13 = new TableColumn<>("Descriçao_Evento");
        column13.setCellValueFactory(new PropertyValueFactory<Evento, String>("descricaoEvento"));

        tableEvento.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                eventoController.toBoundary(novo);
            }
        });

        Callback<TableColumn<Evento, Void>, TableCell<Evento, Void>> cb = new Callback<>() {

            @Override
            public TableCell<Evento, Void> call(TableColumn<Evento, Void> param) {
                TableCell<Evento, Void> cell = new TableCell<>() {
                    final Button btnApagar = new Button("Apagar");

                    {
                        btnApagar.setOnAction(e -> {
                            Evento evento = tableEvento.getItems().get(getIndex());
                            try {
                                eventoController.excluir(evento);
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
        TableColumn<Evento, Void> column14 = new TableColumn<>("Ação");
        column14.setCellFactory(cb);

        tableEvento.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14);
        tableEvento.setItems(eventoController.getListaEventos());
    }

    private void ligacoes() {
        eventoController.idEventoProperty().addListener((obs, antigo, novo) -> {
            lblId.setText(String.valueOf(novo));
        });
        IntegerStringConverter integerConverter = new IntegerStringConverter();
        Bindings.bindBidirectional(eventoController.nomeEventoProperty(),txtNomeEvento.textProperty());
        Bindings.bindBidirectional(eventoController.dataProperty(),dataEvento.valueProperty());
        Bindings.bindBidirectional(eventoController.localEventoProperty(),txtLocalEvento.textProperty());
        Bindings.bindBidirectional(txtCapacidadeMaxima.textProperty(),eventoController.capacidadeMaximaProperty(),(StringConverter) integerConverter);
        Bindings.bindBidirectional(eventoController.logradouroProperty(),txtLogradouro.textProperty());
        Bindings.bindBidirectional(txtNumero.textProperty(),eventoController.numeroProperty(),(StringConverter) integerConverter);
        Bindings.bindBidirectional(eventoController.bairroProperty(),txtBairro.textProperty());
        Bindings.bindBidirectional(eventoController.cidadeProperty(),txtCidade.textProperty());
        Bindings.bindBidirectional(eventoController.estadoProperty(),cbEstado.valueProperty());
        Bindings.bindBidirectional(eventoController.cepProperty(),txtCep.textProperty());
        Bindings.bindBidirectional(eventoController.complementoEnderecoProperty(),txtComplementoEndereco.textProperty());
        Bindings.bindBidirectional(eventoController.descricaoEventoProperty(),txtDescricaoEvento.textProperty());

    }
}
