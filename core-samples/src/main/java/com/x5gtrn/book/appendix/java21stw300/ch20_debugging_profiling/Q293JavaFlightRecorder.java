package com.x5gtrn.book.appendix.java21stw300.ch20_debugging_profiling;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import jdk.jfr.Configuration;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordingFile;
import java.nio.file.Files;
import java.nio.file.Path;
public final class Q293JavaFlightRecorder implements Sample {
    public String chapter(){return "20_Debugging_Profiling";}
    public int problem(){return 293;}
    public String title(){return "Java Flight Recorder による障害解析";}
    public void run() throws Exception {
        Configuration config = Configuration.getConfiguration("default");
        Path dir = Files.createTempDirectory("q293");
        Path jfr = dir.resolve("recording.jfr");
        try (Recording recording = new Recording(config)) {
            recording.start();
            long acc = 0;                                   // 記録中に負荷を発生させる
            for (int i=0;i<800_000;i++) acc += Integer.toString(i).length();
            Thread.sleep(150);
            recording.stop();
            recording.dump(jfr);
            System.out.println("acc=" + acc);
        }
        long events = RecordingFile.readAllEvents(jfr).size();
        System.out.println("JFR 記録イベント数 = " + events + "（低オーバーヘッドで常時記録し障害解析に使える）");
        Files.deleteIfExists(jfr); Files.deleteIfExists(dir);
    }
}
