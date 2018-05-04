package com.arkivit.model.db;

import java.io.File;
import java.io.FileInputStream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
		
		@Lob
		@Column(name="Excel_File")
		private File excelFile;


		public Webbleveranser() {
			
		}

		public Webbleveranser(String company, File excelFile) {
			this.company = company;
			this.excelFile = excelFile;

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

	
		public void setExcelFile(File excelFile) {
			this.excelFile = excelFile;
		}


		@Override
		public String toString() {
			return "WebLev [id=" + id + ", company=" + company + ", excelFile=" + excelFile + "]";
		}

	}
