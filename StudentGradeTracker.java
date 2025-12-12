import java.util.*;

public class StudentGradeTracker {
    static class Student {
        String id;
        String name;
        List<Double> scores = new ArrayList<>();

        Student(String id, String name) {
            this.id = id;
            this.name = name;
        }

        void addScore(double s) { scores.add(s); }

        double average() {
            if (scores.isEmpty()) return 0.0;
            double sum = 0;
            for (double d : scores) sum += d;
            return sum / scores.size();
        }

        String letterGrade() {
            double avg = average();
            if (scores.isEmpty()) return "N/A";
            if (avg >= 90) return "A";
            if (avg >= 80) return "B";
            if (avg >= 70) return "C";
            if (avg >= 60) return "D";
            return "F";
        }

        @Override
        public String toString() {
            return String.format("%s | %s | Avg: %.2f | Grade: %s | Scores: %s",
                    id, name, average(), letterGrade(), scores);
        }
    }

    private final Map<String, Student> students = new LinkedHashMap<>();
    private final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        new StudentGradeTracker().run();
    }

    void run() {
        while (true) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": createStudent(); break;
                case "2": addScoreToStudent(); break;
                case "3": showStudent(); break;
                case "4": listStudents(); break;
                case "5": removeStudent(); break;
                case "6": showClassSummary(); break;
                case "0": System.out.println("Exiting. Goodbye!"); return;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }

    void printMenu() {
        System.out.println();
        System.out.println("=== Student Grade Tracker ===");
        System.out.println("1. Add new student");
        System.out.println("2. Add score to student");
        System.out.println("3. Show student details");
        System.out.println("4. List all students");
        System.out.println("5. Remove student");
        System.out.println("6. Class summary (class average, highest, lowest)");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }

    void createStudent() {
        System.out.print("Enter student id: ");
        String id = sc.nextLine().trim();
        if (id.isEmpty()) { System.out.println("Id cannot be empty."); return; }
        if (students.containsKey(id)) {
            System.out.println("Student with this id already exists.");
            return;
        }
        System.out.print("Enter student name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Name cannot be empty."); return; }
        students.put(id, new Student(id, name));
        System.out.println("Student added: " + id + " - " + name);
    }

    void addScoreToStudent() {
        System.out.print("Enter student id: ");
        String id = sc.nextLine().trim();
        Student s = students.get(id);
        if (s == null) { System.out.println("No student found with id " + id); return; }
        System.out.print("Enter score (0-100): ");
        String line = sc.nextLine().trim();
        try {
            double score = Double.parseDouble(line);
            if (score < 0 || score > 100) { System.out.println("Score must be 0-100."); return; }
            s.addScore(score);
            System.out.println("Added score " + score + " to " + s.name);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid number.");
        }
    }

    void showStudent() {
        System.out.print("Enter student id: ");
        String id = sc.nextLine().trim();
        Student s = students.get(id);
        if (s == null) { System.out.println("No student found with id " + id); return; }
        System.out.println(s);
    }

    void listStudents() {
        if (students.isEmpty()) { System.out.println("No students yet."); return; }
        System.out.println("All students:");
        for (Student s : students.values()) {
            System.out.println(s);
        }
    }

    void removeStudent() {
        System.out.print("Enter student id to remove: ");
        String id = sc.nextLine().trim();
        Student removed = students.remove(id);
        if (removed == null) System.out.println("No student with id " + id);
        else System.out.println("Removed student " + removed.name);
    }

    void showClassSummary() {
        if (students.isEmpty()) { System.out.println("No students to summarize."); return; }
        double totalAvg = 0;
        int countWithScores = 0;
        Student highest = null, lowest = null;
        for (Student s : students.values()) {
            if (!s.scores.isEmpty()) {
                double avg = s.average();
                totalAvg += avg;
                countWithScores++;
                if (highest == null || avg > highest.average()) highest = s;
                if (lowest == null || avg < lowest.average()) lowest = s;
            }
        }
        if (countWithScores == 0) {
            System.out.println("No scores entered yet for any student.");
            return;
        }
        System.out.printf("Class average (of students with scores): %.2f%n", totalAvg / countWithScores);
        System.out.println("Highest: " + highest);
        System.out.println("Lowest : " + lowest);
    }
}