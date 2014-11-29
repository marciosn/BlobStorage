package com.marciosn.cloud.storage.blobs.controll.rep;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
@ManagedBean
@SessionScoped
public class Repositorio {
	
	public Repositorio(){	
	}
	
	List<Blob> lista = new ArrayList<Blob>();
	List<Blob> listamusics = new ArrayList<Blob>();
	List<Blob> listvideos = new ArrayList<Blob>();
	List<Blob> listapdfs = new ArrayList<Blob>();
	List<Blob> listageneral = new ArrayList<Blob>();
	public List<Blob> getLista() {
		return lista;
	}

	public void setLista(List<Blob> lista) {
		this.lista = lista;
	}
	
	public List<Blob> getListamusics() {
		return listamusics;
	}

	public void setListamusics(List<Blob> listamusics) {
		this.listamusics = listamusics;
	}

	public List<Blob> getListvideos() {
		return listvideos;
	}

	public void setListvideos(List<Blob> listvideos) {
		this.listvideos = listvideos;
	}

	public List<Blob> getListapdfs() {
		return listapdfs;
	}

	public void setListapdfs(List<Blob> listapdfs) {
		this.listapdfs = listapdfs;
	}

	public List<Blob> getListageneral() {
		return listageneral;
	}

	public void setListageneral(List<Blob> listageneral) {
		this.listageneral = listageneral;
	}

	public String insere(Blob b){
		lista.add(b);
		return "lista";
	}
}
