package com.victor.ipc.dao;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.victor.ipc.data.Message;
import com.victor.ipc.data.User;

public class MessageDao {

	private List<Message> messages = new ArrayList<Message>();

	private Map<String,ObjectOutputStream> userSocket = new HashMap<String,ObjectOutputStream>();

	private List<User> users = new ArrayList<User>();
	private MessageDao(){

	}

	public void sendUses(){
		synchronized (users) {
			for (User u : users) {
				sendAll(u);
			}
		}
	}

	public static MessageDao dao = new MessageDao();
	public void saveMessage(Message mess) {
		synchronized (messages) {

			messages.add(mess);
			messages.notifyAll();
		}
	}

	public void putUserSocket(String name, ObjectOutputStream out) {
		synchronized (userSocket) {
			userSocket.put(name, out);
		}
	}

	public void removeUserSocket(String name){
		synchronized (userSocket) {
			userSocket.remove(name);
		}
	}

	public boolean contains(User user){
		synchronized (users) {
			return users.contains(user);
		}
	}

	public void removeUser(User user) {
		synchronized (users) {
			users.remove(user);
			user.setType(9);
			sendAll(user);
		}
	}
	public void addUser(User user){
		synchronized (users) {
			users.add(user);
		}
	}

	private void sendAll(Object mess){
		synchronized (userSocket) {
			Collection<ObjectOutputStream> vs = userSocket.values();
			for (ObjectOutputStream os:vs) {
				try {
					os.writeObject(mess);
					os.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
	private void sendSingleMessage(Message mess){
		String toName = mess.getToName();
		ObjectOutputStream os = userSocket.get(toName);
		try {
			os.writeObject(mess);
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessage() {
		synchronized (messages) {
			while (messages.isEmpty()) {
				try {
					messages.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			for (Message m : messages) {
				switch (m.getType()) {
				case gongliao: sendAll(m); break;
				case siliao: sendSingleMessage(m);break;
				}
			}
			messages.clear();
		}
	}

}

