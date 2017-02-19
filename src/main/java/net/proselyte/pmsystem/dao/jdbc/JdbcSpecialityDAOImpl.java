package net.proselyte.pmsystem.dao.jdbc;

import net.proselyte.pmsystem.dao.GenericDAO;
import net.proselyte.pmsystem.dao.SpecialityDAO;
import net.proselyte.pmsystem.model.Speciality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static net.proselyte.pmsystem.util.ConnectionUtil.connection;
import static net.proselyte.pmsystem.util.ConnectionUtil.getConnection;

/**
 * Implementation of {@link GenericDAO} interface for class{@link Speciality}
 */
public class JdbcSpecialityDAOImpl implements SpecialityDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcSpecialityDAOImpl.class);

    private static final String GET_BY_ID = "SELECT * FROM specialities WHERE ID = ?";
    private static final String INSERT_ROW = "INSERT INTO specialiteies VALUES (?, ?)";
    private static final String UPDATE_ROW = "UPDATE specialities SET NAME = ? WHERE ID = ?";
    private static final String DELETE_ROW = "DELETE FROM specialities WHERE ID = ?";
    private static final String GET_ALL = "SELECT * FROM specialities";




    public JdbcSpecialityDAOImpl() {
        getConnection();
    }

    @Override
    public Speciality getById(Long id) {
        Speciality speciality = new Speciality();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)){
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    speciality.setId(resultSet.getLong("ID"));
                    speciality.setName(resultSet.getString("NAME"));
                } else{
                    LOGGER.error("There's no speciality with id " + id + " in database");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connection to DB ", e);
        }
        return speciality;
    }

    @Override
    public void save(Speciality speciality) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROW)){
            preparedStatement.setLong(1, speciality.getId());
            preparedStatement.setString(2, speciality.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connection to DB ", e);
        }

    }

    @Override
    public void update(Speciality speciality) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)){
            preparedStatement.setString(1, speciality.getName());
            preparedStatement.setLong(2, speciality.getId());
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            LOGGER.error("Exception occurred while connection to DB ", e);
        }
    }

    @Override
    public void remove(Speciality speciality) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROW)){
            preparedStatement.setLong(1, speciality.getId());
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            LOGGER.error("Exception occurred while connection to DB ", e);
        }
    }

    public Collection<Speciality> getAll(){
        Collection<Speciality> list = new ArrayList<Speciality>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Speciality speciality = new Speciality();
                    speciality.setId(resultSet.getLong("id"));
                    speciality.setName(resultSet.getString("name"));
                    list.add(speciality);
                }
            }
        } catch(SQLException e){
            LOGGER.error("Exception occured while connection to DB " + e);
        }
        return list;
    }
}
