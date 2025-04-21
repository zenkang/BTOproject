package ProjectRegistration;

import Abstract.Repository;

import Enumerations.RegistrationStatus;




public class ProjectRegistrationRepository extends Repository<ProjectRegistration> {
    private static ProjectRegistrationRepository instance;
    public ProjectRegistrationRepository(String filePath) {
        super(filePath);
    }
    public static ProjectRegistrationRepository getInstance(String filePath) {
        if (instance == null) {
            instance = new ProjectRegistrationRepository(filePath);
        }
        return instance;
    }

    @Override
    public ProjectRegistration fromCSVRow(String row) {

        if (row == null || row.trim().isEmpty()) {
            return null;
        }

        String[] values = row.split(",", -1);
        if (values.length < 5) {
            return null;
        }

        for (int i = 0; i < 5; i++) {
            values[i] = values[i].trim();
        }

        if (values[0].isEmpty() || values[1].isEmpty() || values[2].isEmpty()) {
            return null;
        }

        return new ProjectRegistration(
                values[0],
                values[1],
                values[2],
                values[3],
                RegistrationStatus.valueOf(values[4].toUpperCase())
        );
        }
    @Override
    public String CSVHeader() {
        return "Registration ID,Project ID,Project Name,Officer ID,Status";
    }
    }





