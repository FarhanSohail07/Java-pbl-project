import java.util.List;
import java.util.Scanner;

public class DonorTraceApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DonorRegistry registry = new DonorRegistry();
        AIAnalytics analytics = new AIAnalytics(); // shared AI Analytics tracker
        DonorRegistration registration = new DonorRegistration(scanner, registry);
        DonorSearch search = new DonorSearch(scanner, registry, analytics);
        AIChatAssistant chatAssistant = new AIChatAssistant(scanner, registry, analytics);

        boolean running = true;

        System.out.println("=====================================================");
        System.out.println(" Welcome to DonorTrace - Connecting Patients to Life-Saving Donors");
        System.out.println("=====================================================");

        while (running) {
            printMenu();
            int choice = readChoice(scanner);

            switch (choice) {
                case 1:
                    registration.registerDonor();
                    break;
                case 2:
                    search.searchDonor();
                    break;
                case 3:
                    registry.viewAllDonors();
                    break;
                case 4:
                    System.out.println("Total Registered Donors: " + registry.getDonorCount());
                    break;
                case 5:
                    runAIRecommendation(scanner, registry, search, analytics);
                    break;
                case 6:
                    chatAssistant.startChat();
                    break;
                case 7:
                    analytics.displayAnalytics();
                    break;
                case 8:
                    running = false;
                    System.out.println("Thank you for using DonorTrace. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 8.");
            }
        }
        scanner.close();
    }

    /**
     * Drives the "AI Recommendation" menu option: prompts for a blood group,
     * urgency, and optional patient location, searches the registry, then
     * scores and displays donors in AI-recommended priority order.
     */
    private static void runAIRecommendation(Scanner scanner, DonorRegistry registry,
                                             DonorSearch search, AIAnalytics analytics) {
        System.out.println("\n--- AI Donor Recommendation ---");

        String bloodGroup = search.promptBloodGroup();
        String urgency = search.promptUrgency();

        System.out.print("Enter patient location (optional, press Enter to skip): ");
        String patientLocation = scanner.nextLine().trim();

        // Record this search for AI Analytics as well
        analytics.recordSearch(bloodGroup);

        List<Donor> results = registry.searchDonors(bloodGroup);

        System.out.println("\nBlood Group: " + bloodGroup + " | Urgency: " + urgency);

        if (results.isEmpty()) {
            System.out.println("No eligible & available donors found for blood group " + bloodGroup + ".");
            return;
        }

        List<AIRecommendation.ScoredDonor> scored = AIRecommendation.scoreDonors(
                results, patientLocation.isEmpty() ? null : patientLocation);

        AIRecommendation.displayRecommendations(scored);
    }

    private static void printMenu() {
        System.out.println("\n----------- MAIN MENU -----------");
        System.out.println("1. Register Donor");
        System.out.println("2. Search Donor");
        System.out.println("3. View All Donors");
        System.out.println("4. View Donor Count");
        System.out.println("5. AI Recommendation");
        System.out.println("6. AI Assistant");
        System.out.println("7. AI Analytics");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int readChoice(Scanner scanner) {
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            return choice;
        } catch (NumberFormatException e) {
            return -1; // will trigger "Invalid choice" in switch default
        }
    }
}
