package util;

import bean.JCB;
import bean.PCB;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xzy
 * @create 2021/11/3 19:28
 */
public class Sort {
    public static LinkedList sortPcb(List list){
        List new_list = new LinkedList();

        while (list.size() != 0){
            Index max = new Index();
            max.setData(0);
            for (int i = 0; i < list.size(); i++){
                PCB pcb = (PCB) list.get(i);
                if(max.getData() < pcb.getPriority()){
                    max.setData(pcb.getPriority());
                    max.setIndex(i);
                }
            }

            new_list.add(list.get(max.getIndex()));
            list.remove(max.getIndex());
        }

        return (LinkedList) new_list;
    }
}

class Index{
    private int index;
    private int data;

    @Override
    public String toString() {
        return "index{" +
                "index=" + index +
                ", data=" + data +
                '}';
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}