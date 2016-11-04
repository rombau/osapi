package at.greenmoon.os.api.test.fixtures;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public final class ResourceLoader {

    private ResourceLoader() {
        // utility class
    }

    @SuppressWarnings("nls")
    public static byte[] getResourceStream(String path, Map<String, String> params) throws IOException {

        StringBuilder builder = new StringBuilder();

        if (params != null) {
            ArrayList<String> list = new ArrayList<String>(params.keySet());
            Collections.sort(list);
            for (String key : list) {
                if (key != null && params.get(key) != null) {
                    if (builder.length() > 0) {
                        builder.append("&");
                    }
                    builder.append(key);
                    builder.append("=");
                    builder.append(params.get(key));
                }
            }
        }

        if (builder.length() > 0) {
            path += "?";
            path += builder.toString();
        }

        InputStream is = ResourceLoader.class.getResourceAsStream(path);
        if (is == null) {
            throw new IOException("Resource " + path + " not found.");
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            int nRead;
            byte[] data = new byte[4096];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                os.write(data, 0, nRead);
            }
            os.flush();
            return os.toByteArray();
        }
        finally {
            is.close();
            os.close();
        }

    }
}