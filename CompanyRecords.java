import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.*;

public class CompanyRecords {

    private class handleDatabase {
        String searchInFile(String pathFile, String searchKey) {
            try (BufferedReader reader = new BufferedReader(new FileReader(pathFile))) {
                String line;
                boolean firstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue; // Skip header line
                    }
                    if (line.contains(searchKey)) {
                        return line;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error searching in file: " + pathFile + ". File may not exist or access denied.");
            }
            return "";
        }

        void readFileWithHeader(String pathFile) {
            try (BufferedReader reader = new BufferedReader(new FileReader(pathFile))) {
                String line;
                boolean firstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        System.out.println("=== " + line + " ===");
                        firstLine = false;
                    } else {
                        System.out.println(line);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + pathFile + ". File may not exist or access denied.");
            }
        }

        void writeFile(String pathFile, String entry) {
            try (FileWriter writer = new FileWriter(pathFile, true)) {
                writer.write(entry);
            } catch (IOException e) {
                System.out.println("Error writing to file: " + pathFile + ". Please check file permissions.");
            }
        }

        List<String> readAllLines(String pathFile) {
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(pathFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + pathFile + ". File may not exist or access denied.");
            }
            return lines;
        }

        void overwriteFile(String pathFile, List<String> lines) {
            try (FileWriter writer = new FileWriter(pathFile)) {
                for (String line : lines) {
                    writer.write(line + System.lineSeparator());
                }
            } catch (IOException e) {
                System.out.println("Error updating file: " + pathFile + ". Please check file permissions.");
            }
        }

        void createFileWithHeader(String pathFile, String header) {
            try (FileWriter writer = new FileWriter(pathFile)) {
                writer.write(header + System.lineSeparator());
                System.out.println("File created successfully: " + pathFile);
            } catch (IOException e) {
                System.out.println("Error creating file: " + pathFile + ". Please check file permissions.");
            }
        }

        int getRecordCount(String pathFile) {
            int count = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(pathFile))) {
                String line;
                boolean firstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    if (!line.trim().isEmpty()) {
                        count++;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + pathFile);
            }
            return count;
        }
    }

    handleDatabase fileHandler = new handleDatabase();

    // Encryption utility class
    private class SecurityManager {
        private String encryptionKey = "CGCB_SECURE_2024";
        
        public String encrypt(String data) {
            try {
                String dataToEncrypt = data + encryptionKey;
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(dataToEncrypt.getBytes());
                return Base64.getEncoder().encodeToString(hash).substring(0, 16);
            } catch (Exception e) {
                System.out.println("Encryption error: " + e.getMessage());
                return data;
            }
        }
        
        public String generateManagerAccessCode(String managerId, String branchCode) {
            return encrypt(managerId + "_" + branchCode + "_ACCESS");
        }
        
        public String generateFilePath(String branchCode, String fileType, String accessCode) {
            return encrypt(branchCode + "_" + fileType + "_" + accessCode) + ".txt";
        }
        
        public boolean validateAccess(String managerId, String branchCode, String accessCode) {
            String expectedCode = generateManagerAccessCode(managerId, branchCode);
            return expectedCode.equals(accessCode);
        }
    }

    SecurityManager security = new SecurityManager();

    // Email Generator
    private class EmailGenerator {
        public String generateOfficialEmail(String name, String branchCode, String empType) {
            String cleanName = name.toLowerCase()
                .replaceAll("[^a-z ]", "") 
                .replaceAll("\\s+", ".");  
            
            if (empType.equals("mng")) {
                return cleanName + "@" + branchCode + ".cgcb.ac.in";
            } else if (empType.equals("pemp")) {
                return cleanName + "." + empType + "@" + branchCode + ".cgcb.ac.in";
            } else if (empType.equals("cemp")) {
                return cleanName + "." + empType + "@" + branchCode + ".cgcb.ac.in";
            }
            return cleanName + "@" + branchCode + ".cgcb.ac.in";
        }
    }

    EmailGenerator emailGenerator = new EmailGenerator();

    private class Employee {
        String name;
        String personalEmail;
        String OfficialEmailId;
        String branchCode;
        String empId;
        String phoneNumber;
        String address;
        String dateOfJoining;
        String dateOfBirth;
        String designation;
        private long baseSalary = 20000;

        public Employee() {}

        public Employee(String details) {
            String[] parts = details.split("\t");
            if (parts.length >= 9) {
                this.empId = parts[0];
                this.name = parts[1];
                this.OfficialEmailId = parts[2];
                this.branchCode = parts[3];
                this.phoneNumber = parts[4];
                this.address = parts[5];
                this.dateOfJoining = parts[6];
                this.dateOfBirth = parts[7];
                this.designation = parts[8];
            }
        }

        public void viewProfile() {
            System.out.println("Employee ID: " + this.empId);
            System.out.println("Name: " + this.name);
            System.out.println("Official Email ID: " + this.OfficialEmailId);
            System.out.println("Branch Code: " + this.branchCode);
            System.out.println("Phone Number: " + this.phoneNumber);
            System.out.println("Address: " + this.address);
            System.out.println("Date Of Joining: " + this.dateOfJoining);
            System.out.println("Date Of Birth: " + this.dateOfBirth);
            System.out.println("Designation: " + this.designation);
        }

        // Method to generate password from DOB
        public String generatePasswordFromDOB() {
            if (this.dateOfBirth == null || this.dateOfBirth.isEmpty()) {
                return "default@cgcb";
            }
            // Remove dashes and add @cgcb suffix
            return this.dateOfBirth.replaceAll("-", "") + "@cgcb";
        }
    }

    private class Manager extends Employee {
        String managedBranch;
        String managerId;
        String accessCode;

        public Manager(String details) {
            super(details);
            if (details.split("\t").length > 10) {
                String[] parts = details.split("\t");
                this.managedBranch = parts[3];
                this.managerId = this.empId;
                this.accessCode = parts[10];
            }
        }

        // Method to get encrypted file paths for this branch
        private String getEmployeeFilePath() {
            return security.generateFilePath(this.managedBranch, "employees", this.accessCode);
        }

        private String getContractEmployeeFilePath() {
            return security.generateFilePath(this.managedBranch, "contract_employees", this.accessCode);
        }

        private String getBranchFilePath() {
            return security.generateFilePath(this.managedBranch, "branch_data", this.accessCode);
        }

        private String getLeaveFilePath() {
            return security.generateFilePath(this.managedBranch, "leave_data", this.accessCode);
        }

        private String getPayslipFilePath() {
            return security.generateFilePath(this.managedBranch, "payslip_data", this.accessCode);
        }

        private boolean validateManagerAccess() {
            return security.validateAccess(this.managerId, this.managedBranch, this.accessCode);
        }

        // Auto-generate Employee ID
        private String generateEmployeeId(String employeeType) {
            if (!validateManagerAccess()) {
                return "ACCESS_DENIED";
            }

            String branchFile = getBranchFilePath();
            int employeeCount = fileHandler.getRecordCount(getEmployeeFilePath()) + 
                              fileHandler.getRecordCount(getContractEmployeeFilePath()) + 1;
            
            String empId = employeeType + this.managedBranch + String.format("%04d", employeeCount);
            return empId;
        }

        public void ProduceYearlyReport() {
            if (!validateManagerAccess()) {
                System.out.println("Access denied! Invalid access code.");
                return;
            }

            try {
                int permanentEmployees = fileHandler.getRecordCount(getEmployeeFilePath());
                int contractEmployees = fileHandler.getRecordCount(getContractEmployeeFilePath());
                
                double totalSalary = (permanentEmployees * 25000) + (contractEmployees * 20000);
                double totalBonus = permanentEmployees * 5000;
                
                System.out.println("=== YEARLY REPORT FOR BRANCH: " + this.managedBranch + " ===");
                System.out.println("Total Permanent Employees: " + permanentEmployees);
                System.out.println("Total Contract Employees: " + contractEmployees);
                System.out.println("Total Salary Paid: ₹" + totalSalary);
                System.out.println("Total Bonus Paid: ₹" + totalBonus);
                System.out.println("Branch Performance: Excellent");
                
                String report = this.managedBranch + "\t" + permanentEmployees + "\t" + contractEmployees + 
                              "\t" + totalSalary + "\t" + totalBonus + "\tExcellent\n";
                fileHandler.writeFile("ceo_reports.txt", report);
                
            } catch (Exception e) {
                System.out.println("Error generating yearly report: " + e.getMessage());
            }
        }

        public void yearlyBonus() {
            if (!validateManagerAccess()) {
                System.out.println("Access denied! Invalid access code.");
                return;
            }
            System.out.println("Yearly bonus processing initiated...");
            String ceoSignal = fileHandler.searchInFile("ceo_commands.txt", "BONUS_" + this.managedBranch);
            if (ceoSignal.contains("AUTHORIZED")) {
                System.out.println("CEO authorization confirmed. Processing bonus...");
                System.out.println("Bonus processed successfully for permanent employees.");
            } else {
                System.out.println("Waiting for CEO authorization to process bonus.");
            }
        }

        public void EnrollNewEmployee() {
            if (!validateManagerAccess()) {
                System.out.println("Access denied! Invalid access code.");
                return;
            }

            Scanner sc = new Scanner(System.in);
            System.out.println("=== ENROLL NEW PERMANENT EMPLOYEE ===");
            
            String empId = generateEmployeeId("pemp");
            if (empId.equals("ACCESS_DENIED")) return;
            
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Personal Email: ");
            String personalEmail = sc.nextLine();
            System.out.print("Enter Phone Number: ");
            String phone = sc.nextLine();
            System.out.print("Enter Address: ");
            String address = sc.nextLine();
            System.out.print("Enter Date of Joining (DD-MM-YYYY): ");
            String doj = sc.nextLine();
            System.out.print("Enter Date of Birth (DD-MM-YYYY): ");
            String dob = sc.nextLine();
            System.out.print("Enter Designation: ");
            String designation = sc.nextLine();
            System.out.print("Enter Bank Account Number: ");
            String bankAccount = sc.nextLine();
            
            // Auto-generate official email
            String officialEmail = emailGenerator.generateOfficialEmail(name, this.managedBranch, "pemp");
            
            String employeeRecord = empId + "\t" + name + "\t" + officialEmail + "\t" + 
                                  this.managedBranch + "\t" + phone + "\t" + address + "\t" + 
                                  doj + "\t" + dob + "\t" + designation + "\t" + bankAccount + "\n";
            
            fileHandler.writeFile(getEmployeeFilePath(), employeeRecord);
            System.out.println("Permanent employee enrolled successfully!");
            System.out.println("Generated Employee ID: " + empId);
            System.out.println("Official Email: " + officialEmail);
            System.out.println("Password for login: " + dob.replaceAll("-", "") + "@cgcb");
        }

        public void enrollNewContractEmployee() {
            if (!validateManagerAccess()) {
                System.out.println("Access denied! Invalid access code.");
                return;
            }

            Scanner sc = new Scanner(System.in);
            System.out.println("=== ENROLL NEW CONTRACT EMPLOYEE ===");
            
            String empId = generateEmployeeId("cemp");
            if (empId.equals("ACCESS_DENIED")) return;
            
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Personal Email: ");
            String personalEmail = sc.nextLine();
            System.out.print("Enter Phone Number: ");
            String phone = sc.nextLine();
            System.out.print("Enter Address: ");
            String address = sc.nextLine();
            System.out.print("Enter Date of Joining (DD-MM-YYYY): ");
            String doj = sc.nextLine();
            System.out.print("Enter Date of Birth (DD-MM-YYYY): ");
            String dob = sc.nextLine();
            System.out.print("Enter Designation: ");
            String designation = sc.nextLine();
            System.out.print("Enter Contract Duration (months): ");
            String duration = sc.nextLine();
            System.out.print("Enter Salary: ");
            String salary = sc.nextLine();
            
            // Auto-generate official email
            String officialEmail = emailGenerator.generateOfficialEmail(name, this.managedBranch, "cemp");
            
            String employeeRecord = empId + "\t" + name + "\t" + officialEmail + "\t" + 
                                  this.managedBranch + "\t" + phone + "\t" + address + "\t" + 
                                  doj + "\t" + dob + "\t" + designation + "\t" + duration + "\t" + salary + "\n";
            
            fileHandler.writeFile(getContractEmployeeFilePath(), employeeRecord);
            System.out.println("Contract employee enrolled successfully!");
            System.out.println("Generated Employee ID: " + empId);
            System.out.println("Official Email: " + officialEmail);
            System.out.println("Password for login: " + dob.replaceAll("-", "") + "@cgcb");
        }

        public void viewEmployeeDetails() {
            if (!validateManagerAccess()) {
                System.out.println("Access denied! Invalid access code.");
                return;
            }

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Employee ID to search: ");
            String empId = sc.nextLine();
            
            String employeeType = empId.substring(0, 4);
            String filename = employeeType.equals("pemp") ? getEmployeeFilePath() : getContractEmployeeFilePath();
            
            String employeeData = fileHandler.searchInFile(filename, empId);
            if (!employeeData.isEmpty()) {
                System.out.println("=== EMPLOYEE DETAILS ===");
                String[] parts = employeeData.split("\t");
                System.out.println("ID: " + parts[0]);
                System.out.println("Name: " + parts[1]);
                System.out.println("Email: " + parts[2]);
                System.out.println("Branch: " + parts[3]);
                System.out.println("Phone: " + parts[4]);
                System.out.println("Designation: " + parts[8]);
            } else {
                System.out.println("Employee not found with ID: " + empId);
            }
        }

        public void provideSalary() {
            if (!validateManagerAccess()) {
                System.out.println("Access denied! Invalid access code.");
                return;
            }

            System.out.println("Salary processing for branch " + this.managedBranch + "...");
            System.out.println("Salary processed successfully for all employees.");
        }

        public void removeEmployee() {
            if (!validateManagerAccess()) {
                System.out.println("Access denied! Invalid access code.");
                return;
            }

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Employee ID to remove: ");
            String empId = sc.nextLine();
            
            String employeeType = empId.substring(0, 4);
            String filename = employeeType.equals("pemp") ? getEmployeeFilePath() : getContractEmployeeFilePath();
            
            List<String> employees = fileHandler.readAllLines(filename);
            List<String> updatedEmployees = new ArrayList<>();
            boolean found = false;
            
            // Keep header
            if (!employees.isEmpty()) {
                updatedEmployees.add(employees.get(0));
            }
            
            for (int i = 1; i < employees.size(); i++) {
                String employee = employees.get(i);
                if (!employee.startsWith(empId)) {
                    updatedEmployees.add(employee);
                } else {
                    found = true;
                }
            }
            
            if (found) {
                fileHandler.overwriteFile(filename, updatedEmployees);
                System.out.println("Employee " + empId + " removed successfully.");
            } else {
                System.out.println("Employee not found with ID: " + empId);
            }
        }

        public void managerDashboard() {
            Scanner sc = new Scanner(System.in);
            boolean logout = false;
            
            if (!validateManagerAccess()) {
                System.out.println("Access denied! Invalid access code. Please contact CEO.");
                return;
            }
            
            while (!logout) {
                System.out.println("\n=== MANAGER DASHBOARD ===");
                System.out.println("Welcome, " + this.name + " (" + this.managerId + ")");
                System.out.println("Branch: " + this.managedBranch);
                System.out.println("Access Code: " + this.accessCode.substring(0, 4) + "****");
                System.out.println("1. Enroll New Permanent Employee");
                System.out.println("2. Enroll New Contract Employee");
                System.out.println("3. View Employee Details");
                System.out.println("4. Remove Employee");
                System.out.println("5. Produce Yearly Report");
                System.out.println("6. Process Salary");
                System.out.println("7. Process Yearly Bonus");
                System.out.println("8. View My Profile");
                System.out.println("9. Logout");
                System.out.print("Enter your choice: ");
                
                int choice = sc.nextInt();
                sc.nextLine();
                
                switch (choice) {
                    case 1:
                        EnrollNewEmployee();
                        break;
                    case 2:
                        enrollNewContractEmployee();
                        break;
                    case 3:
                        viewEmployeeDetails();
                        break;
                    case 4:
                        removeEmployee();
                        break;
                    case 5:
                        ProduceYearlyReport();
                        break;
                    case 6:
                        provideSalary();
                        break;
                    case 7:
                        yearlyBonus();
                        break;
                    case 8:
                        viewProfile();
                        break;
                    case 9:
                        logout = true;
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private class PermanentEmployee extends Employee {
        private String bankAccountNumber;
        private String salary;

        public PermanentEmployee(String details) {
            super(details);
            String[] parts = details.split("\t");
            if (parts.length >= 10) {
                this.bankAccountNumber = parts[9];
                this.salary = parts.length > 10 ? parts[10] : "25000";
            }
        }

        public void viewOtherEmployeeDetails() {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Employee ID to view: ");
            String empId = sc.nextLine();
            
            String employeeType = empId.substring(0, 4);
            String branchCode = empId.substring(4, 7);
            
            // For security, employees can only view basic details
            System.out.println("Employee search feature - Contact Manager for detailed information.");
        }

        public void viewPayslip() {
            System.out.println("Payslip feature - Contact HR for payslip details.");
        }

        public void applyLeave() {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Leave Start Date (DD-MM-YYYY): ");
            String startDate = sc.nextLine();
            System.out.print("Enter Leave End Date (DD-MM-YYYY): ");
            String endDate = sc.nextLine();
            System.out.print("Enter Reason: ");
            String reason = sc.nextLine();
            
            String leaveApplication = this.empId + "\t" + startDate + "\t" + endDate + "\t" + 
                                    reason + "\t" + "Pending" + "\n";
            fileHandler.writeFile(this.branchCode + "_emp_leave.txt", leaveApplication);
            System.out.println("Leave application submitted successfully.");
        }

        public void viewAttendance() {
            System.out.println("Attendance feature - Under development");
        }

        public void permanentEmployeeDashboard() {
            Scanner sc = new Scanner(System.in);
            boolean logout = false;
            
            while (!logout) {
                System.out.println("\n=== EMPLOYEE DASHBOARD ===");
                System.out.println("Welcome, " + this.name + " (" + this.empId + ")");
                System.out.println("1. View My Profile");
                System.out.println("2. View Payslip");
                System.out.println("3. Apply Leave");
                System.out.println("4. View Attendance");
                System.out.println("5. View Other Employee Details");
                System.out.println("6. Logout");
                System.out.print("Enter your choice: ");
                
                int choice = sc.nextInt();
                sc.nextLine();
                
                switch (choice) {
                    case 1:
                        viewProfile();
                        break;
                    case 2:
                        viewPayslip();
                        break;
                    case 3:
                        applyLeave();
                        break;
                    case 4:
                        viewAttendance();
                        break;
                    case 5:
                        viewOtherEmployeeDetails();
                        break;
                    case 6:
                        logout = true;
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private class ContractEmployee extends Employee {
        String contractDuration;
        private String salary;

        ContractEmployee(String Details) {
            super(Details);
            String[] parts = Details.split("\t");
            if (parts.length >= 11) {
                this.contractDuration = parts[9];
                this.salary = parts[10];
            }
        }

        public void applyLeave() {
            String LeaveDateStart;
            String LeaveDateEnd;
            String Reason;
            boolean isApproved = false;
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter Leave Start Date:");
            LeaveDateStart = sc.nextLine();
            System.out.println("Enter Leave End Date:");
            LeaveDateEnd = sc.nextLine();
            System.out.println("Enter Reason for Leave:");
            Reason = sc.nextLine();
            String pathFile = super.branchCode + "_cemp_leave.txt";
            String entry = super.empId + "\t" + LeaveDateStart + "\t" + LeaveDateEnd + "\t" + Reason + "\t" + isApproved + "\n";
            fileHandler.writeFile(pathFile, entry);
            System.out.println("Leave application submitted successfully.");
        }

        public void viewLeaveStatus() {
            String filename = super.branchCode + "_cemp_leave.txt";
            System.out.println("Leave Status feature - Under development");
        }

        public void viewPayslip() {
            System.out.println("Payslip feature - Contact HR for payslip details.");
        }

        public void viewProfile() {
            super.viewProfile();
            System.out.println("Contract Duration: " + this.contractDuration);
            System.out.println("Salary: " + this.salary);
        }

        public void contractEmployeeDashboard() {
            Scanner sc = new Scanner(System.in);
            boolean logout = false;

            while (!logout) {
                System.out.println("\n=== CONTRACT EMPLOYEE DASHBOARD ===");
                System.out.println("Welcome, " + this.name + " (" + this.empId + ")");
                System.out.println("1. View My Profile");
                System.out.println("2. View Payslip");
                System.out.println("3. Apply Leave");
                System.out.println("4. View Leave Status");
                System.out.println("5. Logout");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        viewProfile();
                        break;
                    case 2:
                        viewPayslip();
                        break;
                    case 3:
                        applyLeave();
                        break;
                    case 4:
                        viewLeaveStatus();
                        break;
                    case 5:
                        logout = true;
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private class CEO {
        private List<String> branches;

        public CEO() {
            this.branches = fileHandler.readAllLines("secure_branches.txt");
            if (this.branches.size() > 1) {
                this.branches = this.branches.subList(1, this.branches.size()); 
            }
        }

        public void createNewBranch() {
            Scanner sc = new Scanner(System.in);
            System.out.println("=== CREATE NEW BRANCH ===");
            
            System.out.print("Enter Branch Code : ");
            String branchCode = sc.nextLine().toUpperCase();
            System.out.print("Enter Branch Address: ");
            String address = sc.nextLine();
            System.out.print("Enter Branch Phone: ");
            String phone = sc.nextLine();
            System.out.print("Enter Manager Name: ");
            String managerName = sc.nextLine();
            System.out.print("Enter Manager Personal Email: ");
            String managerEmail = sc.nextLine();
            System.out.print("Enter Manager Phone: ");
            String managerPhone = sc.nextLine();
            System.out.print("Enter Manager Date of Birth (DD-MM-YYYY): ");
            String dob = sc.nextLine();
            String password = dob.replaceAll("-", "") + "@cgcb";
            
            // Generate manager ID and access code
            String managerId = "mng" + branchCode + "001";
            String accessCode = security.generateManagerAccessCode(managerId, branchCode);
            
            // Generate encrypted file paths
            String employeeFile = security.generateFilePath(branchCode, "employees", accessCode);
            String contractEmployeeFile = security.generateFilePath(branchCode, "contract_employees", accessCode);
            String branchFile = security.generateFilePath(branchCode, "branch_data", accessCode);
            String leaveFile = security.generateFilePath(branchCode, "leave_data", accessCode);
            String payslipFile = security.generateFilePath(branchCode, "payslip_data", accessCode);
            
            List<String> branchesData = fileHandler.readAllLines("secure_branches.txt");
            if (branchesData.isEmpty()) {
                String header = "BranchCode\tAddress\tPhone\tManagerID\tManagerName\tAccessCode\tEmployeeFile\tContractEmployeeFile\tBranchFile\tLeaveFile\tPayslipFile";
                fileHandler.createFileWithHeader("secure_branches.txt", header);
            }
            
            // Add new branch to secure_branches.txt
            String branchRecord = branchCode + "\t" + address + "\t" + phone + "\t" + 
                                managerId + "\t" + managerName + "\t" + accessCode + "\t" +
                                employeeFile + "\t" + contractEmployeeFile + "\t" + 
                                branchFile + "\t" + leaveFile + "\t" + payslipFile + "\n";
            fileHandler.writeFile("secure_branches.txt", branchRecord);
            
            // Create all branch files with headers
            fileHandler.createFileWithHeader(employeeFile, 
                "EmployeeID\tName\tOfficialEmail\tBranchCode\tPhone\tAddress\tDateOfJoining\tDateOfBirth\tDesignation\tBankAccount");
            
            fileHandler.createFileWithHeader(contractEmployeeFile,
                "EmployeeID\tName\tOfficialEmail\tBranchCode\tPhone\tAddress\tDateOfJoining\tDateOfBirth\tDesignation\tContractDuration\tSalary");
            
            fileHandler.createFileWithHeader(branchFile,
                "BranchCode\tPermanentEmployees\tContractEmployees\tCreatedDate");
            
            fileHandler.createFileWithHeader(leaveFile,
                "EmployeeID\tLeaveStart\tLeaveEnd\tReason\tStatus");
            
            fileHandler.createFileWithHeader(payslipFile,
                "EmployeeID\tMonth\tBasicSalary\tAllowances\tDeductions\tNetSalary");
            
            // Create manager file with header
            String managerHeader = "EmployeeID\tName\tOfficialEmail\tBranchCode\tPhone\tAddress\tDateOfJoining\tDateOfBirth\tDesignation\tManagedBranch\tAccessCode";
            fileHandler.createFileWithHeader(branchCode + "_mng.txt", managerHeader);
            
            // Add manager record
            String officialEmail = emailGenerator.generateOfficialEmail(managerName, branchCode, "mng");
            String currentDate = java.time.LocalDate.now().toString();
            String managerRecord = managerId + "\t" + managerName + "\t" + officialEmail + "\t" + 
                                 branchCode + "\t" + managerPhone + "\t" + address + "\t" +
                                 currentDate + "\t" + dob + "\tManager\t" + branchCode + "\t" + accessCode + "\n";
            fileHandler.writeFile(branchCode + "_mng.txt", managerRecord);
           
            String branchData = branchCode + "\t0\t0\t" + currentDate + "\n";
            fileHandler.writeFile(branchFile, branchData);
            
            System.out.println("\n=== NEW BRANCH CREATED SUCCESSFULLY ===");
            System.out.println("Branch Code: " + branchCode);
            System.out.println("Manager ID: " + managerId);
            System.out.println("Official Email: " + officialEmail);
            System.out.println("Password for login: " + password);
            System.out.println("Access Code: " + accessCode);
            System.out.println("IMPORTANT: Provide this access code to the manager securely!");
        }

        public void viewAllBranchesDetails() {
            System.out.println("=== ALL BRANCHES DETAILS ===");
            fileHandler.readFileWithHeader("secure_branches.txt");
        }

        public void viewAllEmployees() {
            System.out.println("=== VIEW ALL EMPLOYEES ===");
            for (String branch : this.branches) {
                if (!branch.trim().isEmpty()) {
                    String[] parts = branch.split("\t");
                    if (parts.length >= 7) {
                        String branchCode = parts[0];
                        String employeeFile = parts[6];
                        String contractFile = parts[7];
                        
                        System.out.println("\n--- Branch: " + branchCode + " ---");
                        System.out.println("Permanent Employees:");
                        fileHandler.readFileWithHeader(employeeFile);
                        System.out.println("Contract Employees:");
                        fileHandler.readFileWithHeader(contractFile);
                    }
                }
            }
        }

        public void provideSalaryToAll() {
            System.out.println("=== PROCESSING SALARY FOR ALL BRANCHES ===");
            double totalSalary = 0;
            int totalEmployees = 0;
            
            for (String branch : this.branches) {
                if (!branch.trim().isEmpty()) {
                    String[] parts = branch.split("\t");
                    if (parts.length >= 8) {
                        String branchCode = parts[0];
                        String branchFile = parts[8];
                        
                        String branchData = fileHandler.searchInFile(branchFile, branchCode);
                        if (!branchData.isEmpty()) {
                            String[] dataParts = branchData.split("\t");
                            int permEmployees = Integer.parseInt(dataParts[1]);
                            int contractEmployees = Integer.parseInt(dataParts[2]);
                            totalEmployees += (permEmployees + contractEmployees);
                            
                            double branchSalary = (permEmployees * 25000) + (contractEmployees * 20000);
                            totalSalary += branchSalary;
                            
                            System.out.println("Branch " + branchCode + ": " + 
                                             permEmployees + " permanent + " + 
                                             contractEmployees + " contract = ₹" + branchSalary);
                        }
                    }
                }
            }
            
            System.out.println("TOTAL: " + totalEmployees + " employees = ₹" + totalSalary);
            System.out.println("Salary processing completed for all branches.");
        }

        public void giveYearlyBonus() {
            System.out.println("=== PROCESSING YEARLY BONUS ===");
            for (String branch : this.branches) {
                if (!branch.trim().isEmpty()) {
                    String[] parts = branch.split("\t");
                    String branchCode = parts[0];
                    System.out.println("Authorizing bonus for branch: " + branchCode);
                    fileHandler.writeFile("ceo_commands.txt", "BONUS_" + branchCode + "_AUTHORIZED\n");
                }
            }
            System.out.println("Yearly bonus authorization completed for all branches.");
        }

        public void viewBranchFiles() {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Branch Code: ");
            String branchCode = sc.nextLine().toUpperCase();
            
            List<String> branchesData = fileHandler.readAllLines("secure_branches.txt");
            for (String branch : branchesData) {
                if (branch.startsWith(branchCode + "\t")) {
                    String[] parts = branch.split("\t");
                    System.out.println("=== ENCRYPTED FILES FOR BRANCH: " + branchCode + " ===");
                    System.out.println("Employee File: " + parts[6]);
                    System.out.println("Contract Employee File: " + parts[7]);
                    System.out.println("Branch Data File: " + parts[8]);
                    System.out.println("Leave File: " + parts[9]);
                    System.out.println("Payslip File: " + parts[10]);
                    return;
                }
            }
            System.out.println("Branch not found: " + branchCode);
        }

        public void resetManagerAccessCode() {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Branch Code: ");
            String branchCode = sc.nextLine().toUpperCase();
            System.out.print("Enter Manager ID: ");
            String managerId = sc.nextLine();
            
            String newAccessCode = security.generateManagerAccessCode(managerId, branchCode);
            
            // Update in secure_branches.txt
            List<String> branchesData = fileHandler.readAllLines("secure_branches.txt");
            List<String> updatedBranches = new ArrayList<>();
            
            for (String branch : branchesData) {
                String[] parts = branch.split("\t");
                if (parts.length > 5 && parts[0].equals(branchCode) && parts[3].equals(managerId)) {
                    parts[5] = newAccessCode;
                    branch = String.join("\t", parts);
                }
                updatedBranches.add(branch);
            }
            
            fileHandler.overwriteFile("secure_branches.txt", updatedBranches);
            
            String managerFile = branchCode + "_mng.txt";
            List<String> managerData = fileHandler.readAllLines(managerFile);
            List<String> updatedManager = new ArrayList<>();
            
            for (String manager : managerData) {
                String[] parts = manager.split("\t");
                if (parts.length > 10 && parts[0].equals(managerId)) {
                    parts[10] = newAccessCode;
                    manager = String.join("\t", parts);
                }
                updatedManager.add(manager);
            }
            
            fileHandler.overwriteFile(managerFile, updatedManager);
            
            System.out.println("Access code reset successfully for manager: " + managerId);
            System.out.println("New Access Code: " + newAccessCode);
        }

        public void authorizeBonus() {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Branch Code to authorize bonus: ");
            String branchCode = sc.nextLine().toUpperCase();
            fileHandler.writeFile("ceo_commands.txt", "BONUS_" + branchCode + "_AUTHORIZED\n");
            System.out.println("Bonus authorized for branch: " + branchCode);
        }

        public void ceoDashboard() {
            Scanner sc = new Scanner(System.in);
            boolean logout = false;
            
            while (!logout) {
                System.out.println("\n=== CEO DASHBOARD ===");
                System.out.println("1. View All Branches Details");
                System.out.println("2. View All Employees");
                System.out.println("3. Provide Salary to All");
                System.out.println("4. Give Yearly Bonus");
                System.out.println("5. Create New Branch");
                System.out.println("6. View Branch Encrypted Files");
                System.out.println("7. Reset Manager Access Code");
                System.out.println("8. Authorize Bonus for Branch");
                System.out.println("9. Logout");
                System.out.print("Enter your choice: ");
                
                int choice = sc.nextInt();
                sc.nextLine();
                
                switch (choice) {
                    case 1:
                        viewAllBranchesDetails();
                        break;
                    case 2:
                        viewAllEmployees();
                        break;
                    case 3:
                        provideSalaryToAll();
                        break;
                    case 4:
                        giveYearlyBonus();
                        break;
                    case 5:
                        createNewBranch();
                        break;
                    case 6:
                        viewBranchFiles();
                        break;
                    case 7:
                        resetManagerAccessCode();
                        break;
                    case 8:
                        authorizeBonus();
                        break;
                    case 9:
                        logout = true;
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private class userHandler {
        void login() {
            String emailId;
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter your Email ID: ");
            emailId = sc.nextLine();
            
            // CEO Login
            if (emailId.equals("ceo@cgcb.ac.in")) {
                System.out.println("Enter your Password: ");
                String password = sc.nextLine();
                if (password.equals("OEC@3732")) {
                    System.out.println("Login successful! \nWelcome!");
                    CEO ceo = new CEO();
                    ceo.ceoDashboard();
                    return;
                } else {
                    System.out.println("Invalid CEO password.");
                    return;
                }
            }

            // Manager Login
            if (emailId.contains("@cgcb.ac.in") && emailId.contains("mng")) {
                System.out.println("Enter your Password: ");
                String password = sc.nextLine();
                System.out.println("Enter your Access Code: ");
                String accessCode = sc.nextLine();
                
                String[] parts = emailId.split("@");
                String domainPart = parts[0];
                String[] domainParts = domainPart.split("\\.");
                String branchCode = domainParts.length > 1 ? domainParts[1] : "";
                
                String managerFile = branchCode + "_mng.txt";
                String managerData = fileHandler.searchInFile(managerFile, emailId);
                
                if (!managerData.isEmpty()) {
                    String[] managerParts = managerData.split("\t");
                    // Extract DOB from manager record and generate expected password
                    String dob = managerParts[7]; // Date of Birth is at index 7
                    String expectedPassword = dob.replaceAll("-", "") + "@cgcb";
                    
                    if (managerParts.length > 10 && managerParts[10].equals(accessCode) && password.equals(expectedPassword)) {
                        System.out.println("Login successful! Welcome Manager " + managerParts[1]);
                        Manager manager = new Manager(managerData);
                        manager.managerDashboard();
                        return;
                    }
                }
                System.out.println("Invalid credentials or access code. Please try again.");
            } 
            // Employee Login (Permanent or Contract)
            else if (emailId.contains("@cgcb.ac.in")) {
                System.out.println("Enter your Password: ");
                String password = sc.nextLine();
                
                // Determine employee type and branch from email
                String employeeType = "";
                String branchCode = "";
                
                if (emailId.contains("pemp")) {
                    employeeType = "pemp";
                    String[] parts = emailId.split("@")[0].split("\\.");
                    branchCode = parts.length > 1 ? parts[1] : "";
                } else if (emailId.contains("cemp")) {
                    employeeType = "cemp";
                    String[] parts = emailId.split("@")[0].split("\\.");
                    branchCode = parts.length > 1 ? parts[1] : "";
                }
                
                if (!branchCode.isEmpty()) {
                    // Search for employee in the appropriate file
                    String filename = "";
                    if (employeeType.equals("pemp")) {
                        // Get the encrypted employee file path from secure_branches.txt
                        String branchData = fileHandler.searchInFile("secure_branches.txt", branchCode);
                        if (!branchData.isEmpty()) {
                            String[] parts = branchData.split("\t");
                            filename = parts[6]; // Employee file path
                        }
                    } else if (employeeType.equals("cemp")) {
                        // Get the encrypted contract employee file path from secure_branches.txt
                        String branchData = fileHandler.searchInFile("secure_branches.txt", branchCode);
                        if (!branchData.isEmpty()) {
                            String[] parts = branchData.split("\t");
                            filename = parts[7]; // Contract employee file path
                        }
                    }
                    
                    if (!filename.isEmpty()) {
                        String employeeData = fileHandler.searchInFile(filename, emailId);
                        if (!employeeData.isEmpty()) {
                            String[] employeeParts = employeeData.split("\t");
                            // Extract DOB from employee record (index 7)
                            String dob = employeeParts[7];
                            String expectedPassword = dob.replaceAll("-", "") + "@cgcb";
                            
                            if (password.equals(expectedPassword)) {
                                System.out.println("Login successful! Welcome " + employeeParts[1]);
                                
                                if (employeeType.equals("pemp")) {
                                    PermanentEmployee emp = new PermanentEmployee(employeeData);
                                    emp.permanentEmployeeDashboard();
                                } else if (employeeType.equals("cemp")) {
                                    ContractEmployee emp = new ContractEmployee(employeeData);
                                    emp.contractEmployeeDashboard();
                                }
                                return;
                            }
                        }
                    }
                }
                System.out.println("Invalid credentials. Please check your email and password.");
            } else {
                System.out.println("Invalid email format. Please use your company email ID.");
            }
        }

        void register() {
            System.out.println("Registration is only available for external users. Employees and Managers should use their company email IDs to login.");
            System.out.println("If you are an employee, please contact your manager for login credentials.");
            System.out.println("If you are a manager, please contact the CEO for login credentials.");
        }
    }

    public static void main(String[] args) {
        CompanyRecords company = new CompanyRecords();
        company.run();
    }

    public void run() {
        String Welcome = "====================================\n    CGCB - WE FOR A MODERN INDIA\n====================================";
        System.out.println(Welcome);
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nFor Knowing More Enter 1");
            System.out.println("Login To your Account Enter 2");
            System.out.println("Exit Enter 4");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    try (BufferedReader reader = new BufferedReader(new FileReader("info.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading company information. Please contact administrator.");
        }
                    break;
                case 2:
                    userHandler handler = new userHandler();
                    handler.login();
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