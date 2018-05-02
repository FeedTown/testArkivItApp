package com.hibernate;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="webbleveranser")
public class WebLev {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	
	@Column(name="Company")
	private String company;
	
	@Column(name="Excel_File")
	private File excelFile;
	
	@Column(name="Log")
	private File log;
	
	public WebLev() {
		
	}

	public WebLev(String company, File excelFile, File log) {
		this.company = company;
		this.excelFile = excelFile;
		this.log = log;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public File getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	public File getLog() {
		return log;
	}

	public void setLog(File log) {
		this.log = log;
	}

	@Override
	public String toString() {
		return "WebLev [id=" + id + ", company=" + company + ", excelFile=" + excelFile + ", log=" + log + "]";
	}
	
	
	
}
