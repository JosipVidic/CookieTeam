package hr.foi.cookie.interfaces;

import hr.foi.cookie.core.exceptions.DataSourceException;

import java.util.List;

/**
 * Represents a source of data in the form of an entity class.
 * The class must allow entities to be grabbed in two ways: via getById,
 * which returns a single resource of such type, and by getAll, which returns
 * all such entities.
 * 
 * @author Marin
 *
 * @param <Entity> Entity to be grabbed.
 */
public interface IDataSource<Entity> extends IUniquelyGettable<Entity> {
	/**
	 * An IDataSource object represents a source of data that belongs to a certain class of Entities
	 * and defines two methods: getting a single entity by its unique ID, and getting all entities of 
	 * this type.
	 * @return A list of returned entities.
	 * @throws DataSourceException
	 */
	public List<Entity> getAll() throws DataSourceException;
	
	/**
	 * An IDataSource object represents a source of data that belongs to a certain class of Entities
	 * and defines two methods: getting a single entity by its unique ID, and getting all entities of 
	 * this type.
	 * @param Offset from start of all entities
	 * @param Limit of returned entities (e.g. 30 should return only 30 elements in the list).
	 * @return A list of returned entities.
	 * @throws DataSourceException
	 */
	public List<Entity> getAll(int offset, int limit) throws DataSourceException;
}
