package com.victor.ipc.thread;

import com.victor.ipc.dao.MessageDao;

public class SendNameThread extends Thread {
	public void run(){
		while (true) {
			try {
				Thread.sleep(1000);
				MessageDao.dao.sendUses();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
