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
    public boolean checkSafe(Configure configure, LinkedList pcbBeReady, PCB pcb){
//        System.out.println(pcbBeReady);
//        reader.next();
        List available = configure.getType();
        List request = pcb.getType();

//        System.out.println("Banker");
//        System.out.println(available);
//        System.out.println(request);
//        reader.next();

        //判断need与available的关系
        for(int i = 0; i < request.size(); i++){
            if((int) request.get(i) > (int) available.get(i)){
                return false;
            }
        }

        //模拟分配
        for(int i = 0; i < available.size(); i++){
            available.set(i, (int)available.get(i)-(int)request.get(i));
            configure.setType(available);
            this.configure = configure;
        }
//        System.out.println(available);
//        reader.next();

        //安全性检测
        safe = new ArrayList();
        safe.add(pcb.getName());
        if(pcbBeReady.size() != 0){
            for(int i = 0; i < pcbBeReady.size(); i++){
                PCB pcb1 = (PCB) pcbBeReady.get(i);
                List request1 = pcb1.getType();
                for(int j = 0; j < request.size(); j++){
                    if((int) request1.get(i) > (int) available.get(i)){
                        return false;
                    }
                }
                safe.add(pcb1.getName());
            }
        }

        return true;
    }
    public List getBanker(Configure configure, LinkedList pcbBeReady, PCB pcb){
        List list = new ArrayList();
        list.add(false);

        if(checkSafe(configure, pcbBeReady, pcb)){
            list.set(0,true);
            list.add(this.configure);
            list.add(safe);
        }

        return list;
    }
}
