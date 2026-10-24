# ClearText — Sensitive Data Detector

**ClearText** is a web-based sensitive data detection and redaction tool built using HTML, CSS, JavaScript, Java, and Spring Boot. It helps users identify potentially sensitive information in text documents, review detected matches, selectively redact sensitive values, and export a sanitized copy of the document.

The application uses Java-based regular expression rules to detect email addresses, phone numbers, and identifier-like values. Its interactive interface highlights detected matches, displays detection statistics, and allows users to choose which values should be redacted.

## Features

- **Sensitive Data Detection:** Identifies email addresses, phone numbers, and identifier-like values using configurable detection rules.
- **Interactive Document Preview:** Highlights detected values directly within the source text.
- **Selective Redaction:** Allows users to select individual matches or select all detected values for redaction.
- **Multiple Redaction Styles:** Supports `[REDACTED]`, `[REDACTED: TYPE]`, and character masking.
- **Sample Documents:** Includes sample email threads, HR records, and mixed documents for demonstration and testing.
- **Text File Upload:** Supports uploading `.txt` files for scanning.
- **Export Functionality:** Downloads the resulting text as a `.txt` file.
- **Real-Time Result Updates:** Displays match counts by category and updates the selected findings count.
- **Spring Boot Integration:** Connects the frontend to backend REST endpoints for scanning and redaction.
- **Responsive Interface:** Provides a structured workspace for entering source text and reviewing detection results.

## Technology Stack

- **Frontend:** HTML5, CSS3, JavaScript
- **Backend:** Java, Spring Boot
- **Detection Engine:** Java Regular Expressions (Regex)
- **Communication:** REST API, JSON
- **File Handling:** JavaScript File API and Blob-based text export

## Application Workflow

1. **Input:** Paste text into the editor, upload a `.txt` file, or select a sample document.
2. **Configure:** Enable or disable email, phone, and identifier detection rules.
3. **Scan:** Send the document to the Spring Boot backend through the `/api/scan` endpoint.
4. **Review:** Examine highlighted matches, detection categories, and match counts.
5. **Select:** Choose the specific findings that need redaction.
6. **Redact:** Submit the selected matches to the `/api/redact` endpoint and choose a redaction style.
7. **Export:** Download the resulting text as a `.txt` file.

## API Endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/scan` | Scans text and returns detected matches and statistics. |
| POST | `/api/redact` | Redacts selected matches according to the chosen style. |

## Getting Started

### Prerequisites

- Java Development Kit (JDK)
- Maven, if the backend project uses Maven
- A modern web browser

### Installation and Execution

1. Clone the repository:

   ```bash
   git clone <YOUR_REPOSITORY_URL>
   ```

2. Open the Spring Boot project in your preferred IDE.
3. Configure the application and run the Spring Boot backend.
4. Open the frontend HTML file using a suitable local development server or configure it to be served by Spring Boot.
5. Ensure the frontend requests are routed to the running backend.
6. Open the application in your browser and scan a sample document to test the workflow.

**Note:** The frontend expects the backend endpoints `/api/scan` and `/api/redact` to be available at the same origin or through an appropriately configured proxy. If the frontend and backend run on different origins, configure the required API base URL and CORS settings.

## Limitations

- Detection relies on regular expressions and may produce false positives or miss unusual formats.
- Identifier detection is heuristic-based and does not guarantee that every identifier is sensitive.
- The current prototype focuses on plain-text documents and `.txt` uploads.
- It is a demonstration project, not a complete production-grade Data Loss Prevention (DLP) solution.

## Future Enhancements

- Support for PDF and DOCX document parsing.
- More configurable detection rules and custom sensitive-data patterns.
- Improved detection accuracy and evaluation using labelled datasets.
- Authentication and role-based access control.
- Audit logging and secure data-retention policies.
- Automated testing and performance evaluation.
- Enhanced reporting and batch document processing.

## Project Objective

The objective of ClearText is to demonstrate how a lightweight web application can combine a Java Spring Boot backend with an interactive frontend to detect, review, and redact potentially sensitive information before text is shared or exported.

**Disclaimer:** This application is intended for educational and demonstration purposes. Do not rely on it as the sole mechanism for protecting confidential or regulated information.
