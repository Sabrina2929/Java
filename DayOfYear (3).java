/**
 * CSC-239 Project 1:Day of the Year
 * Student: Sabrina Soltani
 * Date: 10/04/2022
 * Description: This program prompts the user to input a string of text
 * representing a numeric date in the following format:
 * MM DD YYYY
 * The program outputs the day of that particular year that
 * the string represents.
 */
import java.util.Scanner;
import java.text.DecimalFormat;

public class DayOfYear {
    /** Main method */
    public static void main(String[] args) {
        // Create a Scanner
        Scanner scan=new Scanner(System.in);
        // Prompt the user to enter accepts numeric date in the format MM DD YYYY.
        System.out.println("This program accepts numeric dates in the format MM DD YYYY");
        while(true){
            // Prompt the user to enter numeric date or exit program.
            System.out.print("Please enter numeric date (or q to exit program): ");
            String inputDate=scan.nextLine();
            if(inputDate.equalsIgnoreCase("q")) {
                System.out.println("Exit program.");
                break;
            }
            int m; int d; int y;
            try{
                String[] data=inputDate.split(" ");
                m=Integer.parseInt(data[0]);
                d=Integer.parseInt(data[1]);
                y=Integer.parseInt(data[2]);
            }
            catch(NumberFormatException ex){
                System.out.println("Non-numeric date entered: "+inputDate);
                continue;
            }
            if(isCorrectMonth(m, d, y)){
                int elapsedDays = d;
                for(int i=1; i<m; i++){
                    elapsedDays += getNumberOfDays(i, y);
                }
                if(elapsedDays == 1)

                    System.out.println("1 day has elapsed from Jan 01," +
                            " "+ y +" to "+toString(inputDate) + ".");
                else
                    System.out.println(elapsedDays+" days have elapsed from Jan 01, "
                            + y +" to "+toString(inputDate) + ".");
            }
            else{
                DecimalFormat df = new DecimalFormat("00");
                System.out.println("ERROR: Invalid Date string \""
                        + inputDate +"\" resulted in invalid numeric date: ");
                System.out.println("month = " + df.format(+m)+ ", day = " +d+
                        ", year = "+y + "\n");
            }
        }
    }
    static boolean isCorrectYear(int y){
        return (y>=1900 && y<=2100);
    }
    // Leap Year
    static boolean isLeapYear(int y){
        if(y%100 == 0){
            return y % 400 == 0;
        }
        else{
            return (y%4 == 0);
        }
    }
    // Function will return total number of days
    static int getNumberOfDays(int m, int y){
        //months which has 31 days
        if(m==1 || m==3 || m == 5||
                m==7 || m==8 || m==10 || m==12)
            return 31;
        //leap year condition, if month is two
        else if(m == 2){
            if(isLeapYear(y))
                return 29;
            return 28;
        }
        return 30;
    }

    static boolean isCorrectMonth(int m, int d, int y){
        if(!(m>=1 && m<=12)){
            System.out.println("ERROR: Invalid numeric month: "+m);
            return false;
        }
        //month is not valid
        if(!isCorrectYear(y)){
            System.out.println("ERROR: Invalid numeric year: "+y+ "." +
                    " Year must be between 1900 and 2100.");
            return false;
        }
        if(!(d>=1 && d<= getNumberOfDays(m, y))){
            System.out.println("ERROR: Invalid day "+d+ "," +
                    " for numeric month: "+m+" (year = "+y+")");
            return false;
        }
        return true;
    }
    static String toString(String input) {
        //Split
        String[] data = input.split(" ");
        int m = Integer.parseInt(data[0]);
        String monthInWords = "";

        switch (m) {
            case 1 -> monthInWords += "Jan";
            case 2 -> monthInWords += "Feb";
            case 3 -> monthInWords += "Mar";
            case 4 -> monthInWords += "Apr";
            case 5 -> monthInWords += "May";
            case 6 -> monthInWords += "Jun";
            case 7 -> monthInWords += "Jul";
            case 8 -> monthInWords += "Aug";
            case 9 -> monthInWords += "Sep";
            case 10 -> monthInWords += "Oct";
            case 11 -> monthInWords += "Nov";
            case 12 -> monthInWords += "Dec";
        }
      return monthInWords +" "+ data[1] + ", "+data[2];
    }
}