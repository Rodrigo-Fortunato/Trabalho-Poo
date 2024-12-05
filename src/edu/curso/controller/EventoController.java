package edu.curso.controller;

import edu.curso.entity.Evento;
import edu.curso.persistence.EventoDAO;
import edu.curso.persistence.ICRUDDAO;
import edu.curso.persistence.IDatabaseInitializer;
import edu.curso.persistence.InicializadorDBMySql;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.time.LocalDate;

public class EventoController {

    private ObservableList<Evento> listaEventos = FXCollections.observableArrayList();
    private LongProperty idEvento = new SimpleLongProperty();
    private StringProperty nomeEvento = new SimpleStringProperty("");
    private ObjectProperty<LocalDate> data = new SimpleObjectProperty<>(LocalDate.now());
    private StringProperty localEvento = new SimpleStringProperty("");
    private IntegerProperty capacidadeMaxima = new SimpleIntegerProperty(0);
    private StringProperty logradouro = new SimpleStringProperty("");
    private IntegerProperty numero = new SimpleIntegerProperty(0);
    private StringProperty bairro = new SimpleStringProperty("");
    private StringProperty cidade = new SimpleStringProperty("");
    private StringProperty estado = new SimpleStringProperty("");
    private StringProperty cep = new SimpleStringProperty("");
    private StringProperty complementoEndereco = new SimpleStringProperty("");
    private StringProperty descricaoEvento = new SimpleStringProperty("");


    private IDatabaseInitializer databaseInitializer;
    private ICRUDDAO eventoDAO;

    public EventoController() throws EventosException {
        this.databaseInitializer = new InicializadorDBMySql();
        this.eventoDAO = new EventoDAO();
        pesquisarTodos();
        limparTudo();
    }

    public void gravar() throws EventosException {
        Evento evento = toEntity();
        if (idEvento.getValue() == 0) {
            eventoDAO.inserir(evento);
            limparTudo();
            System.out.println("gravar"+evento.getDataEvento());
            System.out.println(Date.valueOf(evento.getDataEvento()));
        } else {
            eventoDAO.atualizar(evento);
            limparTudo();
            System.out.println("Atualizar"+evento.getDataEvento());
            System.out.println(Date.valueOf(evento.getDataEvento()));
        }
        pesquisarTodos();

    }

    public void excluir(Evento evento) throws EventosException {
        eventoDAO.remover(evento);
        limparTudo();
        pesquisarTodos();
    }

    public void pesquisarTodos() throws EventosException {
        listaEventos.clear();
        listaEventos.addAll(eventoDAO.pesquisarPor(""));
    }

    public void pesquisar() throws EventosException {
        listaEventos.clear();
        listaEventos.addAll(eventoDAO.pesquisarPor(nomeEvento.get()));
    }

    public Evento toEntity() {
        Evento evento = new Evento();

        evento.setNomeEvento(nomeEvento.get());
        evento.setDataEvento(data.get());
        evento.setLocalEvento(localEvento.get());
        evento.setCapacidadeMaxima(capacidadeMaxima.get());
        evento.setLogradouro(logradouro.get());
        evento.setNumero(numero.get());
        evento.setBairro(bairro.get());
        evento.setCidade(cidade.get());
        evento.setEstado(estado.get());
        evento.setCep(cep.get());
        evento.setComplementoEndereco(complementoEndereco.get());
        evento.setDescricaoEvento(descricaoEvento.get());


        return evento;
    }

    public void toBoundary(Evento evento) {
        if (evento != null) {
            idEvento.set(evento.getIdEvento());
            nomeEvento.set(evento.getNomeEvento());
            data.set(evento.getDataEvento());
            localEvento.set(evento.getLocalEvento());
            capacidadeMaxima.set(evento.getCapacidadeMaxima());
            logradouro.set(evento.getLogradouro());
            numero.set(evento.getNumero());
            bairro.set(evento.getBairro());
            cidade.set(evento.getCidade());
            estado.set(evento.getEstado());
            cep.set(evento.getCep());
            complementoEndereco.set(evento.getComplementoEndereco());
            descricaoEvento.set(evento.getDescricaoEvento());

        }
    }

    public void limparTudo() {
        idEvento.set(0);
        nomeEvento.set("");
        data.set(LocalDate.now());
        localEvento.set("");
        capacidadeMaxima.set(0);
        logradouro.set("");
        numero.set(0);
        bairro.set("");
        cidade.set("");
        estado.set("");
        cep.set("");
        complementoEndereco.set("");
        descricaoEvento.set("");
    }

    public ObservableList<Evento> getListaEventos() {
        return listaEventos;
    }


    public LongProperty idEventoProperty() {
        return idEvento;
    }


    public StringProperty nomeEventoProperty() {
        return nomeEvento;
    }


    public ObjectProperty<LocalDate> dataProperty() {
        return data;
    }


    public StringProperty localEventoProperty() {
        return localEvento;
    }


    public IntegerProperty capacidadeMaximaProperty() {
        return capacidadeMaxima;
    }


    public StringProperty logradouroProperty() {
        return logradouro;
    }


    public IntegerProperty numeroProperty() {
        return numero;
    }


    public StringProperty bairroProperty() {
        return bairro;
    }


    public StringProperty cidadeProperty() {
        return cidade;
    }


    public StringProperty estadoProperty() {
        return estado;
    }


    public StringProperty cepProperty() {
        return cep;
    }

    public StringProperty complementoEnderecoProperty() {
        return complementoEndereco;
    }

    public StringProperty descricaoEventoProperty() {
        return descricaoEvento;
    }
}
