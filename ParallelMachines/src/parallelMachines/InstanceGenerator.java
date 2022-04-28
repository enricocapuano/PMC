package parallelMachines;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

public class InstanceGenerator {
	
	int numJob;
	int numMac;
	LinkedList<Integer> pTimes;
	LinkedList<String> conflicts;
	boolean file;
	
	public InstanceGenerator() {
		numJob = 0;
		numMac = 0;
		pTimes = new LinkedList<>();
		conflicts = new LinkedList<>();
		file = false;
	}
	
	public void generator() {
		
		
		while(numMac >= numJob) {
			//numJob generation
			double j = Math.random();
				
			if(j <= 0.2) {
				numJob = 10;
			}	
			else if(j <= 0.4 && j > 0.2) {
				numJob = 25;
			}
			else if(j <= 0.6 && j > 0.4) {
				numJob = 50;
			}
			else if(j <= 0.8 && j > 0.6) {
				numJob = 75;
			}
			else {
				numJob = 100;
			}
			
			//numMac generation
			double m = Math.random();
			
			if(m <= 0.25) {
				numMac = 5;
			}	
			else if(m <= 0.5 && m > 0.25) {
				numMac = 10;
			}
			else if(m <= 0.75 && m > 0.5) {
				numMac = 15;
			}
			else {
				numMac = 20;
			}
		}
		
//		System.out.println(numJob);
//		System.out.println(numMac);
		
		//processing times generation
		double r = Math.random();
		for(int i = 0; i < numJob; i++) {
			int pTime = 0;
			if(r <= 0.33) {
				pTime = (int) (Math.random()*10);
			}	
			else if(r <= 0.66 && r > 0.33) {
				pTime = (int) (Math.random()*50);
			}
			else {
				pTime = (int) (Math.random()*100);
			}
			
			if(pTime == 0)
				pTime = pTime+1;
			
			pTimes.add(pTime);
			
		}
		Collections.sort(pTimes);
//		for(Integer p : pTimes) {
//			System.out.println(p);
//		}
		
		//conflicts generation
		for(Integer p1 : pTimes) {
			for(Integer p2: pTimes) {
				double choice = Math.random(); //double per scegliere con quale probabilità inserire il conflitto
				double prob = Math.random();
				if(pTimes.indexOf(p1) != pTimes.indexOf(p2)) {
					if(choice <= 0.2) {
						if(prob < 0.1) {
							if(!conflicts.contains(pTimes.indexOf(p1)+" "+pTimes.indexOf(p2)) && !conflicts.contains(pTimes.indexOf(p2)+" "+pTimes.indexOf(p1))) //non inserisco due conflitti uguali (?)
								conflicts.add(pTimes.indexOf(p1)+" "+pTimes.indexOf(p2));
						}
					}	
					else if(choice <= 0.4 && choice > 0.2) {
						if(prob < 0.2) {
							if(!conflicts.contains(pTimes.indexOf(p1)+" "+pTimes.indexOf(p2)) && !conflicts.contains(pTimes.indexOf(p2)+" "+pTimes.indexOf(p1))) //non inserisco due conflitti uguali (?)
								conflicts.add(pTimes.indexOf(p1)+" "+pTimes.indexOf(p2));
						}
					}
					else if(choice <= 0.6 && choice > 0.4) {
						if(prob < 0.3) {
							if(!conflicts.contains(pTimes.indexOf(p1)+" "+pTimes.indexOf(p2)) && !conflicts.contains(pTimes.indexOf(p2)+" "+pTimes.indexOf(p1))) //non inserisco due conflitti uguali (?)
								conflicts.add(pTimes.indexOf(p1)+" "+pTimes.indexOf(p2));
						}
					}
					else if(choice <= 0.8 && choice > 0.6) {
						if(prob < 0.4) {
							if(!conflicts.contains(pTimes.indexOf(p1)+" "+pTimes.indexOf(p2)) && !conflicts.contains(pTimes.indexOf(p2)+" "+pTimes.indexOf(p1))) //non inserisco due conflitti uguali (?)
								conflicts.add(pTimes.indexOf(p1)+" "+pTimes.indexOf(p2));
						}
					}
					else {
						if(prob < 0.5) {
							if(!conflicts.contains(pTimes.indexOf(p1)+" "+pTimes.indexOf(p2)) && !conflicts.contains(pTimes.indexOf(p2)+" "+pTimes.indexOf(p1))) //non inserisco due conflitti uguali (?)
								conflicts.add(pTimes.indexOf(p1)+" "+pTimes.indexOf(p2));
						}
					}
				}
			}
		}
		
		
//		for(String c : conflicts) {
//			System.out.println(c);
//		}
		
	}
	
	public void writeFile() {
		
		for(int i = 0; i < 100; i++) {
			generator();
			FileWriter w;
			try {
				w = new FileWriter("D:\\desktop enrico\\polito\\TESI\\test\\test"+i+".txt");//se lanci il metodo più volte sovrascrive i file nella cartella ogni volta
				w.write(numJob+"\n");
				w.write(numMac+"\n");
				for(Integer p : pTimes) {
					w.write(p+"\n");
				}
				for(String c : conflicts) {
					w.write(c+"\n");
				}
				w.flush();
				numJob = 0;
				numMac = 0;
				pTimes = new LinkedList<>();
				conflicts = new LinkedList<>();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		file = true;
	}

	public boolean isFile() {
		return file;
	}

	
	
	
	

}
