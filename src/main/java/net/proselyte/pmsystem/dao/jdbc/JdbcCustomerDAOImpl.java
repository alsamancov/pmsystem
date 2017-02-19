package net.proselyte.pmsystem.dao.jdbc;

import net.proselyte.pmsystem.dao.CustomerDAO;
import net.proselyte.pmsystem.dao.GenericDAO;
import net.proselyte.pmsystem.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static net.proselyte.pmsystem.util.ConnectionUtil.*;

/**
 * Implementation of {@link GenericDAO} interface for class {@link Customer}
 *
 * @author Oleksii Samantsov
 */
public class JdbcCustomerDAOImpl implements CustomerDAO {
    private static final String INSERT_NEW ="INSERT INTO customers(ID, NAME, DESCRITION) VALUES (?, ?, ?)";
    private static final String GET_BY_ID = "SELECT * FROM customers WHERE ID = ?";
    private static final String UPDATE_ROW = "UPDATE customers SET NAME = ?, DESCRIPTION = ? WHERE ID = ?";
    private static final String DELETE_ROW = "DELETE FROM customers WHERE ID = ?";
    private static final String SHOW_ALL = "SELECT * FROM customers";

    private List<Customer> customers = new ArrayList<Customer>();

    public JdbcCustomerDAOImpl() {
        getConnection();
    }

    @Override
    public Customer getById(Long id) {
        try {
            preparedStatement = connection.prepareStatement(GET_BY_ID);
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(!resultSet.next()){
                    return null;
                }
                Customer customer = new Customer();
                customer.setId(resultSet.getLong(1));
                customer.setName(resultSet.getString(2));
                customer.setDescription(resultSet.getString(3));
                return customer;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            closePreparedStatement();
        }
    }

    @Override
    public void save(Customer customer) {
        try {
            preparedStatement = connection.prepareStatement(INSERT_NEW);
            preparedStatement.setLong(1, customer.getId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }finally {
            closePreparedStatement();
        }

    }

    @Override
    public void update(Customer customer) {
        try {
            preparedStatement = connection.prepareStatement(UPDATE_ROW);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getDescription());
            preparedStatement.setLong(3, customer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }finally {
            closePreparedStatement();
        }
    }

    @Override
    public void remove(Customer customer) {
        try {
            preparedStatement = connection.prepareStatement(DELETE_ROW);
            preparedStatement.setLong(1, customer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }finally {
            closePreparedStatement();
        }
    }

    public ArrayList<Customer> showCustomer(){
        try {
            preparedStatement = connection.prepareStatement(SHOW_ALL);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Customer customer = new Customer();
                    customer.setId(resultSet.getLong("id"));
                    customer.setName(resultSet.getString("name"));
                    customer.setDescription(resultSet.getString("description"));
                    customers.add(customer);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }finally {
            closePreparedStatement();
        }
        return (ArrayList<Customer>) customers;
    }
}
