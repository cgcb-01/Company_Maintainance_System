import java.util.*;
class LinkedHashMapdemo{
    public static void main(String[] args)
    {
        LinkedHashMap<String,Integer> recentItems=new LinkedHashMap<>();
        recentItems.put("Laptop",55000);
        recentItems.put("Smartphone",20000);
        recentItems.put("Headphones",3000);
        recentItems.put("Laptop",80000);
        System.out.println("Recent Items:"+ recentItems);
        for(String item: recentItems.keySet())
        {
            System.out.println("Item:"+ item+ ", Price:"+ recentItems.get(item));
        }
        recentItems.remove("Phone");
        System.out.println("After removing Smartphone:"+ recentItems);
        System.out.println("Contains Headphones?"+recentItems.containsKey("Headphones"));
        System.out.println("Total Items:"+ recentItems.size());
        recentItems.clear();
        System.out.println("After clearing:"+ recentItems);
    }
}