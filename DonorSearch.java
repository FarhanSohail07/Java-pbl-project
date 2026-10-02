import java.util.List;
import java.util.Scanner;

public class DonorSearch {
    private Scanner scanner;
    private DonorRegistry registry;
    private AIAnalytics analytics; // tracks blood-group search counts for AI Analytics
    private static final String[] VALID_BLOOD_GROUPS = {
        "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
    };

    public DonorSearch(Scanner scanner, DonorRegistry registry, AIAnalytics analytics) {
        this.scanner = scanner;
        this.registry = registry;
        this.analytics = analytics;
    }

    public void searchDonor() {
        System.out.println("\n--- Donor Search ---");

        String bloodGroup = readBloodGroupForSearch();
        String urgency = readUrgency();

        // Record this search for AI Analytics (menu option "AI Analytics")
        analytics.recordSearch(bloodGroup);

        List<Donor> results = registry.searchDonors(bloodGroup);

        System.out.println("\nSearching for Blood Group: " + bloodGroup + " | Urgency: " + urgency);
        printUrgencyMessage(urgency);

        if (results.isEmpty()) {
            System.out.println("No eligible & available donors found for blood group " + bloodGroup + ".");
            return;
        }

        System.out.println("\n--- Matching Donors (" + results.size() + " found) ---");
        int count = 1;
        for (Donor d : results) {
            System.out.println(count + ". " + d);
            count++;
        }
    }

    private void printUrgencyMessage(String urgency) {
        switch (urgency) {
            case "CRITICAL":
                System.out.println(">>> CRITICAL: Please contact donors immediately!");
                break;
            case "URGENT":
                System.out.println(">>> URGENT: Contact donors within a few hours.");
                break;
            case "NORMAL":
                System.out.println(">>> NORMAL: Contact donors at your convenience.");
                break;
            default:
                System.out.println(">>> Unrecognized urgency level.");
        }
    }

    /**
     * Public wrapper so other features (e.g. AI Recommendation menu) can
     * reuse the same validated blood-group prompt without duplicating code.
     */
    public String promptBloodGroup() {
        return readBloodGroupForSearch();
    }

    /**
     * Public wrapper so other features (e.g. AI Recommendation menu) can
     * reuse the same validated urgency prompt without duplicating code.
     */
    public String promptUrgency() {
        return readUrgency();
    }

    private String readBloodGroupForSearch() {
        String bloodGroup;
        while (true) {
            System.out.print("Enter blood group to search (A+, A-, B+, B-, AB+, AB-, O+, O-): ");
            bloodGroup = scanner.nextLine().trim().toUpperCase();
            if (isValidBloodGroup(bloodGroup)) {
                break;
            } else {
                System.out.println("Invalid blood group. Please try again.");
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

    private String readUrgency() {
        while (true) {
            System.out.println("Select urgency level:");
            System.out.println("1. Normal");
            System.out.println("2. Urgent");
            System.out.println("3. Critical");
            System.out.print("Enter choice (1-3): ");

            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    return "NORMAL";
                case "2":
                    return "URGENT";
                case "3":
                    return "CRITICAL";
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        }
    }
}
