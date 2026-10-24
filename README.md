# ClearText — Sensitive Data Detector (Spring Boot)

A prototype that scans text for email-shaped strings, phone-like sequences, and identifier-like values, shows match positions, and redacts selected matches.

## Requirements
- Java 17+
- Maven 3.9+ (or IntelliJ IDEA with Maven support)
- Browser

## Run
From this directory:

```bash
mvn spring-boot:run
```

Open http://localhost:8080

## REST API
### `GET /api/health`
Returns a simple health status.

### `POST /api/scan`
Example body:
```json
{
  "text": "Contact jane@example.com, Employee ID: EMP-12345, phone +1 (415) 555-0136",
  "detectEmails": true,
  "detectPhones": true,
  "detectIdentifiers": true
}
```

Returns counts and a list of matches with `index`, `start`, `end`, `value`, and `type`.

### `POST /api/redact`
Send the original text, the matches returned by `/api/scan`, the indexes to redact, and a style (`BLOCK`, `TYPE`, or `MASK`).

## Demo workflow
1. Load one of the synthetic samples or choose a `.txt` file under 1 MB.
2. Scan document.
3. Select/deselect individual matches.
4. Apply redactions.
5. Export the redacted copy.

## Design notes and limitations
- Matching uses Java regular expressions; it is not an AI/ML model.
- Regex can create false positives and false negatives. The broad identifier rules are heuristic.
- Current frontend accepts plain-text files only. PDF/DOCX parsing is not included.
- Text is sent to the Spring Boot server on localhost. No database is used.
- Before production use, add stronger input validation, security controls, labelled evaluation data, audit policies, and jurisdiction-specific identifier rules.
