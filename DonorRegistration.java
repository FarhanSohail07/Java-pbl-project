import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DonorRegistration {
    private Scanner scanner;
    private DonorRegistry registry;
    private static final String[] VALID_BLOOD_GROUPS = {
        "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
    };
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DonorRegistration(Scanner scanner, DonorRegistry registry) {
        this.scanner = scanner;
        this.registry = registry;
    }

    public void registerDonor() {
        System.out.println("\n--- Donor Registration ---");
        try {
            String name = readName();
            int age = readAge();
            String bloodGroup = readBloodGroup();
            String location = readLocation();
            LocalDate lastDonationDate = readLastDonationDate();
            boolean available = readAvailability();

            Donor donor = new Donor(name, age, bloodGroup, location, lastDonationDate, available);
            registry.addDonor(donor);

            System.out.println("Donor registered successfully!");
        } catch (Exception e) {
            System.out.println("Registration failed due to an unexpected error: " + e.getMessage());
        }
    }

    private String readName() {
        String name;
        while (true) {
            System.out.print("Enter donor name: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty. Please try again.");
            } else {
                break;
            }
        }
        return name;
    }

    private int readAge() {
        int age;
        while (true) {
            System.out.print("Enter donor age (must be 18 or above): ");
            try {
                age = Integer.parseInt(scanner.nextLine().trim());
                if (age < 18) {
                    System.out.println("Donor must be at least 18 years old.");
                } else if (age > 65) {
                    System.out.println("Age seems too high for donation eligibility. Please re-check.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return age;
    }

    private String readBloodGroup() {
        String bloodGroup;
        while (true) {
            System.out.print("Enter blood group (A+, A-, B+, B-, AB+, AB-, O+, O-): ");
            bloodGroup = scanner.nextLine().trim().toUpperCase();
            if (isValidBloodGroup(bloodGroup)) {
                break;
            } else {
                System.out.println("Invalid blood group. Please enter a valid one.");
            }
        }
        return bloodGroup;
    }

    private boolean isValidBloodGroup(String bg) {
        for (String valid : VALID_BLOOD_GROUPS) {
            if (valid.equals(bg)) {
                return true;
            }
        }
        return false;
    }

    private String readLocation() {
        String location;
        while (true) {
            System.out.print("Enter donor location/city: ");
            location = scanner.nextLine().trim();
            if (location.isEmpty()) {
                System.out.println("Location cannot be empty.");
            } else {
                break;
            }
        }
        return location;
    }

    private LocalDate readLastDonationDate() {
        while (true) {
            System.out.print("Enter last donation date (yyyy-MM-dd) or type 'never' if first time: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("never")) {
                return null;
            }

            try {
                LocalDate date = LocalDate.parse(input, DATE_FORMAT);
                if (date.isAfter(LocalDate.now())) {
                    System.out.println("Donation date cannot be in the future.");
                    continue;
                }
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd (e.g., 2024-05-20).");
            }
        }
    }

    private boolean readAvailability() {
        while (true) {
            System.out.print("Is the donor currently available? (yes/no): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes") || input.equals("y")) {
                return true;
            } else if (input.equals("no") || input.equals("n")) {
                return false;
            } else {
                System.out.println("Please enter 'yes' or 'no'.");
            }
        }
    }
}
