public class Main {

    public static void main(String[] args) {
        MyThread.x = 0;

        for(int i = 0; i < 2; i++){
            MyThread t = new MyThread(i);
            t.start();
        }
    }
}
