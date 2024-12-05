package edu.curso.persistence;

import edu.curso.controller.EventosException;
import edu.curso.entity.Evento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO implements ICRUDDAO<Evento> {

    private final static String DB_CLASS = "org.mariadb.jdbc.Driver";
    private final static String DB_URL = "jdbc:mariadb://localhost:3306/eventosdb?allowPublicKeyRetrieval=true";
    private final static String DB_USER = "root";
    private final static String DB_PASS = "alunofatec";

    private Connection con = null;

    public EventoDAO() throws EventosException {
        try {
            Class.forName(DB_CLASS);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new EventosException(e);
        }
    }


    @Override
    public void inserir(Evento evento) throws EventosException {
        try {
            String sql = """
                    INSERT INTO evento (nome_evento, data,local,capacidade_max,
                    logradouro,numero,bairro,cidade,estado,cep,complemento,descricao_evento)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """;


            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, evento.getNomeEvento());
            preparedStatement.setDate(2, Date.valueOf(evento.getDataEvento()));
            preparedStatement.setString(3, evento.getLocalEvento());
            preparedStatement.setInt(4, evento.getCapacidadeMaxima());
            preparedStatement.setString(5, evento.getLogradouro());
            preparedStatement.setInt(6, evento.getNumero());
            preparedStatement.setString(7, evento.getBairro());
            preparedStatement.setString(8, evento.getCidade());
            preparedStatement.setString(9, evento.getEstado());
            preparedStatement.setString(10, evento.getCep());
            preparedStatement.setString(11, evento.getComplementoEndereco());
            preparedStatement.setString(12, evento.getDescricaoEvento());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new EventosException(e);
        }
    }

    @Override
    public void atualizar(Evento evento) throws EventosException {


        try {
            String sql = """
                    UPDATE evento SET nome_evento = ?,data = ?,local = ?,capacidade_max = ?,logradouro = ?,numero = ?,bairro = ?
                    ,cidade = ?,estado = ?,cep = ?,complemento = ?,descricao_evento = ? WHERE id_evento = ?
                    """;

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, evento.getNomeEvento());
            preparedStatement.setDate(2, Date.valueOf(evento.getDataEvento()));
            preparedStatement.setString(3, evento.getLocalEvento());
            preparedStatement.setInt(4, evento.getCapacidadeMaxima());
            preparedStatement.setString(5, evento.getLogradouro());
            preparedStatement.setInt(6, evento.getNumero());
            preparedStatement.setString(7, evento.getBairro());
            preparedStatement.setString(8, evento.getCidade());
            preparedStatement.setString(9, evento.getEstado());
            preparedStatement.setString(10, evento.getCep());
            preparedStatement.setString(11, evento.getComplementoEndereco());
            preparedStatement.setString(12, evento.getDescricaoEvento());
            preparedStatement.setLong(13, evento.getIdEvento());


            int i = preparedStatement.executeUpdate();
            System.out.println(i);

        } catch (SQLException e) {
            throw new EventosException(e);
        }


    }

    @Override
    public void remover(Evento evento) throws EventosException {

        try {
            String sql = "DELETE FROM evento WHERE id_evento = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1, evento.getIdEvento());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new EventosException(e);
        }


    }

    @Override
    public List<Evento> pesquisarPor(String nome) throws EventosException {
        List<Evento> eventos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM evento WHERE nome_evento LIKE ? ";


            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, "%" + nome + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Evento evento = new Evento();
                evento.setIdEvento(resultSet.getLong("id_evento"));
                evento.setNomeEvento(resultSet.getString("nome_evento"));
                evento.setDataEvento(resultSet.getDate("data").toLocalDate());
                evento.setLocalEvento(resultSet.getString("local"));
                evento.setCapacidadeMaxima(resultSet.getInt("capacidade_max"));
                evento.setLogradouro(resultSet.getString("logradouro"));
                evento.setNumero(resultSet.getInt("numero"));
                evento.setBairro(resultSet.getString("bairro"));
                evento.setCidade(resultSet.getString("cidade"));
                evento.setEstado(resultSet.getString("estado"));
                evento.setCep(resultSet.getString("cep"));
                evento.setComplementoEndereco(resultSet.getString("complemento"));
                evento.setDescricaoEvento(resultSet.getString("descricao_evento"));

                eventos.add(evento);
            }
        } catch (SQLException e) {
            throw new EventosException(e);
        }


        return eventos;
    }
}
