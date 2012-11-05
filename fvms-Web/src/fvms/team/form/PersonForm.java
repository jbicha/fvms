package fvms.team.form;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import fvms.common.form.Constants;
import fvms.common.form.MessageManager;
import fvms.team.ejb.Person;
import fvms.team.ejb.PersonBO;
import fvms.team.ejb.Team;
import fvms.team.ejb.TeamBO;

@Named
@SessionScoped
public class PersonForm  implements Serializable {
	
	static final long serialVersionUID = -1;
	
    private static final ResourceBundle RESOURCE_BUNDLE =
    		ResourceBundle.getBundle(Constants.constMESSAGES, Locale.getDefault());
	
	Person person;
	Person updatePerson;
	Long selectedTeamLeaderId;
	String teamLeaderFirstName;
	String teamLeaderLastName;
	
	@EJB
	private PersonBO personBO;
	
	@EJB
	private TeamBO teamBO;
	
	@Inject
	private MessageManager messageManager;
	
	String selectedTab;
	
	/**
	 * Called directly from menu bar menu item 
	 */	
	public String init()
	{
		System.out.println("PersonForm.init");
		person = new Person();
		updatePerson = null;
		selectedTab = "personTab";
		messageManager.setMessageText(null);
		
		return "/Pages/Team/Person.xhtml"; 
	}
	
	/**
	 * Get all person objects as List
	 * @return
	 */
	public List<Person> getPersonList()
	{
		System.out.println("PersonForm.getPersonList");
		selectedTab = "personListTab";
		return personBO.listAll();
	}
	
	HashMap<String, Long> teamLeaderMap;
	
	public Map<String, Long> getTeamLeaderMap()
	{
		System.out.println("PersonForm.getTeamLeaderMap");
		
		createTeamLeaderMap(null);
		
		return teamLeaderMap;
	}
	
	public void changeTeamLeaderList( AjaxBehaviorEvent evt)
	{
		System.out.println("PersonForm.changeTeamLeaderList");
		teamLeaderMap = null;
		createTeamLeaderMap(teamLeaderLastName);
	}
	
	public void createTeamLeaderMap(String lastNameFilter)
	{
		
		if(teamLeaderMap == null)
		{
			teamLeaderMap = new HashMap<String, Long>();
			
			List<Person> teamLeaderList = teamBO.getTeamLeaderList(lastNameFilter);
			
			for(Person ldr: teamLeaderList)
			{
				if(ldr != null)
				{
					String label = new String(ldr.getFirstName()+" "+ldr.getLastName());
					Long id = teamBO.getId(ldr);
					teamLeaderMap.put(label, id);
				}
			}
			
			Collection<Long> values = teamLeaderMap.values();
			Iterator<Long> iter = values.iterator();
			if(iter.hasNext())
			{
				selectedTeamLeaderId = iter.next();
			}
		}
	}
	
	public void addToTeam()
	{
		System.out.println("addToTeam id="+selectedTeamLeaderId);
		Team curTeam = teamBO.addToTeam(selectedTeamLeaderId, person);
		person = personBO.find(person);
		
	}
	
	public String updateLink(Person personIn)
	{
		System.out.println("PersonForm.updateLink name="+person.getFirstName());
		updatePerson = personBO.find(personIn);
		person = personBO.find(personIn);
		selectedTab = "personTab";		
		return "/Pages/Team/Person.xhtml";
	}
	
	
	public String addLink()
	{
		person = new Person();
		updatePerson = null;
		selectedTab = "personTab";
		return "/Pages/Team/Person.xhtml";
	}

	public void submit()
	{
		System.out.println("PersonForm.submit - "+person.getFirstName());
		
		
		try {
			personBO.save(person, updatePerson);
			
			if(person.getRole().equals("teamLeader") && (updatePerson == null || !updatePerson.getRole().equals("teamLeader")))
			{
				teamBO.addTeamLeader(person);
				teamLeaderMap = null;
			}
			
			messageManager.setMessageText(RESOURCE_BUNDLE.getString("save.success"));
		
		} catch (Exception ex) {
			System.out.println("PersonForm.submit exception message="+ex.getMessage());
			if(ex.getMessage().equals("Duplicate Person"))
			{
				messageManager.setMessageText("This Person already exists in the Database");
			}
			else
			{
				messageManager.setMessageText(RESOURCE_BUNDLE.getString("save.error"));
			}
			
			ex.printStackTrace();
		}

	}
	
	public void clear()
	{
		person = new Person();
	}
	
	public Boolean renderPersonTab()
	{
		Boolean rend = false;
		if(selectedTab.equals("personTab"))
		{
			rend = true;
		}
		System.out.println("renderPersonTab="+rend);
		
		return rend;
	}
	
	public String getTeamLeaderName()
	{
		String name = "";
		if(person.getTeam() != null)
		{
			Person tl = person.getTeam().getTeamLeader();
			name = tl.getFirstName()+" "+tl.getLastName();
		}
			return name;
	}
	
	public void setTeamLeaderName( String name)
	{
		// null method
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getSelectedTab() {
		return selectedTab;
	}

	public void setSelectedTab(String selectedTab) {
		System.out.println("PersonForm.setSelectedTab="+selectedTab);
		this.selectedTab = selectedTab;
		messageManager.setMessageText(null);
		
		String page = null;
		
		if(selectedTab.equals("personListTab"))
		{
			System.out.println("changeTab PersonList");
			page = "/Pages/Team/PersonList.xhtml";
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalCtx = facesContext.getExternalContext();
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		    String requestServer = request.getServerName();


			System.out.println("redirect=http://"+requestServer+":8080/fvms"+page);
			try {
				externalCtx.redirect( "http://"+requestServer+":8080/fvms"+page);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 		
		}
		else if(selectedTab.equals("personTab"))
		{
			System.out.println("changeTab Person");
		}
		else
		{
			System.out.println("changeTab unkown");
		}

	}

	public String getTeamLeaderFirstName() {
		return teamLeaderFirstName;
	}

	public void setTeamLeaderFirstName(String teamLeaderFirstName) {
		this.teamLeaderFirstName = teamLeaderFirstName;
	}

	public String getTeamLeaderLastName() {
		return teamLeaderLastName;
	}

	public void setTeamLeaderLastName(String teamLeaderLastName) {
		this.teamLeaderLastName = teamLeaderLastName;
	}

	public Long getSelectedTeamLeaderId() {
		return selectedTeamLeaderId;
	}

	public void setSelectedTeamLeaderId(Long selectedTeamLeaderId) {
		this.selectedTeamLeaderId = selectedTeamLeaderId;
	}

}
