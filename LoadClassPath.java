
package inventory_system;

import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author tebogo
 */
public class LoadClassPath extends URLClassLoader
{

    public LoadClassPath(URL[] urls) {
        super(urls);
    }
    
    @Override
    public void addURL(URL url){
        super.addURL(url);
    }
}
