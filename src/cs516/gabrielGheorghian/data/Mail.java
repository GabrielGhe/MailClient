package cs516.gabrielGheorghian.data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author 0737019
 *
 */
public class Mail implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String sender;
	private ArrayList<String> toRecipients;
	private ArrayList<String> ccRecipients;
	private ArrayList<String> bccRecipients;
	private String subject;
	private String content;
	private Date messageDate;
	private int folder;
	
	
	/**
	 * Default Constructor
	 */
	public Mail() {
		super();
		this.toRecipients = null;
		this.ccRecipients = null;
		this.bccRecipients = null;
		this.subject = "";
		this.content = "";
		this.messageDate = new Date();
		this.folder = 0;
	}
	
	
	/** 9 parameter constructor for Mail
	 * @param toRecipients
	 * @param ccRecipients
	 * @param bccRecipients
	 * @param subjectLine
	 * @param content
	 */
	public Mail(int id, String sender, ArrayList<String> toRecipients,
			ArrayList<String> ccRecipients, ArrayList<String> bccRecipients,
			String subject, String content, Date messageDate, int folder) {
		super();
		this.setId(id);
		this.sender = sender;
		this.toRecipients = toRecipients;
		this.ccRecipients = ccRecipients;
		this.bccRecipients = bccRecipients;
		this.subject = subject;
		this.content = content;
		this.messageDate = messageDate;
		this.folder = folder;
	}
	
	
	/** getter for folderid
	 * @return
	 */
	public int getFolder() {
		return folder;
	}


	/** setter for folderid
	 * @param folder
	 */
	public void setFolder(int folder) {
		this.folder = folder;
	}


	/** getter for sender
	 * @return
	 */
	public String getSender() {
		return sender;
	}


	/**setter for sender
	 * @param sender
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}


	/** getter for recipients
	 * @return
	 */
	public ArrayList<String> getToRecipients() {
		return toRecipients;
	}


	/**setter for recipients
	 * @param toRecipients
	 */
	public void setToRecipients(ArrayList<String> toRecipients) {
		this.toRecipients = toRecipients;
	}


	/**getter for cc recipients
	 * @return
	 */
	public ArrayList<String> getCcRecipients() {
		return ccRecipients;
	}


	/** setter for cc recipients
	 * @param ccRecipients
	 */
	public void setCcRecipients(ArrayList<String> ccRecipients) {
		this.ccRecipients = ccRecipients;
	}


	/**getter for Bcc recipients
	 * @return
	 */
	public ArrayList<String> getBccRecipients() {
		return bccRecipients;
	}


	/**setter for bcc recipients
	 * @param bccRecipients
	 */
	public void setBccRecipients(ArrayList<String> bccRecipients) {
		this.bccRecipients = bccRecipients;
	}


	/**get subject
	 * @return
	 */
	public String getSubject() {
		return subject;
	}


	/**set subject
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}


	/**get content
	 * @return
	 */
	public String getContent() {
		return content;
	}


	/**set content
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}


	/**get message date
	 * @return
	 */
	public String getMessageDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(messageDate);
	}


	/**set message date
	 * @param messageDate
	 */
	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}


	/**get id
	 * @return
	 */
	public int getId() {
		return id;
	}


	/**set id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	public String toString(){
		String x = "";
		x += "ID = " + id;
		x += "  Sender = " + sender;
		x += "  ToRecipients = " + toRecipients;
		x += "  CcRecipients = " + ccRecipients;
		x += "  BccRecipients = " + bccRecipients;
		x += "  Subject = " + subject;
		x += "  Content = " + content;
		x += "  MessageDate = " + messageDate;
		x += "  Folder = " + folder;
		
		return x;
	}

}
