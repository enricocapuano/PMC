package parallelMachines;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.cplex.IloCplex;

public class Solver {
	
	LinkedList<String> dati;
	int numJob;
	int numMac;
	LinkedList<Job> jobs;
	int nProb;
	InstanceGenerator g;
	
	public Solver() throws IloException {
		dati = new LinkedList<>();
		numJob = 0;
		numMac = 0;
		jobs = new LinkedList<>();
		g = new InstanceGenerator();
	}
	
	//lettura da file
	public void readFile() {
		BufferedReader reader;
		nProb = (int)(Math.random()*99);
		try {
			reader = new BufferedReader(new FileReader("D:\\desktop enrico\\polito\\TESI\\test\\test"+nProb+".txt"));
//			reader = new BufferedReader(new FileReader("D:\\desktop enrico\\polito\\TESI\\prova.txt"));
	        String line = reader.readLine();
	        
	        while(line!=null) {
	            //System.out.println(line);
	            dati.add(line);
	            line = reader.readLine();
	        }
		}catch(IOException e) {
			System.out.print("Eccezione IO");
		}
        
        numJob = Integer.parseInt(dati.get(0));
        numMac = Integer.parseInt(dati.get(1));
        int nJob = 0;
        for(int i = 2; i < numJob+2; i++) {
        	int p = Integer.parseInt(dati.get(i));
        	Job j = new Job(nJob, p);
        	jobs.add(j);
        	nJob++;
        }
        
        for(int i = numJob+2; i < dati.size(); i++) {
        	String string = dati.get(i);
        	String[] parts = string.split(" ");
        	String part1 = parts[0];
        	String part2 = parts[1]; 
        	int n1 = Integer.parseInt(part1);
        	int n2 = Integer.parseInt(part2);
        	Job j1 = null;
        	Job j2 = null;
        	for(Job j : jobs) {
        		if(j.getNumJob() == n1) {
        			 j1 = j;
        		}
        		if(j.getNumJob() == n2) {
        			j2 = j;
        		}
        	}
        	j1.addConflict(j2);
//        	j2.addConflict(j1);
        	
        }
        
        
	}
	
	//risoluzione
	public void solveMe() {
		
	
//		g.writeFile();
		
		
		
		
		this.readFile();
		
        IloCplex cplex;
		try {
			cplex = new IloCplex();
			//variables
			IloIntVar[][] x = new IloIntVar[numMac][];

			for(int i = 0; i < numMac; i++){
				x[i] = cplex.boolVarArray(numJob);
			}
			
			//objective
			IloLinearNumExpr y = cplex.linearNumExpr();
			
			for(int i = 0; i < numMac; i++) {
				for(Job j : jobs) {
					y.addTerm(j.getP(), x[i][j.getNumJob()]);
				}
			}
			
			
			cplex.addMinimize(y); 
			
			//constraints
			LinkedList<String> constraints = new LinkedList<>();
			for(int j = 0; j < numJob; j++) {
				IloLinearNumExpr expr = cplex.linearNumExpr();
				for(int i = 0; i < numMac; i++) {
					expr.addTerm(1.0, x[i][j]);
				}
				constraints.add(cplex.addEq(expr, 1).toString());
//				cplex.addEq(expr, 1);
			}
			
			for(int i = 0; i < numMac; i++) {
				
				for(Job j : jobs) {
				
					for(Job jj : j.getConflicts()) {
//						IloLinearIntExpr expr = cplex.linearIntExpr();
//						expr.addTerm(1.0, x[i][j]);
//						expr.addTerm(1.0, x[i][jj.numJob]);
						constraints.add(cplex.addLe(cplex.sum(x[i][j.getNumJob()], x[i][jj.getNumJob()]), 1).toString());
//						cplex.addLe(cplex.sum(x[i][j.getNumJob()], x[i][jj.getNumJob()]), 1);
					}
					
				}
				
			}
			
			for(int i = 0; i < numMac; i++) {
				IloLinearNumExpr expr = cplex.linearNumExpr();
				for(Job j : jobs) {
					expr.addTerm(j.getP(), x[i][j.getNumJob()]);
				}
				constraints.add(cplex.addLe(expr, y).toString());
//				cplex.addGe(y, expr);
			}
			
			constraints.add(cplex.addGe(y, 0).toString());
//			cplex.addGe(y, 0);
			
			System.out.println("Risolvo il problema numero: "+nProb);
			System.out.println(y);
			//solve model
			if(cplex.solve()) {
				System.out.println("Modello risolto \nf.o. = "+cplex.getObjValue());
			}else {
				System.out.println("Modello non risolto");
			}
			
			//print number of jobs and machines
			System.out.println("Numero di jobs: "+numJob);
			System.out.println("Numero di macchine: "+numMac);
			
			//print constraints
			for(String s : constraints) {
				System.out.println(s);
			}
			
			//print variables
			for(int i = 0; i < numMac; i++) {
				for(int j= 0; j < numJob; j++) {
					if(cplex.getValue(x[i][j]) > 0)
						System.out.println("x["+i+"]["+j+"] : "+cplex.getValue(x[i][j]));
				}
			}
			
			
			
			cplex.end();
		}catch(IloException e) {
			System.out.print("eccezione Ilo");
		}
		
		
	}

}
