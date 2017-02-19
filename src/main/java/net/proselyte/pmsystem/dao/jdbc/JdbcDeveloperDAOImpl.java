package net.proselyte.pmsystem.dao.jdbc;

import net.proselyte.pmsystem.dao.DeveloperDAO;
import net.proselyte.pmsystem.dao.GenericDAO;
import net.proselyte.pmsystem.model.Developer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static net.proselyte.pmsystem.util.ConnectionUtil.*;



/**
 * Implementation of {@link GenericDAO} interface for class {@link Developer}
 *
 * @author Oleksii Samantsov
 */
public class JdbcDeveloperDAOImpl implements DeveloperDAO {
    private static final String INSERT_NEW = "INSERT INTO developers(firstName, lastName, age, salary, yearsOfExperience, experience, id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_BY_ID = "SELECT * FROM developers WHERE ID = ?";
    private static final String UPDATE_ROW = "UPDATE developers SET firstName = ?, lastName = ?, age = ?, salary = ?, yearsOfExperience = ? experience ? WHERE ID = ?";
    private static final String DELETE_ROW = "DELETE FROM developers WHERE ID = ?";
    private static final String GET_ALL = "SELECT * FROM developers";

    public JdbcDeveloperDAOImpl() {
        getConnection();
    }

    @Override
    public Developer getById(Long id) {
        try {
            preparedStatement = connection.prepareStatement(GET_BY_ID);
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(!resultSet.next()){
                    return null;
                }
                Developer developer = new Developer();
                developer.setId(resultSet.getLong("id"));
                developer.setFirstName(resultSet.getString("firstName"));
                developer.setLastName(resultSet.getString("lastName"));
                developer.setAge(resultSet.getInt("age"));
                developer.setSalary(resultSet.getBigDecimal("salary"));
                developer.setYearsOfExperience(resultSet.getInt("yearsOfExperience"));
                developer.setExperience(resultSet.getString("experience"));
                return developer;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void save(Developer developer) {
        try {
            preparedStatement = connection.prepareStatement(INSERT_NEW);
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setInt(3, developer.getAge());
            preparedStatement.setBigDecimal(4, developer.getSalary());
            preparedStatement.setInt(5, developer.getYearsOfExperience());
            preparedStatement.setString(6, developer.getExperience());
            preparedStatement.setLong(7, developer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally{
            closePreparedStatement();
        }
    }

    @Override
    public void update(Developer developer) {
        try {
            preparedStatement = connection.prepareStatement(UPDATE_ROW);
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setInt(3, developer.getAge());
            preparedStatement.setBigDecimal(4, developer.getSalary());
            preparedStatement.setInt(5, developer.getYearsOfExperience());
            preparedStatement.setString(6, developer.getExperience());
            preparedStatement.setLong(7, developer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            closePreparedStatement();
        }
    }

    @Override
    public void remove(Developer developer) {
        try {
            preparedStatement = connection.prepareStatement(DELETE_ROW);
            preparedStatement.setLong(1, developer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            closePreparedStatement();
        }
    }

    @Override
    public Collection<Developer> showAllDevelopers() {
        try {
            preparedStatement = connection.prepareStatement(GET_ALL);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                Collection<Developer> list = new ArrayList<Developer>();
                while (resultSet.next()) {
                    Developer developer = new Developer();
                    developer.setId(resultSet.getLong("id"));
                    developer.setFirstName(resultSet.getString("firstName"));
                    developer.setLastName(resultSet.getString("lastName"));
                    developer.setAge(resultSet.getInt("age"));
                    developer.setSalary(resultSet.getBigDecimal("salary"));
                    developer.setYearsOfExperience(resultSet.getInt("yearsOfExperience"));
                    developer.setExperience(resultSet.getString("experience"));
                    list.add(developer);
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
