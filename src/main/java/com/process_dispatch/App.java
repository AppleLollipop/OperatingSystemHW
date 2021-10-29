package com.process_dispatch;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        ProcessQueue queue = new ProcessQueue();
        for (int i = 1; i <= 5; i++) {
            queue.pushBackProcess(promptCreatePCB(i));
        }
        queue.finishProcessPush();

        int idle_count = 0;
        while (idle_count < 5) {
            if (queue.executeStep()) {
                idle_count = 0;
            } else {
                idle_count++;
            }
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
}
