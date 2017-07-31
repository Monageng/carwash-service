package kbm.co.za.mobile.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Client") 
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;

	public Client() {
		
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getCellNumber() {
		return cellNumber;
	}
	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	//	@NotNull @XmlAttribute(required=false) 
	private String name;
	
//	@NotNull @XmlAttribute(required=false)
	private String surname;
	
//	@NotNull @XmlAttribute(required=false)
	private String regNo;
	
//	@NotNull @XmlAttribute(required=false)
	private String cellNumber; 
	
	private String dateOfBirth;
}
