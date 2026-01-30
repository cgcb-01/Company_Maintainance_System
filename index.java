import java.util.*;
class index{
    public static void main(String[] args) {
        {
            ArrayList<String> courses=new ArrayList<>();
            courses.add("java");
            courses.add("python");
            courses.add("c++");
            courses.add(1,"Javascript");
            System.out.println("Courses at Index 2:"+ courses.get(2));
            courses.set(2,"Golang");
            System.out.println("Conatains Python?"+ courses.contains("python"));
            System.out.println("Size of the ArrayList:"+ courses.size());
            courses.remove("c++");
            courses.remove(0);
            System.out.println("Courses List:"+ courses);
            courses.add("java");
            System.out.println("Index of Java:"+ courses.indexOf("java"));
            System.out.println("Last Index of Java:"+ courses.lastIndexOf("java"));
            System.out.println("Is the list empty?"+ courses.isEmpty());
            ArrayList<String> clonedList=(ArrayList<String>)courses.clone();
            System.out.println("Cloned List:"+ clonedList);
            ArrayList<String> newCourses=new ArrayList<>();
            newCourses.add("Rust");
            System.out.println("New Courses List:"+ newCourses);
            //Sublist
            System.out.println("Sublist :"+ courses.subList(2,2));
            courses.ensureCapacity(20);
            courses.trimToSize();
            clonedList.clear();
            System.out.println("All current Courses:");
            for(String course:courses){
                System.out.println(course);
            }
            System.out.println("Iterating using Iterator:");
            Iterator<String> it=courses.iterator();
            while(it.hasNext()){
                System.out.println(it.next());
            }
            
            List<String> popular=Arrays.asList("Java","Python","JavaScript");
            courses.retainAll(popular);
            System.out.println("Popular Courses:"+ courses);
Object[] arr=courses.toArray();
System.out.println("Array from ArrayList:"+ Arrays.toString(arr));
courses.removeIf(c->c.startsWith("k"));
System.out.println("After removing courses starting with k:"+ courses);
        }
    }
}

