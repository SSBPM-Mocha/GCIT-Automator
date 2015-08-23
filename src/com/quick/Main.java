package com.quick;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import org.hyperic.sigar.*;
import org.ini4j.*;

public class Main {

    private static int upCount = 2;
    private static int downCount = 1; //same tyhing as fileprocessedcount I think? use this idea for the ini for resuming
    private static long keyPressWait = 40;
    private static Sigar sigar = new Sigar();
    private static String diskLetter;
    private static int filesProcessedCount = 1; //move initialization to .ini

    public static void main(String[] args) throws AWTException, InterruptedException,
            SigarException, InvalidFileFormatException, IOException {

        System.load("C:\\Program Files\\___Random shit like batch files\\GCIT Auto\\sigar-amd64-winnt.dll");
        Ini ini = new Ini(new File("config/config.ini"));

        Scanner console = new Scanner(System.in);
        Robot r = new Robot();
        System.out.println("How many files are there?");
        int loopCount = console.nextInt();
        System.out.println("What disk letter is being written to? ex: C D E F");
        diskLetter = console.next() + ":";
        System.out.println("Wait: 3");
        Thread.sleep(1000);
        System.out.println("Wait: 2");
        Thread.sleep(1000);
        System.out.println("Wait: 1");
        Thread.sleep(1000);

        while(loopCount > 0) {
            open(r);
            save(r);
            countdown();
            loopCount--;
            filesProcessedCount++;
        }
        System.out.println("DONE!");
        console.nextLine();



    }

    public static void countdown() throws SigarException, InterruptedException {
        //sigarTest();
        boolean copying = true;
        int counter = 0;
        int secondCounter = 4; //starts at 4 due to sleep at end of save()
        int diskIdleCounter = 0;
        long prevTime = 0;
        long prevBytes = 0;
        long currentRate = 0;
        final long interval = 250;
        int pollsPerSec = 4;//interval/second
        int pollTracker = 0;

        while (copying) {
            if (pollsPerSec - 1 > pollTracker) {
                pollTracker++;
            } else {
            //if (counter % 2 == 0) {
                System.out.println("Timer: " + secondCounter);
                secondCounter++;
                pollTracker = 0;
            }
            counter++;

            long time = System.currentTimeMillis();
            long bytes = sigar.getDiskUsage(diskLetter).getWriteBytes();
            if (prevTime != 0) {
                currentRate = (bytes - prevBytes) / ((time - prevTime) / interval);
                //System.out.println("disk written bytes per second=" + currentRate);
            }
            prevTime = time;
            prevBytes = bytes;
            Thread.sleep(interval);

            if (currentRate < 400000) {
                diskIdleCounter++;
                if (diskIdleCounter == 25) {
                    copying = false;
                }

            } else {
                diskIdleCounter = 0;
            }
        }
    }
    public static void open(Robot r) throws InterruptedException {

        System.out.println();
        System.out.println("Opening file " + filesProcessedCount + "....");

        r.keyPress(18); //alt
        Thread.sleep(keyPressWait);
        r.keyRelease(18);

        r.keyPress(40); //down
        Thread.sleep(keyPressWait);
        r.keyRelease(40);

        r.keyPress(10); //enter
        Thread.sleep(keyPressWait);
        r.keyRelease(10);


        Thread.sleep(600);

        r.keyPress(16); //shift



        Thread.sleep(keyPressWait);

        r.keyPress(9); //tab
        Thread.sleep(keyPressWait);
        r.keyRelease(9);

        r.keyPress(9); //tab
        Thread.sleep(keyPressWait);
        r.keyRelease(9);

        r.keyRelease(16); //release shift

        for (int i = 0; i < upCount; i++) {
            r.keyPress(38); //up
            Thread.sleep(keyPressWait);
            r.keyRelease(38);
        }
        for (int i = 0; i < downCount; i++) {
            r.keyPress(40); //down
            Thread.sleep(keyPressWait);
            r.keyRelease(40);
        }
        downCount++;

        r.keyPress(10); //enter
        Thread.sleep(keyPressWait);
        r.keyRelease(10);

        Thread.sleep(600);

    }
    public static void save(Robot r) throws InterruptedException {

        System.out.println("Saving file " + filesProcessedCount + "....");
        System.out.println();

        r.keyPress(18); //alt
        Thread.sleep(keyPressWait);
        r.keyRelease(18);

        r.keyPress(40); //down
        Thread.sleep(keyPressWait);
        r.keyRelease(40);

        r.keyPress(40); //down
        Thread.sleep(keyPressWait);
        r.keyRelease(40);

        r.keyPress(40); //down
        Thread.sleep(keyPressWait);
        r.keyRelease(40);

        r.keyPress(40); //down
        Thread.sleep(keyPressWait);
        r.keyRelease(40);

        r.keyPress(10); //enter
        Thread.sleep(keyPressWait);
        r.keyRelease(10);

        r.keyPress(10); //enter
        Thread.sleep(keyPressWait);
        r.keyRelease(10);

        Thread.sleep(600);

        r.keyPress(10); //enter
        Thread.sleep(keyPressWait);
        r.keyRelease(10);

        for (int i = 0; i <3; i++) {
            System.out.println("Timer: " + i);
            Thread.sleep(1000);
        }
        System.out.println("Timer: 3");
        Thread.sleep(250);
    }
}
