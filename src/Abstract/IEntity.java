package Abstract;

public interface IEntity {
    public String toCSVRow();
    public IEntity fromCSVRow(String row);
    public String getID();
    public String toString();
}
