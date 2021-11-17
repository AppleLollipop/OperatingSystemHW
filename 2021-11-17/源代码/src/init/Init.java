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
import z_test.Warning;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static util.Sort.sortPcb;

/**
 * @author xzy
 * @create 2021/10/30 22:31
 */
public class Init {
    private Configure configure; //系统配置
    Configure configureRealTime; //实时配置

    private List pcbFree; //空闲队列
    private List pcbBeReady; //就绪队列
    private List pcbRunning; //运行队列
    private List pcbBlock; //阻塞队列
    private List pcbComplete; //完成的队列

    private List memory; //内存块

//    private List<JCB> jcbList; //作业
    private List<PCB> pcbList; //进程

    Scanner reader = new Scanner(System.in);

    Banker banker = new Banker();//应行家算法
    Warning warning = new Warning();//弹框

    Windows windows; //系统主窗口
    MemoryWindow memoryWindow; //内存块实时使用情况
    ConfigureWindow configureWindow; //系统资源实时情况
    PcbTable pcbTable; //进程动态数据表格

    Init(){
        //初始化配置
        initAssembly();

        int nowTime = 0;
        long startTime = System.currentTimeMillis();//计时器
        //当4个队列全为空时则退出运行
        while(pcbFree.size()!=0 || pcbBeReady.size()!=0 ||
                pcbRunning.size()!=0 || pcbBlock.size()!=0){
            long endTime = System.currentTimeMillis();
            if(endTime-startTime <= 200){
                continue;
            }
            startTime = System.currentTimeMillis();

            //空闲队列不为空时进行
            pcbFreeOperation(nowTime);

            //就绪队列不为空时进行
            pcbBeReadyOperation();

            //运行队列不为空时进行
            pcbRunningOperation(nowTime);

            //更新界面上的系统实时信息
            printConfigure(nowTime);

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
    public void initAssembly(){
        //初始化主窗口
        windows = new Windows();
        memoryWindow = new MemoryWindow();
        configureWindow = new ConfigureWindow();

        //系统配置初始化
        configure = new ConfigureTable().init();
        configureRealTime = (Configure)configure.clone();
//        System.out.println("系统初始化成功");
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
//        jcbList = new JcbTable().Init();
//        System.out.println("作业初始化成功");
//        windows.print("作业初始化成功");
//        windows.print(jcbList.toString());

        //进程初始化
        pcbTable = new PcbTable();
        pcbList = pcbTable.init(new PcbDao().select());
//        pcbList = new PcbTable(new PcbDao().select()).Init();
//        System.out.println("进程初始化成功");
        windows.print("进程初始化成功");

        //作业进入空闲队列
        for(int i = 0; i < pcbList.size(); i++){
            pcbFree.add(pcbList.get(i));
        }
//        windows.print("进程从磁盘加载到空闲队列");
    }
    public void printConfigure(long nowTime){
        String pdata = "秒数:"+nowTime+"\n"+"空闲队列："+pcbFree.size()+"\n"+
                "就绪队列："+pcbBeReady.size()+"\n"+
                "运行队列："+pcbRunning.size()+"\n"+"阻塞队列："+pcbBlock.size()+"\n"+
                "完成队列："+pcbComplete.size();
        pcbTable.init(allToOneList());
        configureWindow.print(configureRealTime, pdata);
        memoryWindow.print(memory);

//        System.out.println("秒数"+nowTime);
//        System.out.println("空闲"+pcbFree.size());
//        System.out.println("就绪"+pcbBeReady.size());
//        System.out.println("运行"+pcbRunning.size());
//        System.out.println("阻塞"+pcbBlock.size());
//        System.out.println("完成"+pcbComplete.size());
//        reader.next();
//        warning.Init("pause");
    }
    public void pcbFreeOperation(long nowTime){
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

                            //将此内存块标记为当前进程占用
                            memoryData.setState(1);
                            memoryData.setPcbname(pcb.getName());
                            memory.set(j ,memoryData);

                            //实时系统资源
                            configureRealTime.setMemoryNumber(configureRealTime.getMemoryNumber()-1);
                            configureRealTime.setMemory(configureRealTime.getMemory()-configureRealTime.getMemoryBlock());
                            isRich = 1;
                            windows.print("当前系统内存为"+configureRealTime.getMemory()*100/configure.getMemory()+"%, 内存充足，同意申请");
                            break;
                        }
                        //当一个内存块的大小不能满足进程的需求时
                        //调用多个空闲内存块满足此进程的需求
                        else if(memoryData.getState() == 0&&
                                memoryData.getSpace()<pcb.getNeedSpace()&&
                                getFreeBlock(memory)>=pcb.getNeedSpace()){

                            int needSpace = pcb.getNeedSpace();
                            for(int k = 0; k < memory.size(); k++){
                                MemoryData memoryData1 = (MemoryData) memory.get(k);
                                if(memoryData1.getState() == 0){
                                    memoryData1.setState(1);
                                    memoryData1.setPcbname(pcb.getName());
                                    needSpace -= memoryData1.getSpace();
                                }
                                if(needSpace <= 0){

                                    break;
                                }
                            }
                            //实时系统资源
                            configureRealTime.setMemoryNumber(configureRealTime.getMemoryNumber()-1);
                            configureRealTime.setMemory(configureRealTime.getMemory()-configureRealTime.getMemoryBlock());
                            isRich = 1;
                            windows.print("当前系统内存为"+configureRealTime.getMemory()*100/configure.getMemory()+"%, 内存充足，同意申请");
                            break;
//                            //计算多少块内存能够满足
//                            int num = (int)(pcb.getNeedSpace()*1.0/memoryData.getSpace());
//                            if(pcb.getNeedSpace()%memoryData.getSpace() != 0){
//                                num+=1;
//                            }
//                            System.out.println(pcb.getName());
//                            System.out.println(pcb.getNeedSpace());
//                            System.out.println(memoryData.getSpace());
//                            System.out.println("need");
//                            System.out.println(num);
//                            int numCp = num;
//                            if(num <= configureRealTime.getMemoryBlock()){
//                                for(int k = 0; k < memory.size(); k++){
//                                    MemoryData memoryData2 = (MemoryData) memory.get(k);
//                                    if(memoryData2.getState() == 0){
//                                        memoryData2.setState(1);
//                                        memoryData2.setPcbname(pcb.getName());
//                                        memory.set(k ,memoryData2);
//
//                                        //实时系统资源
//                                        configureRealTime.setMemoryNumber(configureRealTime.getMemoryNumber()-1);
//                                        configureRealTime.setMemory(configureRealTime.getMemory()-configureRealTime.getMemoryBlock());
//
//                                        //需要的内存块总数减去一个内存块（已经分配了一个）
//                                        numCp--;
//                                        if(numCp <= 0){
//                                            isRich = 1;
//                                            windows.print("当前系统内存为"+configureRealTime.getMemory()*100/configure.getMemory()+"%, 内存充足，同意申请");
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
                        }
                    }
                    if(isRich == 1){
                        //内存足够
//                        PCB pcb1 = (PCB) pcbFree.get(i);
//                        pcb.setState("running");
                        pcb.setState("beReady");
                        pcbBeReady.add(pcb);
                        pcbFree.remove(i);
                        windows.print(pcb.getName()+"从空闲队列进入到就绪队列中");
                    }else{
                        //内存不足
//                        PCB pcb2 = (PCB) pcbFree.get(i);
                        pcb.setState("blocked");
                        pcbBlock.add(pcb);
                        pcbFree.remove(i);
                        windows.print(pcb.getName()+"从空闲队列进入到阻塞队列中");
                    }
                }
            }
        }
    }
    public void pcbBeReadyOperation(){
        if(pcbBeReady.size() != 0){
            //当就绪队列不为空，则对队列排序, 按优先级排序
            pcbBeReady = sortPcb(pcbBeReady);
            windows.print("就绪队列中进行按优先级进行排序，并取出优先级最高的进程");
            //若优先级最高的进程需求的资源得到满足则分配资源并进入运行队列
//                    banker.showData(windows,configureRealTime);

//                    windows.print("input");
//                    reader.next();

            if(pcbRunning.size() == 0){
                windows.print("运行队列为空，通过银行家算法对进程的资源申请进行安全性检测");
                //当有进程在运行时，就绪队列的进程无法进入到运行队列中
                PCB pcb = (PCB) pcbBeReady.get(0);
                ((LinkedList) pcbBeReady).remove(0);
//                System.out.println(banker.checkSafe(configureRealTime, (LinkedList) pcbBeReady, pcb));
                List list = banker.getBanker(configureRealTime, (LinkedList) pcbBeReady, pcb, (LinkedList) pcbBlock, (LinkedList) pcbFree);
                //能够分配资源，则进入运行队列
                toRunning(list, pcb);
            }
            else{
                windows.print("运行队列不为空，判断优先级是否能够完成运行资格抢占");
                //判断就绪队列中的进行是否能够完成运行进程的抢占
                PCB pcb = (PCB) pcbBeReady.get(0);//获取最高优先级的进程
                PCB pcb1 = (PCB) pcbRunning.get(0);
                if(pcb.getPriority() > pcb1.getPriority()){
                    //就绪队列中的进程优先级比运行中的要高，抢占运行资格
//                    backResource(configureRealTime, pcb1);
                    pcbRunning.remove(0);
                    pcb1.setState("blocked");
                    pcbBlock.add(pcb1);
                    windows.print(pcb.getName()+"抢占了"+pcb1.getName()+"的运行资格");
                    windows.print(pcb1.getName()+"进入阻塞队列");

                    windows.print("通过应行家算法对"+pcb.getName()+"的资源申请进行安全性检测");
                    ((LinkedList) pcbBeReady).remove(0);
//                System.out.println(banker.checkSafe(configureRealTime, (LinkedList) pcbBeReady, pcb));
                    List list = banker.getBanker(configureRealTime, (LinkedList) pcbBeReady, pcb, (LinkedList) pcbBlock, (LinkedList) pcbFree);
                    //能够分配资源，则进入运行队列
                    toRunning(list, pcb);
                }
            }
        }
    }
    public void pcbRunningOperation(long nowTime){
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
                    //归还内存和占用的资源
                    windows.print(pcb.getName()+"运行完成");
                    pcb.setState("finish");

                    //从内存中取出，归还内存块
                    for(int j = 0; j < memory.size(); j++){
                        MemoryData memoryData = (MemoryData) memory.get(j);

//                        if(memoryData.getState()==1){
//                            System.out.println(j);
//                            System.out.println(memoryData);
////                            System.out.println(memoryData.getPcbname());
//                            System.out.println(pcb.getName());
//                            System.out.println(nowTime);
//                        }

                        if(memoryData.getState()==1&&
                                memoryData.getPcbname().equals(pcb.getName())){
                            memoryData.setPcbname(null);
                            memoryData.setState(0);
                            memory.set(j, memoryData);

                            //更新实时显示的系统信息中内存的数据
                            configureRealTime.setMemory(configureRealTime.getMemory()+configureRealTime.getMemoryBlock());
                            configureRealTime.setMemoryNumber(configureRealTime.getMemoryNumber()+1);
                        }
                    }
                    windows.print(pcb.getName()+"已归还内存");

                    //归还资源向量所占用的资源
                    configureRealTime = backResource(configureRealTime, pcb);
                    pcbComplete.add(pcb);
                    pcbRunning.remove(i);
                    windows.print(pcb.getName()+"已归还资源");
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
            //当阻塞队列有东西时将其放回就绪队列中
            if(pcbBlock.size() != 0){
                for (int i = 0; i < pcbBlock.size(); i++){
                    PCB pcb = (PCB) pcbBlock.get(i);
                    windows.print(pcb.getName()+"从阻塞队列进入就绪队列");
                    pcbBeReady.add(pcb);
                    pcbBlock.remove(i);
                }
            }
        }
    }
    public void toRunning(List list, PCB pcb){
        if((Boolean) list.get(0) == true){
            windows.print(pcb.getName()+"向系统提出资源申请"+pcb.getType());
            windows.print("当前系统资源"+configureRealTime.getType());
            windows.print("通过应行家算法检测找到安全序列组"+list.get(2));
            windows.print("为其分配资源");
            pcb.setState("running");
            pcb.setPriority(pcb.getPriority()-1);//优先级减 1
            pcbRunning.add(pcb);//加入运行队列
            Configure configure = (Configure) list.get(1);
            configureRealTime.setType(configure.getType());
        }else {
            int a = 1;//无用，去掉idea代码重复的提示
            windows.print(pcb.getName()+"向系统提出资源申请"+pcb.getType());
            windows.print("当前系统资源"+configureRealTime.getType());
            windows.print("通过应行家算法检测没有找到安全序列组");
            windows.print("不为其分配资源，使其进入阻塞");
            pcb.setState("blocked");
            pcbBlock.add(pcb);
        }
    }
    //获取当前还剩余多少空闲的内存空间
    public int getFreeBlock(List memory){
        int free = 0;
        for(int i = 0; i < memory.size(); i++){
            MemoryData memoryData = (MemoryData) memory.get(i);
            if(memoryData.getState() == 0){
                free+=memoryData.getSpace();
            }
        }

        return free;
    }
}
