import java.util.ArrayList;
import java.util.List;

public class DonorRegistry {
    private ArrayList<Donor> donors;

    public DonorRegistry() {
        donors = new ArrayList<>();
    }

    public void addDonor(Donor donor) {
        donors.add(donor);
    }

    public int getDonorCount() {
        return donors.size();
    }

    public void viewAllDonors() {
        if (donors.isEmpty()) {
            System.out.println("No donors registered yet.");
            return;
        }
        System.out.println("\n--- All Registered Donors ---");
        for (int i = 0; i < donors.size(); i++) {
            System.out.println((i + 1) + ". " + donors.get(i));
        }
    }

    /**
     * Search donors by blood group, availability, and 90-day eligibility.
     */
    public List<Donor> searchDonors(String bloodGroup) {
        List<Donor> matches = new ArrayList<>();
        for (Donor d : donors) {
            if (d.getBloodGroup().equalsIgnoreCase(bloodGroup)
                    && d.isAvailable()
                    && d.isEligible()) {
                matches.add(d);
            }
        }
        return matches;
    }

    public List<Donor> getAllDonors() {
        return donors;
    }
}
