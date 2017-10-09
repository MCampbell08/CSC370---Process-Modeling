public class MyThread extends Thread{

    @Override
    public void run(){
        System.out.println("Id: " + Thread.currentThread().getId() + " | Name: " + Thread.currentThread().getName());
    }
}
