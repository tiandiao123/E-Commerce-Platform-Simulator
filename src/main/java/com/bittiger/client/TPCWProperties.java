package com.bittiger.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TPCWProperties {

	private static ResourceBundle configuration = null;
	// Information about Workload
	public int workloads[];
	public double rate[];
	public double mixRate;
	public double TPCmean;
	public long warmup;
	public long mi;
	public long warmdown;
	public long interval;

	public double rwratio;
	public double read[];
	public double write[];

	// Information about server
	public String writeQueue;
	public String readQueue[];
	public String candidateQueue[];

	public String username;
	public String password;

	public int destroyerSleepInterval;
	public String destroyTarget;

	private static transient final Logger LOG = LoggerFactory
			.getLogger(TPCWProperties.class);

	public TPCWProperties(String fileName) {
		try {
			configuration = ResourceBundle.getBundle(fileName);
			checkPropertiesFileAndGetURLGenerator();
		} catch (java.util.MissingResourceException e) {
			System.err
					.println("No properties file has been found in your classpath.<p>");
			Runtime.getRuntime().exit(1);
		}
	}

	protected String getProperty(String property) {
		return configuration.getString(property);
	}

	public void checkPropertiesFileAndGetURLGenerator() {
		try {

			mixRate = Double.parseDouble(getProperty("mixRate"));
			TPCmean = Double.parseDouble(getProperty("TPCmean"));
			warmup = Long.parseLong(getProperty("warmup"));
			warmdown = Long.parseLong(getProperty("warmdown"));
			mi = Long.parseLong(getProperty("mi"));
			interval = Long.parseLong(getProperty("interval"));

			StringTokenizer rl = null;
			int rlCnt = 0;
			rl = new StringTokenizer(getProperty("rate_vector"), ",");
			rate = new double[rl.countTokens()];
			while (rl.hasMoreTokens()) {
				rate[rlCnt] = Double.parseDouble(rl.nextToken().trim());
				rlCnt++;
			}
			// LOG.info("rate is " + Arrays.toString(rate));

			StringTokenizer wl = new StringTokenizer(
					getProperty("workload_vector"), ",");
			workloads = new int[wl.countTokens()];
			int wlCnt = 0;
			while (wl.hasMoreTokens()) {
				workloads[wlCnt] = Integer.parseInt(wl.nextToken().trim());
				wlCnt++;
			}
			LOG.info("workloads is " + Arrays.toString(workloads));

			if (workloads.length * interval != warmup + warmdown + mi) {
				LOG.error("workload length can not match warm up/down + mi");
				Runtime.getRuntime().exit(0);
			}

			rwratio = Double.parseDouble(getProperty("rwratio"));

			rl = new StringTokenizer(getProperty("read"), ",");
			read = new double[rl.countTokens()];
			rlCnt = 0;
			while (rl.hasMoreTokens()) {
				read[rlCnt] = Double.parseDouble(rl.nextToken().trim());
				rlCnt++;
			}
			LOG.info("read is " + Arrays.toString(read));

			rl = new StringTokenizer(getProperty("write"), ",");
			write = new double[rl.countTokens()];
			rlCnt = 0;
			while (rl.hasMoreTokens()) {
				write[rlCnt] = Double.parseDouble(rl.nextToken().trim());
				rlCnt++;
			}
			LOG.info("write is " + Arrays.toString(write));

			loadPropertyFromSetEnv();

			destroyerSleepInterval = Integer
					.parseInt(getProperty("destroyerSleepInterval"));
			LOG.info("destroyerSleepInterval is " + destroyerSleepInterval);
		} catch (Exception e) {
			System.err.println("Error while checking properties: "
					+ e.getMessage());
			Runtime.getRuntime().exit(0);
		}
	}

	public static ResourceBundle getConfiguration() {
		return configuration;
	}

	public static void setConfiguration(ResourceBundle configuration) {
		TPCWProperties.configuration = configuration;
	}

	private void loadPropertyFromSetEnv() throws IOException {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("scripts/set_env.sh");
			// load a properties file
			prop.load(input);

			// #!/bin/bash
			//
			// set -o allexport
			//
			// MYSQL_USERNAME='root'
			// MYSQL_PASSWORD='TigerBit!2016'
			//
			// # HOSTS
			// #MASTER=35.162.86.105
			// #SLAVE=(54.204.168.204 35.161.215.21)
			// #CANDIDATE=(35.164.142.220)
			//
			// set +o allexport
			// get the property value and print it out
			username = prop.getProperty("MYSQL_USERNAME").replaceAll("\'", "");
			password = prop.getProperty("MYSQL_PASSWORD").replaceAll("\'", "");
			LOG.info("MySQL usr/pass is " + username + "," + password);

			writeQueue = processServer(prop.getProperty("MASTER"))[0];
			LOG.info("writeQueue is " + writeQueue);
			String[] slaves = processServer(prop.getProperty("SLAVE"));
			readQueue = new String[1 + slaves.length];
			readQueue[0] = writeQueue;
			for (int i = 0; i < slaves.length; i++) {
				readQueue[i + 1] = slaves[i];
			}
			LOG.info("readQueue is " + Arrays.toString(readQueue));
			
			candidateQueue = processServer(prop.getProperty("CANDIDATE"));
			LOG.info("candidateQueue is " + Arrays.toString(candidateQueue));

			destroyTarget = slaves[0];
			LOG.info("destroyTarget is " + destroyTarget);
		} catch (IOException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String[] processServer(String servers) {
		if (servers.startsWith("(")) {
			// get rid of ()
			servers = servers.substring(1, servers.length() - 1);
		}
		return servers.split(" ");
	}

}
