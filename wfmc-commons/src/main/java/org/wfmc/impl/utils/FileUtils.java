package org.wfmc.impl.utils;

import javax.activation.MimetypesFileTypeMap;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * FileUtils este o clasa utilitara care :
 * <p/>
 * - faciliteaza operatiile de formatare a cailor de documente si foldere
 * in functie de delimitatorii utilizati de sistemul de fisiere utilizat
 * (delimitator root respectiv delimitator de directoare).
 * <p/>
 * - implementeaza functiile de translatie a unui fisier (inclusiv calea sa) intre 2 sisteme de fisiere care folosesc
 * delimitatori diferiti.
 * <p/>
 * - identifica mimetype-ul unui fisier in functie de extensia sa
 * <p/>
 * - implementeaza metode cara faciliteaza incarcarea unor fisiere de tip resource de pe file system
 *
 * @author Lucian DRAGOMIR
 * @version %I%, %G%
 * @since 1.0
 */
public class FileUtils {

    //static members
    private static final String extensionDelimiter = ".";
    private static final FileUtils fileUtilsDMS = new FileUtils("/", "/");
    private static final FileUtils fileUtilsOS = new FileUtils(System.getProperty("file.separator"), System.getProperty("file.separator"));
    private static MimetypesFileTypeMap mimetypesFileTypeMap = null;

    //instance members
    private String rootPath;
    private String pathDelimiter;
    private String pathDelimiterQuoted;

    public FileUtils(String rootPath, String pathDelimiter) {
        this.rootPath = rootPath;
        this.pathDelimiter = pathDelimiter;
        this.pathDelimiterQuoted = Pattern.quote(pathDelimiter);
    }

    private static MimetypesFileTypeMap createMimetypesFileTypeMap() {
        MimetypesFileTypeMap mimetypesFileTypeMapReturn = new MimetypesFileTypeMap();
        return mimetypesFileTypeMapReturn;
    }

    private static MimetypesFileTypeMap getMimetypesFileTypeMap() {
        if (mimetypesFileTypeMap == null) {
            synchronized (FileUtils.class) {
                if (mimetypesFileTypeMap == null) {
                    mimetypesFileTypeMap = createMimetypesFileTypeMap();
                }
            }
        }
        return mimetypesFileTypeMap;
    }

    public static void main(String[] args) {
        //elo
        FileUtils fuElo = new FileUtils("ARCPATH:", String.valueOf((char) 182));
        FileUtils fuCmis = new FileUtils("/", "/");
        FileUtils fuWin = new FileUtils("C:", "\\");

        FileUtils fu = fuWin;
        FileUtils fuOther = (fu.equals(fuElo) ? fuCmis : fuElo);

        String[] fileNames = {
                fu.getRootPath() + fu.getPathDelimiter(),
                fu.getRootPath(),
                fu.getPathDelimiter() + "gigi",
                fu.getPathDelimiter() + "gigi" + fu.getPathDelimiter() + "ionel" + fu.getPathDelimiter() + "textfile.txt",
                fu.getRootPath() + fu.getPathDelimiter() + "gigi" + fu.getPathDelimiter() + "ionel" + fu.getPathDelimiter() + "textfile.txt",
                fu.getPathDelimiter() + "gigi" + fu.getPathDelimiter() + "ionel" + fu.getPathDelimiter() + "noextfile",
                fu.getPathDelimiter(),
                ""};

        //fuOther = fuWin;
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Test file extensions");
        String[] arrFiles = new String[]{"a.docx", "a.pdf", "a.PDF", "a.xls", "a.png", "a.bmp"};
        for (String file : arrFiles) {
            System.out.println("File: " + file + ", mimeType: " + fu.getMimeType(file));
        }


        for (String pathName : fileNames) {
            System.out.println("-----------------------------------------------------------------");
            System.out.println("path:                    >" + pathName + "<");
            System.out.println("getNormalizedPathName:   >" + fu.getNormalizedPathName(pathName) + "<");
            System.out.println("getFileName:             >" + fu.getFileName(pathName) + "<");
            System.out.println("getFileExtension:        >" + fu.getFileExtension(pathName) + "<");
            System.out.println("getFileBaseName:         >" + fu.getFileBaseName(pathName) + "<");
            System.out.println("getParentFolderPathName: >" + fu.getParentFolderPathName(pathName) + "<");
            System.out.println("converdedPathName:       >" + fu.convertPathName(pathName, fuOther) + "<");
            System.out.println();
        }
    }


