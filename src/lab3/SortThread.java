package lab3;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class SortThread implements Runnable{


	private static long threadID = 0;
	private long seed;
	private Map map;
	private Semaphore mapMutex;
	private Boolean loadedSortingFunction;
	private String threadName;
	private int seedMin;
	private int seedMax;
	private int numberOfElementsToSort;
	private int mapSize;
	private Random rand;

	java.lang.reflect.Method methodSolve;
	Class<?> reflectIntSorter;

	public SortThread(long seed, Map map, Semaphore mapMutex, int seedMin, int seedMax, int numberOfElementsToSort)
	{
		threadID++;

		this.seed = seed;
		this.map = map;
		this.mapMutex = mapMutex;
		this.seedMin = seedMin;
		this.seedMax = seedMax;
		this.numberOfElementsToSort = numberOfElementsToSort;

		mapSize = seedMax- seedMin;
		rand = new Random(seed);
		loadedSortingFunction = false;
		threadName = "SortThread " + threadID + " > ";

		System.out.println("SortThread> Created new thread. ID: " + threadID);

		try{
			reflectIntSorter = Class.forName("package1.IntSorter");

			System.out.println(threadName + "Found IntSorter");

			methodSolve = reflectIntSorter.getMethod("solve", int[].class);

			System.out.println(threadName + "Found method package1.IntSorter.solve");

			if(methodSolve != null)
				loadedSortingFunction = true;

		}
		catch(Exception e){
			System.out.println("Exception> " + threadName + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		if(loadedSortingFunction == false)
		{
			System.out.println(threadName + "ERROR > No loaded sorting function");
			return;
		}

		if(map.size() >= mapSize)
		{
			System.out.println(threadName + "ERROR > Whole map contains only sorted elements");
			return;
		}

		try
		{
			while(true)
			{
				System.out.println(threadName + "New lap");
				int seed;

				//
				// check if element with rand seed exist.
				//

				int array[] = new int[numberOfElementsToSort];

				while(true)
				{
					mapMutex.acquire();
					if(map.size() >= mapSize)
					{
						System.out.println(threadName + " INFO > Whole map contains only sorted elements");
						mapMutex.release();
						return;
					}

					seed = rand.nextInt(mapSize) + seedMin;

					if(map.containsKey(seed) == true)
					{
						mapMutex.release();
						continue;
					}

					//
					// Fill data for rand seed
					//
					Random randTable = new Random(seed);

					array = new int[numberOfElementsToSort];

					for (int i = 0; i< numberOfElementsToSort; i++)
					{
						array[i] = randTable.nextInt(10000);
					}

				    map.put(seed, array.clone());
				    mapMutex.release();

				    System.out.println(threadName + "INFO > Createt array for seed: "+ seed);
				    break;
				}

				System.out.println(threadName + "INFO > Start sort array for seed: "+ seed);

				Object obj = methodSolve.invoke(reflectIntSorter.newInstance(), array);
				array = (int[])obj;
				System.out.println(threadName + "INFO > Array for seed: "+ seed + " has been sorted");

				mapMutex.acquire();
				map.put(seed, array);
				mapMutex.release();

				System.out.println(threadName + "INFO > Array for seed: "+ seed + " has been added to map");

				//Thread.sleep(4000);
			}
		}
		catch (Exception e)
		{
			System.out.println("Exception Thread" + threadID + "> " + e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			mapMutex.release();
		}
	}
}
