public class Main {

    public static void main(String[] args) throws InterruptedException {
        long openingTime = System.currentTimeMillis();
        ThreadList patientController = new ThreadList();
        patientController.start();
        while(System.currentTimeMillis()-openingTime < 120000){
            if(ThreadList.patients.size() != 0){
                ThreadList.patients.get(MyThread.therapist).start();
                System.out.println("Patient: " + ThreadList.patients.get(MyThread.therapist).getId() + " is seeing the therapist. | Had to wait: " + (System.currentTimeMillis() - ThreadList.patients.get(MyThread.therapist).patientTimeStart));
                ThreadList.patientsSeen.add(ThreadList.patients.get(MyThread.therapist));


                ThreadList.patients.get(MyThread.therapist).join();
                System.out.println("Patient: " + ThreadList.patients.get(MyThread.therapist - 1).getId() + " is leaving the therapist.");
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
