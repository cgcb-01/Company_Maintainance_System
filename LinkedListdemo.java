import java.util.*;
class LinkedListdemo{
    public static void main(String[] args) 
    {
     LinkedList<String> fruits=new LinkedList<>();
    fruits.add("apple");
    fruits.add("banana");
    fruits.add("Cherry");      
    fruits.add(1,"Mango");
    System.out.println("Initial List:"+fruits);
    fruits.addFirst("Pineapple");
    fruits.addLast("Grapes");
    System.out.println("First Element:"+ fruits.getFirst());
    System.out.println("Last Element:"+ fruits.getLast());
    System.out.println("Element at index 2:"+ fruits.get(2));
    fruits.set(2,"Orange");
    fruits.remove();
    fruits.remove(2);
    fruits.remove("Grapes");
    System.out.println("Updated List:"+ fruits);

    System.out.println("Conatains Banana?"+ fruits.contains("banana"));
    System.out.println("Contains Kiwi?"+ fruits.contains("kiwi"));

    System.out.println("Size of the LinkedList:"+ fruits.size());

    System.out.println("Peek(heard Element):"+fruits.peek());
    System.out.println("Poll(remove Head Element:"+fruits.poll());
    System.out.println("fruits.poll():"+fruits.poll());
    Iterator<String> it=fruits.iterator();
    while(it.hasNext())
    {
        System.out.print(it.next());
    }
    System.out.println();
    Iterator<String> desc=fruits.descendingIterator();
    System.out.println("Reverse Iteration:");
    while(desc.hasNext())
    {
        System.out.print(desc.next());
    }
    System.out.println();
    fruits.clear();
    System.out.println("After clearing:"+ fruits);
    }
    }
  