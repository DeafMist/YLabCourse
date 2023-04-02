package lesson3.task3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class OrgStructureParserImpl implements OrgStructureParser {
    @Override
    public Employee parseStructure(File csvFile) throws IOException {
        Map<Long, List<Employee>> subordinatesMap = new HashMap<>();
        Map<Long, Employee> employees = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(csvFile)) {
            Scanner scanner = new Scanner(fis);
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(";");

                Employee employee = getEmployee(line);

                List<Employee> subordinates = subordinatesMap.getOrDefault(employee.getBossId(), new ArrayList<>());
                subordinates.add(employee);
                subordinatesMap.put(employee.getBossId(), subordinates);

                employees.put(employee.getId(), employee);
            }
        }

        Employee boss = new Employee();
        for (Map.Entry<Long, Employee> entry : employees.entrySet()) {
            Employee employee = entry.getValue();
            employee.setSubordinate(subordinatesMap.getOrDefault(employee.getId(), new ArrayList<>()));
            employee.setBoss(employees.get(employee.getBossId()));
            if (employee.getBossId() == null) {
                boss = employee;
            }
        }

        return boss;
    }

    private Employee getEmployee(String[] line) {
        Long id = Long.parseLong(line[0]);
        Long boss_id = null;
        if (isDigit(line[1])) {
            boss_id = Long.parseLong(line[1]);
        }

        return new Employee(id, boss_id, line[2], line[3]);
    }

    private static boolean isDigit(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
