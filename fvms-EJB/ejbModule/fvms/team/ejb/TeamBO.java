package fvms.team.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import fvms.util.ejb.ObjectManager;

@Stateless
public class TeamBO {
	
	ObjectContainer db = null;

	public void addTeamLeader(Person personIn)
	{
		
		Person person = null;;
		
		try{
			
			
		  db = ObjectManager.openDb();
		  
		  final ObjectSet<Person> result = db.queryByExample(personIn);
		  System.out.println("PersonBO.save result size="+result.size());
		  if(result.size() > 0)
		  {
			  person = result.get(0);
		  }
		  
		  Team team = new Team();
		  team.setTeamLeader(person);
		  db.store(team);
		  person.setTeam(team);
		  db.store(person);
		  
		} catch (Exception e) {
			 e.printStackTrace();
		} finally {
			ObjectManager.closeDb(db);
		}

	}
	
	public List<Team> listAll()
	{
		List<Team> result = null;
		List<Team> teamList = null;
		
		try{
			
			
		  db = ObjectManager.openDb();
		  
		  result = db.query(Team.class);
		  
		  teamList = new ArrayList<Team>(result);
		  
		} catch (Exception e) {
			 e.printStackTrace();
		} finally {
			ObjectManager.closeDb(db);
		}
		
		return teamList;

		
	}
	
	public List<Person> getTeamLeaderList(String lastNameFilter)
	{
//		System.out.println("TeamBO.getTeamLeaderList");
		
		List<Team> result = null;
		
		List<Person> teamLeaderList = new ArrayList<Person>();
		
		try{
			
			db = ObjectManager.openDb();
			  
			result = db.query(Team.class);
			
//			System.out.println("TeamBO.getTeamLeaderList result size="+result.size());
			
			for(Team team: result)
			{
				if(team.getTeamLeader() != null)
				{
					Person teamLeader = team.getTeamLeader();
					if(lastNameFilter != null)
					{
						System.out.println("name="+lastNameFilter+" name="+teamLeader.getLastName());
						if(teamLeader.getLastName().startsWith(lastNameFilter))
						{
							teamLeaderList.add(teamLeader);
						}
					}
					else
					{
						teamLeaderList.add(teamLeader);
					}
				}
			}
			
		} catch (Exception e) {
			 e.printStackTrace();
		} finally {
			ObjectManager.closeDb(db);
		}
		
		return teamLeaderList;
	}
	
	public Team addToTeam(Long teamLeaderId, Person person)
	{
		Team team = null;
		Person ldr = null;
		Person per = null;
		
		try{
			
			
			  db = ObjectManager.openDb();
			  ldr = db.ext().getByID(teamLeaderId);
			  db.activate(ldr, 2);
			  team = ldr.getTeam();
			  System.out.println(team);
 			  per = (Person) db.queryByExample(person).get(0);
			  team.add(per);
			  db.store(team);
			  per.setTeam(team);
			  db.store(per);
			  
			} catch (Exception e) {
				 e.printStackTrace();
			} finally {
				ObjectManager.closeDb(db);
			}
		return team;
	}
	
	public Long getId(Person leader)
	{
		
		Person ldr = null;
		Long id = null;
		
		try{		
			
			
		  db = ObjectManager.openDb();
		  
		  final ObjectSet<Person> result = db.queryByExample(leader);
		
		  ldr = result.get(0);
		  
		  id = db.ext().getID(ldr);
		  
		} catch (Exception e) {
			 e.printStackTrace();
		} finally {
			ObjectManager.closeDb(db);
		}
		
		return id;
	}
	
	public Person getLeaderById( Long id)
	{
		Person ldr = null;
				
		try{		
			
			
		  db = ObjectManager.openDb();
		  
		  ldr = db.ext().getByID(id);
		  
		} catch (Exception e) {
			 e.printStackTrace();
		} finally {
			ObjectManager.closeDb(db);
		}
		
		return ldr;
	}

}
