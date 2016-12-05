import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class serverOpen extends JFrame implements ActionListener, ItemListener {

	TextArea textarea; 
	List list;
	Panel buttons;
	TextField details;
	Button up, download,reconnect,home;
	String username,servername,password;
	String SFTPWORKINGDIR="";
	String WORKINGDIR;
	int port;
	String[] files;
	File currentDir;
	Vector filelist ;
	ChannelSftp sftpChannel ;
	
	
	 JSch jsch = new JSch();
	 Session session = null;
	public serverOpen(String username,String servername,int port,String password,String SFTPWORKINGDIR) {
		// TODO Auto-generated constructor stub
		try{
		this.username=username;
		this.password=password;
		this.servername=servername;
		this.port=port;
		this.SFTPWORKINGDIR=SFTPWORKINGDIR;
		 WORKINGDIR=SFTPWORKINGDIR;
		session = jsch.getSession(username, servername, port);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(password);
        session.connect();

        Channel channel = session.openChannel("sftp");
        channel.connect();
       sftpChannel = (ChannelSftp) channel;
        
        
        
		
	    list = new List(12, false); // Set up the list
	    list.setFont(new Font("MonoSpaced", Font.PLAIN, 14));
	    list.addActionListener(this);
	    list.addItemListener(this);
	    

	    details = new TextField(); // Set up the details area
	    details.setFont(new Font("MonoSpaced", Font.PLAIN, 12));
	    details.setEditable(false);
	    JPanel jp=new JPanel();
	    jp.setLayout(new BorderLayout());
	    
	    buttons = new Panel(); // Set up the button box
	    buttons.setLayout(new BorderLayout());
	    buttons.setFont(new Font("SansSerif", Font.BOLD, 14));

	    up = new Button("Upload"); // Set up the two buttons
	    download = new Button("Download");
	    reconnect=new Button("Reconnect");
	    home=new Button("home");
	    up.addActionListener(this);
	    download.addActionListener(this);
	    buttons.add(home);
	    buttons.add(up); // Add buttons to button box
	    buttons.add(download);
	    buttons.add(reconnect);
		    

	    JToolBar jt=new JToolBar();
	    jt.add(home);
	    jt.add(reconnect);
	    jt.addSeparator();
	    jt.add(up);
	    jt.add(download);
	    
	   

	   // jp.add(buttons,"Center");
	    jp.add(details,"North");
	    jp.add(jt,"South");
	    
	    
	    
	    
	   
	    
	    sftpChannel.cd(SFTPWORKINGDIR);
         filelist = sftpChannel.ls(SFTPWORKINGDIR);
        System.out.println("entry.getFilename()");
        for(int i=0; i<filelist.size();i++){
            LsEntry entry = (LsEntry) filelist.get(i);
            System.out.println(entry.getFilename());
            list.add(entry.getFilename()); 
        }
	    
    	System.out.println(SFTPWORKINGDIR);
	    this.add(list,"Center"); // Add stuff to the window
	   // this.add(jt);
	 //   this.add(details,"North");
	    this.add(jp,"North");
	    this.setSize(500, 350);
	    this.setVisible(true);
	    
	    
	    
		}catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
		    
	}
	  public void listDirectory() {
		    // Convert the string to a File object, and check that the dir exists
		    File dir = new File(SFTPWORKINGDIR);
		    if (!dir.isDirectory())
		      throw new IllegalArgumentException("FileLister: no such directory");

		    // Get the (filtered) directory entries
		    files = dir.list();

		    // Sort the list of filenames.
		    java.util.Arrays.sort(files);

		    // Remove any old entries in the list, and add the new ones
		    list.removeAll();
		    list.add("[Up to Parent Directory]"); // A special case entry
		    for (int i = 0; i < files.length; i++)
		      list.add(files[i]);

		    // Display directory name in window titlebar and in the details box
		    this.setTitle(SFTPWORKINGDIR);
		    details.setText(SFTPWORKINGDIR);

		    // Remember this directory for later.
		    currentDir = dir;
		  }
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == list) { // Double click on an item
		      int i = list.getSelectedIndex(); // Check which item
		      if (i == 0)
		      {}
		      else { // Otherwise, get filename
		          try
		          {
		        	  LsEntry entry1 = (LsEntry) filelist.get(i);
		          
		            SftpATTRS attr = entry1.getAttrs();
		            long length = attr.getSize();
		            boolean isDir = attr.isDir();
		            boolean isLink = attr.isLink();
		       
		        if (isDir){
		        
		        	SFTPWORKINGDIR=SFTPWORKINGDIR+"/"+entry1.getFilename();
		        	System.out.println(SFTPWORKINGDIR);
		        	
		        	 sftpChannel.cd(SFTPWORKINGDIR);
		        	 details.setText(sftpChannel.pwd());
		        	 filelist = sftpChannel.ls(SFTPWORKINGDIR);
		             System.out.println("entry.getFilename()");
		             for(int j=0; j<filelist.size();j++){
		                 LsEntry entry = (LsEntry) filelist.get(j);
		                 System.out.println(entry.getFilename());
		                 //list.add(entry.getFilename()); 
		             }
		        	 
		        	
		        	list.removeAll();
		        	
		        	sftpChannel.cd(SFTPWORKINGDIR);
		            filelist = sftpChannel.ls(SFTPWORKINGDIR);
		           System.out.println("entry.getFilename()");
		           for(int k=0; k<filelist.size();k++){
		               LsEntry entry2 = (LsEntry) filelist.get(k);
		               System.out.println(entry2.getFilename());
		               list.add(entry2.getFilename()); 
		           }
		        	
		        }
		          }catch(Exception e1)
		          {
		        	  System.out.println(e1);
		        	  SFTPWORKINGDIR="/home";
		        	  
		          }
	}

}
	}
}
