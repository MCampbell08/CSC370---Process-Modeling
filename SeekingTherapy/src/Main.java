import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        List<MyThread> currentPatients = new ArrayList<>();
        long openingTime = System.currentTimeMillis();
        ThreadList patientController = new ThreadList();
        patientController.start();
        currentPatients.addAll(ThreadList.patients);
        while(System.currentTimeMillis()-openingTime < 120000){
            if(currentPatients.size() < 3){
                for(int i = 0; i < (3 - currentPatients.size()); i++){
                    currentPatients.add(ThreadList.patients.get(0));
                    ThreadList.patients.remove(0);
                }
            }
            for(MyThread currCustomer : currentPatients){
                currCustomer.start();
            }
            for (MyThread currCustomer : currentPatients){
                currCustomer.join();
                ThreadList.patients.remove(currCustomer);
                System.out.println("Removing currCustomer: " + currCustomer.getId());
            }
            System.out.println("Size: " + currentPatients.size());
        }
        patientController.join();
    }
}
