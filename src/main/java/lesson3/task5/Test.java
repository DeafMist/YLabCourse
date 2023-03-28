package lesson3.task5;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        File dataFile = new Generator().generate("src/main/java/lesson3/task5/data.txt", 245314);
        System.out.println(new Validator(dataFile).isSorted());
        File sortedFile = new Sorter().sortFile(dataFile);
        System.out.println(new Validator(sortedFile).isSorted());
    }
}
