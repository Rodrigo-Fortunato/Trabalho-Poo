package edu.curso.persistence;

import edu.curso.controller.EventosException;
import edu.curso.entity.Evento;
import edu.curso.entity.Participante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipanteDAO implements ICRUDDAO<Participante> {
    private final static String DB_CLASS = "org.mariadb.jdbc.Driver";
    private final static String DB_URL = "jdbc:mariadb://localhost:3306/eventosdb?allowPublicKeyRetrieval=true";
    private final static String DB_USER = "root";
    private final static String DB_PASS = "alunofatec";

    private Connection con = null;

    public ParticipanteDAO() throws EventosException {
        try {
            Class.forName(DB_CLASS);
            this.con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new EventosException(e);
        }
    }

    @Override
    public void inserir(Participante participante) throws EventosException {
        try {
            String sql = """
                    INSERT INTO participante (nome_participante,cpf,telefone,email,tipo_ingresso)
                    VALUES (?,?,?,?,?)
                    """;


            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,participante.getNomeParticipante());
            preparedStatement.setString(2,participante.getCpf());
            preparedStatement.setString(3,participante.getTelefoneParticipante());
            preparedStatement.setString(4,participante.getEmailParticipante());
            preparedStatement.setString(5,participante.getTipoIngresso());
//            preparedStatement.setLong(6,participante.getEvento().getIdEvento());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new EventosException(e);
        }

    }

    @Override
    public void atualizar(Participante participante) throws EventosException {


        try {
            String sql = """
                UPDATE participante SET nome_participante =? , cpf =?, telefone =?,email =?,tipo_ingresso =?
                WHERE id_participante = ?
                """;

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,participante.getNomeParticipante());
            preparedStatement.setString(2,participante.getCpf());
            preparedStatement.setString(3,participante.getTelefoneParticipante());
            preparedStatement.setString(4,participante.getEmailParticipante());
            preparedStatement.setString(5,participante.getTipoIngresso());
            preparedStatement.setLong(6,participante.getIdParticipante());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new EventosException(e);
        }

    }

    @Override
    public void remover(Participante participante) throws EventosException {


        try {
            String sql = "DELETE FROM participante WHERE id_participante = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1,participante.getIdParticipante());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new EventosException(e);
        }


    }

    @Override
    public List<Participante> pesquisarPor(String nome) throws EventosException {
        List<Participante> participantes = new ArrayList<>();

        try {
            String sql = """
                    SELECT * FROM participante WHERE nome_participante LIKE ?
                    """;

            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1,"%"+nome+"%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Participante participante = new Participante();
//                Evento evento = new Evento();
//
//                evento.setIdEvento(resultSet.getLong("id_evento"));
//                evento.setNomeEvento(resultSet.getString("nome_evento"));
//                evento.setDataEvento(resultSet.getDate("data").toLocalDate());
//                evento.setLocalEvento(resultSet.getString("local"));
//                evento.setCapacidadeMaxima(resultSet.getInt("capacidade_max"));
//                evento.setLogradouro(resultSet.getString("logradouro"));
//                evento.setNumero(resultSet.getInt("numero"));
//                evento.setBairro(resultSet.getString("bairro"));
//                evento.setCidade(resultSet.getString("cidade"));
//                evento.setEstado(resultSet.getString("estado"));
//                evento.setCep(resultSet.getString("cep"));
//                evento.setComplementoEndereco(resultSet.getString("complemento"));
//                evento.setDescricaoEvento(resultSet.getString("descricao_evento"));

                participante.setIdParticipante(resultSet.getInt("id_participante"));
                participante.setNomeParticipante(resultSet.getString("nome_participante"));
                participante.setCpf(resultSet.getString("cpf"));
                participante.setTelefoneParticipante(resultSet.getString("telefone"));
                participante.setEmailParticipante(resultSet.getString("email"));
                participante.setTipoIngresso(resultSet.getString("tipo_ingresso"));
//                participante.setEvento(evento);

                participantes.add(participante);

            }

        } catch (SQLException e) {
            throw new EventosException(e);
        }

        return participantes;
    }
}
