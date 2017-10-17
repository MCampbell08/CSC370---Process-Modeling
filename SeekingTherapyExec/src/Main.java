import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        long openingTime = System.currentTimeMillis();
        ThreadList patientController = new ThreadList();
        patientController.start();
        while(System.currentTimeMillis()-openingTime < 120000){
            if(ThreadList.patients.size() != 0){
                ThreadList.patients.get(ThreadList.therapist).start();
                System.out.println("Patient: " + ThreadList.patients.get(ThreadList.therapist).getId() + " is seeing the therapist. | Had to wait: " + (System.currentTimeMillis() - ThreadList.patientsStartTime.get(ThreadList.patients.get(ThreadList.therapist - 1))));
                ThreadList.patientsSeen.add(ThreadList.patients.get(ThreadList.therapist));


                ThreadList.patients.get(ThreadList.therapist).join();
                System.out.println("Patient: " + ThreadList.patients.get(ThreadList.therapist - 1).getId() + " is leaving the therapist.");
            }
        }
        for(Thread thread : ThreadList.patients){
            if(!ThreadList.patientsSeen.contains(thread)){
                System.out.println("Patient " + thread.getId() + " did not get to the therapist today. They waited for: " + (System.currentTimeMillis() - ThreadList.patientsStartTime.get(ThreadList.patients.get(ThreadList.therapist - 1))));
            }
        }
        ThreadList.closingTime = true;
        patientController.join();
    }
}
