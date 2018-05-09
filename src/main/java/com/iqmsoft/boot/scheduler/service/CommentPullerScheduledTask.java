package com.iqmsoft.boot.scheduler.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import com.iqmsoft.boot.scheduler.dto.Comment;



@Component
public class CommentPullerScheduledTask {

	private static final Logger log = LoggerFactory
			.getLogger(CommentPullerScheduledTask.class);

	
	@Value("${periodic.scheduledJob.enabled:false}")
	private boolean scheduledJobEnabled;
	
	@Value("${periodic.incoming.comments.dir}")
	private String commentsDir;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"MM-dd-yyyy HH:mm:ss");

	@Scheduled(fixedRate = 60000) 
	public void pullRandomComment() {
		if (!scheduledJobEnabled) {
			return;
		}
		int min = 1;
		int max = 500;
		Random r = new Random();
		int id = r.nextInt((max - min) + 1) + min;
		String outFileName = createDataFile(id);
	
		Comment comment = new Comment();
		
		comment.setPostId(r.nextInt());
		comment.setEmail("test@test.com");
		comment.setBody("This is a test");
		comment.setName("test");
		comment.setId(r.nextInt());
	
		log.info("Pulled comment #" + id + " at " + dateFormat.format(new Date()));
		log.info("Writing to " + outFileName);
		
		 try(PrintStream ps = new PrintStream(outFileName)) { ps.println( comment.toString()); }
		 catch (FileNotFoundException e) {
			 log.error("Couldn't write comment file.", e);
		 }
	}

	private String createDataFile(int id) {

		File dir = new File(commentsDir);
		if (!dir.exists()) {
			try {
				dir.mkdir();
			} catch (SecurityException se) {
				throw se;
			}
		}

		String name = "comment_" + String.format("%1$03d.", id)
				+ new Date().getTime();
		return dir.getAbsolutePath() + File.separator + name;
	}

	

}
