import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class FileOrganizer {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java FileOrganizer <source> <target>");
            return;
        }
        boolean dryRun = args.length >= 3 && args[2].equalsIgnoreCase("--dry-run");
        Path sourceDirectory = Paths.get(args[0]);
        Path targetDirectory = Paths.get(args[1]);
        // File extension -> folder name
        Map<String, String> categoryMap = new HashMap<>();

        categoryMap.put("jpg", "Images");
        categoryMap.put("jpeg", "Images");
        categoryMap.put("png", "Images");
        categoryMap.put("gif", "Images");

        categoryMap.put("pdf", "Documents");
        categoryMap.put("doc", "Documents");
        categoryMap.put("docx", "Documents");
        categoryMap.put("txt", "Documents");

        categoryMap.put("mp3", "Audio");
        categoryMap.put("wav", "Audio");

        categoryMap.put("mp4", "Video");
        categoryMap.put("mkv", "Video");
        categoryMap.put("avi", "Video");

        categoryMap.put("zip", "Archives");
        categoryMap.put("rar", "Archives");

        try {

            DirectoryStream<Path> files =
                    Files.newDirectoryStream(sourceDirectory);

            for (Path file : files) {

                // Ignore folders
                if (Files.isDirectory(file)) {
                    continue;
                }

                String fileName = file.getFileName().toString();

                // Find the file extension
                int dotIndex = fileName.lastIndexOf(".");

                if (dotIndex == -1) {
                    System.out.println(fileName + " -> Others");
                    continue;
                }

                String extension =
                        fileName.substring(dotIndex + 1).toLowerCase();

                // Find the category
                String category =
                        categoryMap.getOrDefault(extension, "Others");

                // Create category folder
                Path categoryFolder =
                        targetDirectory.resolve(category);

                Files.createDirectories(categoryFolder);

                // Initial destination
                Path targetFile =
                        categoryFolder.resolve(fileName);

                // Handle duplicate filenames
                int counter = 1;

                while (Files.exists(targetFile)) {

                    String baseName = fileName;
                    String extensionPart = "";

                    int duplicateDotIndex =
                            fileName.lastIndexOf(".");

                    if (duplicateDotIndex != -1) {
                        baseName =
                                fileName.substring(0, duplicateDotIndex);

                        extensionPart =
                                fileName.substring(duplicateDotIndex);
                    }

                    String newFileName =
                            baseName + "_" + counter + extensionPart;

                    targetFile =
                            categoryFolder.resolve(newFileName);

                    counter++;
                }

                if (dryRun) {
                    System.out.println(
                            "[DRY-RUN] " + fileName + " -> " + targetFile.getFileName()
                    );
                } else {
                    Files.move(file, targetFile);
                    System.out.println(
                            fileName + " -> " + targetFile.getFileName()
                    );
                }
            }

            files.close();

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
        }
    }
}