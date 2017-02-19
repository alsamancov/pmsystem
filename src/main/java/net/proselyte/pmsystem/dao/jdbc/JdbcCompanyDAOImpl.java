package net.proselyte.pmsystem.dao.jdbc;

import net.proselyte.pmsystem.dao.CompanyDAO;
import net.proselyte.pmsystem.dao.GenericDAO;
import net.proselyte.pmsystem.model.Company;
import net.proselyte.pmsystem.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static net.proselyte.pmsystem.util.ConnectionUtil.*;

/**
 * Implementation of {@link GenericDAO} interface for class {@link Company}.
 *
 * @author Oleksii Samantsov
 */
public class JdbcCompanyDAOImpl implements CompanyDAO {
    private static final String GET_BY_ID = "SELECT * FROM companies WHERE id = ?";
    private static final String INSERT_NEW = "INSERT INTO companies VALUES (?, ?, ?)";
    private static final String UPDATE_ROW = "UPDATE companies SET NAME = ?, DESCRIPTION = ? WHERE ID = ?";
    private static final String DELETE_ROW = "DELETE FROM companies WHERE ID = ?";
    private static final String GET_ALL = "SELECT ID, NAME, DESCRIPTION FROM companies";
    private static final String SHOW_RELATED_CUSTOMES = "SELECT customers.name" +
            " FROM customes JOIN company_customers ON " +
            "company_customers.customersID = customers.ID" +
            " JOIN companies ON company_customers.CompanyID = company.ID" +
            " WHERE companies.ID = ?";
    private static final String SHOW_RELATED_PROJECTS_AND_DEVELOPERS = "SELECT projects.name," +
            " developers.firstName FROM projects LEFT JOIN team_developers " +
            "ON projects.teamId = team_developers.teamId LEFT JOIN developers " +
            "ON team_developers.developerId = developers.id WHERE projects.companyId = ?";


    private HashSet<String> customers = new HashSet();
    private HashSet<String> projects = new HashSet();
    private HashSet<String> developers = new HashSet();

    public JdbcCompanyDAOImpl() {
            getConnection();
    }

    public Company getById(Long id) {

        try {
            preparedStatement = connection.prepareStatement(GET_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }
            Company company = new Company();
            company.setId(resultSet.getLong("id"));
            company.setName(resultSet.getString("name"));
            company.setDescription("description");
            return company;
        } catch (SQLException e) {
            throw new RuntimeException("Exception occured while connection to DB " + e);
        }finally {
            closePreparedStatement();
        }
    }


    public void save(Company company) {
        try {
            preparedStatement = connection.prepareStatement(INSERT_NEW);
            preparedStatement.setLong(1, company.getId());
            preparedStatement.setString(2, company.getName());
            preparedStatement.setString(3, company.getDescription());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Exception occurred while connecting to DB " + e);
        }finally {
            closePreparedStatement();
        }
    }

    public void update(Company company) {
        try {
            preparedStatement = connection.prepareStatement(UPDATE_ROW);
            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getDescription());
            preparedStatement.setLong(3, company.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Exception occurred while connecting to DB " + e);
        } finally {
            closePreparedStatement();
        }
    }

    public void remove(Company company) {
        try {
            preparedStatement = connection.prepareStatement(DELETE_ROW);
            preparedStatement.setLong(1, company.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Exception occurred while connection to DB " + e);
        }finally {
            closePreparedStatement();
        }

    }

    public Collection<Company> getAll(){
        try {
            try(Connection connection  = ConnectionUtil.connection){
                try(Statement statement = connection.createStatement()){
                    Collection<Company> list = new ArrayList<Company>();
                    try(ResultSet resultSet = statement.executeQuery(GET_ALL)){
                        Company company = new Company();
                        company.setId(resultSet.getLong(1));
                        company.setName(resultSet.getString(2));
                        company.setDescription(resultSet.getString(3));
                        list.add(company);
                    }
                    return list;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void showRelatedCustomers(Long id){
        try {
            preparedStatement = connection.prepareStatement(SHOW_RELATED_CUSTOMES);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                customers.add(resultSet.getString(1));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException("Exception occurred while connecting to DB " + e);
        }finally {
            closePreparedStatement();
        }
        if(!customers.isEmpty()){
            for(String str : customers){
                System.out.print(str + "; ");
            }
        } else{
            System.out.println("There's no clients in this company");
        }
    }

    public void showRelatedProjectsAndDevelopers(Long id){
        try {
            preparedStatement = connection.prepareStatement(SHOW_RELATED_PROJECTS_AND_DEVELOPERS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                projects.add(resultSet.getString(1));
                developers.add(resultSet.getString(2));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException("Exception occurred while connecting to DB " + e);
        }finally {
            closePreparedStatement();
        }
        if(!projects.isEmpty()){
            for(String str : projects){
                System.out.print(str + "; ");
            }
            System.out.println("Developers of the company are : ");
            for(String str : developers){
                System.out.print(str + "; ");
            }
        } else {
            System.out.println("There's no developers in this company");
        }
    }
}
