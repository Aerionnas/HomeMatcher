
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HomeMatcher {

    static int dbSize = 1000;

    // we declare the arrays in the order they appear as columns in RE.csv
    static String[] street = new String[dbSize]; // col 0
    static String[] city = new String[dbSize]; // col 1
    static String[] zip = new String[dbSize]; // col 2
    static String[] state = new String[dbSize]; // col 3

    static int[] beds = new int[dbSize]; // col 4
    static int[] baths = new int[dbSize]; // col 5
    static int[] sqft = new int[dbSize]; // col 6

    static String[] type = new String[dbSize]; // col 7
    static String[] sale_date = new String[dbSize]; // col 8

    static int[] price = new int[dbSize]; // col 9

    static double[] latitude = new double[dbSize]; // col 10
    static double[] longitude = new double[dbSize]; // col 11

    static int dataSize = 0;

    static int numberOfBedrooms=0;
    static int highestPriceWillingToPay=0;
    static int smallestSquareFootage=0;
    static String requestedZip= "00000";

    public static void readData() {
        // Read in the data from RE.csv
        try{
            File places = new File("RE.csv");
            Scanner scan = new Scanner(places);
            scan.nextLine(); // skip the line of headers...
        int pos = 0;
        while (scan.hasNextLine()) {
            String line = scan.nextLine(); // read in the next line from the file

            String[] fields = line.split(","); // split the columns apart

            // fill in the next position of all the arrays with the current fields
            street[pos] = fields[0];
            city[pos] = fields[1];
            zip[pos] = fields[2];
            state[pos] = fields[3];
            beds[pos] = Integer.parseInt(fields[4]);
            baths[pos] = Integer.parseInt(fields[5]);
            sqft[pos] = Integer.parseInt(fields[6]);
            type[pos] = fields[7];
            sale_date[pos] = fields[8];
            price[pos] = Integer.parseInt(fields[9]);
            latitude[pos] = Double.parseDouble(fields[10]);
            longitude[pos] = Double.parseDouble(fields[11]);

            pos++; // increment position and get ready to load in the next line of data
        }
        dataSize = pos; // this is the number of rows of data we read in
        scan.close();
        }

        catch( FileNotFoundException e){
            System.out.println("error");
            
        }
        
    }

    public static void writeData() {
        // now we print out the index, price, and address
        for (int i = 0; i < dataSize; i++) {
            System.out.printf("%4d $%6d %-40s %s %s %s%n", i, price[i], street[i], city[i], state[i], zip[i]);
        }
    }

    public static void findExpensiveHouse() {
        // First we find the most expensive home in the dataset
        int h = 0; // h is the index of the most expensive house we have seen so far!
        for (int i = 0; i < dataSize; i++) {
            if (price[i] > price[h]) {
                h = i; // we found a more expensive home!
            }
        }
        System.out.printf("%n%nThe most expensive house is the one with index %d%n" +
                "it has price $%d and address %s %s %s%n%n%n",
                h, price[h], street[h], city[h], state[h]);
    }

    public static void findCheap1BR(){
        // we let h be the index of the least expensive 1BR home we have seen so far
        // if h is -1 it will mean that  we haven't seen any 1 bedroom houses yet,
            int h=-1;
    
            for(int i=0; i<dataSize; i++) {
                if (beds[i]==1){ // we are only interested in 1BR homes
                    if (h==-1) { // this happens for the first 1 bedroom home we see
                        h=i;
                    } else if (price[i] < price[h]) {
                        // this happens for the 2nd and later 1 bedroom homes we see
                        // if they are less expensive than all previous 1BR  homes we've seen
                        h = i; // we found a less expensive home!
                    }
                }
            }
            if (h==-1) {
                System.out.printf("There aren't any 1BR homes in the dataset%n");
            }else {
                System.out.printf(
                    "The least expensive 1BR home  is the one with index %d, %n" +
                    "it has price $%d and address %s %s %s%n%n",
                    h, price[h], street[h],city[h], state[h]);
            }
          }

    public static void getSpecs(){
        // user is asked questions about the preferences for their home.
        TextIO.putf("How many bedrooms are you looking for?%n");
        numberOfBedrooms=TextIO.getInt();
        TextIO.putf("What is the most you want to pay for your house?%n");
        highestPriceWillingToPay=TextIO.getlnInt();
        TextIO.putf("what is the smallest square footage you are interested in? %n");
        smallestSquareFootage=TextIO.getlnInt();
     
    }

    public static void findDreamHouse(){
        int numberOfHouses=0;
        for (int i= 0; i< dataSize; i++){
            if(numberOfBedrooms == beds[i]){
                if(highestPriceWillingToPay>= price[i]){
                    if(smallestSquareFootage <= sqft[i] ){
                        numberOfHouses++;
                        System.out.printf(
                    "Home %d : %n" +
                    "has price $%d and address %s %s %s%n%n",
                    i, price[i], street[i],city[i], state[i]);
                    }
                }

                
                
            }
        }
        System.out.println("There are a total of " + numberOfHouses + " homes that make your specifications.");
    }
    public static void main(String[] args) {
        readData();
        // writeData();
        findExpensiveHouse();
        findCheap1BR();
        getSpecs();
        findDreamHouse();

        
    }

}