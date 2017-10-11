import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        List<MyThread> tempPatients = new ArrayList<>();
        long openingTime = System.currentTimeMillis();
        ThreadList patientController = new ThreadList();
        patientController.start();
        while(System.currentTimeMillis()-openingTime < 120000){
            for(int i = 0; i < ThreadList.patients.size(); i++){
                ThreadList.patients.get(i).start();
                tempPatients.add(ThreadList.patients.get(i));
                System.out.println("Starting thread");
                System.out.println("Serving currCustomer: " + ThreadList.patients.get(i).getId());
            }
            for (MyThread currCustomer : ThreadList.patients){
                currCustomer.join();
                System.out.println("Removing currCustomer: " + currCustomer.getId());
            }
            ThreadList.patients.removeAll(tempPatients);
            tempPatients.
        }
        patientController.join();
    }
}
