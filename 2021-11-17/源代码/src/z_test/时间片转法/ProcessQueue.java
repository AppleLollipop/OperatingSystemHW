package z_test.时间片转法;

import java.util.ArrayList;
import java.util.List;

//where the process be contained
public class ProcessQueue {

    private List<PCB> listPCBQueue = new ArrayList<>();//PCB队列
    private int nQueueCursorIndex = 0;//游标
    private int m_nTotalExecuteTime = 0;//总执行时间

    //入队
    public void pushBackProcess(PCB new_process) {
        listPCBQueue.add(new_process);
    }

    //时间片走一步
    //若没有实际运行到则返回false
    public void executeStep() {
        PCB current = listPCBQueue.get(nQueueCursorIndex);

        //PCB运行
        current.nWorkedTime++;
        m_nTotalExecuteTime++;

        //检查当前PCB是否完成
        if (current.nWorkedTime == current.nRequestTime) {
            //set fnished state
            current.nExecuteState = 1;
            //print message
            System.out.println(current.strProcessName + "已结束");
            //remove current
            __removeCursorCurrent();
        }
        //step forward
        __cursorMoveForward();
    }

    public int getTotalExecuteTime() {
        return m_nTotalExecuteTime;
    }

    public void printState() {
        System.out.println("进程名\t要求时间\t已运行时间");
        System.out.println("---------------------------------------------");
        for (PCB pcb : listPCBQueue) {
            System.out.printf("%s\t%d\t\t\t%d\n", pcb.strProcessName, pcb.nRequestTime, pcb.nWorkedTime);
        }
    }

    public boolean isAllFinished() {
        return listPCBQueue.size() == 0;
    }

    //cursor move forward
    private void __cursorMoveForward() {
        nQueueCursorIndex++;
        if (nQueueCursorIndex >= listPCBQueue.size()) {
            //back to head if out of range
            nQueueCursorIndex = 0;
        }
    }

    //remove the current pcb
    //after removing, the cursor point to the previous one of current
    private void __removeCursorCurrent() {
        //just remove
        listPCBQueue.remove(nQueueCursorIndex);
    }


}
