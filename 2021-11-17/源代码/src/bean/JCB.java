package bean;

import java.util.List;

/**
 * @author xzy
 * @create 2021/10/30 21:09
 *
 * 作业类
 */
public class JCB implements Cloneable{
    private int id;
    private String name;
    private int needSpace;
    private int priority;
    private List type;
    private int needtime;
    private int arrivetime;

    @Override
    public String toString() {
        return "JCB{" +"\n"+
                "id=" + id +"\n"+
                "name='" + name + '\'' +"\n"+
                "needSpace=" + needSpace +"\n"+
                "priority=" + priority +"\n"+
                "type=" + type +"\n"+
                "needtime=" + needtime +"\n"+
                "arrivetime=" + arrivetime +"\n"+
                '}'+"\n";
    }

    public int getNeedtime() {
        return needtime;
    }

    public void setNeedtime(int needtime) {
        this.needtime = needtime;
    }

    public int getArrivetime() {
        return arrivetime;
    }

    public void setArrivetime(int arrivetime) {
        this.arrivetime = arrivetime;
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

}
