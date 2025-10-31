import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class CompanyRecords {

private class Employee{
    String name;
    String personalEmail;
    String OfficialEmailId;
    String branchCode;
    String empId;
    //managerId with:mng, permanent employee with:pemp, contract employee with:cemp
    String phoneNumber;
    String address;
    String dateOfJoining;
    String dateOfBirth;
    String designation;
    long  baseSalary=20000;
}
private class Manager extends Employee{
    String managedBranch;
    public void ProduceYearlyReport()
    {
        //code to produce yearly report of that branch. total no of permanent and contract employees, total salary paid, total bonus paid etc, Along with ow muc the branc work done. Send to CEO
    }
    public void yearlyBonus()
    {
        // to only permanent Employee for a particular month. If CEo sends Signal to give Bonus.
    }
    public void generateEmployeeId()
    {
        //code to generate employee id for new employees. for permanent employee : pemp<branchCode><4 digit unique no> for contract employee : cemp<branchCode><4 digit unique no>
    }
    public void EnrollNewEmployee()
    {
        //code to enroll new employee. has access to the employee file and the contract employee file of that branch. serach for that files using the managerId in the branch File. Decrypt the location and then open the file. Increment the total no of permanent and contract employees in the branch file.
    }
    public void enrollNewContractEmployee()
    {
        //code to enroll new contract employee. has access to the employee file and the contract employee file of that branch. serach for that files using the managerId serach for that files using the managerId in the branch File. Decrypt the location and then open the file. Increment the total no of permanent and contract employees in the branch file.
    }
    public void viewEmployeeDetails()
    {
        //code to view employee details.
    }
    public void provideSalary()
    {
        //code to provide salary. override the base salary in Employee
    }
    public void removeEmployee()
    {
        //code to enroll new contract employee. has access to the employee file and the contract employee file of that branch. serach for that files using the managerId. Decrypt the location and then open the file.
    }
}
private class PermanentEmployee extends Employee{
    private String bankAccountNumber;
    private String salary;
    //override te salary of employee wit extra.
    public void viewOtherEmployeeDetails()
    {
        //will view other employee details but not sensitive data like bank account and salary
    }
    public void viewPayslip()
    {
        //code to view payslip
    }
    public void applyLeave()
    {
        //code to apply leave
    }
    public void viewAttendance()
    {
        //code to view attendance
    }
}
private class CEO {
    private String branches[];// read from the brances file.
    public void giveYearlyBonus()
    {
        // to only permanent Employee for a particular month.
    }
    public void viewAllBranchesDetails()
    {
        //code to view all branches like total Employees no of Contact and permanent Employees, . Full details of a particular branc if he wants later. Option to search for a Employee for a particular branch. Options to search for a particular branch. Decide the base Salary and by owmuc to override. Employee is a subclass of CEO.
    }
    public void viewAllEmployees()
    {
        //code to view all employees
    }
    public void provideSalaryToAll()
    {
        //code to provide salary to all employees in all brances Like sow Total no of Contract Employees and Permanent Employee of each branch manager salary and total for each branc all details. 
    }
    public void createNewBranch()
    {
        // Should enter branch code, branch address, branch phone number, branch manager details etc. and create new file for that branch for employee and contract employee. And insert that detail in the details file of that branch. File locations must be encrypted. The file of eac branch should ave the total no of permanent and contract employees also.
    }
}
private class ContractEmployee extends Employee{
    String contractDuration;
    String agencyName;
    public void viewPayslip()
    {
        //code to view payslip
    }
    public void applyLeave()
    {
        //code to apply leave
    }
    public void viewAttendance()
    {
        //code to view attendance
    }
}
private class userHandler {
    void login()
    {
         String emailId;
        Scanner sc = new Scanner (System.in);
        System.out.println("Enter your Email ID: ");
        emailId = sc.nextLine();
        String password;
        System.out.println("Create your Password: ");
        password = sc.nextLine();
        //if the emailId is of an Employee
        if(emailId.contains("@cgcb.ac.in"))
        {
            
        }
        if(emailId.contains("mng"))
            {
                //search in manager file
            }
            else if(emailId.contains("pemp"))
            {
                //search in permanent employee file
                //take out the branch code from emailId.
                //Search in that particular branch file
            }
            else if(emailId.contains("cemp"))
            {
                //search in contract employee file
                //take out the branch code from emailId.
                //Search in that particular branch file
            }
    }
    void register(){
        String emailId;
        Scanner sc = new Scanner (System.in);
        System.out.println("Enter your Email ID: ");
        emailId = sc.nextLine();
        if(emailId.contains("@cgcb.ac.in"))
        {
            System.out.println("Please use a personal Email Id to Register.\n If you are an Employee Please Login using your Company Email ID.");
        }
        else{
            String password;
            System.out.println("Create your Password: ");
            password = sc.nextLine();
            //Checking if the user is already present in the users file.
            List<List<String>> usersList = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] blocks = line.split("\t");
                    usersList.add(Arrays.asList(blocks));
                }
            } catch (IOException e) {
                System.out.println("An Unexpected error occurred. Please try again later.");
            }

            boolean alreadyRegistered = false;
            for(int i = 0; i < usersList.size(); i++)
            {
                List<String> user = usersList.get(i);
                if(!user.isEmpty() && user.get(0).equals(emailId))
                {
                    alreadyRegistered = true;
                    break;
                }
            }

            if(alreadyRegistered)
            {
                System.out.println("The Email ID is already registered. Please Login.");
                login();
                return;
            }

            try (FileWriter usersFile = new FileWriter("users.txt", true)) {
                usersFile.write(emailId.trim() + "\t" + password.trim() + System.lineSeparator());
            } catch(IOException e) {
                System.out.println("Error in registering user. Please try again later.");
            }
        }
    }// end of register function

}// end of userhandler class

    public static void main(String[] args)
    { 
        String Welcome="====================================\n    CGCB - WE FOR A MODERN INDIA\n====================================";
        System.out.println(Welcome);
        Scanner sc = new Scanner (System.in);
        boolean exit = false;

        while (exit == false) {
            
            System.out.println("\nFor Knowing More Enter 1");
            System.out.println("Login To your Account Enter 2");
            System.out.println("Register New Account Enter 3");
            System.out.println("Exit Enter 4");
            System.out.print("Enter your choice: ");

            int choice=sc.nextInt();
            
            switch(choice)
            {
                case 1:
                    try (BufferedReader reader = new BufferedReader(new FileReader("info.txt"))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                System.out.println(line);
	            }
	        } catch (IOException e) {
	            System.out.println("An error occurred while reading: " + e.getMessage());
	        }

                    break;
                case 2:
                    System.out.println("Login Feature Coming Soon....");
                    break;
                case 3:
                    System.out.println("Registration Feature Coming Soon....");
                    break;
                case 4:
                    System.out.println("Exiting... Thank you for using CGCB!");
                    exit = true; 
                    break;
                default:
                    System.out.println("Invalid choice. Please try again....");
            }
            
        }
        
        sc.close();
    }
}


