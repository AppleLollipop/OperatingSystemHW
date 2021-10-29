package com.process_dispatch;

//where the process be contained
public class ProcessQueue {

    //push完调用FinishProcessPush()形成环
    public void pushBackProcess(PCB new_process) {
        if (pcbQueueHead == null) {
            pcbQueueHead = new_process;
            pcbQueueCursor = pcbQueueHead;
        } else {
            pcbQueueCursor.pcbNext = new_process;
            pcbQueueCursor = pcbQueueCursor.pcbNext;
        }
    }

    //
    public void finishProcessPush() {
        // pcbQueueCursor.next must be null
        pcbQueueCursor.pcbNext = pcbQueueHead;
        //reuse the cursor for run cursor
        pcbQueueCursor = pcbQueueHead;
    }

    //时间片走一步
    //若没有实际运行到则返回false
    public boolean executeStep() {
        if (pcbQueueCursor.nExecuteState == 1) {
            pcbQueueCursor = pcbQueueCursor.pcbNext;
            return false;
        } else {
            __printState();

            pcbQueueCursor.nWorkedTime++;
            m_nTotalExecuteTime++;
            if (pcbQueueCursor.nWorkedTime == pcbQueueCursor.nRequestTime) {
                System.out.println(pcbQueueCursor.strProcessName + " 已结束");
                pcbQueueCursor.nExecuteState = 1;
            }
            pcbQueueCursor = pcbQueueCursor.pcbNext;
            return true;
        }
    }

    public int getTotalExecuteTime() {
        return m_nTotalExecuteTime;
    }

    public void __printState() {
        System.out.println("进程名\t要求时间\t已运行时间");
        System.out.println("---------------------------------------------");

        PCB tempCursor = pcbQueueHead;
        do {
            System.out.printf("%s\t%d\t\t\t%d\n", tempCursor.strProcessName, tempCursor.nRequestTime,
                tempCursor.nWorkedTime);
            tempCursor = tempCursor.pcbNext;
        } while (tempCursor != pcbQueueHead);
    }

    private PCB pcbQueueHead = null;
    private PCB pcbQueueCursor = null;
    private int m_nTotalExecuteTime = 0;
}
