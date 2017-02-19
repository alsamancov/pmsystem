package net.proselyte.pmsystem.dao.jdbc;

import net.proselyte.pmsystem.dao.DocumentDao;
import net.proselyte.pmsystem.dao.GenericDAO;
import net.proselyte.pmsystem.model.Document;

import java.sql.ResultSet;
import java.sql.SQLException;

import static net.proselyte.pmsystem.util.ConnectionUtil.*;

/**
 * Implementation of {@link GenericDAO} interface of class{@link Document}
 *
 * @author Oleksii Samantsov
 */
public class JdbcDocumentDAOImpl implements DocumentDao {
    private static final String INSERT_NEW = "INSERT INTO documents (ID, NAME, CONTENT) VALUES (?, ?, ?)";
    private static final String GET_BY_ID = "SELECT * FROM documents WHERE ID = ?";
    private static final String UPDATE_ROW = "UPDATE documents SET NAME = ? CONTENT = ? WHERE ID = ?";
    private static final String DELETE_ROW = "DELETE FROM documents WHERE ID = ?";

    public JdbcDocumentDAOImpl() {
        getConnection();
    }

    @Override
    public Document getById(Long id) {
        try {
            preparedStatement = connection.prepareStatement(GET_BY_ID);
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if(!resultSet.next()){
                    return null;
                }
                Document document = new Document();
                document.setId(resultSet.getLong("id"));
                document.setName(resultSet.getString("name"));
                document.setContent(resultSet.getString("content"));
                return document;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            closePreparedStatement();
        }
    }

    @Override
    public void save(Document document) {
        try {
            preparedStatement = connection.prepareStatement(INSERT_NEW);
            preparedStatement.setLong(1, document.getId());
            preparedStatement.setString(2, document.getName());
            preparedStatement.setString(3, document.getContent());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            closePreparedStatement();
        }
    }

    @Override
    public void update(Document document) {
        try {
            preparedStatement = connection.prepareStatement(UPDATE_ROW);
            preparedStatement.setString(1, document.getName());
            preparedStatement.setString(2, document.getContent());
            preparedStatement.setLong(3, document.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            closePreparedStatement();
        }
    }

    @Override
    public void remove(Document document) {
        try {
            preparedStatement = connection.prepareStatement(DELETE_ROW);
            preparedStatement.setLong(1, document.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            closePreparedStatement();
        }
    }
}
