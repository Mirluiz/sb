package zip.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

@RestController
public class ZipController {

    @PostMapping("/zip")
    public HashMap<String, String> store(@RequestParam(value = "file_path", defaultValue = "") String file_path) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");
        Zip z = new Zip(123);
        z.zip("/Users/zeynal/sites/javadump/dir");
        return map;
//        return new Zip(123);
    }
}
