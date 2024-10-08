package io.github.kabanfriends.kabansmp.core.text.language;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import io.github.kabanfriends.kabansmp.core.KabanSMP;
import io.github.kabanfriends.kabansmp.core.config.LanguageConfig;
import io.github.kabanfriends.kabansmp.core.util.ResourceExtractor;
import io.github.kabanfriends.kabansmp.translation.language.Language;
import io.github.kabanfriends.kabansmp.translation.language.LanguageTranslator;
import net.kyori.adventure.translation.GlobalTranslator;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.LocaleUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;

public class LanguageManager {

    private static LanguageTranslator translator;

    public static void load() {
        new LanguageConfig().load();

        File langDir = new File("plugins/KabanSMP/lang");

        if (!langDir.exists()) {
            langDir.mkdir();
        }

        /*
        Extract language files from JAR
        */
        try {
            new ResourceExtractor(KabanSMP.getInstance(), langDir, "lang", null).extract(false);
        } catch (IOException e) {
            KabanSMP.getInstance().getLogger().log(Level.WARNING, "Failed to extract default language files!");
            e.printStackTrace();
        }

        KabanSMP.getInstance().getLogger().log(Level.INFO, "Default locale: " + LanguageConfig.DEFAULT_LOCALE.get());
        translator = new LanguageTranslator(LanguageConfig.DEFAULT_LOCALE.get());

        try {
            if (langDir.isDirectory()) {
                for (File file : langDir.listFiles()) {
                    if (file.getName().endsWith(".json")) {
                        String code = FilenameUtils.removeExtension(file.getName());
                        Locale locale;
                        try {
                            locale = LocaleUtils.toLocale(code);
                        } catch (IllegalArgumentException e) {
                            KabanSMP.getInstance().getLogger().log(Level.WARNING, "Invalid locale: " + code);
                            continue;
                        }

                        KabanSMP.getInstance().getLogger().log(Level.INFO, "Loading language: " + locale.getLanguage());

                        // Read the json
                        JsonObject json;
                        try {
                            json = JsonParser.parseReader(new FileReader(file)).getAsJsonObject();
                        } catch (JsonParseException e) {
                            KabanSMP.getInstance().getLogger().log(Level.SEVERE, "Invalid json: " + file.getName());
                            e.printStackTrace();
                            continue;
                        }

                        // Add language keys
                        Language language = new Language(locale);

                        for (String key : json.keySet()) {
                            language.addKey(key, json.getAsJsonPrimitive(key).getAsString());
                        }

                        translator.addLanguage(locale, language);
                    }
                }
            }
        } catch (Exception e) {
            KabanSMP.getInstance().getLogger().log(Level.SEVERE, "Failed to load language files:");
            e.printStackTrace();
        }

        GlobalTranslator.translator().addSource(translator);
    }

    public static void unload() {
        GlobalTranslator.translator().removeSource(translator);
        translator = null;
    }

    public static LanguageTranslator getTranslator() {
        return translator;
    }
}
