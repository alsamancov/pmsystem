package net.proselyte.pmsystem.dao.jdbc;

import net.proselyte.pmsystem.dao.GenericDAO;
import net.proselyte.pmsystem.dao.ProjectDAO;
import net.proselyte.pmsystem.model.Project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static net.proselyte.pmsystem.util.ConnectionUtil.*;

/**
 * Implementation of {@link GenericDAO} interface for class {@link Project}
 *
 * @author Oleksii Samantsov
 */
public class JdbcProjectDAOImpl implements ProjectDAO {
    private static final String GET_BY_ID = "SELECT * FROM projects WHERE ID = ?";
    private static final String UPDATE_ROW = "UPDATE projects SET NAME = ?, DESCRIPTION = ?, teamId = ?, documentId = ?, CompanyId = ? where ID = ?";
    private static final String DELETE_ROW = "DELETE FROM projects WHERE ID = ?";
    private static final String INSERT_ROW = "INSERT INTO projects VALUES(?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL = "SELECT * FROM projects";

    public JdbcProjectDAOImpl() {
        getConnection();
    }

    @Override
    public Project getById(Long id){
        Project project = new Project();
        try {
            preparedStatement = connection.prepareStatement(GET_BY_ID);
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    project.setId(resultSet.getLong("id"));
                    project.setName(resultSet.getString("name"));
                    project.setDescription(resultSet.getString("description"));
                    project.setTeamId(resultSet.getLong("teamId"));
                    project.setDocumentId(resultSet.getLong("documentId"));
                    project.setCompanyId(resultSet.getLong("CompanyId"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            closePreparedStatement();
        }
        return project;
    }

    @Override
    public void save(Project project){
        try {
            preparedStatement = connection.prepareStatement(INSERT_ROW);
            preparedStatement.setLong(1, project.getId());
            preparedStatement.setString(2, project.getName());
            preparedStatement.setString(3, project.getDescription());
            preparedStatement.setLong(4, project.getTeamId());
            preparedStatement.setLong(5, project.getDocumentId());
            preparedStatement.setLong(6, project.getCompanyId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            closePreparedStatement();
        }
    }

    @Override
    public void update(Project project){
        try {
            preparedStatement = connection.prepareStatement(UPDATE_ROW);
            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setLong(3, project.getTeamId());
            preparedStatement.setLong(4, project.getDocumentId());
            preparedStatement.setLong(5, project.getCompanyId());
            preparedStatement.setLong(6, project.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            closePreparedStatement();
        }
    }

    @Override
    public void remove(Project project){
        try {
            preparedStatement = connection.prepareStatement(DELETE_ROW);
            preparedStatement.setLong(1, project.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            closePreparedStatement();
        }
    }

    public Collection<Project> showAllProjects(){
        try {
            preparedStatement = connection.prepareStatement(GET_ALL);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                Collection<Project> list = new ArrayList<Project>();
                while(resultSet.next()){
                    Project project = new Project();
                    project.setId(resultSet.getLong("id"));
                    project.setName(resultSet.getString("name"));
                    project.setDescription(resultSet.getString("description"));
                    list.add(project);
                }
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            closePreparedStatement();
        }
    }
}
