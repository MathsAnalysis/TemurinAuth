package it.mathsanalysis.auth.config;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ConfigFile {

    private final String fileName;
    private final Path dataDirectory;
    private final Path filePath;
    private final ConfigurationLoader<CommentedConfigurationNode> loader;
    private CommentedConfigurationNode rootNode;


    public ConfigFile(Path dataDirectory, String fileName) {
        this.dataDirectory = dataDirectory;
        this.fileName = fileName;
        this.filePath = dataDirectory.resolve(fileName + ".yml");
        this.loader = YamlConfigurationLoader.builder().path(filePath).build();

        createFileIfNotExists();
        load();
    }


    private void createFileIfNotExists() {
        if (Files.notExists(dataDirectory)) {
            try {
                Files.createDirectories(dataDirectory);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (Files.notExists(filePath)) {
            try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream(fileName + ".yml")) {
                if (stream == null) {
                    throw new IllegalStateException("File di configurazione di default non trovato nelle risorse.");
                }
                Files.copy(stream, filePath);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    public void load() {
        try {
            rootNode = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void save() {
        try {
            loader.save(rootNode);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getString(String path, String def) {
        return rootNode.node(path).getString(def);
    }

    public int getInt(String path, int def) {
        return rootNode.node(path).getInt(def);
    }

    public boolean getBoolean(String path, boolean def) {
        return rootNode.node(path).getBoolean(def);
    }

    public double getDouble(String path, double def) {
        return rootNode.node(path).getDouble(def);
    }

    public List<String> getStringList(String path) {
        try {
            return rootNode.node(path).getList(String.class);
        } catch (SerializationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public CommentedConfigurationNode getNode(String path) {
        return rootNode.node(path);
    }

}