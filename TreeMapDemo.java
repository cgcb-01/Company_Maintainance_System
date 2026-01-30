import java.util.*;
class treeMapDemo{
    public static void main(String[] args)
    {
        TreeMap<Integer,String> studentMap=new TreeMap<>();
        studentMap.put(103,"Amit");
        studentMap.put(101,"Ravi");
        studentMap.put(102,"Priya");
        System.out.println("Student List:"+ studentMap);
        System.out.println("First Key:"+ studentMap.firstKey());
        System.out.println("Last Key:"+ studentMap.lastKey());
        System.out.println("Students before 103:"+ studentMap.headMap(103));
        System.out.println("Students from 101:"+ studentMap.tailMap(101));
        System.out.println("Students between 101 and 104:"+ studentMap.subMap(101,103));
    }
}