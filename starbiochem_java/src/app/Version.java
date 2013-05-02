/* Created by JReleaseInfo AntTask from Open Source Competence Group */
/* Creation date Wed Apr 24 15:23:37 EDT 2013 */
package app;

import java.util.Date;

/**
 * This class provides information gathered from the build environment.
 * 
 * @author JReleaseInfo AntTask
 */
public class Version {


   /** buildDate (set during build process to 1366831417115L). */
   private static Date buildDate = new Date(1366831417115L);

   /**
    * Get buildDate (set during build process to Wed Apr 24 15:23:37 EDT 2013).
    * @return Date buildDate
    */
   public static final Date getBuildDate() { return buildDate; }


   /** year (set during build process to "${year}"). */
   private static String year = new String("${year}");

   /**
    * Get year (set during build process to "${year}").
    * @return String year
    */
   public static final String getYear() { return year; }


   /** project (set during build process to "StarBiochem"). */
   private static String project = new String("StarBiochem");

   /**
    * Get project (set during build process to "StarBiochem").
    * @return String project
    */
   public static final String getProject() { return project; }


   /** buildTimestamp (set during build process to "${build.time}"). */
   private static String buildTimestamp = new String("${build.time}");

   /**
    * Get buildTimestamp (set during build process to "${build.time}").
    * @return String buildTimestamp
    */
   public static final String getBuildTimestamp() { return buildTimestamp; }


   /** version (set during build process to "${version}"). */
   private static String version = new String("${version}");

   /**
    * Get version (set during build process to "${version}").
    * @return String version
    */
   public static final String getVersion() { return version; }

}
