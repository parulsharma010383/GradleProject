package com.biomerieux.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ManageDatabaseResetCommand extends Command {
	
	public ManageDatabaseResetCommand() {
		super("sc mkdir tempdir ");
	}

	private ProcessBuilder copyDatabase;
	private static final String OPENDS_USERROOT_DIR = System.getProperty("BIOMERIEUX_PROGRAMS",
			"C:/temp") + File.separator + "opends" + File.separator + "db" + File.separator + "userRoot";
	private static final String OPENDS_DB_BACKUP_DIR = System.getProperty("BIOMERIEUX_PROGRAMS",
			"D:/biomerieux/programs") + File.separator + "opends" + File.separator + "db" + File.separator
			+ "compression";
	private static final String OPENDS_DEFAULT_DB = "./resources";
	File dbWorkingDir = null;
	File ldifFile = null;

	@Override
	public void execute() throws IOException, InterruptedException {

		int restoreResult = 0;
		dbWorkingDir = new File(OPENDS_DB_BACKUP_DIR);
		dbWorkingDir.mkdirs();
		
		System.out.println("Inside Manage Opends");

	//	int backUpResult = makeCopyOfExistingOpenDSDatabase(new File(OPENDS_USERROOT_DIR), dbWorkingDir);
		//logger.info("after copying existingOpenDSDatabase :" + backUpResult);
		//Thread.sleep(10000);
		//int cleanUpResult = cleanUpUserRoot(new File(OPENDS_USERROOT_DIR));
		//logger.info("after cleaning UserRoot : " + cleanUpResult);
		//Thread.sleep(10000);
		
		try {
			restoreResult = installDefaultDatabase(new File(OPENDS_DEFAULT_DB), new File(OPENDS_USERROOT_DIR));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("after restoring DefaultDatabase :" + restoreResult);
	}

	private int cleanUpUserRoot(File file) throws IOException, InterruptedException {
		int resultCode = -1;
		try {
			ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "del /F /Q \"" + file.getAbsolutePath() + "\"");
			logger.info("cleanUpuserRoot" + pb.command().toString());
			System.out.println("cleanUpuserRoot" + pb.command().toString());
			Process process = pb.start();
			resultCode = process.waitFor();

		} catch (Exception e) {
			logger.info("cleanUpUserRoot :" + e.getMessage());
			e.printStackTrace();
		}
		return resultCode;
	}

	private int installDefaultDatabase(File fileres, File fileuseroot) throws IOException, InterruptedException, URISyntaxException {

		System.out.println("installDefaultDatabase");
		System.out.println("Jar files::");
//		JarFile file = new JarFile("TestFirstGradle.jar");  
//		Enumeration<JarEntry> jenum=file.entries();
//		while(jenum.hasMoreElements()){   
//		    JarEntry entry = jenum.nextElement();   
//		    System.out.println(entry.getName());   
//		} 
//		
		
		logger.info("Resource file :" + fileres);
		logger.info("Useroot file :" + fileuseroot);
		//System.out.println("Resource file :" + fileres);
		//System.out.println("Useroot file :" + fileuseroot);
		int resultCode = restoreBackDefaultDatabase(fileres, fileuseroot);
		return resultCode;
	}

	private int restoreBackDefaultDatabase(File sourceDir, File destinationDir)
			throws IOException, InterruptedException, URISyntaxException {
		logger.info("entering restoreBackDefaultDatabase method");
		System.out.println("entering restoreBackDefaultDatabase method");
		// TODO handle if no files in source folder, NULLPOINTER Exception.
		boolean hasDatabase = false;
		String resource = "00000001.jdb";
		File jdbFile = new File(this.getClass().getClassLoader().getResource(resource).getFile());
	//	File jdbFile = new File(this.getClass().getClassLoader().getResource(resource).getPath());
		String srcjdbFile = this.getClass().getClassLoader().getResource(resource).toExternalForm();
		if (null != jdbFile) {
			System.out.println("file details:" + jdbFile.toString());
			System.out.println("file srcjdbFile:" + srcjdbFile.toString());
		} else {
			System.out.println("file details: NULL" +jdbFile.toString());
			System.out.println("file srcjdbFile:" + srcjdbFile.toString());
		}

		//File[] files = destinationDir.listFiles();
	//	logger.info("Inside Restore Back Default Database :");
//		for (File dest : files) {
//			if (dest.getName().endsWith(".jdb")) {
//				throw new FileNotFoundException("JDB File already exists");
//			}
//		}

		if (!hasDatabase) {
			try {

				String sourceDirFiles = "."+ "\"" + "00000001.jdb" + "\"";
				//String sourceDirFiles = "\"" + sourceDir.getAbsolutePath() + "\\*.jdb\"";
				System.out.println("copying file destination:");
				
				
				//Path srcpath = Paths.get(srcjdbFile);
				
				//System.out.println("PATH SRCPATH : "+ srcpath.toString());
				
				String destinationPath = "\"" + destinationDir.getAbsolutePath() + "\"";
				
				//Path destpath = Paths.get(destinationPath);
				
				//System.out.println("PATH SRCPATH : "+ srcpath.toString());
			//	System.out.println("source :" + srcpath);
				System.out.println("destination :" + destinationPath);
			//	Files.copy(srcpath,destpath);
				
				
				
				  copyDatabase = new ProcessBuilder("cmd", "/c", "copy " + sourceDirFiles + " "
				  + destinationPath); 
				  logger.info("copyDatabase command:" +copyDatabase.command().toString());
				  Process process = copyDatabase.start();
				  int resultCode = process.waitFor(); 
				  System.out.println("finished copying jdb file");
				  return resultCode;
			
				
				

			} catch (Exception e) {
				logger.info("makeCopyOfExistingOpenDSDatabase Exception :" + e.getMessage());
				e.printStackTrace();
			}

		}

		return -1;
	}

	int makeCopyOfExistingOpenDSDatabase(File sourceDir, File destinationDir) throws IOException, InterruptedException {
		boolean hasDatabase = false;
		File[] files = destinationDir.listFiles();
		for (File dest : files) {
			if (dest.getName().endsWith(".jdb")) {
				throw new FileNotFoundException("JDB File already exists");
			}
		}

		if (!hasDatabase) {

			try {
				

				String sourceDirFiles = "\"" + sourceDir.getAbsolutePath() + "\\*.jdb\"";
				String destinationPath = "\"" + destinationDir.getAbsolutePath() + "\"";
				copyDatabase = new ProcessBuilder("cmd", "/c", "copy " + sourceDirFiles + " " + destinationPath);

				logger.info("copyDatabase command:" + copyDatabase.command().toString());
				Process process = copyDatabase.start();
				int resultCode = process.waitFor();
				return resultCode;
			} catch (Exception e) {
				logger.info("makeCopyOfExistingOpenDSDatabase Exception :" + e.getMessage());
				e.printStackTrace();
			}

		}

		return -1;
	}

	@Override
	String processResults(int resultCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
