package Philo;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This test is a mutltithreading test.
 * Due to there are thread sleeping in the process, the test is a maintest.
 * Verify that it is not Deadlocks and Livelocks by looking at print
 * @author June
 */
public class MainTest {
	private static ExecutorService exec;
	private static ReentrantLock[] forks;
			
	
	public static void main(String[] args) {
		forks =new ReentrantLock[5];
		forks = Main.createFork(5, forks);
		exec = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 5; i++)
			exec.execute(new Philosopher(i,forks));}}
