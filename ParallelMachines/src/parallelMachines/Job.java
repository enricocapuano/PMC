package parallelMachines;

import java.util.LinkedList;

public class Job {
	
	int numJob;
	int p;
	LinkedList<Job> conflicts;
	public Job(int numJob, int p) {
		super();
		this.numJob = numJob;
		this.p = p;
		this.conflicts = new LinkedList<>();
	}
	public int getP() {
		return p;
	}
	public void addConflict(Job j) {
		conflicts.add(j);
	}
	
	
	public int getNumJob() {
		return numJob;
	}
	public LinkedList<Job> getConflicts() {
		return conflicts;
	}
	@Override
	public String toString() {
		return "Job [p=" + p + "]";
	}
	
	

}
