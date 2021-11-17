package util;

import bean.Configure;
import bean.JCB;
import bean.PCB;
import sun.awt.image.ImageWatched;

import java.util.*;

/**
 * @author xzy
 * @create 2021/11/3 20:03
 */
public class Banker {
    Scanner reader = new Scanner(System.in);
    Configure configure;
    private List safe;

    public void showData(Windows windows, Configure configure){
        windows.print(configure.toString());
    }
    public boolean checkSafe(Configure configure, LinkedList pcbBeReady, PCB pcb, LinkedList pcbBlock, LinkedList pcbFree){
//        System.out.println(pcbBeReady);
//        reader.next();
        List available = configure.getType();//现在系统拥有的资源向量
        List request = pcb.getType();//当前进程需要的资源向量
        List need = pcb.getType();//进行需要的最大资源
        List allocation = new ArrayList();//用于储存已经分别到当前进程的资源

        //就绪队列+阻塞队列 放进一个队列
        List list = new LinkedList();//之后需要获取资源的进程, 就绪队列+阻塞队列
        for (int i = 0; i < pcbBeReady.size(); i++){
            list.add(pcbBeReady.get(i));
        }
        for (int i = 0; i < pcbBlock.size(); i++){
            list.add(pcbBlock.get(i));
        }
        for (int i = 0; i < pcbFree.size(); i++){
            list.add(pcbFree.get(i));
        }

//        System.out.println("Banker");
//        System.out.println(available);
//        System.out.println(request);
//        reader.next();

        //requests <= need
        for(int i = 0; i < request.size(); i++){
            if((int) request.get(i) > (int) need.get(i)){
                return false;
            }
        }
        //requests <= available
        for(int i = 0; i < request.size(); i++){
            if((int) request.get(i) > (int) available.get(i)){
                return false;
            }
        }

        //模拟分配资源
        for(int i = 0; i < available.size(); i++){
            available.set(i, (int)available.get(i)-(int)request.get(i));//系统资源减去当前进程需要的资源向量
            configure.setType(available);//更新系统资源
            allocation.add(request.get(i));//当前进程已经获取到的资源向量
            this.configure = configure;
        }
//        System.out.println(available);
//        reader.next();

        //安全性检测
        safe = new ArrayList();//safe为安全序列组队列
        safe.add(pcb.getName());

        //list队列为在此进程申请相应资源后，未来会向系统申请资源的进程
        //即阻塞队列＋就绪队列中的进程
        if(list.size() != 0){
            for(int i = 0; i < list.size(); i++){
                PCB pcb1 = (PCB) list.get(i);
                List request1 = pcb1.getType();
//                List allocation = pcb.getType();

//                System.out.println(request);
//                System.out.println(available);
                for(int j = 0; j < request.size(); j++){
                    if((int) request1.get(j) > (int) available.get(j)+(int)pcb.getType().get(j)){
                        //不存在安全序列组，将刚刚模拟分配的资源归还
                        available.set(i, (int)available.get(i)+(int)request.get(i));
                        configure.setType(available);
                        return false;
                    }
                }
                safe.add(pcb1.getName());
            }
        }

//        System.out.println("this");
        return true;
    }
    public List getBanker(Configure configure, LinkedList pcbBeReady, PCB pcb, LinkedList pcbBlock, LinkedList pcbFree){
        List list = new ArrayList();
        list.add(false);

        if(checkSafe(configure, pcbBeReady, pcb, pcbBlock, pcbFree)){
            list.set(0,true);
            list.add(this.configure);
            list.add(safe);
        }

        //list 储存三个数值，分别为ture/false，更新后的系统状态，安全序列组
        return list;
    }
}
