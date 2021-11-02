package init;

import bean.Configure;
import bean.JCB;
import bean.PCB;
import dao.ConfigureDao;
import util.Windows;
import util.table.ConfigureTable;
import util.table.JcbTable;
import util.table.PcbTable;
import util.windows.Resources;

import java.util.LinkedList;
import java.util.List;

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
    private List<JCB> jcbList; //作业
    private List<PCB> pcbList; //进程

    init(){
        //初始化主窗口
        Windows windows = new Windows();
        //系统配置初始化
        configure = new ConfigureTable().init();
        System.out.println("系统初始化成功");
        windows.print(configure.toString());
        windows.print("系统初始化成功");

        //管道初始化
        pcbFree = new LinkedList();
        pcbBeReady = new LinkedList();
        pcbRunning = new LinkedList();
        pcbBlock = new LinkedList();
        System.out.println("管道初始化成功");
        windows.print("管道初始化成功");

        //作业初始化
        jcbList = new JcbTable().init();
        System.out.println("作业初始化成功");
        windows.print("作业初始化成功");

        //进程初始化
        pcbList = new PcbTable().init();
        System.out.println("进程初始化成功");
        windows.print("进程初始化成功");

        //资源管理器
        Resources resources = new Resources();
    }

    public void setData(){

    }

    public static void main(String[] args) {
        new init();
    }
}
