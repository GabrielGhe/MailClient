package cs516.gabrielGheorghian.data;

import java.io.Serializable;

/**
 * @author 0737019 Based on neon's MailConfig
 */
/**
 * @author 0737019
 *
 */
public class MailConfig implements Serializable {

	//variables for user
	private static final long serialVersionUID = 2L;
	private String userName;
	private String userEmail;
	private String urlPop3;
	private String urlSmtp;
	
	//user and pass'
	private String userNamePop3;
	private String userPassPop3;
	private String urlMySql;
	private String userMySql;
	private String passMySql;
	
	//ports
	private int portPop3;
	private int portSmtp;
	private int portMySql;

	private boolean isGmail;
	private boolean isSMTPAuth;

	/**
	 * Default Constructor
	 */
	public MailConfig() {
		super();
		userName = "";
		userEmail = "";
		urlPop3 = "";
		urlSmtp = "";
		userNamePop3 = "";
		userPassPop3 = "";
		urlMySql = "";
		userMySql = "";
		passMySql = "";
		portPop3 = 110;
		portSmtp = 25;
		portMySql = 3306;
		isGmail = false;
	}

	/** 12 parameter constructor for MailConfig
	 * Constructor for MailConfig
	 * @param userName
	 * @param userEmail
	 * @param urlPop3
	 * @param urlSmtp
	 * @param userNamePassPop
	 * @param urlMySql
	 * @param userMySql
	 * @param passMySql
	 * @param portPop3
	 * @param portSmtp
	 * @param portMySql
	 * @param isGmail
	 */
	public MailConfig(String userName, String userEmail, String urlPop3,
			String urlSmtp, String userNamePop3, String userPassPop3, String urlMySql,
			String userMySql, String passMySql, int portPop3, int portSmtp,
			int portMySql, boolean isGmail) {
		super();
		this.userName = userName;
		this.userEmail = userEmail;
		this.urlPop3 = urlPop3;
		this.urlSmtp = urlSmtp;
		this.userNamePop3 = userNamePop3;
		this.userPassPop3 = userPassPop3;
		this.urlMySql = urlMySql;
		this.userMySql = userMySql;
		this.passMySql = passMySql;
		this.portPop3 = portPop3;
		this.portSmtp = portSmtp;
		this.portMySql = portMySql;
		this.isGmail = isGmail;
	}
	
	/**
	 * getter for user name for POP3
	 * @return
	 */
	public String getUserNamePop3() {
		return userNamePop3;
	}

	/**
	 *  setter for user name for POP3
	 * @param userNamePop
	 */
	public void setUserNamePop3(String userNamePop3) {
		this.userNamePop3 = userNamePop3;
	}

	/**
	 * getter for pass for POP3
	 * @return
	 */
	public String getUserPassPop3() {
		return userPassPop3;
	}

	
	/** getter for isSMTP
	 * @return
	 */
	public boolean isSMTPAuth() {
		return isSMTPAuth;
	}

	
	/**setter for isSMTP
	 * @param isSMTPAuth
	 */
	public void setSMTPAuth(boolean isSMTPAuth) {
		this.isSMTPAuth = isSMTPAuth;
	}
	
	/**
	 * getter for user password for POP3
	 * @param userPassPop
	 */
	public void setUserPassPop3(String userPassPop3) {
		this.userPassPop3 = userPassPop3;
	}

	/**
	 * getter for userName
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/** 
	 * setter for userName
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * getter for userEmail
	 * @return
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * setter for userEmail
	 * @param userEmail
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * getter for url for POP3
	 * @return
	 */
	public String getUrlPop3() {
		return urlPop3;
	}

	/**
	 * setter for url for POP3
	 * @param urlPop3
	 */
	public void setUrlPop3(String urlPop3) {
		this.urlPop3 = urlPop3;
	}

	/**
	 * getter for url for STMP
	 * @return
	 */
	public String getUrlSmtp() {
		return urlSmtp;
	}

	/**
	 * setter for url for STMP
	 * @param urlSmtp
	 */
	public void setUrlSmtp(String urlSmtp) {
		this.urlSmtp = urlSmtp;
	}

	/**
	 * getter for url for MySql
	 * @return
	 */
	public String getUrlMySql() {
		return urlMySql;
	}

	/**
	 * setter for url for MySql
	 * @param urlMySql
	 */
	public void setUrlMySql(String urlMySql) {
		this.urlMySql = urlMySql;
	}

	/**
	 * getter for user for MySql
	 * @return
	 */
	public String getUserMySql() {
		return userMySql;
	}

	/**
	 * setter for user for MySql
	 * @param userMySql
	 */
	public void setUserMySql(String userMySql) {
		this.userMySql = userMySql;
	}

	/**
	 * getter for password for MySql
	 * @return
	 */
	public String getPassMySql() {
		return passMySql;
	}

	/**
	 * setter for pass for MySql
	 * @param passMySql
	 */
	public void setPassMySql(String passMySql) {
		this.passMySql = passMySql;
	}

	/**
	 * getter for port for POP3
	 * @return
	 */
	public int getPortPop3() {
		return portPop3;
	}

	/**
	 * setter for port for POP3
	 * @param portPop3
	 */
	public void setPortPop3(int portPop3) {
		this.portPop3 = portPop3;
	}

	/**
	 * getter for port for STMP
	 * @return
	 */
	public int getPortSmtp() {
		return portSmtp;
	}

	/**
	 * setter for port for STMP
	 * @param portSmtp
	 */
	public void setPortSmtp(int portSmtp) {
		this.portSmtp = portSmtp;
	}

	/**
	 * getter for port for MySql
	 * @return
	 */
	public int getPortMySql() {
		return portMySql;
	}

	/**
	 * setter for port for MySql
	 * @param portMySql
	 */
	public void setPortMySql(int portMySql) {
		this.portMySql = portMySql;
	}

	/**
	 * getter for if it's a gmail account or not 
	 * @return
	 */
	public boolean isGmail() {
		return isGmail;
	}

	/**
	 * setter for if it's a gmail account or not 
	 * @param isGmail
	 */
	public void setIsGmail(boolean isGmail) {
		this.isGmail = isGmail;
	}

}
