package interfaces;

import sun.misc.Resource;

import java.nio.file.Path;

/**
 * {@link ResourceLoader}
 */
public interface ResourceLoader {
    Resource load(String resourcePath);

    default Resource load(Path resourcePath) {
        // provide default implementation to load
        // resource from a Path and return the content
        // in a Resource object.
        return null;
    }
}
