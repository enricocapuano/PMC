package parallelMachines;

import ilog.concert.IloException;

public class Main {

	public static void main(String[] args) throws IloException {
		

		
//		InstanceGenerator g = new InstanceGenerator();
//		g.writeFile();
		
		Solver s = new Solver();
		s.solveMe();
	}
	
	
	
}
