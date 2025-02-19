# TeyaLedgerApplication
TeyaLedgerApplication is a simple application designed to manage financial transactions. This application provides APIs to record money movements, view the current balance, and access the transaction history. Built using Java and Spring Boot, the project leverages in-memory data structures to ensure quick and seamless operation.


## Features
- Record money movements (deposits and withdrawals)
- View current balance
- View transaction history

## Technologies Used
- Java
- Spring Boot
- Gradle

## Getting Started

### Prerequisites
- Java 17 or higher
- Gradle
- IntelliJ IDEA (Optional)

### Installation
    1. Clone the repository:
        git clone https://github.com/aj-khan/TeyaLedgerApplication.git
    2. Navigate to the project directory:
        cd TeyaLedgerApplication
    3. Build the project:
        ./gradlew clean build

### Running the Application
    ./gradlew bootRun
  
### Steps to Import the project in IntelliJ IDEA (Assuming IntelliJ IDEA is already installed)
    1. Open Project: In the IntelliJ IDEA welcome screen, select Open.
    2. Select Project Directory: Navigate to the cloned TeyaLedgerApplication project directory and select it.
    3. Import Project: IntelliJ IDEA will detect the build.gradle file. Select Open as Project and follow the prompts to import the project.
    4. Wait for Dependencies: IntelliJ IDEA will download and resolve the project dependencies. This might take a few minutes.
    5. Open Main Class: Open the TeyaLedgerApplication.java file in the src/main/java/com/teya/ledger/ directory.
    6. Run the Application: Click the green play button next to the main method or right-click on the TeyaLedgerApplication class and select Run 'TeyaLedgerApplication'.
The application will start on http://localhost:8080.
### API Endpoints

- **Record a Transaction**
  - URL: /v1/ledger/transactions
  - Method: POST
  - Request Body:
    ```json
    {
      "type": "DEPOSIT",
      "amount": 100.0
    }
  - Success Response:
    - Code: 200 OK
    - Content: Transaction recorded successfully
  - Error Responses:
    - Code: 400 BAD REQUEST
    - Content: Insufficient balance for the withdrawal (if insufficient funds for withdrawal)
    - Content: Invalid amount for the transaction (if the transaction amount is invalid)


- **View Current Balance**
  - URL: /v1/ledger/balance
  - Method: GET
  - Success Response:
    - Code: 200 OK
    - Content: 100.0 (current balance)
    

- **View Transaction History**
  - URL: /v1/ledger/history
  - Method: GET
  - Success Response:
    - Code: 200 OK
    - Content:
    ```json  
    [
      {
      "id": "uuid",
      "type": "DEPOSIT",
      "amount": 100.0,
      "transactionTime": "transaction time"
      }
    ]
    ```
    - Content: No transactions to display (if no transactions)

### Examples
  - Record a Transaction
    -  **To record a deposit of 100 units:**
        ```sh 
       curl -X POST "http://localhost:8080/v1/ledger/transactions" -H "Content-Type: application/json" -d "{\"type\": \"DEPOSIT\", \"amount\": 100.0}"
        ```
        This will return : Transaction recorded successfully

    - **To record a withdrawal of 50 units:**
      ```sh
      curl -X POST "http://localhost:8080/v1/ledger/transactions" -H "Content-Type: application/json" -d "{\"type\": \"WITHDRAWAL\", \"amount\": 50.0}"
      ```
      This will return : Transaction recorded successfully

    - **To attempt to record a withdrawal with insufficient funds:**
      ```sh
      curl -X POST "http://localhost:8080/v1/ledger/transactions" -H "Content-Type: application/json" -d "{\"type\": \"WITHDRAWAL\", \"amount\": 150.0}"
      ```
      This should return an error message: Insufficient balance for the withdrawal.

  - View Current Balance

    - **To view the current balance:**
        ```sh
        curl -X GET "http://localhost:8080/v1/ledger/balance"
        ```
      This will return the current balance: 50.0.

  - View Transaction History
    - **To view the transaction history:**
      ```sh 
      curl -X GET "http://localhost:8080/v1/ledger/history"
      ```

      This will return the transaction history in JSON format:
      ```json
          [
            {
              "id": "uuid", 
              "type": "DEPOSIT",
              "amount": 100.0,
              "transactionTime": "transaction time"
            },
            {
              "id": "uuid", 
              "type": "WITHDRAWAL",
              "amount": 50.0,
              "transactionTime": "transaction time"
            }
          ]

      ```