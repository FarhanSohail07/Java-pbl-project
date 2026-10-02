import java.util.HashMap;
import java.util.Map;

/**
 * AIAnalytics tracks how many times each blood group has been searched
 * (via Donor Search or AI Assistant) and provides simple insights such
 * as the most searched blood group and a donation-drive recommendation.
 */
public class AIAnalytics {

    // Maps blood group -> number of times it was searched
    private HashMap<String, Integer> searchCounts;

    public AIAnalytics() {
        searchCounts = new HashMap<>();
    }

    /**
     * Records a search for the given blood group by incrementing its counter.
     * If the blood group has not been searched before, it is initialized to 1.
     */
    public void recordSearch(String bloodGroup) {
        if (bloodGroup == null || bloodGroup.isEmpty()) {
            return;
        }
        bloodGroup = bloodGroup.toUpperCase();
        searchCounts.put(bloodGroup, searchCounts.getOrDefault(bloodGroup, 0) + 1);
    }

    /**
     * Finds and returns the blood group with the highest search count.
     * Returns null if no searches have been recorded yet.
     */
    public String getMostSearchedBloodGroup() {
        String mostSearched = null;
        int maxCount = -1;
        for (Map.Entry<String, Integer> entry : searchCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostSearched = entry.getKey();
            }
        }
        return mostSearched;
    }

    /**
     * Displays a full analytics report: per-blood-group search counts,
     * the most searched blood group, and a simple AI-style recommendation
     * encouraging donor registrations for that blood group.
     */
    public void displayAnalytics() {
        System.out.println("\n--- AI Analytics: Blood Group Search Trends ---");

        if (searchCounts.isEmpty()) {
            System.out.println("No searches have been performed yet.");
            return;
        }

        for (Map.Entry<String, Integer> entry : searchCounts.entrySet()) {
            System.out.println("Blood Group: " + entry.getKey() + "  ->  Searched " + entry.getValue() + " time(s)");
        }

        String mostSearched = getMostSearchedBloodGroup();
        System.out.println("\nMost Searched Blood Group: " + mostSearched);
        System.out.println("AI Recommendation: Encourage more " + mostSearched + " donor registrations.");
    }
}
