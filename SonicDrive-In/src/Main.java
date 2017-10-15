public class Main {

    private static final int CUSTOMER_AMOUNT = 10;

    public static void main(String[] args) {
        Customers.x = 0;

        for(int i = 0; i < CUSTOMER_AMOUNT; i++){
            Customers customer = new Customers();
            customer.start();
        }
    }
}
