package net.proselyte.pmsystem.dao.jdbc;

import net.proselyte.pmsystem.dao.GenericDAO;
import net.proselyte.pmsystem.dao.TeamDAO;
import net.proselyte.pmsystem.model.Team;
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
 * Implementation of {@link GenericDAO} interface for class {@link Team}
 *
 * @author Oleksii Samantsov
 */
public class JdbcTeamDaoImpl implements TeamDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcTeamDaoImpl.class);

    private static final String GET_ALL = "SELECT * FROM teams";
    private static final String GET_BY_ID = "SELECT * FROM teams WHERE ID = ?";
    private static final String INSERT_ROW = "INSERT INTO teams VALUES (?, ?)";
    private static final String UPDATE_ROW = "UPDATE teams SET NAME = ? WHERE NAME = ?";
    private static final String DELETE_ROW = "DELETE FROM teams WHERE ID = ?";

    public JdbcTeamDaoImpl() {
        getConnection();
    }

    @Override
    public Team getById(Long id) {
        Team team = new Team();
        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)){
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    team.setId(resultSet.getLong("id"));
                    team.setName(resultSet.getString("name"));
                } else{
                    LOGGER.error("There's no team with id " + id + " in database");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connection to DB ", e);
        }
        return team;
    }

    @Override
    public void save(Team team){
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROW)){
            preparedStatement.setLong(1, team.getId());
            preparedStatement.setString(2, team.getName());
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            LOGGER.error("Exception occurred while connection to DB ", e);
        }
    }

    @Override
    public void update(Team team) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)){
            preparedStatement.setString(1, team.getName());
            preparedStatement.setLong(2, team.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connection to DB ", e);
        }
    }

    @Override
    public void remove(Team team) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROW)){
            preparedStatement.setLong(1, team.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connection to DB ", e);
        }
    }

    public Collection<Team> getAll(){
        Collection<Team> list = new ArrayList<Team>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Team team = new Team();
                    team.setId(resultSet.getLong("id"));
                    team.setName(resultSet.getString("name"));
                    list.add(team);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connection to DB ", e);
        }
        return list;
    }
}
