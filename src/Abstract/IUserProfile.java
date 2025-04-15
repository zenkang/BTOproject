package Abstract;

import Enumerations.MaritalStatus;

public interface IUserProfile {
    public String getName();
    public String getNric();
    public int getAge();
    public MaritalStatus getMaritalStatus();
    public String getPassword();
}
