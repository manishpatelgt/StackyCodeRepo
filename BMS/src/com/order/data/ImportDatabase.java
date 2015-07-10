package com.order.data;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.order.application.Consts;

public class ImportDatabase {

	InputStream ips;
	String DBNAME;

	public ImportDatabase(InputStream inputstream) {
		// TODO Auto-generated constructor stub
		this.ips = inputstream;

	}

	public void copyDataBase() {

		try {
			// Open your local db as the input stream

			OutputStream databaseOutputStream = new FileOutputStream(
					Consts.APPLICATION_DATABASES_PATH+"order");
			// Path to the just created empty db

			InputStream databaseInputStream;

			byte[] buffer = new byte[1024];
			@SuppressWarnings("unused")
			int length;

			databaseInputStream = ips;

			while ((length = databaseInputStream.read(buffer)) > 0) {
				databaseOutputStream.write(buffer);
			}

			databaseInputStream.close();
			databaseOutputStream.flush();
			databaseOutputStream.close();

		} catch (Exception e) {
			// TODO: handle exception
			// Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}
}
