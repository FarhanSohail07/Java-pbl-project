import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * AIRecommendation calculates a simple rule-based "AI-style" recommendation
 * score for a list of matching donors, and maps overall search urgency to a
 * recommended response priority.
 *
 * Scoring rules:
 *   Available                       = +40
 *   Eligible (90-day rule)          = +30
 *   Same location as the patient    = +20
 *   Age between 18 and 40 (inclusive) = +10
 */
public class AIRecommendation {

    /**
     * Simple holder class that pairs a Donor with its calculated score
     * and the list of human-readable reasons behind that score.
     */
    public static class ScoredDonor {
        private Donor donor;
        private int score;
        private List<String> reasons;

        public ScoredDonor(Donor donor, int score, List<String> reasons) {
            this.donor = donor;
            this.score = score;
            this.reasons = reasons;
        }

        public Donor getDonor() { return donor; }
        public int getScore() { return score; }
        public List<String> getReasons() { return reasons; }
    }

    /**
     * Calculates a recommendation score (and the reasons behind it) for every
     * donor in the provided list, then returns the donors sorted in
     * descending order of score (highest score first).
     *
     * @param donors           list of donors to score (already matched by blood group)
     * @param patientLocation  the location of the patient/searcher; can be null or empty
     *                         if location-based scoring should be skipped
     */
    public static List<ScoredDonor> scoreDonors(List<Donor> donors, String patientLocation) {
        List<ScoredDonor> scoredList = new ArrayList<>();

        for (Donor d : donors) {
            int score = 0;
            List<String> reasons = new ArrayList<>();

            if (d.isAvailable()) {
                score += 40;
                reasons.add("Available (+40)");
            }

            if (d.isEligible()) {
                score += 30;
                reasons.add("Eligible - 90 day rule (+30)");
            }

            if (patientLocation != null && !patientLocation.trim().isEmpty()
                    && d.getLocation().equalsIgnoreCase(patientLocation.trim())) {
                score += 20;
                reasons.add("Same location as patient (+20)");
            }

            if (d.getAge() >= 18 && d.getAge() <= 40) {
                score += 10;
                reasons.add("Age between 18-40 (+10)");
            }

            scoredList.add(new ScoredDonor(d, score, reasons));
        }

        // Sort donors in descending order of score using a Comparator
        scoredList.sort(new Comparator<ScoredDonor>() {
            @Override
            public int compare(ScoredDonor a, ScoredDonor b) {
                return b.getScore() - a.getScore();
            }
        });

        return scoredList;
    }

    /**
     * Maps a numeric recommendation score to an AI Emergency Mode priority label.
     */
    public static String getPriority(int score) {
        if (score >= 90) {
            return "Immediate Response";
        } else if (score >= 70) {
            return "Fast Response";
        } else if (score >= 50) {
            return "Moderate Response";
        } else {
            return "Low Priority";
        }
    }

    /**
     * Displays the scored/sorted donor list along with the score, reasons,
     * and AI Emergency Mode priority for each donor. Also prints the
     * recommended contact order.
     */
    public static void displayRecommendations(List<ScoredDonor> scoredDonors) {
        if (scoredDonors.isEmpty()) {
            System.out.println("No donors available to recommend.");
            return;
        }

        System.out.println("\n--- AI Recommended Donor Order ---");
        int rank = 1;
        for (ScoredDonor sd : scoredDonors) {
            System.out.println("\nRank " + rank + ": " + sd.getDonor());
            System.out.println("   Score: " + sd.getScore() + " | Priority: " + getPriority(sd.getScore()));
            System.out.println("   Reasons: " + String.join(", ", sd.getReasons()));
            rank++;
        }
    }
}
