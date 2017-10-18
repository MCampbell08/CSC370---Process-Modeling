public class Main {

    public static void main(String[] args) throws InterruptedException {
        long openingTime = System.currentTimeMillis();
        ThreadList patientController = new ThreadList();
        patientController.start();
         while(System.currentTimeMillis()-openingTime < 12000){
            if(ThreadList.patients.size() != 0){
                Thread.sleep(10);
                ThreadList.patients.get(ThreadList.patients.size() - 1).start();
                System.out.println("Patient: " + ThreadList.patients.get(ThreadList.patients.size() - 1).getId() + " is seeing the therapist. | Had to wait: " + (System.currentTimeMillis() - ThreadList.patients.get(ThreadList.patients.size() - 1).patientTimeStart));



                ThreadList.patients.get(ThreadList.patients.size() - 1).join();
                System.out.println("Patient: " + ThreadList.patients.get(ThreadList.patients.size() - 2).getId() + " is leaving the therapist.");
            }
        }
        ThreadList.closingTime = true;
        patientController.join();
    }
}
