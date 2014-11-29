package com.marciosn.cloud.storage.blobs.login;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class Login implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3984002432649039239L;
	private String username;
	private String password;
	
	public Login(){
		HttpSession s = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if(s != null){
			s.invalidate();
		}
	}
	public String loginBean(){
		HttpSession s = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if(username.equals("blob") && password.equals("2014")){
			if(s == null){
				s = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			}
			s.setAttribute("username", username);
			return "/index?faces-redirect=true";
		}else{
			if(s != null){
				s.invalidate();
			}
		}
		return "/login";
	}
	public String getNome() {
		return username;
	}
	public void setNome(String nome) {
		this.username = nome;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
