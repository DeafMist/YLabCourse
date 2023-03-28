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

        long fileLength = getFileLength(sortedFile);

        long curUnit = 1;
        while (curUnit < fileLength) {
            long file1Length = 0;
            long file2Length = 0;
            try (Scanner scanner = new Scanner(new FileInputStream(sortedFile));
                 PrintWriter pw1 = new PrintWriter(file1);
                 PrintWriter pw2 = new PrintWriter(file2)) {
                while (scanner.hasNextLong()) {
                    for (long i = 0; i < curUnit && scanner.hasNextLong(); i++) {
                        pw1.println(scanner.nextLong());
                        file1Length++;
                    }
                    for (long i = 0; i < curUnit && scanner.hasNextLong(); i++) {
                        pw2.println(scanner.nextLong());
                        file2Length++;
                    }
                }
                pw1.flush();
                pw2.flush();
            }

            try (PrintWriter pw = new PrintWriter(sortedFile);
                 Scanner scanner1 = new Scanner(new FileInputStream(file1));
                 Scanner scanner2 = new Scanner(new FileInputStream(file2))) {
                long a1 = Long.MAX_VALUE;
                long a2 = Long.MAX_VALUE;

                long curPosFile1 = 0;
                long curPosFile2 = 0;

                if (scanner1.hasNextLong()) {
                    a1 = scanner1.nextLong();
                    curPosFile1++;
                }

                if (scanner2.hasNextLong()) {
                    a2 = scanner2.nextLong();
                    curPosFile2++;
                }

                while (curPosFile1 <= file1Length && curPosFile2 <= file2Length) {
                    long i = 0;
                    long j = 0;
                    while (i < curUnit && j < curUnit && curPosFile1 <= file1Length && curPosFile2 <= file2Length) {
                        if (a1 < a2) {
                            pw.println(a1);
                            if (scanner1.hasNextLong()) a1 = scanner1.nextLong();
                            curPosFile1++;
                            i++;
                        }
                        else {
                            pw.println(a2);
                            if (scanner2.hasNextLong()) a2 = scanner2.nextLong();
                            curPosFile2++;
                            j++;
                        }
                    }

                    while (i < curUnit && curPosFile1 <= file1Length) {
                        pw.println(a1);
                        if (scanner1.hasNextLong()) a1 = scanner1.nextLong();
                        curPosFile1++;
                        i++;
                    }

                    while (j < curUnit && curPosFile2 <= file2Length) {
                        pw.println(a2);
                        if (scanner2.hasNextLong()) a2 = scanner2.nextLong();
                        curPosFile2++;
                        j++;
                    }
                }

                while (curPosFile1 <= file1Length) {
                    pw.println(a1);
                    curPosFile1++;
                    if (scanner1.hasNextLong()) a1 = scanner1.nextLong();
                }

                while (curPosFile2 <= file2Length) {
                    pw.println(a2);
                    curPosFile2++;
                    if (scanner2.hasNextLong()) a2 = scanner2.nextLong();
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
