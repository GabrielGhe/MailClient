package cs516.gabrielGheorghian.data;

import java.io.Serializable;

/**
 * @author 0737019
 *
 */
public class Folder implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;

	/**
	 * default constructor
	 */
	public Folder(){
		this.id = 0;
		this.name = null;
	}
	
	
	/** Constructor with 2 parameters
	 * @param id
	 * @param name
	 * @return
	 */
	public Folder(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	/**
	 * @return
	 */
	public int getId() {
		return id;
	}

	
	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}	
	
	/**
	 * toString
	 */
	public String toString(){
		return name;
	}
}
