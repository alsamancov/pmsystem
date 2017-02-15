package net.proselyte.pmsystem.dao.jdbc;

import net.proselyte.pmsystem.dao.CompanyDAO;
import net.proselyte.pmsystem.dao.GenericDAO;
import net.proselyte.pmsystem.model.Company;

import static net.proselyte.pmsystem.util.ConnectionUtil.getConnection;

/**
 * Implementation of {@link GenericDAO} interface for class {@link Company}.
 *
 * @author Oleksii Samantsov
 */
public class JdbcCompanyDAOImpl implements CompanyDAO {

    public JdbcCompanyDAOImpl() {
        getConnection();
    }

    public Company getById(Long aLong) {
        return null;
    }

    public void save(Company entity) {

    }

    public void update(Company entity) {

    }

    public void remove(Company entity) {

    }
}
