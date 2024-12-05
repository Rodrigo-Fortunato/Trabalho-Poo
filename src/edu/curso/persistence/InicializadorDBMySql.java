package edu.curso.persistence;

import edu.curso.controller.EventosException;

import java.sql.*;

public class InicializadorDBMySql implements  IDatabaseInitializer{

    private final static String DB_CLASS = "org.mariadb.jdbc.Driver";
    private final static String DB_URL = "jdbc:mariadb://localhost:3306/?";
    private final static String DB_USER = "root";
    private final static String DB_PASS = "alunofatec";

    public InicializadorDBMySql() throws EventosException {
        try {
            initializeDatabase();
            initializeTables();
        } catch (EventosException e) {
            throw new EventosException(e);
        }
    }

    @Override
    public void initializeDatabase() throws EventosException {
        try {
            String sqlCreateDB= "CREATE DATABASE IF NOT EXISTS eventosdb ";
            Class.forName(DB_CLASS);
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);

            PreparedStatement preparedStatement = con.prepareStatement(sqlCreateDB);
            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            throw new EventosException(e);
        }


    }

    @Override
    public void initializeTables() throws EventosException {
        String sqlCreateTableEvento = """
            CREATE TABLE IF NOT EXISTS evento(
            id_evento INT AUTO_INCREMENT PRIMARY KEY,
            nome_evento VARCHAR(100) NOT NULL,
            data DATE NOT NULL,
            local VARCHAR(30) NOT NULL,
            capacidade_max INT NOT NULL,
            logradouro VARCHAR(30) NOT NULL,
            numero INT NOT NULL,
            bairro VARCHAR(30) NOT NULL,
            cidade VARCHAR(30) NOT NULL,
            estado VARCHAR(2) NOT NULL,
            cep VARCHAR(8) NOT NULL,
            complemento VARCHAR(50),
            descricao_evento VARCHAR(300) NOT NULL);
            """;

        String sqlCreateTableParticipante = """
                CREATE TABLE IF NOT EXISTS participante(
                id_participante INT AUTO_INCREMENT PRIMARY KEY,
                nome_participante VARCHAR(100) NOT NULL,
                cpf VARCHAR(14) NOT NULL,
                telefone VARCHAR(11) NOT NULL,
                email VARCHAR(100) NOT NULL,
                tipo_ingresso VARCHAR(5) NOT NULL,
                id_evento INT);
                """;
        try {
            Class.forName(DB_CLASS);
            Connection con = DriverManager.getConnection(DB_URL.replace("?","eventosdb"),DB_USER,DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement(sqlCreateTableEvento);
            preparedStatement.executeUpdate();

            preparedStatement = con.prepareStatement(sqlCreateTableParticipante);
            preparedStatement.executeUpdate();


        } catch (SQLException | ClassNotFoundException e) {
            throw new EventosException(e);
        }

    }
}
