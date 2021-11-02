package bean;

/**
 * @author xzy
 * @create 2021/10/31 14:59
 */
public class Configure {
    private int id;
    private int maxProcess; //系统最大进程数
    private int maxPriority; //系统最大优先级
    private int maxType; //系统最大资源种类数
    private int maxWork; //系统最大作业数
    private int memory; //系统内存大小
    private int memoryBlock; //系统内存块大小
    private int memoryNumber; //内存块数量
    private int instructions; //系统指令大小
    private int type; //系统资源种类数

    @Override
    public String toString() {
        return "Configure{\n" +
                "id = " + id +"\n"+
                "系统最大进程数maxProcess = " + maxProcess +"\n"+
                "系统最大优先级maxPriority = " + maxPriority +"\n"+
                "系统最大资源种类数maxType = " + maxType +"\n"+
                "系统最大作业数maxWork = " + maxWork +"\n"+
                "系统内存大小memory = " + memory +"\n"+
                "系统内存块大小memoryBlock = " + memoryBlock +"\n"+
                "内存块数量memoryNumber = " + memoryNumber +"\n"+
                "系统指令大小instructions = " + instructions +"\n"+
                "系统资源种类数type = " + type +"\n"+
                '}';
    }

    public int getMaxProcess() {
        return maxProcess;
    }

    public void setMaxProcess(int maxProcess) {
        this.maxProcess = maxProcess;
    }

    public int getMaxPriority() {
        return maxPriority;
    }

    public void setMaxPriority(int maxPriority) {
        this.maxPriority = maxPriority;
    }

    public int getMaxType() {
        return maxType;
    }

    public void setMaxType(int maxType) {
        this.maxType = maxType;
    }

    public int getMaxWork() {
        return maxWork;
    }

    public void setMaxWork(int maxWork) {
        this.maxWork = maxWork;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getMemoryBlock() {
        return memoryBlock;
    }

    public void setMemoryBlock(int memoryBlock) {
        this.memoryBlock = memoryBlock;
    }

    public int getMemoryNumber() {
        return memoryNumber;
    }

    public void setMemoryNumber(int memoryNumber) {
        this.memoryNumber = memoryNumber;
    }

    public int getInstructions() {
        return instructions;
    }

    public void setInstructions(int instructions) {
        this.instructions = instructions;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
