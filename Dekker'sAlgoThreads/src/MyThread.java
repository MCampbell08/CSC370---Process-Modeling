import java.util.List;

public class MyThread extends Thread{

    public static int x;

    private static int TaskID = 0;

    public int thisTaskId = 0;

    private static boolean[] RequestSection = new boolean[]{false, false};

    private boolean preventCorrupt = true;

    public MyThread(int i) {
        thisTaskId = i;
    }

    @Override
    public void run(){
        int otherTaskID = Math.abs(thisTaskId - 1);

        if(preventCorrupt) {
            RequestSection[thisTaskId] = true;
            while (RequestSection[otherTaskID]) {
                if (TaskID != thisTaskId) {
                    RequestSection[thisTaskId] = false;
                    while (TaskID != thisTaskId) {
                        pause(10);
                        RequestSection[thisTaskId] = true;
                    }
                }
            }
        }

        int local = x;
        local++;
        x = local;
        System.out.println(x);

        if(preventCorrupt) {
            TaskID = otherTaskID;
            RequestSection[thisTaskId] = false;
        }
    }
    private void pause(long m){
        try{
            sleep(m);
        }
        catch (InterruptedException ex){}
    }
}
