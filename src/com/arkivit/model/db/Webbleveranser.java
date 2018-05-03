package com.arkivit.model.db;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="webbleveranser")
public class Webbleveranser {


		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name="ID")
		private int id;

		@Column(name="Company")
		private String company;

		@Column(name="Excel_File")
		private String excelFile;

		@Column(name="Log")
		private String log;
		
		public Webbleveranser() {
			
		}

		public Webbleveranser(String company, String excelFile, String log) {
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

		public String getExcelFile() {
			return excelFile;
		}

		public void setExcelFile(String excelFile) {
			this.excelFile = excelFile;
		}

		public String getLog() {
			return log;
		}

		public void setLog(String log) {
			this.log = log;
		}

		@Override
		public String toString() {
			return "WebLev [id=" + id + ", company=" + company + ", excelFile=" + excelFile + ", log=" + log + "]";
		}

	}
