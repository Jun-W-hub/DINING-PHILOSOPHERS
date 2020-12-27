package Philo;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.concurrent.locks.ReentrantLock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This class is a single Thread test.
 * In this class, single thread p is created to test same basic methods whether can run in one thread or not.
 * @author June
 *
 */
public class test {
	private PrintStream console = null;
	private ByteArrayOutputStream bytes =null;
	private int r;
	private static ReentrantLock[] forks;
	private static Philosopher p4;

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		forks =new ReentrantLock[5];
		forks = Main.createFork(5, forks);
		p4 = new Philosopher(4,forks);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bytes = new ByteArrayOutputStream();
		console = System.out;
		System.setOut(new PrintStream(bytes));	
	}

	@After
	public void tearDown() throws Exception {
		System.setOut(console);		
		/*
		 * unlock all forks.
		 */
		for(int i=0;i<5;i++) {
			forks[i].tryLock();
			forks[i].unlock();}
		}
	
	/**
	 /* This test check does method create a ReentrantLock which length is 5.	
	 */
	@Test
	public void CreateForkTest(){		
		 forks = Main.createFork(5, forks);
		 
		 /*
		  * Test the length of forks.
		  */
		 assertEquals(5,Array.getLength(forks));
		 
		 /*
		  * Test the type of forks.
		  */
		 assertTrue(forks[0] instanceof ReentrantLock);
		 
		 /*
		  * Test the out.println of method.
		  */
		 assertEquals("Create 5 forks.", bytes.toString().trim().replace("\r", ""));
	}
	
	
	/**
	 * This test is check the method decideRandomTest.
	 * The method should create a Int Random which from [0,3).
	 */
	@Test
	public void DecideRandomTest() {
		r = p4.decideRandom();
		//test the r is it equal to 0 or 1 or 2.
		assertTrue(r==0 || r==1 ||r==2);				
	}
	
	
	/**
	 * This test check the thinking method.
	 */
	@Test
	public void ThinkingTest() {

		p4.thinking();
		 assertEquals("Philosopher4 is thinking!", bytes.toString().trim().replace("\r", ""));		 
	}
	
	
	/**
	 * This test check forkL and forkR in method eating_thinking.
	 * The philosopher4 is a boundary one, because the left of he is fork4, the right of he is fork0.
	 */
	@Test
	public void Eating_thinkingTest() {
		//Test for the Philosopher4, is the Left fork as same as fork4, and is the Right fork as same as fork0
		assertSame(forks[4],p4.getForkL());		
		assertSame(forks[0],p4.getForkR());
	}	
	
	/**
	 * This test check method eating_thinking when the Random is 0(decide to think).
	 */
	@Test
	public void Eating_thinkingTest0() {

		//Test when the Random is 0(decide to think.)
		p4.eating_thinking(0,forks[4], forks[0]);
		assertEquals("Philosopher4 is thinking!", bytes.toString().trim().replace("\r", ""));
	}
	
	
	/**
	 * This test check method eating_thinking when the Random is 1(decide to eat).
	 */
	@Test
	public void Eating_thinkingTest1() {			
	//Test when the Random is 1(decide to eat.)
		p4.eating_thinking(1,forks[4], forks[0]);
		assertEquals("Philosopher4 pick up two forks 4 and 0 to eat!\n"+
				"Philosopher4 finish eating and put down two forks!\n" + 
				"Philosopher4 is thinking!", bytes.toString().trim().replace("\r", ""));
	}
	
	/**
	 * This test check method eating_thinking when the Random is 2(decide to eat.)
	 */
	@Test		
	public void Eating_thinkingTest2() {
		p4.eating_thinking(2,forks[4], forks[0]);
		assertEquals("Philosopher4 pick up two forks 4 and 0 to eat!\n"+
				"Philosopher4 finish eating and put down two forks!\n" + 
				"Philosopher4 is thinking!", bytes.toString().trim().replace("\r", ""));	
	}					
	}
	

	
	
	


