package util;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class FIFO {
    static int volum;//栈的容量
    static List<content>list=new LinkedList<content>();
    static int visit[];//要访问的页面
    static int count=0;//记录页面置换的次数
    public static void main(String[]args)
    {
        Scanner sc=new Scanner(System.in);
        System.out.print("请输入栈的容量:");
        volum=sc.nextInt();
        System.out.println("请输入页面总数:");
        int number=sc.nextInt();
        visit=new int[number];
        System.out.println("请输入各个页面号:");
        for(int i=0;i<number;i++)
            visit[i]=sc.nextInt();
        sFIFO();
        System.out.println("置换次数为:"+count);

    }
    public static void sFIFO()
    {
        int index=0;
        while(index<visit.length)
        {
            boolean flag=false;
            if(list.size()<=volum)
            {
                for(int i=0;i<list.size();i++)
                {
                    if(list.get(i).id==visit[index])
                    {//若已经存在，直接返回看下一个
                        for(int j=0;j<list.size();j++)
                        {
                            int te=list.get(j).count;
                            list.get(j).setCount(te+1);
                        }
                        flag=true;
                        break;
                    }
                }
                if(!flag)
                {
                    if(list.size()==volum)
                    {//如果满了，只能置换页面了
                        int old=list.get(0).count;
                        int temp=0;//暂存最老页面编号
                        //寻找最老的页面
                        for(int j=1;j<volum;j++)
                        {
                            if(old<(list.get(j).count))
                            {
                                temp=j;
                                old=list.get(j).count;
                            }
                        }

                        System.out.println("因为队列满，页面"+list.get(temp).id+"被置换出来");

                        for(int stt=0;stt<list.size();stt++)
                            (list.get(stt).count)++;
                        list.get(temp).count=1;
                        list.get(temp).id=visit[index];
                        count++;
                    }
                    else
                    {
                        for(int t=0;t<list.size();t++)
                        {
                            int te=list.get(t).count;
                            list.get(t).setCount(te+1);
                        }
                        content ncontent=new content(visit[index],1);
                        list.add(ncontent);
                    }

                }
            }
            System.out.print("第"+(index+1)+"次的队列内容：");
            for(int f=0;f<list.size();f++)
                System.out.print(list.get(f).id+" ");
            System.out.println();
            index++;
        }


    }

}

class content
{
    int id;//页面号
    int count;//属于第几老的页面
    public void setCount(int count)
    {
        this.count=count;
    }
    public content(int id,int count)
    {
        this.id=id;
        this.count=count;
    }

}