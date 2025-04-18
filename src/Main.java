import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Applicant.Applicant;
import Applicant.ApplicantRepository;
import User.User;
import java.util.ArrayList;
import User.UsersRepository;
import java.util.Scanner;
import User.UserController;
import Utils.SafeScanner;
import User.UserBoundary;

public class Main {
    public static void main(String[] args) {
        UserBoundary.displayUsers();
        User user = UserBoundary.login();
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
