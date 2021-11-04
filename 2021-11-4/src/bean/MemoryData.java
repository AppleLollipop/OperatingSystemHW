package bean;

/**
 * @author xzy
 * @create 2021/11/4 12:32
 */
public class MemoryData {
    private String pcbname;
    private int state;
    private int space;

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
