package io.github.kabanfriends.kabansmp.core.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import io.github.kabanfriends.kabansmp.core.KabanSMPPlugin;
import io.github.kabanfriends.kabansmp.core.util.ResourceExtractor;
import io.github.kabanfriends.kabansmp.translation.language.Language;
import io.github.kabanfriends.kabansmp.translation.language.LanguageTranslator;
import net.kyori.adventure.translation.GlobalTranslator;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.LocaleUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;

public class LanguageConfig {

    public static Locale defaultLocale = Locale.US;
    public static LanguageTranslator translator;

    public static void load() {
        KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "Loading language config");

        File langDir = new File("plugins/KabanSMP/lang");
        if (!langDir.exists()) {
            langDir.mkdir();
        }

        File langConfig = new File("plugins/KabanSMP/lang.json");
        if (langConfig.exists()) {
            JsonObject json = null;
            try {
                json = JsonParser.parseReader(new FileReader(langConfig)).getAsJsonObject();
            } catch (FileNotFoundException ignored) {
            } catch (JsonParseException e) {
                KabanSMPPlugin.getInstance().getLogger().log(Level.SEVERE, "Invalid json: " + langConfig.getName());
                e.printStackTrace();
            }

            if (json != null) {
                defaultLocale = LocaleUtils.toLocale(json.getAsJsonPrimitive("defaultLocale").getAsString());

                if (json.getAsJsonPrimitive("extractFiles").getAsBoolean()) {
                    try {
                        new ResourceExtractor(KabanSMPPlugin.getInstance(), langDir, "lang", ".*\\.json").extract(true);
                    } catch (IOException e) {
                        e.printStackTrace();
                        KabanSMPPlugin.getInstance().getLogger().log(Level.SEVERE, "Failed to extract language files!");
                    }
                }
            }
        }

        KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "Default locale: " + defaultLocale.getLanguage());

        translator = new LanguageTranslator(defaultLocale);

        try {
            if (langDir.isDirectory()) {
                for (File file : langDir.listFiles()) {
                    if (file.getName().endsWith(".json")) {
                        String code = FilenameUtils.removeExtension(file.getName());
                        Locale locale;
                        try {
                            locale = LocaleUtils.toLocale(code);
                        } catch (IllegalArgumentException e) {
                            KabanSMPPlugin.getInstance().getLogger().log(Level.WARNING, "Invalid locale: " + code);
                            continue;
                        }

                        KabanSMPPlugin.getInstance().getLogger().log(Level.INFO, "Loading language: " + locale.getLanguage());

                        // Read the json
                        JsonObject json;
                        try {
                            json = JsonParser.parseReader(new FileReader(file)).getAsJsonObject();
                        } catch (JsonParseException e) {
                            KabanSMPPlugin.getInstance().getLogger().log(Level.SEVERE, "Invalid json: " + file.getName());
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
            KabanSMPPlugin.getInstance().getLogger().log(Level.SEVERE, "Failed to load language files:");
            e.printStackTrace();
        }

        GlobalTranslator.translator().addSource(translator);
    }

    public static void unload() {
        GlobalTranslator.translator().removeSource(translator);
        translator = null;
    }
}
