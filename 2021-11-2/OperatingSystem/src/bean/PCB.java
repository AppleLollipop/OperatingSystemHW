package bean;

import java.util.List;

/**
 * @author xzy
 * @create 2021/10/30 21:09
 *
 * 进程类
 */
public class PCB {
    private int id;
    private String name;
    private int needSpace;
    private int priority;
    private List type;
    private int time;
    private int waittime;
    private int state;

    @Override
    public String toString() {
        return "PCB{" +"\n"+
                "id=" + id +"\n"+
                "name='" + name + '\'' +"\n"+
                "needSpace=" + needSpace +"\n"+
                "priority=" + priority +"\n"+
                "type=" + type +"\n"+
                "time=" + time +"\n"+
                "waittime=" + waittime +"\n"+
                "state=" + state +"\n"+
                '}'+"\n";
    }

    public List getType() {
        return type;
    }

    public void setType(List type) {
        this.type = type;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNeedSpace() {
        return needSpace;
    }

    public void setNeedSpace(int needSpace) {
        this.needSpace = needSpace;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getWaittime() {
        return waittime;
    }

    public void setWaittime(int waittime) {
        this.waittime = waittime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
