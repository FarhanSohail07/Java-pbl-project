import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Donor {
    private String name;
    private int age;
    private String bloodGroup;
    private String location;
    private LocalDate lastDonationDate;
    private boolean available;

    public Donor(String name, int age, String bloodGroup, String location,
                 LocalDate lastDonationDate, boolean available) {
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.location = location;
        this.lastDonationDate = lastDonationDate;
        this.available = available;
    }

    // Getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getBloodGroup() { return bloodGroup; }
    public String getLocation() { return location; }
    public LocalDate getLastDonationDate() { return lastDonationDate; }
    public boolean isAvailable() { return available; }

    // Setters
    public void setAvailable(boolean available) { this.available = available; }
    public void setLastDonationDate(LocalDate lastDonationDate) { this.lastDonationDate = lastDonationDate; }

    /**
     * A donor is eligible if at least 90 days have passed since last donation.
     * If lastDonationDate is null, the donor has never donated -> eligible.
     */
    public boolean isEligible() {
        if (lastDonationDate == null) {
            return true;
        }
        long daysSinceDonation = ChronoUnit.DAYS.between(lastDonationDate, LocalDate.now());
        return daysSinceDonation >= 90;
    }

    public long daysUntilEligible() {
        if (lastDonationDate == null) return 0;
        long daysSince = ChronoUnit.DAYS.between(lastDonationDate, LocalDate.now());
        long remaining = 90 - daysSince;
        return Math.max(remaining, 0);
    }

    @Override
    public String toString() {
        String lastDonation = (lastDonationDate == null) ? "Never" : lastDonationDate.toString();
        String eligibility = isEligible() ? "Eligible" : ("Not Eligible (" + daysUntilEligible() + " days left)");
        return String.format(
            "Name: %-15s Age: %-3d Blood Group: %-4s Location: %-12s Last Donation: %-10s Available: %-5s Status: %s",
            name, age, bloodGroup, location, lastDonation, available, eligibility
        );
    }
}
