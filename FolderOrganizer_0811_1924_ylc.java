// 代码生成时间: 2025-08-11 19:24:32
@Component
public class FolderOrganizer {

    // Logger for error tracking and information logging
    private static final Logger log = LoggerFactory.getLogger(FolderOrganizer.class);

    private final PathMatcher pathMatcher = new AntPathMatcher();

    /**
     * Method to organize folder structure based on a given root path and pattern.
     *
     * @param rootPath The root directory path to organize.
     * @param pattern  The pattern to match files against for organizing.
     */
    public void organizeFolderStructure(String rootPath, String pattern) {
        try {
            // Validate inputs
            if (rootPath == null || pattern == null) {
                throw new IllegalArgumentException("Root path and pattern cannot be null");
            }

            // Construct root path using Paths API for better file operations
            Path root = Paths.get(rootPath);

            // Check if root path exists and is a directory
            if (!Files.exists(root) || !Files.isDirectory(root)) {
                throw new IllegalArgumentException("Root path must be a valid directory");
            }

            // Iterate over each file in the root directory
            Files.walk(root).filter(Files::isRegularFile).forEach(filePath -> {
                try {
                    // Extract the file name and check against pattern
                    String fileName = filePath.getFileName().toString();
                    if (pathMatcher.match(pattern, fileName)) {
                        // Organize file by moving it to a new directory structure
                        Path targetPath = root.resolve("organized").resolve(fileName);
                        Files.move(filePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                        log.info("File {} organized to {}