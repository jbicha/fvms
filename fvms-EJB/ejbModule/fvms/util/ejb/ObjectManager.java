package fvms.util.ejb;

import javax.annotation.PreDestroy;

import javax.ejb.Stateless;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

@Stateless
public class ObjectManager {

private ObjectContainer db = null;

static public ObjectContainer openDb()
{	
	  String home = System.getProperty("user.home");
	  ObjectContainer db = Db4oEmbedded.openFile(home+"/fvms.db4o");
	  
	  return db;
}

static public void closeDb(ObjectContainer db)
{
	db.close();
}

//public ObjectManager()
//{
//	System.out.println("Opening fvms database");
//	if(db == null)
//	{
//		db = Db4oEmbedded.openFile("fvms");
//	}
//}

//@PreDestroy
//public void close()
//{
//	db.close();
//}
//
//public ObjectContainer getDb() {
//	System.out.println("Opening fvms database");
//	if(db == null)
//	{
//		db = Db4oEmbedded.openFile("fvms");
//	}
//	return db;
//}
//
//public void setDb(ObjectContainer db) {
//	this.db = db;
//}

}
