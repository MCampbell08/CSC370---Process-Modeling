import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        long openingTime = System.currentTimeMillis();
        ThreadList patientController = new ThreadList();
        patientController.start();
        while(System.currentTimeMillis()-openingTime < 120000){
            if(ThreadList.patients.size() != 0 && !ThreadList.switchingPatients){
                    ThreadList.patients.get(MyThread.therapist).start();
                    System.out.println("Patient: " + ThreadList.patients.get(MyThread.therapist).getId() + " is seeing the therapist.");
                    ThreadList.patientsSeen.add(ThreadList.patients.get(MyThread.therapist));


                    ThreadList.patients.get(MyThread.therapist).join();
                    System.out.println("Patient: " + ThreadList.patients.get(MyThread.therapist - 1).getId() + " is leaving the therapist.");

//                for(int i = 0; i < ThreadList.patients.size(); i++){
//                    if(!ThreadList.patientsSeen.contains(ThreadList.patients.get(i))){
//                        ThreadList.patients.get(i).start();
//                        System.out.println("Starting: " + ThreadList.patients.get(i).getId());
//                        ThreadList.patientsSeen.add(ThreadList.patients.get(i));
//                    }
//                }
//                for(int i = 0; i < ThreadList.patients.size(); i++){
//                    if(!ThreadList.patientsSeen.contains(ThreadList.patients.get(i))){
//                        ThreadList.patients.get(i).join();
//                        System.out.println("Joining: " + ThreadList.patients.get(i).getId());
//                    }
//                }

                if(ThreadList.patients.size() <= MyThread.therapist) {
                    ThreadList.switchingPatients = true;
                }
            }
        }
        for(MyThread thread : ThreadList.patients){
            if(!ThreadList.patientsSeen.contains(thread)){
                System.out.println("Patient " + thread.getId() + " did not get to the therapist today. They waited for: " + (System.currentTimeMillis() - thread.getPatientTimeStart()));
            }
        }
        ThreadList.closingTime = true;
        patientController.join();
    }
}
