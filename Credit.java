import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Credit {

    private static ArrayList<CreditAccount> creditAccounts = new ArrayList<>();
    private static Scanner scn = new Scanner(System.in);

    private static CreditAccount foundAccount = null;// credit account
    private static String issuerSymbol = "";// issuer symbol

    private static String accountNumber = "";// account number
    private static double amount = 0; /// amount
    private static String newFileName = ""; // new file

    public static void main(String[] args) {
        File inputFile = new File("C:\\Users\\sabroso\\Documents\\CSC239 fall22\\project2\\dataFile.txt");//file location
        if (inputFile.exists() && !inputFile.isDirectory()) {
            newFileName = renameWithTimestamp();
        }
        showHelp();
        loadData();//loading data
        //create new credit account
        new CreditAccount("AE");
        new CreditAccount("V");
        new CreditAccount("MC");
         // command
        while (true) {
            System.out.println();
            System.out.print("Command: ");
            String command = scn.nextLine();
            System.out.println(String.format(" userInput=%s", command));
            if (command.contains("create")) {// command create
                String[] line = command.split(" ");
                if (line.length == 2) {
                    int issuerCode = CreditAccount.getIssuerCode(line[1]);
                    if (issuerCode != -1) {
                        System.out.println(String.format("%d record(s) read from input file.", creditAccounts.size()));
                        issuerSymbol = CreditAccount.getIssuerSymbol(issuerCode);
                        createAccount();
                        System.out.println(String.format("%d record(s) written to output file.", creditAccounts.size()));
                    } else {
                        System.out.println("Invalid issuer symbol.");
                    }
                } else {
                    System.out.println("Invalid command.");
                }
            } else if (command.contains("verify")) {
                String[] line = command.split(" ");
                if (line.length == 3) {
                    if (line[1].length() == 16) {
                        accountNumber = line[1];
                        amount = Double.parseDouble(line[2].trim());
                        authorizeTransaction();
                    } else {
                        System.out.println("Invalid account number.");
                    }
                } else {
                    System.out.println("Invalid command.");
                }
            } else if (command.equalsIgnoreCase("q")) {
                writeData();
                System.exit(0);
            } else if (command.equalsIgnoreCase("help")) {
                showHelp();
            }
        }
    }
    private static CreditAccount findAccount(String accountNumber) {
        for (CreditAccount acc : creditAccounts) {
            if (acc.getAccountNum().equals(accountNumber)) {
                return acc;
            }
        }
        return null;
    }

    private static void showHelp() {
        System.out.println("This program accepts the following inputs and performs the corresponding actions: USAGE:");
        System.out.println(String.format("%-30s%-30s%-30s", "command parameter(s)", "COMMAND", "PARAMETER(S)"));
        System.out.println(String.format("%-30s%-30s%-30s", "help", "OUTPUT this help text.", ""));
        System.out.println(String.format("%-30s%-30s%-30s", "create issuerSymbol", "CREATE new account.", "issuerSymbol"));
        System.out.println(String.format("%-30s%-30s%-30s", "verify accountNum amount", "VERIFY a purchase or credit.", "accountNum, amount"));
        System.out.println(String.format("%-30s%-30s%-30s", "q", "EXIT the program.", ""));


        System.out.println();
        System.out.println("The command and issuerSymbol values are NOT case sensitive.");
        System.out.println("The following credit cards are supported:");
        System.out.println(String.format("%-20s%-20s", "CARD", "SYMBOL"));
        System.out.println(String.format("%-20s%-20s", "American Express", CreditAccount.ISSUER_AMER_EXPRESS));
        System.out.println(String.format("%-20s%-20s", "Visa", CreditAccount.ISSUER_VISA));
        System.out.println(String.format("%-20s%-20s", "MasterCard", CreditAccount.ISSUER_MASTER_CARD));
        System.out.println(String.format("%-20s%-20s", "Discover", CreditAccount.ISSUER_DISCOVER));
        System.out.println(String.format("%-20s%-20s", "Diners Club", CreditAccount.ISSUER_DINERS_CLUB));
    }

    private static void loadData() {

        File inputFile = new File("C:\\Users\\sabroso\\Documents\\CSC239 fall22\\project2\\dataFile.txt\"");
        if (inputFile.exists() && !inputFile.isDirectory()) {
            try {
                Scanner fileReader = new Scanner(inputFile);
                while (fileReader.hasNext()) {
                    String[] line = fileReader.nextLine().split("\\|");
                    String accountNumber = line[0];
                    double available = Double.parseDouble(line[1].trim());
                    double maxLimit = Double.parseDouble(line[2].trim());

                    CreditAccount acc = new CreditAccount();
                    acc.setAccountNum(accountNumber);
                    acc.setAvailable(available);
                    acc.setMaxLimit(maxLimit);

                    System.out.println(acc);

                    creditAccounts.add(acc);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
//loadData: loads data from the input file.
//• writeData: writes data to the output file.
//• createAccount: creates a new account in memory.
//• authorizeTransaction: verifies a transaction and modifies the “current available credit” amount.
    private static void writeData() {
        //C:\Users\sabroso\Documents\CSC239 fall22\project2
        File inputFile = new File("C:\\Users\\sabroso\\Documents\\CSC239 fall22\\project2\\dataFile.txt");
        if (!inputFile.exists()) {
            try {
                PrintWriter writer = new PrintWriter(inputFile);

                for (CreditAccount acc : creditAccounts) {
                    writer.println(acc.assembleRecordText());
                }
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            inputFile = new File(newFileName);

            try {
                PrintWriter writer = new PrintWriter(inputFile);

                for (CreditAccount acc : creditAccounts) {
                    writer.println(acc.assembleRecordText());
                }
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

//• renameWithTimestamp: renames the existing data file (dataFile.txt) with a timestamp in the file name.
    private static String renameWithTimestamp() {
        return String.format("C:\\Users\\sabroso\\Documents\\CSC239 fall22\\project2\\dateFile_%s.txt", DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss", Locale.getDefault()).format(LocalDateTime.now()));
    }
// create account
    private static void createAccount() {
        CreditAccount newAccount = new CreditAccount(issuerSymbol);
        System.out.println(String.format("New account created for credit card symbol %s: account number=%s, credit limit=$%.2f",
                issuerSymbol,
                newAccount.getAccountNum(),
                newAccount.getMaxLimit()));
        creditAccounts.add(newAccount);
    }
////transaction acceptance or rejection
    private static void authorizeTransaction() {
        foundAccount = findAccount(accountNumber);

        if (foundAccount != null) {
            double newBalance = 0;
            if (amount > 0) {
                System.out.println(foundAccount.getAvailable());
                System.out.println(amount);
                newBalance = foundAccount.getAvailable() - amount;

                if (newBalance >= 0) {
                    System.out.println(String.format("%d record(s) read from input file.", creditAccounts.size()));

                    foundAccount.setAvailable(newBalance);
                    System.out.println(String.format("AUTHORIZATION GRANTED (accountNum=%s, transactionAmount=$%.2f, available credit=$%.2f)", foundAccount.getAccountNum(), amount, newBalance));
                    System.out.println(String.format("%d record(s) written to output file.", creditAccounts.size()));
                } else {
                    System.out.println("AUTHORIZATION DENIED - You cannot spend more than the available balance of your credit card.");
                }

            } else if (amount < 0) {
                newBalance = foundAccount.getAvailable() + Math.abs(amount);
                if (newBalance >= foundAccount.getMaxLimit()) {
                    System.out.println(String.format("%d record(s) read from input file.", creditAccounts.size()));

                    foundAccount.setAvailable(newBalance);
                    System.out.println(String.format("AUTHORIZATION GRANTED (accountNum=%s, transactionAmount=$%.2f, available credit=$%.2f)", foundAccount.getAccountNum(), amount, newBalance));
                    System.out.println(String.format("%d record(s) written to output file.", creditAccounts.size()));
                } else {
                    System.out.println("AUTHORIZATION DENIED - You cannot refund more than the max limit of your credit card.");
                }
            }
        } else {
            System.out.println("ACCOUNT NOT ON FILE");
        }
    }
}