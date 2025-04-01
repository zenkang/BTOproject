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

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter NRIC: ");
        String nric = sc.nextLine();
        System.out.println("Enter Password: ");
        String password = sc.nextLine();
        User user = UserController.login(nric, password);
        System.out.println(user);
        if(user == null)
            return;
        System.out.println("Enter New Password: ");
        String NewPassword = sc.nextLine();
        UserController.changePassword(user,NewPassword);
        UserController.displayUsers();

//        ApplicantRepository repo = new ApplicantRepository("./src/data/ApplicantList.csv");
////        repo.display();
////        repo.CreateApplicant("a","b",12,"yegd","yegw");
////        System.out.println("updated\n");
////        repo.display();
////        repo.deleteApplicantByNRIC("b");
////        System.out.println("deleted\
//        Applicant john = repo.getByID("S1234567A");
//        john.setPassword("PENIS9999999");
//        repo.update(john);
//        repo.display();

    }
}
