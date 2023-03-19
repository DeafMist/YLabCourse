package lesson3.task2;

public class DatedMapTest {
    public static void main(String[] args) {
        DatedMap map = new DatedMapImpl();

        map.put("hello", "privet");
        map.put("world", "mir");
        map.put("ylab", "work");

        System.out.println(map.containsKey("world")); // true
        System.out.println(map.containsKey("zoom")); // false

        map.remove("hello");

        System.out.println(map.keySet());

        System.out.println(map.getKeyLastInsertionDate("hello")); // null
        System.out.println(map.getKeyLastInsertionDate("ylab"));
    }
}
