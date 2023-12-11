import java.util.Locale;

public class CreditAccount {

    private String accountNum;//Credit Card account number
    private double available;//Current available credit
    private double maxLimit;/*Maximum credit for the account. (At all times, the
    available amount must be less than or equal to the
    maxLimit amount.)*/

    private String issuerSymbol;//Credit Card Issuer Symbol
    private boolean accountValid;//Flag to identify a “valid” vs. “invalid” account.
//Credit Card issuer Symbols
    public static final String ISSUER_AMER_EXPRESS = "AE";// American Express

    public static final String ISSUER_VISA = "V";//visa
    public static final String ISSUER_MASTER_CARD = "MC";//master card
    public static final String ISSUER_DISCOVER = "DIS";// discover
    public static final String ISSUER_DINERS_CLUB = "DINE";//Diners Club
//Credit Card issuer Codes – first digit of account number
    public static final int ISSUER_CODE_AE = 3; //American Express
    public static final int ISSUER_CODE_V = 4;//visa
    public static final int ISSUER_CODE_MC = 5;//master card
    public static final int ISSUER_CODE_DIS = 6;//discover
    public static final int ISSUER_CODE_DINE = 7;//diners club

    public CreditAccount() {
    }
///Constructor for creating a new account
    public CreditAccount(String issuerSymbol) {
        this.issuerSymbol = issuerSymbol;

     //Check issuer symbol and return
        //corresponding issuer code
        int issuerCode = getIssuerCode(issuerSymbol);

        long leftLimit = 111111111111111L;
        long rightLimit = 999999999999999L;

        this.accountNum = issuerCode + String.valueOf(leftLimit + (long) (Math.random() * (rightLimit - leftLimit)));
        int lastDigit = Integer.parseInt(this.accountNum.substring(15));
        if (lastDigit >= 0 && lastDigit <= 4) {
            this.maxLimit = 1000;
            this.available = 1000;
        } else {
            this.maxLimit = 500;
            this.available = 500;
        }
    }
//Constructor for loading records
//from the data file
    public CreditAccount(String recordText, int recordLength) {

    }
//Create a string in the format
//suitable for writing to the data
//file.
    public String assembleRecordText() {
        return String.format("%s|%8s|%8s",
                accountNum,
                String.format("%.2f", available),
                String.format("%.2f", maxLimit));
    }
//getters
    public double getAvailable() {
        return available;
    }
//setters account num
    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }
 //setter available
    public void setAvailable(double available) {
        this.available = available;
    }
//setter maxlimit
    public void setMaxLimit(double maxLimit) {
        this.maxLimit = maxLimit;
    }
// getters
    public String getAccountNum() {
        return accountNum;
    }

    public double getMaxLimit() {
        return maxLimit;
    }
// issuer code
    public static int getIssuerCode(String issuerSymbol) {
        switch (issuerSymbol.toUpperCase(Locale.ROOT)) {
            case ISSUER_AMER_EXPRESS:
                return ISSUER_CODE_AE;
            case ISSUER_VISA:
                return ISSUER_CODE_V;
            case ISSUER_MASTER_CARD:
                return ISSUER_CODE_MC;
            case ISSUER_DISCOVER:
                return ISSUER_CODE_DIS;
            case ISSUER_DINERS_CLUB:
                return ISSUER_CODE_DINE;
        }
        return -1;
    }
    //issuer symbol
    public static String getIssuerSymbol(int issuerCode) {
        switch (issuerCode) {
            case ISSUER_CODE_AE:
                return ISSUER_AMER_EXPRESS;
            case ISSUER_CODE_V:
                return ISSUER_VISA;
            case ISSUER_CODE_MC:
                return ISSUER_MASTER_CARD;
            case ISSUER_CODE_DIS:
                return ISSUER_DISCOVER;
            case ISSUER_CODE_DINE:
                return ISSUER_DINERS_CLUB;
        }
        return "";
    }
    @Override
    public String toString() {
        return "CreditAccount{" +
                "accountNum='" + accountNum + '\'' +
                ", available=" + available +
                ", maxLimit=" + maxLimit +
                '}';
    }
}