    public static String getExtensionDelimiter() {
        return extensionDelimiter;
    }

    public static FileUtils getFileUtilsDMS() {
        return fileUtilsDMS;
    }

    public static FileUtils getFileUtilsOS() {
        return fileUtilsOS;
    }

    public static FileUtils proposeFileUtils(String filePathName) {
        if (filePathName == null) {
            return FileUtils.getFileUtilsDMS();
        }
        return (filePathName.contains(FileUtils.getFileUtilsOS().getPathDelimiter())) ? FileUtils.getFileUtilsOS() : FileUtils.getFileUtilsDMS();
    }

    public String getRootPath() {
        return rootPath;
    }

    public String getPathDelimiter() {
        return pathDelimiter;
    }

    public String getMimeType(String filePathName) {
        String mimeType = null;
        try {
            String fileName = getFileName(filePathName).toLowerCase();
            //mimeType = Files.probeContentType(FileSystems.getFileUtilsDMS().getPath(fileName));
            synchronized (FileUtils.class) {
                mimeType = getMimetypesFileTypeMap().getContentType(fileName);
            }

            if (mimeType == null)
                return "application/octet-stream";

            //it is just to keep the exception catch-er required when Files/FileSystems from nio is used with jdk1.7
            if (false) {
                throw new IOException();
            }
        } catch (IOException e) {
            return "application/octet-stream";
        }
        return mimeType;
    }

    public String getFileNameWithExtension(String fileBaseName, String fileExtension) {
        return getFileBaseName(fileBaseName) + extensionDelimiter + fileExtension;
    }

    public String getFileName(String filePathName) {
        String fileName = filePathName;
        int lastDelimiterIndex = filePathName.lastIndexOf(pathDelimiter);
        if (lastDelimiterIndex != -1) {
            fileName = filePathName.substring(lastDelimiterIndex + 1);
        }
        if (fileName.startsWith(rootPath)) {
            fileName = fileName.substring(rootPath.length());
        }
        return fileName;
    }

    public String getFolderName(String folderPathName) {
        return getFileName(folderPathName);
    }

    public String getFileExtension(String filePathName) {
        String fileExtension = "";
        String fileName = getFileName(filePathName);
        int lastDelimiterIndex = fileName.lastIndexOf(extensionDelimiter);
        if (lastDelimiterIndex != -1) {
            fileExtension = fileName.substring(lastDelimiterIndex + 1);
        }
        return fileExtension;
    }

    public String getFileBaseName(String filePathName) {
        String fileName = getFileName(filePathName);
        String fileBaseName = fileName;
        int lastDelimiterIndex = fileName.lastIndexOf(extensionDelimiter);
        if (lastDelimiterIndex != -1) {
            fileBaseName = fileName.substring(0, lastDelimiterIndex);
        }
        return fileBaseName;
    }

    public boolean isValidName(String name) {
        if (name == null
                || name.isEmpty()

                //deleted in order to work with jdk1.6
                //|| name.indexOf(File.separatorChar) != -1

                || name.indexOf(this.pathDelimiter) != -1
                || !name.equalsIgnoreCase(name.trim())) {
            return false;
        }
        return true;
    }

