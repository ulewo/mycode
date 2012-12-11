package com.lhl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SavaLog
{

	public static void savaLog()
	{

		Date d = new Date();
		Timer timer = new Timer();
		TimerTask task = new TimerTask()
		{
			public void run()
			{

				SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println(form.format(new Date()));
				System.out.println("======================��ʱ����ʼ����");
			}
		};
		timer.schedule(task, d, 1000 * 60);
	}

	public static void main(String[] args)
	{

		savaLog();
	}
}
