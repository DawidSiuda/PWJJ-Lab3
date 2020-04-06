package lab3;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Semaphore;


public class Main {

	static private final int SEED_MIN = 0;
	static private final int SEED_MAX = 50000;
	static private final int LIST_SIZE = 600;
	static private final int NUMBER_OF_THREAD = 4;


	static private Boolean endProgram = true;

	public static void main(String[] args)
	{

		Map<Integer , int[]> map = new HashMap<Integer, int[]>();

		Thread sortThreadTab[] = new Thread[NUMBER_OF_THREAD];

		Semaphore mutex = new Semaphore(1);

		//
		// Create and run threads.
		//

		Random rand = new Random(System.currentTimeMillis());


		//for(Thread thread : sortThreadTab)
		for(int i = 0; i <= NUMBER_OF_THREAD; i++)
		{
			sortThreadTab[i] = new Thread(new SortThread(rand.nextInt(), map, mutex, SEED_MIN, SEED_MAX, LIST_SIZE));
			sortThreadTab[i].start();
		}

		while(true){
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
				 Thread.sleep(1000);
				}
				catch(Exception e){
					System.out.println("----------------------------------------------------------");
					System.out.println("Exception Sleep> " + e.getMessage());
					e.printStackTrace();
				}
				continue;
			}
		}

		System.out.println("Main> END PROGRAM");

		System.exit(0);
	}

		// --------------------------------------------------------------------------
//		System.out.println("Started program");
//
//
//		try{
//			Random rand = new Random();
//			int size = 10;
//			int array[] = new int[size];
//
//			//
//			// create array
//			//
//			for (int i = 0; i< size; i++)
//			{
//				array[i] = rand.nextInt(10000);
//			}
//
//			//
//			// print array
//			//
//			{
//				final int linesize = 15;
//				int currentPossitionInLine = 0;
//
//				for (int i = 0; i< size; i++)
//				{
//
//				    System.out.print(array[i] + "  ");
//				    currentPossitionInLine++;
//				    if(currentPossitionInLine >= linesize)
//				    {
//				    	System.out.println("");
//				    	currentPossitionInLine = 0;
//				    }
//				}
//				if(currentPossitionInLine != linesize)
//					System.out.println("");
//			}
//
//			//
//			// Get method
//			//
//			Class<?> reflectIntSorter = Class.forName("package1.IntSorter");
//
//			System.out.println("Main> Found IntSorter");
//
//			java.lang.reflect.Method methodSolve = reflectIntSorter.getMethod("solve", int[].class);
//
//			System.out.println("Main> Found method package1.IntSorter.solve");
//
//			if(methodSolve == null)
//			{
//				System.out.println("Main> ERROR: Cannot find solve");
//				return;
//			}
//
//			Object obj = methodSolve.invoke(reflectIntSorter.newInstance(),array);
//			 array = (int[])obj;
//
//			//
//			// print array
//			//
//			{
//				final int linesize = 15;
//				int currentPossitionInLine = 0;
//
//				System.out.println("\n\n");
//				for (int i = 0; i< size; i++)
//				{
//
//				    System.out.print(array[i] + "  ");
//				    currentPossitionInLine++;
//				    if(currentPossitionInLine >= linesize)
//				    {
//				    	System.out.println("");
//				    	currentPossitionInLine = 0;
//				    }
//				}
//			}

//			//getDeclaredMethod("solve", int[].class);
//
//			// solve(List<IntElement> list)
//			// reflectClass.getDeclaredMethod(name, parameterTypes)
//
//			//
//			// get IntElement
//			//
//			Class<?> reflectIntElement = Class.forName("IntElement");
//			Type typeIntElement = reflectIntElement.getT
//
//			//
//			//create IntElement list
//			//
//
//			//List<?> list = new ArrayList<>();
//			List list = Array.newInstance(reflectIntElement.class, LIST_SIZE);
//
//
//			//
//			// Fill list
//			//
//
//			Random rand = new Random();
//
//			for (int i = 0; i< LIST_SIZE; i++)
//			{
//				Object intElement = reflectIntElement.getConstructor(
//						   int.class).newInstance(rand.nextInt(10000));
//				//IntElement intElement = new IntElement(dtemp--);
//				//IElement intElement = new FloatElement((float)rand.nextInt(10000)/10, "str");
//				list.add(intElement);
//			}
//
//		}
//		catch(Exception e){
//			System.out.println("----------------------------------------------------------");
//			System.out.println("Exception> " + e.getMessage());
//			e.printStackTrace();
//		}
//	}
}
