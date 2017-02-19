package net.proselyte.pmsystem.dao.jdbc;

import net.proselyte.pmsystem.dao.GenericDAO;
import net.proselyte.pmsystem.dao.SkillDAO;
import net.proselyte.pmsystem.model.Skill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static net.proselyte.pmsystem.util.ConnectionUtil.*;

/**
 * Implementation of {@link GenericDAO} interface for class{@link Skill}
 *
 * @author Oleksii Samantsov
 */
public class JdbcSkillDAOImpl implements SkillDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcSkillDAOImpl.class);

    private final static String INSERT_ROW = "INSERT INTO skills VALUES (?, ?)";
    private final static String GET_BY_ID = "SELECT * FROM skills WHERE ID = ?";
    private final static String UPDATE_ROW = "UPDATE skills SET NAME = ? WHERE ID = ?";
    private final static String DELETE_ROW = "DELETE FROM skills WHERE ID = ?";
    private final static String GET_ALL = "SELECT * FROM skills";

    public JdbcSkillDAOImpl() {
        getConnection();
    }

    @Override
    public Skill getById(Long id) {
        Skill skill = new Skill();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)){
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()) {
                    skill.setId(resultSet.getLong("id"));
                    skill.setName(resultSet.getString("name"));
                } else {
                    throw new SQLException();
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connection to DB ", e);
        }
        return skill;
    }

    @Override
    public void save(Skill skill) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROW)) {
            preparedStatement.setLong(1, skill.getId());
            preparedStatement.setString(2, skill.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connection to DB " + e);
        }
    }

    @Override
    public void update(Skill skill) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)){
            preparedStatement.setString(1, skill.getName());
            preparedStatement.setLong(2, skill.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connection to DB " + e);
        }
    }

    @Override
    public void remove(Skill skill) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROW)){
            preparedStatement.setLong(1, skill.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connection to DB " + e);
        }
    }

    public Collection<Skill> showAll(){
        Collection<Skill> list = new ArrayList<Skill>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Skill skill = new Skill();
                    skill.setId(resultSet.getLong("id"));
                    skill.setName(resultSet.getString("name"));
                    list.add(skill);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connection to DB " + e);
        }
        return list;
    }
}
