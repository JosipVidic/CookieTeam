package hr.foi.cookie.interfaces;

import hr.foi.cookie.core.exceptions.DataSourceException;

/**
 * Represents a resource that is uniquely gettable by some ID.
 * @author Marin
 *
 * @param <Type> type of resource that is returned when it is "gotten".
 */
public interface IUniquelyGettable<Type> {
	Type getById(int id) throws DataSourceException;
}
