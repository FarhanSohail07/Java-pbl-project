import java.util.List;
import java.util.Scanner;

/**
 * AIChatAssistant provides a very simple natural-language interface.
 * The user can type free-form sentences such as "I need O+ blood" or
 * "Find A-", and the assistant detects the blood group mentioned using
 * basic String.contains() checks, automatically searches the registry,
 * records the search for analytics, and shows the top AI-recommended donor.
 */
public class AIChatAssistant {

    private Scanner scanner;
    private DonorRegistry registry;
    private AIAnalytics analytics;

    // Ordered so that longer codes (e.g. "AB+") are checked before shorter
    // ones (e.g. "A+") to avoid incorrect partial matches.
    private static final String[] VALID_BLOOD_GROUPS = {
        "AB+", "AB-", "A+", "A-", "B+", "B-", "O+", "O-"
    };

    public AIChatAssistant(Scanner scanner, DonorRegistry registry, AIAnalytics analytics) {
        this.scanner = scanner;
        this.registry = registry;
        this.analytics = analytics;
    }

    /**
     * Starts the AI Assistant conversation loop for a single request:
     * reads a natural-language line, detects the blood group, performs
     * the search, and displays the best recommended donor.
     */
    public void startChat() {
        System.out.println("\n--- AI Assistant ---");
        System.out.println("Tell me what you need, e.g. \"I need O+ blood\" or \"Find A-\".");
        System.out.print("You: ");
        String input = scanner.nextLine().trim();

        String bloodGroup = detectBloodGroup(input);

        if (bloodGroup == null) {
            System.out.println("AI Assistant: Sorry, I couldn't detect a valid blood group in your message.");
            System.out.println("AI Assistant: Please mention one of: A+, A-, B+, B-, AB+, AB-, O+, O-.");
            return;
        }

        System.out.println("AI Assistant: Detected blood group -> " + bloodGroup);

        // Record this search for analytics purposes
        analytics.recordSearch(bloodGroup);

        List<Donor> results = registry.searchDonors(bloodGroup);

        if (results.isEmpty()) {
            System.out.println("AI Assistant: No eligible & available donors found for blood group " + bloodGroup + ".");
            return;
        }

        // Score and rank the matching donors (no specific patient location known here)
        List<AIRecommendation.ScoredDonor> scored = AIRecommendation.scoreDonors(results, null);
        AIRecommendation.ScoredDonor best = scored.get(0);

        System.out.println("AI Assistant: Here is the best recommended donor for you:");
        System.out.println(best.getDonor());
        System.out.println("Score: " + best.getScore()
                + " | Priority: " + AIRecommendation.getPriority(best.getScore()));
        System.out.println("Reasons: " + String.join(", ", best.getReasons()));
    }

    /**
     * Scans the user's free-form input for a mention of a valid blood group
     * using simple case-insensitive String.contains() checks.
     * Returns the detected blood group, or null if none is found.
     */
    private String detectBloodGroup(String input) {
        String upperInput = input.toUpperCase().replaceAll("\\s+", "");
        for (String bg : VALID_BLOOD_GROUPS) {
            if (upperInput.contains(bg)) {
                return bg;
            }
        }
        return null;
    }
}
