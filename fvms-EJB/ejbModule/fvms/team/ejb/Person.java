package fvms.team.ejb;

public class Person {
	String firstName;
	String lastName;
	String cellPhoneNumber;
	String email;
	String role;
	Team team;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCellPhoneNumber() {
		return cellPhoneNumber;
	}
	public void setCellPhoneNumber(String cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	
	public void update( Person in)
	{
		firstName = in.firstName;;
		lastName = in.lastName;
		cellPhoneNumber = in.cellPhoneNumber;
		email = in.email;
		role = in.role;
 		team = in.team;
	}
	
	public String getTeamLeaderName()
	{
		String name = "";
		if(team != null)
		{
			Person tl = team.getTeamLeader();
			name = tl.getFirstName()+" "+tl.getLastName();
		}
			return name;
	}
	
	public void setTeamLeaderName( String name)
	{
		// null method
	}
	
	@Override
	public String toString()
	{
		return firstName+", "+lastName;
	}

}
