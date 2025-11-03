class index{
    public static void main(String[] args)
    {
      Employee Emp1=new Employee();
      //Emp1.getDetails();
      Emp1.printDetails();
        Employee Emp2=new Employee("Sutapa Naskar",19,50000.0);
        Emp2.printDetails();
    }
}
class Employee{
    String empName;
    int age;
    double Salary;
     Employee()
    {
        empName="User";
        age=21;
        Salary=30000.0;
    }
 Employee(String name, int empAge, double empSalary)
    {
        this.empName=name;
        this.age=empAge;
        this.Salary=empSalary;
    }
   /* void getDetails()
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Employee Name:");
        empName=sc.nextLine();
        System.out.println("Enter Employee Age:");
        age=sc.nextInt();
        System.out.println("Enter Employee Salary:");
        Salary=sc.nextDouble();
    }*/
    void printDetails()
    {
        System.out.println("\n----------------------\n");
        System.out.println("Employee Name:"+empName);
        System.out.println("Employee Age:"+age);
        System.out.println("Employee Salary:"+Salary);
    }
    

}