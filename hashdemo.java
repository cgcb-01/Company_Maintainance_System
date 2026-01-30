import java .util.*;
class hashdemo{
public static void main(String[] args)
{
HashMap<Integer,String> empMap=new HashMap<>();
empMap.put(101,"Ravi");
empMap.put(102,"Amit");
empMap.put(103,"Priya");
empMap.put(101,"Rahul");
System.out.println("Employee Map:"+ empMap);
System.out.println("Employee with ID 102:"+ empMap.get(102));
System.out.println("Contains Key 101?"+ empMap.containsKey(101));
System.out.println("Contains Value 'Amit'?"+ empMap.containsValue("Amit"));
empMap.remove(103);
System.out.println("After removing ID 103:"+ empMap);
System.out.println("Total Employees:"+ empMap.size());
for(Map.Entry<Integer,String> entry: empMap.entrySet())
{
    System.out.println("ID:"+ entry.getKey()+ ", Name:"+ entry.getValue());
}
empMap.clear();
System.out.println("After clearing"+empMap);
}
}