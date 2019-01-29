/* Created by JReleaseInfo AntTask from Open Source Competence Group */
/* Creation date Tue Jan 29 11:41:07 EET 2019 */
package versioninfo;

import java.util.Date;

/**
 * This class provides information gathered from the build environment.
 * 
 * @author JReleaseInfo AntTask
 */
public class ReleaseInfo {


   /** buildDate (set during build process to 1548754867817L). */
   private static Date buildDate = new Date(1548754867817L);

   /**
    * Get buildDate (set during build process to Tue Jan 29 11:41:07 EET 2019).
    * @return Date buildDate
    */
   public static final Date getBuildDate() { return buildDate; }


   /** project (set during build process to "Word Game"). */
   private static String project = "Word Game";

   /**
    * Get project (set during build process to "Word Game").
    * @return String project
    */
   public static final String getProject() { return project; }


   /** version (set during build process to "0.6"). */
   private static String version = "0.6";

   /**
    * Get version (set during build process to "0.6").
    * @return String version
    */
   public static final String getVersion() { return version; }


   /**
    * Get releaseYear (set during build process to 2019).
    * @return int releaseYear
    */
   public static final int getReleaseYear() { return 2019; }


   /** publisherName (set during build process to "IXG Java Team 1"). */
   private static String publisherName = "IXG Java Team 1";

   /**
    * Get publisherName (set during build process to "IXG Java Team 1").
    * @return String publisherName
    */
   public static final String getPublisherName() { return publisherName; }


   /**
    * Get debugInfo (set during build process to false).
    * @return boolean debugInfo
    */
   public static final boolean isDebugInfo() { return false; }


   /**
    * Get buildNumber (set during build process to 4).
    * @return int buildNumber
    */
   public static final int getBuildNumber() { return 4; }

}
