package fvms.team.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;

import fvms.common.form.MessageManager;
import fvms.team.ejb.Person;
import fvms.team.ejb.Team;
import fvms.team.ejb.TeamBO;

@Named
@SessionScoped
public class TeamListForm implements Serializable {
	
	static final long serialVersionUID = -1;
	
	List<Team> teamList;
	
	@EJB
	TeamBO teamBO;
	
	@Inject
	MessageManager messageManager;
	
	String selectedTab;
	
	public String init()
	{
		System.out.println("TeamListForm.init");
		teamList = teamBO.listAll();
		selectedTab = "teamListTab";
		messageManager.setMessageText(null);  
		
		return "/Pages/Team/TeamList.xhtml"; 

	}

	public List<Team> getTeamList() {
		return teamList;
	}

	public void setTeamList(List<Team> teamList) {
		this.teamList = teamList;
	}

	public String getSelectedTab() {
		return selectedTab;
	}

	public void setSelectedTab(String selectedTab) {
		this.selectedTab = selectedTab;
	}

}
