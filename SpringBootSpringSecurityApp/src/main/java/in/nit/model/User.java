package in.nit.model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.Data;
@Entity
@Table(name="user_tab")
@Data
public class User {
	@Id
	@GeneratedValue
	@Column(name="userid")
	private Integer id;
	
	private String userName;
	private String userMail;
	private String password;
	@ElementCollection
	@CollectionTable(name="roles_tab",joinColumns = @JoinColumn(name="id"))
	private List<String> roles;

}
