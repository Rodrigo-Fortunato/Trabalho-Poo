package edu.curso.controller;

import edu.curso.entity.Participante;
import edu.curso.persistence.ICRUDDAO;
import edu.curso.persistence.IDatabaseInitializer;
import edu.curso.persistence.InicializadorDBMySql;
import edu.curso.persistence.ParticipanteDAO;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ParticipanteController {

    private ObservableList<Participante> listaParticipantes = FXCollections.observableArrayList();
    private LongProperty idParticipante = new SimpleLongProperty();
    private StringProperty nomeParticipante = new SimpleStringProperty("");
    private StringProperty cpf = new SimpleStringProperty("");
    private StringProperty telefoneParticipante = new SimpleStringProperty("");
    private StringProperty emailParticipante = new SimpleStringProperty("");
    private StringProperty tipoIngresso = new SimpleStringProperty("");

    private IDatabaseInitializer databaseInitializer;
    private ICRUDDAO participanteDAO;

    public ParticipanteController() throws EventosException {
        this.databaseInitializer = new InicializadorDBMySql();
            this.participanteDAO = new ParticipanteDAO();
            pesquisarTodos();
            limparTudo();

    }

    public void gravar() throws EventosException {
        Participante participante = toEntity();
        if (idParticipante.getValue() == 0) {
            participanteDAO.inserir(participante);
            limparTudo();
        } else {
            participanteDAO.atualizar(participante);
            limparTudo();
        }
        pesquisarTodos();


    }


    public void excluir(Participante participante) throws EventosException {
        participanteDAO.remover(participante);
        limparTudo();
        pesquisarTodos();

    }

    private void pesquisarTodos() throws EventosException {
        listaParticipantes.clear();
        listaParticipantes.addAll(participanteDAO.pesquisarPor(nomeParticipante.get()));
    }
    public void pesquisar() throws EventosException{
        listaParticipantes.clear();
        listaParticipantes.addAll(participanteDAO.pesquisarPor(nomeParticipante.get()));
    }

    public Participante toEntity() {
        Participante participante = new Participante();

        participante.setIdParticipante(idParticipante.get());
        participante.setNomeParticipante(nomeParticipante.get());
        participante.setCpf(cpf.get());
        participante.setTelefoneParticipante(telefoneParticipante.get());
        participante.setEmailParticipante(emailParticipante.get());
        participante.setTipoIngresso(tipoIngresso.get());


        return participante;
    }

    public void toBoundary(Participante participante) {
        if (participante != null) {
            idParticipante.set(participante.getIdParticipante());
            nomeParticipante.set(participante.getNomeParticipante());
            cpf.set(participante.getCpf());
            telefoneParticipante.set(participante.getTelefoneParticipante());
            emailParticipante.set(participante.getEmailParticipante());
            tipoIngresso.set(participante.getTipoIngresso());
        }
    }


    public void limparTudo() {
        idParticipante.set(0);
        nomeParticipante.set("");
        cpf.set("");
        telefoneParticipante.set("");
        emailParticipante.set("");
        tipoIngresso.set("");
    }


    public ObservableList<Participante> getListaParticipantes() {
        return listaParticipantes;
    }


    public LongProperty idParticipanteProperty() {
        return idParticipante;
    }


    public StringProperty nomeParticipanteProperty() {
        return nomeParticipante;
    }


    public StringProperty cpfProperty() {
        return cpf;
    }


    public StringProperty telefoneParticipanteProperty() {
        return telefoneParticipante;
    }


    public StringProperty emailParticipanteProperty() {
        return emailParticipante;
    }


    public StringProperty tipoIngressoProperty() {
        return tipoIngresso;
    }
}
