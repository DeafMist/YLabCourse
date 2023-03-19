package lesson3.task5;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Sorter {

    public File sortFile(File dataFile) throws IOException {
        String file1 = "src/lesson3/task5/tempFile1.txt";
        String file2 = "src/lesson3/task5/tempFile2.txt";

        File sortedFile = new File("src/lesson3/task5/sortedFile.txt");
        Files.deleteIfExists(sortedFile.toPath());
        Files.copy(dataFile.toPath(), sortedFile.toPath());

        long fileLength = getFileLength(dataFile);

        long curUnit = 1;
        while (curUnit < fileLength) {
            try (Scanner scanner = new Scanner(new FileInputStream(sortedFile));
                 PrintWriter pw1 = new PrintWriter(file1);
                 PrintWriter pw2 = new PrintWriter(file2)) {
                while (scanner.hasNextLong()) {
                    for (long i = 0; i < curUnit && scanner.hasNextLong(); i++) {
                        pw1.println(scanner.nextLong());
                    }
                    for (long i = 0; i < curUnit && scanner.hasNextLong(); i++) {
                        pw2.println(scanner.nextLong());
                    }
                }
                pw1.flush();
                pw2.flush();
            }

            try (PrintWriter pw = new PrintWriter(sortedFile);
                 Scanner scanner1 = new Scanner(new FileInputStream(file1));
                 Scanner scanner2 = new Scanner(new FileInputStream(file2))) {
                long a1 = scanner1.nextLong();
                long a2 = scanner2.nextLong();
                while (scanner1.hasNextLong() && scanner2.hasNextLong()) {
                    long i = 0;
                    long j = 0;
                    while (i < curUnit && j < curUnit && scanner1.hasNextLong() && scanner2.hasNextLong()) {
                        if (a1 < a2) {
                            pw.println(a1);
                            a1 = scanner1.nextLong();
                            i++;
                        }
                        else {
                            pw.println(a2);
                            a2 = scanner2.nextLong();
                            j++;
                        }
                    }

                    while (i < curUnit && scanner1.hasNextLong()) {
                        pw.println(a1);
                        a1 = scanner1.nextLong();
                        i++;
                    }

                    while (j < curUnit && scanner2.hasNextLong()) {
                        pw.println(a2);
                        a2 = scanner2.nextLong();
                        j++;
                    }
                }

                while (scanner1.hasNextLong()) {
                    pw.println(a1);
                    a1 = scanner1.nextLong();
                }

                while (scanner2.hasNextLong()) {
                    pw.println(a2);
                    a2 = scanner2.nextLong();
                }

                pw.flush();
                curUnit *= 2;
            }
        }

        Files.deleteIfExists(Path.of(file1));
        Files.deleteIfExists(Path.of(file2));

        return sortedFile;
    }

    private static long getFileLength(File dataFile) throws FileNotFoundException {
        long fileLength = 0L;
        try (Scanner scanner = new Scanner(dataFile)) {
            while (scanner.hasNextLong()) {
                scanner.nextLong();
                fileLength++;
            }
        }
        return fileLength;
    }
}
