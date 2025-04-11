
import User.User;

import User.UserBoundary;
import Login.LoginBoundary;
public class Main {
    public static void main(String[] args) {
        User user = LoginBoundary.login();
        if (user == null) {
            return;
        }
        System.out.println(user);
        UserBoundary.route(user);
        System.out.println("end");




        // Test applicant Class and applicant repo, to de done: Applicant controller
//        ApplicantRepository repo = new ApplicantRepository("./src/data/ApplicantList.csv");
//        repo.display();
//        repo.CreateApplicant("a","b",12,"single","yegw");
//        System.out.println("updated\n");
//        repo.display();
//        repo.deleteApplicantByNRIC("b");
//        System.out.println("deleted");
//        Applicant john = repo.getByID("S1234567A");
//        john.setPassword("UPDATEDPASSWORD");
//        repo.update(john);
//        repo.display();

    }
}
