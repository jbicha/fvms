package fvms.team.ejb;

import java.util.ArrayList;
import java.util.List;

public class Team {
	
	Person teamLeader;
	List<Person> members;
	
	public Team()
	{
		members = new ArrayList<Person>();
	}
	
	public void add(Person person)
	{
		members.add(person);
	}

	public List<Person> getMembers() {
		return members;
	}

	public void setMembers(List<Person> members) {
		this.members = members;
	}

	public Person getTeamLeader() {
		return teamLeader;
	}

	public void setTeamLeader(Person teamLeader) {
		this.teamLeader = teamLeader;
	}

}
