# DonorTrace – Blood Donor Management System

A Java console application that helps patients and volunteers find available, eligible blood donors quickly. It supports donor registration, eligibility-based searching, rule-based donor recommendation, natural-language requests and search analytics.

Built as a Project-Based Learning (PBL) project for **Java Programming (IT5304)**, Department of Information Technology, Chennai Institute of Technology, Chennai.

---

## Features

- **Donor registration** with input validation (name, age, blood group, location, last donation date)
- **Search by blood group** and urgency level (Normal / Urgent / Critical)
- **90-day donation-gap check** using `LocalDate` and `ChronoUnit` to decide eligibility
- **View all donors** and the donor count
- **AI Recommendation**: a score-based, explainable ranking of eligible donors with a priority label
- **AI Assistant**: understands simple free-text requests such as "I need O+ blood" (rule-based, no external AI service)
- **AI Analytics**: tracks searches per blood group and shows the most-searched groups
- **Menu-driven console interface**

## Tech Stack

| Item | Details |
|---|---|
| Language | Java (JDK 17) |
| Concepts | OOP (encapsulation, abstraction, polymorphism), Collections Framework (`ArrayList`, `HashMap`), `Comparator` sorting, exception handling |
| Date handling | `java.time` (`LocalDate`, `ChronoUnit`) |
| Storage | In-memory (data is lost when the program exits) |
| IDE | IntelliJ IDEA / Eclipse / VS Code |

## Project Structure

```
DonorTrace/
├── src/
│   ├── Donor.java               # Donor entity
│   ├── DonorRegistry.java       # Stores donors (ArrayList)
│   ├── DonorRegistration.java   # Input and validation
│   ├── DonorSearch.java         # Blood-group and urgency search
│   ├── AIRecommendation.java    # Scoring and ranking of donors
│   ├── AIChatAssistant.java     # Natural-language request handling
│   ├── AIAnalytics.java         # Search statistics (HashMap)
│   └── DonorTraceApp.java       # Main class with the menu
└── README.md
```

> File names may differ slightly in the repository; adjust this tree to match.

## How to Run

**Requirements:** JDK 17 or later.

```bash
# clone the repository
git clone https://github.com/<your-username>/<repo-name>.git
cd <repo-name>

# compile
javac -d out src/*.java

# run
java -cp out DonorTraceApp
```

## How It Works

1. Register donors through the menu (inputs are validated).
2. Search by blood group and urgency. Only donors who pass the eligibility rules, including the 90-day gap, are shown.
3. The AI Recommendation module scores and ranks the eligible donors and explains the ranking.
4. The AI Assistant accepts plain-text requests and runs the matching search.
5. AI Analytics records each search and reports the most demanded blood groups.

## Testing

Verified through functional testing of valid and invalid inputs, covering registration, search, eligibility, recommendation, the AI Assistant and analytics. Details are in the project report.

## Limitations

- Data is stored in memory only, with no database or file persistence.
- The "AI" features are rule-based and do not use machine learning.
- Console interface only, with no GUI or web front end.
- No real-time contact (calls or SMS) with donors.

## Future Scope

- Persistent storage using JDBC and MySQL
- Mobile or web front end
- SMS and email notifications to donors
- Location-based matching (GPS)
- Real machine-learning demand prediction

## Authors

- **J Farhan Sohail** (210425205059)
- **Dheeban V** (210425205036)

**Mentor:** Dr. D. Lita Pansy, Assistant Professor, Department of Information Technology
Chennai Institute of Technology, Chennai (affiliated to Anna
