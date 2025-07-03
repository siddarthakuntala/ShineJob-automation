Shine.com Job Application Automation (Maven Project)
This project automates the process of logging into Shine.com, searching for jobs, and applying to a selected job using Selenium WebDriver in Java. It simulates a complete job search experience, from login to job application, making it ideal for demonstrating real-world browser automation.

📌 Features
Automated Login: Automatically logs in to Shine.com using valid email and password credentials.

Job Search: Inputs job title, location, and experience details in the search form.

Dynamic Interaction: Handles modal popups (like skill suggestions) and interacts with dynamic web elements smoothly.

Multi-tab Handling: Opens job listings in new tabs and switches control to the active tab for further actions.

Auto-Apply: Automatically clicks the "Apply" button on the second job listing, if available.

Screenshot Support: Captures screenshots after each major step for tracking and debugging (/screenshots folder).

Robust Wait Strategy: Utilizes WebDriverWait to handle dynamic content and improve stability.

🛠️ Tech Stack
Java

Selenium WebDriver

Maven (for dependency management)

WebDriverManager (for automatic driver configuration)

📂 Project Structure
shineAutomation.java: Main automation script.

screenshots/: Folder containing saved screenshots from the automation process.

🖥️ How It Works
Launches Chrome using WebDriver.

Logs in using Shine credentials.

Searches for "Software Testing" jobs in "Hyderabad" with 2 years of experience.

Clicks the 2nd job listing.

Switches to the job tab and attempts to apply.

Handles popups, delays, and exceptions effectively.

⚠️ Note
Replace the login credentials with your own before running.

Ensure Google Chrome is installed and compatible with WebDriverManager.

This automation is for educational and demo purposes only. Do not misuse job portals.
