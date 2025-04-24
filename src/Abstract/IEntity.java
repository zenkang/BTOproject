package Abstract;

/**
 * Generic interface representing an entity that can be stored and retrieved from a CSV file.
 * Entities implementing this interface must be identifiable by a unique ID and support
 * serialization/deserialization to and from CSV format.
 */
public interface IEntity {

    /**
     * Converts this entity into a CSV-formatted string for file storage.
     *
     * @return the CSV representation of the entity
     */
    public String toCSVRow();

    /**
     * Constructs an instance of the entity from a given CSV-formatted string.
     * Implementations should parse the string and return a new entity object.
     *
     * @param row the CSV string representing an entity
     * @return a new entity instance parsed from the row
     */
    public IEntity fromCSVRow(String row);

    /**
     * Returns the unique identifier of the entity.
     * Typically used for indexing, lookup, and comparison.
     *
     * @return the entity's unique ID
     */
    public String getID();

    /**
     * Returns a human-readable string representation of the entity.
     *
     * @return the string representation of the entity
     */
    public String toString();
}
