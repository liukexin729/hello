package com.example.hello.server.impl;

import com.example.hello.server.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Administrator
 */
@Slf4j
@Service
public class CommandServiceImpl implements CommandService, InitializingBean {

    @Value("${cmd.threadname:cmd-executor}")
    private String threadName;

    @Value("${cmd.taskQueueMaxStorage:20}")
    private Integer taskQueueMaxStorage;

    @Value("${cmd.corePoolSize:4}")
    private Integer corePoolSize;

    @Value("${cmd.maximumPoolSize:8}")
    private Integer maximumPoolSize;

    @Value("${cmd.keepAliveSeconds:15}")
    private Integer keepAliveSeconds;
    private ThreadPoolExecutor executor;
    private static final String BASH = "sh";
    private static final String BASH_PARAM = "-c";

    @Override
    public String execute(String cmd) {
        Process p = null;
        String res;
        try {
            // need to pass command as bash's param,
            // so that we can compatible with commands: "echo a >> b.txt" or "bash a && bash b"
            List<String> cmds = new ArrayList<>();
            cmds.add(BASH);
            cmds.add(BASH_PARAM);
            cmds.add(cmd);
            log.info("command list:{}",cmds);
            ProcessBuilder pb = new ProcessBuilder(cmds);
            //启动子进程
            p = pb.start();

            log.info("success create process");
            /**
             * get 标准错误输出  （BufferReader - StringBuffer）
             *
             */
            Future<String> errorFuture = executor.submit(new ReadTask(p.getErrorStream()));
            log.info("errorFuture:{}",errorFuture);
            Future<String> resFuture = executor.submit(new ReadTask(p.getInputStream()));
            log.info("resFuture:{}",resFuture);
            /**
             * code 0 :表示正常终止
             */
            int exitValue = p.waitFor();
            if (exitValue > 0) {
                throw new RuntimeException(errorFuture.get());
            }

            /**
             * 返回计算结果
             */
            res = resFuture.get();

            log.info("返回计算结果:{}",res);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
        // remove System.lineSeparator() (actually it's '\n') in the end of res if exists
        if (StringUtils.isNotBlank(res) && res.endsWith(System.lineSeparator())) {
            res = res.substring(0, res.lastIndexOf(System.lineSeparator()));
        }

        log.info("res(resFuture):{}",res);
        return res;

    }

    @Override
    public void afterPropertiesSet() throws Exception {

        /**
         * corePoolSize  int 核心线程池大小
         * maximumPoolSize  int  最大线程池大小
         * keepAliveTime  long 线程最大空闲时间   zhu: keepAliveTime 该参数默认对核心线程无效
         * unit  TimeUnit 时间单位
         * workQueue  BlockingQueue<Runnable>(类型)	线程等待队列
         * threadFactory  ThreadFactory	线程创建工厂
         * handler  RejectedExecutionHandler  拒绝策略
         *
         *  public ArrayBlockingQueue(int capacity)  capacity 容量
         */

        log.info("core:{},max:{},keep:{},unit:{},task:{}",corePoolSize,maximumPoolSize,keepAliveSeconds,TimeUnit.SECONDS,taskQueueMaxStorage);

        executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveSeconds, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(taskQueueMaxStorage),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                    /**
                         public Thread(Runnable target, String name) {
                           init(null, target, name, 0);
                        }
                        runnable target调用run方法 ， name 线程名
                     */
                        return new Thread(r, threadName + r.hashCode());
                    }
                },
                new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 　对于一个任务的执行有时我们不需要它返回结果，但是有我们需要它的返回执行结果。对于线程来讲，
     *  如果不需要它返回结果则实现Runnable，而如果需要执行结果的话则可以实现Callable。
     *  在线程池同样execute提供一个不需要返回结果的任务执行，而对于需要结果返回的则可调用其submit方法。
     */
    class ReadTask implements Callable<String> {
        InputStream is;

        ReadTask(InputStream is) {
            this.is = is;
        }

        @Override
        public String call() throws Exception {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
    }
}
