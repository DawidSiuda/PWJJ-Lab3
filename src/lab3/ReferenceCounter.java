package lab3;

import java.util.concurrent.Semaphore;

public class ReferenceCounter {

	public ReferenceCounter()
	{
		g1= 0;
		g2= 0;
		m1= 0;
		m2= 0;
		mutex = new Semaphore(1);
	}

	public void incrementMemoryCall()
	{
		try
		{
		mutex.acquire();
		g1++;
		g2++;
		mutex.release();
		}
		catch (Exception e)
		{
			System.out.println("Exception ReferenceCounter> " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void incrementUnsuccessfulMemoryCall()
	{
		try
		{
		mutex.acquire();
		m1++;
		m2++;
		mutex.release();
		}
		catch (Exception e)
		{
			System.out.println("Exception ReferenceCounter> " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void cleanSecondsCountern()
	{
		try
		{
		mutex.acquire();
		g2 = 0;
		m2 = 0;
		mutex.release();
		}
		catch (Exception e)
		{
			System.out.println("Exception ReferenceCounter> " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void PrintStat()
	{
		try
		{
		mutex.acquire();
		System.out.println("ReferenceCounter> g1:" + g1 + ", m1: " + m1+ ", g2: " + g2 + ", m2: " + m2);
		System.out.println("ReferenceCounter> m1/g1 = " + (float)((float)m1/g1) + ", m2/g2 = " + (float)((float)m2/g2));
		mutex.release();
		}
		catch (Exception e)
		{
			System.out.println("Exception ReferenceCounter> " + e.getMessage());
			e.printStackTrace();
		}
	}

	int g1;
	int g2;
	int m1;
	int m2;
	Semaphore mutex;
}