    public String getNormalizedPathName(String pathName) {
        if (!pathName.startsWith(rootPath)) {
            if (pathName.startsWith(pathDelimiter)) {
                pathName = rootPath + pathName;
            } else {
                pathName = rootPath + pathDelimiter + pathName;
            }
        }
        if (isRootDelimiter() && (pathName.startsWith(rootPath + pathDelimiter))) {
            pathName = rootPath + pathName.substring((rootPath + pathDelimiter).length());
        }
        if (pathName.equals(rootPath + pathDelimiter)) {
            pathName = rootPath;
        }
        return pathName;
    }

    public String getParentFolderPathName(String pathName) {
        String parentFolderPathName = pathName;
        int lastDelimiterIndex = parentFolderPathName.lastIndexOf(pathDelimiter);
        if (lastDelimiterIndex != -1 && lastDelimiterIndex != 0) {
            parentFolderPathName = parentFolderPathName.substring(0, lastDelimiterIndex);
        } else {
            parentFolderPathName = rootPath;
        }
        return parentFolderPathName;
    }

    public String concatenate(String pathName, String name) {
        if (name == null || name.trim().isEmpty()) {
            return pathName;
        }
        if (!name.equals(getFileName(name))) {
            throw new RuntimeException("The name is a tree path name, the method accepts only simple name (without path).");
        }
        return pathName + (pathName.endsWith(pathDelimiter) ? "" : pathDelimiter) + name;
    }

    public String convertPathName(String pathName, FileUtils to) {
        pathName = getNormalizedPathName(pathName).substring(rootPath.length());
        pathName = pathName.replaceAll(this.pathDelimiterQuoted, to.getPathDelimiter());
        return to.getNormalizedPathName(pathName);
    }

    public String convertPathNameWithoutNormalize(String pathName, FileUtils to) {
        String toPathDelimiter = java.util.regex.Matcher.quoteReplacement(to.getPathDelimiter());
        pathName = pathName.replaceAll(this.pathDelimiterQuoted, toPathDelimiter);
        return pathName;
    }

    private boolean isRootDelimiter() {
        return rootPath.endsWith(pathDelimiter);
    }

    public static Properties openOsResource(String osFilePath) throws IOException {
        Properties prop = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(osFilePath);
            prop.load(in);
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                //do nothing
            }

        }
        return prop;
    }

    public static String extractFilePath(String filePathName) {
        if (filePathName == null)
            return null;
        int slashPos = filePathName.lastIndexOf('\\');
        if (slashPos == -1)
            slashPos = filePathName.lastIndexOf('/');

        return filePathName.substring(0, slashPos + 1);
    }

    public static String replace(String template, Map<String, String> placeholderMap) {
        String replaced = template;
        for (String key : placeholderMap.keySet()) {
            replaced = replaced.replace(key, placeholderMap.get(key));
        }
        return replaced;
    }

    public static String replaceTemporalItems(String template) {
        Map<String, String> placeholderMap = null;
        //init map
        placeholderMap = calcDateParsingItems(getDateParsingFormats());

        //replace items in map
        return replace(template, placeholderMap);
    }

    private static Map<String, SimpleDateFormat> getDateParsingFormats() {
        Map<String, SimpleDateFormat> dateParsingFormats = new HashMap<String, SimpleDateFormat>();
        String[] itemParts = new String[]{"yyyy", "MM", "dd", "HH", "mm", "ss"};
        for (int index = 0; index < itemParts.length; index++) {
            dateParsingFormats.put("${" + itemParts[index] + "}", new SimpleDateFormat(itemParts[index]));
        }
        return dateParsingFormats;
    }

    private static Map<String, String> calcDateParsingItems(Map<String, SimpleDateFormat> simpleDateFormatMap) {
        Map<String, String> dateParsingValues = new HashMap<String, String>();
        Calendar calendar = Calendar.getInstance();
        for (String key : simpleDateFormatMap.keySet()) {
            synchronized (simpleDateFormatMap) {
                dateParsingValues.put(key, simpleDateFormatMap.get(key).format(calendar.getTime()));
            }
        }
        return dateParsingValues;
    }
}
