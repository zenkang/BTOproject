package ProjectApplication;

import Abstract.IEntity;

public class ProjectApplication implements IEntity {

    @Override
    public String toCSVRow() {
        return "";
    }

    @Override
    public IEntity fromCSVRow(String row) {
        return null;
    }

    @Override
    public String getID() {
        return "";
    }
}
