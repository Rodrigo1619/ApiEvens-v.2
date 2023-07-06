package com.grupo4.topTrend.models.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"eventxusers", "tokens", "tickets", "sendTranfers", "receivedTransfers", "roles"})
@Entity
@Table(name = "usuario")
public class User implements UserDetails{
	private static final long serialVersionUID = 1460435087476558985L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;

	@Column(name = "correo_electronico")
	private String email;

	@Column(name = "nombre_usuario")
	private String username;
	
	@Column(name = "contrasenha")
	@JsonIgnore
	private String password;
	
	@Column(name = "activo", insertable = false)
	private Boolean active;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<EventXUser> eventxusers;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Token> tokens;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Ticket> tickets;

	@OneToMany(mappedBy = "oldOwner", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Transfer> sendTranfers;

	@OneToMany(mappedBy = "newOwner", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Transfer> receivedTransfers;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(
			name = "rolxusuario",
			joinColumns = @JoinColumn(name = "id_usuario"),
			inverseJoinColumns = @JoinColumn(name = "id_rol")
			)
	@JsonIgnore
	private Set<Role> roles = new HashSet<>();

	public User(String email, String username, String password) {
		super();
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	public User(String email, String username, String password, Role role) {
		super();		this.email = email;
		this.username = username;
		this.password = password;
		this.roles.add(role);
	}
	
	public User( UUID code, String email, String username, String password, Boolean active, Set<Role> roles) {
		super();
		this.code = code;
		this.email = email;
		this.username = username;
		this.password = password;
		this.active = active;
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    Set<Role> roles = this.getRoles();
	    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
	     
	    for (Role role : roles) {
	        authorities.add(new SimpleGrantedAuthority(role.getRole()));
	    }
	     
	    return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.active;
	}
}
