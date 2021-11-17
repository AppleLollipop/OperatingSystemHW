package bean;

/**
 * @author xzy
 * @create 2021/11/4 12:32
 *
 * 内存数据类
 */
public class MemoryData {
    private String pcbname; //占用的进程名
    private int state; //使用状态0/1
    private int space; //内存的空间大小
//    private String queueName; //占用这块内存的队列种类

    @Override
    public String toString() {
        return "MemoryData{" +
                "pcbname='" + pcbname + '\'' +
                ", state=" + state +
                ", space=" + space +"kb"+
                '}';
    }

    public String getPcbname() {
        return pcbname;
    }

    public void setPcbname(String pcbname) {
        this.pcbname = pcbname;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }
}
