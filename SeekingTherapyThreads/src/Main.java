public class Main {

    public static void main(String[] args) throws InterruptedException {
        long openingTime = System.currentTimeMillis();
        TherapistThread patientController = new TherapistThread();
        patientController.start();
        while (System.currentTimeMillis() - openingTime < /*120000*/10000) {

        }
        TherapistThread.closingTime = true;
        patientController.join();
        System.out.println("Closing Time.");
    }
}
