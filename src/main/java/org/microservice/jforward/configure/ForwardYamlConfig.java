package org.microservice.jforward.configure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.microservice.jforward.configure.ForwardConfig.UpstreamConfig;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 *  YAML configure
 */
public class ForwardYamlConfig implements Configure<ForwardConfig>{

	private Yaml yaml = new Yaml();
	private ForwardConfig configData = null;
	
	@Override
	public void load(String file) throws FileNotFoundException {
		configData = yaml.loadAs(new FileInputStream(new File(file)), ForwardConfig.class);
	}

	@Override
	public <C> C getProperty(String name, Class<C> clazz) {
		return null;
	}

	@Override
	public ForwardConfig getProperties() {
		return configData;
	}

	
	public static void testDump() {
		ForwardConfig forwardConfig = new ForwardConfig();
		forwardConfig.setHost("/host");
		forwardConfig.setName("tn");
		forwardConfig.setPort(8080);
		UpstreamConfig upstreamConfig = new UpstreamConfig();
		upstreamConfig.setName("upstream");
		List<String> urls = new ArrayList<>();
		urls.add("http://test1");
		urls.add("http://test2");
		upstreamConfig.setUrls(urls);
		Map<String, String> nameMappings = new HashMap<>();
		nameMappings.put("/test", "upstream");
		forwardConfig.setProxy(nameMappings);
		List<UpstreamConfig> ups = new ArrayList<>();
		ups.add(upstreamConfig);
		forwardConfig.setUpstreams(ups);
		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		Yaml y = new Yaml(options);
		String dump = y.dump(forwardConfig);
		System.out.println(dump);
	}
	
	public static void testLoad() {
		ForwardYamlConfig config = new ForwardYamlConfig();
		try {
			config.load("/Users/zhoupulei/Projects/git/zuul/zuul-undertow/src/main/resources/test.yaml");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(config.getProperties());
	}
	
	public static void main(String args[]){
		testLoad();
	}
}
