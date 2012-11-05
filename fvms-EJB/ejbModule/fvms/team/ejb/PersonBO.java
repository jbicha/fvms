package fvms.team.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import fvms.util.ejb.ObjectManager;

@Stateless
public class PersonBO implements Serializable{

	private static final long serialVersionUID = 1881413500711441951L;
	
//	@EJB
//	ObjectManager objectManager;
	
	ObjectContainer db = null;

	/**
	 * 
	 * @param personIn
	 * 	personIn and origPerson are referring to the data for the same person. personIn
	 * 	is the data after it has been edited in the web browser and has been sent back to
	 *  the server and is now being passed into this method.
	 * @param origPerson
	 * 	This is the original data before it was updated in the browser. This is needed in
	 * 	order to do a query to retrieve this persons data from the database so it can be
	 * 	updated from the data returned from the browser.
	 * @return
	 */
	public Person save(Person personIn, Person origPerson  ) throws Exception
	{
		Person person = personIn;

		try {
		  db = ObjectManager.openDb();
		  
		  if(origPerson != null)
		  {
			  final ObjectSet<Person> result = db.queryByExample(origPerson);
			  System.out.println("PersonBO.save result size="+result.size());
			  if(result.size() > 0)
			  {
				  person = result.get(0);
				  person.update(personIn);
			  }
		  }
		  else
		  {
			  Person queryPerson = new Person();
			  queryPerson.setLastName(personIn.getFirstName());
			  queryPerson.setLastName(personIn.getLastName());
			  queryPerson.setCellPhoneNumber(personIn.getCellPhoneNumber());
			  
			  final ObjectSet<Person> result = db.queryByExample(queryPerson);
			  System.out.println("PersonBO.save result size="+result.size());
			  if(result.size() > 0)
			  {
				  throw new Exception("Duplicate Person");
			  }

		  }

		  db.store(person);
		  
		} finally {
			ObjectManager.closeDb(db);
		}
		
		return person;
	}

	public List<Person> listAll()
	{
		System.out.println("PersonBO.listAll");
		List<Person> result = null;
		List<Person> teamLeaders = null;
		
		try{
			
		  db = ObjectManager.openDb();
		  
		  result = db.query(Person.class);
		  
		  teamLeaders = new ArrayList<Person>();
		  
		  for( Person per: result)
		  {
			  teamLeaders.add(per);
		  }
		  
		} catch (Exception e) {
			 e.printStackTrace();
		} finally {
			ObjectManager.closeDb(db);
		}
		
		return teamLeaders;

	}
	
	public Person find(Person per)
	{		
		Person person = null;
		
		try{		
			
		  db = ObjectManager.openDb();
		  
		  final ObjectSet<Person> result = db.queryByExample(per);
		
		  person = result.get(0);
		  
		} catch (Exception e) {
			 e.printStackTrace();
		} finally {
			ObjectManager.closeDb(db);
		}
		
		System.out.println("PersonBO.find found name = "+person.getFirstName());

		return person;	
	}


}
