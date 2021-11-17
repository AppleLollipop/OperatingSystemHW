package bean;

import java.util.List;

/**
 * @author xzy
 * @create 2021/10/31 14:59
 *
 * 系统配置类
 */
public class Configure implements Cloneable{
    private int id;
    private int maxProcess; //系统最大进程数
    private int maxPriority; //系统最大优先级
    private int maxType; //系统最大资源种类数
    private int maxWork; //系统最大作业数
    private int memory; //系统内存大小
    private int memoryBlock; //系统内存块大小
    private int memoryNumber; //内存块数量
    private int instructions; //系统指令大小
    private List type; //系统资源种类数

    @Override
    public String toString() {
        return
                "Configure{\n" +
//                "id = " + id +"\n"+
                "系统最大进程数maxProcess = " + maxProcess +"\n"+
                "系统最大优先级maxPriority = " + maxPriority +"\n"+
                "系统最大资源种类数maxType = " + maxType +"\n"+
                "系统最大作业数maxWork = " + maxWork +"\n"+
                "系统内存块大小memoryBlock = " + memoryBlock +"\n"+
//                "系统指令大小instructions = " + instructions +"\n"+
                        "\n"+
                "当前系统内存大小memory = " + memory +"\n"+
                "当前内存块数量memoryNumber = " + memoryNumber +"\n"+
                "当前系统资源type = " + type +"\n"+
                '}';
    }

    @Override
    public Object clone() {
        Configure configure = null;
        try {
            configure = (Configure) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return configure;
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

    public List getType() {
        return type;
    }

    public void setType(List type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
