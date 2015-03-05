package hr.foi.cookie.types;

import java.util.Date;

public class User {
	protected int id;
	protected String username;
	protected String name;
	protected String surname;
	protected String email;
	protected Date registrationDate;
	// type - missing
	// rank - missing
	
	public User(int id, String username, String name, String surname, String email, Date registrationDate)
	{
		this.id = id;
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.registrationDate = registrationDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
}
