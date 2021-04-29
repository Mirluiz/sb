package zip.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {
    private int id;

    public Zip(int id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }


    public void zip(String p) throws IOException {

        Path file = new File(p).toPath();

        boolean isPathDir = Files.isDirectory(file);

        File fZip = new File(isPathDir? file.toAbsolutePath() + "/test.zip" : file.getParent() + "/test.zip");
//        File fZip = new File("/Users/zeynal/sites/javadump/test.zip"); //temp. replace it with user input
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(fZip));

        if (isPathDir){

            Path abs_p = Paths.get(p);

            try (Stream<Path> paths = Files.walk(abs_p)) {
                paths
                        .filter(Files::isRegularFile)
                        .forEach(path -> {
                            try {
                                Path relative_path = abs_p.relativize(path.toAbsolutePath());
                                ZipEntry e = new ZipEntry(String.valueOf(relative_path));
                                out.putNextEntry(e);

                                byte[] bytes = Files.readAllBytes(path);
                                out.write(bytes, 0, bytes.length);
                                out.closeEntry();
                            } catch (IOException ioException) {
                                System.out.println(ioException.getMessage());
                            }
                        });
            }

            out.finish();
            out.close();
        } else {
            Path fileName = Paths.get(p).getFileName();
            ZipEntry e = new ZipEntry(String.valueOf(fileName));
            out.putNextEntry(e);

            byte[] bytes = Files.readAllBytes(Paths.get(p));
            out.write(bytes, 0, bytes.length);
            out.closeEntry();

            out.finish();
            out.close();
        }


    }
}
