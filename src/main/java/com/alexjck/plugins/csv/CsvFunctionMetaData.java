package com.alexjck.plugins.csv;

import org.graylog2.plugin.PluginMetaData;
import org.graylog2.plugin.ServerStatus;
import org.graylog2.plugin.Version;

import java.net.URI;
import java.util.Collections;
import java.util.Set;

public class CsvFunctionMetaData implements PluginMetaData {
    private static final String PLUGIN_PROPERTIES = "com.alexjck.graylog.plugins.graylog-plugin-function-csv/graylog-plugin.properties";

    @Override
    public String getUniqueId() {
        return "com.alexjck.plugins.csv.CsvFunctionPlugin";
    }

    @Override
    public String getName() {
        return "Csv function";
    }

    @Override
    public String getAuthor() {
        return "Alexander Campos <alexjck@gmail.com>";
    }

    @Override
    public URI getURL() {
        return URI.create("https://github.com/alcampos/graylog-plugin-function-csv");
    }

    @Override
    public Version getVersion() {
        return Version.fromPluginProperties(getClass(), PLUGIN_PROPERTIES, "version", Version.from(0, 0, 1, "unknown"));
    }

    @Override
    public String getDescription() {
        return "Pipeline function that returns key value map from csv";
    }

    @Override
    public Version getRequiredVersion() {
        return Version.fromPluginProperties(getClass(), PLUGIN_PROPERTIES, "graylog.version", Version.from(2, 0, 0));
    }

    @Override
    public Set<ServerStatus.Capability> getRequiredCapabilities() {
        return Collections.emptySet();
    }
}
