
/*
METROPOLITAN COMMUNITY COLLEGE
CSIS 222–OBJECT-ORIENTED PROGRAMMING IN JAVA
HOMEWORK01–COUNTS AS PROGRAMMING PROJECT (ASSIGNMENT)
Author: Juan Ramon Lepe Manzo
Student ID: S1448233 
Date: Sep/2020
Instructor: Carol Hull
Description: 
Build a program that will run like a cash register. 
1. The program will take in a random number of prices (dollars and cents). 
2. When the cashier (user) has reached the end, they must enter the number 0 to indicate the end of the process. 
3. The next part is to indicate any coupons to enter. Similar to price section, a random number 
of coupon prices will be entered and the number 0 will indicate the end of the process.
4. The next process is to display the item total, coupon total,subtotal,tax percentage, tax amount, 
and total. 
5. Then the cashier will enter the received money amount from the customer, 
6. the change, and the breakdown of each monetary value. 

*/
import java.util.Scanner;  // Import the Scanner class
import java.security.SecureRandom; // import randorm class
import java.util.Random;

//-----------------------
// CLASS cash_register
//-----------------------
public class JuanLepe_HW01 {  // beginning of class 

    // CONSTANT VARIABLES
    static double TAX_VALUE=0.5;
    static double[][] BILLS = {{20,0}, {10,0}, {5,0},{1,0},{0.25,0},{0.10,0},{0.5,0},{0.01,0}}; //Array bills

    //-----------------------
    // Main method
    //-----------------------
    public static void main (String[] args) {

        Random rand = new Random(); //Create a random object
        Scanner keyIn = new Scanner(System.in);  // Create a Scanner object
        cash_register var_cash_register = new cash_register(); // Instance class cash_registrer

        // DECLARED VARIABLES
        double price_total=0.0, coupon_total=0.0, total=0.0, money=0.0;

        // Get Item Prices
        price_total = var_cash_register.item_price( rand, keyIn);

        // Get Coupons
        coupon_total = var_cash_register.coupon( rand, keyIn);

        // Display Totals
        total = var_cash_register.display_total(price_total, coupon_total);

        // Enter money amont
        money = var_cash_register.dollar_amount(total,keyIn);

        // Broke down bill
        var_cash_register.broke_down_bill(money,total);

        /// END
        System.out.println ("----------------------***END OF PROGRAM ***----------------------");

        

    }  // end of main method

    //---------------------------
    //  Method to get item price
    //---------------------------
    public double item_price( Random rand, Scanner keyIn)
    {
       // Declare Variable
       double price=0.0, price_total=0.0;
       String str = "",exitLoop="";
       int consecutive=0;

       System.out.println ("----- ITEM PRICES ----- ");

       // While for items
       do{
           consecutive++;
           str = "" + consecutive;
           price=rand.nextDouble()+rand.nextInt(99);
           System.out.printf("%n%s. Item Price : %.2f%n",str,price);
           System.out.printf("Enter '0' to finish or ENTER next.");
           
           exitLoop = keyIn.nextLine(); // validate key
            price_total += price; 
       }while(!exitLoop.equals("0"));
       System.out.printf("%n-->PRICE TOTAL : %.2f%n%n", price_total);

       return price_total; //return total

    } // end item_price method

    //---------------------------
    //  Method to get coupons
    //---------------------------
    public double coupon( Random rand, Scanner keyIn)
    {
       // Declare Variable
       double coupon=0.0, coupon_total = 0.0;
       String str = "",exitLoop="";
       int consecutive=0;

        System.out.println ("----- COUPONS ----- ");
        consecutive = 0;

        //while for coupons
        do{
            consecutive++;
            str = "" + consecutive;
            coupon=rand.nextDouble()+rand.nextInt(99);
            System.out.printf("%n%s. Coupon : %.2f%n",str,coupon);
            System.out.printf("Enter '0' to finish or ENTER next.");
            exitLoop = keyIn.nextLine(); // validate key
            coupon_total += coupon; 
        }while(!exitLoop.equals("0"));
        System.out.printf("%n-->Coupon Total: %.2f%n", coupon_total);

        return coupon_total; // Return total

    } // end coupon method

    //---------------------------
    // Method to display Totals
    //---------------------------
    public double display_total(double price_total, double coupon_total)
    {
       // Declare Variable
        double tax_percentage=(TAX_VALUE*100);
        double subtotal=0.0, tax_amount=0.0, total=0.0;

        // Display Totals
        System.out.println("-------------------------------------------------");
        System.out.printf("->PRICE TOTAL:  '%.2f'%n", price_total);
        System.out.printf("->COUPON TOTAL: '%.2f'%n", coupon_total);
        subtotal=(price_total-coupon_total); // Calculate subtotal
        System.out.printf("->SUBTOTAL: '%.2f'%n", subtotal);
        System.out.println("->TAX PERCENTAGE: " + String.valueOf((int)tax_percentage) + "%") ;
        tax_amount=(subtotal*TAX_VALUE); // Calculate taxes
        System.out.printf("->TAX AMOUNT: '%.2f'%n", tax_amount);
        total=(subtotal+tax_amount); // Calculate total
        System.out.printf("->TOTAL: '%.2f'%n", total);
        System.out.println("-------------------------------------------------");

        return total; // Return grand total
    } // end display_total

    //---------------------------
    // Method to introduce dollar amount
    //---------------------------
    public double dollar_amount(double total, Scanner keyIn){
        double money =0.0;

        //while for correct money amount
        do{
            System.out.printf( "Please, enter the received money amount  : ");
            money = isNumeric(keyIn.next());
            // Validate money amount from the user
            if (money<total)
            {
                System.out.printf("Money amount need to be greater or equal than '%.2f'%n", total);
            }
        }while(money<total);  
        
        return money; // Return money amount
    }// end dollar_amount method

    //---------------------------
    // Method to broke money
    //---------------------------
    public void broke_down_bill(double money, double total){
        double change = (money-total);
        System.out.printf("%n---------------------CHANGE %.2f------------------%n",change);

        for (int i=0;i<=7;i++){
            if (change>=BILLS[i][0]) BILLS[i][1] = change/BILLS[i][0]; //break it into smaller amounts 
            if ((int) BILLS[i][1]>0) // if there smaller amount
            {
                // Display bill and quantity
                System.out.printf("$ %2.2f",BILLS[i][0]);
                System.out.println(" - "+ (int) BILLS[i][1]);
    
            }
            // Discount money from the change.
            change = (change-(BILLS[i][0] * (int) BILLS[i][1]));
        }

    }// end bloke_down_bill method

    //---------------------------
    // funtion to validate doble values
    //---------------------------
    public double  isNumeric(String strNum) {
        try {
            Double.valueOf(strNum);
            return Double.valueOf(strNum);
        } catch (NumberFormatException nfe){
            return -0.1;
        }
    } //end isNumeric method

}  // end of class 

