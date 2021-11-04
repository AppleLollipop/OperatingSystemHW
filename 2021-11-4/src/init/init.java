package init;

import bean.Configure;
import bean.JCB;
import bean.MemoryData;
import bean.PCB;

import dao.PcbDao;
import util.*;
import util.table.ConfigureTable;
import util.table.PcbTable;
import util.windows.ConfigureWindow;
import util.windows.MemoryWindow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static util.Sort.sortPcb;

/**
 * @author xzy
 * @create 2021/10/30 22:31
 */
public class init {
    private Configure configure; //系统配置

    private List pcbFree; //空闲队列
    private List pcbBeReady; //就绪队列
    private List pcbRunning; //运行队列
    private List pcbBlock; //阻塞队列
    private List pcbComplete; //完成的队列

    private List memory; //内存块

    private List<JCB> jcbList; //作业
    private List<PCB> pcbList; //进程

    Scanner reader = new Scanner(System.in);

    Banker banker = new Banker();//应行家算法
    Warning warning = new Warning();//弹框

    MemoryWindow memoryWindow; //内存块实时使用情况
    ConfigureWindow configureWindow; //系统资源实时情况
    PcbTable pcbTable; //进程动态数据表格

    init(){
        //初始化主窗口
        Windows windows = new Windows();
        memoryWindow = new MemoryWindow();
        configureWindow = new ConfigureWindow();

        //系统配置初始化
        configure = new ConfigureTable().init();
        Configure configureRealTime = (Configure)configure.clone();
        System.out.println("系统初始化成功");
        windows.print(configure.toString());
//        windows.print(configureRealTime.toString());
        windows.print("系统初始化成功");

        //队列初始化
        pcbFree = new LinkedList();
        pcbBeReady = new LinkedList();
        pcbRunning = new LinkedList();
        pcbBlock = new LinkedList();
        pcbComplete = new LinkedList();
        memory = new LinkedList();
//        System.out.println("队列初始化成功");
        windows.print("队列初始化成功");

        //初始化内存块
        for (int i = 0; i < configure.getMemoryNumber(); i++){
            MemoryData memoryData = new MemoryData();
            memoryData.setState(0);
            memoryData.setSpace(configure.getMemoryBlock());

            memory.add(memoryData);
        }
        windows.print("内存块初始化成功");

        //作业初始化
//        jcbList = new JcbTable().init();
//        System.out.println("作业初始化成功");
//        windows.print("作业初始化成功");
//        windows.print(jcbList.toString());

        //进程初始化
        pcbTable = new PcbTable();
        pcbList = pcbTable.init(new PcbDao().select());
//        pcbList = new PcbTable(new PcbDao().select()).init();
//        System.out.println("进程初始化成功");
        windows.print("进程初始化成功");

        //作业进入空闲队列
        for(int i = 0; i < pcbList.size(); i++){
            pcbFree.add(pcbList.get(i));
        }
        windows.print("作业进入空间队列");

//        String input = reader.next();

        int nowTime = 0;
        long startTime = System.currentTimeMillis();
        //当4个队列全为空时则退出运行
        while(pcbFree.size()!=0 || pcbBeReady.size()!=0 ||
                pcbRunning.size()!=0 || pcbBlock.size()!=0){
            long endTime = System.currentTimeMillis();
            if(endTime-startTime <= 200){
                continue;
            }
            startTime = System.currentTimeMillis();

            String pdata = "秒数:"+nowTime+"\n"+"空闲队列："+pcbFree.size()+"\n"+
                    "就绪队列："+pcbBeReady.size()+"\n"+
                    "运行队列："+pcbRunning.size()+"\n"+"阻塞队列："+pcbBlock.size()+"\n"+
                    "完成队列："+pcbComplete.size();
            pcbTable.init(allToOneList());
            configureWindow.print(configureRealTime, pdata);
            memoryWindow.print(memory);

//            System.out.println("秒数"+nowTime);
//            System.out.println("空闲"+pcbFree.size());
//            System.out.println("就绪"+pcbBeReady.size());
//            System.out.println("运行"+pcbRunning.size());
//            System.out.println("阻塞"+pcbBlock.size());
//            System.out.println("完成"+pcbComplete.size());
//            reader.next();
//            warning.init("pause");

            //空闲队列不为空时进行
            if(pcbFree.size() != 0){
                //检测空闲队列中的数据是否到达时间进入就绪队列
                for(int i = 0; i < pcbFree.size(); i++){
                    PCB pcb = (PCB) pcbFree.get(i);
                    if(pcb.getArrivetime() < nowTime){
                        windows.print(pcb.getName()+"已经到达，并向系统提出内存申请");
                        //时间到达，进程进入就绪队列
                        //判断内存是否足够
                        int isRich = 0;
                        for(int j = 0; j < memory.size(); j++){
                            MemoryData memoryData = (MemoryData) memory.get(j);
                            if(memoryData.getState() == 0&&
                                memoryData.getSpace()>=pcb.getNeedSpace()){

                                memoryData.setState(1);
                                memoryData.setPcbname(pcb.getName());
                                //实时系统资源
                                configureRealTime.setMemoryNumber(configureRealTime.getMemoryNumber()-1);
                                configureRealTime.setMemory(configureRealTime.getMemory()-configureRealTime.getMemoryBlock());
                                isRich = 1;
                                windows.print("当前系统内存为"+configureRealTime.getMemory()*100/configure.getMemory()+"%, 内存充足，同意申请");
                                break;
                            }
                        }
                        if(isRich == 1){
                            //内存足够
                            PCB pcb1 = (PCB) pcbFree.get(i);
//                            pcb1.setState("running");
                            pcbBeReady.add(pcb1);
                            pcbFree.remove(i);
                        }else{
                            //内存不足
                            PCB pcb2 = (PCB) pcbFree.get(i);
                            pcb2.setState("blocked");
                            pcbBlock.add(pcb2);
                            pcbFree.remove(i);
                        }
                    }
                }
            }

            //就绪队列不为空时进行
            if(pcbBeReady.size() != 0){
                //当就绪队列不为空，则对队列排序, 按优先级排序
                pcbBeReady = sortPcb(pcbBeReady);
                //若优先级最高的进程需求的资源得到满足则分配资源并进入运行队列
//                    banker.showData(windows,configureRealTime);

//                    windows.print("input");
//                    reader.next();

                PCB pcb = (PCB) pcbBeReady.get(0);
                ((LinkedList) pcbBeReady).remove(0);
//                System.out.println(banker.checkSafe(configureRealTime, (LinkedList) pcbBeReady, pcb));
                List list = banker.getBanker(configureRealTime, (LinkedList) pcbBeReady, pcb);
                //能够分配资源，则进入运行队列
                if((Boolean) list.get(0) == true){
                    windows.print(pcb.getName()+"向系统提出资源申请"+pcb.getType());
                    windows.print("当前系统资源"+configureRealTime.getType());
                    windows.print("通过应行家算法检测找到安全序列组"+list.get(2));
                    windows.print("为其分配资源");
                    pcb.setState("running");
                    pcbRunning.add(pcb);
                    Configure configure = (Configure) list.get(1);
                    configureRealTime.setType(configure.getType());
                }else {
                    windows.print(pcb.getName()+"向系统提出资源申请"+pcb.getType());
                    windows.print("当前系统资源"+configureRealTime.getType());
                    windows.print("通过应行家算法检测没有找到安全序列组");
                    windows.print("不为其分配资源，使其进入阻塞");
                    pcb.setState("blocked");
                    pcbBlock.add(pcb);
                }
            }

            //运行队列不为空时进行
            if(pcbRunning.size() != 0){
                //运行时间加 1 秒
                for(int i = 0; i < pcbRunning.size(); i++){
                    PCB pcb = (PCB) pcbRunning.get(i);
                    pcb.setRunTime(pcb.getRunTime()+1);
                    pcbRunning.set(i, pcb);
                }

                //判断运行是否完成
                List index = new ArrayList();
                for(int i = 0; i < pcbRunning.size(); i++){
                    PCB pcb = (PCB) pcbRunning.get(i);
                    if(pcb.getRunTime() >= pcb.getNeedtime()){
                        //运行完成
                        //归还资源
                        windows.print(pcb.getName()+"运行完成，并已归还资源");
                        pcb.setState("finish");
                        configureRealTime = backResource(configureRealTime, pcb);
                        pcbComplete.add(pcbRunning.get(i));
                        pcbRunning.remove(i);
//                        index.add(i);
                    }
                }
//                System.out.println(pcbRunning);
                //pcb进入完成队列，移除运行中的pcb
//                if(index.size() != 0){
//                    for(int i = 0; i < index.size(); i++){
//                        int thisIndex = (int) index.get(i);
//                        pcbComplete.add(pcbRunning.get(thisIndex));
//                        pcbRunning.remove(thisIndex);
//                    }
//                }
//                System.out.println(pcbRunning);
            }else{
                //运行队列为空时，去就绪队列和阻塞队列中寻找未运行进程
                if(pcbBlock.size() != 0){
                    for (int i = 0; i < pcbBlock.size(); i++){
                        PCB pcb = (PCB) pcbBlock.get(i);
                        windows.print(pcb.getName()+"从阻塞队列进入就绪队列");
                        pcbBeReady.add(pcb);
                        pcbBlock.remove(i);
                    }
                }
            }

            nowTime += 1;
        }

        windows.print("程序运行结束, 运行时间为 "+nowTime+" 秒");

        //资源管理器
//        Resources resources = new Resources();
    }
    public Configure backResource(Configure configure, PCB pcb){
        List configureType = configure.getType();
        List pcbType = pcb.getType();
        for(int i = 0; i < configureType.size(); i++){
            configureType.set(i, (int)configureType.get(i)+(int)pcbType.get(i));
        }

        configure.setType(configureType);
        return configure;
    }
    public List allToOneList(){
        List list = new LinkedList();
        for(int i = 0; i < pcbFree.size(); i++){
            list.add(pcbFree.get(i));
        }
        for(int i = 0; i < pcbBeReady.size(); i++){
            list.add(pcbBeReady.get(i));
        }
        for(int i = 0; i < pcbRunning.size(); i++){
            list.add(pcbRunning.get(i));
        }
        for(int i = 0; i < pcbBlock.size(); i++){
            list.add(pcbBlock.get(i));
        }
        for(int i = 0; i < pcbComplete.size(); i++){
            list.add(pcbComplete.get(i));
        }

        return list;
    }
}
