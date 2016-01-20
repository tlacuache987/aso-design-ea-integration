package mx.com.adesis.asodesign.eamodeler.modeltojson;

import java.io.IOException;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.ui.velocity.VelocityEngineFactory;

public class VelocityApplicationConfiguration {

    public VelocityEngine getVelocityEngine() throws VelocityException, IOException{
    	VelocityEngineFactory factory = new VelocityEngineFactory();
        Properties props = new Properties();
        props.put("resource.loader", "class");
        props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
               
        factory.setVelocityProperties(props);
        return factory.createVelocityEngine();      
    }
}