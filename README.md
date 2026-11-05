# Accounting Ledger Application

A command-line interface (CLI) application built in Java to track financial transactions for personal or business use. All transaction data is automatically read from and saved to a pipe-delimited CSV file (`transactions.csv`).

---

## Features

- **Home Screen Navigation**
    - `D) Add Deposit`: Prompt for deposit details and save to the transaction history.
    - `P) Make Payment (Debit)`: Prompt for payment details and save negative amounts automatically.
    - `L) Ledger`: Access full transaction records and reporting options.
    - `X) Exit`: Safely terminate the application.

- **Ledger Screen** *(Displays newest entries first)*
    - `A) All`: View all recorded entries.
    - `D) Deposits`: Filter and view only positive transactions (deposits).
    - `P) Payments`: Filter and view only negative transactions (debits/payments).
    - `R) Reports`: Access predefined or custom search reports.
    - `H) Home`: Return to the main menu.

- **Reports Sub-Menu**
    - `1) Month To Date`: Display transactions from the current month.
    - `2) Previous Month`: Display transactions from the prior month.
    - `3) Year To Date`: Display transactions from the current year.
    - `4) Previous Year`: Display transactions from the prior year.
    - `5) Search by Vendor`: Filter transactions by a specific vendor name.
    - `0) Back`: Return to the Ledger menu.

---

## File Format

Transactions are stored in `transactions.csv` using the following pipe-delimited (`|`) format:

```text
date|time|description|vendor|amount
2026-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50
20236-04-15|11:15:00|Invoice 1001 paid|Joe|1500.00