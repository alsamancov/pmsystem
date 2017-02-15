package net.proselyte.pmsystem.dao;

import net.proselyte.pmsystem.model.Developer;

/**
 * Extension of {@link GenericDAO} interface for class {@link Developer}
 *
 * @author Oleksii Samantsov
 */
public interface DeveloperDAO extends GenericDAO<Developer, Long> {
    void showAllDevelopers(Developer developer);
}
