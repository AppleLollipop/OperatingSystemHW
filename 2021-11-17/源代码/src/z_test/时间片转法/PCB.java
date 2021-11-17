package z_test.时间片转法;

//进程控制块
public class PCB {
    public String strProcessName = "null";
    public PCB pcbNext = null; // "pointer" to next pcb
    public int nRequestTime = 0; //要求运行时间
    public int nWorkedTime = 0; //已运行时间
    public int nExecuteState = 0; // 0 = ready, 1 = end
}
