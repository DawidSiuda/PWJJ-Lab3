package lab3;

import java.lang.ref.Reference;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Semaphore;


public class Main {

	static private final int SEED_MIN = 0;
	static private final int SEED_MAX = 50000;
	static private final int LIST_SIZE = 600;
	static private final int NUMBER_OF_THREAD = 4;
	static private final int TIME_BETWEEN_SHOW_STATS = 2000;


	static private Boolean endProgram = true;

	public static void main(String[] args)
	{

		Map<Integer , Reference<int[]>> map = new HashMap<Integer,  Reference<int[]>>();

		Thread sortThreadTab[] = new Thread[NUMBER_OF_THREAD];

		Semaphore mutex = new Semaphore(1);

		ReferenceCounter referenceCounter = new ReferenceCounter();

		//
		// Create and run threads.
		//

		Random rand = new Random(System.currentTimeMillis());


		//for(Thread thread : sortThreadTab)
		for(int i = 0; i < NUMBER_OF_THREAD; i++)
		{
			sortThreadTab[i] = new Thread(new SortThread(rand.nextInt(), map, mutex, SEED_MIN, SEED_MAX, LIST_SIZE, referenceCounter));
			sortThreadTab[i].start();
		}

		while(true){
			referenceCounter.PrintStat();
			referenceCounter.cleanSecondsCountern();
			endProgram = true;
			for(Thread thread : sortThreadTab)
			{
				if(thread.isAlive() == true)
				{
					endProgram = false;
					break;
				}
			}

			if(endProgram == true)
			{
				break;
			}
			else
			{
				try{
				 Thread.sleep(TIME_BETWEEN_SHOW_STATS);
				}
				catch(Exception e){
					System.out.println("----------------------------------------------------------");
					System.out.println("Exception Sleep> " + e.getMessage());
					e.printStackTrace();
				}
			}
		}

		System.out.println("Main> END PROGRAM");

		System.exit(0);
	}

}
