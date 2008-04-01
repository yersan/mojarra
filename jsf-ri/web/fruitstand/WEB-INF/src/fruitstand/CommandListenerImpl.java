/*
 * $Id: CommandListenerImpl.java,v 1.2 2002/01/10 22:20:14 edburns Exp $
 *
 * Copyright 2000-2001 by Sun Microsystems, Inc.,
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Sun Microsystems, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Sun.
 */

// CommandListenerImpl.java

package fruitstand;

import org.mozilla.util.Assert;
import org.mozilla.util.Debug;
import org.mozilla.util.ParameterCheck;

import javax.faces.CommandListener;
import javax.faces.CommandEvent;
import javax.faces.ObjectManager;
import javax.faces.CommandFailedException;
import javax.faces.AbstractCommand;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.io.OptionalDataException;

/**
 *
 *  <B>CommandListenerImpl</B> is a class ...
 *
 * <B>Lifetime And Scope</B> <P>
 *
 * @version $Id: CommandListenerImpl.java,v 1.2 2002/01/10 22:20:14 edburns Exp $
 * 
 * @see	Blah
 * @see	Bloo
 *
 */

public class CommandListenerImpl extends AbstractCommand implements CommandListener
{
//
// Protected Constants
//

//
// Class Variables
//


private static final String USER_EXT = "user";
private static final String USER_FILE = "default." + USER_EXT;

//
// Instance Variables
//

// Attribute Instance Variables

// Relationship Instance Variables

private ServletContext servletContext;

//
// Constructors and Initializers    
//

public CommandListenerImpl()
{
}

public CommandListenerImpl(String completion, String error)
{
    super(completion, error);
}


//
// Class methods
//

//
// General Methods
//

private String getAbsPathForUserName(UserBean user) {
    String result = null;
    int i;

    // build the path for this user
    result = servletContext.getRealPath(USER_FILE);
    i = result.lastIndexOf(File.separator);
    result = result.substring(0, i) + File.separator + 
	user.getUserName() + "." + USER_EXT;
    return result;
}

/**

* write a file whose name is the value of this beans userName property
* and whose contents are the Serialized bean of this user.

*/



private void serializeBean(UserBean user) {
    ParameterCheck.nonNull(user);

    String absFileName = getAbsPathForUserName(user);
    FileOutputStream fos = null;
    ObjectOutputStream oos = null;

    try {
	fos = new FileOutputStream(absFileName);
	oos = new ObjectOutputStream(fos);
	oos.writeObject(user);
	oos.close();
    }
    catch (InvalidClassException e) {
	servletContext.log("Can't write file " + absFileName, e);
    }
    catch (NotSerializableException e) {
	servletContext.log("Can't write file " + absFileName, e);
    }
    catch (IOException e) {
	servletContext.log("Can't write file " + absFileName, e);
    }
}

/**

* Take the argument userBean and de-serialize the user, then call copy
* on the user from the objectManager.

*/

private void validateLogin(UserBean user) throws CommandFailedException {
    String absFileName = getAbsPathForUserName(user);
    FileInputStream fos = null;
    ObjectInputStream oos = null;
    UserBean userFromFile = null;
    
    try {
	fos = new FileInputStream(absFileName);
	oos = new ObjectInputStream(fos);
	userFromFile = (UserBean) oos.readObject();
	oos.close();
    }
    catch (ClassNotFoundException e) {
	servletContext.log("Can't read file " + absFileName, e);
    }
    catch (InvalidClassException e) {
	servletContext.log("Can't read file " + absFileName, e);
    }
    catch (StreamCorruptedException e) {
	servletContext.log("Can't read file " + absFileName, e);
    }
    catch (OptionalDataException e) {
	servletContext.log("Can't read file " + absFileName, e);
    }
    catch (IOException e) {
	servletContext.log("Can't read file " + absFileName, e);
    }

    if (null == userFromFile) {
	throw new CommandFailedException("Can't read UserBean from file: " + 
					 absFileName);
    }
    user.copyFrom(userFromFile);
}

//
// Methods from CommandListener
//

public void doCommand(CommandEvent e)  throws CommandFailedException
{
    String sourceName = e.getSourceName();
    ObjectManager ot = ObjectManager.getInstance();
    HttpServletRequest req = (HttpServletRequest) e.getRequest();
    UserBean user = (UserBean) ot.get(req, "UserBean");
    servletContext = req.getSession().getServletContext();

    Assert.assert_it(null != sourceName);
    Assert.assert_it(null != ot);
    Assert.assert_it(null != req);
    Assert.assert_it(null != user);

    if (sourceName.equals("createAccount")) {
	// serialize the user bean
	synchronized(servletContext) {
	    serializeBean(user);
	}
    }
    else if (sourceName.equals("Login")) {
	synchronized(servletContext) {
	    validateLogin(user);
	}
    }
	
}




} // end of class CommandListenerImpl
