package Philo;


import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The Main class is executing all program.
 * It Creates five ReentrantLocks as forks, and creates five Threads as Philosopher. 
 * It call other classes and functions to simulate the situation that philosopher problem.
 * @author Jun Wang 
 **/
public class Main {	
	private static ExecutorService exec;
	private static ReentrantLock[] forks;
	
	public static void main(String[] args) {		
		int size= 5;
	
			/*
			 * Create five Locks as forks
			 */
			forks =new ReentrantLock[size];
			forks = Main.createFork(size, forks);
			
			/*
			 * Create a fixed Thread pool which size is 5 as five Philosophers.
			 */
			exec = Executors.newFixedThreadPool(size);
			for (int i = 0; i < size; i++) {
				exec.execute(new Philosopher(i,forks));}}

	/**
	 * This method is create five ReentrantLocks as five forks.
	 * @param n the size of forks.
	 * @param forks an empty ReentrantLock Array. 
	 * @return forks a ReentrantLock Array which include n ReentrantLocks.
	 */
	public static ReentrantLock[] createFork (int n, ReentrantLock[] forks) {
		 for (int i =0; i<n; i++) {			
				forks[i] = new ReentrantLock();				
			}
			System.out.println("Create "+ n+" forks.");
		 return forks;
	}

}

/**
 * This class implements a Runnable to simulate a Philosopher.
 * It simulates all the things that philosophers might do and encounter.
 *1. Each philosopher can only take forks to his left and right.
 *2. In the beginning, philosophers decided whether eating or thinking because of a random Numbers.
 *3. If thinking is decided, the philosopher will think for a random period of time and then re-decide whether to eat or think.
 *4. If eating is decided, philosophers only eat if they are given two forks at the same time.
 *5. After each eating, the philosopher put down both forks and must have started thinking, then re-decide whether to eat or think.
 *6. If there is no fork, the philosopher will go to think a period of random time, then re-decide eating or thinking.
 *7, when only a fork is available, the philosopher will put down the fork and started thinking, then re-decide whether to eat or think.
 * @author June
 */
class Philosopher implements Runnable  {
	//
	private int id;
	private ReentrantLock forkL;
	private ReentrantLock forkR;
	private Random random = new Random();
	private int r;

	/**
	 * This is the constructor of Philosopher class.
	 * @param id is a identify number of each philosopher .
	 * @param forks is a Array which means all the forks in the table.
	 */
	public Philosopher (int id, ReentrantLock[] forks) {
		this.id = id;
		this.forkL = forks[id];
		this.forkR = forks[(id+1)%5];
	}
	
	/**
	 * This method is get the left fork of the philosopher.
	 * @return forkL a ReentrantLock which is the left fork of the philosopher.
	 */
	public ReentrantLock getForkL() {
		return forkL;
	}
	
	/**
	 * This method is get the right fork of the philosopher.
	 * @return forkL a ReentrantLock which is the right fork of the philosopher.
	 */
	public ReentrantLock getForkR() {
		return forkR;
	}
	
	/**
	 * This method create a Random which used to decide philosopher eating or thinking at first.
	 * @return r a intRandom which in range [0,3).
	 */
	public int decideRandom() {
		r = random.nextInt(3);
		return r;
	}
	
	/**
	 * This method simulates eating and thinking of philosopher.
	 * First philosopher will decide thinking or eating by a random integer. 
	 * If the random equal to 0, function "thinking" will be call.
	 * If he eat, he will try to pick up two forks to eat. When he is eating, thread will sleep 500msec then put down two forks and go to think.
	 * If he does not get fork or only get one fork, he will put down the fork and thinking. 
	 * @param r an integer which decide the philosopher eating(=0) of thinking(=1 or =2) at first.
	 */
	public void eating_thinking(int r,ReentrantLock forkL, ReentrantLock forkR) {
		if (r == 0) {
			this.thinking();
		}else {
			if (forkL.tryLock()) {
					if(forkR.tryLock()){
						try {System.out.println("Philosopher"+ this.id+" pick up two forks "+ this.id+" and "+(this.id+1)%5+" to eat!" );
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						forkL.unlock();
						forkR.unlock();
						System.out.println("Philosopher"+ this.id+" finish eating and put down two forks!");
						this.thinking();
					}else{
						forkL.unlock();
						this.thinking();}
			}else {
				this.thinking();
			}
		}
	}
	
	/**
	 * This method simulates thinking of philosophers.
	 * When the method thinking is called, computer will create a random integer which from 0 to 500 to use for sleep. 
	 * The thread will sleep during the given period.
	 */
	public void thinking() {
		r=random.nextInt(500);
		System.out.println("Philosopher"+ this.id+" is thinking!");
		try {
			Thread.sleep(r);
		} catch (InterruptedException e) {

			e.printStackTrace();}
	}
	

	
	/**
	 * This method simulates all situations for a philosopher.
	 * When a philosopher sets beside the table, he goes into a loop which is eating or thinking.
	 */
	@Override
	public void run() {
		while(true) {
//			synchronized (this){
				r = this.decideRandom();
				this.eating_thinking(r,forkL,forkR);
}}}