package lesson3.task3;

import java.io.File;
import java.io.IOException;

public class OrgStructureTest {
    public static void main(String[] args) {
        OrgStructureParser orgStructureParser = new OrgStructureParserImpl();
        try {
            System.out.println(orgStructureParser.parseStructure(new File("src/lesson3/task3/input.csv")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
