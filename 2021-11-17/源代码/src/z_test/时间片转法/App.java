package z_test.时间片转法;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        ProcessQueue queue = new ProcessQueue();

        queue.pushBackProcess(createPCB(1, 5));
        queue.pushBackProcess(createPCB(2, 8));
        queue.pushBackProcess(createPCB(3, 2));
        queue.pushBackProcess(createPCB(4, 9));
        queue.pushBackProcess(createPCB(5, 3));

        int idle_count = 0;
        //队列为空，则完成所有
        while (queue.isAllFinished() == false) {
            queue.printState();
            System.out.println("");
            queue.executeStep();
        }

        System.out.println("总运行时间：" + queue.getTotalExecuteTime());

    }

    public static PCB promptCreatePCB(int prompt_number) {
        PCB pcb = new PCB();
        pcb.strProcessName = "进程" + Integer.toString(prompt_number);

        Scanner scanner = new Scanner(System.in);
        System.out.printf("输入第%d个进程的 <要求运行时间>: ", prompt_number);

        if (scanner.hasNextInt()) {
            pcb.nRequestTime = scanner.nextInt();
        }

        return pcb;
    }

    public static PCB createPCB(int number, int request_time) {
        PCB pcb = new PCB();
        pcb.strProcessName = "进程" + Integer.toString(number);
        pcb.nRequestTime = request_time;
        return pcb;
    }
}
