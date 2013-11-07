package cs516.gabrielGheorghian.data;
import java.io.Serializable;

/**
 * @author 0737019
 *
 */
public class Contact implements Serializable{
	
	//variables
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String phoneNum;
	private String email;
	
	/**
	 * Default constructor for Contact
	 */
	public Contact(){
		this.id = 0;
		this.name = "";
		this.phoneNum = "";
		this.email = "";
	}
	
	/**Constructor for Contact
	 * @param id
	 * @param email
	 * @param name
	 * @param phoneNum
	 */
	public Contact(int id, String email, String name, String phoneNum){
		this.id = id;
		this.name = name;
		this.phoneNum = phoneNum;
		this.email = email;
	}

	
	/** Getter for id
	 * @return
	 */
	public int getId() {
		return id;
	}

	/** Setter for ID
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter for name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for name
	 * @return
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for phone number
	 * @return
	 */
	public String getPhoneNum() {
		return phoneNum;
	}

	/**
	 * Setter for phone number
	 * @return
	 */
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	/**
	 * Getter for email
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for email
	 * @return
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String toString() {
			String x = "";
			x += "ID = " + id;
			x += "  Name = " + name;
			x += "  Phone Number = " + phoneNum;
			x += "  Email = " + email;
			
			return x;
		};
}
